package com.optile.dropbox.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

	private By accountNameLocator=By.xpath("//div[@id='account-header']/ul/li[3]/span/button");
	private By signoutLinkLocator=By.linkText("Sign out");
	
	private By newFolderLinkLocator=By.xpath("//a[@id='new_folder_button']/img");
	private By uploadLinkLocator=By.xpath("//a[@id='upload_button']/img");
	private By deleteLinkLocator=By.id("delete_action_button");
	private By showDeletedFilesLinkLocator=By.id("show_del_button");
	
	private By foldersLocator=By.xpath("//ol[@id='browse-files']/li/div/a");
	private By folderLocator=By.xpath("//ol/li");
	
	private By newFolderTextBoxLocator=By.xpath("//*[@id='null']/input");
	private By searchTextBoxLocator=By.xpath("//form/div/div[2]/input");

	private By deleteButtonLocator=By.xpath("//button[@class='button-primary dbmodal-button']");
	
	public HomePage(WebDriver driver){
		super(driver);
	}
	
	public void logout(){
		
		clickOnAccountName();
		clickOnSignOut();
	}
	
	public void createFolder(String folderName){
		
		clickOnNewFolderLink();
		enterNameForNewFolder(folderName);
	}
	
	public void uploadFile(String fileLocation){
		clickOnUploadLink();
	}
	
	public boolean checkFolderIsCreated(String folderName){
		List <String>allFolders=getAllFoldersName();
		if(allFolders.contains(folderName))
			return true;
		else
			return false;
	}
	
	public void clickOnShowDeletedFilesLink(){
		driver.findElement(showDeletedFilesLinkLocator).click();
	}
	
	public List<String> getAllFoldersName(){
		List <String> foldersName=new ArrayList<String>();
		for (WebElement element :driver.findElements(foldersLocator)){
			foldersName.add(element.getText());
		}
		return foldersName;
	}
	
	public void searchForFolderOrFile(String name){
		waitForWebElementToBeClickable(searchTextBoxLocator);
		driver.findElement(searchTextBoxLocator).sendKeys(name);
		driver.findElement(searchTextBoxLocator).sendKeys(Keys.ENTER);
	}
	
	public void deleteFolderOrFile(){
		
		selectFolderOrFile();
		selectDeleteLink();
		driver.switchTo().activeElement();
		selectDeleteButton();
	}
	
	public void selectDeleteLink(){
		driver.findElement(deleteLinkLocator).click();
	}
	
	private void clickOnAccountName(){
		
		waitForWebElementToBeVisible(accountNameLocator);
		driver.findElement(accountNameLocator).click();
	}
	
	private void clickOnSignOut(){
		driver.findElement(signoutLinkLocator).click();
	}
	
	private void clickOnNewFolderLink(){
		waitForWebElementToBeVisible(newFolderLinkLocator);
		driver.findElement(newFolderLinkLocator).click();
	}
	
	private void clickOnUploadLink(){
		driver.findElement(uploadLinkLocator).click();
	}
	
	private void enterNameForNewFolder(String folderName){
		driver.findElement(newFolderTextBoxLocator).sendKeys(folderName);
		driver.findElement(newFolderTextBoxLocator).sendKeys(Keys.ENTER);
	}
	
	private void selectFolderOrFile(){
		driver.findElement(folderLocator).click();
	}
	
	private void selectDeleteButton(){
		driver.findElement(deleteButtonLocator).click();
	}
	
	
	
}
