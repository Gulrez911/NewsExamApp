package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Marathi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://news18marathi.com/news/these-four-zodiac-signs-have-strong-faith-in-love-in-marathi-mhpd-1011200.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("newleftwrap")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("nwarthd")) {
						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
					}

					if (ee.className().contains("nwartbx")) {

						 
							Elements ee4 = ee.children();
							for (Element ee5 : ee4) {
								if (ee5.className().contains("nwartbximg")) {
									Elements img = ee5.getElementsByTag("img");
									String src = img.attr("src");
									if (src.contains("?im=")) {
										src = src.substring(0, src.lastIndexOf("?im="));
									}
									System.out.println(src);
								}

								if (ee5.className().contains("nwartbxdtl")) {
									Elements ee455 = ee5.children();
									for (Element ee57 : ee455) {
										if (ee57.className().contains("nwartbyln")) {
											Elements time = ee5.getElementsByTag("time");

											String date = time.text();
											if (date.contains("IST")) {
												date = date.substring(0, date.lastIndexOf("IST"));

												System.out.println(date);
											}

										}

									}

								
							}
						}

					}
					if (ee.className().contains("arbodyhide")) {
						Elements ee4 = ee.children();
						for (Element ee5 : ee4) {
							if (ee5.className().contains("nwcntrgt")) {
								Elements ee6 = ee5.children();
								for (Element ee7 : ee6) {
									if (ee7.className().contains("nwartcntdtl")) {

										Elements p = ee7.getElementsByTag("p");
										for (Element p2 : p) {
											String se = p2.text();
											if (!se.equals("")) {

												if (!p2.toString().contains("Tags")
														&& !p2.toString().contains("twitter")	&& !p2.toString().contains("सूचना")) {
													if (ss.equals("")) {
														ss += p2.text();

													} else {
														ss += "\n\n" + p2.text();
													}

												}
											}
//											System.out.println(ss);

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
