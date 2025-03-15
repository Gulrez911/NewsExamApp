package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingTVpunjabPunjabi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://tvpunjab.com/is-the-changing-climate-posing-a-threat-to-heart-patients-find-out-from-the-doctor/")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("a-sides")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements article = ee.children();
					for (Element article2 : article) {

						if (article2.className().equals("entry-header")) {

							Elements h1 = article2.getElementsByTag("h1");

							String head = h1.text();

							System.out.println(head);

						}
						if (article2.className().equals("post-thumbnail")) {

							Elements img = article2.getElementsByTag("img");

							String src = img.attr("src");

							System.out.println(src);
							System.out.println();
						}

						if (article2.className().equals("entry-content")) {

							Elements p = article2.getElementsByTag("p");
							for (Element p2 : p) {
								String se = p2.text();
								if (!se.equals("")) {

									if (!p2.toString().contains("ਨੋਟ:")&&!p2.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += p2.text();

										} else {
											ss += "\n\n" + p2.text();
										}

									}
								}
//								System.out.println(ss);

							}

						}
					}

				}
			}

		}
		System.out.println(ss);
	}

}
