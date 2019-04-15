package ivg.cn.sentinel.datasource.extension;

import ivg.cn.sentinel.core.property.DynamicSentinelProperty;
import ivg.cn.sentinel.core.property.SentinelProperty;

public abstract class AbstractDataSource<S, T> implements ReadableDataSource<S, T>{

	protected final Converter<S, T> parser;
	private final SentinelProperty<T> property;
	
	public AbstractDataSource(Converter<S, T> parser) {
		this.parser = parser;
		this.property = new DynamicSentinelProperty<T>();
	}
	
	@Override
	public T loadConfig() throws Exception {
		return loadConfig(readSource());
	}
	
	public T loadConfig(S source) throws Exception {
		T t = parser.convert(source);
		return t;
	}
	
	@Override
	public SentinelProperty<T> getProperty() {
		return property;
	}
	
}
