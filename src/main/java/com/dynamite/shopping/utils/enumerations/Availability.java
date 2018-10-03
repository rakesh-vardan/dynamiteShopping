package com.dynamite.shopping.utils.enumerations;

public enum Availability {
	
	ONLINE ("Available online only"), 
	SOLDOUT("Sold out online");
	
	private String status;
	 
	private Availability(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}

}
