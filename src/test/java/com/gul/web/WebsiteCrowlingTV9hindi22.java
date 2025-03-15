package com.gul.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingTV9hindi22 {
	public static void main(String[] args) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		String ss2 = "";
		String url = "https://www.tv9hindi.com/world/pakistan-news/imran-khan-toshakhana-case-live-updates-pti-workers-arrested-road-accident-pakistan-au112-1773382.html";
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("detailBody") || element2.className().contains("VideoDetailwrap")
						|| element2.className().contains("PhotoDetailwrap")
						|| element2.className().contains("ArticleBodyCont")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("article-HD")) { // Headline
							System.out.println(ee.text());
							newsDto.setMainheadline(ee.text());
						}
						if (ee.className().contains("articleImg") || ee.className().contains("ArticleBodywrap")) { // Main
																													// Image

							Elements img = ee.getElementsByTag("img");
							String src = img.attr("src");

							System.out.println(src);
							newsDto.setMainImage(src);
						}

						if (ee.className().contains("author-box")) { // date
							System.out.println(ee.text());

							ss += ee.text();

							newsDto.setMaindate(ss);
						}

						if (ee.className().contains("summery") || ee.className().contains("ArticleBodyCont")) { // summary

							Elements sss = ee.children();
							for (Element cd : sss) {
								if (!cd.toString().contains("ये भी पढ़ें")) {
									System.out.println(" p " + cd.text());
									System.out.println(" ...............  ");
									ss2 += cd.text();

									newsDto.setSummary(ss2);
								}
							}

						}

					}
				}
			}
		}

	}

}
