package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{

	//For UI of Report
	public ExtentSparkReporter sparkReporter;
	//For CommonProperities
	public ExtentReports report;
	//For Creating Test-Case-Entries in Report
	public ExtentTest test;

	String screenShotPath;

	public void onStart(ITestContext context) {
		
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String browser = context.getCurrentXmlTest().getParameter("browser");
		String fileName = "ExtentReport_" + browser + "_" + timestamp + ".html";
		String fileLocation = System.getProperty("user.dir") + "/reports/" + fileName ;
		
		sparkReporter = new ExtentSparkReporter(fileLocation);
		
		sparkReporter.config().setDocumentTitle("One Cognizant");
		sparkReporter.config().setReportName("Live Support - GSD");
		sparkReporter.config().setTheme(Theme.DARK);
		
		//Setting Properties
		report = new ExtentReports();
		report.attachReporter(sparkReporter);
		report.setSystemInfo("Application", "One Cognizant");
		report.setSystemInfo("Module", "Live Support - GSD");
		report.setSystemInfo("Operating System", System.getProperty("os.name"));
		report.setSystemInfo("User Name", System.getProperty("user.name"));
		report.setSystemInfo("Browser", browser);

	}
	
	public void onTestStart(ITestResult result) {
		System.out.println();
		System.out.println("->TEST-CASE STARTED");
	}
	public void onTestSuccess(ITestResult result) {
		System.out.println("->TEST-CASE SUCCESS!!");
		//Creates the Test-Case which is success
		test = report.createTest(result.getName());
		//Updates the Status of Test-Case
		test.log(Status.PASS, "Test-Case \"" + result.getName() + "\" is Passed");
		try {
			screenShotPath = ScreenshotUtilities.takeScreenshotReport(result.getName());
			 test.pass("Screenshot for Test Success", MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onTestFailure(ITestResult result) {
		System.out.println("->TEST-CASE FAILURE");
		//Creates the Test-Case which fails
		test = report.createTest(result.getName());
		//Updates the Status of Test-Case
		test.log(Status.FAIL, "Test-Case \"" + result.getName() + "\" is Failed");
		test.log(Status.FAIL, "Because it has " + result.getThrowable());
		try {
			screenShotPath = ScreenshotUtilities.takeScreenshotReport(result.getName());
			 test.pass("Screenshot for Test Success", MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onTestSkipped(ITestResult result){
		System.out.println("->TEST-CASE SKIPPED");
		//Creates the Test-Case which skipped
		test = report.createTest(result.getName());
		test.log(Status.SKIP, "Test-Case \"" + result.getName() + "\" is Skipped");//Updates the Status of Test-Case
	}	
	public void onFinish(ITestContext context) {
		System.out.println();
		System.out.println("->TEST-SUITE FINISHED");
		//Writes the test-information in output-view
		report.flush();
	}

}
