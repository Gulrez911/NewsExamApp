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
public class NewsPunjabiController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsAbplivePunjabi" })
	public @ResponseBody Map<String, Object> getNewsAbplivePunjabi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("ABPlive")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
						Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

						if (element.className().equals("uk-container")) {
//					System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("uk-grid-small left-sidebar-grid")) {

									Elements ee2 = ee.children();

									for (Element ee3 : ee2) {
										if (ee3.className().contains("uk-width-3-4")) {

											Elements ee34 = ee3.children();

											for (Element ee345 : ee34) {
												if (ee345.className().contains("uk-grid uk-grid-small")) {

													Elements ee3456 = ee345.children();

													for (Element ee34567 : ee3456) {
														if (ee34567.className().contains("uk-width-expand")) {

															Elements ee3456789 = ee34567.children();

															for (Element ee345670 : ee3456789) {
																if (ee345670.className().contains("other_news")) {
																	News news = new News();
																	Elements ee3456703 = ee345670.children();
																	for (Element ere54 : ee3456703) {
																		Elements a = ere54.getElementsByTag("a");
																		String href = a.attr("href");
																		System.out.println(href);

																		NewsDto newsDto = secondURlAbplivePunjabi(href);

																		news.setUrl(newsDto.getUrl());
																		news.setMainheadline(newsDto.getMainheadline());
//																news.setMaindate(newsDto.getMaindate());
																		news.setSdate(newsDto.getMaindate());
																		news.setMaindate(newsDto.getMaindate());
																		news.setSummary(newsDto.getSummary());
																		news.setMainImage(newsDto.getMainImage());

																		news.setSurl(excel.getMainLink());

																		news.setCategory(excel.getCategory());
																		news.setLanguage(excel.getLanguage());
																		news.setWebsiteName(excel.getWebsiteName());

																		Elements ere5455 = ere54.children();
																		for (Element ere542 : ere5455) {
																			if (ere542.className()
																					.contains("uk-grid-collapse")) {
																				Elements img = ere542
																						.getElementsByTag("img");
																				String src = img.attr("data-src");
																				if (src.contains("?impolicy")) {
																					src = src.substring(0, src
																							.lastIndexOf("?impolicy"));
																					System.out.println(src);
																				}
																				news.setSimage(src);
																				System.out.println(ere542.text());
																				news.setSheadline(ere542.text());

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
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
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

//				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlAbplivePunjabi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().equals("uk-width-expand uk-position-relative")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("")) {

						Elements ee2 = ee.children();

						for (Element ee23 : ee2) {
							if (ee23.className().contains("article-title")) {

								String head = ee23.text();
								System.out.println(head);
								newsDto.setMainheadline(head);
							}
							if (ee23.className().contains(
									"uk-flex uk-flex-bottom _no_margin_bottom uk-margin-bottom uk-flex-between")) {
								Elements ee2343 = ee23.children();
								for (Element ee23e : ee2343) {
									Elements ee2343s = ee23e.children();
									for (Element ee23se : ee2343s) {
										if (ee23se.className().contains("article-author")) {
											String date = ee23se.text();
											if (date.contains("Updated at :")) {
												String tt = "Updated at : ";
												date = date.substring(date.lastIndexOf("Updated at : ") + tt.length(),
														date.length());
												if (date.contains("(IST)")) {

													date = date.replace("(IST)", "").trim();
													System.out.println(date);
												}
												newsDto.setMaindate(date);
											}

										}
									}

								}
							}
							if (ee23.className().contains("news_featured cont_accord_to_img")) {

								Elements img = ee23.getElementsByTag("img");
								String src = img.attr("data-src");
								if (src.contains("?impolicy")) {
									src = src.substring(0, src.lastIndexOf("?impolicy"));
									System.out.println(src);
								}
								newsDto.setMainImage(src);
							}

						}

					}

					if (ee.className().contains("article-data _thumbBrk uk-text-break")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {

							if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += p2.text();

								} else {
									ss += "\n\n" + p2.text();
								}
								newsDto.setSummary(ss);
//								System.out.println(ss);
							}

						}

					}

				}

			}
		}

		return newsDto;
	}

//	2

	@RequestMapping(value = { "/getNewsPunjabijagranPunjabi" })
	public @ResponseBody Map<String, Object> getNewsPunjabijagranPunjabi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("Punjabijagran")) {
				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
						Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

						if (element.className().equals("newsFJagran")) {
//					System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("topicList")) {

									Elements ee2 = ee.getElementsByTag("li");
									for (Element ee23 : ee2) {
										News news = new News();
										Elements a = ee23.getElementsByTag("a");
										String href = "https://www.punjabijagran.com" + a.attr("href");
										System.out.println(href);

										NewsDto newsDto = secondURlPunjabijagranPunjabi(href);

										news.setUrl(newsDto.getUrl());
										news.setMainheadline(newsDto.getMainheadline());
										news.setMaindate(newsDto.getMaindate());
										news.setSdate(newsDto.getMaindate());
										news.setSummary(newsDto.getSummary());
										news.setMainImage(newsDto.getMainImage());

										news.setSurl(excel.getMainLink());

										news.setCategory(excel.getCategory());
										news.setLanguage(excel.getLanguage());
										news.setWebsiteName(excel.getWebsiteName());

//								System.out.println(a.text());

										for (Element aa : a) {
											Elements aa3 = aa.children();
											for (Element aa4 : aa3) {
												if (aa4.className().contains("proimg fl")) {
													Elements img = aa4.getElementsByTag("img");
													String src = "";
													if (img.toString().contains("data-src")) {
														src = img.attr("data-src");
													} else {
														src = img.attr("src");
													}
													news.setSimage(src);
													System.out.println(src);
												}
												if (aa4.className().contains("protxt fr")) {
													Elements aa42 = aa4.children();
													for (Element aa423 : aa42) {
														if (aa423.className().contains("h3")) {
															String h3 = aa423.text();
															System.out.println(h3);

															news.setSheadline(h3);

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
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
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

//				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlPunjabijagranPunjabi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().equals("detailBox")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("articleHd")) {

						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
						Elements ee44 = ee.children();

						for (Element ee445 : ee44) {
							if (ee445.className().contains("dateInfo")) {
								String date = ee445.text();
								if (date.contains(",")) {
									String tt = ",";
									date = date.substring(date.lastIndexOf(", ") + tt.length(), date.length());
									if (date.contains("(IST)")) {

										date = date.replace("(IST)", "").trim();
										System.out.println(date);
										newsDto.setMaindate(date);
									}

								}

							}
						}

					}

					if (ee.className().contains("bodySummery")) {

						Elements img = ee.getElementsByTag("img");
						String src = "";
						if (img.toString().contains("data-src")) {
							src = img.attr("data-src");
							newsDto.setMainImage(src);
						} else {
							src = img.attr("src");
							newsDto.setMainImage(src);
						}
						System.out.println(src);
					}
					int flag = 0;
					if (ee.className().contains("articleBody")) {
						if (flag == 0) {
							Elements p = ee.getElementsByTag("p");
							for (Element p2 : p) {
								String se = p2.text();
//		System.out.println(se);
								if (!se.equals("")) {

									if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += p2.text();

										} else {
											ss += "\n\n" + p2.text();
										}
										newsDto.setSummary(ss);
//			System.out.println(ss);

									}
								}
//								System.out.println(ss);

							}
							flag = 1;
						}
					}

				}

			}
		}

		return newsDto;
	}
//	3

	@RequestMapping(value = { "/getNewsTV9punjabiPunjabi" })
	public @ResponseBody Map<String, Object> getNewsTV9punjabiPunjabi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("TV9punjabi")) {
				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
						Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

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

									System.out.println(p.text());

									NewsDto newsDto = secondURlTV9punjabiPunjabi(href);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
									news.setMaindate(newsDto.getMaindate());
									news.setSdate(newsDto.getMaindate());
									news.setSummary(newsDto.getSummary());
									news.setMainImage(newsDto.getMainImage());

									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());

									news.setSheadline(p.text());
									Elements img = ee.getElementsByTag("img");

									String src = img.attr("src");

									if (src.contains("?w=")) {
										src = src.substring(0, src.lastIndexOf("?w="));
										System.out.println(src);
									} else {
										System.out.println(src);

									}
									news.setSimage(src);

									newsList.add(news);
									System.out.println();
								}

							}
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
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

//				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlTV9punjabiPunjabi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().equals("detailBody")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().equals("article-HD")) {

						String head = ee.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}
					if (ee.className().equals("authorBox")) {

						Elements pp = ee.getElementsByTag("p");
						String date = pp.text();
						System.out.println(date);
						newsDto.setMaindate(date);
					}
					if (ee.className().contains("ArticleBodyCont")) {

						Elements img = ee.getElementsByTag("img");

						String src = img.attr("data-src");

						if (src.contains("?w=")) {
							src = src.substring(0, src.lastIndexOf("?w="));
							System.out.println(src);
						} else {
							System.out.println(src);
						}
						newsDto.setMainImage(src);

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();

									} else {
										ss += "\n\n" + p2.text();
									}
									newsDto.setSummary(ss);

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

	@RequestMapping(value = { "/getNewsTVpunjabPunjabi" })
	public @ResponseBody Map<String, Object> getNewsTV9punjabPunjabi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("TVpunjab")) {
				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
						Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

						if (element.className().equals("a-sides")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								Elements article = ee.children();
								News news = new News();
								for (Element article2 : article) {
									if (article2.className().equals("thumbss")) {
										Elements img = article2.getElementsByTag("img");

										String src = img.attr("src");

										System.out.println(src);
										news.setSimage(src);
									}
									if (article2.className().equals("entry-header")) {

										Elements a = article2.getElementsByTag("a");
										String href = a.attr("href");
										System.out.println(href);

										NewsDto newsDto = secondURlTVpunjabPunjabi(href);

										news.setUrl(newsDto.getUrl());
										news.setMainheadline(newsDto.getMainheadline());
//								news.setMaindate(newsDto.getMaindate());

										news.setMainImage(newsDto.getMainImage());
										news.setSummary(newsDto.getSummary());
										news.setSurl(excel.getMainLink());

										news.setCategory(excel.getCategory());
										news.setLanguage(excel.getLanguage());
										news.setWebsiteName(excel.getWebsiteName());

//								System.out.println(a.text());

										Elements h2 = article2.getElementsByTag("h2");
										String head = h2.text();
										System.out.println(head);
										news.setSheadline(head);

										Elements article33 = article2.children();
										for (Element article32 : article33) {

											if (article32.className().equals("entry-meta")) {
												Elements article3w3 = article32.children();
												for (Element article3e2 : article3w3) {

													if (article3e2.className().equals("posted-on")) {
														Elements as = article3e2.getElementsByTag("a");

														for (Element sa : as) {
															Elements sad = sa.children();
															for (Element sda : sad) {
																if (sda.className().contains("updated")) {
																	String date = sda.text();
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
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
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

//				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlTVpunjabPunjabi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().equals("a-sides")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements article = ee.children();
					for (Element article2 : article) {

						if (article2.className().equals("entry-header")) {

							Elements h1 = article2.getElementsByTag("h1");

							String head = h1.text();

							System.out.println(head);
							newsDto.setMainheadline(head);
						}
						if (article2.className().equals("post-thumbnail")) {

							Elements img = article2.getElementsByTag("img");

							String src = img.attr("src");

							System.out.println(src);
							newsDto.setMainImage(src);
							System.out.println();
						}

						if (article2.className().equals("entry-content")) {

							Elements p = article2.getElementsByTag("p");
							for (Element p2 : p) {
								String se = p2.text();
								if (!se.equals("")) {

									if (!p2.toString().contains("ਨੋਟ:") && !p2.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += p2.text();
											newsDto.setSummary(ss);
										} else {
											ss += "\n\n" + p2.text();
											newsDto.setSummary(ss);
										}

									}
								}
//								System.out.println(ss);

							}

						}
					}

				}
			}
		}

		return newsDto;
	}
}
