package com.cucumber.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author Manu Sharma
 *
 */
public class XLS_Reader {
	public static String filename = System.getProperty("user.dir")+File.separator+"data"+File.separator+"DataNew2.xls";
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	
	public XLS_Reader(String path) {
		
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
		
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);
		int col_Num=-1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_Num=i;
		}
		if(col_Num==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(col_Num);
		
		if(cell==null)
			return "";
		//System.out.println(cell.getCellType());
		if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			  return cell.getStringCellValue();
		else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
			  
			  String cellText  = String.valueOf(cell.getNumericCellValue());
			  if (HSSFDateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cal.get(Calendar.MONTH)+1 + "/" + 
		                      cellText;
		           
		           //System.out.println(cellText);

		         }

			  
			  
			  return cellText;
		  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
		      return ""; 
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
		
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(colNum);
		if(cell==null)
			return "";
		
	  if(cell.getCellType()==Cell.CELL_TYPE_STRING)
		  return cell.getStringCellValue();
	  else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
		  
		  String cellText  = String.valueOf(cell.getNumericCellValue());
		 /* if (HSSFDateUtil.isCellDateFormatted(cell)) {
	           // format in form of M/D/YY
			  double d = cell.getNumericCellValue();

			  Calendar cal =Calendar.getInstance();
			  cal.setTime(HSSFDateUtil.getJavaDate(d));
	            cellText =
	             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
	           cellText = cal.get(Calendar.MONTH)+1 + "/" +
	                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
	                      cellText;
	           
	          // System.out.println(cellText);

	         }

		  */
		  
		  return cellText;
	  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
	      return "";
	  else 
		  return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	// returns true if value is set in XL sheet else false
public boolean setCellData(String sheetName,int rowNum, int colNum, String value){		
		
		try{
		fis = new FileInputStream(path); 
		workbook = new HSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		//System.out.println("index - "+ index);		
		
		sheet = workbook.getSheetAt(index);
		//sheet = workbook.getSheet("TestData");	 

	    // cell style
	     //System.out.println("befor row");
	    
		//row = sheet.getRow(rowNum-1);
	    if(sheet.getRow(rowNum-1)==null) {
		cell = sheet.createRow(rowNum-1).createCell(colNum);
	    }
	    else {
	    	cell = sheet.getRow(rowNum-1).createCell(colNum);
	    }
		//cell=row.createCell(colNum);
		
		//cell=row.getCell(colNum);
		
		if (null == cell) {
			//System.out.println("inside");
			cell = sheet.getRow(rowNum-1).createCell(colNum);
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		
		
			/*
			 * CellStyle cs = workbook.createCellStyle(); cs.setWrapText(true);
			 * cell.setCellStyle(cs);
			 */
	    cell.setCellValue(value);
         //System.out.println(value);
	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			System.out.println("Exception Occured while writing to XL Sheet = "+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
		//System.out.println("setCellData setCellData******************");
		try{
		fis = new FileInputStream(path); 
		workbook = new HSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		
		sheet = workbook.getSheetAt(index);
		//System.out.println("A");
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
				colNum=i;
		}
		
		if(colNum==-1)
			return false;
		sheet.autoSizeColumn(colNum); //ashish
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);
			
	    cell.setCellValue(data);
	    HSSFCreationHelper createHelper = (HSSFCreationHelper) workbook.getCreationHelper();

	    //cell style for hyperlinks
	    //by default hypelrinks are blue and underlined
	    CellStyle hlink_style = workbook.createCellStyle();
	    HSSFFont hlink_font = workbook.createFont();
	    hlink_font.setUnderline(HSSFFont.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    hlink_style.setFont(hlink_font);
	    //hlink_style.setWrapText(true);

	    HSSFHyperlink link = createHelper.createHyperlink(HSSFHyperlink.LINK_FILE);
	    link.setAddress(url);
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlink_style);
	      
	    fileOut = new FileOutputStream(path);
		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		
		
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);	
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;
		
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		//System.out.println("**************addColumn*********************");
		
		try{				
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		sheet=workbook.getSheetAt(index);
		
		row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		
		//cell = row.getCell();	
		//if (cell == null)
		//System.out.println(row.getLastCellNum());
		if(row.getLastCellNum() == -1)
			cell = row.createCell(0);
		else
			cell = row.createCell(row.getLastCellNum());
	        
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try{
		if(!isSheetExist(sheetName))
			return false;
		fis = new FileInputStream(path); 
		workbook = new HSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		HSSFCreationHelper createHelper = (HSSFCreationHelper) workbook.getCreationHelper();
		style.setFillPattern(HSSFCellStyle.NO_FILL);
		
	    
	
		for(int i =0;i<getRowCount(sheetName);i++){
			row=sheet.getRow(i);	
			if(row!=null){
				cell=row.getCell(colNum);
				if(cell!=null){
					cell.setCellStyle(style);
					row.removeCell(cell);
				}
			}
		}
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
  // find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
				if(index==-1)
					return false;
				else
					return true;
		}
		else
			return true;
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
		 return -1;
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row==null)
			return -1;
		
		return row.getLastCellNum();
		
		
		
	}
	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
		//System.out.println("ADDING addHyperLink******************");
		
		url=url.replace('\\', '/');
		if(!isSheetExist(sheetName))
			 return false;
		
	    sheet = workbook.getSheet(sheetName);
	    
	    for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
	    		//System.out.println("**caught "+(i+index));
	    		setCellData(sheetName, screenShotColName, i+index, message,url);
	    		break;
	    	}
	    }


		return true; 
	}
	public int getCellRowNum(String sheetName,String colName,String cellValue){
		
		for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    		return i;
	    	}
	    }
		return -1;
		
	}
		
    public static HashMap loadExcelLines(String string)
    {
        // Used the LinkedHashMap and LikedList to maintain the order
        HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();

        LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();

        String sheetName = null;
        // Create an ArrayList to store the data read from excel sheet.
        // List sheetData = new ArrayList();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(string);
            // Create an excel workbook from the file system
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            for (int i = 0; i < workBook.getNumberOfSheets(); i++)
            {
                XSSFSheet sheet = workBook.getSheetAt(i);
                // XSSFSheet sheet = workBook.getSheetAt(0);
                sheetName = workBook.getSheetName(i);

                Iterator rows = sheet.rowIterator();
                while (rows.hasNext())
                {
                    XSSFRow row = (XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();

                    List data = new LinkedList();
                    while (cells.hasNext())
                    {
                        XSSFCell cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        data.add(cell);
                    }
                    hashMap.put(row.getRowNum(), data);

                    // sheetData.add(data);
                }
                outerMap.put(sheetName, hashMap);
                hashMap = new LinkedHashMap<Integer, List>();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return outerMap;

    } 
	
	
}
