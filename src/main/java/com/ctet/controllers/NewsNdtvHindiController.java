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
public class NewsNdtvHindiController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsNdtvHindiController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsNdtvHi" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsNdtvHi.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoNdtvHi.xml");
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
				if (bbcurl.contains("gadgets360")) {
					System.out.println("found:........");
					break;
				}
				Document doc = Jsoup.connect(bbcurl).timeout(50000).ignoreHttpErrors(true).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {

					Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

					if (element.className().equals("lisingNews")) {

						Elements gggg2 = element.getElementsByClass("news_Itm");

						for (Element ddd2 : gggg2) {
							News news = new News();
							Elements divChildren = ddd2.children();

							for (Element ddd22 : divChildren) {
								if (ddd22.className().equals("news_Itm-img")) {
									Elements img2 = ddd22.getElementsByTag("img");
									String src2 = img2.attr("src");

									System.out.println(src2);
									news.setSimage(src2);
									Elements aa = ddd22.getElementsByTag("a");
									String url = aa.attr("href");
									//
									System.out.println(url);
									NewsDto newsDto = secondURlNdtvHindi(url);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());

									news.setMainImage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());
									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
								}
								if (ddd22.className().equals("news_Itm-cont")) {

									Elements ddd2222 = ddd22.children();

									for (Element ddd222233 : ddd2222) {

										if (ddd222233.className().equals("posted-by")) {
											Elements span = ddd222233.getElementsByTag("time");

											String date = span.text();
											if (date.contains("Updated:")) {

												String tt = "Updated:";
												date = date.substring(date.lastIndexOf("Updated:") + tt.length(),
														date.length()).trim();
												System.out.println(date);
												news.setMaindate(date);

												news.setSdate(date);
											}

										}
										if (ddd222233.className().equals("newsCont")) {
											Elements sssss = ddd222233.getElementsByTag("p");
//											String href = ss2dd.attr("href");
//											System.out.println(href);
											String sheadline = sssss.html().replace("&nbsp;", " ");
											System.out.println(sheadline);
											news.setSheadline(sheadline);
											newsList.add(news);

											System.out.println("....................");

										}

									}

								}

							}

						}
					}
				}
			}

		 
			for (News news2 : newsList) {

				if (news2.getSheadline() != null && news2.getSummary() != null && news2.getMainImage() != null) {

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
					if (news33.size() == 1) {
						{
							for (News news22 : news33) {
								news22.setMaindate(news2.getMaindate());
								news22.setSdate(news2.getMaindate());
								news22.setUpdateDate(new Date());
								newsRepository.save(news22);
							}

						}

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

	public static NewsDto secondURlNdtvHindi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		int flag = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		String ss2 = "";
		String ss = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
			Elements heads = element.children();

//			section
			for (Element ddd2 : heads) {

				if (ddd2.className().contains("sp-hd")) {

					Elements date = ddd2.children();

					for (Element ssrr : date) {
						if (ssrr.className().contains("sp-ttl-wrp")) {

							Elements heads2 = ssrr.children();

							for (Element ssrraa : heads2) {
								if (ssrraa.className().contains("sp-ttl")) {
									Elements h1 = ssrraa.getElementsByTag("h1");
									String head = h1.text();
									if (!head.equals("")) {
										System.out.println(head);
										newsDto.setMainheadline(head);
									}

								}
							}

//							System.out.println(ssrr.text());
						}

					}

				}

//				

				if (ddd2.className().contains("row s-lmr")) {
					Elements headsw = ddd2.children();

					for (Element ell : headsw) {
						Elements ss2dd = ell.getElementsByTag("article");
						for (Element ell2 : ss2dd) {
							Elements ell3 = ell2.children();

							for (Element ell4 : ell3) {
								if (ell4.className().contains("sp-wrp")) {
//									System.out.println("//");
									Elements ell5ff = ell4.children();

									for (Element ell5ff8 : ell5ff) {
										if (ell5ff8.className().contains("sp-hd")) {
											Elements ell5ff85 = ell5ff8.children();

											for (Element ell5ff858 : ell5ff85) {
												if (ell5ff858.className().contains("sp-ttl-wrp")) {
													Elements ell5ff8588 = ell5ff858.children();

													for (Element ell5ff85885 : ell5ff8588) {
														if (ell5ff85885.className().contains("ins_storybody")) {
															Elements ell5fdf85885d = ell5ff85885.children();

															for (Element ell5ff85z885 : ell5fdf85885d) {
																if (ell5ff85z885.className()
																		.contains("ins_instory_dv")) {
																	Elements img = ell5ff85z885.getElementsByTag("img");
																	String src = img.attr("data-src");
																	System.out.println(src);
																	newsDto.setMainImage(src);
																	System.out.println("///////");
																}
																if (ell5ff85z885.className().contains("place_cont")) {
																	Elements b = ell5ff85z885.getElementsByTag("b");
																	String loc = b.text();
																	if (!loc.equals("")) {
																		ss += loc + " ";
																		flag = 1;
																	}

																}
															}

															Elements p = ell5ff85885.getElementsByTag("p");
															for (Element pp : p) {
																Elements ppp = pp.getElementsByTag("p");
																String first = ppp.toString();
																if (first.length() > 8) {
																	first = first.substring(0, 8);
																	if (!first.contains("class")) {
																		for (Element p2 : ppp) {
																			String se = p2.text();
																			if (!se.equals("")) {

																				if (!p2.toString().contains("Tags")
																						&& !p2.toString()
																								.contains("फॉलो")
																						&& !p2.toString()
																								.contains("twitter")
																						&& !p2.toString()
																								.contains("post")
																						&& !p2.toString()
																								.contains("देखें")
																						&& !p2.toString()
																								.contains("पढ़ें")) {
																					if (ss.equals("")) {
																						ss += p2.text();
																						newsDto.setSummary(ss);

																					} else {
																						if (flag == 1) {
																							ss += p2.text().trim();
																							newsDto.setSummary(ss);
																							flag = 0;
																						} else {
																							ss += "\n\n"
																									+ p2.text().trim();
																							newsDto.setSummary(ss);
																						}

																					}

																				}
																			}
//																System.out.println(ss);

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
//		

				}
			}

		}
		return newsDto;
	}

}
