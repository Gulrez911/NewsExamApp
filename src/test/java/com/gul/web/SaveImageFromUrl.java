package com.gul.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class SaveImageFromUrl {

	public static void main(String[] args) throws Exception {
		String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
//		if (imageUrl.endsWith("/")) {
		String sss=  "http://www.avajava.com/images/";
		int length = sss.length();
			System.out.println(imageUrl.substring(length));
			
			String ffff = "https://wallpapers.com/images/file/3840x1080-4k-girl-and-cat-amrh7mrluqxfz7if.jpg";
			isURL(ffff);
//		}
//		String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
//		String destinationFile = "H://Downloaded Images/image.jpg";
//
//		saveImage(imageUrl, destinationFile);
	}

//	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
//		URL url = new URL(imageUrl);
//		InputStream is = url.openStream();
//		OutputStream os = new FileOutputStream(destinationFile);
//
//		byte[] b = new byte[2048];
//		int length;
//
//		while ((length = is.read(b)) != -1) {
//			os.write(b, 0, length);
//		}
//
//		is.close();
//		os.close();
//	}

	
	public static boolean isURL(String url) {
		  try {
		     (new java.net.URL(url)).openStream().close();
		     return true;
		  } catch (Exception ex) { }
		  return false;
		}
	
}