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
public class NewsNdtvController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsNdtvController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsNdtv" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsNdtvEn.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoNdtv.xml");
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

//									System.out.println(src2);

									Elements aa = ddd22.getElementsByTag("a");
									String url = aa.attr("href");
									//
									System.out.println(url);
									if (url.contains("gadgets360") || url.contains("https://sports.ndtv.com")) {
										System.out.println("found:........");
										break;
									}
									NewsDto newsDto = secondURl(url);
									news.setSimage(src2);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());
									news.setSdate(newsDto.getMaindate());
									news.setSummary(newsDto.getSummary());
									news.setMainImage(newsDto.getMainImage());

									news.setSurl(bbcurl);

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
								}
								if (ddd22.className().equals("news_Itm-cont")) {

									Elements ddd2222 = ddd22.children();

									for (Element ddd222233 : ddd2222) {

//										System.out.println(ddd222233);
										if (ddd222233.className().equals("newsHdng")) {
											Elements ss2dd = ddd222233.getElementsByTag("a");
//											String href = ss2dd.attr("href");
//											System.out.println(href);
//											System.out.println(ss2dd.text());

											news.setSheadline(ss2dd.text()); // sheadline
										}
										if (ddd222233.className().equals("posted-by")) {

//											Elements span = ddd222233.getElementsByTag("span");
//											String date = span.text();
//											if (date.contains("|")) {
//
////												date = date.replace(" | ", "");
//												date = date.substring(date.indexOf('|') + 2, date.length()).trim();
//											}
//
//											date = date.substring(date.indexOf(' '), date.lastIndexOf(",")).trim();
//
////											System.out.println(sss);
//											news.setSdate(date);
//											news.setMaindate(date);
											newsList.add(news);

										}

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

	public static NewsDto secondURl(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		int flag = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		String ss2 = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
			Elements section = element.getElementsByTag("section");
//			section
			for (Element ddd2 : section) {

				Elements h1 = ddd2.children();
				for (Element el : h1) {
					if (el.className().equals("sp-hd")) {

						Elements head = el.children();

						for (Element ell : head) {

							if (flag2 == 0) {
								if (ell.className().equals("sp-ttl-wrp")) {
									Elements ss2dd = ell.getElementsByTag("h1");
//								String href = ss2dd.attr("href");
//									System.out.println(ss2dd.text());
									newsDto.setMainheadline(ss2dd.text()); // mainHaedline
									flag2 = 1;
								}
							}
							if (flag == 0) {

								if (ell.className().equals("pst-by")) {
									

									String date = el.text();
									
									if (date.contains("Updated: ")&&date.contains("IST")) {
										String tt = "Updated: ";
										date = date.substring(date.lastIndexOf("Updated: ") + tt.length(), date.lastIndexOf("IST")).trim();
//										date = date.substring(date.lastIndexOf(" ") , date.length()).trim();
										System.out.println(date);
										System.out.println();
									}
									
//									System.out.println(ell.text());
								 
								
									
									
									
									
//									System.out.println(ell.text());
									newsDto.setMaindate(date); // mainDate
									flag = 1;
								}
							}

						}

					}

					if (el.className().equals("row s-lmr")) {
						Elements heads = el.children();

						for (Element ell : heads) {
							Elements ss2dd = ell.getElementsByTag("article");
							for (Element ell2 : ss2dd) {
								Elements ell3 = ell2.children();
								for (Element ell4 : ell3) {
									Elements ell5 = ell4.children();
									for (Element ell6 : ell5) {
										Elements ell7 = ell6.children();

										for (Element ell8 : ell7) {

											Elements ell9 = ell8.children();
											for (Element ell10 : ell9) {

												if (ell10.className().equals("sp-cn ins_storybody")) {
													Elements ell11 = ell10.children();
													if (flag3 == 0) {
														for (Element ell12 : ell11) {

															if (ell12.className().equals("ins_instory_dv")) {
																Elements ell17 = ell12.children();

																for (Element ell19 : ell17) {
																	if (ell19.className()
																			.equals("ins_instory_dv_cont")) {
																		if (flag4 == 0) {
																			Elements img = ell19
																					.getElementsByTag("img");
																			String img2 = img.attr("src");
																			if (!img2
																					.contains("data:image/svg+xml;b")) {

																				System.out.println(img2);
//																				System.out.println(img2);
																				newsDto.setMainImage(img2); // mainImage
																				flag4 = 1;
																			}

																		}
																	}
																}

															}

															if ((ell12.toString().contains("<p")
																	|| ell12.toString().contains("<b"))
																	&& !ell12.className().equals("ins_instory_dv")
																	&& !ell12.toString()
																			.contains("(Except for the headline")) {

																if (ss2.equals("")) {
																	ss2 += ell12.text();
																} else {
																	if (!ell12.text().equals("")) {
																		ss2 += "\n\n" + ell12.text();
																	}

																}

																newsDto.setSummary(ss2); // summary
//																System.out.println("...................");
															}

														}
														flag3 = 1;
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
