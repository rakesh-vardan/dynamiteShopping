package com.dynamite.shopping.pageobjects.results;

import com.dynamite.shopping.element.Link;

public interface Result  {
			
	public Link image();

	public String title();
	
	public void click();
	
	public String availability();
		
}
