package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsGujarati2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://zeenews.india.com/gujarati/india/emergency-alert-test-messages-create-panic-among-mobile-upsers-in-nagpur-across-india-latest-gujarati-news-283437")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("col-12 col-md-9 pl-0")) {
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

					}

					if (ee.className().contains("row")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("col-12 col-md-9")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("article_content article_image")) {
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

									if (ee5.className().contains("article_content")) {
										Elements ee6 = ee5.children();

										for (Element ee7 : ee6) {
											if (ee7.className().contains("article_content")) {

												Elements p = ee7.getElementsByTag("p");
												for (Element p2 : p) {
													String se = p2.text();
													if (!se.equals("")) {

														if (!se.contains("એજન્સી") && !se.contains("લેટેસ્ટ")&& !se.contains("twitter")) {
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
