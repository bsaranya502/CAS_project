package testSuites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.BeCognizantPage;
import pageObjects.LiveSupportGSDPage;
import pageObjects.OneCognizantHomePage;
import testBase.TestBase;
import utilities.ScreenshotUtilities;

public class TestSuite_1 extends TestBase {
	
	
	//TS_01 - User Information in Be. Cognizant
	//TC_US_01
	@Parameters({"browser"})
	@Test(priority = 1)
	public void validateUser(String browser) throws IOException {
		
		beCognizantPage = new BeCognizantPage(driver);
		beCognizantPage.clickUser();
		String username = beCognizantPage.getUsername();
		String mail = beCognizantPage.getMailID();
		
		//Capturing in Different Ways
		//1.Printing in Console
		System.out.println("Username: " + username);
		System.out.println("Mail: " + mail);
		//2.Taking Screenshot
		ScreenshotUtilities.takeScreenshot(driver, ("TS_01_UserInformation_" + browser));
		//3.Writing in Excel
		beCognizantPage.writeUserNameAndMailInExcel(("TS_01_" + browser));
		
		Assert.assertEquals(username.contains("Contractor"), true, "Invalid Username");
		Assert.assertEquals(mail.contains("@cognizant.com"), true, "Invalid Mail");
	}
	
	
	//TS_02 - Welcome Message
	//TC_WL_01
	@Parameters({"browser"})
	@Test(priority = 2, dependsOnMethods = "validateUser")
	public void validateWelcomeMessage(String browser) throws IOException {
		beCognizantPage = new BeCognizantPage(driver);
		beCognizantPage.clickOnecognizant();

		List<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(1));	

		oneCogHomePage = new OneCognizantHomePage(driver);
		oneCogHomePage.clickSearch();
		oneCogHomePage.searchInOneC(propertyUtility.getToBeSearched());
		oneCogHomePage.clickLiveSupportGSD();

		driver.switchTo().frame("appFrame");
		
		gsdPage = new LiveSupportGSDPage(driver);
		
		String welcomeMessage = gsdPage.getWelcomeMessage();

		System.out.println(welcomeMessage);
		ScreenshotUtilities.takeScreenshot(driver, ("TS_02_WelcomeMessage_" + browser));
		gsdPage.writeWelcomeMessageInExcel(("TS_02_" + browser));
		
		Assert.assertEquals(welcomeMessage.contains("Welcome"), true, "Improper Welcome Message");
		
	}
	
	
	//TS_03 - Default Country and Language
	//TC_CL_01
	@Parameters({"browser"})
	@Test(priority = 3 , dependsOnMethods = "validateWelcomeMessage")
	public void validateDefaultLanguageAndCountry(String browser) throws IOException {		
		
		gsdPage = new LiveSupportGSDPage(driver);
		
		String currentCountry = gsdPage.getCurrentCountry();
		String currentLanguage = gsdPage.getCurrentLanguage();
		
		System.out.println("Default Country: " + currentCountry);
		System.out.println("Default Language: " + currentLanguage);
		
		ScreenshotUtilities.takeScreenshot(driver, ("TS_03_DefaultCountryAndLanguage_" + browser));
		gsdPage.writeCurrCountryAndLangInExcel(("TS_03_" + browser));
		
		Assert.assertEquals(currentCountry, "India", "Wrong Default Country");
		Assert.assertEquals(currentLanguage, "English", "Wrong Default Language");
		
		
	}
	
	
	//TS_04 - Details of Language Drop-down
	//TC_LD_01
	@Parameters({"browser"})
	@Test(priority = 4, dependsOnMethods = "validateWelcomeMessage")
	public void getLanguages(String browser) throws IOException,InterruptedException {
		
		gsdPage = new LiveSupportGSDPage(driver);
		
		gsdPage.clickLanguageDropdown();
		List<String> languages = gsdPage.getLanguagesInDropdown();
		for(String language : languages) {
			System.out.println(language);	
		}
		ScreenshotUtilities.takeScreenshot(driver, ("TS_04_LanguageDropdown_" + browser));
		gsdPage.writeLanguagesInExcel(("TS_04_" + browser), languages);
	}
	
	//TS_05 - Details of GSD page
	//TC_DG_01
	@Parameters({"browser"})
	@Test(priority = 5, dependsOnMethods = "validateWelcomeMessage")
	public void checkDetails(String browser) throws IOException, InterruptedException {
	
		gsdPage = new LiveSupportGSDPage(driver);
		
		int count=1;
		do {
			gsdPage.clickCountryDropdown();
			
			String currentCountry = gsdPage.clickAndGetRandomCountry();
			System.out.println("Current Country: " + currentCountry);
			
			gsdPage.printContentOfPage();
			ScreenshotUtilities.takeScreenshot(driver,("TS_05_"+ currentCountry + "_"+ browser));
			gsdPage.writeContentsOfPage(("TS_05_C" + count + "_" + browser));
			count++;
		}
		while(count<=3);
		
		

	}
	
	
}
