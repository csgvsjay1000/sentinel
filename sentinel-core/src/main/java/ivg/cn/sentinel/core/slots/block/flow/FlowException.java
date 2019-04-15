package ivg.cn.sentinel.core.slots.block.flow;

import ivg.cn.sentinel.core.slots.block.AbstractRule;
import ivg.cn.sentinel.core.slots.block.BlockException;

@SuppressWarnings("serial")
public class FlowException extends BlockException{

	public FlowException(String ruleLimitApp, AbstractRule rule) {
		super(ruleLimitApp, rule);
	}

}
