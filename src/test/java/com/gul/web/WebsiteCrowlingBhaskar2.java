package com.gul.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingBhaskar2 {
	public static void main(String[] args) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		String ss2 = "";
		String url = "https://www.bhaskar.com/national/news/mumbai-saraswati-vaidya-manoj-sahni-flat-murder-case-update-131384166.html";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bf64dc76")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("a88a1c42")) {
							System.out.println(ee.text()); // mainHeadline
							newsDto.setMainheadline(ee.text());
						}

						if (ee.className().contains("d1172d9b")) {
							System.out.println(ee.text()); // time
							newsDto.setMaindate(ee.text());
						}
						if (ee.className().contains("f3e032cb")) {

							Elements video = ee.getElementsByTag("video");

							String vid = video.attr("poster");
							System.out.println(vid); // Mainimage
							newsDto.setMainImage(vid);

						}
						
						String classs = ee.attr("class");
						
						
						if (classs.equals("")) {
							Elements pp = ee.getElementsByTag("p");
							ss= pp.text();
							if(ss2.equals("")) {
								ss2+=ss;
							}else {
								ss2+=ss+"\n\n";
								newsDto.setSummary(ss2);
							}
							
//							System.out.println(ss2);
						}
//						Elements pp = element2.getElementsByTag("p");
//						System.out.println(pp.text());

//						Elements ssss=	  ee.getElementById("ul");
//						if (ee.className().contains("efb7defa")) { // Headline
//							System.out.println(ee.text());
//							newsDto.setMainheadline(ee.text());
//						}

					}

				}
			}
		}

	}

}
