package ivg.cn.sentinel.core.property;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DynamicSentinelProperty<T> implements SentinelProperty<T>{

	protected Set<PropertyListener<T>> listeners = Collections.synchronizedSet(new HashSet<PropertyListener<T>>());
	
	private T value = null;
	
	/**  属性添加监听器，同时监听器去加载属性值 */
	@Override
	public void addListener(PropertyListener<T> listener) {
		listeners.add(listener);
		listener.configLoad(value);
	}

	@Override
	public void removeListener(PropertyListener<T> listener) {
		listeners.remove(listener);
		
	}

	@Override
	public boolean updateValue(T newValue) {
		if (isEqual(value, newValue)) {
			// 若新旧数据相同，则不更新
			return false;
		}
		value = newValue;  // 是否同一线程?
		
		// 所有监听器更新配置
		for (PropertyListener<T> listener : listeners) {
			listener.configUpdate(newValue);
		}
		
		return true;
	}
	
	private boolean isEqual(T oldValue, T newValue) {
		if (oldValue == null && newValue == null) {
			return true;
		}
		if (oldValue == null) {
			return false;  // why ?
		}
		return oldValue.equals(newValue);
	}
	
	public void close() {
		listeners.clear();
	}

}
