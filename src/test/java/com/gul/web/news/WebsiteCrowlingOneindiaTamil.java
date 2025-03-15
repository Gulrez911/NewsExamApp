package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaTamil {
	public static void main(String[] args) throws IOException {
		Connection connection = Jsoup.connect("https://tamil.oneindia.com/news/india/?ref_medium=Desktop&ref_source=OI-TA&ref_campaign=menu-header");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
//		Document doc = Jsoup.connect(
//				"https://tamil.oneindia.com/news/india/?ref_medium=Desktop&ref_source=OI-TA&ref_campaign=menu-header")
//				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("oi-cityblock-list")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements ee2 = ee.children();
					for (Element ee3 : ee2) {
						if (ee3.className().contains("clearfix")) {
							Elements ee4 = ee3.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("cityblock-img news-thumbimg")) {
									Elements a = ee5.getElementsByTag("a");

									String href = "https://tamil.oneindia.com" + a.attr("href");
									System.out.println(href);

									Elements img = ee5.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src);
//									System.out.println();
								}
								if (ee5.className().contains("cityblock-desc")) {

									Elements ee55 = ee5.children();
									for (Element ee555 : ee55) {
										if (ee555.className().contains("cityblock-title news-desc")) {

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
