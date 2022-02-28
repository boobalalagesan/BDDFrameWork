package com.cucumber.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cucumber.pages.exceptions.DataSheetException;

import jxl.read.biff.BiffException;
import jxl.Workbook;
import jxl.Cell;
import jxl.JXLException;
import jxl.Sheet;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;



public class DataUtil{
	
		
	
	String xlFilePath;
	
	public String[][] getData(String xls, String sheetName) throws IOException,BiffException,DataSheetException{
		
		String[][] dataObjectArray=null;
		FileInputStream fis = new FileInputStream(xls);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		 
		
		int columns= getColumnCount(xls,sheetName);
		int rows= getRowCount(xls,sheetName);
		System.out.println("columns"+columns+" " + "rows"+rows);
		@SuppressWarnings("unused")
		int ActualRowCount =0;
		int dataObjectArraySize=getValidRows(xls,sheetName);		
		dataObjectArray = new String[dataObjectArraySize][columns-2];
		int index=0;
		boolean headingStatus= true;
		if(headingStatus)
		{
			for(int row=1;row<rows;row++) {
				
				String executionStatus = sheet.getCell(0,row).getContents();
				//System.out.println(executionStatus);
				//System.out.println(sheet.getCellComment(1, row+1).toString());
				if(executionStatus.trim().equalsIgnoreCase("Y")) {
					ActualRowCount++;
					for(int col=1;col<columns-1;col++) {
						dataObjectArray[index][col-1] = sheet.getCell(col,row).getContents();
						//System.out.println(col + ":" +dataObjectArray[index][col-1]);
					}
					index++;
				}
			}
		}
		else {
			throw new DataSheetException(
				"The Sheet headings are invalid: The heading status should be Browser and Execution Status");
			}
			
		
		//fis.close();
		return dataObjectArray;
	}
	
public String[][] getMultipleTestData(String xls, String sheetName, String testcaseName) throws IOException,BiffException,DataSheetException{
		
		String[][] dataObjectArray=new String[40][40];
		FileInputStream fis = new FileInputStream(xls);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		
		int columns= getColumnCount(xls,sheetName);
		int rows= getRowCount(xls,sheetName);
		
	
		@SuppressWarnings("unused")
		int ActualRowCount =0;
		int count=getValidTestDataRows(xls, sheetName, testcaseName);
		
		
		
		int j=0;
		boolean headingStatus= true;
		if(headingStatus)
		{
			for(int row=1;row<rows;row++) {
				String testCase = sheet.getCell(0,row).getContents();
				//System.out.println(testCase);
				
				if(testCase.equalsIgnoreCase(testcaseName)) {
					ActualRowCount++;
					int index=0;
					for(int col=1;col<columns;col++) {
						if(!sheet.getCell(col,row).getContents().isEmpty()) {
						dataObjectArray[j][index] = sheet.getCell(col,row).getContents();
						//System.out.println(dataObjectArray[j][index] +": " +j + ": "+ index);
						index++;
						}
					}
					j++;
					
				}
				
			}
		}
		else {
			throw new DataSheetException(
				"The Sheet headings are invalid: The heading status should be Browser and Execution Status");
			}
			
	
		//fis.close();
		return dataObjectArray;
		//
		//
		/*
		DataUtil getdata =new DataUtil();
		getdata.getColumnCount("\\BDDFramework/Data/DataNew.xls", "TestData");
		getTestData("DataNew.xls", "TestData", "http://NICAPTEST.GENPT.NET/api/V1/universal");
		*/

	}

	
public String[] getTestData(String xls, String sheetName, String testcaseName) throws IOException,BiffException,DataSheetException{
		
		String[] dataObjectArray=new String[20];
		FileInputStream fis = new FileInputStream(xls);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		
		int columns= getColumnCount(xls,sheetName);
		int rows= getRowCount(xls,sheetName);
		
	
		@SuppressWarnings("unused")
		int ActualRowCount =0;
		int dataObjectArraySize=getValidTestDataRows(xls, sheetName, testcaseName);
		
		
		int index=0;
		boolean headingStatus= true;
		if(headingStatus)
		{
			for(int row=1;row<rows;row++) {
				String testCase = sheet.getCell(0,row).getContents();
				//System.out.println(testCase);
				
				if(testCase.equalsIgnoreCase(testcaseName)) {
					ActualRowCount++;
					for(int col=1;col<columns;col++) {
						dataObjectArray[index] = sheet.getCell(col,row).getContents();
						
						index++;
					}
					
				}
			}
		}
		else {
			throw new DataSheetException(
				"The Sheet headings are invalid: The heading status should be Browser and Execution Status");
			}
			
		
		//fis.close();
		return dataObjectArray;
	}
	public int getValidTestDataRows(String xls,String sheetName,String testcaseName) throws IOException, BiffException, DataSheetException{
		FileInputStream fis = new FileInputStream(xls);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int rows =getRowCount(xls,sheetName);
		int count=0;
		for(int row=1;row<rows;row++) {
			if(sheet.getCell(0,row).getContents().equalsIgnoreCase(testcaseName)) {
				count++;
			}
		}
		fis.close();
		return count;
	}
	public int getValidRows(String xls,String sheetName) throws IOException, BiffException, DataSheetException{
		FileInputStream fis = new FileInputStream(xls);
		Workbook workbook = Workbook.getWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int rows =getRowCount(xls,sheetName);
		int count=0;
		for(int row=1;row<rows;row++) {
			if(sheet.getCell(0,row).getContents().equalsIgnoreCase("Y")) {
				count++;
			}
		}
		fis.close();
		return count;
	}
		public int getRowCount(String xls,String sheetName) throws IOException, BiffException
	   {
			FileInputStream fis = new FileInputStream(xls);
			Workbook workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);
			int rows =sheet.getRows();
			fis.close();
			return rows;
	   }
	 
	   public int getColumnCount(String xls,String sheetName)throws IOException, BiffException,DataSheetException{
		   int column=0;
		   try {
			   FileInputStream fis = new FileInputStream(xls);
				Workbook workbook = Workbook.getWorkbook(fis);
				Sheet sheet = workbook.getSheet(sheetName);
				column =sheet.getColumns();
				
				if(column!=0) {
					return column;
				}else {
					throw new DataSheetException("The input data sheet is blank");
				}
		   }
		   catch(FileNotFoundException fe) {
			   throw new DataSheetException("Please provide a valid sheet path"+xls+ " "+ fe);
		   }
		   catch(NullPointerException ne) {
			   throw new DataSheetException("No sheet fount with the class name:"+ sheetName);
		   }
	   }
	   
	   
	   
	  
	public void writeData(String xls,String sheetName,String value)throws IOException, BiffException,DataSheetException, Throwable, JXLException {
			WritableWorkbook workbook =null;
		   try {
			   	
				workbook=Workbook.createWorkbook(new File(xls));
				 WritableSheet excelSheet =  workbook.createSheet(sheetName,4);
				 Label label = new Label(0, 0, "Keyword");
		            excelSheet.addCell(label);
		            Label val=new Label(1,0,"Value");
		            excelSheet.addCell(val);
		            workbook.write();
				
		   }
		   catch(FileNotFoundException fe) {
			   throw new DataSheetException("Please provide a valid sheet path"+xls+ " "+ fe);
		   }
		   catch(NullPointerException ne) {
			   throw new DataSheetException("No sheet fount with the class name:"+ sheetName);
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
		   finally {

	            if (workbook != null) {
	                try {
	                	workbook.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } catch (WriteException e) {
	                    e.printStackTrace();
	                }
	            }
	  }
	}
	public String[] getWriteData(String xls,String sheetName) {
	   Workbook workbook;
	   String[] val=new String[10];
	try {
		workbook = Workbook.getWorkbook(new File(xls));
	   
        Sheet sheet = workbook.getSheet(0);
        Cell cell1 = sheet.getCell(0, 0);
        System.out.print(cell1.getContents() + ":");    // Test Count + :
        val[0]=cell1.getContents();
        Cell cell2 = sheet.getCell(0, 1);
        System.out.println(cell2.getContents());
        val[1]=cell2.getContents();
		
	}
		catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return val;
	}
		  
	public static boolean setResultsData(XLS_Reader xls, String sheetName, int rowNum, int colNum, String value){
		/*System.out.println("sheetName - "+ sheetName);
		System.out.println("colName - "+ colNum);
		System.out.println("rowNum - "+ rowNum);
		System.out.println("data - "+ data);*/		
		 
			boolean updated = xls.setCellData(sheetName, rowNum, colNum, value);
			//System.out.println("is sheet Updated?="+updated);
			if(updated)				
					return true;
				else
					return false;
			
		}
}

	

