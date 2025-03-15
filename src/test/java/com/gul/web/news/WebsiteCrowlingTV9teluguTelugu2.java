package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingTV9teluguTelugu2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://tv9telugu.com/trending/delhi-metro-pole-dance-goes-viral-on-the-internet-999925.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("detailBody")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee2 : divChildren2) {
					if (ee2.className().equals("article-HD")) {
						String head =  ee2.text();
						System.out.println(head);
					}if (ee2.className().equals("author-box")) {

						Elements pp = ee2.getElementsByTag("p");
						String date = pp.text();

						if (date.contains("on: ")) {
							String tt = "on: ";
							date = date.substring(date.lastIndexOf("on: ") + tt.length(), date.length()).trim();

							System.out.println(date);
							System.out.println();
						}

					}
					if (ee2.className().equals("articleImg")) {

						Elements img = ee2.getElementsByTag("img");

						String src = img.attr("src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
						} else {
							System.out.println(src);
						}

						 

					}
					
					if (ee2.className().contains("ArticleBody")) {
						
						Elements p = ee2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ")&&!p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}
//		System.out.println(ss);

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
