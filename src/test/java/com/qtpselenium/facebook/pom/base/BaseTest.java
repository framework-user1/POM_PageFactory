package com.qtpselenium.facebook.pom.base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.qtpselenium.facebook.pom.util.ExtentManager;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.qtpselenium.facebook.pom.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {

    public WebDriver driver = null;
    public ExtentTest test;
    public ExtentReports extent = ExtentManager.getInstance();
    public Xls_Reader xls = new Xls_Reader(FBConstants.DATA_XLS_PATH);
    public String gridRun;

    public void init(String browser) {

        //Reading gridRun as either Yes/No
        gridRun = FBConstants.GRID_RUN;

        if (gridRun.equals("No")) {

            test.log(LogStatus.INFO, "Opening the browser - " + browser);

            if (browser.equalsIgnoreCase("Firefox")) {
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("Chrome")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("IE")) {
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
            }

            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            test.log(LogStatus.INFO, "Successfully opened the browser - " + browser);

        } else {

            test.log(LogStatus.INFO, "Opening the browser - " + browser);
            DesiredCapabilities cap = null;

            if (browser.equals("Firefox")) {
                cap = DesiredCapabilities.firefox();
                cap.setBrowserName("firefox");
                cap.setJavascriptEnabled(true);
                cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

            } else if (browser.equals("Chrome")) {

                cap = DesiredCapabilities.chrome();
                cap.setBrowserName("chrome");
                cap.setJavascriptEnabled(true);
                cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
            }

            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                test.log(LogStatus.INFO, "Successfully opened the browser - " + browser);
            } catch (Exception e) {
                test.log(LogStatus.ERROR, "Unable to launch the browser - " + browser);
                e.printStackTrace();
            }
        }
    }

    public void takeScreenshot() {
        Date d = new Date();
        String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + d.toString().replace(":", "_").replace(" ", "_") + ".png";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(LogStatus.INFO, test.addScreenCapture(screenshotPath));
    }

    public void reportFailure(String failureMsg) {
        test.log(LogStatus.FAIL, failureMsg);
        waitForPageToLoad(2);
        takeScreenshot();
        Assert.fail(failureMsg);
    }

    public void reportPass(String passMsg) {
        test.log(LogStatus.PASS, passMsg);
        waitForPageToLoad(2);
        takeScreenshot();
    }

    public void waitForPageToLoad(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}