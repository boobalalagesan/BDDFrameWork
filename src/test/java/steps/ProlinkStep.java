package steps;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cucumber.runner.RunTestNGTest;
import com.cucumber.util.DataUtil;
import com.cucumber.util.RunConfig;
import com.cucumber.util.XLWriter;
import com.github.j3t.ssl.utils.SSLContextBuilder;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.cucumber.listener.Reporter;
import com.cucumber.pages.*;

public class ProlinkStep extends BasePage{

	public ExtentTest test;	
	public RemoteWebDriver rDriver;
	
	String[] Nvalues=null;
	DataUtil obj=new DataUtil();
	 XLWriter writer=null;
	 Scenario s;
	public String scenario_name="";
	@Before
	public void befor(Scenario s) throws Throwable {
		System.out.println(s.getName());
		if(test==null) 
		{
			test=RunTestNGTest.extent.createTest(s.getName());
			//test=SmokeTest.extent.createTest(s.getName());
			Reporter.addScenarioLog(s.getName());
		}
		ScenarioName=s.getName();
		LaunchApp(ScenarioName);
		scenario_name=s.getName();
		FileInputStream fis = new FileInputStream(RunConfig.DATA_XLS_PATH);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(RunConfig.RunReport);
		Cell c1=sheet.findCell(ScenarioName);
		r1=c1.getRow();
	
		long minRunningMemory = (1024*1024);

		Runtime runtime = Runtime.getRuntime();

		if(runtime.freeMemory()<minRunningMemory) {
		 System.gc();
		}
		
		
	}
	
	
	   BasePage basePage=new BasePage(rDriver,test);
	
	
    @SuppressWarnings("restriction")
	public void LaunchApp(String ScenarioName ) throws Throwable { 			
	
    	String[] testData=obj.getTestData(RunConfig.DATA_XLS_PATH, RunConfig.RunReport, ScenarioName);         
          
    
   
    	 rDriver=basePage.OpenApplication(RunConfig.ApplicationURL,testData[0].toString());
    	     
           
}

    public void getStatusCode(RemoteWebDriver driver, String browser) {
    	try {
    	String currentUrl=driver.getCurrentUrl();			
		 URL url = new URL(currentUrl);
	        HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
	        System.out.println(http.getResponseMessage());
	        int statusCode = http.getResponseCode();
	        System.out.println(statusCode);
	        if(statusCode==200) {
	        	String msg="Application Launched in" +browser+ " browser Successfully";        
	            test.log(Status.PASS,msg );
		        }

	        else if(200 != statusCode) {
               System.out.println(currentUrl + " gave a response code of " + statusCode);
	        }
          TimeUnit.SECONDS.sleep(2);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	
	
    @Given("user credentials to launch Prolink$")
	public void Login_Prolink()throws Throwable{
    	try {
			LoginPage login =new LoginPage(rDriver,test);
            PageFactory.initElements(rDriver, login);           
            login.Login();
		}
		catch(Exception e) { 
            test.log(Status.FAIL, "Exception occured");
            e.printStackTrace();
     }
    }
	
	
	
	
	
   
      
    
   	   
   
	
    
   
    
	
    
   
	

	
	 @After 
	 public void after() 
	 { 
		 try { 
			 String[][] dataObjectArray=null;
				
				
				System.out.println("Row of:"+scenario_name+ " "+r1);				
				String status=test.getStatus().toString();
				System.out.println(status);
	         DataUtil.setResultsData(xls,RunConfig.RunReport,r1+1,3,status);
			 System.out.println("Clearing Cache..");
			 ResourceBundle.clearCache(); 
			  rDriver.quit();
			 } 
		 catch(Exception e) 
		 { 
		 e.printStackTrace();
		 }
	 }
	 
}

