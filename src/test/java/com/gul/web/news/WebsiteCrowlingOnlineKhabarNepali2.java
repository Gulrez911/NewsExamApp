package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOnlineKhabarNepali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect("https://www.onlinekhabar.com/2023/06/1329339").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("ok-single-middle")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("ok-container ok-post-header-container")) {

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("ok-entry-header")) {
								Elements ee24 = ee25566.children();
								for (Element ee27 : ee24) {
									if (ee27.className().contains("ok-post-title-right")) {
										Elements ee253 = ee27.children();
										for (Element ee254 : ee253) {
											if (ee254.className().contains("entry-title")) {
												Elements h1 = ee254.getElementsByTag("h1");

												String head = h1.text();
												System.out.println(head);
											}

											if (ee254.className().contains("ok-title-info flx")) {
												Elements ee2545 = ee254.children();
												for (Element ee25457 : ee2545) {
													if (ee25457.className().contains("ok-news-post-hour")) {

														Elements span = ee25457.getElementsByTag("span");

														String date = span.text();
														System.out.println(date);
													}
												}

											}

										}
									}
								}
							}
						}
					}
					if (ee.className().contains("ok-section ok-page-details flx flx-wrp")) {
						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("entry-content")) {
								Elements ee3 = ee25566.children();
								for (Element ee4 : ee3) {
									if (ee4.className().contains("ok-post-detail-featured-img")) {
										Elements ee47 = ee4.children();
										for (Element ee473 : ee47) {
											if (ee473.className().contains("post-thumbnail")) {
												Elements img = ee473.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
											}
										}
									}
									if (ee4.className().contains("ok18-single-post-content-wrap")) {

										Elements p = ee4.getElementsByTag("p");
										for (Element p2 : p) {
											String se = p2.text();
											if (!se.equals("")) {

												if (!se.contains("https://t.me/") && !se.contains("Link:")
														&& !se.toString().contains("twitter.com")
														&& !p2.toString().contains("<a href=")
														&& !se.contains("மேலும் படிக்க")) {
													if (ss.equals("")) {
														ss += p2.text();

													} else {
														ss += "\n\n" + p2.text();
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
		System.out.println(ss);

	}
}
