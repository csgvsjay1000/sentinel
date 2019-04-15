package ivg.cn.sentinel.core.slots.clusterbuilder;

import java.util.HashMap;
import java.util.Map;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.ClusterNode;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.node.Node;
import ivg.cn.sentinel.core.slotchain.ProcessorSlot;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;

public class ClusterBuilderSlot implements ProcessorSlot<DefaultNode>{

	
	private volatile ClusterNode clusterNode;
	
	private static final Object lock = new Object();  // 全局锁，所有资源共享创建的锁
	static volatile Map<ResourceWrapper, ClusterNode> clusterNodeMap = new HashMap<>();
	
	@Override
	public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode node, int count,
			boolean prioritized, Object... args) throws Throwable {
		if (clusterNode == null) {
			synchronized (lock) {
				if (clusterNode == null) {
					clusterNode = new ClusterNode();
					Map<ResourceWrapper, ClusterNode> newMap = new HashMap<>(clusterNodeMap.size()+1);
					newMap.putAll(clusterNodeMap);
					newMap.put(node.getId(), clusterNode);
					clusterNodeMap = newMap;
				}
			}
		}
		node.setClusterNode(clusterNode);
		
		if (!"".equals(context.getOrigin())) {
			// 如果请求来源不为空，可能为default
			// 创建originNode
			// 为context上下文环境下所有的Entry设置originNode
			Node originNode = node.getClusterNode().getOrCreateOriginNode(context.getOrigin());
			context.getCurEntry().setOriginNode(originNode);
		}
		
	}

}
