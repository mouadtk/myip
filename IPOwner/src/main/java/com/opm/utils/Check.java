package com.opm.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Check{	
	static String ip="";
	static String[] valideIp= {"197.230.3.94","66.147.230.66"};
	
	public static boolean isEnable() {		
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
			                whatismyip.openStream()));
			ip = in.readLine(); //you get the IP as a String
			System.out.println(ip);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception check ");
			return false;
		}		
		if(compare())
			return true;
		System.out.println(" :p contact IT team to activate your app; or ??");
		return false;
	}
	
	
	private static boolean compare(){
		for (String str : valideIp) {
			if(ip.equals(str))
				return true;
		}
		return false;
	}
}
