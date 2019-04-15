package ivg.cn.sentinel.core;

import ivg.cn.sentinel.core.node.Node;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;

public abstract class Entry {

	long createTime;
	
	/**  Entry关联node */
	Node curNode;
	
	/**  
	 * Entry来源node, 在ClusterBuildSlot中设置，若origin不为default的话
	 * 一个上下文中Entry树的来源节点都一样
	 *  */
	Node originNode;
	
	ResourceWrapper resourceWrapper;
	
	public Entry(ResourceWrapper resourceWrapper) {
		this.resourceWrapper = resourceWrapper;
	}
	
	public void setCurNode(Node curNode) {
		this.curNode = curNode;
	}
	
	public Node getCurNode() {
		return curNode;
	}
	
	public void setOriginNode(Node originNode) {
		this.originNode = originNode;
	}
	
	public Node getOriginNode() {
		return originNode;
	}
	
	/**  获取Entry树的最后一个节点，一般为parent entry node */
	public abstract Node getLastNode();
}
