package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingOneindia {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://hindi.oneindia.com/news/sports/?utm_medium=Desktop&utm_source=OI-HI&utm_campaign=menu-header";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("oi-cityblock-list")) {

					Elements li = element2.getElementsByTag("li");
//					Elements divChildren2 = element2.children();
					NewsDto newsDto = new NewsDto();
					for (Element ee : li) {

						Elements rr = ee.children();

						for (Element tt : rr) {

							if (tt.className().contains("cityblock-img news-thumbimg")) {

								Elements aurl = tt.getElementsByTag("a");
//								String title = aurl.attr("title");
								String href = "https://hindi.oneindia.com" + aurl.attr("href");
								System.out.println(href);

								Elements imgel = tt.getElementsByTag("img");
//								String img = imgel.attr("data-pagespeed-lazy-src");
								String img = imgel.attr("src");
								System.out.println(img);
								System.out.println("......................?????????????????................");
								newsDto.setUrl(url);
								newsDto.setMainImage(img);
							}
							if (tt.className().contains("cityblock-desc")) {

								Elements hh = tt.children();
								for (Element ww : hh) {
									if (ww.className().contains("cityblock-title news-desc")) {
										System.out.println(ww.text());
										newsDto.setMainheadline(ww.text());
									}
									if (ww.className().contains("cityblock-time oi-datetime-cat")) {
										System.out.println(ww.text());
										newsDto.setMaindate(ww.text());
										list.add(newsDto);
										System.out.println("........");
									}

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

		int count = 0;
		for (NewsDto newsDto : list) {
			if (newsDto.getMainheadline() != null && newsDto.getMainImage() != null) {
				count++;
			}
			System.out.println(": " + list.size());
			System.out.println(": count" + count);
		}
	}
}
