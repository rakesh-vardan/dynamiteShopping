package com.dynamite.shopping.utils.enumerations;

public enum PaymentInfo {
			
	CARD_NUMBER  ("1111111111111111"),
	MONTH        ("1"),
	YEAR         ("2020"),
	CVV          ("1234");		
		
	private String value;
	 
	private PaymentInfo(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
