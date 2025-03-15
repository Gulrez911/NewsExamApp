package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingIndianExpressGujarati {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://gujarati.indianexpress.com/gujarat/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains(
					"ie-stories show-image image-alignright ts-3 is-3 is-landscape is-style-borders resize-image119x67")) {
				System.out.println("2222222222222");
				Elements article = element.getElementsByTag("article");
				for (Element article2 : article) {

					Elements article9 = article2.children();
					for (Element article10 : article9) {
						if (article10.className().contains("post-thumbnail")) {
							Elements img = article10.getElementsByTag("img");

							String src = img.attr("src");
							if (src.contains("?w=")) {
								src = src.substring(0, src.lastIndexOf("?w="));

							}
							System.out.println(src);
						}
						if (article10.className().contains("entry-wrapper")) {
							Elements article3 = article10.children();
							for (Element article4 : article3) {
								if (article4.className().contains("entry-title")) {
									Elements a = article4.getElementsByTag("a");

									String href = a.attr("href");
									System.out.println(href);

									String head = a.text();
									System.out.println(head);
								}

								if (article4.className().contains("entry-meta-wrapper")) {
									Elements time = article4.getElementsByTag("time");
									String date = time.text();

									if (date.contains("Updated:")) {
										String tt = "Updated: ";
										date = date.substring(date.lastIndexOf("Updated: ") + tt.length(),
												date.length());

										if (date.contains("IST")) {
											date = date.substring(0, date.lastIndexOf("IST"));

											System.out.println(date);
										}

									}else {
										if (date.contains("IST")) {
											date = date.substring(0, date.lastIndexOf("IST"));

											System.out.println(date);
										}

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
