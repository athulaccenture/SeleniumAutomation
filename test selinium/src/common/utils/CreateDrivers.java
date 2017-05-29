package common.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.accenture.omnichannelframework.api.Logger;
import com.accenture.omnichannelframework.api.MethodDataStore;
import com.accenture.omnichannelframework.api.OmnichannelFramework;

import adapters.desktop.driver.SeleniumAdapter;

public class CreateDrivers {
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static final Logger LOGGER = OmnichannelFramework.getLogger();
	protected static MethodDataStore methodDataStore;

	@BeforeClass
	public void setUp() {
		driver = (WebDriver) OmnichannelFramework.getInstanceFromAdapter(SeleniumAdapter.ID);
		// driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver, 30);
		methodDataStore = OmnichannelFramework.getMethodDataStore();
	}

	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String fileName = String.format("%s\\%s\\%sFailed.png", testResult.getMethod().getXmlTest().getName(),
					testResult.getMethod().getMethodName(), testResult.getMethod().getMethodName());
			File destFile = new File(OmnichannelFramework.getMediaPath(SeleniumAdapter.ID) + fileName);
			FileUtils.copyFile(scrFile, destFile);
		}
	}
}

