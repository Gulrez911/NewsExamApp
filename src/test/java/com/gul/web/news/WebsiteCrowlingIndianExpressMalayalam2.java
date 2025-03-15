package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingIndianExpressMalayalam2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://malayalam.indianexpress.com/entertainment/indian-music-at-manchester-swasika-shares-dance-video-874559/")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className()
					.contains("wp-block-column ie-network-grid__lhs margin-bottom-none is-layout-flow")) {
				System.out.println("2222222222222");
				Elements article = element.children();
				for (Element article2 : article) {

					if (article2.className().contains("wp-block-post-title")) {
						Elements h1 = article2.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
					}
					if (article2.className().contains("wp-block-post-featured-image")) {
						Elements img = article2.getElementsByTag("img");
						String src = img.attr("src");
						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));

						}
						System.out.println(src);
					}
					if (article2.className().contains("entry-content wp-block-post-content is-layout-flow")) {

						Elements ee45 = article2.children();
						for (Element ee455 : ee45) {
							int first = ee455.toString().length();
							String ssss = ee455.toString();
							if (first > 5) {
								ssss = ssss.substring(0, 4);
								if (!ssss.contains("<div")&&!ssss.contains("<fi")) {
									Elements p = ee455.getElementsByTag("p");
									for (Element p2 : p) {
										String se = p2.text();
										if (!se.equals("")) {

											if (!p2.toString().contains("Tags") && !p2.toString().contains("twitter")
													&& !p2.toString().contains("Also Read") && !p2.toString()
															.contains("https://malayalam.indianexpress.com/")) {
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
				}
			}

		}
		System.out.println(ss);
	}

}
