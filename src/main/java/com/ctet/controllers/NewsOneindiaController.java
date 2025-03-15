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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.common.EmailGenericMessageThread;
import com.ctet.common.ExcelReader;
import com.ctet.common.PropertyConfig;
import com.ctet.data.News;
import com.ctet.repositories.NewsRepository;
import com.ctet.services.impl.SchedulerNews;
import com.ctet.services.impl.TaskSchedulerService;
import com.ctet.web.dto.JobOne;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;

@Controller
public class NewsOneindiaController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsOneindiaController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsOneindia" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
	 
		File file = ResourceUtils.getFile("classpath:NewsOneindia.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoOneindia.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			Document doc = Jsoup.connect(urlExcel).timeout(30000).get();

			System.out.println("url : " + doc.baseUri());
			Elements elements = doc.getAllElements();

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);

				Elements divChildren = element.children();

				for (Element element2 : divChildren) {

					if (element2.className().contains("oi-cityblock-list")) {

						Elements li = element2.getElementsByTag("li");
//						Elements divChildren2 = element2.children();

						for (Element ee : li) {

							News news = new News();
							Elements rr = ee.children();

							for (Element tt : rr) {

								if (tt.className().contains("cityblock-img news-thumbimg")) {

									Elements aurl = tt.getElementsByTag("a");
//									String title = aurl.attr("title");
									String href = "https://hindi.oneindia.com" + aurl.attr("href");
									System.out.println(href);

									Elements imgel = tt.getElementsByTag("img");
//									String img = imgel.attr("data-pagespeed-lazy-src");
									String img = imgel.attr("src");
									System.out.println(img);

									NewsDto newsDto = secondURl(href);
									news.setUrl(href);
									news.setMaindate(newsDto.getMaindate());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMainImage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());

									news.setSurl(urlExcel); // surl
									news.setSimage(img); // simage

									news.setWebsiteName(excel.getWebsiteName());
									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());

								}
								if (tt.className().contains("cityblock-desc")) {

									Elements hh = tt.children();
									for (Element ww : hh) {
										if (ww.className().contains("cityblock-title news-desc")) {
											System.out.println(ww.text());
											news.setSheadline(ww.text()); // sheadline
										}
										if (ww.className().contains("cityblock-time oi-datetime-cat")) {
											System.out.println(ww.text());
											news.setSdate(ww.text()); // stime

											newsList.add(news);
											System.out.println("........");
										}

									}
								}

							}

						}
					}
				}
			}

			for (News news2 : newsList) {

				if (news2.getSheadline() != null && news2.getSummary() != null) {
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
					System.out.println(news33.size());
				}
				System.out.println(newsList.size());
			}

//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}
		List<News> news = newsRepository.findAll();
		map.put("news", news);
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURl(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;
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

				if (element2.className().contains("oi-article-lt")) {
//					NewsDto newsDto = new NewsDto();

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
							ss2+=tt.text();
							newsDto.setSummary(ss2);
						}
					}

				}
			}
		}

		return newsDto;
	}

}
