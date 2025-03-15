package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Punjabi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://punjab.news18.com/news/national/husband-kept-wife-dead-body-in-freezer-in-madhya-pradesh-was-sitting-calmly-on-chair-outside-house-know-the-case-tc-438381.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("jsx-667339927 article_section")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("jsx-667339927 container")) {

						Elements ee1 = ee.children();
						for (Element ee12 : ee1) {
							if (ee12.className().contains("jsx-667339927 artcl_container")) {
								Elements ee123 = ee12.children();
								for (Element ee1234 : ee123) {
									if (ee1234.className().contains("jsx-667339927 artcl_lft")) {
										Elements ee12345 = ee1234.children();
										for (Element ee123456 : ee12345) {
											if (ee123456.className().equals("jsx-667339927")) {
												System.out.println(ee123456.text());
											}
											if (ee123456.className().contains("artcl_contents")) {
												Elements img = ee123456.getElementsByTag("img");

												for (Element img2 : img) {
													if (img2.toString().contains("src=\"https://images.news18.com")) {
														String src = img2.attr("src");
														if (src.contains(".jpg")) {
															if (src.contains("?im=")) {
																src = src.substring(0, src.lastIndexOf("?im="));
																System.out.println(src);
															}
														}

													}
												}
											}
											if (ee123456.className().contains("jsx-355980391 khbren_section tbl-forkorts-article tbl-forkorts-article-active")) {
												Elements ee1234567 = ee123456.children();
												for (Element ee12345678 : ee1234567) {
													if (ee12345678.className().contains("content_sec")) {
														Elements p = ee12345678.getElementsByTag("p");
														for (Element p2 : p) {
															if (ss.equals("")) {
																ss += p2.text();

															} else {
																ss += "\n\n" + p2.text();
															}
															System.out.println(ss);

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
