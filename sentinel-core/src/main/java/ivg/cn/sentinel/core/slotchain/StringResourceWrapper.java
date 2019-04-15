package ivg.cn.sentinel.core.slotchain;

import ivg.cn.sentinel.core.EntryType;

public class StringResourceWrapper extends ResourceWrapper{

	public StringResourceWrapper(String name, EntryType type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public EntryType getType() {
		return type;
	}

}
