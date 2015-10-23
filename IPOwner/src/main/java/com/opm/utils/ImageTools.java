package com.opm.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.Exception;
import com.DeathByCaptcha.SocketClient;

public class ImageTools {

	public static void getImage(WebDriver driver, By selector, String ResultPath, String Name){
		
		boolean scroll =  false;
		File res_image=new File(ResultPath+Name+".png");
		try {
			 
	 		/**
	 		 * select images
	 		 */
	 		WebElement Image = driver.findElements(selector).get(0);
	        
	 		/**
	 		 * Focus on the image & scroll down if necessary
	 		 */
	 		if(driver.manage().window().getSize().height < Image.getLocation().getY() + Image.getSize().getHeight()) { // si image n 'est pas cadr� totalement
	 			new Actions(driver).moveToElement(Image, Image.getSize().getWidth(),Image.getSize().getHeight()).perform();
	 			scroll =  true;
	 		}
	 		/**
	 		 * get a screen shot  
	 		 */ 		
			File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			BufferedImage tmp = ImageIO.read(screen), dest= null;
	        /***
	         * get the captcha image from the screen shot we took
	         */
	        if(scroll)
	        	dest=tmp.getSubimage (  Image.getLocation().getX(),
	        							tmp.getHeight()-Image.getSize().getHeight(),
	        							Image.getSize().getWidth(),
	        							Image.getSize().getHeight());
	        else 
	        	dest=tmp.getSubimage (Image.getLocation().getX(),Image.getLocation().getY(), Image.getSize().getWidth(),Image.getSize().getHeight());
	        /** 
	         * save result & copy to disk
	         **/
	        ImageIO.write(dest,"png", screen);
	        FileUtils.copyFile(screen,res_image);
	        System.out.println("Image saved "+res_image.getAbsolutePath());
	         
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void getImageSupprot(WebDriver driver, By selector, String ResultPath, String Name){
		
		boolean scroll =  false;
		File res_image=new File(ResultPath+Name+".png");
		try {
			 
	 		/**
	 		 * select images
	 		 */
	 		WebElement Image = driver.findElements(selector).get(0);
	        
	 		/**
	 		 * Focus on the image & scroll down if necessary
	 		 */
	 		if(driver.manage().window().getSize().height < Image.getLocation().getY() + Image.getSize().getHeight()) { // si image n 'est pas cadr� totalement
	 			new Actions(driver).moveToElement(Image, Image.getSize().getWidth(),Image.getSize().getHeight()).perform();
	 			scroll =  true;
	 		}
	 		/**
	 		 * get a screen shot  
	 		 */ 		
			File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			BufferedImage tmp = ImageIO.read(screen), dest= null;
	        /***
	         * get the captcha image from the screen shot we took
	         */
	        if(scroll)
	        	dest=tmp.getSubimage (  Image.getLocation().getX(),
	        							tmp.getHeight()-Image.getSize().getHeight()-15,
	        							Image.getSize().getWidth(),
	        							Image.getSize().getHeight());
	        else 
	        	dest=tmp.getSubimage (Image.getLocation().getX(),Image.getLocation().getY(), Image.getSize().getWidth(),Image.getSize().getHeight());
	        /** 
	         * save result & copy to disk
	         **/
	        ImageIO.write(dest,"png", screen);
	        FileUtils.copyFile(screen,res_image);
	        System.out.println("Image saved "+res_image.getAbsolutePath());
	         
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unused")
	public static void getImage(String url ,String dest){
		
		File screen = new File(dest+".png");
		BufferedImage buffer=null;
		try {			
			URL url1 = new URL("url");
			buffer= ImageIO.read(url1);			
			ImageIO.write(buffer,"png", screen);
			File res_image=new File(dest+".png");
			//FileUtils.copyFile(screen, res_image);
		} catch (IOException e) {	
			e.printStackTrace();
		}				
	}
	public static void checkSolde(){
		Client client = (Client)new SocketClient("amiken22", "aminegmvl01");
		  double balance;
		try {
			balance = client.getBalance();
			System.out.println(balance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static String getCaptcha(String CaptchaImage) throws IOException, Exception, InterruptedException{
		
		 Client client = (Client)new SocketClient("amiken22", "aminegmvl01");
		    try {
		         double balance = client.getBalance();
		         System.out.println(balance);
		         Captcha captcha = client.decode(CaptchaImage, 45);		         
		         if (null != captcha) {
		             //System.out.println("CAPTCHA " + captcha.id + " solved: " + captcha.text);
		             return captcha.text;
		         }
		     } catch (IOException | Exception | InterruptedException e) {
		    	 e.printStackTrace();
		     }
		return null;
	}

	public static Captcha getCaptchaResponse(String CaptchaImage) throws IOException, Exception, InterruptedException{

		Client client = (Client)new SocketClient("amiken22", "aminegmvl01");
		    try {
		         double balance = client.getBalance();
		         System.out.println(balance);
		         Captcha captcha = client.decode(CaptchaImage, 45);		         
		         if (null != captcha) {		            
		             return captcha;
		         }
		     } catch (IOException | Exception | InterruptedException e) {
		    	 e.printStackTrace();
		     }
		return null;
	}
	
	public static void report(Captcha captcha){
		Client client = (Client)new SocketClient("amiken22", "aminegmvl01");
		
			try {
				client.report(captcha);
			} catch (IOException e) {				
				e.printStackTrace();
			} catch (Exception e) {			
				e.printStackTrace();
			}
		
	}
	
}
