package ivg.cn.sentinel.core.property;

public interface SentinelProperty<T> {

	void addListener(PropertyListener<T> listener);
	
	void removeListener(PropertyListener<T> listener);

	boolean updateValue(T newValue);
	
}
