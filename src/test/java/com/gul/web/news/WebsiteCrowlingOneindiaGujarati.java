package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaGujarati {
	public static void main(String[] args) throws IOException {

		Connection connection = Jsoup.connect("https://gujarati.oneindia.com/news/jamnagar/");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
		
//		Document doc = Jsoup.connect("https://gujarati.oneindia.com/news/").userAgent(
//				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36")
//				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("oi-cityblock-list")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements ee2 = ee.children();
					for (Element ee3 : ee2) {
						if (ee3.className().equals("clearfix")) {
							Elements ee4 = ee3.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("cityblock-img")) {
									Elements a = ee5.getElementsByTag("a");

									String href = "https://gujarati.oneindia.com" + a.attr("href");
									System.out.println(href);

									Elements img = ee5.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src);

								}

								if (ee5.className().contains("cityblock-desc")) {

									Elements ee55 = ee5.children();
									for (Element ee555 : ee55) {
										if (ee555.className().contains("cityblock-title")) {

											String head = ee555.text();
											System.out.println(head);

										}
										if (ee555.className().contains("cityblock-time")) {
											String date = ee555.text();

											if (date.contains(",")) {

												int index4 = date.indexOf(',');
												date = date.substring(index4 + 1, date.lastIndexOf("[IST]")).trim();

												System.out.println(date);
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

}
