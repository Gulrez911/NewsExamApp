package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingPunjabijagranPunjabi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";

		Document doc = Jsoup.connect("https://www.punjabijagran.com/national/general-kashmere-gate-ito-yamuna-bank-and-red-for-flood-water-1-km-away-from-cm-residence-see-the-condition-in-pictures-and-videos-9252683.html").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("detailBox")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("articleHd")) {

						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						Elements ee44 = ee.children();

						for (Element ee445 : ee44) {
							if (ee445.className().contains("dateInfo")) {
								String date = ee445.text();
								if (date.contains(",")) {
									String tt = ",";
									date = date.substring(date.lastIndexOf(", ") + tt.length(),
											date.length());
									if (date.contains("(IST)")) {

										date = date.replace("(IST)", "").trim();
										System.out.println(date);
									}

								}

							}
						}

					}

					if (ee.className().contains("bodySummery")) {

						Elements img = ee.getElementsByTag("img");
						String src = "";
						if (img.toString().contains("data-src")) {
							src = img.attr("data-src");
						} else {
							src = img.attr("src");
						}
						System.out.println(src);
					}
					int flag = 0;
					if (ee.className().contains("articleBody")) {
						if (flag == 0) {
							Elements p = ee.getElementsByTag("p");
							for (Element p2 : p) {
								String se = p2.text();
//		System.out.println(se);
								if (!se.equals("")) {

									if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
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
							flag = 1;
						}
					}

				}

			}

		}
		System.out.println(ss);
	}

}
