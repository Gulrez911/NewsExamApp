package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Punjabi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://punjab.news18.com/national/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("blog_list")) {
//				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("blog_list_row ")) {
//						System.out.println("3333");

						Elements a = ee.getElementsByTag("a");

						String href = a.attr("href");
						System.out.println(href);

						for (Element ee55661 : a) {
							Elements figure = ee55661.getElementsByTag("figure");
							for (Element figure2 : figure) {
								Elements figcaption = figure2.getElementsByTag("figcaption");
								String head = figcaption.text();
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
