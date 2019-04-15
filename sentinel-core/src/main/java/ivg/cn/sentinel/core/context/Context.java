package ivg.cn.sentinel.core.context;

import ivg.cn.sentinel.core.Entry;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.node.Node;

public class Context {

	String name;
	
	DefaultNode entranceNode;
	
	Entry curEntry;
	
	String origin;
	
	public Context(DefaultNode entranceNode, String name) {
		this.entranceNode = entranceNode;
		this.name = name;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public Entry getCurEntry() {
		return curEntry;
	}
	
	/** 该context下新建一个Entry时设置 */
	public void setCurEntry(Entry curEntry) {
		this.curEntry = curEntry;
	}
	
	/**  设置curEntry的curNode */
	public Context setCurNode(Node node) {
		this.curEntry.setCurNode(node);
		return this;
	}
	
	public Node getCurNode() {
		return this.curEntry.getCurNode();
	}
	
	public String getName() {
		return name;
	}
	
	/**  
	 * 获取上下文中最后一个节点，即curEntry的lastNode 
	 * 在NodeSelectorSlot中使用，创建节点树时使用
	 * */
	public Node getLastNode() {
		if (curEntry != null && curEntry.getLastNode() != null) {
			return curEntry.getLastNode();
		}
		return entranceNode;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public Node getOriginNode() {
		// curEntry 在此时调用不可能为null
		// 在新建一个Entry时curEntry就赋值了
		return curEntry == null ? null : curEntry.getOriginNode();
	}
}
