package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOnlineKhabarNepali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.onlinekhabar.com/trend/%e0%a4%a8%e0%a5%87%e0%a4%aa%e0%a4%be%e0%a4%b2%e0%a5%80-%e0%a4%ab%e0%a4%bf%e0%a4%b2%e0%a5%8d%e0%a4%ae\r\n"
				+ "").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("ok-grid-12")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("span-4")) {

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("ok-news-post")) {

								Elements a = ee25566.getElementsByTag("a");

								String href = a.attr("href");
								System.out.println(href);

								Elements img = ee25566.getElementsByTag("img");

								String src = img.attr("src");
								System.out.println(src);
								Elements h2 = ee25566.getElementsByTag("h2");

								String head = h2.text();
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
