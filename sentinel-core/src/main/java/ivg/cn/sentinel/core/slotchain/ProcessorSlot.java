package ivg.cn.sentinel.core.slotchain;

import ivg.cn.sentinel.core.context.Context;

public interface ProcessorSlot<T> {

	void entry(Context context, ResourceWrapper resourceWrapper, T param, int count,boolean prioritized,
			Object... args) throws Throwable;
	
}
