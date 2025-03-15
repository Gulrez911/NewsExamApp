package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Urdu2 {
	public static void main(String[] args) throws IOException {

		String ss = "";
		Document doc = Jsoup.connect(
				"https://urdu.news18.com/news/sports/ind-vs-wi-1st-test-yashasvi-jaiswal-is-set-to-play-rohit-sharma-and-rahul-dravid-can-give-these-players-chance-in-playing-xi-sib-478662.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

//			jsx-2252781433 container clearfix article_readmore
//			jsx-2252781433 news_page_left
			if (element.className().contains("jsx-964381257 news_page news_page_scroll")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {

					if (ee.className().contains("jsx-964381257 container clearfix article_readmore")) {
						Elements ee22 = ee.children();
						for (Element ee44 : ee22) {
							if (ee44.className().contains("jsx-964381257 news_page_left")) {
								Elements ee2233 = ee44.children();
								for (Element ee55 : ee2233) {
									if (ee55.className().contains("jsx-964381257 news_title")) {
										System.out.println(ee55.text());
									}
									if (ee55.className().contains("jsx-964381257 article_content")) {
										Elements ee66 = ee55.children();
										for (Element ee5577 : ee66) {
											if (ee5577.className().contains("jsx-964381257 article_content_img")) {
												Elements img = ee5577.getElementsByTag("img");

												for (Element img2 : img) {
													if (img2.toString().contains("src=\"https://images.news18.com")) {
														String src = img2.attr("src");
														if (src.contains(".jpg")||src.contains(".jpeg")) {
															if (src.contains("?im=")) {
																src = src.substring(0, src.lastIndexOf("?im="));
																System.out.println(src);
															} else {
																System.out.println(src);
															}
														}

													}
												}

											}

											if (ee5577.className().contains("article_content_row")) {
												Elements ee626 = ee5577.children();
												for (Element ee55773 : ee626) {
													if (ee55773.className().contains("newbyeline")) {

														Elements ee6246 = ee55773.children();
														for (Element ee62462 : ee6246) {
															if (ee62462.className().contains("newbyeline-agency")) {
																
																Elements time = ee5577.getElementsByTag("time");
																System.out.println(time.text());
															}
														}
													}
												}
												Elements h2 = ee5577.getElementsByTag("h2");

												ss += h2.text();
												System.out.println(ss);
//												ss
											}
											if (ee5577.className().contains("storypara")) {

												if (ss.equals("")) {
													ss += ee5577.text();
//													System.out.println(ss);
												} else {
													ss += "\n\n" + ee5577.text();
//													System.out.println(ss);
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
		System.out.println(ss);
	}

}
