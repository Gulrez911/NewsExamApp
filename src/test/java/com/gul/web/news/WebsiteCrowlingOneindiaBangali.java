package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaBangali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect(
				"https://bengali.oneindia.com/movies/?ref_medium=Desktop&ref_source=OI-BN&ref_campaign=menu-header")
				.get();

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
								if (ee5.className().equals("cityblock-img news-thumbimg")) {
									Elements a = ee5.getElementsByTag("a");

									String href = "https://bengali.oneindia.com" + a.attr("href");
									System.out.println(href);

									Elements img = ee5.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src);
//									System.out.println();
								}
								if (ee5.className().equals("cityblock-desc")) {

									Elements ee45 = ee5.children();
									for (Element ee55 : ee45) {
										if (ee55.className().contains("cityblock-title")) {
											Elements h2 = ee55.getElementsByTag("h2");

											String head = h2.text();
											System.out.println(head);

										}
										if (ee55.className().contains("cityblock-time")) {
											String date = ee55.text();

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
