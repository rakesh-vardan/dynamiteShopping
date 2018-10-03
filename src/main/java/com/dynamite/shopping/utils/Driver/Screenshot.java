package com.dynamite.shopping.utils.Driver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import com.dynamite.shopping.utils.Property.PropertyFile;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	
	private WebDriver driver;	
	private final String folderPath;
	
	private final String folderNotExistError = "screenshot folder does not exist";
	private final String cannotCleanFolderError = "cannot clean screenshot folder";
	private final String cannotCaptureScreenshotError = "cannot capture the screenshot";
	public static Logger log = Logger.getLogger("errorLogger");
	
	
	public Screenshot(WebDriver driver) throws Exception {		
		this.driver = driver;
		folderPath = new PropertyFile().get("screenshotsFolderPath");
		validateFolderExists();				
	}

	private void validateFolderExists() {
		File screenShotFolder = new File(folderPath);
		if (!screenShotFolder.exists()) {
			log.fatal(folderNotExistError);
			throw new RuntimeException(folderNotExistError);
		}
	}
	
	public void cleanFolder()
	{		
		try{					
			File screenShotFolder = new File(folderPath);
			for(File file: screenShotFolder.listFiles()) 
				file.delete();
		}
		catch(Exception ex) {
			log.fatal(cannotCleanFolderError);
			throw new RuntimeException(cannotCleanFolderError, ex);
		}
	}	
	
	public void capture(String methodName) 
	{		
		File scrFile;
		 
		 try {
			 scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			 FileUtils.copyFile(scrFile, new File(getScreenshotName(methodName)));	
			 return;
		 } 
		 catch (IOException e) {
			 e.printStackTrace();
		 }
		 
		 log.fatal(cannotCaptureScreenshotError);
		 throw new RuntimeException(cannotCaptureScreenshotError);
        
    }
	
	private String getScreenshotName(String methodName) {
		 String localDateTime = LocalDateTime.now().toString().replaceAll("[^0-9a-zA-Z]", "");
		 StringBuilder name = new StringBuilder().append(folderPath) 
				       							 .append(methodName)
				       							 .append("_")
				       							 .append(localDateTime)
				       							 .append(".png");
		 return name.toString();
	 }

}
