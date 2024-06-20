package pageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtility;
import utilities.ScreenshotUtilities;

public class LiveSupportGSDPage {
	
	public WebDriver driver;

	ScreenshotUtilities sc;
	
	public LiveSupportGSDPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		sc = new ScreenshotUtilities(driver);
	}
	
	@FindBy(xpath = "//div[@id='ui-view']//div/p[@class='Welcome-automated-text']")
	private WebElement txt_welcome;
	
	@FindBy(xpath = "(//a[@id='menu2']/span[@class='optionCountrySelected'])[1]")
	private WebElement txt_defaultCountry;
	
	@FindBy(xpath = "(//a[@id='menu1']/span[@class='optionLangSelected'])[1]")
	private WebElement txt_defaultLanguage;
	
	@FindBy(xpath = "//form/div[1]/ul/li/a")
	private List<WebElement> txt_languageOptions;
	
	@FindBy(xpath = "//form/div[2]/ul/li/a")
	private List<WebElement> link_countryOptions;
	
	@FindBy(xpath = "//form/div[1]/a")
	private WebElement btn_langauge;
	
	@FindBy(xpath = "//form/div[2]/a")
	private WebElement btn_country;
	
	@FindBy(xpath = "//div[@id='ui-view']/div/div[@class='row']/div/div/div/div")
	private List<WebElement> txt_categories;
	
	
	private By txt_topic_rel_loc = By.xpath("./div[1]");
	
	private By txt_titles_rel_loc = By.xpath("./div[2]/div/div");
	
	
	
	public String getWelcomeMessage() {
		return txt_welcome.getText();
	}	
	public String getCurrentCountry() {
		return txt_defaultCountry.getText();
	}
	public String getCurrentLanguage() {
		return txt_defaultLanguage.getText();
	}
	public List<String> getLanguagesInDropdown(){
		
		List<String> languageList = new ArrayList<String>();
		
		for(WebElement languageElement : txt_languageOptions) {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			String language = (String) js.executeScript("return arguments[0].innerHTML",languageElement);
			languageList.add(language);
		}
		
		return languageList;
	}
	public void clickLanguageDropdown(){
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", btn_langauge);
		}
		public void clickCountryDropdown(){
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", btn_country);
		}
		
		public String clickAndGetRandomCountry() {
			int randNum = -1;
			while(!(randNum>=0 && randNum<link_countryOptions.size())){
				randNum = (int)Math.round(Math.random()*link_countryOptions.size());
			}
			WebElement randCountry = link_countryOptions.get(randNum);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", randCountry);
		
		return getCurrentCountry();
	}
	
	public void printContentOfPage(){
		for(WebElement txt_category : txt_categories) {
			//Titles
			WebElement category = txt_category.findElement(txt_topic_rel_loc);
			System.out.println("CATEGORY: " + category.getText());
			System.out.println();
			
			List<WebElement> txt_titles = txt_category.findElements(txt_titles_rel_loc);
			for(WebElement txt_title : txt_titles) {
				String toolTip = txt_title.getAttribute("data-bs-original-title");
				if(toolTip.equals(""))
					toolTip = "No Tool-Tip";
				
				System.out.println("TITLE: " + txt_title.getText());
				System.out.println("TOOL-TIP: " + toolTip);	
			}
			System.out.println();
			
		}
		System.out.println();
	}
	
	public void writeWelcomeMessageInExcel(String sheetName) throws IOException {
		ExcelUtility.write(sheetName, 0, 0, "Welcome Message");
		ExcelUtility.write(sheetName, 0, 1, txt_welcome.getText());
	}
	public void writeCurrCountryAndLangInExcel(String sheetName) throws IOException {
		ExcelUtility.write(sheetName, 0, 0, "Default Country");
		ExcelUtility.write(sheetName, 0, 1, txt_defaultCountry.getText());
		ExcelUtility.write(sheetName, 1, 0, "Default Language");
		ExcelUtility.write(sheetName, 1, 1, txt_defaultLanguage.getText());
	}
	public void writeLanguagesInExcel(String sheetName, List<String> languages) throws IOException {
		
		ExcelUtility.write(sheetName, 0, 0, "Languages");
		for(int i=0; i<languages.size(); i++) {	
			ExcelUtility.write(sheetName, i, 1, languages.get(i));
		}
	}
	public void writeContentsOfPage(String sheetName) throws IOException {
		ExcelUtility.write(sheetName, 0, 0, "Current Country");
		ExcelUtility.write(sheetName, 0, 1, getCurrentCountry());
		
		int catgIndex=0;
		for(WebElement txt_category : txt_categories) {
			//Titles
			WebElement category = txt_category.findElement(txt_topic_rel_loc);
			ExcelUtility.write(sheetName, 1, catgIndex, category.getText());
			
			
			int titleIndex=2;
			List<WebElement> txt_titles = txt_category.findElements(txt_titles_rel_loc);
			for(WebElement txt_title : txt_titles) {
				String toolTip = txt_title.getAttribute("data-bs-original-title");
				if(toolTip.equals(""))
					toolTip = "No Tool-Tip";
				
				ExcelUtility.write(sheetName, titleIndex, catgIndex, txt_title.getText());
				ExcelUtility.write(sheetName, titleIndex, catgIndex+1, toolTip);
				titleIndex++;
			}
			catgIndex = catgIndex + 2;	
		}
		System.out.println();
		
	}

}
