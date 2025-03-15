package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingTV9kannadaKannada {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://tv9kannada.com/sports/cricket-news").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("wrapper_section")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (!ee.toString().contains("adsCont") && ee.toString().contains("<a")
							&& ee.toString().contains("<img")) {
						Elements p = ee.getElementsByTag("a");
						String href = p.attr("href");
						System.out.println(href);

						System.out.println(p.text());

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("data-src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
						} else {
							System.out.println(src);
						}

						System.out.println();
					}

 
				}
			}

		}

	}

}
