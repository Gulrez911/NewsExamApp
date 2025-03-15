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
public class NewsKannadaController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsNews18Kannada" })
	public @ResponseBody Map<String, Object> getNewsNews18Kannada(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
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

						if (element.className().contains("blog_list")
								|| element.className().contains("latest_news_list")) {
//							System.out.println("2222222222222");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news = new News();
								if (ee.className().contains("blog_list_row ")) {
//									System.out.println("3333");

									Elements a = ee.getElementsByTag("a");

									String href = a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlNews18Kannada(href);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());

									news.setSdate(newsDto.getMaindate());
									news.setMainImage(newsDto.getMainImage());
									news.setSimage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());
									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
									for (Element ee55661 : a) {
										Elements figure = ee55661.getElementsByTag("figure");
										for (Element figure2 : figure) {
											Elements figcaption = figure2.getElementsByTag("figcaption");
											String head = figcaption.text();
											System.out.println(head);
											news.setSheadline(head);
											newsList.add(news);
											System.out.println();
										}
									}

								} else {
									Elements li = ee.getElementsByTag("li");
									for (Element ee55661 : li) {
										Elements ee556612 = ee55661.children();
										for (Element ee5566125 : ee556612) {
											Elements aa = ee5566125.getElementsByTag("a");

											String href = aa.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlNews18Kannada(href);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());
											news.setMaindate(newsDto.getMaindate());

											news.setSdate(newsDto.getMaindate());
											news.setMainImage(newsDto.getMainImage());
											news.setSimage(newsDto.getMainImage());
											news.setSummary(newsDto.getSummary());
											news.setSurl(excel.getMainLink());

											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());
											for (Element ee5566s1 : aa) {
												Elements figure = ee5566s1.getElementsByTag("figure");
												for (Element figure2 : figure) {
													Elements figcaption = figure2.getElementsByTag("figcaption");
													String head = figcaption.text();
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

	public static NewsDto secondURlNews18Kannada(String url) throws IOException {

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
//			System.out.println("  ..........   " );

			if (element.className().contains("news_page news_page_scroll")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("container clearfix")) {

						Elements ee1 = ee.children();
						for (Element ee12 : ee1) {
							if (ee12.className().contains("news_page_left")) {
								Elements ee123 = ee12.children();
								for (Element ee1234 : ee123) {
									if (ee1234.className().contains("news_title")) {

										Elements h1 = ee1234.getElementsByTag("h1");
										String head = h1.text();
										System.out.println(head);
										newsDto.setMainheadline(head);
									}

									if (ee1234.className().contains("article_content")) {

										Elements ee1235 = ee1234.children();

										for (Element ee12348 : ee1235) {
											if (ee12348.className().contains("article_content_img")) {
												Elements img = ee12348.getElementsByTag("img");

												for (Element img2 : img) {
													if (img2.toString().contains("src=")) {
														if (flag == 0) {
															if (img2.toString()
																	.contains("src=\"https://images.news18.com")) {
																String src = img2.attr("src");
																if (src.contains(".jpg")) {
																	if (src.contains("?im")) {
																		src = src.substring(0, src.lastIndexOf("?im"));
																		System.out.println(src);
																		newsDto.setMainImage(src);
																	}
																}

															}
															flag = 1;
														}
													}

												}
											}
											if (ee12348.className().contains("article_content_row")) {
												Elements time = ee12348.getElementsByTag("time");

												String date = time.text();
												if (date.contains("IST")) {
													date = date.substring(0, date.lastIndexOf("IST"));
													newsDto.setMaindate(date);
													System.out.println(date);
												}

											}

										}

									}

									if (ee1234.className().contains("khbren_section")) {
										Elements ee1234567 = ee1234.children();
										for (Element ee12345678 : ee1234567) {
											if (ee12345678.className().contains("khbr_rght_sec")) {
												Elements p = ee12345678.getElementsByTag("p");
												for (Element p2 : p) {
													if (!p2.toString().contains("Tags")
															&& !p2.toString().contains("twitter")
															&& !p2.toString().contains("Breaking News")) {
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

		return newsDto;
	}

//	2
	@RequestMapping(value = { "/getNewsTV9Kannada" })
	public @ResponseBody Map<String, Object> getNewsTV9Kannada(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("TV9")) {

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

						if (element.className().equals("wrapper_section")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								News news = new News();
								if (!ee.toString().contains("adsCont") && ee.toString().contains("<a")
										&& ee.toString().contains("<img")) {
									Elements p = ee.getElementsByTag("a");
									String href = p.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlTV9Kannada(href);

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

									System.out.println(p.text());
									news.setSheadline(p.text());
									Elements img = ee.getElementsByTag("img");

									String src = img.attr("data-src");

									if (src.contains("?w=")) {
										src = src.substring(0, src.lastIndexOf("?w="));
										System.out.println(src);
										news.setSimage(src);
										newsList.add(news);
									} else {
										System.out.println(src);
										news.setSimage(src);
										newsList.add(news);
									}

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

	public static NewsDto secondURlTV9Kannada(String url) throws IOException {

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

			if (element.className().contains("detailBody")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee2 : divChildren2) {
					if (ee2.className().equals("article-HD")) {
						String head = ee2.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}
					if (ee2.className().equals("author-box")) {

						Elements pp = ee2.getElementsByTag("p");
						String date = pp.text();

						if (date.contains("on:")) {
							String tt = "on:";
							date = date.substring(date.lastIndexOf("on:") + tt.length(), date.length()).trim();

							System.out.println(date);
							newsDto.setMaindate(date);
							System.out.println();
						}

					}
					if (ee2.className().equals("articleImg")) {

						Elements img = ee2.getElementsByTag("img");

						String src = img.attr("src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
							newsDto.setMainImage(src);
						} else {
							System.out.println(src);
							newsDto.setMainImage(src);
						}

					}

					if (ee2.className().contains("ArticleBody") || ee2.className().contains("ArticleBodyCont")) {

						Elements p = ee2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ಇದನ್ನೂ ಓದಿ:")
										&& !p2.toString().contains("ಮತ್ತಷ್ಟು ರಾಷ್ಟ್ರೀಯ")
										&& !p2.toString().contains("twitter")
										&& !p2.toString().contains("Published On")) {
									if (ss.equals("")) {
										ss += p2.text().trim();
										newsDto.setSummary(ss);

									} else {
										ss += "\n\n" + p2.text().trim();
										newsDto.setSummary(ss);
									}
//		System.out.println(ss);

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

//	3
	@RequestMapping(value = { "/getNewsOneindiaKannada" })
	public @ResponseBody Map<String, Object> getNewsOneindiaKannada(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
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

						if (element.className().equals("oi-cityblock-list")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {
									News news = new News();
									if (ee3.className().contains("clearfix")) {
										Elements ee4 = ee3.children();
										for (Element ee5 : ee4) {
											if (ee5.className().contains("cityblock-img")) {
												Elements a = ee5.getElementsByTag("a");

												String href = "https://kannada.oneindia.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlOneindiaKannada(href);

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

													if (ee555.className().contains("cityblock-time")) {

														Elements a = ee5.getElementsByTag("a");

														String head = a.text();
														System.out.println(head);
														news.setSheadline(head);
														String date = ee555.text();

														if (date.contains(",")) {

															int index4 = date.indexOf(',');
															date = date.substring(index4 + 1, date.lastIndexOf("[IST]"))
																	.trim();
															news.setMaindate(date);

															news.setSdate(date);
															System.out.println(date);
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

	public static NewsDto secondURlOneindiaKannada(String url) throws IOException {

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

									String src = "https://kannada.oneindia.com" + img.attr("src");
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

								if (!p2.toString().contains("Tags") && !p2.toString().contains("twitter")) {
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

//	4
	@RequestMapping(value = { "/getNewsZeeNewsKannada" })
	public @ResponseBody Map<String, Object> getNewsZeeNewsKannada(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
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
											NewsDto newsDto = secondURlZeeNewsKannada(href);

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

	public static NewsDto secondURlZeeNewsKannada(String url) throws IOException {

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

					if (ee.className().contains("article_left")) {
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
												}else {
													System.out.println(src);
													newsDto.setMainImage(src);
												}
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
}
