package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import testBase.TestBase;

public class ScreenshotUtilities extends TestBase{
	
	static WebDriver driver1;
	public ScreenshotUtilities(WebDriver driver1){
		ScreenshotUtilities.driver1 = driver1;
	}
	public static void takeScreenshot(WebDriver driver, String fileName) {
		
		
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File destinationFile = new File("C:\\Users\\2320027\\eclipse-workspace\\CAS_Project_GSD\\ScreenShots" + fileName + timestamp + ".png");
		try {
			FileHandler.copy(screenshotFile, destinationFile);
		} 
		catch (IOException e) {
			System.out.println("Can't Copy the Screenshot to a File");
		}
		
	}

	public static String takeScreenshotReport(String fileName) {
		
		
		File screenshotFile = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File destinationFile = new File("C:\\Users\\2320027\\eclipse-workspace\\CAS_Project_GSD\\ScreenShots" + fileName + timestamp + ".png");
		try {
			FileHandler.copy(screenshotFile, destinationFile);
		} 
		catch (IOException e) {
			System.out.println("Can't Copy the Screenshot to a File");
		}
		return destinationFile+"";
		
	}
	
	public static void takeScreenshot(WebElement element, String fileName) {
		
		File screenshotFile = element.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File("./Utilities/Screenshots/" + fileName + ".png");
		try {
			FileHandler.copy(screenshotFile, destinationFile);
		} 
		catch (IOException e) {
			System.out.println("Can't Copy the Screenshot to a File");
		}
		
	}

}
