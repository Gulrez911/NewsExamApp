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
public class NewsGujaratiController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsTV9Gujarati" })
	public @ResponseBody Map<String, Object> getNewsTV9Gujarati(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("TV9")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//					count++;
//					System.out.println("...........  "+count);
					Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

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
								NewsDto newsDto = secondURlTV9Gujarati(href);

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
								String head = p.text();
								System.out.println(head);
								news.setSheadline(head);

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

	public static NewsDto secondURlTV9Gujarati(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
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

						if (date.contains("on: ")) {
							String tt = "on: ";
							date = date.substring(date.lastIndexOf("on: ") + tt.length(), date.length()).trim();

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

					if (ee2.className().contains("ArticleBody")) {

						Elements p = ee2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ") && !p2.toString().contains("twitter")) {
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

//	2

	@RequestMapping(value = { "/getNewsBBCGujarati" })
	public @ResponseBody Map<String, Object> getNewsBBCGujarati(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("BBC")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//					count++;
//					System.out.println("...........  "+count);
					Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

					if (element.className().equals("bbc-1kz5jpr")) {
						System.out.println("2222222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {
							if (ee.className().equals("bbc-t44f9r")) {

								Elements sss = ee.getAllElements();
								for (Element ww : sss) {

									if (ww.className().contains("e1v051r10")) {
										Elements ss = ww.getAllElements();
										News news = new News();
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
														NewsDto newsDto = secondURlBBCGujarati(url);

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
												String date = weeeew.text();
												System.out.println(date);
												news.setMaindate(date);

												news.setSdate(date);
												newsList.add(news);
												System.out.println();
											}

										}
									}
								}
//								Elements sss = ee.getElementsByTag("img");
//								System.out.println(sss);
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

			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlBBCGujarati(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
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
							String head = a.text();
							System.out.println(head);
							newsDto.setMainheadline(head);
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
	// 3

	@RequestMapping(value = { "/getNewsOneindiaGujarati" })
	public @ResponseBody Map<String, Object> getNewsOneindiaGujarati(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
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
//					count++;
//					System.out.println("...........  "+count);
						Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

						if (element.className().equals("oi-cityblock-list")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {
									News news = new News();
									if (ee3.className().equals("clearfix")) {
										Elements ee4 = ee3.children();
										for (Element ee5 : ee4) {
											if (ee5.className().contains("cityblock-img")) {
												Elements a = ee5.getElementsByTag("a");

												String href = "https://gujarati.oneindia.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlOneindiaGujarati(href);

												news.setUrl(newsDto.getUrl());
												news.setMainheadline(newsDto.getMainheadline());

												news.setSimage(newsDto.getMainImage());
												news.setSummary(newsDto.getSummary());
												news.setSurl(excel.getMainLink());

												news.setCategory(excel.getCategory());
												news.setLanguage(excel.getLanguage());
												news.setWebsiteName(excel.getWebsiteName());
												Elements img = ee5.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);
												news.setMainImage(src);
											}

											if (ee5.className().contains("cityblock-desc")) {

												Elements ee55 = ee5.children();
												for (Element ee555 : ee55) {

													Elements a = ee5.getElementsByTag("a");

													String head = a.text();
													System.out.println(head);
													news.setSheadline(head);

													if (ee555.className().contains("cityblock-time")) {
														String date = ee555.text();

														if (date.contains(",")) {

															int index4 = date.indexOf(',');
															date = date.substring(index4 + 1, date.lastIndexOf("[IST]"))
																	.trim();

															System.out.println(date);
															news.setSdate(date);
															news.setMaindate(date);
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

	public static NewsDto secondURlOneindiaGujarati(String url) throws IOException {

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
						newsDto.setMainheadline(h1);
						System.out.println(h1);
					}
					if (ee.className().equals("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://gujarati.oneindia.com" + img.attr("src");
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

	@RequestMapping(value = { "/getNewsABPliveGujarati" })
	public @ResponseBody Map<String, Object> getNewsABPliveGujarati(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("ABPlive")) {

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
//					count++;
//					System.out.println("...........  "+count);
						Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

						if (element.className().equals("uk-width-3-4")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("uk-grid uk-grid-small")) {
									System.out.println("33");
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {
										Elements ee4 = ee3.children();
										for (Element ee5 : ee4) {
											News news = new News();
											if (ee5.className().contains("other_news")) {

												System.out.println("555555");
												Elements a = ee5.getElementsByTag("a");
												String href = a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlABPliveGujarati(href);

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
												Elements img = ee5.getElementsByTag("img");
												String src = img.attr("data-src");
												if (src.contains("?impolicy")) {
													src = src.substring(0, src.lastIndexOf("?impolicy"));

												}
												System.out.println(src);
												news.setSimage(src);
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

	public static NewsDto secondURlABPliveGujarati(String url) throws IOException {

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

			if (element.className().equals("uk-width-expand uk-position-relative")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("article-title")) {

								String head = ee3.text();
								System.out.println(head);
								newsDto.setMainheadline(head);
							}
							if (ee3.className().contains("uk-margin-bottom uk-flex-between")) {

								Elements p = ee3.getElementsByTag("p");

								for (Element eee3 : p) {
									if (eee3.className().contains("article-author")) {

										String date = eee3.text();

										if (date.contains("at : ")) {
											String tt = "at : ";
											date = date.substring(date.lastIndexOf("at : ") + tt.length(),
													date.length());

											if (date.contains("(IST)")) {
												date = date.substring(0, date.lastIndexOf("(IST)"));

												System.out.println(date);
												newsDto.setMaindate(date);
											}

										}

									}
								}
							}
							if (ee3.className().contains("news_featured cont_accord_to_img")) {
								Elements img = ee3.getElementsByTag("img");
								String src = img.attr("data-src");
								if (src.contains("?impolicy")) {
									src = src.substring(0, src.lastIndexOf("?impolicy"));

								}
								System.out.println(src);
								newsDto.setMainImage(src);

							}
						}
					}
					if (ee.className().contains("article-data _thumbBrk uk-text-break")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!se.contains("Tags:") && !se.contains("https:")
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
//							System.out.println(ss);

						}

					}
				}
			}

		}

		return newsDto;
	}
//	5

	@RequestMapping(value = { "/getNewsIndianexpressGujarati" })
	public @ResponseBody Map<String, Object> getNewsIndianexpressGujarati(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
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
								"ie-stories show-image image-alignright ts-3 is-3 is-landscape is-style-borders resize-image119x67")) {
							System.out.println("2222222222222");
							Elements article = element.getElementsByTag("article");
							for (Element article2 : article) {
								News news = new News();

								Elements article9 = article2.children();
								for (Element article10 : article9) {
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
												NewsDto newsDto = secondURlIndianexpressGujarati(href);

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

	public static NewsDto secondURlIndianexpressGujarati(String url) throws IOException {

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

			if (element.className().contains("wp-block-column")) {
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
					if (article2.className().contains("entry-content")) {

						Elements p = article2.getElementsByTag("p");
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
//							System.out.println(ss);

						}

					}
				}
			}

		}

		return newsDto;
	}
}
