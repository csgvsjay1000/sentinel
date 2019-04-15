package ivg.cn.sentinel.core.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import ivg.cn.sentinel.core.Constants;
import ivg.cn.sentinel.core.EntryType;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.node.EntranceNode;
import ivg.cn.sentinel.core.slotchain.StringResourceWrapper;

public class ContextUtil {

	private static ThreadLocal<Context> contextHolder = new ThreadLocal<>();
	
	static volatile Map<String, DefaultNode> contextNameNodeMap = new HashMap<>();
	static ReentrantLock LOCK = new ReentrantLock();
	
	public static Context trueEnter(String name, String origin) {
		Context context = contextHolder.get();
		
		if (context == null) {
			Map<String, DefaultNode> localCacheNameMap = contextNameNodeMap;
			DefaultNode node = localCacheNameMap.get(name);
			if (node == null) {
				// 如果节点为空
				try {
					LOCK.lock();
					node = localCacheNameMap.get(name);
					if (node == null) {
						node = new EntranceNode(new StringResourceWrapper(name, EntryType.IN), null);
						Constants.ROOT.addChild(node);
						
						Map<String, DefaultNode> newMap = new HashMap<>(contextNameNodeMap.size()+1);
						newMap.putAll(contextNameNodeMap);
						newMap.put(name, node);
						contextNameNodeMap = newMap;
					}
				} finally {
					LOCK.unlock();
				}
			}
			
			// 如果节点不为空，说明之前清理了
			context = new Context(node, name);
			context.setOrigin(origin);
			contextHolder.set(context);
		}
		
		return context;
	}
	
}
