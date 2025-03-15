package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingLoksattaMarathi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.loksatta.com/about/upsc/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains(
					"wp-block-newspack-blocks-ie-stories is-style-borders")) {
				System.out.println("2222222222222");
				Elements article = element.getElementsByTag("article");
				for (Element ee5 : article) {

					Elements ee6 = ee5.children();

					for (Element ee7 : ee6) {
						Elements figure = ee7.getElementsByTag("figure");
						for (Element figure2 : figure) {
							Elements a = figure2.getElementsByTag("a");
							String href = a.attr("href");
							System.out.println(href);

							Elements img = figure2.getElementsByTag("img");
							String src = img.attr("src");
							if (src.contains("?w")) {
								src = src.substring(0, src.lastIndexOf("?w"));
							}
							System.out.println(src);
//							System.out.println();

						}

						if (ee7.className().contains("entry-wrapper")) {
							Elements eee6 = ee7.children();

							for (Element eee7 : eee6) {
								if (eee7.className().contains("entry-title")) {
									String head = eee7.text();
									System.out.println(head);
								}
								if (eee7.className().contains("entry-meta")) {
									Elements time = eee7.getElementsByTag("time");
									String date = time.text();
									if (date.contains("IST")) {
										date = date.substring(0, date.lastIndexOf("IST"));

										System.out.println(date);
									}
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
