package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNeNowAssamese {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://assam.nenow.in/category/neighbour/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("jeg_posts jeg_load_more_flag")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("jeg_post jeg_pl_lg_2 format-standard")) {
						System.out.println("3333");
						Elements ee22 = ee.children();
						for (Element ee55 : ee22) {
							if (ee55.className().contains("jeg_thumb")) {
								Elements a = ee55.getElementsByTag("a");

								String href = a.attr("href");
								System.out.println(href);

								Elements img = ee55.getElementsByTag("img");

								String src = img.attr("data-src");
								if (src.contains("?resize=")) {
									src = src.substring(0, src.lastIndexOf("?resize="));

								}
								System.out.println(src);
							
							}

							if (ee55.className().contains("jeg_postblock_content")) {
								Elements h3 = ee55.getElementsByTag("h3");

								String head =h3.text();
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
