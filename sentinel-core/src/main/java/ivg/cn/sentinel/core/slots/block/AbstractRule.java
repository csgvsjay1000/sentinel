package ivg.cn.sentinel.core.slots.block;

public abstract class AbstractRule implements Rule{

	String resource;
	
	private String limitApp;
	
	public String getResource() {
		return resource;
	}
	
	public String getLimitApp() {
		return limitApp;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setLimitApp(String limitApp) {
		this.limitApp = limitApp;
	}
	
}
