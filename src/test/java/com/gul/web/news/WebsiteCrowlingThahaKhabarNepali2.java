package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingThahaKhabarNepali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect("https://thahakhabar.com/news/183671/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("container")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("mb-30 post-body")) {

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("heading-title-50 mb-15")) {

								Elements h1 = ee25566.getElementsByTag("h1");
								String head = h1.text();
								System.out.println(head);

							}
						}

					}
					if (ee.className().contains("row")) {
						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("col-lg-9")) {
								Elements ee2552 = ee25566.children();
								for (Element ee566 : ee2552) {
									if (ee566.className().contains("detail-title-img mb-30")) {
										Elements ee2e552 = ee566.children();
										for (Element ee56 : ee2e552) {
											if (ee56.className().contains("post-img-section")) {
												Elements img = ee56.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
											}
										}
									}

									if (ee566.className().contains("category-collage mb-30")) {
										Elements ee2e552 = ee566.children();
										for (Element ee8 : ee2e552) {
											if (ee8.className().contains("post-body")) {
												Elements ee2e = ee8.children();
												for (Element ee76 : ee2e) {
													if (ee76.className().contains("detail-news-details-paragh detail-fontsize text-justify mb-30 post")) {

														Elements p = ee76.getElementsByTag("p");
														for (Element p2 : p) {
															String se = p2.text();
															if (!se.equals("")) {

																if (!p2.toString().contains("ଇଟିଭି ଭାରତ")
																		&& !p2.toString().contains("twitter")
																		&& !p2.toString().contains(
																				"https://www.etvbharat.com/")) {
																	if (ss.equals("")) {
																		ss += p2.text();

																	} else {
																		ss += "\n\n" + p2.text();
																	}

																}
															}
//															System.out.println(ss);

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
