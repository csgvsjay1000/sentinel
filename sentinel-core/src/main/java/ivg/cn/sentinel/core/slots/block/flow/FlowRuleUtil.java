package ivg.cn.sentinel.core.slots.block.flow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ivg.cn.sentinel.core.slots.block.flow.controller.DefaultController;

public class FlowRuleUtil {

	
//	public static Map<String, List<FlowRule>> buildFlowRuleMap(List<FlowRule> list) {
//		return buildFlowRuleMap(list, true);
//	}
	
	/**  将ruleList变成map
	 * 一个资源可能有多个限流规则
	 *  */
	public static Map<String, List<FlowRule>> buildFlowRuleMap(List<FlowRule> list, boolean sort) {
		Map<String, List<FlowRule>> newRuleMap = new ConcurrentHashMap<>();
		if (list == null || list.isEmpty()) {
			return newRuleMap;
		}
		Map<String, Set<FlowRule>> tmpMap = new ConcurrentHashMap<>();
		
		// 遍历规则
		// 1、判断节点是否有效
		// 2、设置流量塑形策略
		// 3、一个资源可以有多个限流规则
		for (FlowRule flowRule : list) {
			
			TrafficShapingController controller = generateRater(flowRule);
			if (controller != null) {
				flowRule.setRater(controller);
			}
			
			String key = flowRule.getResource();
			Set<FlowRule> flowRules = tmpMap.get(key);
			if (flowRules == null) {
				flowRules = new HashSet<>();
				tmpMap.put(key, flowRules);
			}
			flowRules.add(flowRule);
		}
		
		for(Entry<String, Set<FlowRule>> entry : tmpMap.entrySet()){
			List<FlowRule> rules = new ArrayList<>(entry.getValue());
			newRuleMap.put(entry.getKey(), rules);
		}
		return newRuleMap;
	}
	
	private static TrafficShapingController generateRater(FlowRule flowRule) {
		return new DefaultController(flowRule.getCount(), flowRule.getGrade());
	}
	
}
