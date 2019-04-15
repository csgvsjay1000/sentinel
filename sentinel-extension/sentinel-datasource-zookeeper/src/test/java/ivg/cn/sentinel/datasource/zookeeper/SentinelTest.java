package ivg.cn.sentinel.datasource.zookeeper;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import ivg.cn.sentinel.core.slots.block.flow.FlowRule;
import ivg.cn.sentinel.core.slots.block.flow.FlowRuleManager;
import ivg.cn.sentinel.datasource.extension.ReadableDataSource;

public class SentinelTest {

	@Test
	public void testSentinel() {
		
		
		
	}
	
	public static void main(String[] args) {
		ReadableDataSource<String, List<FlowRule>> flowRules = new ZookeeperDataSource<>(
				"192.168.5.131:10100", "Sentinel-Demo", "SYSTEM-CODE-DEMO-FLOW",
				source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>(){}));
		
		FlowRuleManager.register2Property(flowRules.getProperty());
		
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
