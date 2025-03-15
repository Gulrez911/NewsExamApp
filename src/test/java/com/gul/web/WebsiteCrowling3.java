package com.gul.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowling3 {
	public static void main(String[] args) throws IOException {
 
		  System.setProperty("http.proxyHost", "127.0.0.1");
	        System.setProperty("http.proxyPort", "8182");
		Document doc = Jsoup.connect("https://wallpapers.com/car").get();

		Elements elements = doc.getAllElements();
 
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
 
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("related-kw")) {
//				System.out.println(".............");

				Elements gggg = element.getElementsByTag("li");
				System.out.println(">>>>");
				for (Element ddd : gggg) {
					Elements ss = ddd.getElementsByTag("a");
					String xxxx = ss.attr("href");
					System.out.println(xxxx);

					if (!xxxx.contains(".html")) {

						secondURl(xxxx);
					}
				}

			}
		}
	}

	public static void secondURl(String url) throws IOException {

		Document doc2 = Jsoup.connect(url).get();

		Elements elements2 = doc2.getAllElements();

		System.out.println(elements2.size());
		for (int j = 0; j < elements2.size(); j++) {

			Element element2 = elements2.get(j);
//			System.out.println("  ..........   "+element );

			if (element2.className().equals("kw-contents")) {

				Elements gggg2 = element2.getElementsByTag("a");
				System.out.println(">>>>");
				for (Element ddd2 : gggg2) {
					Elements ss2 = ddd2.getElementsByTag("a");
					String xxxx2 = ss2.attr("href");
						System.out.println(xxxx2);
						
						thirdURl(xxxx2);
//						isURL(xxxx2);
						
//						String urlweb = "https://wallpapers.com"+xxxx2;
//						System.out.println("https://wallpapers.com"+xxxx2);
//						UrlValidator urlValidator = new UrlValidator();
//						if(urlValidator.isValid(urlweb)) {
//							isURL(urlweb);
//						}
				}
			}
		}
	}

	
	public static void thirdURl(String url) throws IOException {
		
		Document doc2 = Jsoup.connect(url).get();

		Elements elements2 = doc2.getAllElements();

		System.out.println(elements2.size());
		for (int j = 0; j < elements2.size(); j++) {

			Element element2 = elements2.get(j);
//			System.out.println("  ..........   "+element );

			if (element2.className().equals("img-unit")) {

				Elements gggg2 = element2.getElementsByTag("a");
				System.out.println(">>>>");
				for (Element ddd2 : gggg2) {
					Elements ss2 = ddd2.getElementsByTag("a");
					String xxxx2 = ss2.attr("href");
						System.out.println(xxxx2);
			 
						String urlweb = "https://wallpapers.com"+xxxx2;
						System.out.println("https://wallpapers.com/images/file/"+xxxx2);
						UrlValidator urlValidator = new UrlValidator();
						if(urlValidator.isValid(urlweb)) {
							isURL(urlweb);
							url(urlweb);
						}
				}
			}
		}
	}
	public static boolean isURL(String url) {
		try {
			(new java.net.URL(url)).openStream().close();
			url(url);
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static void url(String url2) throws IOException {
		
		try {
			URL url = new URL(url2);

			
			
			
			
			
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {// status 200
				Scanner s = new Scanner(url.openStream());
				while (s.hasNextLine()) {
					System.out.println(s.nextLine());
					
					
//					String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
//					String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
						String sss = "https://wallpapers.com/images/file/";
						int length = sss.length();
						System.out.println(url2.substring(length));
						String destinationFile = "H://Downloaded Images/" + url2.substring(length);

						saveImage(url2, destinationFile);
					
					
					
//					s.nextLine();
					break;
				}
			} else if (code == HttpURLConnection.HTTP_NOT_FOUND) {// status 404

				// TODO: url not found
			} else {
				// TODO: other reponse status
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
}
