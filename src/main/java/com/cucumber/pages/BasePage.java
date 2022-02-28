package com.cucumber.pages;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;
import com.cucumber.util.HibernateUtil;
//import com.cucumber.util.HibernateUtil;
//import com.cucumber.base.BaseTest;
import com.cucumber.util.RunConfig;
import com.cucumber.util.XLS_Reader;
import com.google.common.io.Files;



public class BasePage  {
	public  RemoteWebDriver driver;
	public  ExtentTest test;
	public String browser="";
	public static Hashtable<String,String> envDetailsTable;
	public static String[][] testResults =new String[100][100];
	 private static WebElement element;
	 public XLS_Reader xls = new XLS_Reader(RunConfig.DATA_XLS_PATH);
	 public XLS_Reader xls1 = new XLS_Reader(RunConfig.TESTDATA_XLS_PATH);
	 
	//public static SessionFactory factory= HibernateUtil.getSessionFactory();
	public static String[] inputData=new String[20];
	public static String[] outputData=new String[20];
	 public static int r1;
	 public static String ScenarioName;
	 public static HashMap<String, String> Vehicledetails = new HashMap<String, String>();
	 public static String lineAbbr="";				
	public static String partnum="";
	public static String part_Number=null;
	public static boolean isJumpstart=false;
	public static String vehcondText= "";
	public static String getNXPCartID=""; 
	public static String POName=""; 
	public static String PODate=""; 
	public static String SalesOrderNo=""; 
	public static String PurchaseOrderNo=""; 
	public static String LineAbbrev=""; 
	public static String PartNO=""; 
	public static String PartDesc=""; 
	public static String Quantity="";
	public static String SOno="";
	public static String DC_ABBREV="";
	public BasePage(){
		//default constructor		
	}
	
	public BasePage(RemoteWebDriver driver, ExtentTest test){
		this.driver = driver;
		this.test = test;
		}
	
		
	public RemoteWebDriver OpenApplication(String url, String browser) throws Exception{
		
		  String remoteTest = System.getProperty("test.remotely");
		  System.out.println("************** test.remptely = "+remoteTest);
		  if(!StringUtils.isEmpty(remoteTest))
		  { executeRemoteTest(browser); 
		  } else {
		  executeLocalTest(browser);
		  }
		 
		//executeLocalTest(browser);
		TimeUnit.SECONDS.sleep(2);
		driver.get(url.toString());
		return driver;
		
	}

	
	private void executeLocalTest(String browser) {
		DesiredCapabilities capabilities = null;
		if(browser.equals("Firefox")){
				System.setProperty("webdriver.gecko.driver", RunConfig.MOZILLA_DRIVER_EXE);				
				driver = new FirefoxDriver();
				driver.manage().window().maximize();								
			}
			
			else if(browser.equals("Chrome")){				
				System.setProperty("webdriver.chrome.driver", RunConfig.CHROME_DRIVER_EXE);					
				ChromeOptions co = new ChromeOptions();	
				co.addArguments("--disable-gpu");
				//co.addArguments("--incognito");
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY , co);
				
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);	
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				
				driver = new ChromeDriver(capabilities);
				driver.manage().window().maximize();
			}
			
			else if(browser.equals("IE")){				
				System.setProperty("webdriver.ie.driver", RunConfig.IE_DRIVER_EXE);	
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability("requireWindowFocus", true);
				//capabilities.setCapability("ensureCleanSession", true);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ignore-certificate-error", true);
				capabilities.setCapability("nativeEvents", false);    
				capabilities.setCapability("unexpectedAlertBehaviour", "accept");				
				capabilities.setCapability("disable-popup-blocking", true);
				capabilities.setCapability("enablePersistentHover", true);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				capabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, true);
				capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true);
				driver = new InternetExplorerDriver(capabilities);
				driver.manage().window().maximize();
			}
			}

	private void executeRemoteTest(String browser) throws MalformedURLException {
		String seleniumHubUrl="";
		seleniumHubUrl = System.getProperty("selenium.hub.url");
		System.out.println("************ selenium.hub.url = "+seleniumHubUrl);
		if(StringUtils.isEmpty(seleniumHubUrl)) {
			throw new RuntimeException("Error: The system property selenium.hub.url is not supplied.");
		}
		if(browser.equals("Chrome")){
		ChromeOptions co = new ChromeOptions();				
		//co.addArguments("--incognito");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY , co);
		
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);	
		cap.setCapability("ignore-certificate-error", true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new RemoteWebDriver(new URL(seleniumHubUrl),cap);
		driver.manage().window().maximize();
		}
		else if(browser.equals("IE")){				
											
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();		
			
			//System.setProperty("webdriver.ie.driver", RunConfig.IE_DRIVER_EXE);	
		
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("requireWindowFocus", true);
			//capabilities.setCapability("ensureCleanSession", true);
			ieCapabilities.setCapability("ignoreZoomSetting", true);
			ieCapabilities.setCapability("ignore-certificate-error", true);
			ieCapabilities.setCapability("nativeEvents", false);    
			ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");				
			ieCapabilities.setCapability("disable-popup-blocking", true);
			ieCapabilities.setCapability("enablePersistentHover", true);
			ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ieCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			ieCapabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, true);
			ieCapabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true);
			driver = new RemoteWebDriver(new URL(seleniumHubUrl),ieCapabilities);						
			driver.manage().window().maximize();
		}
	}
	
	public void closeBrowser(){
		//System.out.println("1");
		if(driver!=null){
			//System.out.println("2");
			
			driver.manage().deleteAllCookies();
			driver.quit();
		}
	}
	
	public void getBase64Screenshot(WebDriver d1, ExtentTest test) throws IOException {

	    Date oDate = new Date();
	    SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sDate = oSDF.format(oDate);
	    String encodedBase64 = null;
	    FileInputStream fileInputStream = null;
	  //  TakesScreenshot screenshot = (TakesScreenshot) Base.driver;
	   // File source = screenshot.getScreenshotAs(OutputType.FILE);
	    File scrFile = ((TakesScreenshot)d1).getScreenshotAs(OutputType.FILE);
	   
	    
	    String destination = "ExtentReport_screenshots" + File.separator +  sDate + ".png";
	    File finalDestination = new File(destination);
	    FileUtils.copyFile(scrFile, finalDestination);

	    try {
	        fileInputStream =new FileInputStream(finalDestination);
	        byte[] bytes =new byte[(int)finalDestination.length()];
	        fileInputStream.read(bytes);
	        encodedBase64 = new String(Base64.encodeBase64(bytes));
	        
	    }catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
	   // ScreenCapture sc = new ScreenCapture();
	   
       // MediaEntityBuilder.createScreenCaptureFromBase64String(encodedBase64).build();
		/*
		 * media = new ThreadLocal<Media>(); sc.setPath(null);
		 * sc.setBase64String(encodedBase64); sc.getSourceWithIcon(); media.set(sc);
		 */
	   // test.addScreenCaptureFromPath(MediaEntityBuilder.createScreenCaptureFromBase64String(encodedBase64).build());
	   test.log(Status.INFO, " ", MediaEntityBuilder.createScreenCaptureFromBase64String(encodedBase64).build());
	  // return "data:image/gif;base64,"+MediaEntityBuilder.createScreenCaptureFromBase64String(encodedBase64).build();
	}
	
    

	public void waitForPageLoad(RemoteWebDriver driver) {
		
		WebDriverWait wait = new WebDriverWait(driver, 120);
		//WebDriverWait wait = new WebDriverWait(driver, 0)
		
	    wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver wdriver) {
	            return ((JavascriptExecutor) wdriver).executeScript(
	                "return document.readyState"
	            ).equals("complete");
	        }
	    });
	}
	
	public boolean isElementPresent(WebElement element){
		 
		boolean isPresent = false;				
		
		try {
			boolean status=element.isDisplayed();
			System.out.println("element status from isElementPresent= "+status);
			Assert.assertEquals(element.isDisplayed(),true);
			isPresent = true;
		}catch(Exception e){
			isPresent = false;			
		}
		
		return isPresent;
		
	}
	
	public boolean waitUntilPageLoad(String id)
    {
        String a = driver.getTitle();
        WebDriverWait wait = new WebDriverWait(driver,10);   
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElementById(id)));
        if (element!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	public void click(String xpath) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			if(element.isDisplayed()) {
				element.click();
			}
			
		}
		catch(NoSuchElementException e) {
 			test.log(Status.FAIL, "NoSuchElementException Occured");
 		}
 		catch(ElementNotVisibleException e) {
 			test.log(Status.FAIL, "ElementNotVisibleException Occured");
 		}
 		catch(TimeoutException e) {
 			test.log(Status.FAIL, "TimeoutException Occured");
 		}
			catch(InvalidElementStateException e) {
				test.log(Status.FAIL, "InvalidElementStateException Occured");
			}
		catch(Exception e){
			System.out.println(e);	
		}
	}
	public boolean isElementPresent(By elementLocation){
		WebElement element; 
		boolean isPresent = false;				
		
		try {
			element = driver.findElement(elementLocation);
			isPresent  =element.isDisplayed();
			System.out.println("element status from isElementPresent= "+isPresent );
			
		}
		catch(NoSuchElementException e) {
 			test.log(Status.FAIL, "NoSuchElementException Occured");
 		}
 		catch(ElementNotVisibleException e) {
 			test.log(Status.FAIL, "ElementNotVisibleException Occured");
 		}
 		catch(TimeoutException e) {
 			test.log(Status.FAIL, "TimeoutException Occured");
 		}
			catch(InvalidElementStateException e) {
				test.log(Status.FAIL, "InvalidElementStateException Occured");
			}
		catch(Exception e){
			isPresent = false;			
		}
		
		return isPresent;
	}
	
	public void waitUntilClickable(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void clickButton(RemoteWebDriver driver, String identifyBy,
			String locator, String alt) throws InterruptedException {
		
	}
	public boolean syncObject(By locator) throws TimeoutException {
		boolean IsDisplayed = false;
		WebElement element = null;
		try { 
			WebDriverWait wait = new WebDriverWait(driver, 3);			
			element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));	
			System.out.println("element status from isElementPresentExplict= "+IsDisplayed); 
			IsDisplayed = true;
			return IsDisplayed;
			// currentTime();
		} catch (TimeoutException e) {
			System.out.println("element status from isElementPresentExplict= "+IsDisplayed);
			return IsDisplayed;
			
		}
}
	
	public boolean isPageDisplayed(WebElement element){		 
		boolean isDisplayed = false;						
		try {
			isDisplayed = true;
		}catch(Exception e){
			isDisplayed = false;			
		}		
		return isDisplayed;
		}
	
	public void highLight(WebElement element)
	{
	JavascriptExecutor js=(JavascriptExecutor)driver; 

	js.executeScript("arguments[0].setAttribute('style', ' border: 2px solid red;');", element);
	test.log(Status.PASS, element.getText().toString() + " is displayed" );
	try 
	{
	Thread.sleep(1000);
	} 
	catch (InterruptedException e) {

	System.out.println(e.getMessage());
	} 

	js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element); 

	}
	public void takeScreenShot() throws Exception{
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_")+ ".png";
		String filePath = RunConfig.REPORTS_PATH+"screenshots//"+screenshotFile;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//test.log(Status.INFO,"INFO");
		//test.addScreenCaptureFromPath(filePath);
		getBase64Screenshot(driver, test);
		
	}
	
	
	
	public String alert1;
	public String alert2;
	public void handleAlert_1()throws Throwable{
		try {
	        Alert alert = driver.switchTo().alert();
	        Date d = new Date();
			String screenshotFile = d.toString().replace(":", "_").replace(" ", "_")+ ".png";
			String filePath = RunConfig.REPORTS_PATH+"screenshots//"+screenshotFile;
	        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        ImageIO.write(image, "png", new File(filePath));	
	        System.out.println("Alert detected");
	        test.log(Status.INFO, "Alert detected: {}" + alert.getText());
	        test.addScreenCaptureFromPath(filePath);
	        alert1=alert.getText();
	        alert.accept();
	        
	       /* if(alert.getText().equalsIgnoreCase(anotherString)("At least one store must be added to the event")) {*/
	        	
	        	 System.out.println(alert1);
	        test.log(Status.INFO,"INFO");
			
	        //}
	    }
		catch(NoSuchElementException e) {
 			test.log(Status.FAIL, "NoSuchElementException Occured");
 		}
 		catch(ElementNotVisibleException e) {
 			test.log(Status.FAIL, "ElementNotVisibleException Occured");
 		}
 		catch(TimeoutException e) {
 			test.log(Status.FAIL, "TimeoutException Occured");
 		}
			catch(InvalidElementStateException e) {
				test.log(Status.FAIL, "InvalidElementStateException Occured");
			}
		catch (Exception e) {
	    }
	}
	public void handleAlert_2()throws Throwable{
		try {
	        Alert alert_1 = driver.switchTo().alert();
	        Date d = new Date();
			String screenshotFile = d.toString().replace(":", "_").replace(" ", "_")+ ".png";
			String filePath = RunConfig.REPORTS_PATH+"screenshots//"+screenshotFile;
	        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        ImageIO.write(image, "png", new File(filePath));	
	        System.out.println("Alert detected");
	        test.log(Status.INFO, "Alert detected: {}" + alert_1.getText());
	        if(alert_1.getText().equalsIgnoreCase("Please confirm that all in-transits have been recorded. Once finalized, no more updates are allowed. Comment:")) {
	        alert_1.sendKeys("Ok");
	        }
	        else if(alert_1.getText().contains("Error")) {
	        	test.log(Status.FAIL, alert_1.getText());
	        }
	        test.addScreenCaptureFromPath(filePath);
	        alert2=alert_1.getText();
	        alert_1.accept();	      
	        System.out.println(alert2);
	        if(alert1!=null && alert2!=null) {
	        test.log(Status.FAIL," Please select the Mandatory Field "+alert1);			
	        }
	    }
		catch(NoSuchElementException e) {
 			test.log(Status.FAIL, "NoSuchElementException Occured");
 		}
 		catch(ElementNotVisibleException e) {
 			test.log(Status.FAIL, "ElementNotVisibleException Occured");
 		}
 		catch(TimeoutException e) {
 			test.log(Status.FAIL, "TimeoutException Occured");
 		}
			catch(InvalidElementStateException e) {
				test.log(Status.FAIL, "InvalidElementStateException Occured");
			}
		catch (UnhandledAlertException e) {
	    	e.printStackTrace();
	    }
		
		catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	

		

	public void captureComponent(ExtentTest test) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension oScreenSize = toolkit.getScreenSize();
		Rectangle rect = new Rectangle(oScreenSize);
		Robot robot = null;

		try {
			Date d = new Date();
			String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";

			String format = "png";
			String fileName = RunConfig.REPORTS_PATH + "screenshots//" + screenshotFile;

			File filesource = new File(fileName);
			//File filedestination = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotFile);
			//File filedestination = new File("/screenshots/" + screenshotFile);
			try {
				robot = new Robot();
			} catch (AWTException e) {

				e.printStackTrace();
			}

			BufferedImage captureImage = robot.createScreenCapture(rect);
			//ImageIO.write(captureImage, format, new File(fileName));
			ImageIO.write(captureImage, format, filesource);
			//FileUtils.copyFile(filesource, filedestination);
			Reporter.addScreenCaptureFromPath(filesource.toString());
			test.addScreenCaptureFromPath(filesource.toString());
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

		public void clickElement(String xpath) throws Exception {
		waitForPageLoad(driver);
		waitUntilClickable(xpath);
		 element=driver.findElement(By.xpath(xpath));
		element.click();
		takeScreenShot();
	}
	public String getTextFromElement(String xpath) throws Exception {
		waitForPageLoad(driver);
		waitForElementPresence(xpath);
		element=driver.findElement(By.xpath(xpath));
		takeScreenShot();
		return element.getText();
	}
	public static void main(String arg[]) {
		String imagefilepath = "Customer_CustomerNameCredit";
		String imagepath = imagefilepath.substring(0, imagefilepath.indexOf("_"));
		String imagename = imagefilepath.substring(imagefilepath.indexOf("_") + 1, imagefilepath.length());
		System.out.println("imagepath" + imagepath);
		System.out.println("imagename" + imagename);
	}
	
	public void waitForPageLoad(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, RunConfig.GLOBAL_WAIT);
		// WebDriverWait wait = new WebDriverWait(driver, 0)

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) wdriver).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	public void waitForPageLoading(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, RunConfig.GLOBAL_WAIT);
		wait.until(pageLoadCondition);
	}

	public void waitForElementPresence(String xpath) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver,RunConfig.GLOBAL_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	public void waitUntilClickable(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, RunConfig.GLOBAL_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	public void clickDropdownValue(List<WebElement> dropdownoptions, String dropdownvalue)throws Throwable
    {
        try
        {
            for (WebElement option : dropdownoptions)
            {
                System.out.println(option.getText());
                if(!isJumpstart)
                {
                   if(dropdownvalue.equals(option.getText()))
                   {
                       option.click();
                       break;
                    }
                }
                else
                {                   
                    int Separator = option.getText().indexOf(':');
                    String text = option.getText().substring(Separator+2);
                    if(dropdownvalue.equals(text))
                       {
                           option.click();
                           isJumpstart=false;
                           break;
                        }
                   
                }
            }
        }
        catch(Exception ex)
        {
            test.log(Status.FAIL, "Exception occured while clicking dropdown value");
            ex.printStackTrace();
        }
    }
  
	
	
	
}
