package ivg.cn.sentinel.core.slots.block.flow;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.slots.block.AbstractRule;
import ivg.cn.sentinel.core.slots.block.RuleConstant;

public class FlowRule extends AbstractRule{

	int grade = RuleConstant.FLOW_GRADE_QPS;
	
	double count;
	
	/**  直接限流 */
	private int strategy = RuleConstant.STRATEGY_DIRECT;
	
	// 流量整形
	TrafficShapingController controller;
	
	@Override
	public boolean passCheck(Context context, DefaultNode node, int count, Object... args) {
		return true;
	}
	
	FlowRule setRater(TrafficShapingController rater) {
        this.controller = rater;
        return this;
    }

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
	
	public int getStrategy() {
		return strategy;
	}

}
