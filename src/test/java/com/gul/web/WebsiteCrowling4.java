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

public class WebsiteCrowling4 {
	public static void main(String[] args) throws IOException {
 
		Document doc = Jsoup.connect("https://wallpapers.com/bullseye-toy-story").get();

		Elements elements = doc.getAllElements();
 
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
 
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("kw-contents")) {
 
				Elements gggg = element.getElementsByTag("li");
				System.out.println(">>>>");
				for (Element ddd : gggg) {
					Elements ss = ddd.getElementsByTag("a");
					String xxxx = ss.attr("href");
//					System.out.println(xxxx);

					if (xxxx.contains(".html")) {

						System.out.println(xxxx);
						secondURl(xxxx);

					}
				}
			}
		}

	}

	

	public static void secondURl(String url) throws IOException {

		Document doc2 = Jsoup.connect(url).get();

		Elements elements222 = doc2.getAllElements();
		for (int j = 0; j < elements222.size(); j++) {
			Element ssssssss = elements222.get(j);
//			System.out.println("/////////////////");
			if (ssssssss.className().equals("img-unit")) {
				System.out.println("1111111"); 
			 
					
					Elements jj = ssssssss.getElementsByTag("a");
 
						String d = jj.attr("href");
			
				System.out.println(d);
			}
		}

	}
	
 
}
