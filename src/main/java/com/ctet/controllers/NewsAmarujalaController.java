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
public class NewsAmarujalaController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsAmarujalaController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsAmarujala" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		 
		File file = ResourceUtils.getFile("classpath:NewsAmarujala.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoAmarujala.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			Document doc = Jsoup.connect(urlExcel).get();

			System.out.println("url : " + doc.baseUri());
			Elements elements = doc.getAllElements();

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);

				Elements divChildren = element.children();

				for (Element element2 : divChildren) {

					if (element2.className().contains("page")) {

						Elements divChildren2 = element2.children();

						for (Element ee : divChildren2) {
							if (ee.className().equals("__main_listing_content")
									|| ee.className().equals("__main_listing_content image_in_rgt")||ee.className().equals("__main_listing_content ")) {
								News news = new News();

								Elements rr = ee.children();

								String ss2 = "";
								for (Element tt : rr) {
									if (tt.toString().contains("<h2")) {
										System.out.println(tt.text());
										Elements aurl = tt.getElementsByTag("a");
										String href = "https://www.amarujala.com" + aurl.attr("href");
										System.out.println(href);
										ss2 += tt.text();

										NewsDto newsDto = secondURlAmarujala(href);
										news.setUrl(href);
										news.setMaindate(newsDto.getMaindate());
										news.setMainheadline(newsDto.getMainheadline());
										news.setMainImage(newsDto.getMainImage());
										news.setSummary(newsDto.getSummary());

										news.setSurl(urlExcel); // surl
										news.setSheadline(ss2); // sheadline

										news.setWebsiteName(excel.getWebsiteName());
										news.setCategory(excel.getCategory());
										news.setLanguage(excel.getLanguage());

									}
									if (tt.className().contains("image")) {

										Elements gg = tt.children();

										for (Element gg2 : gg) {

											if (gg2.toString().contains("<picture")) {
												Elements imgel = tt.getElementsByTag("img");
												String img = "https:" + imgel.attr("src");
												System.out.println(img);
												news.setSimage(img); // simage
											}
											if (gg2.toString().contains("<img")
													&& !gg2.toString().contains("<picture")) {
												Elements imgel = tt.getElementsByTag("img");
												String img = "https:" + imgel.attr("data-src");
												System.out.println(img);
												news.setSimage(img); // simage
											}

										}

									}
									if (tt.className().contains("image_description")) {
										Elements aurl = tt.getElementsByTag("a");
										String href = "https://www.amarujala.com" + aurl.attr("href");
										String title = aurl.attr("title");
										System.out.println("href: " + href);
										System.out.println("title: " + title);
//										news.setSheadline(title); // sdate
										NewsDto newsDto = secondURlAmarujala(href);
										news.setUrl(href);
										news.setMaindate(newsDto.getMaindate());
										news.setMainheadline(newsDto.getMainheadline());
										news.setMainImage(newsDto.getMainImage());
										news.setSummary(newsDto.getSummary());

										news.setSurl(urlExcel); // surl
										news.setSheadline(title); // sheadline

										news.setWebsiteName(excel.getWebsiteName());
										news.setCategory(excel.getCategory());
										news.setLanguage(excel.getLanguage());

									}
									if (tt.className().contains("__timestamp followDv")) {
										String time = tt.text();
										System.out.println("Time: " + time);
										news.setSdate(time); // sdate
										newsList.add(news);
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

	public static NewsDto secondURlAmarujala(String url) throws IOException {

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

				if (element2.className().contains("__article_detail auw-lazy-load article-read-bar-start")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.toString().contains("<h1")) {
							System.out.println(ee.text());
							newsDto.setMainheadline(ee.text()); // mainHeadline
						}
						if (ee.toString().contains("auther-time")) {
							System.out.println(ee.text());
							newsDto.setMaindate(ee.text()); // mainDate
						}
						if (ee.className().contains("image")) {
							Elements imgel = ee.getElementsByTag("img");
							String img = "https:" + imgel.attr("src");
							System.out.println(img);
							newsDto.setMainImage(img); // image
						}
						if (ee.className().contains("khas-batei ul_styling")) {

							Elements h2 = ee.getElementsByTag("h2");

							ss2 += h2.text();
							System.out.println(h2.text());
							System.out.println(".........");
							newsDto.setSummary(ss2); // summary
						}
						if (ee.className().contains("article-desc ul_styling")) {

							ss2 += ee.text().replace("विस्तार ", "");
							System.out.println(ee.text().replace("विस्तार ", ""));

							newsDto.setSummary(ss2); // summary

						}
					}
				}
			}
		}

		return newsDto;
	}

}
