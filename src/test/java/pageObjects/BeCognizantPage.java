package pageObjects;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtility;
import utilities.ScreenshotUtilities;

public class BeCognizantPage {
	
	public WebDriver driver;
	ScreenshotUtilities sc;
	
	//constructor
	public BeCognizantPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		sc = new ScreenshotUtilities(driver);
	}
	
	@FindBy(xpath = "//div[@class='_8ZYZKvxC8bvw1xgQGSkvvA==']") 
	private WebElement btn_user;
	
	@FindBy(xpath = "//div[@id='mectrl_currentAccount_primary']")
	private WebElement txt_username;
	
	@FindBy(xpath = "//div[@id='mectrl_currentAccount_secondary']")
	private WebElement txt_mailid;
	
	@FindBy(xpath = "//div[contains(text(),'OneCognizant')]")
	private WebElement link_onecognizant;
	
	public void clickUser() {
		btn_user.click();
	}
	
	public String getUsername() {
		return txt_username.getText();
	}
	
	public String getMailID() {
		return txt_mailid.getText();
	}
	
	public void clickOnecognizant() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("document.querySelector(\"article>div>div\").scrollTop=300");
		link_onecognizant.click();
	}
	
	public void writeUserNameAndMailInExcel(String sheetName) throws IOException {
		ExcelUtility.write(sheetName, 0, 0, "Username");
		ExcelUtility.write(sheetName, 1, 0, "Mail");
		ExcelUtility.write(sheetName, 0, 1, txt_username.getText());
		ExcelUtility.write(sheetName, 1, 1, txt_mailid.getText());
		
	}
}	

