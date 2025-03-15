package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingReportersNepalNepali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect("https://www.reportersnepal.com/2023/07/832633").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("col-md-8 text-justify post-side")) {
				System.out.println("2222222222222");
				Elements header = element.getElementsByTag("header");

				for (Element eed : header) {
					Elements eed3 = eed.children();

					for (Element eed3w : eed3) {
						if (eed3w.className().contains("single-heading text-center")) {
							Elements h1 = eed3w.getElementsByTag("h1");

							String head = h1.text();
							System.out.println(head);
							System.out.println();
						}

						if (eed3w.className().contains("row text-muted post-meta")) {
							Elements eed3w5 = eed3w.children();

							for (Element eed3w56 : eed3w5) {
								if (eed3w56.className().contains("col-sm-5")) {
									Elements eed3w566 = eed3w56.children();

									for (Element eed3w5667 : eed3w566) {
										if (eed3w5667.className().contains("d-flex mb-3")) {

											String date = eed3w5667.text();

											if (date.contains(":")) {

												int index4 = date.indexOf(":");
												date = date.substring(index4 + 1, date.length()).trim();

												System.out.println(date);
												System.out.println();
											}

										
										}
									}
								}
							}
						}
					}

				}
				Elements eed32 = element.children();

				for (Element eed324 : eed32) {
					if (eed324.className().contains("post-entry")) {

						Elements eed3245 = eed324.children();
						for (Element eed3243 : eed3245) {
							if (eed3243.className().contains("p-1 b-1 rounded mx-auto d-block")) {
								Elements img = eed3243.getElementsByTag("img");

								String src = img.attr("src");

								if (src.contains("?resize=")) {
									src = src.substring(0, src.lastIndexOf("?resize="));

								} else if (src.contains("?fit=")) {
									src = src.substring(0, src.lastIndexOf("?fit="));

								}

								System.out.println(src);
							}
						}
						Elements p = eed324.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!se.contains("https://t.me/") && !se.contains("Link:")
										&& !se.toString().contains("twitter.com") && !p2.toString().contains("<a href=")
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

				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("post-list d-flex")) {

						Elements a = ee.getElementsByTag("a");

						String href = a.attr("href");
						System.out.println(href);

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("media cat-media")) {
								Elements ee2554 = ee25566.children();
								for (Element ee255665 : ee2554) {
									if (ee255665.className().contains("mr-3")) {

										Elements img = ee25566.getElementsByTag("img");

										String src = img.attr("src");
										if (src.contains("?resize=")) {
											src = src.substring(0, src.lastIndexOf("?resize="));

										}
										System.out.println(src);
									}

									if (ee255665.className().contains("media-body")) {

										Elements h2 = ee25566.getElementsByTag("h5");

										String head = h2.text();
										System.out.println(head);
										System.out.println();

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
