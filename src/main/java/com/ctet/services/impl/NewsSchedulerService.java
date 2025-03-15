package com.ctet.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ctet.common.ExcelReader;
import com.ctet.data.News;
import com.ctet.repositories.NewsRepository;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;

@Service
public class NewsSchedulerService {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");

	
	@Autowired
	NewsRepository newsRepository;
	
//	@Scheduled(cron = "0 */1 * * * *")  	  //per 5 min
	public void testNews() throws Exception {
		System.out.println(" Regular task1 performed using Cron at "
				+ dateFormat.format(new Date()));
	}
//	@Scheduled(cron = "0 */1 * * * *")  	  //per 5 min
	public void testNews2() throws Exception {
		System.out.println(" Regular task2 performed using Cron at "
				+ dateFormat.format(new Date()));
	}
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronOneindia() throws Exception {
		
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

									NewsDto newsDto = secondURlOneindia(href);
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
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlOneindia(String url) throws IOException {

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
	
//	@Scheduled(cron = "0 */15 * * * *")  	  //per 5 min
	public void performTaskUsingCronNdtvHi() throws Exception {
		
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

							Elements divChildren = ddd2.children();
							News news = new News();

							for (Element ddd22 : divChildren) {
								if (ddd22.className().equals("news_Itm-img")) {
									Elements img2 = ddd22.getElementsByTag("img");
									String src2 = img2.attr("src");

									System.out.println(src2);

									Elements aa = ddd22.getElementsByTag("a");
									String url = aa.attr("href");
									//
									System.out.println(url);
									if (url.contains("gadgets360") || url.contains("https://sports.ndtv")) {
										System.out.println("found:........");
										break;
									}
									NewsDto newsDto = secondURlNdtvHi(url);
									news.setSimage(src2);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());
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
//										if (ddd222233.className().equals("newsHdng")) {
//											Elements ss2dd = ddd222233.getElementsByTag("a");
//											String href = ss2dd.attr("href");
//											System.out.println(href);
////											System.out.println(ss2dd.text());
//										}
										if (ddd222233.className().equals("posted-by")) {
											Elements span = ddd222233.getElementsByTag("span");
											String sss = span.text();

											System.out.println("date: " + sss);
											news.setSdate(sss);
										}
										if (ddd222233.className().equals("newsCont")) {
											Elements sssss = ddd222233.getElementsByTag("p");
//											String href = ss2dd.attr("href");
//											System.out.println(href);
											String sheadline = sssss.html().replace("&nbsp;", " ");
											System.out.println(sheadline);
											System.out.println("....................");
											news.setSheadline(sheadline);
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
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlNdtvHi(String url) throws IOException {

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
			Elements head = element.children();

//			section
			for (Element ddd2 : head) {

				if (ddd2.className().equals("sp-hd")) {

//					Elements ss2dd = ddd2.getElementsByTag("h1");
//					System.out.println(ss2dd.text());
//					newsDto.setMainheadline(ss2dd.text());
					Elements date = ddd2.children();
					for (Element ssrr : date) {

						if (ssrr.className().equals("sp-ttl-wrp")) {

							Elements heads2 = ssrr.children();

							for (Element ssrraa : heads2) {
								if (ssrraa.className().equals("sp-ttl")) {
									System.out.println(".................");
									System.out.println(ssrraa.text());
									newsDto.setMainheadline(ssrraa.text());
									System.out.println(".................");
								}
							}

//							System.out.println(ssrr.text());
						}
//						if (ssrr.className().equals("sp-ttl-wrp")) {
//							Elements ss2dd = ssrr.getElementsByTag("h1");
//							System.out.println(ss2dd.text());
////							
////							System.out.println(ssrr.text());
//							newsDto.setMainheadline(ss2dd.text());
//						}
					}
					for (Element ssrr : date) {
						if (ssrr.className().equals("pst-by")) {
							System.out.println(ssrr.text());
							newsDto.setMaindate(ssrr.text());
						}
					}

				}

//				

				if (ddd2.className().equals("row s-lmr")) {
					Elements heads = ddd2.children();

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
																if (ell19.className().equals("ins_instory_dv_cont")) {

//																	Elements ell18 = ell19.children();
//																	for (Element ell20 : ell18) {
//																		if (ell20.className().equals(" story_pics")) {
//																			Elements img = ell20
//																					.getElementsByTag("img");
//																			String img2 = img.attr("src");
//																			System.out.println(img2);
//																		}
//																	}

																	if (flag4 == 0) {
																		String img2 = "";
																		Elements img = ell19.getElementsByTag("img");
																		if (img.toString().contains("data-src=")) {

																			img2 = img.attr("data-src");
																		} else {
																			img2 = img.attr("src");
																		}
																		if (!img2.contains("data:image/")) {

																			System.out.println(img2);
																			newsDto.setMainImage(img2);
																		}

																		flag4 = 1;
																	}

																}
															}

														}
//														ins_instory_dv

														String sss = ell12.toString();
														if (!sss.contains("ins_instory_dv")) {
															String sss2 = ell12.text();
															if (sss.length() > 5) {
																String ll = sss.substring(0, 5);

																if (ll.contains("<b ")) {

																	ss2 += sss2 + " ";
																	System.out.println(ss2);
																}
																if (ll.contains("<p")) {
																	ss2 += sss2;
																	System.out.println(ss2);
																	newsDto.setSummary(ss2);
																}

															}
														}
														if (ell12.className().contains("twitter-tweet")) {
															continue;
														}
														Elements ell17 = ell12.children();

														for (Element ell19 : ell17) {

															if (ell19.className().contains("reltd-main")) {
																continue;
															}

															if ((ell19.toString().contains("<p")
																	|| ell19.toString().contains("<b"))

															) {

//																String ss = ell19.text();

																Elements ell21 = ell19.children();

																String sss2 = ell19.text();
																if (sss.length() > 5) {
																	String ll = sss.substring(0, 10);

																	if (ll.contains("<p")) {
																		if (!sss2.contains("भी पढ़ें")) {
																			if (ss2.equals("")) {
																				ss2 += sss2;
																				newsDto.setSummary(ss2);
																			} else {
																				ss2 += "\n" + sss2;
																				newsDto.setSummary(ss2);
																			}
																		}
																		continue;
																	}

																}

																for (Element ell22 : ell21) {
//																		||!ell22.toString().contains("<ul")
																	if (ell22.toString().contains("href")) {
																		continue;
																	}
//																	if (ell22.toString().contains("<strong>")) {
//																		continue;
//																	}
																	if (ell22.toString().contains("<h3>")) {
																		continue;
																	}
																	if (ell22.toString().contains("देखें Video:")) {
																		continue;
																	}
//																	if (ell22.toString().contains("(Disclaimer: ")) {
//																		continue;
//																	}
//																	if (ell22.toString().contains("<br>")) {
//																		continue;
//																	}
																	String ss = ell22.text();

																	if (!ss.contains("हेडलाइन के अलावा")

																			&& !ell22.toString().contains("<div")
																			&& !ell22.toString().contains("<ul")) {

																		if (!ss.equals("")
																				&& !sss.contains("ins_instory_dv")) {

																			if (!ell19.className()
																					.contains("ft-social")) {
																				if (!ss.contains("भी पढ़ें")) {
																					if (ss2.equals("")) {
																						ss2 += ss;
																						newsDto.setSummary(ss2);
																					} else {
																						ss2 += "\n\n" + ss;
																						newsDto.setSummary(ss2);
																					}
																				}
																			}
																		}

//																			System.out.println(ss2);

																	}

																}

															}

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
//		

			}
		}
		return newsDto;
	}
	
//	@Scheduled(cron = "0 */15 * * * *")  	  //per 5 min
	public void performTaskUsingCronNdtv() throws Exception {
		
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
									NewsDto newsDto = secondURlNdtv(url);
									news.setSimage(src2);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());
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
											Elements span = ddd222233.getElementsByTag("span");
											String sss = span.text();

//											System.out.println(sss);
											news.setSdate(sss);
//											String sstt = news.getMainImage();

//											if (sstt != null) {
												newsList.add(news);
//											}
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
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlNdtv(String url) throws IOException {

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
//									System.out.println(ell.text());
									newsDto.setMaindate(ell.text()); // mainDate
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
																ss2 += ell12.text();
//																System.out.println(ss2);
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
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronJansatta() throws Exception {
		
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
									NewsDto newsDto = secondURlJansatta(href);

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


		}
		List<News> news = newsRepository.findAll();
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlJansatta(String url) throws IOException {

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
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronIndianExpress() throws Exception {
		
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
			String urlExcel = excel.getMainLink();
			String url22 = urlExcel + "/page/";
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

					if (element.className().equals("leftpanel")) {

						Elements divChildren = element.children();

						for (Element ddd2 : divChildren) {

							if (ddd2.className().contains("nation")) {
								Elements dd2 = ddd2.children();
								for (Element dd3 : dd2) {
									News  news = new News();
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
												NewsDto newsDto = secondURlIndianExpress(url);
 

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
			}

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
						list.add(news2.getSurl()+": "+count); 
					}
//					System.out.println(news33.size());
				}

			}

			totalNewsCount.addAll(list);
//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}
		System.out.println(totalNewsCount);
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlIndianExpress(String url) throws IOException {

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
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronHindustantimes() throws Exception {
		
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
										NewsDto newsDto = secondURlHindustantimes(url);

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
		System.out.println("test.............................................");
		
	}
	
	
	public static NewsDto secondURlHindustantimes(String url) throws IOException {

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
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronBhaskar() throws Exception {
		
		File file = ResourceUtils.getFile("classpath:NewsBhaskar.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBhaskar.xml");
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
//				System.out.println("  ..........   "+element );
//				fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//				if (element.className().equals("tbl-forkorts-article-active")) {

				Elements divChildren = element.children();

				for (Element element2 : divChildren) {

					if (element2.className().contains("ba1e62a6")) {

						Elements divChildren2 = element2.children();

						for (Element ee : divChildren2) {

							Elements divChildrenss = ee.children();

							for (Element elementss : divChildrenss) {
								//

								Elements ddd = elementss.children();
								News news = new News();
								for (Element ssdds : ddd) {
//									efb7defa
									if (ssdds.className().contains("efb7defa")) {
										System.out.println("........");

										Elements ww = ssdds.children();

										for (Element rr : ww) {
											if (rr.className().contains("c7ff6507 db9a2680")) {
												Elements aa = rr.getElementsByTag("a");
												String href = "https://www.bhaskar.com" + aa.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlBhaskar(href);

												news.setUrl(href);
												news.setMaindate(newsDto.getMaindate());
												news.setMainheadline(newsDto.getMainheadline());
												news.setMainImage(newsDto.getMainImage());
												news.setSummary(newsDto.getSummary());

												news.setSurl(urlExcel);
												news.setSdate(newsDto.getMaindate());

												news.setWebsiteName(excel.getWebsiteName());
												news.setCategory(excel.getCategory());
												news.setLanguage(excel.getLanguage());

												for (Element tt : aa) {
													Elements dfddd = tt.children();

													for (Element aabb : dfddd) {
														if (aabb.className().contains("f3426d1d")
																|| aabb.className().contains("ad3ccf1a")) {
															String title = aabb.attr("title");
															System.out.println(title);
															news.setSheadline(title);
														}
														Elements fff = aabb.children();
														for (Element dd : fff) {
															Elements ffff = dd.children();

															for (Element ggg : ffff) {
																Elements jj = ggg.children();
																for (Element iijj : jj) {

																	if (iijj.toString().contains("<img")) {

																		Elements aass = iijj.getElementsByTag("img");
																		String img = aass.attr("src");
																		System.out.println(img);
																		news.setSimage(img);
																		newsList.add(news);
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

								for (Element ddrr : ddd) {

									if (ddrr.className().contains("c7ff6507 db9a2680")) {

										Elements sss = ddrr.children();

										for (Element sdsd : sss) {
											String classs = sdsd.attr("class");

											if (classs.equals("")) {
												Elements a = sdsd.getElementsByTag("a");

												String href = "https://www.bhaskar.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlBhaskar(href);

												news.setUrl(href);
												news.setMaindate(newsDto.getMaindate());
												news.setMainheadline(newsDto.getMainheadline());
												news.setMainImage(newsDto.getMainImage());
												news.setSummary(newsDto.getSummary());

												news.setSurl(urlExcel);
												news.setSimage(newsDto.getMainImage());
												news.setSdate(newsDto.getMaindate());

												news.setWebsiteName(excel.getWebsiteName());
												news.setCategory(excel.getCategory());
												news.setLanguage(excel.getLanguage());

//												Elements dfddd = a.children();
												for (Element aa : a) {
													Elements dfddd = aa.children();
//													c62bd949
													for (Element aabb : dfddd) {

														if (aabb.className().contains("ad3ccf1a")) {
															String title = aabb.attr("title");
															System.out.println(title);
															news.setSheadline(title);

															newsList.add(news);

														}
//														c62bd949

//														Elements fff = aabb.children();
//														for (Element dd : fff) {
//															Elements ffff = dd.children();
														//
//															for (Element ggg : ffff) {
//																Elements jj = ggg.children();
														//
//																for (Element iijj : jj) {
														//
//																	if (iijj.toString().contains("<img")
//																			|| aabb.toString().contains("<img")) {
														//
//																		Elements aass = iijj.getElementsByTag("img");
//																		String img = aass.attr("src");
//																		System.out.println(img);
//																		newsDto.setMainImage(img);
//																		list.add(newsDto);
//																	}
														//
//																}
//															}
														//
//														}

													}
													//
												}

											}
//											System.out.println(sss.text());
										}

									}
									//
								}
								//
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


		}
		System.out.println("test.............................................");
		
	}
	
	public static NewsDto secondURlBhaskar(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;

		String ss = "";
		String ss2 = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bf64dc76")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("a88a1c42")) {
							System.out.println(ee.text()); // mainHeadline
							newsDto.setMainheadline(ee.text());
						}

						if (ee.className().contains("d1172d9b")) {
							System.out.println(ee.text()); // time
							newsDto.setMaindate(ee.text());
						}
						if (ee.className().contains("f3e032cb")) {

							Elements video = ee.getElementsByTag("video");

							String vid = video.attr("poster");
							System.out.println(vid); // Mainimage
							newsDto.setMainImage(vid);

						}

						String classs = ee.attr("class");

						if (classs.equals("")) {
							Elements pp = ee.getElementsByTag("p");
							ss = pp.text();
							if (ss2.equals("")) {
								ss2 += ss;
							} else {
								ss2 += ss + "\n\n";
							}
							newsDto.setSummary(ss2);

							System.out.println(ss2);
						}

					}

				}
			}
		}

		return newsDto;
	}
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronAmarujala() throws Exception {
		
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
	 
		System.out.println("test.............................................");
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
	
//	@Scheduled(cron = "0 */14 * * * *")  	  //per 5 min
	public void performTaskUsingCronBBCHi() throws Exception {
		
		
		File file = ResourceUtils.getFile("classpath:NewsBBC.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBBC.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
//			int pageCount=Integer.parseInt(excel.getPage());  
			String url22 = urlExcel + "?page=";
			for (int j = 1; j <= excel.getPage(); j++) {
				String bbcurl = url22 + j;
				System.out.println(bbcurl);
				Document doc = Jsoup.connect(bbcurl).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {

					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );
//				fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//				if (element.className().equals("tbl-forkorts-article-active")) {

					Elements divChildren = element.children();

					for (Element element2 : divChildren) {

						if (element2.className().contains("bbc-1kz5jpr")) {

							Elements divChildren2 = element2.children();

							for (Element ee : divChildren2) {

//							System.out.println("rrrrrr ");

								News news = new News();
								if (ee.className().contains("bbc-t44f9r")) {

									Elements sss = ee.getAllElements();
									for (Element ww : sss) {

										if (ww.className().contains("e1v051r10")) {
											Elements ss = ww.getAllElements();
											for (Element weeeew : ss) {
												if (weeeew.className().contains("promo-image")) {
													Elements dddd = weeeew.getAllElements();
													for (Element d : dddd) {

														if (d.className().contains("e5q9uf21")) {
															Elements sd = d.getAllElements();
															for (Element dd : sd) {
																if (dd.className().contains("bbc-6aqi2i")) {
																	Elements abc = dd.getAllElements();

																	for (Element aaa : abc) {

																		Elements abcd = aaa.children();

																		for (Element bbb : abcd) {
																			Elements dsdsds = bbb.children();

																			if (dsdsds.toString().contains("img")) {

																				String src2 = dsdsds.attr("src");
																				System.out.println(src2);
																				news.setSimage(src2); // simage
																			}
																		}
//																if(er.className().contains("e5q9uf21")) {
//																	
//																}
																	}

																}
															}
														}
													}
												}

												if (weeeew.className().contains("promo-text")) {
													Elements www = weeeew.getAllElements();
													for (Element ccc : www) {
														if (ccc.className().contains("e47bds20")) {

															Elements a = ccc.getElementsByTag("a");
															String url = a.attr("href");
															System.out.println(url); // url
															news.setSurl(bbcurl);
															NewsDto newsDto = secondURlBBCHi(url);
//															System.out.println(newsDto);
															System.out.println(a.text());
															news.setSheadline(a.text()); // sheadline

															news.setUrl(newsDto.getUrl());
															news.setMainheadline(newsDto.getMainheadline());
															news.setMaindate(newsDto.getMaindate());
															news.setSummary(newsDto.getSummary());
															news.setMainImage(newsDto.getMainImage());

														}
													}
												}

												if (weeeew.className().contains("e1mklfmt0")) {

													System.out.println(weeeew.text()); // date
													news.setSdate(weeeew.text());
													System.out.println();
													news.setCategory(excel.getCategory());
//													news.setCreateDate(new Date());
													news.setLanguage(excel.getLanguage());
													news.setWebsiteName(excel.getWebsiteName());
													newsList.add(news);
												}

											}
										}
									}
//								Elements sss = ee.getElementsByTag("img");
//								System.out.println(sss);
								}
//							bbc-6aqi2i

							}
						}
					}
				}

				System.out.println("Page no: "+j);
			}

			for (News news2 : newsList) {

				if (news2.getSheadline() != null && news2.getSummary() != null) {
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);
					
					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(),news2.getSheadline());
					if (news33.isEmpty()) {
						
						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
					System.out.println(news33.size());
				}
				System.out.println(newsList.size());
			}
			
//			map.put("news", newsList);

		}
 
		System.out.println("test.............................................");
		
		System.out.println(" Regular task2 performed using Cron at "
				+ dateFormat.format(new Date()));
		
		
	}
	
	public static NewsDto secondURlBBCHi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;

		String sum = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-irdbz7 ebmt73l0")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

//						System.out.println("rrrrrr ");
						Elements sdsds = ee.children();

//						if (sdsds.className().contains("bbc-19j92fr ebmt73l0")) {

//							Elements sss = ee.getAllElements();
						for (Element ww : sdsds) {

							if (ww.className().contains("bbc-1151pbn ebmt73l0")) {

								System.out.println("  Head:   " + ww.text()); // Headline
								newsDto.setMainheadline(ww.text());
							}
							if (ww.className().contains("e1j2237y6 bbc-q4ibpr ebmt73l0")) {

								Elements sss = ww.children();
								for (Element sde : sss) {

									Elements ssss = sde.children();
									for (Element ab : ssss) {
										Elements bc = ab.children();

										for (Element cd : bc) {

											if (cd.className().contains("bbc-1a3w4ok euvj3t11")) {
												System.out.println("  name:   " + cd.text());
												newsDto.setMainheadline(ww.text());
											}
											if (cd.className().contains("bbc-1p92jtu euvj3t10")) {
												System.out.println("  bbc:   " + cd.text());
												newsDto.setMainheadline(ww.text());
												break;
											}

										}

									}

								}
							}

							if (ww.className().contains("bbc-1ka88fa ebmt73l0")) {
								Elements sss = ww.children();

								for (Element cd : sss) {

									if (cd.className().contains("bbc-1qdcvv9 e1aczekt0")) {
										Elements we = cd.children();

										for (Element se : we) {
											if (se.className().contains("bbc-172p16q ebmt73l0")) {
												Elements sd = se.children();
												if (ii == 0) {
													for (Element sssd : sd) {
														if (sssd.className().contains("bbc-189y18v ebmt73l0")) {
//														System.out.println(sssd);
															Elements ss6 = sssd.getElementsByTag("img");
															String src22 = ss6.attr("src");
															System.out.println(src22);

															newsDto.setMainImage(src22);
														}
														if (sssd.className().contains("bbc-3edg7g ebmt73l0")) {
															System.out.println(sssd.text()); // image info

//															newsDto.setMainheadline(ww.text());
														}
													}
													ii++;
												}
											}
										}
									}
								}
							}

							if (ww.className().contains("e1j2237y7 bbc-q4ibpr ebmt73l0")) {

								Elements mm = ww.children();
								for (Element elementee : mm) {
									if (elementee.className().contains("bbc-zyc2da e1mklfmt0")) {
										System.out.println(elementee.text()); // date
										newsDto.setMaindate(elementee.text());
									}
								}

							}

							if (ww.className().contains("bbc-19j92fr ebmt73l0")) {
								Elements sss = ww.children();
								for (Element cd : sss) {
									if (cd.className().contains("bbc-kl1u1v e17g058b0")
											&& !cd.toString().contains("ीबीसी हिन्दी")&& !cd.toString().contains("ये भी पढ़ें")) {
										System.out.println(" p " + cd.text());
										sum += cd.text();
										newsDto.setSummary(sum);
										System.out.println(" ...............  ");
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
	
//	@Scheduled(cron = "*/3 * * * * *")
//	@Scheduled(cron = "*/50 * * * * *")
//	@Scheduled(cron = "0 0 * * * *")         //@hourly
//	@Scheduled(cron = "0 0/30 * * * *")         //@half_hourly
//	@Scheduled(cron = "0 */16 * * * *")  	  //per 5 min
	public void performTaskUsingCron() throws Exception {

//		System.out.println("Regular task performed using Cron at "
//				+ dateFormat.format(new Date()));
		
		System.out.println(" Regular task performed using Cron at "
				+ dateFormat.format(new Date()));
		
		
		
		
		File file = ResourceUtils.getFile("classpath:tv9hindi.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtotv9hindi.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			Document doc = Jsoup.connect(urlExcel).get();
			News news;

			System.out.println("url : " + doc.baseUri());
			Elements elements = doc.getAllElements();

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);

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
											System.out.println(excel.getMainLink());
											System.out.println(href);
											if (href.equals("")) {
												break;
											}
											news.setUrl(href);
											NewsDto newsDto = secondURltv9hindi(href);

											news.setMaindate(newsDto.getMaindate());

											news.setSurl(urlExcel);
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

											news.setWebsiteName(excel.getWebsiteName());
											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
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

				if (news2.getSheadline() != null && news2.getSummary() != null && news2.getMainImage() != null) {
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

					List<News> news33 = newsRepository.findTestBySimage(news2.getSimage(), news2.getSheadline());
					if (news33.isEmpty()) {

						news2.setCreateDate(new Date());
						newsRepository.save(news2);
					}
//					System.out.println(news33.size());
				}
				System.out.println(newsList.size());
			}

//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}
		
		
		
		
		

	}
	
	public static NewsDto secondURltv9hindi(String url) throws IOException {

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

				if (element2.className().contains("detailBody") || element2.className().contains("VideoDetailwrap")
						|| element2.className().contains("PhotoDetailwrap")
						|| element2.className().contains("ArticleBodyCont")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						if (ee.className().contains("article-HD")) { // Headline
							System.out.println(ee.text());
							newsDto.setMainheadline(ee.text());
						}
						if (ee.className().contains("articleImg") || ee.className().contains("ArticleBodywrap")) { // Main
																													// Image

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

							Elements sss = ee.children();
							for (Element cd : sss) {
								if (!cd.toString().contains("ये भी पढ़ें")) {
									System.out.println(" p " + cd.text());
									System.out.println(" ...............  ");
									ss2 += cd.text();

									newsDto.setSummary(ss2);
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
