package com.qtpselenium.facebook.pom.pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qtpselenium.facebook.pom.util.FBConstants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TopMenu {
	
	@FindBy(xpath = FBConstants.USER_NAVIGATION_LABEL)
	public WebElement user_navigation_label;
	
	@FindBy(xpath = FBConstants.SETTINGS)
	public WebElement settings;
	
	public WebDriver driver;
	public ExtentTest test;
	
	public TopMenu(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public void logout() {
				
	}
	
	public void gotoSettings() {
		test.log(LogStatus.INFO, "Navigating to settings");
		user_navigation_label.click();
		settings.click();
		test.log(LogStatus.INFO, "Clicked on settings");
	}

}
