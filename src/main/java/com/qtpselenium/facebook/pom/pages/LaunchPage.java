package com.qtpselenium.facebook.pom.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.qtpselenium.facebook.pom.base.BasePage;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LaunchPage extends BasePage {

	public LaunchPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
	}

	public LoginPage gotoLoginPage() {
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		test.log(LogStatus.INFO, "Opening the URL - " + FBConstants.HOMEPAGE_URL);
		driver.get(FBConstants.HOMEPAGE_URL);
		test.log(LogStatus.PASS, "URL opened successfully - " + FBConstants.HOMEPAGE_URL);
		
		LoginPage loginPage = new LoginPage(driver, test);
		PageFactory.initElements(driver, loginPage);

		return loginPage;
	}
}
