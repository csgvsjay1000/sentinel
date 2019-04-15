package ivg.cn.sentinel.core.slots.nodeselector;

import java.util.HashMap;
import java.util.Map;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.slotchain.ProcessorSlot;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;

/**
 * 负责构建调用链，用于熔断操作
 * 每个资源有且只有一个NodeSelectorSlot实例
 * 资源用名称标识
 * 一个分布式环境下的微服务不会超过100个资源
 * */
public class NodeSelectorSlot implements ProcessorSlot<Object>{

	private volatile Map<String/**context.name*/, DefaultNode> map = new HashMap<>(10);
	
	@Override
	public void entry(Context context, ResourceWrapper resourceWrapper, Object param, int count, boolean prioritized,
			Object... args) throws Throwable {
		// 1、根据context.name判断全局是否有resourceWrapper.name的Node，每次退出完整调用链时会清空该线程的context的，也清空该context下的entranceNode
		// 2、若没有，则创建一个DefaultNode(resourceWrapper),存入缓存(context.name,DefaultNode(resourceWrapper))
		// 3、获取context关联的curEntry的上一个Entry的Node，并将新建的Node设置为其child
		// 3.1、若curEntry的上一个Entry的Node为空，说明该Entry为第一个Entry，当前Node的符节点就设为context的entranceNode入口节点
		// 4、更新context的curNode,同时更新context的curEntry的关联Node为当前新建的Node
		
		DefaultNode node = map.get(context.getName());
		if (node == null) {
			synchronized (this) {
				node = map.get(context.getName());
				if (node == null) {
					node = new DefaultNode(resourceWrapper, null);
					Map<String/**context.name*/, DefaultNode> local = new HashMap<>(map.size()+1);
					local.putAll(map);
					local.put(context.getName(), node);
					map = local;
				}
				((DefaultNode)context.getLastNode()).addChild(node);
			}
		}
		context.setCurNode(node);
		
	}

}
