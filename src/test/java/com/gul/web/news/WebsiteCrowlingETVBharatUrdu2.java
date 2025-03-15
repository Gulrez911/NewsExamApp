package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingETVBharatUrdu2 {
	public static void main(String[] args) throws IOException {

		String ss = "";
		Document doc = Jsoup.connect(
				"https://www.etvbharat.com/urdu/national/state/jammu/do-not-use-much-mobile-phone-says-eye-specialist-dr-pranav-gupta/na20230605151722562562004")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("flex flex-col md:flex-col-reverse md:mb-8")) {
				Elements img = element.getElementsByTag("img");

				String src = img.attr("src");
				System.out.println(src);

				Elements h1 = element.getElementsByTag("h1");

				System.out.println(h1.text());

				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("mb-3 md:border-b-2 md:border-gray-500")) {
						Elements ee33 = ee.children();

						for (Element ee4 : ee33) {
							if (ee4.className().contains("fresnel-container fresnel-greaterThan-xs")) {
								Elements ee334 = ee4.children();

								for (Element ee54 : ee334) {
									if (ee54.className()
											.contains("text-sm text-gray-600 md:text-black always-english")) {
										String date = ee54.text();

										if (date.contains("Published: ")) {
											String tt ="Published: ";
											date = date.substring(date.lastIndexOf("Published: ") +tt.length()  , date.length());
											System.out.println(date);

										}
									}
								}
							}
						}
					}
				}
			}

			if (element.className().equals("text-base md:text-md")) {
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					if (ee.className().equals("content")) {

						Elements ee33 = ee.getElementsByTag("p");

						for (Element ee44 : ee33) {

							if (!ee44.toString().contains("پڑھیں") && !ee44.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += ee44.text();
									System.out.println(ss);
								} else {
									ss += "\n\n" + ee44.text();
									System.out.println(ss);
								}
							}

						}

					}
				}
			}
		}

	}

}
