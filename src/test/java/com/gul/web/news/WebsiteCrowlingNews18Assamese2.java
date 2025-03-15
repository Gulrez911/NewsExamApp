package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Assamese2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect(
				"https://assam.news18.com/news/nation/a-bus-catch-fire-in-an-expressway-in-maharastra-25-people-died-8-injured-in-the-accident-sb-307698.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   " );

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
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("nwartbximg")) {
								Elements img = ee3.getElementsByTag("img");
								String src = img.attr("src");
								if (src.contains("?im=")) {
									src = src.substring(0, src.lastIndexOf("?im="));
									System.out.println(src);
								}
							}
							if (ee3.className().contains("nwartbxdtl")) {
								Elements ee34 = ee3.children();
								for (Element ee344 : ee34) {
									if (ee344.className().contains("nwartbyln")) {

										Elements time = ee344.getElementsByTag("time");

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

					if (ee.className().contains("nwartcnt arbodyactive arbodyhide")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("nwcntrgt")) {
								Elements ee24 = ee3.children();
								for (Element ee35 : ee24) {
									if (ee35.className().contains("nwartcntdtl")) {

										Elements p = ee35.getElementsByTag("p");
										for (Element p2 : p) {
											if (!p2.toString().contains("Tags") && !p2.toString().contains("twitter")) {
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
		System.out.println(ss);
	}

}
