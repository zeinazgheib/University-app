package com.uapp.tasks;

public class Header_Param {
	String Key, Value;
	
	public Header_Param(String KEY, String VALUE) {
		this.Key = KEY;
		this.Value = VALUE;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

}
