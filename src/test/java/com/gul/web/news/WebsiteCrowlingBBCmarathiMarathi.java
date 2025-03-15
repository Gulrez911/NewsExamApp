package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBBCmarathiMarathi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.bbc.com/marathi/topics/c719d2enyn3t").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("bbc-1kz5jpr")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();
				
				for (Element ee : divChildren2) {
					if (ee.className().equals("bbc-t44f9r")) {

						Elements sss = ee.getAllElements();
						for (Element ww : sss) {

							if (ww.className().contains("e1v051r10")) {
								Elements ss = ww.getAllElements();
								for (Element weeeew : ss) {
									if (weeeew.className().contains("promo-image")) {
										Elements dddd = weeeew.getAllElements();
										 									
												String src2 = dddd.attr("src");
												System.out.println(src2);
									 
									}

									if (weeeew.className().contains("promo-text")) {
										Elements www = weeeew.getAllElements();
										for (Element ccc : www) {
											if (ccc.className().contains("e47bds20")) {

												Elements a = ccc.getElementsByTag("a");
												String url = a.attr("href");
												System.out.println(url);
												System.out.println(a.text());
												
											}
										}
									}

									
									if (weeeew.className().contains("e1mklfmt0")) {
										
										System.out.println(weeeew.text());
										System.out.println();
									}
									
									
								}
							}
						}
//						Elements sss = ee.getElementsByTag("img");
//						System.out.println(sss);
					}
				}
				
			}

		}

	}

}
