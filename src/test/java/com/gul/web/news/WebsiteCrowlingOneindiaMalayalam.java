package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaMalayalam {
	public static void main(String[] args) throws IOException {
		Connection connection = Jsoup.connect(
				"https://malayalam.oneindia.com/news/kerala/?ref_medium=Desktop&ref_source=OI-ML&ref_campaign=menu-header");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
//		Document doc = Jsoup.connect(
//				"https://malayalam.oneindia.com/topic/bigg-boss?utm_medium=Desktop&utm_source=OI-ML&utm_campaign=Home-TrendingTopics2")
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
//								if (ee5.className().contains("cityblock-img")) {

//									System.out.println();
//								}
								if (ee5.className().contains("cityblock-desc")) {

									Elements ee52 = ee5.children();
									for (Element ee523 : ee52) {
										Elements ee522 = ee523.children();
										for (Element ee5223 : ee522) {
											if (ee5223.className().contains("cityblock-title")) {
												String head = ee5223.text();
												System.out.println(head);
												System.out.println();
											} else {
//												if (!ee5223.toString().contains("<a href")) {

													String head = ee5223.text();
													if (!head.contains("[IST]")) {
														
														System.out.println(head);
														System.out.println();
													}
//												}
											}
										}

									}

//									Elements h2 = ee5.getElementsByTag("a");

								} else {
									Elements a = ee5.getElementsByTag("a");

									String text = a.attr("href");
									if (text.contains("/")) {
										String href = "https://malayalam.oneindia.com" + a.attr("href");
										System.out.println(href);

										Elements img = ee5.getElementsByTag("img");

										String src = img.attr("src");
										System.out.println(src);
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
