package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ScreenshotUtilities;

public class OneCognizantHomePage {
	
	public WebDriver driver;
    
	ScreenshotUtilities sc;
	//constructor
	public OneCognizantHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		sc = new ScreenshotUtilities(driver);
	}
	//locators
	@FindBy(xpath="//div[@class='nav-wrapper']/child::ul[@class='searchBasedTopBar']/li")
	WebElement btn_search;
	
	@FindBy(xpath = "//*[@id='oneCSearchTop']")
	private WebElement txt_searchBox;
	
	@FindBy(xpath = "//div[@class='appsSearchResult']/div[2]")
	private WebElement link_LiveSupportGSD;
	
	//Actions
	public void clickSearch() {
		 JavascriptExecutor js=(JavascriptExecutor)driver;
		 js.executeScript("arguments[0].click();",btn_search);
	}
	
	public void searchInOneC(String search) {
		txt_searchBox.sendKeys(search);
	}
	
	public void clickLiveSupportGSD() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", link_LiveSupportGSD);
	}

}
