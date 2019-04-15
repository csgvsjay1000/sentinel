package ivg.cn.sentinel.datasource.extension;

import ivg.cn.sentinel.core.property.SentinelProperty;

public interface ReadableDataSource<S,T> {

	/** 加载配置
	 * 1、readSource()
	 * 2、convert(source)
	 * */
	T loadConfig() throws Exception;
	
	/**  第一次初始化时会从这里读取配置数据 */
	S readSource() throws Exception;
	
	SentinelProperty<T> getProperty();
	
	/**  关闭数据源 */
	void close() throws Exception;
	
}
