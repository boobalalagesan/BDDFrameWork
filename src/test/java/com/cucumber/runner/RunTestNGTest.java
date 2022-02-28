package com.cucumber.runner;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;*/
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static java.util.Arrays.asList;
import com.cucumber.util.DataUtil;
import com.cucumber.util.RunConfig;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cucumber.email.SendMailAftrExec;
import com.cucumber.pages.BasePage;


import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;

public class RunTestNGTest extends BasePage{

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent=null;
	//public static SessionFactory sessionFactoryObj;

	@BeforeSuite
	public void Start() throws Exception{
		System.out.println("Before Suite");
		  TrustManager[] trustAllCerts = new TrustManager[]{
      		    new X509TrustManager() {

      		        @Override
      		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
      		            return null;
      		        }

      		        @Override
      		        public void checkClientTrusted(
      		                java.security.cert.X509Certificate[] certs, String authType) {
      		        }

      		        @Override
      		        public void checkServerTrusted(
      		                java.security.cert.X509Certificate[] certs, String authType) {
      		        }
      		    }
      		};
        
      		try {
      		    SSLContext sc = SSLContext.getInstance("SSL");
      		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
      		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      		} catch (Exception e) {
      		}
		getData1();
		getData2();
		//buildSessionFactory();		
		
		htmlReporter = new ExtentHtmlReporter(RunConfig.REPORTS_PATH+".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);	
	}
	
	
	@Test(dataProvider = "FeatureSet1")
	public void FeatureTest1(String featureName, String tags) {
		long id = Thread.currentThread().getId();
		System.out.println("FeatureSet1 is executing. Thread id is: " + id);
		try {
			Execute_test(featureName,tags);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(dataProvider = "FeatureSet2")
	public void FeatureTest2(String featureName, String tags) {
		long id = Thread.currentThread().getId();
		System.out.println("FeatureSet2 is executing. Thread id is: " + id);
		try {
			Execute_test(featureName,tags);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void Execute_test(String featureName, String tags) throws MalformedURLException{
		
		
		List<String> CucumberOpts= new ArrayList<String>();
		
		if(tags.isEmpty()) {
			CucumberOpts.add("-p");
			CucumberOpts.add("com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html");
			CucumberOpts.add("--glue");
			CucumberOpts.add("steps");
			CucumberOpts.add(featureName);
		}
		else {
			CucumberOpts.add("-p");
			CucumberOpts.add("com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html");
			CucumberOpts.add("--glue");
			CucumberOpts.add("steps");
			CucumberOpts.add(featureName);
			CucumberOpts.add("--tags");
			CucumberOpts.add(tags);
		}
		
		String[] feature = featureName.split("\\/");
		String featurename="";
		for(int i=0;i<feature.length;i++) {
			if(feature[i].contains("."))
			{
				String[] temp=feature[i].split("\\.");
				featurename=temp[0];
			}
		}
		String[] argv=CucumberOpts.toArray(new String[CucumberOpts.size()]);
		RuntimeOptions runtimeOptions= new RuntimeOptions(new ArrayList<String>(asList(argv)));
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		ResourceLoader resourceLoader = new MultiLoader(classloader);
		ClassFinder classFinder=new ResourceLoaderClassFinder(resourceLoader, classloader);
		Runtime runtime =new Runtime(resourceLoader,classFinder,classloader,runtimeOptions);
		try {
			runtime.run();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		 
	}
	
	@AfterSuite
	public void tearup() throws Throwable{
		System.out.println("AfterSuite");
		
		SendMailAftrExec mail=new SendMailAftrExec();
		
		if(extent!=null){
			//extent.endTest(test);
			extent.flush();
			//htmlReporter.flush();
		}
		
		// try { mail.sendMail(); } catch(Exception e) { e.printStackTrace(); }
		 
	}
	
	@DataProvider(name = "FeatureSet1" , parallel=true)
	public Object[][] getData1() throws Exception {
		Object[][] dataMap=null;
		DataUtil dataSheetObj=new DataUtil();
		String dataSheetNM = "FeatureSequence1";
		dataMap= dataSheetObj.getData(RunConfig.DATA_XLS_PATH, dataSheetNM);
		return dataMap;
	}
	
	@DataProvider(name = "FeatureSet2", parallel=true)
	public Object[][] getData2() throws Exception {
		Object[][] dataMap=null;
		DataUtil dataSheetObj=new DataUtil();
		String dataSheetNM = "FeatureSequence2";
		dataMap= dataSheetObj.getData(RunConfig.DATA_XLS_PATH, dataSheetNM);
		return dataMap;
	}
	
	
}
