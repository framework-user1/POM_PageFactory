package com.qtpselenium.facebook.pom.util;

public class FBConstants {

	// Grid
	public static final String GRID_RUN = "No";
		
	// Drivers
	public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe";
	public static final String IE_DRIVER_PATH = System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe";
	
	// Locators
	public static final String LOGIN_USERNAME = "//input[@id='email']";
	public static final String LOGIN_PASSWORD = "//input[@id='pass']";
	public static final String LOGIN_BUTTON = "//label[@id='loginbutton']";
	public static final String PROFILE_LINK = "//div[@id='pagelet_welcome_box']/ul/li[1]/div/a";
	public static final String USER_NAVIGATION_LABEL = "//*[@id='userNavigationLabel']";
	public static final String SETTINGS = "//*[@id='BLUE_BAR_ID_DO_NOT_USE']/div/div/div[1]/div/div/ul/li[11]/a/span/span";
	public static final String PASSWORD_CHANGE = "//h3[text()='Password']";
	public static final String OLD_PASSWORD = "//*[@id='password_old']";
	public static final String NEW_PASSWORD = "//*[@id='password_new']";
	public static final String CONFIRM_NEW_PASSWORD = "//*[@id='password_confirm']";
	public static final String CONFIRM_PASSWORD_CHANGE = "//input[@value='Save Changes']";
	public static final String PASSWORD_CHANGED = "//div[text()='Password Changed']";
	public static final String KILL_SESSIONS = "//input[@value='kill_sessions']";
	public static final String CONTINUE_PASSWORD_CHANGE = "//button[text()='Continue']";
	public static final String PROFILE_NAME = "//*[@id='fb-timeline-cover-name']";
	
	// URLs
	public static final String HOMEPAGE_URL = "http://www.facebook.com";
	public static final String EXTENT_REPORTS_PATH = "\\ExtentReports";

	// Paths
	public static final String DATA_XLS_PATH = System.getProperty("user.dir")+"\\Data\\Data.xlsx";
	public static final String DATA_SHEET_NAME = "Data";
	public static final String TESTCASE_SHEET_NAME = "TestCases";
	public static final String XSLTZipFolder = "\\XSLTZipFolder";
	public static final String XSLT_REPORTS_PATH = "\\XSLT_Reports\\output";
	
	

}
