package com.optile.dropbox.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	JavascriptExecutor jse;

	public BasePage(WebDriver driver2) {
		this.driver = driver2;

	}

	public boolean isElementPresent(By locatorKey) {
		try {
			driver.findElement(locatorKey).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public WebDriverWait getWait(WebDriverWait wait) {
		return getWait(wait, 3);
	}

	public WebDriverWait getWait(WebDriverWait wait, int seconds) {
		if (wait == null) {
			wait = new WebDriverWait(driver, seconds);

			return wait;
		} else {
			return wait;
		}
	}

	public void waitForWebElement(By element) {
		waitForWebElement(element, 30);
	}

	public void waitForWebElement(By element, int seconds) {
		wait = getWait(wait, seconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}

	public void waitForWebElements(By element, int seconds) {
		wait = getWait(wait, seconds);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
	}

	public void waitForWebElementToBeVisible(By element) {
		wait = getWait(wait, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public void waitForWebElementToBeClickable(By element) {
		wait = getWait(wait);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement fluentWait(final WebElement ele) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return ele;
			}
		});

		return foo;
	}

	public void waiting(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// eating the exception
		}
	}

	public void scrollDown() {

		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 250);");
	}

	public void scrollUp() {

		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -250);");
	}

	public WebElement scrollToElement(By by) {

		wait = getWait(wait);

		jse = (JavascriptExecutor) driver;
		// presence in DOM
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		WebElement element = driver.findElement(by);
		// scrolling
		jse.executeScript("arguments[0].scrollIntoView(true);", element);

		return element;
	}

	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 3) {
			try {
				driver.findElement(by);
				result = true;
				break;
			} catch (StaleElementReferenceException e) {

			}
			attempts++;
		}
		return result;
	}


	public void implicitWait(long seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public void switchToActivePopupOrFrame(){
		driver.switchTo().activeElement();
	}
	
	
}
