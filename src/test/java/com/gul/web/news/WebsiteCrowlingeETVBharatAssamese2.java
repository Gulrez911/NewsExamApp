package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingeETVBharatAssamese2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect(
				"https://www.etvbharat.com/assamese/assam/sports/other-sports/18-member-indian-mens-squad-announced-for-asian-champions-trophy-2023/assam20230725191017366366149")
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
						for (Element eee4 : eee) {
							if (eee4.className().contains("md:border-gray-500")) {
								Elements eee45 = eee4.children();
								for (Element eee456 : eee45) {
									if (eee456.className().contains("fresnel-greaterThan-xs")) {
										Elements eee4565 = eee456.children();
										for (Element eee456555 : eee4565) {
											if (eee456555.className().contains("flex justify-between items-center")) {
												Elements eee45655555 = eee456555.children();
												for (Element eee456555552 : eee45655555) {
													if (eee456555552.className().contains("always-english")) {
														String date = eee456555552.text();
														if (date.contains("Published:")) {
															String tt = "Published:";
															date = date.substring(
																	date.lastIndexOf("Published:") + tt.length(),
																	date.length()).trim();
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
					}
					if (ee.className().contains("text-base md:text-md")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("আরও পড়ুন:")&&!p2.toString().contains("twitter")&&!p2.toString().contains("www.instagram.com")&&!p2.toString().contains("https://www.etvbharat.com/")) {
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
