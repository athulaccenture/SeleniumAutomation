package Web;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.accenture.omnichannelframework.api.OmnichannelFramework;

import common.utils.CreateDrivers;
import common.utils.Shot;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;

public class loginveeva extends CreateDrivers {

	@Test(description = "Launch the Veeva URL and verify its Successfully Launched")
	@Parameters({ "VeevaURL" })
	public void launch(@Optional("https://login.veevavault.com/") String VeevaURL) {
		driver.get(VeevaURL);
		driver.manage().window().maximize();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(methodDataStore.getValue("UsernameFieldXpath"))));
		LOGGER.info("Veeva Page is Launched and Login Screen could be seen");
		Shot.seleniumSaveScreenshot(driver, "Launch.png");
		System.out.println("Veeva Page is Launched and Login Screen could be seen");

	}

	@Test(description = "Login with Valid Credentials")
	@Parameters({ "Username", "Password" })
	public void login(@Optional("athul.ks@sb-sanofi.com") String Username, @Optional("Nano6543#") String Password)
			throws AWTException {

		driver.findElement(By.xpath(methodDataStore.getValue("UsernameFieldXpath"))).sendKeys(Username);
		driver.findElement(By.xpath(methodDataStore.getValue("passwordFieldXpath"))).sendKeys(Password);
		try {
			driver.findElement(By.xpath(methodDataStore.getValue("LoginButton"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userMenu']")));
			String User = driver.findElement(By.xpath("//*[@id='userMenu']")).getText();
			System.out.println("The user " + User + " is Logged in");
			LOGGER.info("The user " + User + " is Logged in");
			Shot.seleniumSaveScreenshot(driver, "VerifyLogin.png");
		} catch (Exception e) {
			System.err.println("Wrong Credentials");
			LOGGER.error("Wrong Credentials");
			Assert.fail("Wrong Credentials");
			e.printStackTrace();
		}
	}

	// SUBMISSIONS

	@Test(description = "Create an independent Submission")
	@Parameters({ "seqName", "legacySysId" })

	public void submissions(@Optional("12345") String seqName, @Optional("a123") String legacySysId) {

		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Submissions")).click();
		driver.findElement(By.xpath(methodDataStore.getValue("CreateNewSubmission"))).click();
		driver.findElement(By.xpath("//*[@id='name__v']")).sendKeys(seqName);
		driver.findElement(By.xpath("//*[@id='doss_id__c']")).sendKeys(legacySysId);
		driver.findElement(By.xpath("//*[@id='application__v']")).click();
		driver.findElement(By.linkText("12345")).click();
		driver.findElement(By.xpath("//*[@id='submission_type__rim']")).click();
		driver.findElement(By.linkText("Annual Report")).click();
		driver.findElement(By.xpath("//*[@class='saveAction vv_button vv_button_primary']")).click();
		Shot.seleniumSaveScreenshot(driver, "3.png");
	}

	// REPORTS

	@Test(description = "Selecting and running a Report")
	public void reports() throws Exception {

		driver.findElement(By.xpath("//*[@name= 'reports__v']")).click();
		driver.findElement(By.xpath("//*[@class='reportName vv_doc_title_name' and contains (text(), 'Role/Task')]")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("(//*[@class='pillContainer vv_pill_container'])[1]")).click();
		driver.findElement(By.linkText("Submissions")).click();
		driver.findElement(By.xpath("(//*[@class='pillContainer vv_pill_container'])[1]")).click();
		driver.findElement(By.linkText("Regulatory")).click();
		driver.findElement(By.xpath("(//*[@class='pillContainer vv_pill_container'])[2]")).click();
		driver.findElement(By.linkText("Fluzone")).click();
		driver.findElement(By.xpath("//a[@class='ok vv_button vv_primary vv_ellipsis']")).click();
		Thread.sleep(3000);
        driver.findElement(By.xpath("//a[@class='editRuntimeFilters fa fa-pencil vv_pencil_icon vv_edit_runtime']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("(//*[@class='multiItemSelectAutoComplete removeItem'])[2]")).click();
		driver.findElement(By.xpath("(//*[@class='pillContainer vv_pill_container'])[2]")).click();
		driver.findElement(By.linkText("DLS Test Product")).click();
		driver.findElement(By.xpath("//a[@class='ok vv_button vv_primary vv_ellipsis']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id=\"body\"]/div/div/div/div[3]/h3/span")).click();
		driver.findElement(By.xpath("//*[@class='fa fa-cog']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Export to Excel")).click();
		Thread.sleep(15000);
		Desktop dt = Desktop.getDesktop();
		dt.open(new File("C:\\Users\\athul.ks\\Downloads\\Role-Task Information by Product & Functional Area.xlsx"));
		Thread.sleep(5000);		
	}

	// APPLICATIONS AND SUBMISSIONS

	@Test(description = "Create an Application and a related Submission")
	@Parameters({ "appNum", "appType", "region", "seqNum", "legacySysId", "subType" })

	public void createApplication_submission(@Optional("13042021") String appNum,
			@Optional("Corporate Package") String appType, @Optional("JPAC") String region,
			@Optional("1111") String seqNum, @Optional("abc1234") String legacySysId,
			@Optional("Annual Report") String subType) throws AWTException, Exception {
		OmnichannelFramework.getTestSessionParameter().setValue("Region", region);
		OmnichannelFramework.getTestSessionParameter().setValue("AppType", appType);
		OmnichannelFramework.getTestSessionParameter().setValue("AppNum", appNum);
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Applications")).click();
		driver.findElement(By.xpath(methodDataStore.getValue("CreateApplicationButton"))).click();
		driver.findElement(By.id("name__v")).sendKeys(appNum);
		driver.findElement(By.id("region__v")).click();
		driver.findElement(By.linkText(region)).click();
		driver.findElement(By.id("application_type__rim")).click();
		driver.findElement(By.linkText(appType)).click();
		driver.findElement(By.xpath(methodDataStore.getValue("ClickSaveButton"))).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath(methodDataStore.getValue("CreateSubmission"))).click();
		driver.findElement(By.id("name__v")).sendKeys(seqNum);
		driver.findElement(By.id("doss_id__c")).sendKeys(legacySysId);
		driver.findElement(By.id("submission_type__rim")).click();
		driver.findElement(By.linkText(subType)).click();
		driver.findElement(By.xpath(methodDataStore.getValue("SaveSubmission"))).click();
		driver.findElement(
				By.xpath("//tr[@class='vv_veeva_document vv_document_table documentTable']//following::a[text()='"
						+ seqNum + "']"))
				.click();
		Thread.sleep(7000);
		Shot.seleniumSaveScreenshot(driver, "4.png");
		/*
		 * List <WebElement> links = driver.findElements(By.
		 * xpath("//tr[@class='vv_veeva_document vv_document_table documentTable']/td[1]"
		 * )); Iterator<WebElement> iter = links.iterator();
		 * 
		 * while(iter.hasNext()) { 
		 * WebElement we = iter.next(); 
		 * String s=
		 * we.getText(); 
		 * System.out.println("The number is "+s); if
		 * (s.equals(seqNum)) { we.click(); } }
		 */
	}

	

	// Validate
	@Test(description = "Validate whether the Newly Created Application is populated on the table")
	public void validation() throws InterruptedException {
		String region = (String) OmnichannelFramework.getTestSessionParameter().getValue("Region");
		String applicationType = (String) OmnichannelFramework.getTestSessionParameter().getValue("AppType");
		String applicationNumber = (String) OmnichannelFramework.getTestSessionParameter().getValue("AppNum");
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Applications")).click();
		// Filter by Region
		driver.findElement(By.xpath(methodDataStore.getValue("RegionFilter"))).click();

		driver.findElement(By.xpath("//*[@type='checkbox']//following::label[text()='" + region + "']")).click();
		// Filter by Application Type
		driver.findElement(By.xpath(methodDataStore.getValue("ApplicationTypeFilter"))).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@type='checkbox']//following::label[text()='" + applicationType + "']"))
				.click();
		Thread.sleep(2000);
		// Getting Total number of Pages as String
		String pages = driver.findElement(By.xpath(methodDataStore.getValue("TotalPages"))).getText();
		// Splitting out unwanted text
		String getnumPages = pages.split("of ")[1];
		// Parsing it to integer before for loop
		int numberOfPages = Integer.parseInt(getnumPages);

		List<WebElement> table = driver.findElements(By.xpath(methodDataStore.getValue("NoOfRowsinTable")));
		System.out.println("Row Number is " + table.size());
		// Creating outer-loop for total Number of Pages
		outerloop: for (int j = 0; j <= numberOfPages; j++) {
			Thread.sleep(1000);
			// Creating outer-loop for total Number of Rows in that Particular
			// Page
			for (int i = 1; i <= table.size(); i++) {
				// Getting the Text from the 1st cell of that particular Row
				WebElement cell = driver
						.findElement(By.xpath("(//tr[@class='gridViewRow vv_gridview_row '])[" + i + "]/td[1]"));
				String textFromCell = cell.getText();
				System.out.println("The Text from the cell is " + textFromCell);
				// Evaluating whether the Text in the cell is Equal our
				// Application Number
				if (textFromCell.equals(applicationNumber)) {
					System.out.println("Clicking on Element");
					WebElement applicationNum = driver
							.findElement(By.xpath("((//tr[@class='gridViewRow vv_gridview_row '])[" + i
									+ "]/td[1]//following::a[@class='vv_link data-name__v actionLinkLink'])[1]"));
					applicationNum.click();
					break outerloop;
				}
			}
			driver.findElement(By.xpath("(//*[@class='fa fa-caret-right'])[1]")).click();
			Thread.sleep(4000);
			System.out.println("Going to Next Page");
		}
		Shot.seleniumSaveScreenshot(driver, "5.png");
		LOGGER.info("The Application found and the number is " + applicationNumber);
	}

	// Upload
	@Test(description = "Upload an HA Communication document")
	@Parameters({ "FilePath" })
	public void upload(@Optional("C:\\Users\\athul.ks\\Desktop\\AKS Template.docx") String FilePath)
			throws AWTException, Exception {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(methodDataStore.getValue("CreateButton"))));
		driver.findElement(By.xpath(methodDataStore.getValue("ClickonCreate"))).click();
		driver.findElement(By.linkText("Upload")).click();
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		driver.findElement(By.id("inboxFileChooserHTML5")).click();
		Thread.sleep(2000);
		StringSelection s = new StringSelection(FilePath);
		Thread.sleep(2000);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		Robot robot = new Robot();
		robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_V);
		robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		driver.findElement(By.xpath(methodDataStore.getValue("DocumentType"))).click();
		driver.findElement(By.linkText("Regulatory › Health Authority Communication")).click();
		driver.findElement(By.xpath(methodDataStore.getValue("SaveButton"))).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "6.png");
	}
	// Import

	@Test(description = "Import a package from Submission")
	@Parameters ({"File"})
	public void Import(@Optional ("US-3.3.zip") String File) throws Exception {
		driver.findElement(By.xpath(methodDataStore.getValue("ClickActionsMenu"))).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Import")).click();
		driver.findElement(By.xpath(methodDataStore.getValue("ChooseButton"))).click();
		Thread.sleep(2000);
		StringSelection s = new StringSelection(File);
		Thread.sleep(2000);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		Robot robot = new Robot();
		robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_V);
		robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		driver.findElement(By.xpath(methodDataStore.getValue("NextButton"))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(methodDataStore.getValue("ImportButton"))).click();
		Thread.sleep(5000);
		Shot.seleniumSaveScreenshot(driver, "7.png");
	}

	// Search
	@Test(description = "Search for an Application in the Search box")
	public void search() throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(methodDataStore.getValue("Home"))));
		Thread.sleep(1000);
		driver.findElement(By.xpath(methodDataStore.getValue("HomeTab"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(methodDataStore.getValue("SearchDropdown"))));
		Thread.sleep(2000);
		driver.findElement(By.xpath(methodDataStore.getValue("Dropdown"))).click();
		driver.findElement(By.linkText("Applications")).click();
		System.out.println("Clicked");
		String applicationNumber = (String) OmnichannelFramework.getTestSessionParameter().getValue("AppNum");
		driver.findElement(By.xpath(methodDataStore.getValue("SearchTextBox"))).sendKeys(applicationNumber);
		driver.findElement(By.xpath(methodDataStore.getValue("SearchButton"))).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath(methodDataStore.getValue("SearchedRecord"))).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "8.png");
	}
	
	@Test(description = "VIEW_FR_016.001.TS")
	@Parameters ({"Submission"})
	public void View_16 (@Optional ("0005") String Submission){
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Submissions")).click();
		driver.findElement(By.linkText(Submission)).click();
		
	}
	
	
	@Test(description = "VIEW_FR_008.001.TS")
	public void View_08 () throws Exception{
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Submissions")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@title='Application Number']")).click();
		Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@id='facet_00A000000002502']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@title='Submission Type']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id='facet_A0V000000001F66']")).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "Filter.png");
	}
	
	@Test(description = "VIEW_FR_003.002.TS")
	public void View_03 () throws Exception{
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Applications")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@title='qwqw']")).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText("180520171")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("(//*[@class='maTarget fa fa-caret-down'])[1]")).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText("All Related Documents")).click();
		Thread.sleep(10000);
		Shot.seleniumSaveScreenshot(driver, "Docs.png");
		driver.findElement(By.xpath("(//*[@class='mdvImg vv_mdv_col'])[2]")).click();
		Thread.sleep(10000);
		Shot.seleniumSaveScreenshot(driver, "Docs 2.png");
	}
	
	@Test(description = "VIEW_FR_003.001.TS")
	@Parameters ({"Submission"})
	public void View_03_01 (@Optional ("0005") String Submission) throws Exception{
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Submissions")).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText(Submission)).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("(//*[@class='maTarget fa fa-caret-down'])[1]")).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText("All Related Documents")).click();
		Thread.sleep(10000);
		Shot.seleniumSaveScreenshot(driver, "Docs 1.png");
		driver.findElement(By.xpath("(//*[@class='mdvImg vv_mdv_col'])[2]")).click();
		Thread.sleep(10000);
		Shot.seleniumSaveScreenshot(driver, "Docs 2.png");
	}
	
	@Test(description = "ARCV_FR_016.01.TC")
	public void ARCV_16 () throws Exception{
		driver.findElement(By.name("applications__c")).click();
		driver.findElement(By.linkText("Submissions")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@title='Application Number']")).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "Filter 1.png");
        driver.findElement(By.xpath("//*[@id='facet_00A000000002502']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@title='Application Type']")).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "Filter 2.png");
		driver.findElement(By.xpath("//*[@id='facet_A0V000000001F13']")).click();
		Thread.sleep(4000);
		Shot.seleniumSaveScreenshot(driver, "Filter 3.png");
	}
}
