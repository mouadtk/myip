package com.opm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Files;


public class Proxy {
	
	String IP;
	String Port;
	String username;
	String Passwd;
	String path;
	boolean status;
	
	public Proxy() {}
	
	public Proxy(String iP, String port, String username, String passwd) {
		super();
		IP = iP;
		Port = port;
		this.username = username;
		Passwd = passwd;
		status=true;
	}


	public void generateZip(){
		try {
			
			/***
	         * change values in background.js
	         * proxy details
	         ***/
	        File manifest =  new File("Resources/proxyFolder/manifest.json");
	        File bg =  new File("Resources/proxyFolder/background.js");
	        /***
	         *  create folder with proxyIP
	         **/
	        File directory = new File("Resources/proxyFolder/proxies");
	        if(!directory.exists())	directory.mkdir();
	        
	        FileOutputStream fos = new FileOutputStream("Resources/proxyFolder/proxies/extension_"+IP+".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);	
			
			/**
			 * copiynf files
			 */
	        File tmpfile = new File(directory.getAbsolutePath()+"/"+bg.getName());
	        Files.copy(bg, tmpfile );
	        Files.copy(manifest, new File(directory.getAbsolutePath()+"/"+manifest.getName()));
	        /***/
	        String content = IOUtils.toString(new FileInputStream(tmpfile));
	        content = content.replaceAll("%ip%",IP);
	        content = content.replaceAll("%port%",Port);
	        content = content.replaceAll("%username%",username);
	        content = content.replaceAll("%password%",Passwd);
	        IOUtils.write(content, new FileOutputStream(tmpfile));
	        
	        /**
	         * zipping shit
	         */
			
			String file1Name = "manifest.json";
			String file2Name = "background.js";

			addToZipFile(file1Name, zos, directory.getAbsolutePath());
			addToZipFile(file2Name, zos, directory.getAbsolutePath());
			zos.close();
			fos.close();
			path="Resources/proxyFolder/proxies/extension_"+IP+".zip";

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void addToZipFile(String fileName, ZipOutputStream zos, String path) throws FileNotFoundException, IOException {


		File file = new File(path+"/"+fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}	
}
