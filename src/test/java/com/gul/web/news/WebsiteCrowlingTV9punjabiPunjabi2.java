package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingTV9punjabiPunjabi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://tv9punjabi.com/world/pakistan/mass-murder-in-pakistan-unknown-attacker-killed-9-family-members-in-khyber-pakhtunkhwa-know-full-detail-in-punjabi-2033339")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("detailBody")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().equals("article-HD")) {

						String head = ee.text();
						System.out.println(head);

					}if (ee.className().equals("authorBox")) {

						Elements pp = ee.getElementsByTag("p");
						String date = pp.text();
						System.out.println(date);

					}
					if (ee.className().contains("ArticleBodyCont")) {

						
						Elements img = ee.getElementsByTag("img");

						String src = img.attr("data-src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
						} else {
							System.out.println(src);
						}

						

						Elements p = ee.getElementsByTag("p");
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
