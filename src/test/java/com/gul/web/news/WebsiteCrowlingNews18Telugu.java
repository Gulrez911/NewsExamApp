package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Telugu {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://telugu.news18.com/mission-paani/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("blog_list")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("blog_list")) {
//						System.out.println("3333");
						Elements ee22 = ee.children();
						for (Element ee55 : ee22) {
							if (ee55.className().contains("blog_list_row")) {
								Elements a = ee55.getElementsByTag("a");
//								System.out.println("444444444");
								String href = a.attr("href");
								System.out.println(href);
								Elements ee5566 = ee55.children();
								for (Element ee55661 : ee5566) {
									Elements ee5566155 = ee55661.getElementsByTag("figure");
									for (Element ee556631 : ee5566155) {
//										if (ee556631.className().contains("jsx-eec58b514a6fb0")) {
											Elements img = ee556631.getElementsByTag("img");

											String src = img.attr("data-src");
											if (src.contains("?im")) {
												src = src.substring(0, src.lastIndexOf("?im"));
												System.out.println(src);
											} else {
												System.out.println(src);
											}
//										}
									}

								}

								System.out.println(ee55.text());
								System.out.println();
							}

						}
					}

				}

			}

		}

	}

}
