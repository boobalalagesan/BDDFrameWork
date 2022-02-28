package com.cucumber.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author Manu Sharma
 *
 */
/**
 * The Class XLWriter.
 *
 *
 */
public class XLWriter {
	public  String path;
	
	/*Workbook workbook = null;
	FileInputStream fis = null;
	FileOutputStream fos = null;
	Cell cell = null;*/
	
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	
	public XLWriter(String path){
		this.path=path;
		
		
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(2);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	/**
	 * Update excel data.
	 *
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 * @param content
	 *            the content
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 */
	public void updateXLData(String sheetName,
			String content, int row, int col) {
		/*Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		Row rowX = null;*/
		
		/*try {
			System.out.println("Path from updateXLData= "+path);
			fis = new FileInputStream(path);
			//workbook = getWorkbookObj(fis, path);
			Sheet sheet = workbook.getSheet(sheetName);
			rowX =  sheet.getRow(row);
			String rowValue=rowX.getCell(7).toString();
			System.out.println("rowValue= "+rowValue);
					
			cell = sheet.getRow(row).getCell(col);
			if (null == cell) {
				cell = sheet.getRow(row).createCell(col);
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
			cell.removeCellComment();
			cell.setCellValue(content);
			fis.close();
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			//Logs.LOGGER.log(Level.SEVERE, "", e);
			System.out.println("Exception Occured writing to XL File= " + e.getMessage());
		}*/
		
	}
	public void updateExcelData(String fileName, String sheetName,
			String content, int row, int col) {
		//Logs.LOGGER.info("Writing data to cell: " + row + "," + col);
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			//cell = sheet.getRow(row).getCell(col);
			
				cell = sheet.createRow(row).createCell(col);
				cell.setCellType(Cell.CELL_TYPE_STRING);
		System.out.println(content);
			//cell.removeCellComment();
			cell.setCellValue(content);
			fis.close();
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			//Logs.LOGGER.log(Level.SEVERE, "", e);
			e.printStackTrace();
		}
	}

	// update the excel file based on 2d array
	/**
	 * Update excel data.
	 *
	 * @param data
	 *            the data
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 */
	public static void updateExcelData(String[][] data, String fileName,
			String sheetName) {
		//Logs.LOGGER.info("Writing data ....");
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Cell cell = null;
		Row row = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);
			Sheet sheet = workbook.getSheet(sheetName);
			for (int i = 0; i < data.length; i++) {
				int rowNumber = Integer.parseInt(data[i][0]);
				row = sheet.getRow(rowNumber);
				if (null == row) {
					row = sheet.createRow(rowNumber);
				}
				for (int j = 0; j < data[i].length; j++) {
					cell = sheet.getRow(rowNumber).getCell(j,
							Row.CREATE_NULL_AS_BLANK);
					if (null == cell) {
						cell = sheet.getRow(rowNumber).createCell(j);
					}
					cell.removeCellComment();
					cell.setCellValue(data[i][j]);
				}
			}
			fis.close();
			fos = new FileOutputStream(fileName);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			//Logs.LOGGER.log(Level.SEVERE, "", e);
			e.printStackTrace();
		}
	}

	// Create new excel work sheet
	/**
	 * Creates the work sheet.
	 *
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 */
	public static void createWorkSheet(String fileName, String sheetName) {
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		boolean found = false;
		try {
			fis = new FileInputStream(fileName);
			workbook = getWorkbookObj(fis, fileName);

			// First check is sheet exists ?
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				if (workbook.getSheetAt(i).getSheetName()
						.equalsIgnoreCase(sheetName)) {
					found = true;
					break;
				}
			}
			fis.close();
			if (!found) {
				workbook.createSheet(sheetName);
				fos = new FileOutputStream(fileName);
				workbook.write(fos);
				fos.close();
			} else {
				//Logs.LOGGER.info("Sheet already present.");
			}
		} catch (Exception e) {
			//Logs.LOGGER.log(Level.SEVERE, "", e);
		}
	}
	public static void copyExcelFile(String inputXLFilename, String outputXLFilename) {
		Workbook book1 = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;	
		
		//XSSFWorkbook book1 = null;

			try {
				fis = new FileInputStream(inputXLFilename);
				book1 = getWorkbookObj(fis, inputXLFilename);
				
			    //book1 = new XSSFWorkbook(new FileInputStream("Enrollment.xlsx"));

			    for (int i = 7; i > 0; i--) {
				book1.removeSheetAt(i);								
			    }

			    fos = new FileOutputStream(
			    		outputXLFilename, false);
			    book1.write(fos);
			    fos.close();
			} catch (Exception e) {
				//Logs.LOGGER.log(Level.SEVERE, "", e);
				
			}
			/*} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}*/
	  }

	// Get the object based on the excel files
	/**
	 * Gets the workbook obj.
	 *
	 * @param fis
	 *            the fis
	 * @param fileName
	 *            the file name
	 * @return the workbook obj
	 */
	private static Workbook getWorkbookObj(FileInputStream fis, String fileName) {
		try {
			String fileExtensionName = fileName
					.substring(fileName.indexOf("."));
			if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
				return new XSSFWorkbook(fis);
			} else if (fileExtensionName.equalsIgnoreCase(".xls")) {
				return new HSSFWorkbook(fis);
			} else {
				//Logs.LOGGER.severe("Invalid file type.");
				//Logs.LOGGER.severe("We support excel files with extension .xls or .xlsx");
				return null;
			}
		} catch (Exception e) {
			//Logs.LOGGER.log(Level.SEVERE, "", e);
			return null;
		}
	}
	
	
}
