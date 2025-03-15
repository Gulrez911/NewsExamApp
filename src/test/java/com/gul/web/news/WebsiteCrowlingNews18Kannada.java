package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Kannada {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://kannada.news18.com/trend/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("blog_list")||element.className().contains("latest_news_list")) {
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

					}else {
						Elements li = ee.getElementsByTag("li");
						for (Element ee55661 : li) {
							Elements ee556612 = ee55661.children();
							for (Element ee5566125 : ee556612) {
								Elements aa = ee5566125.getElementsByTag("a");

								String href = aa.attr("href");
								System.out.println(href);

								for (Element ee5566s1 : aa) {
									Elements figure = ee5566s1.getElementsByTag("figure");
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

	}

}
