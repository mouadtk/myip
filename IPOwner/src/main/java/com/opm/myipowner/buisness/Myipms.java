package com.opm.myipowner.buisness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;
import com.opm.myipowner.service.ServiceMYIPMS;
import com.opm.utils.DriverSetting;
import com.opm.utils.ImageTools;
import com.opm.utils.Proxies;
import com.opm.myipowner.dao.OwnerDAO;

/**
 *
 * @author Moaud-TK
 */

@Service
@Scope("prototype")
public class Myipms extends  Thread{
	
	
	WebDriver driver;
	static String CaptchaPath		= System.getProperty("user.dir") + "/Resources";
	private List<Owner> owners = null;
	private UserMYIPMS user;
	
	private int maxQuery = 50;
		
	public ServiceMYIPMS MYIPMS;
	
	public List<Owner> getOwners() {
		return owners;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}

	public UserMYIPMS getUser() {
		return user;
	}

	public void setUser(UserMYIPMS user) {
		this.user = user;
	}

	public Myipms() {}
	
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
		
		if(this.MYIPMS == null){
			System.out.println("waaalo");
			return;
		}
		try{
			/**
			 * Generate proxies extensions
			 ***/
			Proxies.doGenerateAllZip();
			Thread.sleep(1000);
			/**
			 * launch driver
			 ***/
			driver = DriverSetting.withoutProxy();
			login(user.getUsername(), user.getPassword());
			Thread.sleep(15000);
			int index = 0;
			
			for(int j=0; j<this.owners.size() ;j++){
				
				if(index == maxQuery)break;
				int i =0;
				Map<Integer, String> ranges =  new HashMap<Integer, String>();
				List<String> Res = getOwnerRangeIPs(owners.get(j).getName());		
				for(String rg : Res){ ranges. put(i, rg); i++;}
				owners.get(j).setRange(ranges);
				this.MYIPMS.UpdateOwner(owners.get(j));
				index++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(driver!=null)
				driver.quit();
		}
		driver.quit();
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

}