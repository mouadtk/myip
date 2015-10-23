package com.opm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class LogWriter {

	public static void write(String path, String msg) {
		try{
		String repo="log_reports";
		File dir= new File(repo);
		if(!dir.isDirectory()){
			dir.mkdir();
			dir.canWrite();dir.canExecute();dir.canRead();
		}
		File f = new File(dir.getAbsolutePath()+File.separator+path);
		if(!f.exists())
			f.createNewFile();
		FileWriter	fw = new FileWriter(f.getAbsolutePath(), true);
		
		BufferedWriter   bw = new BufferedWriter(fw);
		   bw.write(msg+"\n");
		   bw.close();
		}catch(Exception e )
		{
			System.out.println(e.getMessage());
		}
		
	}
}