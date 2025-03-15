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

public class WebsiteCrowlingHindustanTimesVideo2 {
	public static void main(String[] args) throws IOException {
		String ss2 = "";
		Document doc = Jsoup.connect(
				"https://www.hindustantimes.com/videos/news/got-you-mangoes-pak-pm-sharifs-mango-conversation-with-erdogan-goes-viral-watch-101686019282235.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
			Elements divChildren2 = element.children();
			for (Element ddd222 : divChildren2) {

				
				if (ddd222.className().equals("container")) {
					Elements divChildren23 = ddd222.children();
					
					for (Element ddd22233 : divChildren23) {
						if (ddd22233.className().equals("mainContainer")) {
							Elements divChildren = ddd22233.children();
//						NewsDto dto = new NewsDto();

							for (Element ddd2 : divChildren) {
								Elements dd12 = ddd2.children();
								for (Element ddd13 : dd12) {
									if (ddd13.className().equals("videoDetail")) {
										Elements dd2 = ddd13.children();
										for (Element dd3 : dd2) {
										 
										 

										 
											if (dd3.className().equals("videoBox")) {
												Elements dd4 = dd3.children();
												for (Element dd5 : dd4) {
													Elements img2 = dd5.getElementsByTag("iframe");
													
													String videoId = img2.attr("src");
													String str2 =videoId;
													String str = "https://www.youtube.com/embed/";
											
													int startIndex =str.length();
																									 
													str2 = str2.substring(startIndex, str2.length());
							
													System.out.println(str2);
												}
											}

										}

									}

									System.out.println("??????????????");
									System.out.println(ss2);
//				printWriter.close();
								}
							}
						}
					}
				
					
				}
			
			}
		}
	}
}
