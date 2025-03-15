package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingeETVBharatBangali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://www.etvbharat.com/bengali/west-bengal/bharat/man-allegedly-harass-young-woman-by-threatening-to-make-obscene-pictures-viral/wb20230705155209226226662")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("actual-content lg:container lg:mx-auto px-3 md:px-0 bg")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					if (ee.className().contains("flex flex-col md:flex-col-reverse md:mb-8")) {

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("src");
						System.out.println(src);

						Elements h1 = ee.getElementsByTag("h1");

						String head = h1.text();
						System.out.println(head);

						Elements eee = ee.children();
						for (Element ee22 : eee) {
							if (ee22.className().contains("md:border-gray-500")) {

								Elements eee3 = ee22.children();
								for (Element ee223 : eee3) {
									if (ee223.className().contains("fresnel-greaterThan-xs")) {

										Elements eee3f = ee223.children();
										for (Element ee223f : eee3f) {
											if (ee223f.className().contains("flex justify-between items-center")) {

												Elements eee3ff = ee223f.children();
												for (Element ee223xf : eee3ff) {
													if (ee223xf.className().contains("md:text-black always-english")) {

														String date = ee223xf.text();

														if (date.contains("Published: ")) {
															String tt = "Published: ";
															date = date.substring(date.lastIndexOf("Published: ")+ tt.length(),
																	date.length()).trim();

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
						}

					}
					if (ee.className().contains("text-base md:text-md")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("আরও পড়ুন:") && !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}

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
