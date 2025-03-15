package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.data.News;
import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingHindustanTimes {
	public static void main(String[] args) throws IOException {
		List<NewsDto> list = new ArrayList<>();
		Document doc = Jsoup.connect("https://www.hindustantimes.com/business").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("listingPage")) {

				Elements divChildren = element.children();
				NewsDto dto = new NewsDto();

				for (Element ddd2 : divChildren) {

					if (ddd2.className().contains("cartHolder listView track")) {
						Elements dd2 = ddd2.children();
						for (Element dd3 : dd2) {
							if (dd3.className().equals("hdg3")) {

								Elements url2 = dd3.getElementsByTag("a");
								String url = "https://www.hindustantimes.com"+url2.attr("href");
								
								System.out.println(url);
								String sHeadline = dd3.text();
								System.out.println(sHeadline);
								dto.setMainheadline(sHeadline);
//								System.out.println("1");
							}
							if (dd3.toString().contains("<figure>")) {
								Elements img2 = dd3.getElementsByTag("img");
								String img = img2.attr("data-src");

								System.out.println(img);
								dto.setMainImage(img);
//								System.out.println("2");
							}

							if (dd3.className().equals("storyShortDetail")) {
								String date = dd3.text();
								System.out.println(date);
								dto.setMaindate(date);
								list.add(dto);
								System.out.println("..................");
							}

						}

					}
				}
			}
		}
		System.out.println(list.size());
//		printWriter.close();
	}
}