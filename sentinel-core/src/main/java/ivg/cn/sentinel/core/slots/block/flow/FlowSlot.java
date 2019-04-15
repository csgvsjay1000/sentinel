package ivg.cn.sentinel.core.slots.block.flow;

import java.util.List;
import java.util.Map;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.slotchain.ProcessorSlot;
import ivg.cn.sentinel.core.slotchain.ResourceWrapper;
import ivg.cn.sentinel.core.slots.block.BlockException;

/**  流量槽 */
public class FlowSlot implements ProcessorSlot<DefaultNode>{

	@Override
	public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count,
			boolean prioritized, Object... args) throws Throwable {
		
		checkFlow(context, resourceWrapper, param, count, prioritized, args);
		
	}
	
	private void checkFlow(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count,
			boolean prioritized, Object... args) throws BlockException{
		// 流量检测
		// 1、先判断是否针对该资源有设置规则，若没有就默认通过
		// 2、若有设置则进入校验，校验若不通过抛出流量限制异常，包含限制来源和规则
		
		Map<String, List<FlowRule>> flowRuleMap = FlowRuleManager.getFlowRules();
		
		List<FlowRule> rules = flowRuleMap.get(resourceWrapper.getName());
		if (rules != null) {
			for (FlowRule flowRule : rules) {
				if (!FlowRuleChecker.passCheck(flowRule, context, param, count)) {
					// 如果没通过检测，就抛出异常
					throw new FlowException(flowRule.getLimitApp(), flowRule);
				}
			}
		}
		
	}
	
}
