package com.cucumber.util;

import java.io.File;



public class RunConfig  {

	public static final String MOZILLA_DRIVER_EXE = System.getProperty("user.dir")+ File.separator+"src"+ File.separator+"main"
												   + File.separator+"resources"+ File.separator+"com"+ File.separator+"cucumber"
												   + File.separator+"drivers"+ File.separator+"geckodriver.exe";
	public static final String CHROME_DRIVER_EXE = System.getProperty("user.dir")+ File.separator+"src"+ File.separator+"main"
												   + File.separator+"resources"+ File.separator+"com"+ File.separator+"cucumber"
												   + File.separator+"drivers"+ File.separator+"chromedriver.exe";
	public static final String IE_DRIVER_EXE =System.getProperty("user.dir")+ File.separator+"src"+ File.separator+"main"
												   + File.separator+"resources"+ File.separator+"com"+ File.separator+"cucumber"
												   + File.separator+"drivers"+ File.separator+"IEDriverServer.exe";
	public static String ApplicationURL = "https://qa.napaprolink.com/?site=prolinkus";
	
	public static int OPERATION_SUCCESS=200;
	public static int DATA_START_ROW_NUM;
	public static int TC_ROW_NUM;
	public static final String COMMONDATA_SHEET = "CommonData";
	public static final String TESTCASES_SHEET = "TestCases";
	public static final String TESTDATA_SHEET = "TestData";
	public static final String TOP10_SEARCH = "Top10_Search";
	public static final String RunReport = "RunReport";
	public static final String Counts = "Cat_SubCat_Counts";
	public static final String OUTPUT = "OUTPUT";
	public static final String API_TESTDATA_SHEET = "API_TestData";
	public static int GLOBAL_WAIT=320;
	public static int REPORT_WAIT=550;
	
	public static final String CERT_PATH=System.getProperty("user.dir")+ File.separator+"certificate.cer";
	public static final String apiresult=System.getProperty("user.dir")+ File.separator+"apiresult.txt";
	public static final String REPORTS_PATH = System.getProperty("user.dir")+ File.separator+"test_reports"+File.separator+"ExtentReport_";	
	public static final String REPORTS_CONFIG_PATH = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"extent-config.xml";
	
	public static final String DATA_XLS_PATH = System.getProperty("user.dir")+ File.separator+"Data"+File.separator+"DriverData.xls";
	public static final String TESTDATA_XLS_PATH = System.getProperty("user.dir")+ File.separator+"Data"+File.separator+"DriverData.xls";
	
	public static final String SendingFrom="Automation_Report@genpt.com";
	//public static final String SendingTo="Archana_MSM@genpt.com,Prabhakar_Tadi@genpt.com,Mahesh_Mahajan@genpt.com,Mohammed_Rouff@genpt.com,Ram_Maringanti@genpt.com,Abhishek_Shukla@genpt.com";
	public static final String SendingTo="boobal_alagesan@genpt.com";
	
	
}
