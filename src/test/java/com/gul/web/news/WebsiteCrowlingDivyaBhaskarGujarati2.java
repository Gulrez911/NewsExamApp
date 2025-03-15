package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingDivyaBhaskarGujarati2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		int flag = 0;
		int flag1 = 0;
		Document doc = Jsoup.connect(
				"https://www.divyabhaskar.co.in/national/news/called-a-meeting-of-supporters-assembly-speaker-said-no-one-has-sued-the-party-131483739.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("ba1e62a6")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {

					if (ee.className().equals("bf64dc76")) {
						System.out.println("44444444");
						Elements ul = ee.children();

						for (Element eerr : ul) {

							if (eerr.className().equals("a88a1c42")) {
								Elements h1 = eerr.getElementsByTag("h1");
								String head = h1.text();
								System.out.println(head);

							}
							if (eerr.className().equals("f3e032cb")) {
								Elements img = eerr.getElementsByTag("img");
								for (Element img2 : img) {
									if (flag1 == 0) {
										String src = img2.attr("src");
										System.out.println(src);
										flag1 = 1;
									}

								}

							}
						 
						}

						if (flag == 0) {
							Elements p = ee.getElementsByTag("p");
							for (Element p2 : p) {
								String se = p2.text();
								if (!se.equals("")) {

									if (!p2.toString().contains("Tags")&&!p2.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += p2.text();

										} else {
											ss += "\n\n" + p2.text();
										}

									}
								}
//							System.out.println(ss);

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
