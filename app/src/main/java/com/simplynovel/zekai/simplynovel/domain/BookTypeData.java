package com.simplynovel.zekai.simplynovel.domain;

import java.util.List;

public class BookTypeData {
	private List<String> types;
	private List<String> typeNames;
	private List<String> childTypes;
	private List<String> childTypeNames;
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public List<String> getTypeNames() {
		return typeNames;
	}
	public void setTypeNames(List<String> typeNames) {
		this.typeNames = typeNames;
	}
	public List<String> getChildTypes() {
		return childTypes;
	}
	public void setChildTypes(List<String> childTypes) {
		this.childTypes = childTypes;
	}
	public List<String> getChildTypeNames() {
		return childTypeNames;
	}
	public void setChildTypeNames(List<String> childTypeNames) {
		this.childTypeNames = childTypeNames;
	}


}
