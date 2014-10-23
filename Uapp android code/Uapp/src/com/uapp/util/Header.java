package com.uapp.util;

public class Header {
	private String mName;
	private String mValue;

	public Header(String name, String value) {
		mName = name;
		mValue = value;
		//mValue = URLEncoder.encode(value);
	}

	public String getName() {
		return mName;
	}

	public String getValue() {
		return mValue;
	}

}
