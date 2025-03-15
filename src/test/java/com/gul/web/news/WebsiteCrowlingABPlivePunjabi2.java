package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingABPlivePunjabi2 {
	public static void main(String[] args) throws IOException {

		String ss = "";
		Document doc = Jsoup.connect(
				"https://punjabi.abplive.com/ajab-gajab/shoes-for-beer-you-will-have-to-deposit-your-shoes-at-this-bar-731068")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("uk-width-expand uk-position-relative")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("")) {

						Elements ee2 = ee.children();

						for (Element ee23 : ee2) {
							if (ee23.className().contains("article-title")) {

								String head = ee23.text();
								System.out.println(head);

							}
							if (ee23.className().contains(
									"uk-flex uk-flex-bottom _no_margin_bottom uk-margin-bottom uk-flex-between")) {
								Elements ee2343 = ee23.children();
								for (Element ee23e : ee2343) {
									Elements ee2343s = ee23e.children();
									for (Element ee23se : ee2343s) {
										if (ee23se.className().contains("article-author")) {
											String date = ee23se.text();
											if (date.contains("Updated at :")) {
												String tt = "Updated at : ";
												date = date.substring(date.lastIndexOf("Updated at : ") + tt.length(),
														date.length());
												if (date.contains("(IST)")) {

													date = date.replace("(IST)", "").trim();
													System.out.println(date);
												}

											}
										 
										}
									}

								}
							}
							if (ee23.className().contains("news_featured cont_accord_to_img")) {

								Elements img = ee23.getElementsByTag("img");
								String src = img.attr("data-src");
								if (src.contains("?impolicy")) {
									src = src.substring(0, src.lastIndexOf("?impolicy"));
									System.out.println(src);
								}

							}

						}

					}

					if (ee.className().contains("article-data _thumbBrk uk-text-break")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {

							if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += p2.text();

								} else {
									ss += "\n\n" + p2.text();
								}
//								System.out.println(ss);
							}

						}

					}

				}

			}

		}
		System.out.println(ss);
	}

}
