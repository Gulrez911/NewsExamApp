package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Bangali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://bengali.news18.com/crime/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("jsx-1522687596")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("jsx-1522687596 blog_list")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("jsx-2668003214 blog_list")) {
								Elements ee4 = ee3.children();
								for (Element ee5 : ee4) {
									if (ee5.className().contains("blog_list_row")) {
										Elements a = ee5.getElementsByTag("a");
										String href = a.attr("href");
										System.out.println(href);
										
										Elements img = ee5.getElementsByTag("img");
										String src = img.attr("data-src");
										if (src.contains("?impolicy")) {
											src = src.substring(0, src.lastIndexOf("?impolicy"));
											 
										} 
										System.out.println(src);
										
										String head = ee5.text();
 										System.out.println(head);
										
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

}
