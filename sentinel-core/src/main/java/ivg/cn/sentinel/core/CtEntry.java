package ivg.cn.sentinel.core;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.Node;
import ivg.cn.sentinel.core.slotchain.ProcessorSlot;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;

public class CtEntry extends Entry{

	Entry parent = null;
	Entry child = null;
	
	ProcessorSlot<Object> chain;
	Context context;
	
	public CtEntry(ResourceWrapper resourceWrapper, ProcessorSlot<Object> chain, Context context) {
		super(resourceWrapper);
		this.chain = chain;
		this.context = context;
		
		setupEntryFor(context);
	}
	
	/**
	 * 同一个context中形成树结构  Entry1 <--- Entry2 <--- Entry3
	 * */
	private void setupEntryFor(Context context) {
		this.parent = context.getCurEntry();  // context.getCurEntry() 可能为空，为空表示该Entry为根节点
		if (this.parent != null) {
			// 如果parent不为空，说明当前新创建的entry为上一个entry的子节点
			((CtEntry)this.parent).child = this;
		}
		context.setCurEntry(this);
	}

	@Override
	public Node getLastNode() {
		if (parent != null) {
			return parent.getCurNode();
		}
		return null;
	}
}
