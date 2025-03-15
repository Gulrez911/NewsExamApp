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

public class WebsiteCrowlingHindustanTimesVideo {
	public static void main(String[] args) throws IOException {
		String ss2 = "";
		Document doc = Jsoup.connect("https://www.hindustantimes.com/videos").get();

		Elements elements = doc.getAllElements();
List<NewsDto> list = new ArrayList<NewsDto>();
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("home")) {

				Elements divChildren = element.children();
				NewsDto dto = new NewsDto();
				for (Element ddd223 : divChildren) {

					if (ddd223.className().contains("container")) {
						Elements divChildren3 = ddd223.children();
						for (Element ddd2 : divChildren3) {

							if (ddd2.className().equals("mainContainer")) {
								Elements dd2 = ddd2.children();
								for (Element dd3 : dd2) {
									if (dd3.className().equals("listingPage")) {

										Elements dd344 = dd3.children();
										NewsDto dto2 = new NewsDto();

										for (Element ddd22 : dd344) {

											if (ddd22.className().contains("cartHolder")) {
												Elements dd22 = ddd22.children();
												for (Element dd33 : dd22) {
													if (dd33.className().equals("hdg3")) {

														Elements url2 = dd33.getElementsByTag("a");
														String url = "https://www.hindustantimes.com"
																+ url2.attr("href");

														System.out.println(url);
														String sHeadline = dd33.text();
														System.out.println(sHeadline);
														dto.setMainheadline(sHeadline);

													
//												System.out.println("1");
													}
													if (dd33.toString().contains("<figure>")) {
														Elements img2 = dd33.getElementsByTag("img");
														String img = img2.attr("src");

														System.out.println(img);
														dto.setMainImage(img);
														
//														if (!dd33.toString().contains("storyShortDetail")) {
//															System.out.println("..................2");
//														}

//												System.out.println("2");
													}

													if (dd33.className().equals("storyShortDetail")) {
														String date = dd33.text();
												
							 							int startIndex =	date.indexOf("on ");					 
														date = date.substring(startIndex+3, date.length());
								
														System.out.println(date);
														
														
//														System.out.println(date);
														dto.setMaindate(date);
														list.add(dto);
														System.out.println("..................");
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
			}
		}
		
		System.out.println(": "+list.size());
	}
}
