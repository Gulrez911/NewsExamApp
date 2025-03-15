package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingHindustantimesTamil2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://tamil.hindustantimes.com/sports/bas-de-leede-becomes-only-5th-cricketer-in-odi-history-to-achieve-rare-feat-131688816020519.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("cardHolder detail-card-holder")) {
				System.out.println("2222222222222");
				Elements article = element.children();

				for (Element article4 : article) {
					if (article4.className().contains("newsCardMainHeading")) {
						Elements h1 = article4.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
					}

					Elements span = article4.getElementsByTag("span");
					for (Element span2 : span) {
						Elements img = span2.getElementsByTag("img");
						if (!img.toString().equals("")) {
							String src = "https://telugu.hindustantimes.com" + img.attr("src");
							System.out.println(src);
						}

					}

				}

				for (Element contentSec9 : article) {
					if (contentSec9.className().equals("newsContent")) {
						Elements p = contentSec9.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//		System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("టాపిక్")&&!p2.toString().contains("டாபிக்ஸ்")	&&!p2.toString().contains("href=")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}
//			System.out.println(ss);

								}
							}
//								System.out.println(ss);

						}
					}

				}

			}

		}
		System.out.println(ss);
	}

}
