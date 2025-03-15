package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingOneindia2 {
	public static void main(String[] args) throws IOException {
		int flag = 0;
		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://hindi.oneindia.com/news/business/how-to-check-pan-card-aadhaar-card-linking-status-online-pan-aadhaar-update-759692.html?story=1";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("oi-article-lt")) {
					NewsDto newsDto = new NewsDto();

//					Elements li = element2.getElementsByTag("li");
					Elements divChildren2 = element2.children();

					for (Element tt : divChildren2) {

						if (tt.className().equals("heading")) {

							System.out.println(tt.text());
							newsDto.setMainheadline(tt.text());
						}
						if (tt.className().equals("author-detail clearfix")) {

							System.out.println(tt.text());
							newsDto.setMaindate(tt.text());
						}

						if (tt.className().contains("big_center_img")) {
							if (flag == 0) {

								String img = "https://hindi.oneindia.com" + tt.attr("data-gal-src");
								System.out.println(img);
								newsDto.setMainImage(img);
								flag = 1;
							}

						}
						if (tt.toString().contains("<p") && !tt.toString().contains("<span")) {

							System.out.println(tt.text());
							newsDto.setSummary(tt.text());
						}
					}

				}

			}

		}
		
		int count = 0;
		for (NewsDto newsDto : list) {
			if (newsDto.getMainheadline() != null && newsDto.getMainImage() != null) {
				count++;
			}
			System.out.println(": " + list.size());
			System.out.println(": count" + count);
		}
		
//		for (int ii = 1; ii < list.size(); ii++) {
//			NewsDto newsDto = list.get(ii);
//			System.out.println(ii + " : " + newsDto.getUrl() + " : " + newsDto.getMainheadline());
//		}
	}
}
