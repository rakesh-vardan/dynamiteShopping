package com.dynamite.shopping.utils.Property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {
	
	private Properties properties;
	
	private final String propertyFileName = "./src/main/resources/project.properties";
	
	public PropertyFile() {		
		validateFileExists();
		loadPropertyContent();		
	}

	private void validateFileExists() {
		File propertyFile = new File(propertyFileName);
		if (!propertyFile.exists())
			throw new RuntimeException("property file does not exist");
	}

	private void loadPropertyContent() {
		
		properties = new Properties();
		File file = new File(propertyFileName);
		InputStream input;
		
		try {
			input = new FileInputStream(file);
			properties.load(input);
			return;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		throw new RuntimeException("could not load the property file content");
	}
	
	public String get(String name)
	{		
		try {
			return properties.getProperty(name);
		}
		catch(Exception exception) {
			throw new RuntimeException ("cannot find the " + name);
		}		 
	}	
	
}
