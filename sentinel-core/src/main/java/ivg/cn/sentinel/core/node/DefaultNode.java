package ivg.cn.sentinel.core.node;

import java.util.HashSet;
import java.util.Set;

import ivg.cn.sentinel.core.slotchain.ResourceWrapper;

/**
 * 同一资源有多个节点（不同线程同时发送请求，来源相同也会有多个请求同时到达）
 * */
public class DefaultNode extends StatisticNode{

	ResourceWrapper id;
	
	ClusterNode clusterNode;
	
	volatile Set<Node> childList = new HashSet<>();
	
	public DefaultNode(ResourceWrapper id, ClusterNode clusterNode) {
		this.id = id;
		this.clusterNode = clusterNode;
	}
	
	public void addChild(Node node) {
		if (!childList.contains(node)) {
			synchronized (this) {
				if (!childList.contains(node)) {
					Set<Node> newSet = new HashSet<>(childList.size() + 1);
                    newSet.addAll(childList);
                    newSet.add(node);
                    childList = newSet;
				}
			}
		}
	}
	
	public ResourceWrapper getId() {
		return id;
	}
	
	public void setClusterNode(ClusterNode clusterNode) {
		this.clusterNode = clusterNode;
	}
	
	public ClusterNode getClusterNode() {
		return clusterNode;
	}
}
