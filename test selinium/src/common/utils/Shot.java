package common.utils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.accenture.omnichannelframework.api.OmnichannelFramework;

import adapters.desktop.driver.SeleniumAdapter;

import java.io.File;
import java.io.IOException;  

public class Shot {

	public static void seleniumSaveScreenshot(WebDriver driver, String fileName) {
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File(OmnichannelFramework.getMediaPath(SeleniumAdapter.ID) + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


