package com.gul.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowling {
	public static void main(String[] args) throws IOException {
//		Document doc = Jsoup
//				.connect("https://www.sawaal.com/general-knowledge/biology-questions-and-answers.htm?page=1&sort=")
//				.get();

//		Document doc = Jsoup.connect("https://unsplash.com/collections/8439505/new-year").get();
		Document doc = Jsoup.connect("https://wallpapers.com/animal").get();

		Elements elements = doc.getAllElements();
//		int count = 0;
//		String myStr3 = "Q:";
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("kw-contents")) {
//				System.out.println(".............");
//				Elements imageElement = element.getElementsByTag("img");
////				 String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
//				 String srcValue = imageElement.attr("src");
//				System.out.println(srcValue);
//				System.out.println(".........");
				Elements gggg = element.getElementsByTag("a");
				for (Element ddd : gggg) {

					String xxxx = ddd.attr("href");
//					System.out.println(xxxx); 
					if(xxxx.contains(".html")) {
						
//						System.out.println(xxxx);
						
						Document doc2 = Jsoup.connect(xxxx).get();

						Elements elements222 = doc2.getAllElements();
						for (int j = 0; j < elements222.size(); j++) {
							Element ssssssss = elements222.get(j);
//							System.out.println("/////////////////");
							if (ssssssss.className().equals("img-unit")) {
								Elements hhhh = ssssssss.getElementsByTag("a");
								for (Element ds : hhhh) {

									String d = ds.attr("href");
//									System.out.println(xxxx); 
									String urlweb = "https://wallpapers.com"+d;
									System.out.println("https://wallpapers.com"+d);
									UrlValidator urlValidator = new UrlValidator();
									if(urlValidator.isValid(urlweb)) {
										isURL(urlweb);
									}
								}
							}
						
						}
					}
				}
			}
 
		}

	}
	
	
	public static    boolean isURL(String url) {
		  try {
		     (new java.net.URL(url)).openStream().close();
		     url(url);
		     return true;
		  } catch (Exception ex) { }
		  return false;
		}
	
public static    void  url(String url) throws IOException {
//	String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
//	String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
	String sss=  "https://wallpapers.com/images/file/";
	int length = sss.length();
		System.out.println(url.substring(length));
	String destinationFile = "H://Downloaded Images/"+url.substring(length);

	saveImage(url, destinationFile);
}

	public static   void saveImage(String imageUrl, String destinationFile) throws IOException {
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
