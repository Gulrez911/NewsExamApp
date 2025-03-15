package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsGujarati {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://zeenews.india.com/gujarati/india").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("infinite-scroll-component__outerdiv")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("infinite-scroll-component")) {
						System.out.println("33");
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {

							if (ee3.className().contains("news_item")) {
								Elements ee6 = ee3.children();
								for (Element ee7 : ee6) {
									if (ee7.className().contains("news_right")) {
										Elements a = ee7.getElementsByTag("a");
										String href = "https://zeenews.india.com" + a.attr("href");
										System.out.println(href);

										Elements img = ee7.getElementsByTag("img");

										for (Element img2 : img) {
											if (img2.toString().contains("src=\"https:")) {
												String src = img2.attr("src");
												if (src.contains("?itok")) {
													src = src.substring(0, src.lastIndexOf("?itok"));
													 
												} 
												System.out.println(src);
											}
										}

									}
									if (ee7.className().contains("news_left")) {
										Elements ee8 = ee7.children();
										for (Element ee9 : ee8) {
											if (ee9.className().contains("news_title")) {
												String head = ee9.text();
												System.out.println(head);
												System.out.println();
											}if (ee9.className().contains("news_updatetime")) {
												String date = ee9.text();
												if (date.contains("IST")) {

													date = date.substring(0, date.lastIndexOf("IST"));
													System.out.println(date);
												}
												System.out.println();
											}
										}
									}
								}
//									System.out.println("555555");
//									Elements a = ee5.getElementsByTag("a");
//									String href = a.attr("href");
//									System.out.println(href);
//
//									 
//
//									Elements img = ee5.getElementsByTag("img");
//									String src = img.attr("data-src");
//									if (src.contains("?impolicy")) {
//										src = src.substring(0, src.lastIndexOf("?impolicy"));
//										 
//									} 
//									System.out.println(src);
//									
//									String ss = ee5.text();
//									System.out.println(ss);
//									System.out.println();

							}
						}

					}
				}

			}

		}
	}
}
