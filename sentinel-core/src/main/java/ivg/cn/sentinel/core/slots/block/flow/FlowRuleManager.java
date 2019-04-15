package ivg.cn.sentinel.core.slots.block.flow;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ivg.cn.sentinel.core.property.DynamicSentinelProperty;
import ivg.cn.sentinel.core.property.PropertyListener;
import ivg.cn.sentinel.core.property.SentinelProperty;

public class FlowRuleManager {
	
	private static final FlowPropertyListener LISTENER = new FlowPropertyListener();
	
	/**  List<FlowRule> 数据量有多少，一个资源一个FlowRule，一个应用会有100个资源吗 */
	private static SentinelProperty<List<FlowRule>> currentProperty = new DynamicSentinelProperty<List<FlowRule>>();

	static final Map<String, List<FlowRule>> flowRules = new ConcurrentHashMap<>();  
	
	public static void register2Property(SentinelProperty<List<FlowRule>> property) {
		synchronized (LISTENER) {
			currentProperty.removeListener(LISTENER);
			property.addListener(LISTENER);
			currentProperty = property; 
		}
	}
	
	static Map<String, List<FlowRule>> getFlowRules(){
		return flowRules;
	}
	
	private static class FlowPropertyListener implements PropertyListener<List<FlowRule>>{

		@Override
		public void configUpdate(List<FlowRule> value) {
			Map<String, List<FlowRule>> rules = FlowRuleUtil.buildFlowRuleMap(value, true);
			if (rules != null) {
				flowRules.clear();
				flowRules.putAll(rules);
			}
			System.out.println("FlowRuleManager.FlowPropertyListener.configUpdate() flowRules:"+flowRules);
		}

		@Override
		public void configLoad(List<FlowRule> value) {
			Map<String, List<FlowRule>> rules = FlowRuleUtil.buildFlowRuleMap(value, true);
			if (rules != null) {
				flowRules.clear();
				flowRules.putAll(rules);
			}
			System.out.println("FlowRuleManager.FlowPropertyListener.configLoad() flowRules:"+flowRules);
		}
		
	}
}
