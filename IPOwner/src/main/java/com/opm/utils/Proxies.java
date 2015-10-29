package com.opm.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Proxies {
	
	public static List<Proxy> list= new ArrayList<Proxy>();
	static String myProxiesPath = System.getProperty("user.dir") + "/resources/proxy";
	
	public static void doGenerateAllZip() throws Exception{
		
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(myProxiesPath));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] tmp = sCurrentLine.split(":");
				Proxy p = new Proxy(tmp[0],tmp[1],tmp[2],tmp[3]);
				p.generateZip();
				list.add(p);	
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}		
}
