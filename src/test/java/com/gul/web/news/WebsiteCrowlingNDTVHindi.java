package com.gul.web.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.data.News;
import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingNDTVHindi {
	public static void main(String[] args) throws IOException {
		List<NewsDto> list = new ArrayList<>();
		Document doc = Jsoup.connect("https://ndtv.in/india").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("lisingNews")) {

				Elements gggg2 = element.getElementsByClass("news_Itm");
				NewsDto dto = new NewsDto();
				int count = 0;
				for (Element ddd2 : gggg2) {

					Elements divChildren = ddd2.children();

					for (Element ddd22 : divChildren) {
						if (ddd22.className().equals("news_Itm-img")) {
							Elements img2 = ddd22.getElementsByTag("img");
							String src2 = img2.attr("src");

							System.out.println(src2);
							dto.setMainImage(src2);
							Elements aa = ddd22.getElementsByTag("a");
							String url = aa.attr("href");
//
							System.out.println(url);
							dto.setUrl(url);
						}
						if (ddd22.className().equals("news_Itm-cont")) {

							Elements ddd2222 = ddd22.children();

							for (Element ddd222233 : ddd2222) {

//								System.out.println(ddd222233);
//								if (ddd222233.className().equals("newsHdng")) {
//									Elements ss2dd = ddd222233.getElementsByTag("a");
//									String href = ss2dd.attr("href");
//									System.out.println(href);
////									System.out.println(ss2dd.text());
//								}
								if (ddd222233.className().equals("posted-by")) {
									Elements span = ddd222233.getElementsByTag("time");

									String date = span.text();
									if (date.contains("Updated:")) {

										String tt = "Updated:";
										date = date.substring(date.lastIndexOf("Updated:") + tt.length(), date.length())
												.trim();
										System.out.println(date);
									}

								}
								if (ddd222233.className().equals("newsCont")) {
									Elements sssss = ddd222233.getElementsByTag("p");
//									String href = ss2dd.attr("href");
//									System.out.println(href);
									String sheadline = sssss.html().replace("&nbsp;", " ");
									System.out.println(sheadline);
									dto.setMainheadline(sheadline);

									System.out.println("....................");
									list.add(dto);
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
