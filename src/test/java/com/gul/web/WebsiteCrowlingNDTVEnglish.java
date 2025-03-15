package com.gul.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNDTVEnglish {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.ndtv.com/india/page-13").timeout(3000).get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("lisingNews")) {

				Elements gggg2 = element.getElementsByClass("news_Itm");
				
				int count = 0;
				for (Element ddd2 : gggg2) {

					Elements divChildren = ddd2.children();

					for (Element ddd22 : divChildren) {
						if (ddd22.className().equals("news_Itm-img")) {
							Elements img2 = ddd22.getElementsByTag("img");
							String src2 = img2.attr("src");
							 
							System.out.println(src2);

							Elements aa = ddd22.getElementsByTag("a");
							String url = aa.attr("href");
//
							System.out.println(url);
						 
						}
						if (ddd22.className().equals("news_Itm-cont")) {

							Elements ddd2222 = ddd22.children();

							for (Element ddd222233 : ddd2222) {

//								System.out.println(ddd222233);
//								if (ddd222233.className().equals("newsHdng")) {
//									Elements ss2dd = ddd222233.getElementsByTag("a");
//									String href = ss2dd.attr("href");
//									System.out.println(href);
////									System.out.println(ss2dd.text());
//								}
								if (ddd222233.className().equals("posted-by")) {
									Elements span = ddd222233.getElementsByTag("span");
									String sss = span.text();
								 
									System.out.println("date: "+sss);
								 

								}
								if (ddd222233.className().equals("newsCont")) {
									Elements sssss = ddd222233.getElementsByTag("p");
//									String href = ss2dd.attr("href");
//									System.out.println(href);
//									System.out.println(sssss.html());
									System.out.println("....................");
								}

							}

						}

					}

				}
			}
		}

	}

}
