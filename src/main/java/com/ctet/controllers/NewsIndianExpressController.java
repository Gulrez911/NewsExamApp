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
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
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
public class NewsIndianExpressController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsIndianExpressController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

	@RequestMapping(value = { "/getNewsIndianExpress" })
	public @ResponseBody Map<String, Object> getNews2(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			// connect to the website
			String url = "https://www.jansatta.com/entertainment/kisi-ka-bhai-kisi-ki-jaan-box-office-collection-day-6-salman-khan-movie-100-crore/2781431/";

			Connection conn = Jsoup.connect(url).timeout(5000);
			conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.header("Accept-Encoding", "gzip, deflate, sdch");
			conn.header("Accept-Language", "zh-CN,zh;q=0.8");
			conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
					 
			Document doc = conn.get();

			// Elements elements = doc.body().select("tr.even detailed");
		 
			Elements elements = doc.getAllElements();
			System.out.println(elements.toString());
			System.out.println("...........");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return map;

	}

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsIndianExpress2" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<String> totalNewsCount = new ArrayList<>();
		File file = ResourceUtils.getFile("classpath:NewsIndianExpress.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoIndianExpress.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String bbcurl = excel.getMainLink();
//			String url22 = urlExcel + "/page/";
//			for (int j = 1; j <= excel.getPage(); j++) {
//				String bbcurl = url22 + j;

//				if (j<=12||j==14) {
//					continue;
//				}
			System.out.println(bbcurl);

			Document doc = Jsoup.connect(bbcurl).timeout(20000)
					.header("Content-Type", "application/x-www-form-urlencoded").cookie("TALanguage", "ALL")
					.data("mode", "filterReviews").data("filterRating", "").data("filterSegment", "")
					.data("filterSeasons", "").data("filterLang", "ALL").header("X-Requested-With", "XMLHttpRequest")
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.method(Method.POST).get();

			System.out.println("url : " + doc.baseUri());
			Elements elements = doc.getAllElements();

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

				if (element.className().equals("leftpanel")) {

					Elements divChildren = element.children();

					for (Element ddd2 : divChildren) {

						if (ddd2.className().contains("nation")) {
							Elements dd2 = ddd2.children();
							for (Element dd3 : dd2) {
								News news = new News();
								if (dd3.className().contains("articles")) {

									Elements dd4 = dd3.children();
									for (Element dd5 : dd4) {
										if (dd5.className().contains("snaps")) {

											Elements url2 = dd5.getElementsByTag("a");
											String url = url2.attr("href");

											System.out.println(url);

											Elements img2 = dd5.getElementsByTag("img");
											String img = img2.attr("src");

											System.out.println(img);
											news.setSurl(bbcurl);
											news.setSimage(img);
											NewsDto newsDto = secondURl(url);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());
											news.setMaindate(newsDto.getMaindate());
											news.setSummary(newsDto.getSummary());
											news.setMainImage(newsDto.getMainImage());

											news.setCategory(excel.getCategory());
//												news.setCreateDate(new Date());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());

										}
										if (dd5.className().contains("title")) {
											String title = dd5.text();
											System.out.println(title);
											news.setSheadline(title); // sheadline
										}
										if (dd5.className().contains("date")) {
											String date = dd5.text();
											System.out.println(date);
											System.out.println("..................");
											news.setSdate(date);
											newsList.add(news);
//												list.add(dto);
										}
									}

								}

							}

						}
					}
				}
			}
//			}

			for (News news2 : newsList) {

				System.out.println(newsList.size());
			}
			List<String> list = new ArrayList<String>();
			int count = 0;
			for (News news2 : newsList) {

				if (news2.getSheadline() != null && news2.getSummary() != null && news2.getMainImage() != null) {
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
						count++;
						list.add(news2.getSurl() + ": " + count);
					}
//					System.out.println(news33.size());
				}

			}

			totalNewsCount.addAll(list);
//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}
		System.out.println(totalNewsCount);
		List<News> news = newsRepository.findAll();
		map.put("news", news);
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURl(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss2 = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("container native_story")) {

				Elements divChildren = element.children();

				for (Element ddd2 : divChildren) {

					if (ddd2.className().contains("row")) {
						Elements dd2 = ddd2.children();
						for (Element dd3 : dd2) {
//							NewsDto dto = new NewsDto();
							if (dd3.className().contains("heading-part")) {

								Elements headLine = dd3.getElementsByTag("h1");

								String headline = headLine.text();

								System.out.println(headline);
								newsDto.setMainheadline(headline);
							}

							if (dd3.className().contains("leftpanel")) {
								Elements dd4 = dd3.children();
								for (Element dd5 : dd4) {
									if (dd5.className().contains("story-details")) {
										Elements dd6 = dd5.children();
										for (Element dd7 : dd6) {
											if (dd7.className().contains("main-story")) {
												Elements dd8 = dd7.children();
												for (Element dd9 : dd8) {
													if (dd9.className().contains("articles")) {
														Elements dd10 = dd9.children();
														for (Element dd11 : dd10) {
															if (dd11.className().contains("full-details")) {
																Elements dd12 = dd11.children();
																for (Element dd13 : dd12) {
																	if (dd13.className().contains("editor-share")) {
																		Elements dd14 = dd13.children();
																		for (Element dd15 : dd14) {
																			if (dd15.className().contains(
																					"editor editor-date-logo")) {
																				String date = dd15.text();
																				System.out.println(date);
																				newsDto.setMaindate(date);
																			}
																		}
																	}

																	if (dd13.className().contains("custom-caption")) {
																		Elements img2 = dd13.getElementsByTag("img");
																		String img = img2.attr("src");

																		System.out.println(img);
																		newsDto.setMainImage(img);
																	}

																	Element ss = dd13
																			.getElementById("pcl-full-content");

																	if (ss != null) {
																		Elements dd15 = ss.children();

																		for (Element dd16 : dd15) {

																			String sss = dd16.toString();
																			String sss2 = dd16.text();
																			if (sss.length() > 3) {
																				String ll = sss.substring(0, 5);

																				if (ll.contains("<p")) {

																					if (ss2.equals("")) {
																						ss2 += sss2;
																						newsDto.setSummary(ss2);
																					} else {
																						ss2 += "\n\n" + sss2;
																						newsDto.setSummary(ss2);
																					}
																					System.out.println(ss2);
																					continue;
																				}

																			}

																			if (dd16.className()
																					.contains("ev-meter-content")) {

																				Elements dd17 = dd16.children();

																				for (Element dd18 : dd17) {

//																					Elements ell21 = dd18.children();

																					String sss3 = dd18.toString();
																					String sss4 = dd18.text();
																					if (sss3.length() > 3) {
																						String ll = sss3.substring(0,
																								5);

																						if (ll.contains("<p")) {

																							if (ss2.equals("")) {
																								ss2 += sss4;
																								newsDto.setSummary(ss2);
																							} else {
																								ss2 += "\n\n" + sss4;
																								newsDto.setSummary(ss2);
																							}
																							System.out.println(ss2);
																							continue;
																						}

																					}

//																					ss2 += dd18.text();
//																					System.out.println(ss2);

																				}

																			}

																		}
																	}

																}

															}
														}
													}
												}
											}
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
