package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingAmarujala2 {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";

		String url = "https://www.amarujala.com/india-news/supreme-court-reject-bail-plea-amrapali-group-ex-cmd-anil-kumar-sharma-said-not-deserve-sympathy-news-updates-2023-05-05?pageId=1";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
//		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("__article_detail auw-lazy-load article-read-bar-start")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.toString().contains("<h1")) {
							System.out.println(ee.text());
						}
						if (ee.toString().contains("auther-time")) {
							System.out.println(ee.text());
						}
						if (ee.className().contains("image")) {
							Elements imgel = ee.getElementsByTag("img");
							String img = "https:" + imgel.attr("src");
							System.out.println(img);
						}
						if (ee.className().contains("khas-batei ul_styling")) {

							Elements h2 = ee.getElementsByTag("h2");

							ss2 += h2.text();
							System.out.println(h2.text());
							System.out.println(".........");
						}
						if (ee.className().contains("article-desc ul_styling")) {

//							Elements ee2 = ee.children();

//							for (Element ee3 : ee2) {
//
//								 
//								System.out.println(ee3.text());
////								String dd =	ee3.nodeName();
////								ss2+=ee3.text();
////								System.out.println(ee.text());
//								System.out.println("................");
//							}

					 

//	Elements pp = ee.getElementsByTag("br");
//							
//							
//							for (Element ee2 : pp) {
//								
//								if (ss2.equals("")) {
//									ss2 += ee2.text();
//								} else {
//									ss2 += "\n\n" + ee2.text().replace("विस्तार ","");
//								}
//								
// 							
//							}

							Elements pp = ee.getElementsByTag("p");
							for (Element ee2 : pp) {

								if (ss2.equals("")) {
									ss2 += ee2.text();
								} else {
									ss2 += "\n\n" + ee2.text().replace("विस्तार ", "");
								}
							}
//							
//							
//							
//							ss2+=pp.text();
////							System.out.println(ee.text().replace("विस्तार ",""));
//							System.out.println("................");
//							System.out.println(ss2);

						}
					}
				}

			}

		}
		System.out.println(".................................");
		System.out.println(ss2);
//		for (int ii = 0; ii < list.size(); ii++) {
//			NewsDto newsDto = list.get(ii);
//			System.out.println(ii + " : " + newsDto.getUrl() + " : " + newsDto.getMainheadline());
//		}
//		System.out.println(": " + list.size());
	}
}
