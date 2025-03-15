package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaOdia {
	public static void main(String[] args) throws IOException {

		Connection connection = Jsoup.connect("https://odia.oneindia.com/international/");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
//		Document doc = Jsoup.connect("https://odia.oneindia.com/international/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("content clearfix")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {

					if (ee.className().contains("leftpanel")) {
						Elements article = ee.getElementsByTag("article");
						for (Element ee3 : article) {
							Elements ee34 = ee3.children();
							for (Element ee345 : ee34) {
								if (ee345.className().contains("collection-container")) {
									Elements ee4 = ee345.children();
									for (Element ee5 : ee4) {

										if (ee5.className().contains("article-img")) {
											Elements a = ee5.getElementsByTag("a");

											String href = "https://odia.oneindia.com" + a.attr("href");
											System.out.println(href);

											Elements img = ee5.getElementsByTag("img");

											String src = "https://odia.oneindia.com" + img.attr("src");
											System.out.println(src);
//									System.out.println();
										}
										if (ee5.className().contains("collection-heading")) {
											Elements a = ee5.getElementsByTag("a");

											String head = a.text();
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

}
