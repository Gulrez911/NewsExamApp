package com.ctet.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.common.ExcelReader;
import com.ctet.data.News;
import com.ctet.repositories.NewsRepository;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;

@Controller
public class NewsJansattaController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsJansatta" })
	public @ResponseBody Map<String, Object> getNewsBBC(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsJansatta.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoJansatta.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

			String urlExcel = excel.getMainLink();
//			int pageCount=Integer.parseInt(excel.getPage());  

			System.out.println(urlExcel);
			Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

			System.out.println("url : " + doc.baseUri());
			Elements elements = doc.getAllElements();

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);

				Elements divChildren = element.children();

				for (Element element2 : divChildren) {

					if (element2.className().contains("wp-container-3 wp-block-column ie-network-grid__lhs")) {

						System.out.println("...");
						Elements divChildren2 = element2.getElementsByTag("article");

						for (Element ee : divChildren2) {

							Elements tt = ee.children();

							System.out.println("...");
							News news = new News();

							for (Element hh : tt) {
								if (hh.toString().contains("<figure")) {
									Elements aurl = hh.getElementsByTag("a");
									String href = aurl.attr("href");
									System.out.println(href); 															// surl
									NewsDto newsDto = secondURl(href);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());
									news.setSummary(newsDto.getSummary());
									news.setMainImage(newsDto.getMainImage());

									Elements img = hh.getElementsByTag("img");
									String imgMain = img.attr("src");
									System.out.println(imgMain);
									news.setSurl(urlExcel);
									news.setSimage(imgMain);
								}
								if (hh.className().contains("entry-wrapper")) {
									Elements jj = hh.children();
									for (Element kk : jj) {
										if (kk.className().contains("entry-title")) {
											String head = kk.text();
											System.out.println(head);
											news.setSheadline(head);
										}
										if (kk.className().contains("entry-meta-wrapper")) {
											String time = kk.text();
											System.out.println(time);
											news.setSdate(time);

										}
									}
									
									
									
									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
									newsList.add(news);
								}

							}

						}
					}
				}

			}

			for (News news2 : newsList) {

				System.out.println(news2.getUrl() + " : " + news2.getMainheadline());
				System.out.println(" : " + news2.getSimage() + " : " + news2.getSheadline());
				if (news2.getSheadline() != null && news2.getSummary() != null) {

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
					System.out.println(news33.size());
				}
				System.out.println(newsList.size());
			}

			for (int ii = 0; ii < newsList.size(); ii++) {
				News news2 = newsList.get(ii);
				System.out.println(ii + " : " + news2.getUrl() + " : " + news2.getMainheadline());
				System.out.println(" : " + news2.getSimage() + " : " + news2.getSheadline());
			}
			System.out.println(": " + newsList.size());

			map.put("news", newsList);

		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURl(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;

		String ss = "";
		String ss2 = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);                             												//url
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("wp-container-3 wp-block-column ie-network-grid__lhs")) {

					System.out.println("...");
					Elements divChildren2 = element2.children();

					for (Element ee3 : divChildren2) {

//						NewsDto newsDto = new NewsDto();
						if (ee3.className().contains("wp-block-post-title")) {
							System.out.println(ee3.text());                      
							newsDto.setMainheadline(ee3.text());                // Mainheadline
						}
						if (ee3.className().contains("ie-network-post-meta")) {
							Elements tt = ee3.children();
							for (Element ee4 : tt) {
								if (ee4.className().contains("ie-network-post-meta-wrapper")) {
									System.out.println(ee4.text());                  // date
									newsDto.setMaindate(ee4.text());
								}
							}

						}

						if (ee3.className().contains("wp-block-post-featured-image")) {
							Elements img = ee3.getElementsByTag("img");
							String imgMain = img.attr("src");
							System.out.println(imgMain);
							newsDto.setMainImage(imgMain);                 //image
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
											newsDto.setSummary(ss2);         //summary
//											System.out.println(ss2);
										}

									}
								}
							}
						}

					}
				}
			}
		}

		return newsDto;
	}

}
