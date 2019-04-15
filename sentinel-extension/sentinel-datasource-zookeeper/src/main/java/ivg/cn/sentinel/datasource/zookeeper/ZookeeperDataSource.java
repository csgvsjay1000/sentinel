package ivg.cn.sentinel.datasource.zookeeper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import ivg.cn.sentinel.core.util.NamedThreadFactory;
import ivg.cn.sentinel.datasource.extension.AbstractDataSource;
import ivg.cn.sentinel.datasource.extension.Converter;

public class ZookeeperDataSource<T> extends AbstractDataSource<String, T>{
	private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;
    
    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
    		new ArrayBlockingQueue<>(1), new NamedThreadFactory("sentinel-zookeeper-ds-update"),
    		new ThreadPoolExecutor.DiscardOldestPolicy());
    		
	private final String path;
	CuratorFramework zkClient;
	
	private NodeCache nodeCache = null;
	private NodeCacheListener listener;
	
	public ZookeeperDataSource(final String serverAddr,String groupId, String dataId,Converter<String, T> parser) {
		super(parser);
		this.path = getPath(groupId, dataId);
		
		init(serverAddr);
	}
	
	private void init(final String serverAddr) {
		// 1、创建zk连接，并监听节点变化
		// 2、读取加载节点数据
		initZookeeperListener(serverAddr);
		
		try {
			String reString = readSource();
			getProperty().updateValue(parser.convert(reString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initZookeeperListener(final String serverAddr) {
		
		this.listener = new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				// 节点数据改变
				// 更新规则数据
				String configInfo = null;
				ChildData childData = nodeCache.getCurrentData();
				if (childData != null && childData.getData() != null) {
					configInfo = new String(childData.getData());
				}
				try {
					T newValue = ZookeeperDataSource.this.parser.convert(configInfo);
					getProperty().updateValue(newValue);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		zkClient = CuratorFrameworkFactory.newClient(serverAddr, new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
		zkClient.start();
		try {
			Stat stat = zkClient.checkExists().forPath(path);
			if (stat == null) {
				zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
			}
			// 监听节点数据变化
			this.nodeCache = new NodeCache(zkClient, path);
			this.nodeCache.getListenable().addListener(listener, pool);
			this.nodeCache.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public String readSource() throws Exception {
		if (zkClient != null) {
			byte[] data = zkClient.getData().forPath(path);
			if (data != null) {
				return new String(data);
			}
		}
		return null;
	}

	@Override
	public void close() throws Exception {
		if (nodeCache != null) {
			nodeCache.getListenable().removeListener(listener);
			nodeCache.close();
		}
		if (zkClient != null) {
			zkClient.close();
		}
		pool.shutdown();
	}
	

	private String getPath(String groupId, String dataId) {
        return String.format("/%s/%s", groupId, dataId);
    }
}
