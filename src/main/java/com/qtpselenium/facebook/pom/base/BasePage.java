package com.qtpselenium.facebook.pom.base;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qtpselenium.facebook.pom.pages.common.TopMenu;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BasePage {
	
	public WebDriver driver;
	public ExtentTest test;
	public TopMenu menu;
	
	public BasePage() {}
	
	public BasePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		menu = new TopMenu(driver, test);
		PageFactory.initElements(driver, menu);
	}
	
	public String verifyTitle(String expTitle) {
		test.log(LogStatus.INFO, "Verifying title - " + expTitle);
		return "";
	}
	
	public String verifyString(String locator, String expText) {
		return "";
	}
	
	public boolean isElementPresent(String xpathKey) {
        try {
        	test.log(LogStatus.INFO, "Trying to find the element -> " + xpathKey);
            int count = driver.findElements(By.xpath(xpathKey)).size();
            if (count == 0)
                return false;
            else
                return true;

        } catch (Exception e) {
        	test.log(LogStatus.FAIL, "Unable to check the presence of the element " + xpathKey);
        	e.printStackTrace();
            return false;
        }
    }
	
	public TopMenu getMenu() {
		return menu;
	}
	
	public void takeScreenshot() {
		Date d = new Date();
		String screenshotPath = System.getProperty("user.dir")+"\\Screenshots\\"+d.toString().replace(":", "_").replace(" ", "_")+".png";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		test.log(LogStatus.INFO, test.addScreenCapture(screenshotPath));
	}
	
    public void reportFailure(String failureMsg) {
        test.log(LogStatus.FAIL, failureMsg);
        takeScreenshot();
        Assert.fail(failureMsg);
    }

    public void reportPass(String passMsg) {
        test.log(LogStatus.PASS, passMsg);
        takeScreenshot();
    }
    
}
