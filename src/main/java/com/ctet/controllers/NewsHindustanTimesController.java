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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.common.ExcelReader;
import com.ctet.common.PropertyConfig;
import com.ctet.data.News;
import com.ctet.data.NewsVideo;
import com.ctet.repositories.NewsRepository;
import com.ctet.repositories.NewsVideoRepository;
import com.ctet.services.impl.TaskSchedulerService;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;
import com.ctet.web.dto.NewsVideoDto;

@Controller
public class NewsHindustanTimesController {

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	NewsVideoRepository newsVideoRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsHindustanTimesController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

	@RequestMapping(value = { "/getNewsVideo" })
	public @ResponseBody Map<String, Object> getNewsVideo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<NewsVideo> newsVideos = newsVideoRepository.findAllByOrderByIdDesc();
		map.put("all", newsVideos.size());
		map.put("newsVideos", newsVideos);

		return map;

	}

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsHindustantimesVideo" })
	public @ResponseBody Map<String, Object> getNewsHindustantimesVideo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Document doc = Jsoup.connect("https://www.hindustantimes.com/videos").get();
		List<NewsVideo> newsVideos = new ArrayList<>();
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

			if (element.className().contains("home")) {

				Elements divChildren = element.children();

				for (Element ddd223 : divChildren) {

					if (ddd223.className().contains("container")) {
						Elements divChildren3 = ddd223.children();
						for (Element ddd2 : divChildren3) {

							if (ddd2.className().equals("mainContainer")) {
								Elements dd2 = ddd2.children();
								for (Element dd3 : dd2) {
									if (dd3.className().equals("listingPage")) {

										Elements dd344 = dd3.children();

										for (Element ddd22 : dd344) {

											if (ddd22.className().contains("cartHolder")) {
												Elements dd22 = ddd22.children();

												NewsVideo newsVideo = new NewsVideo();
												for (Element dd33 : dd22) {
													if (dd33.className().equals("hdg3")) {

														Elements url2 = dd33.getElementsByTag("a");
														String url = "https://www.hindustantimes.com"
																+ url2.attr("href");

														System.out.println(url);

														String sHeadline = dd33.text();
														System.out.println(sHeadline);

														NewsVideoDto newsDto = secondURlVideo(url);

														newsVideo.setSheadline(sHeadline); // sheadline

														newsVideo.setUrl(newsDto.getUrl());

														newsVideo.setMaindate(newsDto.getMaindate());

														newsVideo.setMainVideoId(newsDto.getVideoId());
														;

//														newsVideo.setCategory(excel.getCategory());
// 														newsVideo.setLanguage(excel.getLanguage());
//														newsVideo.setWebsiteName(excel.getWebsiteName());

//														System.out.println("1");
													}
													if (dd33.toString().contains("<figure>")) {
														Elements img2 = dd33.getElementsByTag("img");
														String img = img2.attr("src");

														System.out.println(img);

														newsVideo.setSimage(img);
//																if (!dd33.toString().contains("storyShortDetail")) {
//																	System.out.println("..................2");
//																}

//														System.out.println("2");
													}

													if (dd33.className().equals("storyShortDetail")) {
														String date = dd33.text();

														int startIndex = date.indexOf("on ");
														date = date.substring(startIndex + 3, date.length());

														System.out.println(date);

//																System.out.println(date);
														newsVideo.setMaindate(date);
														newsVideos.add(newsVideo);
														System.out.println("..................");
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

		System.out.println(newsVideos.size());

		for (NewsVideo news2 : newsVideos) {

			if (news2.getSheadline() != null && news2.getSimage() != null) {
//			int ss = news2.getMainheadline().length();
//			System.out.println(ss);

				List<NewsVideo> news33 = newsVideoRepository.findTestBySimage2(news2.getSimage(), news2.getSheadline());
				if (news33.isEmpty()) {

					news2.setCreateDate(new Date());
					newsVideoRepository.save(news2);
				}
//				System.out.println(news33.size());
			}

		}
//		System.out.println(newsVideos.size());

		List<News> news = newsRepository.findAll();
		map.put("news", news);
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsVideoDto secondURlVideo(String url) throws IOException {

		NewsVideoDto newsDto = new NewsVideoDto();

		String ss2 = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
			Elements divChildren2 = element.children();
			for (Element ddd222 : divChildren2) {

				if (ddd222.className().equals("container")) {
					Elements divChildren23 = ddd222.children();

					for (Element ddd22233 : divChildren23) {
						if (ddd22233.className().equals("mainContainer")) {
							Elements divChildren = ddd22233.children();
//						NewsDto dto = new NewsDto();

							for (Element ddd2 : divChildren) {
								Elements dd12 = ddd2.children();
								for (Element ddd13 : dd12) {
									if (ddd13.className().equals("videoDetail")) {
										Elements dd2 = ddd13.children();
										for (Element dd3 : dd2) {

											if (dd3.className().equals("videoBox")) {
												Elements dd4 = dd3.children();
												for (Element dd5 : dd4) {
													Elements img2 = dd5.getElementsByTag("iframe");

													String videoId = img2.attr("src");
													String str2 = videoId;
													String str = "https://www.youtube.com/embed/";

													int startIndex = str.length();

													str2 = str2.substring(startIndex, str2.length());
													newsDto.setVideoId(str2);
													System.out.println(str2);
												}
											}

										}

									}

//									System.out.println("??????????????");
									System.out.println(ss2);
//				printWriter.close();
								}
							}
						}
					}

				}

			}
		}
		return newsDto;
	}

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsHindustantimes" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsHindustanTimes.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoHindustanTimes.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			String url22 = urlExcel + "/page-";
			for (int j = 1; j <= excel.getPage(); j++) {
				String bbcurl = url22 + j;

//				if (j<=12||j==14) {
//					continue;
//				}
				System.out.println(bbcurl);

				Document doc = Jsoup.connect(bbcurl).timeout(50000).ignoreHttpErrors(true).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {

					Element element = elements.get(i);
//					System.out.println("  ..........   "+element );
					if (element.className().equals("listingPage")) {

						Elements divChildren = element.children();

						for (Element ddd2 : divChildren) {
							if (ddd2.className().contains("cartHolder listView track")) {
								Elements dd2 = ddd2.children();
								News news = new News();
								for (Element dd3 : dd2) {
									if (dd3.className().equals("hdg3")) {

										Elements url2 = dd3.getElementsByTag("a");
										String url = "https://www.hindustantimes.com" + url2.attr("href");

										System.out.println(url);

										news.setSurl(bbcurl);
										NewsDto newsDto = secondURl(url);

										String sHeadline = dd3.text();
										System.out.println(sHeadline);

										news.setSheadline(sHeadline); // sheadline

										news.setUrl(newsDto.getUrl());
										news.setMainheadline(newsDto.getMainheadline());
										news.setMaindate(newsDto.getMaindate());
										news.setSummary(newsDto.getSummary());
										news.setMainImage(newsDto.getMainImage());

										news.setCategory(excel.getCategory());
//										news.setCreateDate(new Date());
										news.setLanguage(excel.getLanguage());
										news.setWebsiteName(excel.getWebsiteName());
//										System.out.println("1");
									}
									if (dd3.toString().contains("<figure>")) {
										Elements img2 = dd3.getElementsByTag("img");
										String img = img2.attr("data-src");

										System.out.println(img);

										news.setSimage(img);
//										System.out.println("2");
									}

									if (dd3.className().equals("storyShortDetail")) {
										String date = dd3.text();
										System.out.println(date);
										news.setSdate(date);
										newsList.add(news);
										System.out.println("..................");
									}

								}

							}
						}
					}
				}
			}

			for (News news2 : newsList) {

				System.out.println(newsList.size());
			}
			for (News news2 : newsList) {

				if (news2.getSheadline() != null && news2.getSummary() != null && news2.getMainImage() != null) {
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
					System.out.println(news33.size());
				}

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

		String ss2 = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("mainContainer")) {

				Elements divChildren = element.children();
//				NewsDto dto = new NewsDto();

				for (Element ddd2 : divChildren) {
					Elements dd12 = ddd2.children();
					for (Element ddd13 : dd12) {
						if (ddd13.className().equals("fullStory tfStory current videoStory story__detail")) {
							Elements dd2 = ddd13.children();
							for (Element dd3 : dd2) {
								if (dd3.className().equals("hdg1")) {

									String MainHeadline = dd3.text();
									System.out.println(MainHeadline);
									newsDto.setMainheadline(MainHeadline);
//								System.out.println("1");
								}
								if (dd3.className().contains("actionDiv flexElm topTime")) {

									Elements dd4 = dd3.children();
									for (Element dd5 : dd4) {
										if (dd5.className().contains("dateTime secTime storyPage")) {

											String mainDate = dd5.text();

											System.out.println(mainDate);
											newsDto.setMaindate(mainDate);
										}
									}
//								System.out.println("2");
								}

								if (dd3.className().contains("sortDec")) {
									String ss = dd3.text().trim();
									if (ss2.equals("")) {
										ss2 += ss;
									} else {
										ss2 += "\n\n" + ss;
									}
									System.out.println("<<<<<<<");
									System.out.println(ss2);
									newsDto.setSummary(ss2);
								}
								if (dd3.className().equals("storyDetails")) {
									Elements dd4 = dd3.children();
									for (Element dd5 : dd4) {
										if (dd5.className().contains("detail")) {

											Elements dd6 = dd5.children();
											for (Element dd7 : dd6) {
												if (dd7.className().contains("storyParagraphFigure")) {
													Elements dd8 = dd7.children();
													for (Element dd9 : dd8) {
														if (dd9.toString().contains("figure")) {
															Elements img2 = dd3.getElementsByTag("img");
															String img = img2.attr("src");
															System.out.println(img);
															newsDto.setMainImage(img);
														}

													}
												}

												if (dd7.toString().contains("<p>")) {

													if (!dd7.toString().contains("Also Read:")) {
														String ss = dd7.text().trim();

														if (!ss.equals("")) {
															if (ss2.equals("")) {
																ss2 += ss;
															} else {
																ss2 += "\n\n" + ss;
															}
															System.out.println("...........................");
															System.out.println(ss2);
															newsDto.setSummary(ss2);
														}

													}

												}

											}

//											Elements p = dd3.getElementsByTag("p");
//											System.out.println(p.text());

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
