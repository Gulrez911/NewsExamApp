package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Marathi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://lokmat.news18.com/maharashtra/nagpur/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("newctgrstorylist2")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.children();
				Elements li = element.getElementsByTag("li");
				for (Element ee : li) {

					Elements ee22 = ee.children();
					for (Element ee55 : ee22) {

						Elements a = ee55.getElementsByTag("a");

						String href = a.attr("href");
						System.out.println(href);
						Elements ee5566 = ee55.children();
						for (Element ee55661 : ee5566) {
							Elements ee5566155 = ee55661.children();
							for (Element ee556631 : ee5566155) {
								if (ee556631.className().contains("img-figure")) {
									Elements img = ee556631.getElementsByTag("img");
									for (Element img2 : img) {
										String src = img2.attr("src");
										if (src.contains("?impolicy")) {
											src = src.substring(0, src.lastIndexOf("?impolicy"));
											System.out.println(src);
										}  
									}

								}
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
