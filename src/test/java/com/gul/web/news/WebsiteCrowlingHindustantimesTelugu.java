package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingHindustantimesTelugu {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://telugu.hindustantimes.com/national-international").get();
		int flag = 0;
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("infinite-scroll-component")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements ee22 = ee.children();
					for (Element ee223 : ee22) {
						if (ee223.className().equals("card-wrapper")) {
							Elements ee2 = ee223.children();
							for (Element ee3 : ee2) {

								if (ee3.className().contains("cardHolder section-page-cardholder")) {
									Elements ee4 = ee3.children();
									for (Element ee5 : ee4) {
										if (ee5.className().contains("listingWrap listTBpaddAlign")) {
											Elements ee6 = ee5.children();

											for (Element ee7 : ee6) {
												if (ee7.className().contains("row d-flex")) {
													Elements ee8 = ee7.children();

													for (Element ee9 : ee8) {
														if (ee9.className().contains("listing-page-img-thumbnail")) {

															Elements img = ee9.getElementsByTag("img");

															String src = img.attr("srcset");

//														if (flag == 0) {
															if (src.contains("&w=96&q=75 1x")) {
																src = "https://telugu.hindustantimes.com"
																		+ src.substring(0, src.lastIndexOf("1x"));
																System.out.println(src);
															} else {
																System.out.println(src);
															}
//															flag = 1;
//														}

														}
														if (ee9.className().contains("listnewsContAdj")) {
															Elements h2 = ee9.children();
															for (Element h3 : h2) {

																if (h3.className()
																		.contains("listingNewsCont listingNewsHead")) {
																	Elements a = h3.getElementsByTag("a");
																	String href = "https://telugu.hindustantimes.com"
																			+ a.attr("href");
																	System.out.println(href);
																	System.out.println(a.text());
																}
																if (h3.className().contains("relNewsTime")) {
																	Elements p = h3.getElementsByTag("p");

																	String date = p.text();
																	if (date.contains("IST")) {
																		date = date.substring(0,
																				date.lastIndexOf("IST"));

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
					}

				}
			}

		}

	}

}
