package com.qtpselenium.facebook.pom.testcases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.facebook.pom.base.BaseTest;
import com.qtpselenium.facebook.pom.pages.LaunchPage;
import com.qtpselenium.facebook.pom.pages.LoginPage;
import com.qtpselenium.facebook.pom.pages.session.LandingPage;
import com.qtpselenium.facebook.pom.pages.session.ProfilePage;
import com.qtpselenium.facebook.pom.util.DataUtil;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.qtpselenium.facebook.pom.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ProfileTest extends BaseTest {
	
    public String testCaseName = "ProfileTest";
    
    @Test(dataProvider = "getData")
    public void profileTest(Hashtable < String, String > data) {
		
		test = extent.startTest(testCaseName);
		
		if (!DataUtil.isTestRunnable(xls, "LoginTest")) {
            test.log(LogStatus.SKIP, "Skipping the test -> " + testCaseName);
            throw new SkipException("Skipping the test -> " + testCaseName);
        } else if(data.get("Runmode").equals("N")) {
            test.log(LogStatus.SKIP, "LoginTest" + " - Skipping the test dataset - " + data.toString());
            throw new SkipException("Skipping the dataset -> " + data.toString());
        }
		
		init(data.get("Browser"));
		
		LaunchPage launchPage = new LaunchPage(driver, test);
		PageFactory.initElements(driver, launchPage);
		
		LoginPage loginPage = launchPage.gotoLoginPage();
		Object page = loginPage.doLogin(data.get("Username"), data.get("Password"));
		
		if(page instanceof LoginPage)
			Assert.fail(testCaseName + " Failed");
		
		LandingPage landingPage = (LandingPage) page;
		
		ProfilePage profilePage = landingPage.gotoProfilePage();
		profilePage.verifyProfile();
		
		String actualResult = "";
		
		if(!profilePage.isElementPresent(FBConstants.PROFILE_NAME))
			actualResult = "Unsuccessful";
        else  
        	actualResult = "Successful";
        
        if(!actualResult.equals(data.get("ExpectedResult")))
        	reportFailure("LoginTest failed with the data set -> " + data);
        else
    		reportPass("LoginTest passed with the data set -> " + data);
	}
	
    @DataProvider
    public Object[][] getData() {
        xls = new Xls_Reader(FBConstants.DATA_XLS_PATH);
        return DataUtil.getTestData(xls, testCaseName);
    }
    
    @AfterMethod
	public void quit() {
		extent.endTest(test);
		extent.flush();
        driver.quit();
	}
}
