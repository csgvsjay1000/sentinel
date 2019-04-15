package ivg.cn.sentinel.core.slots.block;

@SuppressWarnings("serial")
public class BlockException extends Exception{

	protected AbstractRule rule;
	
	private String ruleLimitApp;

	public BlockException(String ruleLimitApp, AbstractRule rule) {
		super();
		this.rule = rule;
		this.ruleLimitApp = ruleLimitApp;
	}

	public AbstractRule getRule() {
		return rule;
	}

	public void setRule(AbstractRule rule) {
		this.rule = rule;
	}

	public String getRuleLimitApp() {
		return ruleLimitApp;
	}

	public void setRuleLimitApp(String ruleLimitApp) {
		this.ruleLimitApp = ruleLimitApp;
	}
	
	
}
