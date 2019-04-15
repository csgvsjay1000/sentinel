package ivg.cn.sentinel.core.slots.block.flow;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.node.Node;
import ivg.cn.sentinel.core.slots.block.RuleConstant;

/**  流量规则检查器，不允许其他类改变 */
final class FlowRuleChecker {

	static boolean passCheck(FlowRule rule, Context context, DefaultNode node, int count){
		// 1、先检查是否集群限流
		// 2、本地检查
		
		return passLocalCheck(rule, context, node, count);
	}

	/** 本地检查 */
	static boolean passLocalCheck(FlowRule rule, Context context, DefaultNode node, int count){
		
		
		return false;
	}
	
	/**  选择节点,根据节点的统计数据，判断是否限流 */
	static Node selectNode(FlowRule rule, Context context, DefaultNode node){
		// 1、若规则中有{some_origin}，则只对context的origin匹配的限流，返回该originNode
		
		String limitApp = rule.getLimitApp();
		String origin = context.getOrigin();
		int strategy = rule.getStrategy();
		
		if (limitApp.equals(origin)) {
			// 规则中的limitApp匹配上请求中的来源，则返回originNode
			if (strategy == RuleConstant.STRATEGY_DIRECT) {
				// 直接限流
				return context.getOriginNode();
			}
		}
		return null;
		
	}

}
