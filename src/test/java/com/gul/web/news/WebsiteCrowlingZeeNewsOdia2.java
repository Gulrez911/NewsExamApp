package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsOdia2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://zeenews.india.com/hindi/zeeodisha/state-news/dengue-cases-rises-in-bhubaneswar/1812879").timeout(200000)
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("col-12")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("article_content")) {

						if (ee.toString().contains("<h1")) {
							System.out.println("33");
							Elements h1 = ee.getElementsByTag("h1");
							String head = h1.text();
							System.out.println(head);
						}
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("articleauthor_details")) {

								 

								String date = ee3.text();

								if (date.contains(":")) {

									int index4 = date.indexOf(':');
									date = date.substring(index4 + 1, date.lastIndexOf("IST")).trim();

									System.out.println(date);
									System.out.println();
								}
							
							}
						}

					}

					if (ee.className().contains("row")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("col-12 col-md-9")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("article_image")) {
										Elements img = ee5.getElementsByTag("img");

										for (Element img2 : img) {
											if (img2.toString().contains("src=\"https:")) {
												String src = img2.attr("data-src");
												if (src.contains("?im")) {
													src = src.substring(0, src.lastIndexOf("?im"));

												}
												System.out.println(src);
											}
										}
									}
									if (ee5.className().contains("article_content")) {
										Elements ee8 = ee5.children();

										for (Element ee9 : ee8) {
											if (ee9.className().contains("article_description")) {

												Elements p = ee9.getElementsByTag("p");
												for (Element p2 : p) {
													String se = p2.text();
													if (!se.equals("")) {

														if (!se.contains("https://t.me/") && !se.contains("Link:")
																&& !se.toString().contains("twitter.com")
																&& !p2.toString().contains("<a href=")
																&& !se.contains("மேலும் படிக்க")) {
															if (ss.equals("")) {
																ss += p2.text();

															} else {
																ss += "\n\n" + p2.text();
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
		System.out.println(ss);
	}
}