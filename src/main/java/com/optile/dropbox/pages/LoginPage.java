package com.optile.dropbox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

	private By emailTextBoxLocator=By.xpath("//div[@class='credentials-form__fields']/div/div[2]/input[@type='email']");
	private By passwordTextBoxLocator=By.xpath("//div[@class='credentials-form__fields']/div/div[2]/input[@type='password']");
	private By signInLinkLocator=By.linkText("Sign in");
	
	
	public LoginPage(WebDriver driver){
		super(driver);
	}
	
	public void siginIntoDropBox(String email,String password){
		
		switchToActivePopupOrFrame();
		setEmail(email);
		setPassword(password);
		clickOnSignInButton();
	}
	
	public void clickOnSignInLink(){
		driver.findElement(signInLinkLocator).click();
	}
	
	private void setEmail(String email){
		driver.findElement(emailTextBoxLocator).sendKeys(email);
	}
	
	private void setPassword(String password){
		driver.findElement(passwordTextBoxLocator).sendKeys(password);
		
	}
	
	
	private void clickOnSignInButton(){
		//driver.findElement(signinButtonLocator).click();
		driver.findElement(passwordTextBoxLocator).submit();
	}
}
