package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingPunjabijagranPunjabi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.punjabijagran.com/latest-news-punjabi.html").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("newsFJagran")) {
//				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("topicList")) {

						Elements ee2 = ee.getElementsByTag("li");

						for (Element ee23 : ee2) {
							Elements a = ee23.getElementsByTag("a");
							String href = "https://www.punjabijagran.com" + a.attr("href");
							System.out.println(href);
							for (Element aa : a) {
								Elements aa3 = aa.children();
								for (Element aa4 : aa3) {
									if (aa4.className().contains("proimg fl")) {
										Elements img = aa4.getElementsByTag("img");
										String src = "";
										if (img.toString().contains("data-src")) {
											src =img.attr("data-src");
										} else {
											src = img.attr("src");
										}
										System.out.println(src);

									}
									if (aa4.className().contains("protxt fr")) {
										Elements aa42 = aa4.children();
										for (Element aa423 : aa42) {
											if (aa423.className().contains("h3")) {
												String h3 = aa423.text();
												System.out.println(h3);
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
