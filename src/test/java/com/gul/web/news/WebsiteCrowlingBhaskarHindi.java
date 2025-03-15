package com.gul.web.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingBhaskarHindi {
	public static void main(String[] args) throws IOException {

		String url = "https://www.bhaskar.com/entertainment/";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);

			if (element.className().equals("e96634e0")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {

					Elements sss = ee.children();
					for (Element ww : sss) {

						if (ww.className().contains("ff407105") || ww.className().contains("db9a2680")) {
							Elements li = ww.getElementsByTag("li");
							for (Element weeeew : li) {

								Elements dddd = weeeew.children();
								int count = 0;
								for (Element wee2eew : dddd) {
									if (count == 0) {
										Elements a = wee2eew.getElementsByTag("a");

										String first = "https://www.bhaskar.com";
										String url22 = a.attr("href");
										if (!url22.equals("")) {
											String href = first + url22;
											System.out.println(href);
											for (Element aa : a) {
												Elements h3 = aa.getElementsByTag("h3");
												String head = h3.text();
												System.out.println(head);
												System.out.println();

											}
										}

										count = 1;
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
