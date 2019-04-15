package ivg.cn.sentinel.core.property;

public interface PropertyListener<T> {

	void configUpdate(T value);
	
	void configLoad(T value);
	
}
