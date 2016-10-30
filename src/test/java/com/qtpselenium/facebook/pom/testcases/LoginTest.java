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
import com.qtpselenium.facebook.pom.util.DataUtil;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.qtpselenium.facebook.pom.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends BaseTest {

    public String testCaseName = "LoginTest";

    @Test(dataProvider = "getData")
    public void loginTest(Hashtable < String, String > data) {

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

        // LaunchPage launchPage = PageFactory.initElements(driver, LaunchPage.class);
        LaunchPage launchPage = new LaunchPage(driver, test);
        PageFactory.initElements(driver, launchPage);

        test.log(LogStatus.INFO, "Logging into Facebook");
        LoginPage loginPage = launchPage.gotoLoginPage();

        String actualResult = "";
        
        Object page = loginPage.doLogin(data.get("Username"), data.get("Password"));

        if(page instanceof LoginPage) 
        	actualResult = "Unsuccessful";
        else if (page instanceof LandingPage) 
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