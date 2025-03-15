package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBanglaHindustantimesBangali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://bangla.hindustantimes.com/latest-news").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("mainSec")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().equals("listView")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							Elements ee4 = ee3.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("listing clearfix")) {
									Elements ee6 = ee5.children();
									for (Element ee7 : ee6) {
										
										Elements ee8 = ee7.children();
										
										for (Element ee9 : ee8) {
											if (ee9.className().contains("thumbnail")) {

												Elements a = ee9.getElementsByTag("a");

												String src2f ="https://bangla.hindustantimes.com"+ a.attr("href");
												System.out.println(src2f);

												Elements img = ee9.getElementsByTag("img");

												String src = img.attr("data-src");
												System.out.println(src);
												
											}
											if (ee9.className().contains("headlineSec")) {
												Elements ee10 = ee9.children();
												for (Element ee11 : ee10) {
													if (ee11.className().contains("headline")) {
														String head = ee11.text();
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

		}

	}

}
