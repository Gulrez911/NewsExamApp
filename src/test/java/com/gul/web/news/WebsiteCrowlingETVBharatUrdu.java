package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingETVBharatUrdu {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.etvbharat.com/urdu/national/sukhibhava").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("md:w-8/12 h-full px-2 md:flex md:flex-wrap")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {

						Elements ee22 = ee.children();
						for (Element ee2233 : ee22) {
							if (ee2233.className().contains("flex w-full justify-between space-x-4 flex-row-reverse")) {
								Elements ee2244 = ee2233.getElementsByTag("a");
								for (Element ee223366 : ee2244) {
									String after = "https://www.etvbharat.com";
									String href = after + ee223366.attr("href");
									System.out.println(href);

									Elements img = ee223366.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src);

								
									System.out.println(ee223366.text());
									System.out.println();
								}
							}
						}
						System.out.println("........");
					}
					if (ee.className().contains(
							"flex  justify-between px-1 pt-1 pb-1 cursor-pointer border shadow rtl rectangle-card bg-white mt-1 md:mt-2 md:w-1/2 rounded-md")) {

						String after = "https://www.etvbharat.com";
						String href = after + ee.attr("href");
						System.out.println(href);

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("data-src");
						System.out.println(src);

					 
						System.out.println(ee.text());
						System.out.println();
					}
				}

			}

		}

	}

}
