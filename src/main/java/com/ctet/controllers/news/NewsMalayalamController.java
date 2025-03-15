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
public class NewsMalayalamController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsETVbharatMalayalam" })
	public @ResponseBody Map<String, Object> getNewsETVbharatMalayalam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("ETVbharat")) {

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

						if (element.className().equals("md:w-8/12 h-full px-2 md:flex md:flex-wrap")) {
							System.out.println("2222222222222");
//							Elements divChildren2 = element.getElementsByTag("a");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news2 = new News();
								if (ee.className()
										.contains("fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {

										if (ee3.className().contains("flex w-full justify-between")) {

											Elements ee255 = ee3.children();
											for (Element ee25566 : ee255) {
												News news = new News();
												if (ee25566.className().contains(
														"flex  flex-col pb-1 cursor-pointer rounded-md shadow  w-1/3 bg-white")) {

													Elements a = ee25566.getElementsByTag("a");

													String href = "https://www.etvbharat.com" + a.attr("href");
													System.out.println(href);
													NewsDto newsDto = secondURlETVbharatMalayalam(href);

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

																	System.out.println(src);
																	news.setSimage(src);
																}

															}

														}
														if (ee5.className().contains(
																"h-12 my-2 pl-2 pr-1 text-gray-700 leading-tight")) {

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
									NewsDto newsDto = secondURlETVbharatMalayalam(href);

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

	public static NewsDto secondURlETVbharatMalayalam(String url) throws IOException {

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
															newsDto.setMaindate(date);
															System.out.println(date);
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

								if (!p2.toString().contains("আরও পড়ুন:") && !p2.toString().contains("twitter")
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

//	2
	@RequestMapping(value = { "/getNewsOneindiaMalayalam" })
	public @ResponseBody Map<String, Object> getNewsOneindiaMalayalam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
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

											if (ee5.className().contains("cityblock-desc")) {

												Elements ee52 = ee5.children();
												for (Element ee523 : ee52) {
													Elements ee522 = ee523.children();
													for (Element ee5223 : ee522) {
														if (ee5223.className().contains("cityblock-title")) {
															String head = ee5223.text();
															System.out.println(head);
//															news.setSheadline(head);
															newsList.add(news);
															System.out.println();
														} else {
//															if (!ee5223.toString().contains("<a href")) {

															String head = ee5223.text();
															if (!head.contains("[IST]")) {

																System.out.println(head);
//																news.setSheadline(head);
																newsList.add(news);
																System.out.println();
															}
//															}
														}
													}

												}

											}

											else {
												Elements a = ee5.getElementsByTag("a");

												String text = a.attr("href");
												if (text.contains("/")) {
													String href = "https://malayalam.oneindia.com" + a.attr("href");
													System.out.println(href);
													NewsDto newsDto = secondURlOneindiaMalayalam(href);

													news.setUrl(newsDto.getUrl());
													news.setMainheadline(newsDto.getMainheadline());
													news.setSheadline(newsDto.getMainheadline());
													news.setMaindate(newsDto.getMaindate());

													news.setSdate(newsDto.getMaindate());
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

	public static NewsDto secondURlOneindiaMalayalam(String url) throws IOException {

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
					if (ee.className().equals("author-dat-time clearfix")) {
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
					if (ee.className().contains("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://malayalam.oneindia.com" + img.attr("src");
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

//	3
	@RequestMapping(value = { "/getNewsNews18Malayalam" })
	public @ResponseBody Map<String, Object> getNewsNews18Malayalam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
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
//							System.out.println("2222222222222");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								if (ee.className().contains("blog_list_row ")) {
									News news = new News();
//									System.out.println("3333");

									Elements a = ee.getElementsByTag("a");

									String href = a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlNews18Malayalam(href);

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
//											String ssss = news.getSummary();
//											String aaaaa = news.getUrl();
//											String cat = news.getCategory();
//											if (cat.equals("Career")) {
//												System.out.println("5555");
//											}
//
////											if (!ssss.equals("")) {
//												if (aaaaa.equals(
//														"https://malayalam.news18.com/news/career/want-to-be-a-primary-school-teacher-study-diploma-in-elementary-education-ak-613474.html")) {
//													System.out.println("333333");
//												}

											if (news.getUrl().equals(
													"https://malayalam.news18.com/news/career/want-to-be-a-primary-school-teacher-study-diploma-in-elementary-education-ak-613474.html")) {
												System.out.println("55555");
											}

//											if (news.getSummary().length() == 0) {
//												System.out.println("666666");
//											} else {
											newsList.add(news);

//											}

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

	public static NewsDto secondURlNews18Malayalam(String url) throws IOException {

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

			if (element.className().contains("artcl_lft")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
//					if (ee.className().contains("jsx-3181210954")) {

					Elements h1 = ee.getElementsByTag("h1");

					String head = h1.text();
					if (!head.equals("")) {

						System.out.println(head);
						newsDto.setMainheadline(head);
					}

//					}

					if (ee.className().contains("artcl_contents")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("artcl_contents_img")) {
								Elements img = ee3.getElementsByTag("img");

								for (Element img2 : img) {
									if (img2.toString().contains("src=\"https://images.news18.com")) {
										String src = img2.attr("src");
										if (src.contains(".jpg")) {
											if (src.contains("?im=")) {
												src = src.substring(0, src.lastIndexOf("?im="));
												System.out.println(src);
												newsDto.setMainImage(src);
											}
										}

									}
								}
							}

							if (ee3.className().contains("article_content_row")) {
								Elements time = ee3.getElementsByTag("time");

								String date = time.text();
								if (date.contains("IST")) {
									date = date.substring(0, date.lastIndexOf("IST"));

									System.out.println(date);
									newsDto.setMaindate(date);
								}

							}
						}

					}

					if (ee.className().contains("khbren_section")) {
						Elements ee1234567 = ee.children();
						for (Element ee12345678 : ee1234567) {
							if (ee12345678.className().contains("khbr_rght_sec")) {

								Elements ee45 = ee12345678.children();
								for (Element ee455 : ee45) {

									if (ee455.className().contains("content_sec")) {
										Elements ee4555 = ee455.children();
										for (Element ee45555 : ee4555) {
											int first = ee45555.toString().length();
											String ssss = ee45555.toString();
											if (first > 5) {
												ssss = ssss.substring(0, 4);
												if (!ssss.contains("<div") && !ssss.contains("<fig")) {
													Elements p = ee45555.getElementsByTag("p");
													for (Element p2 : p) {
														if (!p2.toString().contains("Tags")
																&& !p2.toString().contains("twitter")
																&& !p2.toString().contains("Also read")) {
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

//	4
	@RequestMapping(value = { "/getNewsIndianExpressMalayalam" })
	public @ResponseBody Map<String, Object> getNewsIndianExpressMalayalam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Indian Express")) {

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
								"image-alignright ts-3 is-3 is-landscape is-style-borders resize-image150x83")) {
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
												NewsDto newsDto = secondURlIndianExpressMalayalam(href);

												news.setUrl(newsDto.getUrl());
												news.setMainheadline(newsDto.getMainheadline());

												news.setMainImage(newsDto.getMainImage());
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
												if (date.contains("Updated")) {
													String tt = "Updated:";
													date = date.substring(date.lastIndexOf("Updated:") + tt.length(),
															date.length());
													if (date.contains("IST")) {

														date = date.replace("IST", "").trim();
														news.setMaindate(date);

														news.setSdate(date);
													}

												} else {
													if (date.contains("IST")) {

														date = date.replace("IST", "").trim();
														news.setMaindate(date);

														news.setSdate(date);
													}
												}
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

	public static NewsDto secondURlIndianExpressMalayalam(String url) throws IOException {

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

			if (element.className()
					.contains("wp-block-column ie-network-grid__lhs margin-bottom-none is-layout-flow")) {
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
					if (article2.className().contains("entry-content wp-block-post-content is-layout-flow")) {

						Elements ee45 = article2.children();
						for (Element ee455 : ee45) {
							int first = ee455.toString().length();
							String ssss = ee455.toString();
							if (first > 5) {
								ssss = ssss.substring(0, 4);
								if (!ssss.contains("<div") && !ssss.contains("<fi")) {
									Elements p = ee455.getElementsByTag("p");
									for (Element p2 : p) {
										String se = p2.text();
										if (!se.equals("")) {

											if (!p2.toString().contains("Tags") && !p2.toString().contains("twitter")
													&& !p2.toString().contains("Also Read") && !p2.toString()
															.contains("https://malayalam.indianexpress.com/")) {
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
				}
			}

		}

		return newsDto;
	}

//	5
	@RequestMapping(value = { "/getNewsZeeNewsMalayalam" })
	public @ResponseBody Map<String, Object> getNewsZeeNewsMalayalam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
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
											NewsDto newsDto = secondURlZeeNewsMalayalam(href);

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

														System.out.println(date);
														news.setMaindate(date);

														news.setSdate(date);
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

	public static NewsDto secondURlZeeNewsMalayalam(String url) throws IOException {

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
												} else {

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
}
