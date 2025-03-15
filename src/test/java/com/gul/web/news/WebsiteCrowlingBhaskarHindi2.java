package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingBhaskarHindi2 {
	public static void main(String[] args) throws IOException {

		NewsDto newsDto = new NewsDto();
		int count = 0;
		String ss = "";
		String ss2 = "";
		String url = "https://www.bhaskar.com/local/rajasthan/dholpur/rajakhera/news/12-people-in-the-grip-of-diarrhea-in-rajkheda-131657164.html";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bf64dc76")) {
					System.out.println("22222222222");
					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("a88a1c42")) {

							Elements h1 = ee.getElementsByTag("h1");
							String head = h1.text();
							System.out.println(head);

						}

						if (ee.className().contains("d1172d9b")) {
							Elements ee2 = ee.children();

							for (Element ee3 : ee2) {

								if (ee3.className().contains("edd8d071")) {

									Elements ee4 = ee3.children();

									for (Element ee5 : ee4) {

										if (ee5.className().contains("c49a6b85")) {
											String date = ee5.text();
											System.out.println(date);
										}
									}

								}
							}
						}
						if (ee.className().contains("f3e032cb")) {

							if (count == 0) {
								Elements img = ee.getElementsByTag("img");

								String src = img.attr("src");
								System.out.println(src); // Mainimage
								count = 1;
							}

						}

						Elements a = ee.getElementsByTag("p");
						String sss = a.text();
						if (!sss.equals("")) {
//						System.out.println(sss);
							if (!sss.toString().contains("देखें") && !a.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += sss;
								} else {
									ss += "\n\n" + sss;
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
