package com.gul.web;

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

public class WebsiteCrowlingTV9hindi {
	public static void main(String[] args) throws IOException {

		int ii = 0;
		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		List<News> newsList = new ArrayList<>();
		String url = "https://www.tv9hindi.com/state";
//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect(url).get();

		News news;
		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("wrapper_section")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {
						Elements ss6 = ee.getElementsByTag("figure");
						news = new News();

						for (Element sde : ss6) {

							Elements ss1 = sde.children();

							for (Element ff : ss1) {
								if (ff.className().contains("imgThumb") || ff.toString().contains("figcaption")
										|| ff.toString().contains("time-stamp")) {
									if (ff.className().contains("imgThumb")) {
										Elements sd = ff.getElementsByTag("a");

										String href = sd.attr("href");

										System.out.println(href);
										news.setUrl(href);
										NewsDto newsDto = secondURl(href);
//
//										news.setMaindate(newsDto.getMaindate());
//
										news.setMainheadline(newsDto.getMainheadline());
										news.setMainImage(newsDto.getMainImage());
										news.setSummary(newsDto.getSummary());

										Element ddd = sd.get(0);
										if (ddd.toString().contains("data-src")) {

											Elements img = ddd.getElementsByTag("img");
											String src = img.attr("data-src");
											news.setSimage(src);
											System.out.println(src);
										} else {
											Elements img = ddd.getElementsByTag("img");
											String src = img.attr("src");
											news.setSimage(src);
											System.out.println(src);
										}
									}
									if (ff.toString().contains("figcaption")) {
										System.out.println(ff.text());
										news.setSheadline(ff.text());
//									news.setSimage(src);
									}

									if (ff.className().contains("time-stamp")) {
										System.out.println(ff.text());
										news.setSdate(ff.text());
										System.out.println(".....................");
										newsList.add(news);
									}
								}

							}
						}

					}
				}
			}
		}

		int count=0;
		for (News news2 : newsList) {
//			System.out.println(news2.getMainheadline());
//			if(news2.getSheadline() != null && news2.getSummary() != null && news2.getMainImage()!=null && news2.getSimage()!=null) {
				if(news2.getSheadline() != null && news2.getSummary() != null) {
				count++;
			}
		}
		System.out.println(count);
//		System.out.println(newsList.toString());
	}

	public static NewsDto secondURl(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		String ss2 = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("detailBody")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("article-HD")) { // Headline
							System.out.println(ee.text());
							newsDto.setMainheadline(ee.text());
						}
						if (ee.className().contains("articleImg")) { // Main Image

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
							System.out.println(ee.text());

							ss2 += ee.text();

							newsDto.setSummary(ss2);
						}

//						if (ee.className().contains("author-box")) {
//							System.out.println(ee.text());
//						}
//						if (ee.className().contains("summery")) {
//							System.out.println(ee.text());
//						}
//						if (ee.className().contains("ArticleBodyCont")) {
//							System.out.println(ee.text());
//						}

					}
				}
			}
		}

		return newsDto;
	}

}

//File file = new File("Sawaal.xlsx");
//InputStream stream = FileUtils.openInputStream(file);
//File f = ResourceUtils.getFile("classpath:swaal.xml");
//System.out.println("processing excel file " + f.getName());
