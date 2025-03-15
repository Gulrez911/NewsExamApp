package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsOdia {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://zeenews.india.com/hindi/zeeodisha/lifestyle").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("news_item")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("news_right")) {

						Elements a = ee.getElementsByTag("a");
						String href = "https://zeenews.india.com" + a.attr("href");
						System.out.println(href);

						Elements img = ee.getElementsByTag("img");

						for (Element img2 : img) {
							if (img2.toString().contains("data-src=\"https:")) {
								String src = img2.attr("data-src");
								if (src.contains("?itok")) {
									src = src.substring(0, src.lastIndexOf("?itok"));

								}
								System.out.println(src);
							}
						}

					}

					if (ee.className().contains("news_left")) {

						Elements ee8 = ee.children();
						for (Element ee9 : ee8) {

							if (ee9.className().contains("news_title")) {
								String head = ee9.text();
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
