package com.gul.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNDTV {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.ndtv.com/india").get();

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
							count++;
							System.out.println(src2);

							Elements aa = ddd22.getElementsByTag("a");
							String url = aa.attr("href");
//
							System.out.println(url);
//							secondURl(url);
						}
						if (ddd22.className().equals("news_Itm-cont")) {

							Elements ddd2222 = ddd22.children();

							for (Element ddd222233 : ddd2222) {

//								System.out.println(ddd222233);
								if (ddd222233.className().equals("newsHdng")) {
									Elements ss2dd = ddd222233.getElementsByTag("a");
									String href = ss2dd.attr("href");
//									System.out.println(href);
									System.out.println(ss2dd.text());
								}
								if (ddd222233.className().equals("posted-by")) {
									Elements span = ddd222233.getElementsByTag("span");
									String date = span.text();
									if (date.contains("|")) {

//										date = date.replace(" | ", "");
										date = date.substring(date.indexOf('|') + 2, date.length()).trim();
									}

									date = date.substring(date.indexOf(' '), date.lastIndexOf(",")).trim();
//									System.out.println(first);
									System.out.println(date);

								}
								if (ddd222233.className().equals("newsCont")) {
									Elements sssss = ddd222233.getElementsByTag("p");
//									String href = ss2dd.attr("href");
//									System.out.println(href);
									System.out.println(sssss.html());
								}

							}

						}

					}

				}
			}
		}

	}

//	public static void secondURl(String url) throws IOException {
//
//		Document doc2 = Jsoup.connect(url).get();
//
//		Elements elements222 = doc2.getAllElements();
//		System.out.println("/////////////////       "+elements222.size());
//		for (int j = 0; j < elements222.size(); j++) {
//			Element ss = elements222.get(j);
//			if (ss.className().equals("content")) {
//				
//				Elements ddd2222 = ss.children();
//				
//				for(Element sp : ddd2222) {
//					
//					if(sp.className().equals("sp-ttl")) {
//						
//						String hdd = sp.text();
//						System.out.println(hdd);
//						System.out.println("1111111111111");
//					}
//				}
//			
//				
// 
//			}
//		}
//
//	}

}
