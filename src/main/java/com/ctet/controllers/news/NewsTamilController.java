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
public class NewsTamilController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsOneindiaTamil" })
	public @ResponseBody Map<String, Object> getNewsOneindiaTamil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
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

						if (element.className().contains("oi-cityblock-list")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {
									if (ee3.className().contains("clearfix")) {
										Elements ee4 = ee3.children();
										News news = new News();
										for (Element ee5 : ee4) {
											if (ee5.className().contains("cityblock-img news-thumbimg")) {
												Elements a = ee5.getElementsByTag("a");

												String href = "https://tamil.oneindia.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlOneindiaTamil(href);

												news.setUrl(newsDto.getUrl());
												news.setMainheadline(newsDto.getMainheadline());

												news.setMainImage(newsDto.getMainImage());
												news.setSummary(newsDto.getSummary());
												news.setSurl(excel.getMainLink());

												news.setCategory(excel.getCategory());
												news.setLanguage(excel.getLanguage());
												news.setWebsiteName(excel.getWebsiteName());
												Elements img = ee5.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
												news.setSimage(src);
//												System.out.println();
											}
											if (ee5.className().contains("cityblock-desc")) {

												Elements ee55 = ee5.children();
												for (Element ee555 : ee55) {
													if (ee555.className().contains("cityblock-title news-desc")) {

														String head = ee555.text();
														System.out.println(head);
														news.setSheadline(head);
													}
													if (ee555.className().contains("cityblock-time")) {
														String date = ee555.text();

														if (date.contains(",")) {

															int index4 = date.indexOf(',');
															date = date.substring(index4 + 1, date.lastIndexOf("[IST]"))
																	.trim();

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

	public static NewsDto secondURlOneindiaTamil(String url) throws IOException {

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
					if (ee.className().equals("heading")) {
						Elements head = ee.getElementsByTag("h1");

						String h1 = head.text();
						System.out.println(h1);
						newsDto.setMainheadline(h1);
					}
					if (ee.className().equals("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://tamil.oneindia.com" + img.attr("src");
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
	@RequestMapping(value = { "/getNewsNews18Tamil" })
	public @ResponseBody Map<String, Object> getNewsNews18Tamil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("News18")) {

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

						if (element.className().contains("blog_list")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("blog_list")) {
									System.out.println("3333");
									Elements ee22 = ee.children();
									for (Element ee55 : ee22) {
										News news = new News();
										if (ee55.className().contains("blog_list_row")) {
											Elements a = ee55.getElementsByTag("a");

											String href = a.attr("href");
											System.out.println(href);

											NewsDto newsDto = secondURlNews18Tamil(href);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());

											news.setMainImage(newsDto.getMainImage());
											news.setSimage(newsDto.getMainImage());
											news.setSummary(newsDto.getSummary());
											news.setSurl(excel.getMainLink());
											news.setMaindate(newsDto.getMaindate());

											news.setSdate(newsDto.getMaindate());
											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());

											Elements ee5566 = ee55.children();
											for (Element ee55661 : ee5566) {
												Elements ee5566155 = ee55661.children();
												for (Element ee556631 : ee5566155) {
													if (ee556631.className().contains("blog_img")) {
														Elements img = ee556631.getElementsByTag("img");

														String src = img.attr("data-src");
														if (src.contains("?im")) {
															src = src.substring(0, src.lastIndexOf("?im"));
															System.out.println(src);
															news.setSimage(src);
														} else {
															System.out.println(src);
															news.setSimage(src);
														}
													}
												}

											}

											System.out.println(ee55.text());
											news.setSheadline(ee55.text());

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

	public static NewsDto secondURlNews18Tamil(String url) throws IOException {

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

			if (element.className().contains("artcl_container")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("artcl_lft")) {
						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("artcl_contents")) {
								Elements ee4 = ee3.children();
								for (Element ee5 : ee4) {
									if (ee5.className().contains("artcl_contents_img")) {
										Elements img = ee5.getElementsByTag("img");
										String src = img.attr("src");
										if (src.contains("?im")) {
											src = src.substring(0, src.lastIndexOf("?im"));
											newsDto.setMainImage(src);
										}
										System.out.println(src);
									}

									if (ee5.className().contains("article_content_row")) {
										Elements ee4578 = ee5.children();
										for (Element ee585 : ee4578) {
											if (ee585.className().contains("artclbyeline")) {
												Elements ee455 = ee585.children();
												for (Element ee57 : ee455) {
													if (ee57.className().contains("artclbyeline-agency")) {
														Elements time = ee5.getElementsByTag("time");

														String date = time.text();
														if (date.contains("IST")) {
															date = date.substring(0, date.lastIndexOf("IST"));

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

							if (ee3.className().contains("khbren_section")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("khbr_rght_sec")) {
										Elements ee43 = ee5.children();

										for (Element ee55 : ee43) {
											if (ee55.className().contains("content_sec")) {

												Elements ee555 = ee55.children();

												for (Element ee5555 : ee555) {
													int first = ee5555.toString().length();
													String ssss = ee5555.toString();
													if (first > 5) {
														ssss = ssss.substring(0, 4);
														if (ssss.contains("<p")) {
															Elements p = ee5555.getElementsByTag("p");
															for (Element p2 : p) {
																String se = p2.text();
																if (!se.equals("")) {

																	if (!p2.toString().contains("Tags")
																			&& !p2.toString().contains("twitter")) {
																		if (ss.equals("")) {
																			ss += p2.text().trim();
																			newsDto.setSummary(ss);

																		} else {
																			ss += "\n\n" + p2.text().trim();
																			newsDto.setSummary(ss);
																		}

																	}
																}
//											System.out.println(ss);

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
	@RequestMapping(value = { "/getNewsIndianexpressTamil" })
	public @ResponseBody Map<String, Object> getNewsIndianexpressTamil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Indianexpress")) {

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

						if (element.className().contains(
								"show-image image-alignright ts-3 is-3 is-landscape is-style-borders resize-image119x67")) {
							System.out.println("2222222222222");
							Elements article = element.getElementsByTag("article");
							for (Element article2 : article) {

								Elements article9 = article2.children();
								for (Element article10 : article9) {
									News news = new News();
									if (article10.className().contains("post-thumbnail")) {
										Elements img = article10.getElementsByTag("img");

										String src = img.attr("src");
										if (src.contains("?w=")) {
											src = src.substring(0, src.lastIndexOf("?w="));
											news.setSimage(src);
										}
										System.out.println(src);
									}
									if (article10.className().contains("entry-wrapper")) {
										Elements article3 = article10.children();
										for (Element article4 : article3) {
											if (article4.className().contains("entry-title")) {
												Elements a = article4.getElementsByTag("a");

												String href = a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlIndianexpressTamil(href);

												news.setUrl(newsDto.getUrl());
												news.setMainheadline(newsDto.getMainheadline());

												news.setMainImage(newsDto.getMainImage());
												news.setSimage(newsDto.getMainImage());
												news.setSummary(newsDto.getSummary());
												news.setSurl(excel.getMainLink());

												news.setCategory(excel.getCategory());
												news.setLanguage(excel.getLanguage());
												news.setWebsiteName(excel.getWebsiteName());
												String head = a.text();
												System.out.println(head);
												news.setSheadline(head);
											}

											if (article4.className().contains("entry-meta-wrapper")) {
												Elements time = article4.getElementsByTag("time");
												String date = time.text();

												if (date.contains("Updated:")) {
													String tt = "Updated: ";
													date = date.substring(date.lastIndexOf("Updated: ") + tt.length(),
															date.length());

													if (date.contains("IST")) {
														date = date.substring(0, date.lastIndexOf("IST"));

														System.out.println(date);
														news.setMaindate(date);

														news.setSdate(date);
														newsList.add(news);
													}

												} else {
													if (date.contains("IST")) {
														date = date.substring(0, date.lastIndexOf("IST"));

														System.out.println(date);
														news.setMaindate(date);

														news.setSdate(date);
														newsList.add(news);
													}

												}
												System.out.println();
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

	public static NewsDto secondURlIndianexpressTamil(String url) throws IOException {

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

			if (element.className().contains("ie-network-grid__lhs")) {
				System.out.println("2222222222222");
				Elements article = element.children();
				for (Element article2 : article) {

					if (article2.className().contains("wp-block-post-title")) {
						Elements h1 = article2.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}
					if (article2.className().contains("wp-block-post-featured-image")) {
						Elements img = article2.getElementsByTag("img");
						String src = img.attr("src");
						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							newsDto.setMainImage(src);
						}
						System.out.println(src);
					}
					if (article2.className().contains("wp-block-post-content")) {

						Elements p = article2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("Tags") && !p2.toString().contains("twitter")
										&& !p2.toString().contains("https://t.me/ietamil")) {
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

//	4
	@RequestMapping(value = { "/getNewsZeeNewsTamil" })
	public @ResponseBody Map<String, Object> getNewsZeeNewsTamil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
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

						if (element.className().equals("view-content")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								News news = new News();
								if (ee.className().contains("story-list clear")) {
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {

										if (ee3.className().contains("story_list_img")) {
											Elements a = ee3.getElementsByTag("a");
											String href = "https://zeenews.india.com" + a.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlZeeNewsTamil(href);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());

											news.setMainImage(newsDto.getMainImage());
											news.setSummary(newsDto.getSummary());
											news.setSurl(excel.getMainLink());

											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());

											Elements img = ee3.getElementsByTag("img");

											for (Element img2 : img) {
												if (img2.toString().contains("src=\"https:")) {
													String src = img2.attr("src");
													if (src.contains("?itok")) {
														src = src.substring(0, src.lastIndexOf("?itok"));
														news.setSimage(src);
													}
													System.out.println(src);
												}
											}

										}
										if (ee3.className().contains("story_list_con")) {
											Elements ee8 = ee3.children();
											for (Element ee9 : ee8) {
												if (ee9.className().contains("story_list_tag")) {
													Elements span = ee3.getElementsByTag("span");
													String date = span.text();
													if (date.contains("IST")) {
														date = date.substring(0, date.lastIndexOf("IST"));
														news.setMaindate(date);

														news.setSdate(date);
														System.out.println(date);
													}
												}
												if (ee9.className().contains("story_list_title")) {
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

	public static NewsDto secondURlZeeNewsTamil(String url) throws IOException {

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

			if (element.className().equals("region region-content")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("article_header_section")) {

						if (ee.toString().contains("<h1")) {
							System.out.println("33");
							Elements h1 = ee.getElementsByTag("h1");
							String head = h1.text();
							System.out.println(head);
							newsDto.setMainheadline(head);
						}

					}

					if (ee.className().contains("article_left clear")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("article_center_col")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("article_img_block")) {
										Elements img = ee5.getElementsByTag("img");

										for (Element img2 : img) {
											if (img2.toString().contains("src=\"https:")) {
												String src = img2.attr("src");
												if (src.contains("?im")) {
													src = src.substring(0, src.lastIndexOf("?im"));
													newsDto.setMainImage(src);
												}
												System.out.println(src);
												newsDto.setMainImage(src);
											}
										}
									}

								}

								Elements article = ee3.getElementsByTag("article");

								for (Element article2 : article) {

									Elements ee6 = article2.children();

									for (Element ee7 : ee6) {
										if (ee7.className().contains("article-block")) {
											Elements ee8 = ee7.children();

											for (Element ee9 : ee8) {
												if (ee9.className().contains("field field-name-body ")) {

													Elements ee10 = ee9.children();

													for (Element ee11 : ee10) {
														if (ee11.className().contains("field-items")) {
															Elements ee12 = ee11.children();

															for (Element ee13 : ee12) {
																if (ee13.className().contains("field-item even")) {
																	Elements p = ee13.getElementsByTag("p");
																	for (Element p2 : p) {
																		String se = p2.text();
																		if (!se.equals("")) {

																			if (!se.contains("https://t.me/")
																					&& !se.contains("Link:")
																					&& !se.toString()
																							.contains("twitter.com")
																					&& !p2.toString()
																							.contains("<a href=")
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
						}
					}
				}

			}

		}

		return newsDto;
	}

//	5
	@RequestMapping(value = { "/getNewsBBCTamil" })
	public @ResponseBody Map<String, Object> getNewsBBCTamil(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("BBC")) {

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

						if (element.className().equals("bbc-1kz5jpr")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								if (ee.className().equals("bbc-t44f9r")) {

									Elements sss = ee.getAllElements();
									for (Element ww : sss) {
										News news = new News();

										if (ww.className().contains("e1v051r10")) {
											Elements ss = ww.getAllElements();
											for (Element weeeew : ss) {
												if (weeeew.className().contains("promo-image")) {
													Elements dddd = weeeew.getAllElements();

													String src2 = dddd.attr("src");
													System.out.println(src2);
													news.setSimage(src2);
												}

												if (weeeew.className().contains("promo-text")) {
													Elements www = weeeew.getAllElements();
													for (Element ccc : www) {
														if (ccc.className().contains("e47bds20")) {

															Elements a = ccc.getElementsByTag("a");
															String url = a.attr("href");
															System.out.println(url);
															NewsDto newsDto = secondURlBBCTamil(url);

															news.setUrl(newsDto.getUrl());
															news.setMainheadline(newsDto.getMainheadline());

															news.setMainImage(newsDto.getMainImage());
															news.setSummary(newsDto.getSummary());
															news.setSurl(excel.getMainLink());

															news.setCategory(excel.getCategory());
															news.setLanguage(excel.getLanguage());
															news.setWebsiteName(excel.getWebsiteName());

															System.out.println(a.text());
															news.setSheadline(a.text());

														}
													}
												}

												if (weeeew.className().contains("e1mklfmt0")) {
													// time
													System.out.println(weeeew.text());
													news.setMaindate(weeeew.text());

													news.setSdate(weeeew.text());
													newsList.add(news);
													System.out.println();
												}

											}
										}
									}
//									Elements sss = ee.getElementsByTag("img");
//									System.out.println(sss);
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

	public static NewsDto secondURlBBCTamil(String url) throws IOException {

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

			Element element = elements.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-fa0wmp")) {

					Elements divChildren2 = element2.children();

					for (Element ww : divChildren2) {

						if (ww.className().contains("bbc-1151pbn ebmt73l0")) {
							Elements a = ww.getElementsByTag("h1");
							System.out.println(a.text());
							newsDto.setMainheadline(a.text());
						}
						if (ww.className().contains("bbc-1ka88fa ebmt73l0")) {
							Elements ww44 = ww.children();

							for (Element ww4 : ww44) {
								if (ww4.className().contains("bbc-1qdcvv9 e1aczekt0")) {
									Elements ww55 = ww4.children();
									for (Element ww5 : ww55) {
										if (ww5.className().contains("bbc-172p16q ebmt73l0")) {
											Elements src2 = ww5.getElementsByTag("img");
											for (Element ww566 : src2) {
												if (flag == 0) {
													String src2f = ww566.attr("src");
													System.out.println(src2f);
													newsDto.setMainImage(src2f);
													flag = 1;
												}

											}

										}

									}
								}
							}

						}

						if (ww.className().contains("bbc-19j92fr ebmt73l0")) {
							Elements a = ww.getElementsByTag("p");
							String sss = a.text();
//							System.out.println(sss);
							if (!sss.toString().contains("(बीबीसी न्यूज") && !a.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += sss.trim();
									newsDto.setSummary(ss);
								} else {
									ss += "\n\n" + sss.trim();
									newsDto.setSummary(ss);
								}

							}

						}

					}
				}
			}
		}

		return newsDto;
	}

//	6
	@RequestMapping(value = { "/getNewsHindustantimesTamil" })
	public @ResponseBody Map<String, Object> getNewsHindustantimesTamil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Hindustantimes")) {

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

						if (element.className().equals("infinite-scroll-component")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().equals("card-wrapper")) {
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {

										if (ee3.className().contains("cardHolder section-page-cardholder")) {
											Elements ee4 = ee3.children();
											for (Element ee5 : ee4) {
												if (ee5.className().contains("listingWrap listTBpaddAlign")) {
													Elements ee6 = ee5.children();

													for (Element ee7 : ee6) {
														News news = new News();
														if (ee7.className().contains("row d-flex")) {
															Elements ee8 = ee7.children();

															for (Element ee9 : ee8) {
//																if (ee9.className()
//																		.contains("listing-page-img-thumbnail")) {
//
//																	Elements img = ee9.getElementsByTag("img");
//
//																	String src = img.attr("srcset");
//
////																	if (flag == 0) {
//																	if (src.contains("&w=96&q=75 1x") && src.contains(
//																			"https://telugu.hindustantimes.com/_next/image?")) {
//																		src = src.substring(0, src.lastIndexOf("1x"));
//																		System.out.println(src);
//																		news.setSimage(src);
//																	} else {
//																		System.out.println(src);
//																		news.setSimage(src);
//																	}
////																		flag = 1;
////																	}
//
//																}
																if (ee9.className().contains("listnewsContAdj")) {
																	Elements h2 = ee9.children();
																	for (Element h3 : h2) {

																		if (h3.className().contains(
																				"listingNewsCont listingNewsHead")) {
																			Elements a = h3.getElementsByTag("a");
																			String href = a.attr("href");
																			System.out.println(href);
																			NewsDto newsDto = secondURlHindustantimesTamil(
																					href);

																			news.setUrl(newsDto.getUrl());
																			news.setMainheadline(
																					newsDto.getMainheadline());

																			news.setMainImage(newsDto.getMainImage());news.setSimage(newsDto.getMainImage());
																			news.setSummary(newsDto.getSummary());
																			news.setSurl(excel.getMainLink());

																			news.setCategory(excel.getCategory());
																			news.setLanguage(excel.getLanguage());
																			news.setWebsiteName(excel.getWebsiteName());

																			System.out.println(a.text());
																			news.setSheadline(a.text());
																		}
																		if (h3.className().contains("relNewsTime")) {
																			Elements p = h3.getElementsByTag("p");

																			String date = p.text();
																			if (date.contains("IST")) {
																				date = date.substring(0,
																						date.lastIndexOf("IST"));

																				System.out.println(date);
																				news.setMaindate(date);

																				news.setSdate(date);
																				newsList.add(news);
																				System.out.println();
																			} else {
																				news.setMaindate(date);

																				news.setSdate(date);
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

	public static NewsDto secondURlHindustantimesTamil(String url) throws IOException {

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

			if (element.className().contains("cardHolder detail-card-holder")) {
				System.out.println("2222222222222");
				Elements article = element.children();

				for (Element article4 : article) {
					if (article4.className().contains("newsCardMainHeading")) {
						Elements h1 = article4.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}

					Elements span = article4.getElementsByTag("span");
					for (Element span2 : span) {
						Elements img = span2.getElementsByTag("img");
						if (!img.toString().equals("")) {

							String src = "https://telugu.hindustantimes.com" + img.attr("src");
							if (src.contains("https://telugu.hindustantimes.com/_next/image?")) {
								System.out.println(src);
								newsDto.setMainImage(src);
							}
						}

					}

				}

				for (Element contentSec9 : article) {
					if (contentSec9.className().equals("newsContent")) {
						Elements p = contentSec9.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//		System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("టాపిక్") && !p2.toString().contains("டாபிக்ஸ்")
										&& !p2.toString().contains("href=")) {
									if (ss.equals("")) {
										ss += p2.text().trim().trim();
										newsDto.setSummary(ss);

									} else {
										ss += "\n\n" + p2.text().trim();
										newsDto.setSummary(ss);
									}
//			System.out.println(ss);

								}
							}
//								System.out.println(ss);

						}
					}

				}

			}

		}

		return newsDto;
	}
}
