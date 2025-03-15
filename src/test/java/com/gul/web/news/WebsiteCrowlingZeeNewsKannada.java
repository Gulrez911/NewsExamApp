package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingZeeNewsKannada {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://zeenews.india.com/kannada/world").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("view-content")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("story-list clear")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {

							if (ee3.className().contains("story_list_img")) {
								Elements a = ee3.getElementsByTag("a");
								String href = "https://zeenews.india.com" + a.attr("href");
								System.out.println(href);

								Elements img = ee3.getElementsByTag("img");

								for (Element img2 : img) {
									if (img2.toString().contains("src=\"https:")) {
										String src = img2.attr("src");
										if (src.contains("?itok")) {
											src = src.substring(0, src.lastIndexOf("?itok"));

										}
										System.out.println(src);
									}
								}

							}
							if (ee3.className().contains("story_list_con")) {
								Elements ee8 = ee3.children();
								for (Element ee9 : ee8) {
									if (ee9.className().contains("story_list_tag")) {
										Elements span = ee3.getElementsByTag("span");
										String date = span.text();
										if (date.contains("IST")) {
											date = date.substring(0, date.lastIndexOf("IST"));

											System.out.println(date);
										}
									}
									if (ee9.className().contains("story_list_title")) {
										String head = ee9.text();
										System.out.println(head);
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
