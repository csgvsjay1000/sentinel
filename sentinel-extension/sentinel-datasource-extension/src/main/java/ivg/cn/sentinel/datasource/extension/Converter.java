package ivg.cn.sentinel.datasource.extension;

/**  转换器 */
public interface Converter<S, T> {

	T convert(S source);
	
}
