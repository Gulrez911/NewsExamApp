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

public class WebsiteCrowlingIndianExpress {
	public static void main(String[] args) throws IOException {
		List<NewsDto> list = new ArrayList<>();
		Document doc = Jsoup.connect("https://indianexpress.com/section/india/page/2").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("leftpanel")) {

				Elements divChildren = element.children();

				for (Element ddd2 : divChildren) {

					if (ddd2.className().contains("nation")) {
						Elements dd2 = ddd2.children();
						for (Element dd3 : dd2) {
							NewsDto dto = new NewsDto();
							if (dd3.className().contains("articles")) {

								Elements dd4 = dd3.children();
								for (Element dd5 : dd4) {
									if (dd5.className().contains("snaps")) {

										Elements url2 = dd5.getElementsByTag("a");
										String url = url2.attr("href");

										System.out.println(url);

										Elements img2 = dd5.getElementsByTag("img");
										String img = img2.attr("src");

										System.out.println(img);
									}
									if (dd5.className().contains("title")) {
										String title = dd5.text();
										System.out.println(title);
									}
									if (dd5.className().contains("date")) {
										String date = dd5.text();
										System.out.println(date);
										System.out.println("..................");
										list.add(dto);
									}
								}

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