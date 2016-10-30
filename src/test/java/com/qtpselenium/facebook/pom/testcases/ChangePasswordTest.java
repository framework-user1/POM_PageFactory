package com.qtpselenium.facebook.pom.testcases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.facebook.pom.base.BaseTest;
import com.qtpselenium.facebook.pom.pages.LaunchPage;
import com.qtpselenium.facebook.pom.pages.LoginPage;
import com.qtpselenium.facebook.pom.pages.session.LandingPage;
import com.qtpselenium.facebook.pom.pages.session.settings.GeneralSettingsPage;
import com.qtpselenium.facebook.pom.util.DataUtil;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.qtpselenium.facebook.pom.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ChangePasswordTest extends BaseTest {

    public String testCaseName = "ChangePasswordTest";

    @Test(dataProvider = "getData")
    public void changePasswordTest(Hashtable < String, String > data) {

        test = extent.startTest(testCaseName);

        if (!DataUtil.isTestRunnable(xls, "LoginTest")) {
            test.log(LogStatus.SKIP, "Skipping the test -> " + testCaseName);
            throw new SkipException("Skipping the test -> " + testCaseName);
        } else if(data.get("Runmode").equals("N")) {
            test.log(LogStatus.SKIP, "LoginTest" + " - Skipping the test dataset - " + data.toString());
            throw new SkipException("Skipping the dataset -> " + data.toString());
        }

        test.log(LogStatus.INFO, "Opening Browser");
        init(data.get("Browser"));

        LaunchPage launchPage = new LaunchPage(driver, test);
        PageFactory.initElements(driver, launchPage);

        test.log(LogStatus.INFO, "Logging into Facebook");
        LoginPage loginPage = launchPage.gotoLoginPage();
        
        Object page = loginPage.doLogin(data.get("Username"), data.get("OldPassword"));

        if(page instanceof LoginPage) 
        	reportFailure("LoginTest failed with the data set -> " + data);
        
        LandingPage landingPage = (LandingPage) page;
        landingPage.getMenu().gotoSettings();
        
        GeneralSettingsPage generalSettings = new GeneralSettingsPage(driver, test);
        PageFactory.initElements(driver, generalSettings);
        
        generalSettings.gotoPasswordChange();
        String actualResult = generalSettings.doPasswordChange(data.get("OldPassword"), data.get("NewPassword"));
        
        if(!actualResult.equals(data.get("ExpectedResult")))
        	reportFailure(testCaseName + " failed with the data set -> " + data);
        else
    		reportPass(testCaseName + " passed with the data set -> " + data);
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