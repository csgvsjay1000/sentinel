package ivg.cn.sentinel.core;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.context.ContextUtil;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.slotchain.ProcessorSlot;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;
import ivg.cn.sentinel.core.slotchain.StringResourceWrapper;
import ivg.cn.sentinel.core.slots.block.flow.FlowSlot;
import ivg.cn.sentinel.core.slots.clusterbuilder.ClusterBuilderSlot;
import ivg.cn.sentinel.core.slots.nodeselector.NodeSelectorSlot;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        
    	// 1、先获取context
    	// 2、获取slot chain
    	// 3、获取一个entry
    	// 4、成功获取到entry表示资源可以进入，处理完业务逻辑后
    	// 5、退出资源，若是调用树的最后根节点则清空context
    	
    	Context context = ContextUtil.trueEnter("default_context", "origin");
    	ProcessorSlot<Object> slot = new NodeSelectorSlot();
    	ProcessorSlot<DefaultNode> clusterSlot = new ClusterBuilderSlot();
    	ProcessorSlot<DefaultNode> flowSlot = new FlowSlot();
    	
    	
    	ResourceWrapper resourceWrapper = new StringResourceWrapper("createOrder", EntryType.OUT);
    	Entry entry = new CtEntry(resourceWrapper, slot, context);
    	try {
			slot.entry(context, resourceWrapper, null, 1, false, new Object[]{});
			clusterSlot.entry(context, resourceWrapper, (DefaultNode)context.getCurNode(), 1, false, new Object[]{});
			flowSlot.entry(context, resourceWrapper, (DefaultNode)context.getCurNode(), 1, false, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}catch (Throwable e) {
			e.printStackTrace();
		} finally {
			
		}
    	
    	
    	
    }
}
