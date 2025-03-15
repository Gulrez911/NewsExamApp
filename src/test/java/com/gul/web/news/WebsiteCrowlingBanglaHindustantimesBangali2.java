package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBanglaHindustantimesBangali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://bangla.hindustantimes.com/elections/west-bengal-panchayat-vote-2023-shamshergunj-candidate-join-tmc-after-winning-with-congress-ticket-31689270338964.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("cardHolder open")) {
				System.out.println("2222222222222");
				Elements article = element.getElementsByTag("article");

				for (Element article2 : article) {
					Elements article3 = article2.children();

					for (Element article4 : article3) {
//						if (article4.className().equals("headline")) {
//							String headline = article4.text();
//							System.out.println(headline);
//						}
						Elements h1 = article4.getElementsByTag("h1");
						if(!h1.toString().equals("")) {
							String headline = h1.text();
							System.out.println(headline);
						}
						
						if (article4.className().contains("pubtime")) {
							String date = article4.text();
							
							if (date.contains("Updated:")) {
								String tt = "Updated: ";
								date = date.substring(date.lastIndexOf("Updated: ") + tt.length(),
										date.length());
								
								
								if (date.contains(",")) {
									date = date.substring(0, date.lastIndexOf(","));
								 
									System.out.println(date);
								}

							}
							
							 
						}
						if (article4.className().contains("storyExpend")||article4.className().contains("photoBlurEffect")) {
							Elements img = article4.getElementsByTag("source");

							String src = img.attr("srcset");
							System.out.println(src);
						}
					}

				}

				Elements contentSec = element.children();
				for (Element contentSec2 : contentSec) {

					if (contentSec2.className().equals("contentSec")) {
						Elements contentSec3 = contentSec2.children();
						for (Element contentSec4 : contentSec3) {

							Elements contentSec6 = contentSec4.children();

							for (Element contentSec9 : contentSec6) {
								if (contentSec9.className().equals("mainArea")) {
									Elements p = contentSec9.getElementsByTag("p");
									for (Element p2 : p) {
										String se = p2.text();
//		System.out.println(se);
										if (!se.equals("")) {

											if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ")&&!p2.toString().contains("twitter")) {
												if (ss.equals("")) {
													ss += p2.text();

												} else {
													ss += "\n\n" + p2.text();
												}
//			System.out.println(ss);

											}
										}
//								System.out.println(ss);

									}
								}
							}

						}

					}
				}

			}

		}
		System.out.println(ss);
	}

}
