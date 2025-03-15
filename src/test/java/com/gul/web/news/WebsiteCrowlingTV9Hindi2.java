package com.gul.web.news;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;

import com.ctet.data.News;
import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingTV9Hindi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		List<News> newsList = new ArrayList<>();

//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect(
				"https://www.tv9hindi.com/education/madhya-pradesh-eligible-teacher-candidates-protest-in-bhopal-2033460.html")
				.get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().contains("detailBody")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee2 : divChildren2) {
					if (ee2.className().equals("article-HD")) {
						String head = ee2.text();
						System.out.println(head);
					}
					if (ee2.className().equals("author-box")) {

						String date = ee2.text();

						if (date.contains("on:")) {
							String tt = "on:";
							date = date.substring(date.lastIndexOf("on:") + tt.length(), date.length()).trim();

							System.out.println(date);
							System.out.println();
						}

					}
					if (ee2.className().equals("articleImg")) {

						Elements img = ee2.getElementsByTag("img");

						String src = img.attr("src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
						} else {
							System.out.println(src);
						}

					}

					if (ee2.className().contains("ArticleBodyCont")) {

						Elements p = ee2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ये भी पढ़ें") && !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text().trim();

									} else {
										ss += "\n\n" + p2.text().trim();
									}

								}
							}

						}
					}

				}

			}
		}
		System.out.println(ss);
	}

}
