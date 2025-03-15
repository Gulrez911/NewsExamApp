package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingJansatta2 {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://www.jansatta.com/national/isro-achieved-great-success-launched-india-largest-lvm3-rocket-with-36-satellites/2720267/";
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
					Elements divChildren2 = element2.children();

					for (Element ee3 : divChildren2) {

						NewsDto newsDto = new NewsDto();
						if (ee3.className().contains("wp-block-post-title")) {
							System.out.println(ee3.text());          //Mainheadline
						}
						if (ee3.className().contains("ie-network-post-meta")) {
							Elements tt = ee3.children();
							for (Element ee4 : tt) {
								if (ee4.className().contains("ie-network-post-meta-wrapper")) {
									System.out.println(ee4.text());
								}
							}

						}

						if (ee3.className().contains("wp-block-post-featured-image")) {
							Elements img = ee3.getElementsByTag("img");
							String imgMain = img.attr("src");
							System.out.println(imgMain);
//							newsDto.setMainImage(imgMain);
						}

						if (ee3.className().contains("wp-container-2 entry-content wp-block-post-content")) {

							Elements tt = ee3.children();

							for (Element ee4 : tt) {
								if (ee4.className().contains("pcl-container")) {

									Elements uu = ee4.children();

									for (Element ee5 : uu) {
//										String sss = ee5.text();
//										System.out.println(sss);

										if (!ee5.toString().contains("<div") && ee5.toString().contains("<p")) {

											if (ss2.equals("")) {
												ss2 += ee5.text();
											} else {
												ss2 += ee5.text() + "\n\n";
											}

											System.out.println(ss2);
										}

									}
								}
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
