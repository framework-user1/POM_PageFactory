package com.qtpselenium.facebook.pom.util;

import java.util.Hashtable;

import com.qtpselenium.facebook.pom.base.BaseTest;

public class DataUtil extends BaseTest{
	
	public static Object[][] getTestData(Xls_Reader xls, String testCaseName) {
		
		Hashtable<String, String> htDataSet = null;
		String sheetName = FBConstants.DATA_SHEET_NAME;
		
		int testCaseRow = 1;
		while(!xls.getCellData(sheetName, 0, testCaseRow).equals(testCaseName)){
			testCaseRow++;
		}
		
		int testDataColRow = testCaseRow+1;
		
		int cols = 0;
		while(!xls.getCellData(sheetName, cols, testDataColRow).equals("")){
			cols++;
		}
		
		int testDataRow = testCaseRow+2;
		int testDataSets=0;
		
		while(!xls.getCellData(sheetName, 0, testDataRow+testDataSets).equals("")) {
			testDataSets++;
		}
		
		// Adding data in 2-dim object array
		
		Object[][] dataArr = new Object[testDataSets][1];
				
		for(int rowNum=0; rowNum<testDataSets; rowNum++) {

			htDataSet = new Hashtable<String, String>();
			
			for(int colNum=0; colNum<cols; colNum++) {
				String key = xls.getCellData(sheetName, colNum, testDataColRow);
				String value = xls.getCellData(sheetName, colNum, (rowNum+testDataRow));
				htDataSet.put(key, value);
			}
			
			dataArr[rowNum][0]=htDataSet;
		}
			
		return dataArr;
	}
	
	public static boolean isTestRunnable(Xls_Reader xls, String testCaseName) {

		String testcases_sheet = FBConstants.TESTCASE_SHEET_NAME;
		int testcaseRow;
		
		for(testcaseRow=2; testcaseRow<=xls.getNoOfRows(testcases_sheet); testcaseRow++) {
			
			String test = xls.getCellData(testcases_sheet, "TestName", testcaseRow);
			if(test.equals(testCaseName)) 
				break;
		}
		
		String runmode = xls.getCellData(testcases_sheet, "Runmode", testcaseRow);
		
		if(runmode.equals("Y"))
			return true;
		else
			return false;
	}
}
