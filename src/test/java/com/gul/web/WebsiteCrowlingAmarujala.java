package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingAmarujala {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://www.amarujala.com/cricket?src=mainmenu";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("page")) {
					NewsDto newsDto = new NewsDto();
					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("__main_listing_content")) {

							Elements rr = ee.children();

							for (Element tt : rr) {

								if (tt.toString().contains("<h2")) {
									System.out.println(tt.text());
									Elements aurl = tt.getElementsByTag("a");
									String href = "https://www.amarujala.com" + aurl.attr("href");
									System.out.println(href);
									ss2 += tt.text();
									newsDto.setMainheadline(ss2);
									newsDto.setUrl(href);
								}

								if (tt.className().contains("image")) {

									Elements gg = tt.children();

									for (Element gg2 : gg) {
										if (gg2.toString().contains("<picture")) {
											Elements imgel = tt.getElementsByTag("img");
											String img = "https:" + imgel.attr("src");
											System.out.println(img);
											newsDto.setMainImage(img);
										}
										if (gg2.toString().contains("<img")&&!gg2.toString().contains("<picture")) {
											Elements imgel = tt.getElementsByTag("img");
											String img = "https:" + imgel.attr("data-src");
											System.out.println(img);
											newsDto.setMainImage(img);
										}
										
									}

								}
								if (tt.className().contains("image_description")) {
									Elements aurl = tt.getElementsByTag("a");
									String title = aurl.attr("title");
									String href = "https://www.amarujala.com" + aurl.attr("href");
									System.out.println("href: " + href);
									System.out.println("title: " + title);

									ss2 += tt.text();
									newsDto.setMainheadline(ss2);
									newsDto.setUrl(href);
								}
								if (tt.className().contains("__timestamp followDv")) {

									System.out.println("Time: " + tt.text());
									System.out.println();
									newsDto.setMaindate( tt.text());
									list.add(newsDto);
								}
							}
						}

					}
				}

			}

		}
//		for (int ii = 1; ii < list.size(); ii++) {
//			NewsDto newsDto = list.get(ii);
//			System.out.println(ii + " : " + newsDto.getUrl() + " : " + newsDto.getMainheadline());
//		}
		System.out.println(": " + list.size());
	}
}
