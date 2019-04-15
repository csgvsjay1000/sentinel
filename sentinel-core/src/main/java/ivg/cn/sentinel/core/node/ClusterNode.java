package ivg.cn.sentinel.core.node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 针对不同线程，统一的统计
 * 一个资源只有一个ClusterNode
 * 该实例会存在多个线程同时访问的问题，注意并发
 * */
public class ClusterNode extends StatisticNode{

	/**  一个资源可以接受很多来源的请求
	 * 针对不同来源的请求做限制，若来源为default则不限制
	 *  */
	Map<String, StatisticNode> originCountMap = new HashMap<>();
	final ReentrantLock lock = new ReentrantLock();
	
	/**  获取或创建originNode */
	public StatisticNode getOrCreateOriginNode(String origin) {
		StatisticNode node = originCountMap.get(origin);
		if (node == null) {
			try {
				lock.lock();
				node = originCountMap.get(origin);
				if (node == null) {
					node = new StatisticNode();
					Map<String, StatisticNode> newMap = new HashMap<>(originCountMap.size()+1);
					newMap.putAll(originCountMap);
					newMap.put(origin, node);
					originCountMap = newMap;
				}
			} finally {
				lock.unlock();
			}
		}
		return node;
	}
	
}
