package com.qtpselenium.facebook.pom.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ReportUtil {
	
	public static String latestExtentReport() {
		
		File latestFile = null;
		   
		try {
			/*Properties CONFIG = new Properties();
	 		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			CONFIG.load(fs);*/
			String dirPath = System.getProperty("user.dir")+FBConstants.EXTENT_REPORTS_PATH;
			
	    	File fl = new File(dirPath);
	        
	    	File[] files = fl.listFiles(new FileFilter() {          
	            public boolean accept(File file) {
	                return file.isFile();
	            }
	        });
	    	
	        long lastMod = Long.MIN_VALUE;
	        
	        for (File file : files) {
	            if (file.lastModified() > lastMod) {
	            	latestFile = file;
	                lastMod = file.lastModified();
	            }
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			return latestFile.toString();
		}
			return latestFile.toString();
	}    
	
	// returns current date and time
	public static String now(String dateFormat) {

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());

	}
		
	// make zip of reports
	public static void zip(String filepath){

		try
	 	{
/*	 		Properties CONFIG = new Properties();
	  		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
	 		CONFIG.load(fs);*/
	 		
	 		File inFolder=new File(filepath);
			System.out.println("Zip Folder -> " + System.getProperty("user.dir")+FBConstants.XSLTZipFolder+"\\"+"XSLT_Reports.zip");
			File outFolder=new File(System.getProperty("user.dir")+FBConstants.XSLTZipFolder+"\\"+"XSLT_Reports"+".zip");
	 		
	 		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
	 		BufferedInputStream in = null;
	 		byte[] data  = new byte[1000];
	 		String files[] = inFolder.list();

	 		for (int i=0; i<files.length; i++)
	 		{
	 			in = new BufferedInputStream(new FileInputStream
	 			(inFolder.getPath() + "/" + files[i]), 1000);  
	 			out.putNextEntry(new ZipEntry(files[i])); 
	 			int count;
	 			while((count = in.read(data,0,1000)) != -1)
	 			{
	 				out.write(data, 0, count);
	 			}
	 			out.closeEntry();
	 		}
	  out.flush();
	  out.close();
	 } catch(Exception e) {
	  e.printStackTrace();
	 } 
  }
		
}
