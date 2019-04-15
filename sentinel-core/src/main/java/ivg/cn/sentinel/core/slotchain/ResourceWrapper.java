package ivg.cn.sentinel.core.slotchain;

import ivg.cn.sentinel.core.EntryType;

public abstract class ResourceWrapper {

	protected String name;
	protected EntryType type = EntryType.OUT;
	
	public abstract String getName();
	
	public abstract EntryType getType();
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResourceWrapper) {
			ResourceWrapper target = (ResourceWrapper)obj;
			return getName().equals(target.getName());
		}
		return false;
	}
	
}
