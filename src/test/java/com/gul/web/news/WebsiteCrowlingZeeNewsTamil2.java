package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsTamil2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://zeenews.india.com/tamil/tamil-nadu/chief-minister-stalin-warns-amit-shah-against-hindi-imposition-says-it-will-destroy-indias-unity-457561")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("region region-content")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("article_header_section")) {

						if (ee.toString().contains("<h1")) {
							System.out.println("33");
							Elements h1 = ee.getElementsByTag("h1");
							String head = h1.text();
							System.out.println(head);
						}

					}

					if (ee.className().contains("article_left clear")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("article_center_col")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("article_img_block")) {
										Elements img = ee5.getElementsByTag("img");

										for (Element img2 : img) {
											if (img2.toString().contains("src=\"https:")) {
												String src = img2.attr("src");
												if (src.contains("?im")) {
													src = src.substring(0, src.lastIndexOf("?im"));

												}
												System.out.println(src);
											}
										}
									}

								}

								Elements article = ee3.getElementsByTag("article");

								for (Element article2 : article) {

									Elements ee6 = article2.children();

									for (Element ee7 : ee6) {
										if (ee7.className().contains("article-block")) {
											Elements ee8 = ee7.children();

											for (Element ee9 : ee8) {
												if (ee9.className().contains("field field-name-body ")) {

													Elements ee10 = ee9.children();

													for (Element ee11 : ee10) {
														if (ee11.className().contains("field-items")) {
															Elements ee12 = ee11.children();

															for (Element ee13 : ee12) {
																if (ee13.className().contains("field-item even")) {
																	Elements p = ee13.getElementsByTag("p");
																	for (Element p2 : p) {
																		String se = p2.text();
																		if (!se.equals("")) {

																			if (!se.contains("https://t.me/")
																					&& !se.contains("Link:")
																					&& !se.toString().contains("twitter.com")&& !p2.toString().contains("<a href=")&&!se.contains("மேலும் படிக்க")) {
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
						}
					}
				}

			}

		}
		System.out.println(ss);
	}
}
