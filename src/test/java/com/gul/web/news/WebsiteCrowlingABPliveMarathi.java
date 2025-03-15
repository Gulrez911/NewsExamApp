package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingABPliveMarathi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://marathi.abplive.com/news/india").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("uk-width-3-4")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("uk-grid uk-grid-small")) {
						System.out.println("33");
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							Elements ee4 = ee3.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("other_news")) {

									System.out.println("555555");
									Elements a = ee5.getElementsByTag("a");
									String href = a.attr("href");
									System.out.println(href);

									 

									Elements img = ee5.getElementsByTag("img");
									String src = img.attr("data-src");
									if (src.contains("?impolicy")) {
										src = src.substring(0, src.lastIndexOf("?impolicy"));
										 
									} 
									System.out.println(src);
									
									String ss = ee5.text();
									System.out.println(ss);
									System.out.println();
								}
							}
						}
					}

				}
			}

		}

	}

}
