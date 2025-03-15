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
public class NewsBangaliController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/ee" })
	public @ResponseBody Map<String, Object> ee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		long ssss=  44842;
		News ss = newsRepository.findById(ssss).get();
		if(ss.getSummary().equals("")) {
			System.out.println("666666");
		}
		System.out.println(ss.toString());
		map.put("news", ss);
		return map;
	}
	@RequestMapping(value = { "/getNewsHindustantimesBangali" })
	public @ResponseBody Map<String, Object> getNewsHindustantimesBangali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Hindustan Times")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

					if (element.className().equals("mainSec")) {
						System.out.println("2222222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {
							if (ee.className().equals("listView")) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {
									Elements ee4 = ee3.children();
									for (Element ee5 : ee4) {
										News news = new News();
										if (ee5.className().contains("listing clearfix")) {
											Elements ee6 = ee5.children();
											for (Element ee7 : ee6) {

												Elements ee8 = ee7.children();
												for (Element ee9 : ee8) {

													if (ee9.className().contains("thumbnail")) {

														Elements a = ee9.getElementsByTag("a");

														String src2f = "https://bangla.hindustantimes.com"
																+ a.attr("href");
														System.out.println(src2f);
														NewsDto newsDto = secondURlHindustantimesBangali(src2f);

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
														Elements img = ee9.getElementsByTag("img");

														String src = img.attr("data-src");
														System.out.println(src);
														news.setSimage(src);
													}
													if (ee9.className().contains("headlineSec")) {
														Elements ee10 = ee9.children();
														for (Element ee11 : ee10) {
															if (ee11.className().contains("headline")) {
																String head = ee11.text();
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

	public static NewsDto secondURlHindustantimesBangali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			if (element.className().equals("cardHolder open")) {
				System.out.println("2222222222222");
				Elements article = element.getElementsByTag("article");

				for (Element article2 : article) {
					Elements article3 = article2.children();

					for (Element article4 : article3) {
						if (article4.className().equals("headline")) {
							String headline = article4.text();
							System.out.println(headline);
							newsDto.setMainheadline(headline);
						}
						if (article4.className().contains("pubtime width55")) {
							String date = article4.text();

							if (date.contains("Updated:")) {
								String tt = "Updated: ";
								date = date.substring(date.lastIndexOf("Updated: ") + tt.length(), date.length());

								if (date.contains(",")) {
									date = date.substring(0, date.lastIndexOf(","));
									newsDto.setMaindate(date);
									System.out.println(date);
								}

							}

						}
						if (article4.className().equals("storyExpend")) {
							Elements img = article4.getElementsByTag("source");

							String src = img.attr("srcset");
							newsDto.setMainImage(src);
							System.out.println(src);
						}
					}

				}

				Elements contentSec = element.children();
				for (Element contentSec2 : contentSec) {

					if (contentSec2.className().equals("contentSec")) {
						Elements contentSec3 = contentSec2.children();
						for (Element contentSec4 : contentSec3) {

							Elements contentSec6 = contentSec4.children();

							for (Element contentSec9 : contentSec6) {
								if (contentSec9.className().equals("mainArea")) {
									Elements p = contentSec9.getElementsByTag("p");
									for (Element p2 : p) {
										String se = p2.text();
//		System.out.println(se);
										if (!se.equals("")) {

											if (!p2.toString().contains("ਇਹ ਵੀ ਪੜ੍ਹੋ")
													&& !p2.toString().contains("twitter")) {
												if (ss.equals("")) {
													ss += p2.text();
													newsDto.setSummary(ss);
												} else {
													ss += "\n\n" + p2.text();
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
				}

			}
		}

		return newsDto;
	}

//	2

	@RequestMapping(value = { "/getNewsNews18Bangali" })
	public @ResponseBody Map<String, Object> getNewsNews18Bangali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("News18")) {
				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

					if (element.className().contains("jsx-1522687596")) {
						System.out.println("2222222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {
							if (ee.className().contains("jsx-1522687596 blog_list")) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {
									if (ee3.className().contains("jsx-2668003214 blog_list")) {
										Elements ee4 = ee3.children();
										for (Element ee5 : ee4) {
											News news = new News();
											if (ee5.className().contains("blog_list_row")) {
												Elements a = ee5.getElementsByTag("a");
												String href = a.attr("href");
												System.out.println(href);

												NewsDto newsDto = secondURlNews18Bangali(href);

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

				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlNews18Bangali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

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

										}
										System.out.println(src);
										newsDto.setMainImage(src);
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

										Elements p = ee.getElementsByTag("p");
										for (Element p2 : p) {
											String se = p2.text();
											if (!se.equals("")) {

												if (!p2.toString().contains("Tags")
														&& !p2.toString().contains("twitter")) {
													if (ss.equals("")) {
														ss += p2.text();
														newsDto.setSummary(ss);
													} else {
														ss += "\n\n" + p2.text();
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

		return newsDto;
	}

//	3
	@RequestMapping(value = { "/getNewsABPliveBangali" })
	public @ResponseBody Map<String, Object> getNewsABPliveBangali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("ABPlive")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

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
											NewsDto newsDto = secondURlABPliveBangali(href);

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

											String ss = ee5.text();
											news.setSimage(src);
											news.setSheadline(ss);
											newsList.add(news);
											System.out.println(ss);
											System.out.println();
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

			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlABPliveBangali(String url) throws IOException {

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

										if (date.contains("Updated at : ")) {
											String tt = "Updated at : ";
											date = date.substring(date.lastIndexOf("Updated at : ") + tt.length(),
													date.length());

											if (date.contains("(IST)")) {
												date = date.substring(0, date.lastIndexOf("(IST)"));
												newsDto.setMaindate(date);
												System.out.println(date);
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

								if (!p2.toString().contains("আরও পড়ুন:") && !se.contains("https:")
										&& !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();
										newsDto.setSummary(ss);
									} else {
										ss += "\n\n" + p2.text();
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
	@RequestMapping(value = { "/getNewsOneindiaBangali" })
	public @ResponseBody Map<String, Object> getNewsOneindiaBangali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Oneindia")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

					if (element.className().equals("oi-cityblock-list")) {
						System.out.println("2222222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {
							Elements ee2 = ee.children();
							for (Element ee3 : ee2) {
								if (ee3.className().equals("clearfix")) {
									Elements ee4 = ee3.children();
									News news = new News();
									for (Element ee5 : ee4) {
										if (ee5.className().equals("cityblock-img news-thumbimg")) {
											Elements a = ee5.getElementsByTag("a");

											String href = "https://bengali.oneindia.com" + a.attr("href");
											System.out.println(href);

											NewsDto newsDto = secondURlOneindiaBangali(href);

											news.setUrl(newsDto.getUrl());
											news.setMainheadline(newsDto.getMainheadline());

											news.setSummary(newsDto.getSummary());
											news.setMainImage(newsDto.getMainImage());

											news.setSurl(excel.getMainLink());

											news.setCategory(excel.getCategory());
											news.setLanguage(excel.getLanguage());
											news.setWebsiteName(excel.getWebsiteName());

											;

											Elements img = ee5.getElementsByTag("img");

											String src = img.attr("src");
											System.out.println(src);
											news.setSimage(src);
//											System.out.println();
										}
										if (ee5.className().equals("cityblock-desc")) {

											Elements ee45 = ee5.children();
											for (Element ee55 : ee45) {
												if (ee55.className().contains("cityblock-title")) {
													Elements h2 = ee55.getElementsByTag("h2");

													String head = h2.text();
													System.out.println(head);
													news.setSheadline(head);
												}
												if (ee55.className().contains("cityblock-time")) {
													String date = ee55.text();

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

	public static NewsDto secondURlOneindiaBangali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

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

									String src = "https://bengali.oneindia.com" + img.attr("src");
									System.out.println(src);
									newsDto.setMainImage(src);
								}
							}

						}

					}
					if (ee.className().equals("oicustomcontent")) {

						Elements p = ee.getElementsByTag("p");
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
//							System.out.println(ss);

						}

					}
				}
			}
		}

		return newsDto;
	}

//	5
	@RequestMapping(value = { "/getNewsETVBharatBangali" })
	public @ResponseBody Map<String, Object> getNewsETVBharatBangali(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("ETVBharat")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();

				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
//				count++;
//				System.out.println("...........  "+count);
					Element element = elements.get(i);
//				System.out.println("  ..........   "+element );

					if (element.className().equals("md:w-8/12 h-full px-2 md:flex md:flex-wrap")) {
						System.out.println("2222222222222");
//						Elements divChildren2 = element.getElementsByTag("a");
						Elements divChildren2 = element.children();
						for (Element ee : divChildren2) {
							News news = new News();
							if (ee.className()
									.contains("fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {
								Elements ee2 = ee.children();
								for (Element ee3 : ee2) {

									if (ee3.className().contains("flex w-full justify-between")) {

										Elements ee255 = ee3.children();
										for (Element ee25566 : ee255) {
											news = new News();
											if (ee25566.className().contains(
													"flex  flex-col pb-1 cursor-pointer rounded-md shadow  w-1/3 bg-white")) {

												Elements a = ee25566.getElementsByTag("a");

												String href = "https://www.etvbharat.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlETVBharatBangali(href);

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

//											System.out.println(a.text());

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
														news.setSheadline(head);
														System.out.println(head);
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
								NewsDto newsDto = secondURlETVBharatBangali(href);

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
								Elements img = ee.getElementsByTag("img");
								String src = "";
								if (img.toString().contains("data-src")) {
									src = img.attr("data-src");
									news.setSimage(src);
								} else {
									src = img.attr("src");
									news.setSimage(src);
								}
								System.out.println(src);

								String head = ee.text();

								System.out.println(head);
								news.setSheadline(head);
								newsList.add(news);
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

	public static NewsDto secondURlETVBharatBangali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

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
						for (Element ee22 : eee) {
							if (ee22.className().contains("md:border-gray-500")) {

								Elements eee3 = ee22.children();
								for (Element ee223 : eee3) {
									if (ee223.className().contains("fresnel-greaterThan-xs")) {

										Elements eee3f = ee223.children();
										for (Element ee223f : eee3f) {
											if (ee223f.className().contains("flex justify-between items-center")) {

												Elements eee3ff = ee223f.children();
												for (Element ee223xf : eee3ff) {
													if (ee223xf.className().contains("md:text-black always-english")) {

														String date = ee223xf.text();

														if (date.contains("Published: ")) {
															String tt = "Published: ";
															date = date.substring(
																	date.lastIndexOf("Published: ") + tt.length(),
																	date.length()).trim();

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
						}

					}
					if (ee.className().contains("text-base md:text-md")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("আরও পড়ুন:") && !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text();
										newsDto.setSummary(ss);
									} else {
										ss += "\n\n" + p2.text();
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
