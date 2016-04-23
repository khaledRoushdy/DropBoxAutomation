package com.optile.dropboxTask.Tests;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.optile.dropbox.dropBoxApiUtilities.DropApiUtils;
import com.optile.dropbox.pages.HomePage;
import com.optile.dropbox.pages.LoginPage;
import com.optile.dropbox.utilities.Utilities;

import junit.framework.Assert;

public class DropBoxTests {

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	String uploadFileLocation = "/Worter.txt";
	public static final String fileName = "Worter.txt";
	public static final String DropBoX_URL = "https://www.dropbox.com/";

	@BeforeMethod()
	public void setup() {
		driver = new FirefoxDriver();
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@Parameters({ "email", "password" })
	@Test
	public void loginLoginOut(String email, String password) {

		loginToDropBox(DropBoX_URL, email, password);
		homePage.logout();
	}

	@Parameters({ "email", "password" })
	@Test
	public void createNewFolder(String email, String password) {
		Random rng = new Random();
		loginToDropBox(DropBoX_URL, email, password);
		String randomFolderName = Utilities.generateString(rng, "abcdefgh", 6);
		homePage.createFolder(randomFolderName);
		homePage.waiting(5);
		boolean folderIsCreated = homePage.checkFolderIsCreated(randomFolderName);
		Assert.assertTrue("The folder is not created", folderIsCreated);

	}

	@Parameters({ "email", "password" })
	@Test
	public void uploadFile(String email, String password) throws DbxException, IOException {
		String webAuthorize = DropApiUtils.authenticateApp();
		driver.get(webAuthorize);
		loginPage.siginIntoDropBox(email, password);
		loginPage.implicitWait(3);
		String authorizationCode = DropApiUtils.getAuthoricationCode(driver);
		DbxAuthFinish authFinish = DropApiUtils.finishAuthentication(authorizationCode);
		DbxClient client = DropApiUtils.getDebxClient(authFinish);
		Utilities.uploadFile(fileName, uploadFileLocation, client);
	}

	@Parameters({ "email", "password" })
	@Test(dependsOnMethods = { "uploadFile" })
	public void deleteFile(String email, String password) throws InterruptedException {

		loginToDropBox(DropBoX_URL, email, password);
		homePage.waiting(5);
		homePage.searchForFolderOrFile(fileName);
		homePage.implicitWait(3);
		homePage.deleteFolderOrFile();

	}

	@Parameters({ "email", "password" })
	@Test
	public void checkShowDeletedFilesFunction(String email, String password) {

		loginToDropBox(DropBoX_URL, email, password);
		loginPage.implicitWait(5);
		int foldersNumberWithoutDeletedFolders = homePage.getAllFoldersName().size();
		homePage.clickOnShowDeletedFilesLink();
		int foldersNumberWithTheDeletedFolders = homePage.getAllFoldersName().size();
		Assert.assertNotSame("The deleted folders are not shown", foldersNumberWithoutDeletedFolders,
				foldersNumberWithTheDeletedFolders);
	}

	private void loginToDropBox(String URL, String email, String password) {
		driver.get(URL);
		driver.manage().window().maximize();
		loginPage.clickOnSignInLink();
		loginPage.siginIntoDropBox(email, password);
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}
