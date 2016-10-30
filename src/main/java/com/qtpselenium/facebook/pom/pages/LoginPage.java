package com.qtpselenium.facebook.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qtpselenium.facebook.pom.base.BasePage;
import com.qtpselenium.facebook.pom.pages.session.LandingPage;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {

	@FindBy(xpath = FBConstants.LOGIN_USERNAME)
	public WebElement input_xp_email;
	
	@FindBy(xpath = FBConstants.LOGIN_PASSWORD)
	public WebElement input_xp_password;
	
	@FindBy(xpath = FBConstants.LOGIN_BUTTON)
	public WebElement btn_login;
	
	public LoginPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
	}
	
	public Object doLogin(String userName, String password) {
		
		boolean loginSuccess = true;

		test.log(LogStatus.INFO, "Logging in with - " + userName + " - " + password);
		
		input_xp_email.sendKeys(userName);
		input_xp_password.sendKeys(password);
		btn_login.click();
		
		loginSuccess = isElementPresent(FBConstants.PROFILE_LINK);
		
		if(loginSuccess) {
			test.log(LogStatus.INFO, "Login successful with - " + userName + " - " + password);
			LandingPage landingPage = new LandingPage(driver, test);
			PageFactory.initElements(driver, landingPage);
			return landingPage;
		}	
		else {
			test.log(LogStatus.INFO, "Login not successful with - " + userName + " - " + password);
			LoginPage loginPage = new LoginPage(driver, test);
			PageFactory.initElements(driver, loginPage);
			return loginPage;
		}	
	}	
}
