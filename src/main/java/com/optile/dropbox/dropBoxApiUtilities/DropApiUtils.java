package com.optile.dropbox.dropBoxApiUtilities;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;



public class DropApiUtils {

	private static DbxAppInfo AppInfo;
	private static DbxRequestConfig Config;
	private static DbxWebAuthNoRedirect WebAuth;
	
	public static String authenticateApp(){
		 AppInfo = new DbxAppInfo(Constant.APP_KEY, Constant.APP_SECRET);
		 Config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
		 WebAuth = new DbxWebAuthNoRedirect(Config, AppInfo);
		 String authorizeUrl = WebAuth.start();
		 return authorizeUrl;
	}

	public static String getAuthoricationCode(WebDriver driver){
		driver.findElement(By.xpath("//form/button[2]")).click();
		String authorizationCode=driver.findElement(By.xpath("//*[@id='auth-code']/input")).getAttribute("data-token");
		return authorizationCode;
	}
	
	public static DbxAuthFinish finishAuthentication(String authorizationCode) throws DbxException{
		return WebAuth.finish(authorizationCode);
	}
	
	public static DbxClient getDebxClient(DbxAuthFinish authFinish){
		String accessToken = authFinish.accessToken;
		return new DbxClient(Config, accessToken);
	}
	
	
	
	
}
