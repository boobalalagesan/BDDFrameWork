package com.cucumber.pages;



import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.JsonHttpResponseCodec;
import org.openqa.selenium.support.FindBy;

import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cucumber.pages.exceptions.DataSheetException;
import com.cucumber.util.DataUtil;
import com.cucumber.util.RunConfig;


import jxl.read.biff.BiffException;


import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;


public class LoginPage  extends BasePage {


	
	
	
	DataUtil obj=new DataUtil();
	public LoginPage(RemoteWebDriver driver, ExtentTest test)
	{
		super(driver,test);
		
	}
	
		public LoginPage() {
		// TODO Auto-generated constructor stub
	}

		@FindBy(xpath="//input[@id='j_username']") WebElement Username;
		@FindBy(xpath="//input[@id='j_password']") WebElement Password;
		@FindBy(xpath="//button[normalize-space()='Sign In']") WebElement Submit;
		
	
		public void Login()throws Throwable{
			System.out.print("inside login method");
		try {
			DataUtil obj=new DataUtil();
        	String[] testData=new String[20];        	
        	testData=obj.getTestData(RunConfig.TESTDATA_XLS_PATH, RunConfig.TESTDATA_SHEET, "Logincreds");
			TimeUnit.SECONDS.sleep(2);
			if(Username.isDisplayed()) {
				
				Username.sendKeys("james");
				//testData[0].toString()
			}
			if(Password.isDisplayed()) {
				Password.sendKeys(testData[1].toString());
				takeScreenShot();
				//Thread.sleep(3000);
			}
			if(Submit.isDisplayed()) {
				Submit.click();
				
			}
			
			
		}
		catch (Exception e) {
            test.log(Status.FAIL, "Logout Exception occured");
     }
	}

}
	


