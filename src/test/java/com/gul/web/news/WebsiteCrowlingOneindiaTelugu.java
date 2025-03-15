package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaTelugu {
	public static void main(String[] args) throws IOException {
		Connection connection = Jsoup.connect("https://telugu.oneindia.com/health/?ref_medium=Desktop&ref_source=OI-TE&ref_campaign=menu-header");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
//		Document doc = Jsoup.connect(
//				"https://telugu.oneindia.com/health/?ref_medium=Desktop&ref_source=OI-TE&ref_campaign=menu-header")
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
						if (ee3.className().contains("clearfix")) {
							Elements ee4 = ee3.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("cityblock-img")) {
									Elements a = ee5.getElementsByTag("a");

									String href = "https://telugu.oneindia.com" + a.attr("href");
									System.out.println(href);

									Elements img = ee5.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src);
//									System.out.println();
								}
								if (ee5.className().contains("cityblock-desc")) {
									Elements h2 = ee5.getElementsByTag("a");

									String head = h2.text();
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
