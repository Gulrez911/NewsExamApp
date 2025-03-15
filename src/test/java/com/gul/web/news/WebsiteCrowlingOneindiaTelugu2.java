package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingOneindiaTelugu2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		int flag = 0;
		Connection connection = Jsoup.connect("https://telugu.oneindia.com/news/india/indigo-airlines-staff-felicitated-the-paramaveera-chakra-awardee-on-board-349461.html?ref_medium=Desktop&ref_source=OI-TE&ref_campaign=Topic-Article");
		connection.userAgent("Mozilla/5.0");
		Document doc = connection.get();
//		Document doc = Jsoup.connect(
//				"https://telugu.oneindia.com/news/india/indigo-airlines-staff-felicitated-the-paramaveera-chakra-awardee-on-board-349461.html?ref_medium=Desktop&ref_source=OI-TE&ref_campaign=Topic-Article")
//				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("oi-article-lt")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().equals("heading")) {
						Elements head = ee.getElementsByTag("h1");

						String h1 = head.text();
						System.out.println(h1);
					}
					if (ee.className().contains("author-detail")) {
						Elements time = ee.getElementsByTag("time");

						String date = time.text();
						if (date.contains(",")) {
							String tt = ",";
							 
							date = date.substring(date.indexOf(", ") + tt.length(),
									date.length());
							if (date.contains("[IST]")) {

								date = date.replace("[IST]", "").trim();
//								System.out.println(date);
							}

						}
						System.out.println(date);
					}
					if (ee.className().equals("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://telugu.oneindia.com" + img.attr("src");
									System.out.println(src);
								}
							}

						}

					}
					if (flag == 0) {
						Elements p = element.getElementsByTag("p");
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
//						System.out.println(ss);

						}
						flag = 1;
					}
				}
			}

		}
		System.out.println(ss);
	}

}
