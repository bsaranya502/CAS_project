package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtilities {
	
	private Properties property = new Properties();
	
	public PropertyUtilities(){
		FileReader file=null;
		try {
			file = new FileReader("./Properties/Properties.properties");
		} 
		catch (FileNotFoundException e) {
			System.out.println("Property File Not Found");
		}
		property = new Properties();
		try {
			property.load(file);
		} 
		catch (IOException e) {
			System.out.println("Can't be loaded");
		}
	}
	
	public String getURL() {
		return property.getProperty("url");	
	}
	public String getToBeSearched() {
		return property.getProperty("searchTopic");
		
	} 

}
