package com.qtpselenium.facebook.pom.pages.session.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qtpselenium.facebook.pom.base.BasePage;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GeneralSettingsPage extends BasePage {

	@FindBy(xpath = FBConstants.PASSWORD_CHANGE)
	public WebElement password_change;
	
	@FindBy(xpath = FBConstants.OLD_PASSWORD)
	public WebElement old_password;
	
	@FindBy(xpath = FBConstants.NEW_PASSWORD)
	public WebElement new_password;
	
	@FindBy(xpath = FBConstants.CONFIRM_NEW_PASSWORD)
	public WebElement confirm_new_password;
	
	@FindBy(xpath = FBConstants.CONFIRM_PASSWORD_CHANGE)
	public WebElement confirm_password_change;

	@FindBy(xpath = FBConstants.PASSWORD_CHANGED)
	public WebElement password_changed;
	
	@FindBy(xpath = FBConstants.KILL_SESSIONS)
	public WebElement kill_sessions;
	
	@FindBy(xpath = FBConstants.CONTINUE_PASSWORD_CHANGE)
	public WebElement continue_password_change;
	
	public GeneralSettingsPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
	}

	public void gotoPasswordChange() {
		if(!isElementPresent(FBConstants.PASSWORD_CHANGE)) 
			reportFailure("Password Change link is not present");
		else
			test.log(LogStatus.INFO, "Clicking on Password Change link");
			password_change.click();
	}

	public String doPasswordChange(String oPwd, String nPwd) {
		
		test.log(LogStatus.INFO, "Performing password change");
		
		old_password.sendKeys(oPwd);
		new_password.sendKeys(nPwd);
		confirm_new_password.sendKeys(nPwd);
		confirm_password_change.click();
		
		if(!isElementPresent(FBConstants.PASSWORD_CHANGED))
			return "Unsuccessful";
		
		test.log(LogStatus.INFO, "Selecting logout of all devices");
		kill_sessions.click();
		continue_password_change.click();

		test.log(LogStatus.INFO, "Password change successful");
		return "Successful";
	}

}
