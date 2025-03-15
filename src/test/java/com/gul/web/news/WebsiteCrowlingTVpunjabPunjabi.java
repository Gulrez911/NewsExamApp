package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingTVpunjabPunjabi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://tvpunjab.com/category/health/").get();

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

						if (article2.className().equals("thumbss")) {
							Elements img = article2.getElementsByTag("img");

							String src = img.attr("src");

							System.out.println(src);

						}
						if (article2.className().equals("entry-header")) {

							Elements a = article2.getElementsByTag("a");
							String href = a.attr("href");
							System.out.println(href);

							Elements h2 = article2.getElementsByTag("h2");
							String head = h2.text();
							System.out.println(head);

//							Elements time = article2.getElementsByTag("time");
//							
//							String date = time.text();
//							System.out.println(date);
							Elements article33 = article2.children();
							for (Element article32 : article33) {

								if (article32.className().equals("entry-meta")) {
									Elements article3w3 = article32.children();
									for (Element article3e2 : article3w3) {

										if (article3e2.className().equals("posted-on")) {
											Elements as = article3e2.getElementsByTag("a");

											for (Element sa : as) {
												Elements sad = sa.children();
												for (Element sda : sad) {
													if (sda.className().contains("updated")) {
													String date = sda.text();
													System.out.println(date);
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
			}

		}

	}

}
