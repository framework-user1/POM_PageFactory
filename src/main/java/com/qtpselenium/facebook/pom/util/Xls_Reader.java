package com.qtpselenium.facebook.pom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Check if a sheet exits in a workbook
// Check if a column exists in a sheet
// Get the no. of rows in a sheet
// Get the no. of cols in a sheet
// Read data from a specific row
// Get cell data based on column index
// Get cell data based on column name
// Get row index in based on cell value
// Read all the data from excel and return object array of hashtables 
// Set cell data based on column index
// Set cell data based on column name

public class Xls_Reader {
	
	public String filePath;
	public File file;
	public FileInputStream finput;
	public XSSFWorkbook workbook;
	public XSSFSheet worksheet;
	
	public Xls_Reader(String filePath) {
		
		this.filePath=filePath;
		
		try {
			
			file = new File(filePath);
			finput = new FileInputStream(file);
			workbook = new XSSFWorkbook(finput);
			finput.close();
			
		} catch(Exception e) {
			
			System.out.println("Error reading from file!!!");
			e.getMessage();
			
		}
	}

	// Set cell data based on column name
	public boolean setCellData(String sheetName, int rowNum, String colName, String cellVal) {
		
		int colNum = -1;
		
		try {
			
			finput = new FileInputStream(file);
			workbook = new XSSFWorkbook(finput);
			worksheet = workbook.getSheet(sheetName);
			int noOfCols = getNoOfCols(sheetName);
			
			if(noOfCols==0) {
				return false;
			} 
			
			colNum = getColIndex(sheetName,colName);
			
			if(colNum == -1) {
				System.out.println("Invalid column index!!");
				return false;
			}
			
			Row currRow = worksheet.getRow(rowNum-1);

			if(currRow==null) {
				System.out.println("Invalid row index");
				return false;
			}
			
			// Cell style
		    CellStyle cs = workbook.createCellStyle();
		    cs.setWrapText(true);

		    Row row = worksheet.getRow(rowNum-1);
			Cell cell = row.createCell(colNum);
			cell.setCellStyle(cs);
			cell.setCellValue(cellVal);
			
			FileOutputStream fout = new FileOutputStream(filePath);
			workbook.write(fout);
	
			workbook.close();
			fout.close();
			System.out.println("File writted successfully!!");	
			
		} catch(Exception e) {
			System.out.println("Error reading from file!!!");
			e.getMessage();
		}
		
		return true;
	}
		
	// Set cell data based on column index
	public boolean setCellData(String sheetName, int rowNum, int colIndex, String cellVal) {

		try {

			finput = new FileInputStream(file);
			workbook = new XSSFWorkbook(finput);
			worksheet = workbook.getSheet(sheetName);
			int noOfCols = getNoOfCols(sheetName);
			
			if(noOfCols==0) {
				return false;
			} else if(colIndex > (noOfCols-1)) {
				System.out.println("Invalid column index!!");
				return false;
			}
			
			Row currRow = worksheet.getRow(rowNum-1);

			if(currRow==null) {
				System.out.println("Invalid row index");
				return false;
			}
			
			// Cell style
		    CellStyle cs = workbook.createCellStyle();
		    cs.setWrapText(true);
		    
		    Row row = worksheet.getRow(rowNum-1);
			Cell cell = row.createCell(colIndex);
		    cell.setCellStyle(cs);
		    cell.setCellValue(cellVal);
			
			FileOutputStream fout = new FileOutputStream(filePath);
			workbook.write(fout);
	
			workbook.close();
			System.out.println("File writted successfully!!");	
			
		} catch(Exception e) {
			System.out.println("Error reading from file!!!");
			e.getMessage();
		}
		
		return true;
	}	
	
	// Check if a sheet exits in a workbook
	public boolean checkSheetExists(String sheetName) {
		
		try {	
			Iterator<Sheet> sheets = workbook.sheetIterator();
			
			while(sheets.hasNext()) {
			
				Sheet currentSheet = sheets.next();
				String currentSheetName = currentSheet.getSheetName();
									
				if(currentSheetName.equalsIgnoreCase(sheetName)) {
					return true;
				}
			}
			
		} catch(Exception e) {
			
			System.out.println("Error reading the file");
			e.getMessage();
			return false;
			
		} 
	
		return false;
	}
		
	// Check the no. of rows in a sheet
	public int getNoOfRows(String sheetName) {
	
		if(!checkSheetExists(sheetName)) {
			System.out.println("Sheet does not exist!!");
			return 0;
		}
		
		int rowCount;
		
		try{	
			
			worksheet = workbook.getSheet(sheetName);
			rowCount = worksheet.getLastRowNum();
			
		} catch(Exception e) {
			
			System.out.println("Worksheet does not exist!!");
			e.getMessage();
			return 0;
			
		}
			return rowCount+1;
	}
	
	// Get no of columns in a sheet
	public int getNoOfCols(String sheetName) {

		if(!checkSheetExists(sheetName)) {
			System.out.println("Sheet does not exist!!");
			return 0;
		}
		
		int colCount = 0;
		
		try {
			
			worksheet = workbook.getSheet(sheetName);
			Iterator<Row> rows = worksheet.rowIterator();

			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			
			while(cells.hasNext()) {
				cells.next();
				colCount++;
			} 
		
		} catch(Exception e) {
			System.out.println("No columns in the sheet!!");
			e.getMessage();
			return 0;
		}

		return colCount;
	}
	
		
	// Read data from a specific row
	public String[] getRowAtIndex(String sheetName, int getRowNum) {

		int currRowNum=1;
		boolean rowFound=false;
		String[] rowArr=null;
		
		try {

			 worksheet = workbook.getSheet(sheetName);
		
			int noOfCols = getNoOfCols(sheetName);
			rowArr = new String[noOfCols];  
			
			Iterator<Row> rows = worksheet.rowIterator();
		
			while(rows.hasNext()) {
										
				Row row = rows.next();
		
				if(currRowNum == getRowNum) {
					
					rowFound = true;	
						
					Iterator<Cell> cells = row.cellIterator();
					int arrIndex = 0;
					
					while(cells.hasNext()) {
						
						Cell cell = cells.next();
						
						switch(cell.getCellType()) {
						
							case Cell.CELL_TYPE_NUMERIC : {
								if(HSSFDateUtil.isCellDateFormatted(cell)) {
									String cellText  = String.valueOf(cell.getNumericCellValue());
								    // format in form of M/D/YY
									double d = cell.getNumericCellValue();

									Calendar cal = Calendar.getInstance();
									cal.setTime(HSSFDateUtil.getJavaDate(d));
								    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
								    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cellText;
								    
								    rowArr[arrIndex]=cellText;
									arrIndex++;
								    break;
								  }
								
								rowArr[arrIndex]=String.valueOf(cell.getNumericCellValue());
								arrIndex++;
								break;
							}
							
							case Cell.CELL_TYPE_STRING : {
								rowArr[arrIndex]=cell.getStringCellValue();
								arrIndex++;
								break;
							}
							
							case Cell.CELL_TYPE_BOOLEAN : {
								rowArr[arrIndex]=String.valueOf(cell.getBooleanCellValue());
								arrIndex++;
								break;
							}
							
							case Cell.CELL_TYPE_BLANK : {
								rowArr[arrIndex]="";
								arrIndex++;
								break;
							}
						}
					}
	
				break;
			} 
				currRowNum++;
		} 
			
		if(rowFound==false) {
			System.out.println("Row number exceeded the no. of rows in the data sheet");
		}
			
			workbook.close();
			
		} catch(Exception e) {
			System.out.println("");
		}
	
		return rowArr;
	}
	
	// Check for a column in a sheet
	public boolean checkColExists(String sheetName, String colName) {
		
		String currColName;
		boolean colExists = false;

		try {
			worksheet = workbook.getSheet(sheetName);
			Iterator<Row> rows = worksheet.rowIterator();

			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			
			while(cells.hasNext()) {
				
				Cell cell = cells.next();
				currColName = cell.getStringCellValue();
				
				if(currColName.equalsIgnoreCase(colName)) {
					colExists=true;
				}
			} 
		
		} catch(Exception e) {
			System.out.println("No columns in the sheet!!");
			e.getMessage();
			return false;
		}

		return colExists;
	}
	
	// Check for a column index in a sheet
	public int getColIndex(String sheetName, String colName) {
	
		int colNum=0;
		String currColName;
				
		try {
			worksheet = workbook.getSheet(sheetName);
			int noOfCols = getNoOfCols(sheetName);
					
			if(noOfCols==0) {
				return -1;
			}
			
			Row currRow = worksheet.getRow(0);
			
			for(colNum=0; colNum<noOfCols; colNum++) {
				currColName = currRow.getCell(colNum).getStringCellValue();
				
				if(currColName.equalsIgnoreCase(colName)) {
					return colNum;
				}
			}
			
			if(colNum == noOfCols) {
				System.out.println("Invalid column index!!");
				return -1;
			}
		
		} catch(Exception e) {
			System.out.println("Error reading from file!!!");
			e.getMessage();
	}
		return -1;	
}
	
	// Get cell data based on column index
	public String getCellData(String sheetName, int colIndex, int rowNum) {
		
		try {
			worksheet = workbook.getSheet(sheetName);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			
			int index = workbook.getSheetIndex(sheetName);
			
			if(index==-1)
				return "";
			
			if(rowNum<=0)
				return "";
			
			Row currRow = worksheet.getRow(rowNum-1);
			
			if(currRow==null) 
				return "";
			
			Cell cellData = currRow.getCell(colIndex);
			
			if(cellData==null)
				return "";
				
			switch(evaluator.evaluateInCell(cellData).getCellType()) {
			
			case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cellData)) {

				String cellText  = String.valueOf(cellData.getNumericCellValue());
			    // format in form of M/D/YY
				double d = cellData.getNumericCellValue();

				Calendar cal = Calendar.getInstance();
				cal.setTime(HSSFDateUtil.getJavaDate(d));
			    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
			    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cellText;
			    return cellText;
			   }
				return String.valueOf(cellData.getNumericCellValue());
				
			case Cell.CELL_TYPE_STRING: 
				return cellData.getStringCellValue();
				
			case Cell.CELL_TYPE_BOOLEAN: 
				return String.valueOf(cellData.getBooleanCellValue());
				
			case Cell.CELL_TYPE_BLANK: 
				return "";
		} 
		
	} catch(Exception e) {
		System.out.println("Error reading from file!!!");
		e.getMessage();
	}
		return "";	
}

	// Get cell data based on column name
	public String getCellData(String sheetName, String colName, int rowNum) {
		
		int colNum = -1;
			
		try {
			
			worksheet = workbook.getSheet(sheetName);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int noOfCols = getNoOfCols(sheetName);
					
			if(noOfCols==0) {
				return "";
			}
			
			colNum = getColIndex(sheetName,colName);
			
			if(colNum == -1) {
				System.out.println("Invalid column index!!");
				return "";
			}
			
			Row currRow = worksheet.getRow(rowNum-1);
			
			if(currRow==null) {
				System.out.println("Invalid row index");
				return "";
			}
			
			Cell cellData = currRow.getCell(colNum);
			
			if(cellData==null)
				return "";
				
			switch(evaluator.evaluateInCell(cellData).getCellType()) {
			
			case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cellData)) {

				String cellText  = String.valueOf(cellData.getNumericCellValue());
			    // format in form of M/D/YY
				double d = cellData.getNumericCellValue();

				Calendar cal = Calendar.getInstance();
				cal.setTime(HSSFDateUtil.getJavaDate(d));
			    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
			    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cellText;
			    return cellText;
			   }
				return String.valueOf(cellData.getNumericCellValue());
				
			case Cell.CELL_TYPE_STRING: 
				return cellData.getStringCellValue();
				
			case Cell.CELL_TYPE_BOOLEAN: 
				return String.valueOf(cellData.getBooleanCellValue());
				
			case Cell.CELL_TYPE_BLANK: 
				return "";
		}  
		
	} catch(Exception e) {
		System.out.println("Error reading from file!!!");
		e.getMessage();
	}
		return "";	
  }	
	
	// Get row index in based on cell value
	public int getRowIndex(String sheetName, String colName, String cellContent) {
		
	try {
		int colIndex = getColIndex(sheetName, colName);
	
		if(colIndex == -1) {
			return -1;
		}
		
		for(int rowNum=1; rowNum<=getNoOfRows(sheetName); rowNum++) {
			String currCellContent = getCellData(sheetName, colName, rowNum);
		
			if(currCellContent.equalsIgnoreCase(cellContent)) {
				return rowNum;
			}
		}
	} catch(Exception e) {
	
		System.out.println("Error reading from the sheet!!");
		e.getMessage();
	}
		System.out.println("Data not found in the sheet!!");
		return -1;
	}	
}

