package com.dynamite.shopping.utils.enumerations;

public enum UserInfo {
	FIRST_NAME  ("aniketh"),
	LAST_NAME   ("reddy"),
	ADDRESS     ("200 broadway"),
	CITY        ("vancouver"),	
	PROVINCE    ("BC"),
	POSTAL_CODE ("V6K1A2"),
	PHONE1      ("877"),
	PHONE2      ("123"),
	PHONE3      ("3245");
		
	private String value;
	 
	private UserInfo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
