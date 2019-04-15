package ivg.cn.sentinel.core;

public enum EntryType {

	IN("IN"),
	OUT("OUT");
	
	private final String name;
	
	EntryType(String name){
		this.name = name;
	}
	
	public boolean equalName(String otherName) {
		return name.equals(otherName);
	}
	
}
