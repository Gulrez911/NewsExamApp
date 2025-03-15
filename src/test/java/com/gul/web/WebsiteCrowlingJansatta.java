package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingJansatta {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://www.jansatta.com/business/";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

 			
				if (element2.className().contains("wp-container-3 wp-block-column ie-network-grid__lhs")) {

					System.out.println("...");
					Elements divChildren2 = element2.getElementsByTag("article");
//					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						Elements tt = ee.children();

							System.out.println("...");
						NewsDto newsDto = new NewsDto();

						for (Element hh : tt) {
							if (hh.toString().contains("<figure")) {
								Elements aurl = hh.getElementsByTag("a");
								String href = aurl.attr("href");
								System.out.println(href);
								newsDto.setUrl(href);

								Elements img = hh.getElementsByTag("img");
								String imgMain = img.attr("src");
								System.out.println(imgMain);
								newsDto.setMainImage(imgMain);
							}
							if (hh.className().contains("entry-wrapper")) {
								Elements jj = hh.children();
								for (Element kk : jj) {
									if (kk.className().contains("entry-title")) {
										String head = kk.text();
										System.out.println(head);
										newsDto.setMainheadline(head);
									}
									if (kk.className().contains("entry-meta-wrapper")) {
										String time = kk.text();
										System.out.println(time);
										newsDto.setMaindate(time);
										
									}
								}
								list.add(newsDto);
							}
							
						}

					}
				}

			}

		}
		for (int ii = 0; ii < list.size(); ii++) {
			NewsDto newsDto = list.get(ii);
			System.out.println(ii + " : " + newsDto.getMainImage());
		}
		System.out.println(": " + list.size());
	}
}
