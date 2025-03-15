package com.ctet.controllers.news;

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
import org.jsoup.HttpStatusException;
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
public class NewsNepaliController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsOnlineKhabarNepali" })
	public @ResponseBody Map<String, Object> getNewsOnlineKhabarNepali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("OnlineKhabar")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.get();

//				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//						count++;
//						System.out.println("...........  "+count);
						Element element = elements.get(i);
//						System.out.println("  ..........   "+element );

						if (element.className().equals("ok-grid-12")) {
							System.out.println("2222222222222");
//							Elements divChildren2 = element.getElementsByTag("a");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news = new News();
								if (ee.className().contains("span-4")) {

									Elements ee255 = ee.children();
									for (Element ee25566 : ee255) {
										if (ee25566.className().contains("ok-news-post")) {

											Elements a = ee25566.getElementsByTag("a");

											String href = a.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlOnlineKhabarNepali(href);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());
											news.setMaindate(newsDto.getMaindate());

											news.setSdate(newsDto.getMaindate());
											news.setMainImage(newsDto.getMainImage());
											news.setSummary(newsDto.getSummary());
											news.setSurl(excel.getMainLink());

											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());
											Elements img = ee25566.getElementsByTag("img");

											String src = img.attr("src");
											System.out.println(src);
											news.setSimage(src);
											Elements h2 = ee25566.getElementsByTag("h2");

											String head = h2.text();
											System.out.println(head);
											news.setSheadline(head);
											newsList.add(news);
											System.out.println();

										}
									}
								}
							}

						}
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpStatusException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlOnlineKhabarNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.get();
//		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("ok-single-middle")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("ok-container ok-post-header-container")) {

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("ok-entry-header")) {
								Elements ee24 = ee25566.children();
								for (Element ee27 : ee24) {
									if (ee27.className().contains("ok-post-title-right")) {
										Elements ee253 = ee27.children();
										for (Element ee254 : ee253) {
											if (ee254.className().contains("entry-title")) {
												Elements h1 = ee254.getElementsByTag("h1");

												String head = h1.text();
												System.out.println(head);
												newsDto.setMainheadline(head);
											}

											if (ee254.className().contains("ok-title-info flx")) {
												Elements ee2545 = ee254.children();
												for (Element ee25457 : ee2545) {
													if (ee25457.className().contains("ok-news-post-hour")) {

														Elements span = ee25457.getElementsByTag("span");

														String date = span.text();
														System.out.println(date);
														newsDto.setMaindate(date);
													}
												}

											}

										}
									}
								}
							}
						}
					}
					if (ee.className().contains("ok-section ok-page-details flx flx-wrp")) {
						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("entry-content")) {
								Elements ee3 = ee25566.children();
								for (Element ee4 : ee3) {
									if (ee4.className().contains("ok-post-detail-featured-img")) {
										Elements ee47 = ee4.children();
										for (Element ee473 : ee47) {
											if (ee473.className().contains("post-thumbnail")) {
												Elements img = ee473.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
												newsDto.setMainImage(src);
											}
										}
									}
									if (ee4.className().contains("ok18-single-post-content-wrap")) {

										Elements p = ee4.getElementsByTag("p");
										for (Element p2 : p) {
											String se = p2.text();
											if (!se.equals("")) {

												if (!se.contains("https://t.me/") && !se.contains("Link:")
														&& !se.toString().contains("twitter.com")
														&& !p2.toString().contains("<a href=")
														&& !se.contains("மேலும் படிக்க")) {
													if (ss.equals("")) {
														ss += p2.text().trim();
														newsDto.setSummary(ss);

													} else {
														ss += "\n\n" + p2.text().trim();
														newsDto.setSummary(ss);
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

//	2
	@RequestMapping(value = { "/getNewsThahakhabarNepali" })
	public @ResponseBody Map<String, Object> getNewsThahakhabarNepali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Thahakhabar")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
//					Connection connection = Jsoup.connect(urlExcel);
//					connection.userAgent("Mozilla/5.0");
//					Document doc = connection.get();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//						count++;
//						System.out.println("...........  "+count);
						Element element = elements.get(i);
//						System.out.println("  ..........   "+element );

						if (element.className().equals("category-collage")) {
							System.out.println("2222222222222");
//							Elements divChildren2 = element.getElementsByTag("a");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {

									if (ee3.className()
											.contains("border-radius-box border-box mb-30 border-category-grey")) {

										Elements ee255 = ee3.children();
										for (Element ee25566 : ee255) {

											News news = new News();
											if (ee25566.className().contains("row ml-0 mr-0")) {
												Elements ee2554 = ee25566.children();
												for (Element ee255665 : ee2554) {

													if (ee255665.className().contains(
															"col-lg-6 col-md-6 hot-post-left img-full-height d-none d-md-block pl-0 pr-0")) {

														Elements a = ee255665.getElementsByTag("a");

														String href = "https://thahakhabar.com" + a.attr("href");
														System.out.println(href);
														NewsDto newsDto = secondURlThahakhabarNepali(href);

														news.setUrl(newsDto.getUrl());
														news.setMainheadline(newsDto.getMainheadline());

														news.setMainImage(newsDto.getMainImage());
														news.setSummary(newsDto.getSummary());
														news.setSurl(excel.getMainLink());

														news.setCategory(excel.getCategory());
														news.setLanguage(excel.getLanguage());
														news.setWebsiteName(excel.getWebsiteName());
														for (Element aa : a) {
															Elements aa2 = aa.children();
															for (Element aa3 : aa2) {
																if (aa3.className()
																		.contains("post-bground-img-hover")) {

																	String style = aa3.attr("style");

																	if (style.contains(".jpg")) {
																		int sepPos = style.indexOf("https://");
																		style = style.substring(sepPos,
																				style.lastIndexOf(".jpg")) + ".jpg";
																		news.setSimage(style);

																	} else if (style.contains(".JPG")) {
																		int sepPos = style.indexOf("https://");
																		style = style.substring(sepPos,
																				style.lastIndexOf(".JPG")) + ".JPG";
																		news.setSimage(style);
																	}

																	System.out.println(style);
//																	System.out.println();
																}
															}

														}

													}

													if (ee255665.className().contains("col-lg-6 col-md-6 pl-0 pr-0")) {
														Elements ee2556653 = ee255665.children();
														for (Element ee2556643 : ee2556653) {
															if (ee2556643.className().contains("post-body p-30 m-0")) {
																Elements ee2535 = ee2556643.children();
																for (Element ee255636 : ee2535) {
																	if (ee255636.className().contains("mb-15")) {
																		Elements h3 = ee255636.getElementsByTag("h3");
																		String head = h3.text();
																		System.out.println(head);
																		news.setSheadline(head);
																	}
																	if (ee255636.className()
																			.contains("post-meta mb-10")) {
																		Elements aa2 = ee255636.children();
																		for (Element aa24 : aa2) {
																			if (!aa24.toString().contains("href")) {
																				String date = aa24.text();
																				System.out.println(date);
																				news.setMaindate(date);

																				news.setSdate(date);
																				newsList.add(news);
																				System.out.println();
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
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpStatusException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlThahakhabarNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
//		Connection connection = Jsoup.connect(url);
//		connection.userAgent("Mozilla/5.0");
//		Document doc2 = connection.get();
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("container")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("mb-30 post-body")) {

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("heading-title-50 mb-15")) {

								Elements h1 = ee25566.getElementsByTag("h1");
								String head = h1.text();
								System.out.println(head);
								newsDto.setMainheadline(head);

							}
						}

					}
					if (ee.className().contains("row")) {
						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("col-lg-9")) {
								Elements ee2552 = ee25566.children();
								for (Element ee566 : ee2552) {
									if (ee566.className().contains("detail-title-img mb-30")) {
										Elements ee2e552 = ee566.children();
										for (Element ee56 : ee2e552) {
											if (ee56.className().contains("post-img-section")) {
												Elements img = ee56.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
												newsDto.setMainImage(src);
											}
										}
									}

									if (ee566.className().contains("category-collage mb-30")) {
										Elements ee2e552 = ee566.children();
										for (Element ee8 : ee2e552) {
											if (ee8.className().contains("post-body")) {
												Elements ee2e = ee8.children();
												for (Element ee76 : ee2e) {
													if (ee76.className().contains(
															"detail-news-details-paragh detail-fontsize text-justify mb-30 post")) {

														Elements p = ee76.getElementsByTag("p");
														for (Element p2 : p) {
															String se = p2.text();
															if (!se.equals("")) {

																if (!p2.toString().contains("ଇଟିଭି ଭାରତ")
																		&& !p2.toString().contains("twitter")
																		&& !p2.toString().contains(
																				"https://www.etvbharat.com/")) {
																	if (ss.equals("")) {
																		ss += p2.text().trim();
																		newsDto.setSummary(ss);

																	} else {
																		ss += "\n\n" + p2.text().trim();
																		newsDto.setSummary(ss);
																	}

																}
															}
//															System.out.println(ss);

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

//	3
	@RequestMapping(value = { "/getNewsReportersNepalNepali" })
	public @ResponseBody Map<String, Object> getNewsReportersNepalNepali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Reporters Nepal")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.get();

//				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//						count++;
//						System.out.println("...........  "+count);
						Element element = elements.get(i);
//						System.out.println("  ..........   "+element );

						if (element.className().equals("col-md-8 text-justify")) {
							System.out.println("2222222222222");
//							Elements divChildren2 = element.getElementsByTag("a");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news = new News();
								if (ee.className().contains("post-list d-flex")) {

									Elements a = ee.getElementsByTag("a");

									String href = a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlReportersNepalNepali(href);
									news.setMaindate(newsDto.getMaindate());

									news.setSdate(newsDto.getMaindate());
									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());

									news.setMainImage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());
									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
									Elements ee255 = ee.children();
									for (Element ee25566 : ee255) {

										if (ee25566.className().contains("media cat-media")) {
											Elements ee2554 = ee25566.children();
											for (Element ee255665 : ee2554) {
												if (ee255665.className().contains("mr-3")) {

													Elements img = ee25566.getElementsByTag("img");

													String src = img.attr("src");
													if (src.contains("?resize=")) {
														src = src.substring(0, src.lastIndexOf("?resize="));
														news.setSimage(src);
													}
													System.out.println(src);
												}

												if (ee255665.className().contains("media-body")) {

													Elements h2 = ee25566.getElementsByTag("h5");

													String head = h2.text();
													System.out.println(head);
													news.setSheadline(head);
													newsList.add(news);
													System.out.println();

												}

											}
										}
									}

								}

							}
						}

					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpStatusException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlReportersNepalNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.get();
//		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("col-md-8 text-justify post-side")) {
				System.out.println("2222222222222");
				Elements header = element.getElementsByTag("header");

				for (Element eed : header) {
					Elements eed3 = eed.children();

					for (Element eed3w : eed3) {
						if (eed3w.className().contains("single-heading text-center")) {
							Elements h1 = eed3w.getElementsByTag("h1");

							String head = h1.text();
							System.out.println(head);
							newsDto.setMainheadline(head);
							System.out.println();
						}

						if (eed3w.className().contains("row text-muted post-meta")) {
							Elements eed3w5 = eed3w.children();

							for (Element eed3w56 : eed3w5) {
								if (eed3w56.className().contains("col-sm-5")) {
									Elements eed3w566 = eed3w56.children();

									for (Element eed3w5667 : eed3w566) {
										if (eed3w5667.className().contains("d-flex mb-3")) {

											String date = eed3w5667.text();

											if (date.contains(":")) {

												int index4 = date.indexOf(":");
												date = date.substring(index4 + 1, date.length()).trim();

												System.out.println(date);
												newsDto.setMaindate(date);
												System.out.println();
											}

										}
									}
								}
							}
						}
					}

				}
				Elements eed32 = element.children();

				for (Element eed324 : eed32) {
					if (eed324.className().contains("post-entry")) {

						Elements eed3245 = eed324.children();
						for (Element eed3243 : eed3245) {
							if (eed3243.className().contains("p-1 b-1 rounded mx-auto d-block")) {
								Elements img = eed3243.getElementsByTag("img");

								String src = img.attr("src");

								if (src.contains("?resize=")) {
									src = src.substring(0, src.lastIndexOf("?resize="));
									newsDto.setMainImage(src);

								} else if (src.contains("?fit=")) {
									src = src.substring(0, src.lastIndexOf("?fit="));
									newsDto.setMainImage(src);
								}

								System.out.println(src);
							}
						}
						Elements p = eed324.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!se.contains("https://t.me/") && !se.contains("Link:")
										&& !se.toString().contains("twitter.com") && !p2.toString().contains("<a href=")
										&& !se.contains("மேலும் படிக்க")) {
									if (ss.equals("")) {
										ss += p2.text().trim();
										newsDto.setSummary(ss);

									} else {
										ss += "\n\n" + p2.text().trim();
										newsDto.setSummary(ss);
									}

								}
							}

						}

					}
				}

				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {

					if (ee.className().contains("post-list d-flex")) {

						Elements a = ee.getElementsByTag("a");

						String href = a.attr("href");
						System.out.println(href);

						Elements ee255 = ee.children();
						for (Element ee25566 : ee255) {
							if (ee25566.className().contains("media cat-media")) {
								Elements ee2554 = ee25566.children();
								for (Element ee255665 : ee2554) {
									if (ee255665.className().contains("mr-3")) {

										Elements img = ee25566.getElementsByTag("img");

										String src = img.attr("src");
										if (src.contains("?resize=")) {
											src = src.substring(0, src.lastIndexOf("?resize="));

										}
										System.out.println(src);
									}

									if (ee255665.className().contains("media-body")) {

										Elements h2 = ee25566.getElementsByTag("h5");

										String head = h2.text();
										System.out.println(head);
										System.out.println();

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
