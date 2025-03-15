package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingABPliveGujarati2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://gujarati.abplive.com/news/india/maharashtra-ajit-pawar-faction-claims-to-remove-sharad-pawar-ncp-president-post-847237")
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
						for (Element ee3 : ee2) {
							if (ee3.className().contains("article-title")) {
								 
								String head = ee3.text();
								System.out.println(head);
							}
							if (ee3.className().contains("uk-margin-bottom uk-flex-between")) {

								Elements p = ee3.getElementsByTag("p");

								for (Element eee3 : p) {
									if (eee3.className().contains("article-author")) {

										String date = eee3.text();

										if (date.contains("at : ")) {
											String tt = "at : ";
											date = date.substring(date.lastIndexOf("at : ") + tt.length(),
													date.length());

											if (date.contains("(IST)")) {
												date = date.substring(0, date.lastIndexOf("(IST)"));

												System.out.println(date);
											}

										}

									}
								}
							}
							if (ee3.className().contains("news_featured cont_accord_to_img")) {
								Elements img = ee3.getElementsByTag("img");
								String src = img.attr("data-src");
								if (src.contains("?impolicy")) {
									src = src.substring(0, src.lastIndexOf("?impolicy"));
									 
								} 
								System.out.println(src);
								 
							}
						}
					}
					if (ee.className().contains("article-data _thumbBrk uk-text-break")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!se.contains("Tags:")&&!se.contains("https:")&&!p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}

								}
							}
//							System.out.println(ss);

						}

					}
				}
			}

		}
		System.out.println(ss);
	}

}
