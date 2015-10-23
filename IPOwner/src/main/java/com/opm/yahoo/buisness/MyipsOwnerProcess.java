package com.opm.yahoo.buisness;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.opm.utils.DriverSetting;
import com.opm.utils.ImageTools;
import com.opm.utils.Proxies;

@Service
public class MyipsOwnerProcess extends  Thread{
	
	WebDriver driver;
	static String CaptchaPath		= System.getProperty("user.dir") + "/Resources";
	public MyipsOwnerProcess() {}
	
	public void doStuff() throws InterruptedException{
		
	}
	
	public boolean login(String email, String passwd){
		
		try{
			driver.get("http://myip.ms/info/memberarea/");
			Thread.sleep(5000);
			WebElement mailInput = driver.findElement(By.id("email"));
			WebElement passwdInput = driver.findElement(By.id("password"));
			mailInput.clear();
			passwdInput.clear();
			mailInput.sendKeys(email);
			passwdInput.sendKeys(passwd);
			
			
			try{
				if(driver.findElements(By.cssSelector("img[id^=code_image]")).size() > 0){
				/*** Begin Captcha ***/	
				Thread.sleep(3000);
				ImageTools.getImageSupprot(driver,By.cssSelector("img[id^=code_image]") ,CaptchaPath+"/","captcha");
				String captchResult = ImageTools.getCaptcha(CaptchaPath+"/captcha.png");
				WebElement captcha = driver.findElement(By.cssSelector("input[id^=code_image]"));
				captcha.clear();
				captcha.sendKeys(captchResult + "");
				}
				/** captcha image deleted **/
				/*** End Captcha ***/
			}catch(Exception e){
				System.out.println("no captcha !!");
			}
			Thread.sleep(2000);				
			driver.findElement(By.id("loginbtn")).click();
			/*******************/
		}catch(Exception e){
			
		}
			return false;
	}
	
	@Override
	public void run(){
		
		try{
			/**
			 * Generate proxies extensions
			 ***/
			Proxies.doGenerateAllZip();
			Thread.sleep(1000);
			/**
			 * launch driver
			 ***/
			driver = DriverSetting.withProxy();
			String email[]  = {"tribak.m@gmail.com","godley_05alberto97165@yahoo.com"};
			String pass[] 	= {"rootfcb@1988","rootfcb@1988"};
			
			login(email[1], pass[1]);
			Thread.sleep(15000);
			
			List<String> Res = getOwnerRangeIPs("Hostrocket Web Services");
			for(String td : Res)
				System.out.println(td);
			
		} catch (Exception e) {
			e.printStackTrace();
			if(driver!=null)
				driver.quit();
		}
	}

	public List<String> getOwnerRangeIPs(String nameOwner){
		
		/**
		 * get IP Ranges for each
		 **/
		List<String> RangesIP = new ArrayList<String>();
		try{
			/**
			 * Submit query 
			 **/
			driver.get("http://myip.ms/browse/ip_ranges/1/txt/"+nameOwner);
			Thread.sleep(15000);
			/**
			 * Read Results
			 **/
			List<WebElement> TDs = driver.findElements(By.cssSelector("#ip_ranges_tbl > tbody > tr > td[class=row_name]"));
			for(WebElement elmt : TDs){
				RangesIP.add(elmt.getText());
			}
			return RangesIP;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String argv[] ){
		new MyipsOwnerProcess().run();
	}
	
}
