package com.dynamite.shopping.page;

public interface PageInterface {
	
	boolean urlContains(String url);
	boolean titleContains(String title);
	boolean urlIs(String url);
	boolean titleIs(String title);
	String getTitle();
	String getUrl();
	void reload();

}
