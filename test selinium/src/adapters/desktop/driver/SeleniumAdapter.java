package adapters.desktop.driver;

import com.accenture.omnichannelframework.api.Adapter;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SeleniumAdapter extends Adapter {
	public static final String ID = "SeleniumAdapter";

    WebDriver driver;

    public void initialize() {
           String currentDriver = getPropertyValue("driver");
           String Path = getPropertyValue("Path");
           if (currentDriver.equalsIgnoreCase("firefox")) {
                  driver = new FirefoxDriver();
           } else if (currentDriver.equalsIgnoreCase("chrome")) {
                  System.setProperty("webdriver.chrome.driver", Path);
                  driver = new ChromeDriver();
                  System.setProperty("webdriver.chrome.driver", "C:\\Users\\athul.ks\\Downloads\\chromedriver\\chromedriver.exe");
                  ChromeOptions options = new ChromeOptions();
                  options.addArguments("--disable-extensions");
                  options.addArguments("--disable-infobars");
                  Map<String, Object> prefs = new HashMap<String, Object>();
                  prefs.put("credentials_enable_service", false);

      // if you give false it will not save password 

                  prefs.put("profile.password_manager_enabled", false);
                  options.setExperimentalOption("prefs", prefs);
                  driver = new ChromeDriver(options);

           } else if (currentDriver.equalsIgnoreCase("IE")) {
                  System.setProperty("webdriver.ie.driver", Path);
                  DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                  caps.setCapability("ignoreProtectedModeSettings", true);
                  caps.setCapability("ie.ensureCleanSession", true);
                  caps.setCapability("enablePersistentHover", false);
                  driver = new InternetExplorerDriver(caps);
                  driver.manage().deleteAllCookies();
           } else if (currentDriver.equalsIgnoreCase("Safari")) {
                  System.setProperty("webdriver.safari.noinstall", "true");
                  SafariOptions options = new SafariOptions();
                  options.setUseCleanSession(true);
                  driver = new SafariDriver(options);
           } else if (currentDriver.equalsIgnoreCase("Opera")) {
                  System.setProperty("webdriver.opera.driver", Path);

                  driver = new OperaDriver();
           }
    }

    public Object getInstance() {
           return driver;
    }

    public String getName() {
           return "Selenium WebDriver";
    }

    public void configureProperties() {
           addProperty("driver", "Driver", "chrome");
           addProperty("Path", "PathForBrowserDriver", "C:\\Users\\athul.ks\\Downloads\\chromedriver\\chromedriver.exe");
    }

    public String getId() {
           return ID;
    }
    
    public void cleanUp() {
           //driver.manage().deleteAllCookies();
           System.out.println("Cleared all Cookies");
           System.out.println("Clean up Adapter");
           driver.quit();
    }

    public void afterSuite() {

           driver.quit();

    }

    @Override
    public boolean shouldInitializeBeforeSuite() {
           // TODO Auto-generated method stub
           return true;
    }
}
