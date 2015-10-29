package com.opm.utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

public class DriverSetting {


	public static int i;
	public static String ChromedriverMac =  "/usr/local/bin/chromedriver";
	public static String Chromedriver =  "/usr/local/bin/chromedriver";
	static {
		String os  = System.getProperty("os.name");
		if(os.contains("Windows"))
			Chromedriver =  "resources/chromedriver.exe";
		if(os.contains("mac os"))
			Chromedriver =  "/usr/local/bin/chromedriver";
		if(os.contains("linux"))
			Chromedriver =  "/usr/local/bin/chromedriver";
		
	}
	
	public static WebDriver withProxy() {
		
		i++;
		if(i==Proxies.list.size())
			i=0;
		WebDriver driver =null;
		boolean proxyBlocked=false;
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(Chromedriver))
				.usingAnyFreePort().withEnvironment(ImmutableMap.of("DISPLAY", ":1.0")).build();
		DesiredCapabilities capabilities;	
		ChromeOptions options ;
		try {
			capabilities = DesiredCapabilities.chrome();
			options =  new ChromeOptions();
			options.addExtensions(new File(Proxies.list.get(i).getPath()));
	    	capabilities.setCapability(ChromeOptions.CAPABILITY,options );    
			
	 		driver = new ChromeDriver(service,capabilities);
	 		driver.manage().deleteAllCookies();
	 		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	 		driver.manage().window();//.setPosition(new Point(1200, 1000));
	 		driver.manage().window().setSize(new Dimension(1248, 800)); 		 		 		
	 		try {
				if(driver.findElement(By.id("main-frame-error"))!=null){									
					proxyBlocked=true;
					LogWriter.write(LogFiles.BadProxy.name(), Proxies.list.get(i).toString());
				}
				
			} catch (Exception e) {
				LogWriter.write(LogFiles.GoodProxy.name(), Proxies.list.get(i).toString());
			}
	 		
		} catch (Exception e) {
			e.printStackTrace();
			driver=null;
		}	
	
 		System.out.println(proxyBlocked);
 		int j=0;
 		while(proxyBlocked){
 			i++;j++;
 			if(i==Proxies.list.size())
				i=0;
 			if(driver!=null)
 				driver.quit();
 			
 			capabilities = DesiredCapabilities.chrome();
			options =  new ChromeOptions();
    		options.addExtensions(new File(Proxies.list.get(i).getPath()));
        	capabilities.setCapability(ChromeOptions.CAPABILITY,options );		
	 		driver = new ChromeDriver(service,capabilities);
	 		driver.manage().window().setSize(new Dimension(1248, 800));
	 		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	 		driver.manage().deleteAllCookies();
	 		try {
				if(driver.findElement(By.id("main-frame-error"))!=null){										
					proxyBlocked=true;
					LogWriter.write(LogFiles.BadProxy.name(), Proxies.list.get(i).toString());
				}
				
			} catch (Exception e) {
				LogWriter.write(LogFiles.MyExecptions.name(),"\n\n\n"+e.getMessage()+"\n\n\n");
				proxyBlocked=false;
				LogWriter.write(LogFiles.GoodProxy.name(), Proxies.list.get(i).toString());
			}
	 		if(j==Proxies.list.size()){	 			
	 			LogWriter.write(LogFiles.LimetedProxy.name(), "*** ALL PROXY ARE LIMITED ***");
	 			System.exit(0);
	 		}
 		}
 		
 		try {
 			System.out.println("Testing driver ");
			driver.get("http://checkip.amazonaws.com");
		} catch (Exception e) {
			System.out.println("Test Failed! Closing driver." + e.getMessage());
			LogWriter.write(LogFiles.BadProxy.name(), " Proxy speed probleme ");
			driver.quit();
			return null;
		} 		 		
		return driver;
	}
	
	public static WebDriver  withoutProxy() {
		try{
		WebDriver driver;
			ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(Chromedriver))
					.usingAnyFreePort().withEnvironment(ImmutableMap.of("DISPLAY", ":1.0")).build();
			driver = new ChromeDriver(service);
	 		driver.manage().window().setSize(new Dimension(1248, 800));  		
	 		return driver;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
}
