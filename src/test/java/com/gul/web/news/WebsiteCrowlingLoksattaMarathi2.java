package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingLoksattaMarathi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://www.loksatta.com/mumbai/pending-purchase-of-mri-ctscan-sonography-machine-at-kem-hospital-mumbai-print-news-dvr-99-3769422/")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("wp-container-3 wp-block-column ie-network-grid__lhs")) {
				System.out.println("2222222222222");
				Elements article = element.children();
				for (Element ee5 : article) {

					if (ee5.className().contains("wp-block-post-title")) {
						String head = ee5.text();
						System.out.println(head);
					}
					if (ee5.className().contains("wp-block-post-featured-image")) {
						Elements img = ee5.getElementsByTag("img");
						String src = img.attr("src");
						if (src.contains("?w")) {
							src = src.substring(0, src.lastIndexOf("?w"));
						}
						System.out.println(src);
					}
					
					if (ee5.className().contains("wp-container-2 entry-content wp-block-post-content")) {


						Elements p = ee5.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("हेही वाचा")&&!p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}

								}
							}
//							System.out.println(ss);

						}

					
					}
					
				}
			}

		}
		System.out.println(ss);
	}

}
