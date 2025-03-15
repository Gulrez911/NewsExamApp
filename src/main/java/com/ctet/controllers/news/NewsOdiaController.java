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
public class NewsOdiaController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsOneindiaOdia" })
	public @ResponseBody Map<String, Object> getNewsOneindiaOdia(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Oneindia")) {

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

						if (element.className().contains("content clearfix")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {

								if (ee.className().contains("leftpanel")) {
									Elements article = ee.getElementsByTag("article");
									for (Element ee3 : article) {
										Elements ee34 = ee3.children();
										for (Element ee345 : ee34) {
											News news = new News();
											if (ee345.className().contains("collection-container")) {
												Elements ee4 = ee345.children();
												for (Element ee5 : ee4) {

													if (ee5.className().contains("article-img")) {
														Elements a = ee5.getElementsByTag("a");

														String href = "https://odia.oneindia.com" + a.attr("href");
														System.out.println(href);
														NewsDto newsDto = secondURlOneindiaOdia(href);

														news.setUrl(newsDto.getUrl());
														news.setMainheadline(newsDto.getMainheadline());

														news.setMainImage(newsDto.getMainImage());
														news.setSummary(newsDto.getSummary());
														news.setSurl(excel.getMainLink());
														news.setMaindate(newsDto.getMaindate());

														news.setSdate(newsDto.getMaindate());
														news.setCategory(excel.getCategory());
														news.setLanguage(excel.getLanguage());
														news.setWebsiteName(excel.getWebsiteName());
														Elements img = ee5.getElementsByTag("img");

														String src = "https://odia.oneindia.com" + img.attr("src");
														System.out.println(src);
														news.setSimage(src);
//												System.out.println();
													}
													if (ee5.className().contains("collection-heading")) {
														Elements a = ee5.getElementsByTag("a");

														String head = a.text();
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

	public static NewsDto secondURlOneindiaOdia(String url) throws IOException {

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

			if (element.className().equals("oi-article-lt")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("heading")) {
						Elements head = ee.getElementsByTag("h1");

						String h1 = head.text();
						System.out.println(h1);
						newsDto.setMainheadline(h1);
					}
					if (ee.className().contains("clearfix")) {
						Elements time = ee.getElementsByTag("time");

						String date = time.text();

						if (date.contains(",")) {

							int index4 = date.indexOf(',');
							date = date.substring(index4 + 1, date.lastIndexOf("[IST]")).trim();

							System.out.println(date);
							newsDto.setMaindate(date);
							System.out.println();
						}
					}
					if (ee.className().equals("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://odia.oneindia.com" + img.attr("src");
									System.out.println(src);
									newsDto.setMainImage(src);
								}
							}

						}

					}

					if (flag == 0) {
						Elements p = element.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text().trim();
										newsDto.setSummary(ss);

									} else {
										ss += "\n\n" + p2.text().trim();
										newsDto.setSummary(ss);
									}

								}
							}
//						System.out.println(ss);

						}
						flag = 1;
					}

				}

			}

		}

		return newsDto;
	}

//	2
	@RequestMapping(value = { "/getNewsZeeNewsOdia" })
	public @ResponseBody Map<String, Object> getNewsZeeNewsOdia(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Zee News")) {

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
						News news = new News();
						if (element.className().equals("news_item")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("news_right")) {

									Elements a = ee.getElementsByTag("a");
									String href = "https://zeenews.india.com" + a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlZeeNewsOdia(href);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());

									news.setSdate(newsDto.getMaindate());
//									news.setMainImage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());
									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
									Elements img = ee.getElementsByTag("img");

									for (Element img2 : img) {
										if (img2.toString().contains("data-src=\"https:")) {
											String src = img2.attr("data-src");
											if (src.contains("?itok")) {
												src = src.substring(0, src.lastIndexOf("?itok"));
												news.setSimage(src);
											}
											System.out.println(src);
											news.setSimage(src);
											news.setMainImage(src);
										}
									}

								}

								if (ee.className().contains("news_left")) {

									Elements ee8 = ee.children();
									for (Element ee9 : ee8) {

										if (ee9.className().contains("news_title")) {
											String head = ee9.text();
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

	public static NewsDto secondURlZeeNewsOdia(String url) throws IOException {

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

			if (element.className().contains("col-12")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("article_content")) {

						if (ee.toString().contains("<h1")) {
							System.out.println("33");
							Elements h1 = ee.getElementsByTag("h1");
							String head = h1.text();
							System.out.println(head);
							newsDto.setMainheadline(head);
						}
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("articleauthor_details")) {

								String date = ee3.text();

								if (date.contains(":")) {

									int index4 = date.indexOf(':');
									date = date.substring(index4 + 1, date.lastIndexOf("IST")).trim();

									System.out.println(date);
									newsDto.setMaindate(date);
									System.out.println();
								}

							}
						}

					}

					if (ee.className().contains("row")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("col-12 col-md-9")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("article_image")) {
										Elements img = ee5.getElementsByTag("img");

										for (Element img2 : img) {
											if (img2.toString().contains("src=\"https:")) {
												String src = img2.attr("data-src");
												if (src.contains("?im")) {
													src = src.substring(0, src.lastIndexOf("?im"));
													newsDto.setMainImage(src);
												}
												System.out.println(src);
												newsDto.setMainImage(src);
											}
										}
									}
									if (ee5.className().contains("article_content")) {
										Elements ee8 = ee5.children();

										for (Element ee9 : ee8) {
											if (ee9.className().contains("article_description")) {

												Elements p = ee9.getElementsByTag("p");
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
			}

		}

		return newsDto;
	}

//	3
	@RequestMapping(value = { "/getNewsEtvBharatOdia" })
	public @ResponseBody Map<String, Object> getNewsEtvBharatOdia(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("EtvBharat")) {

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

						if (element.className().contains("md:flex md:flex-wrap")) {
							System.out.println("2222222222222");
//							Elements divChildren2 = element.getElementsByTag("a");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news2 = new News();
								if (ee.className().contains("w-full flex space-x-2")) {
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {
										if (ee3.className().contains("flex w-full justify-between")) {

											Elements ee255 = ee3.children();
											for (Element ee25566 : ee255) {
												News news = new News();
												if (ee25566.className().contains("rounded-md shadow  w-1/3 bg-white")) {

													Elements a = ee25566.getElementsByTag("a");

													String href = "https://www.etvbharat.com" + a.attr("href");
													System.out.println(href);
													NewsDto newsDto = secondURlEtvBharatOdia(href);

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
													Elements ee4 = ee25566.children();
													for (Element ee5 : ee4) {
														if (ee5.className().contains("relative")) {

															Elements ee6 = ee5.children();
															for (Element ee7 : ee6) {
																if (ee7.className().contains("rounded-t-md w-full")) {
																	Elements img = ee7.getElementsByTag("img");
																	String src = img.attr("src");
																	news.setSimage(src);
																	System.out.println(src);
																}

															}

														}
														if (ee5.className().contains("text-gray-700 leading-tight")) {

															String head = ee5.text();

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
								if (ee.className().contains(
										"flex  justify-between px-1 pt-1 pb-1 cursor-pointer border shadow  rectangle-card bg-white mt-1 md:mt-2 md:w-1/2 rounded-md")) {

									Elements a = ee.getElementsByTag("a");

									String href = "https://www.etvbharat.com" + a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlEtvBharatOdia(href);

									news2.setUrl(newsDto.getUrl());
									news2.setMainheadline(newsDto.getMainheadline());
									news2.setMaindate(newsDto.getMaindate());

									news2.setSdate(newsDto.getMaindate());
									news2.setMainImage(newsDto.getMainImage());
									news2.setSummary(newsDto.getSummary());
									news2.setSurl(excel.getMainLink());

									news2.setCategory(excel.getCategory());
									news2.setLanguage(excel.getLanguage());
									news2.setWebsiteName(excel.getWebsiteName());
									Elements img = ee.getElementsByTag("img");
									String src = "";
									if (img.toString().contains("data-src")) {
										src = img.attr("data-src");
										news2.setSimage(src);
									} else {
										src = img.attr("src");
										news2.setSimage(src);
									}
									System.out.println(src);

									String head = ee.text();

									System.out.println(head);
									news2.setSheadline(head);
									newsList.add(news2);
									System.out.println();
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

	public static NewsDto secondURlEtvBharatOdia(String url) throws IOException {

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

			if (element.className().contains("actual-content lg:container lg:mx-auto px-3 md:px-0 bg")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					if (ee.className().contains("flex flex-col md:flex-col-reverse md:mb-8")) {

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("src");
						System.out.println(src);
						newsDto.setMainImage(src);
						Elements h1 = ee.getElementsByTag("h1");

						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
						Elements eee = ee.children();
						for (Element eee4 : eee) {
							if (eee4.className().contains("md:border-gray-500")) {
								Elements eee45 = eee4.children();
								for (Element eee456 : eee45) {
									if (eee456.className().contains("fresnel-greaterThan-xs")) {
										Elements eee4565 = eee456.children();
										for (Element eee456555 : eee4565) {
											if (eee456555.className().contains("flex justify-between items-center")) {
												Elements eee45655555 = eee456555.children();
												for (Element eee456555552 : eee45655555) {
													if (eee456555552.className().contains("always-english")) {
														String date = eee456555552.text();
														if (date.contains("Published:")) {
															String tt = "Published:";
															date = date.substring(
																	date.lastIndexOf("Published:") + tt.length(),
																	date.length()).trim();
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
					}
					if (ee.className().contains("text-base md:text-md")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("ଇଟିଭି ଭାରତ") && !p2.toString().contains("twitter")
										&& !p2.toString().contains("https://www.etvbharat.com/")) {
									if (ss.equals("")) {
										ss += p2.text().trim();
										newsDto.setSummary(ss);

									} else {
										ss += "\n\n" + p2.text().trim();
										newsDto.setSummary(ss);
									}

								}
							}
//							System.out.println(ss);

						}

					}

				}
			}
		}

		return newsDto;
	}
}
