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

public class WebsiteCrowlingTV9Hindi {
	public static void main(String[] args) throws IOException {

		List<News> newsList = new ArrayList<>();

//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect("https://www.tv9hindi.com/india").get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().contains("tv9_landingWidget")) {
				System.out.println("22222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements ss6 = ee.getElementsByTag("figure");

					for (Element sde : ss6) {

						Elements a = sde.getElementsByTag("a");
						String url = a.attr("href");
						System.out.println(url);
						System.out.println(a.text());
						for (Element ff : a) {
							Elements img = ff.getElementsByTag("img");

							String src = img.attr("data-src");
							if (src.contains("?w=")) {
								src = src.substring(0, src.lastIndexOf("?w="));
								System.out.println(src);
								System.out.println();
							} else {
								System.out.println(src);
							}
						}

					}
				}
			}
		}

	}

}
