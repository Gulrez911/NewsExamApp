package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingReportersNepalNepali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.reportersnepal.com/category/entertainment").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("col-md-8 text-justify")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("post-list d-flex")) {

						Elements a = ee.getElementsByTag("a");

						String href = a.attr("href");
						System.out.println(href);

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("media cat-media")) {
								Elements ee2554 = ee25566.children();
								for (Element ee255665 : ee2554) {
									if (ee255665.className().contains("mr-3")) {

										Elements img = ee25566.getElementsByTag("img");

										String src = img.attr("src");
										if (src.contains("?resize=")) {
											src = src.substring(0, src.lastIndexOf("?resize="));
											 
										}  
										System.out.println(src);
									}

									if (ee255665.className().contains("media-body")) {

										Elements h2 = ee25566.getElementsByTag("h5");

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
	}
}
