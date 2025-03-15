package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Urdu {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://urdu.news18.com/money/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("jsx-458440942 urdnwslist")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().equals("jsx-458440942")) {
						Elements a = ee.getElementsByTag("a");

						String src2f = a.attr("href");
						System.out.println(src2f);

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("srcset");
						String[] parts = src.split(" ");
						String first = parts[0];// "hello"
						String ere = "https://urdu.news18.com";
						ere += first;
						System.out.println(ere);

						Elements h3 = ee.getElementsByTag("h3");

						String ss = h3.text();
						System.out.println(ss);
						System.out.println("...................");
					}

				}

			}

		}

	}

}
