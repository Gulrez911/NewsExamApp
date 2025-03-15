package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Kannada2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		int flag = 0;
		Document doc = Jsoup.connect(
				"https://kannada.news18.com/news/explained/explained-apples-my-photo-stream-is-ending-soon-and-what-iphone-users-should-do-stg-hhb-1253665.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   " );

			if (element.className().contains("news_page news_page_scroll")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("container clearfix")) {

						Elements ee1 = ee.children();
						for (Element ee12 : ee1) {
							if (ee12.className().contains("news_page_left")) {
								Elements ee123 = ee12.children();
								for (Element ee1234 : ee123) {
									if (ee1234.className().contains("news_title")) {

										Elements h1 = ee1234.getElementsByTag("h1");
										String head = h1.text();
										System.out.println(head);
									}

									if (ee1234.className().contains("article_content")) {

										Elements ee1235 = ee1234.children();

										for (Element ee12348 : ee1235) {
											if (ee12348.className().contains("article_content_img")) {
												Elements img = ee12348.getElementsByTag("img");

												for (Element img2 : img) {
													if (img2.toString().contains("src=")) {
														if (flag == 0) {
															if (img2.toString()
																	.contains("src=\"https://images.news18.com")) {
																String src = img2.attr("src");
																if (src.contains(".jpg")) {
																	if (src.contains("?im")) {
																		src = src.substring(0, src.lastIndexOf("?im"));
																		System.out.println(src);
																	}
																}

															}
															flag = 1;
														}
													}

												}
											}
											if (ee12348.className().contains("article_content_row")) {
												Elements time = ee12348.getElementsByTag("time");

												String date = time.text();
												if (date.contains("IST")) {
													date = date.substring(0, date.lastIndexOf("IST"));

													System.out.println(date);
												}

											}

										}

									}

									if (ee1234.className().contains("khbren_section")) {
										Elements ee1234567 = ee1234.children();
										for (Element ee12345678 : ee1234567) {
											if (ee12345678.className().contains("khbr_rght_sec")) {
												Elements p = ee12345678.getElementsByTag("p");
												for (Element p2 : p) {
													if (!p2.toString().contains("Tags")
															&& !p2.toString().contains("twitter")
															&& !p2.toString().contains("Breaking News")) {
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
		System.out.println(ss);
	}

}
