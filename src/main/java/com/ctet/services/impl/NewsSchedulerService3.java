package com.ctet.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
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
import com.ctet.data.NewsVideo;
import com.ctet.repositories.NewsRepository;
import com.ctet.repositories.NewsVideoRepository;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;
import com.ctet.web.dto.NewsVideoDto;

@Service
public class NewsSchedulerService3 {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	NewsVideoRepository newsVideoRepository;

//	@Scheduled(cron = "0 */1 * * * *") // per 1 min
	public void performTaskUsingCronTotal33() throws Exception {

//		NewsSchedulerService2 newsSchedulerService2 = new NewsSchedulerService2();
//		newsSchedulerService2.performTaskUsingCronTotal();

		File fileBhas = ResourceUtils.getFile("classpath:NewsBhaskar.xlsx");
		InputStream streamBhas = FileUtils.openInputStream(fileBhas);
		File fBhas = ResourceUtils.getFile("classpath:NewsDtoBhaskar.xml");
		System.out.println("processing excel file " + fBhas.getName());
		List<NewsDtoExcel> recordsBhas = ExcelReader.parseExcelFileToBeans(streamBhas, fBhas);
		for (NewsDtoExcel excel : recordsBhas) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

			String urlExcel = excel.getMainLink();
//			int pageCount=Integer.parseInt(excel.getPage());  

			System.out.println(urlExcel);
			try {
				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);

					if (element.className().equals("e96634e0")) {
						System.out.println("2222222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {

							Elements sss = ee.children();
							for (Element ww : sss) {

								if (ww.className().contains("ff407105") || ww.className().contains("db9a2680")) {
									Elements li = ww.getElementsByTag("li");
									for (Element weeeew : li) {

										Elements dddd = weeeew.children();
										int count = 0;
										for (Element wee2eew : dddd) {
											if (count == 0) {
												Elements a = wee2eew.getElementsByTag("a");

												String first = "https://www.bhaskar.com";
												String url22 = a.attr("href");
												if (!url22.equals("")) {
													String href = first + url22;
													System.out.println(href);
													News news = new News();
													NewsDto newsDto = secondURlBhaskarHindi(href);

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

													for (Element aa : a) {
														Elements h3 = aa.getElementsByTag("h3");
														String head = h3.text();
														System.out.println(head);
														news.setSheadline(head);
														newsList.add(news);
														System.out.println();

													}
												}

												count = 1;
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

				if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
						&& news2.getMainImage() != null) {

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
								news22.setSimage(news2.getSimage());
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
			try {
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
			} catch (Exception e) {
				System.out.println(e);
				// TODO: handle exception
			}
			for (News news2 : newsList) {

				if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
						&& news2.getMainImage() != null) {

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

		System.out.println("test 1.............................................");

		File filett = ResourceUtils.getFile("classpath:NewsNdtvHi.xlsx");
		InputStream streamtt = FileUtils.openInputStream(filett);
		File ftt = ResourceUtils.getFile("classpath:NewsDtoNdtvHi.xml");
		System.out.println("processing excel file " + ftt.getName());
		List<NewsDtoExcel> recordstt = ExcelReader.parseExcelFileToBeans(streamtt, ftt);
		for (NewsDtoExcel excel : recordstt) {

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

									System.out.println(src2);
									news.setSimage(src2);
									Elements aa = ddd22.getElementsByTag("a");
									String url = aa.attr("href");
									//
									System.out.println(url);
									NewsDto newsDto = secondURlNdtvHindi(url);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());

									news.setMainImage(newsDto.getMainImage());
									news.setSummary(newsDto.getSummary());
									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());
								}
								if (ddd22.className().equals("news_Itm-cont")) {

									Elements ddd2222 = ddd22.children();

									for (Element ddd222233 : ddd2222) {

										if (ddd222233.className().equals("posted-by")) {
											Elements span = ddd222233.getElementsByTag("time");

											String date = span.text();
											if (date.contains("Updated:")) {

												String tt = "Updated:";
												date = date.substring(date.lastIndexOf("Updated:") + tt.length(),
														date.length()).trim();
												System.out.println(date);
												news.setMaindate(date);

												news.setSdate(date);
											}

										}
										if (ddd222233.className().equals("newsCont")) {
											Elements sssss = ddd222233.getElementsByTag("p");
//											String href = ss2dd.attr("href");
//											System.out.println(href);
											String sheadline = sssss.html().replace("&nbsp;", " ");
											System.out.println(sheadline);
											news.setSheadline(sheadline);
											newsList.add(news);

											System.out.println("....................");

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

//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}
		System.out.println("test 2.............................................");

		File file3 = ResourceUtils.getFile("classpath:NewsNdtvEn.xlsx");
		InputStream stream3 = FileUtils.openInputStream(file3);
		File f3 = ResourceUtils.getFile("classpath:NewsDtoNdtv.xml");
		System.out.println("processing excel file " + f3.getName());
		List<NewsDtoExcel> records3 = ExcelReader.parseExcelFileToBeans(stream3, f3);
		for (NewsDtoExcel excel : records3) {

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
				try {
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

//												Elements span = ddd222233.getElementsByTag("span");
//												String date = span.text();
//												if (date.contains("|")) {
												//
////													date = date.replace(" | ", "");
//													date = date.substring(date.indexOf('|') + 2, date.length()).trim();
//												}
												//
//												date = date.substring(date.indexOf(' '), date.lastIndexOf(",")).trim();
												//
////												System.out.println(sss);
//												news.setSdate(date);
//												news.setMaindate(date);
												newsList.add(news);

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

		System.out.println("test 3.............................................");

		System.out.println("test 4.............................................");

		File file6 = ResourceUtils.getFile("classpath:NewsHindustanTimes.xlsx");
		InputStream stream6 = FileUtils.openInputStream(file6);
		File f6 = ResourceUtils.getFile("classpath:NewsDtoHindustanTimes.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records6 = ExcelReader.parseExcelFileToBeans(stream6, f6);
		for (NewsDtoExcel excel : records6) {

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
				try {
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
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
				}
			}

			for (News news2 : newsList) {

				System.out.println(newsList.size());
			}
			for (News news2 : newsList) {

				if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
						&& news2.getMainImage() != null) {

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
		System.out.println("test 6.............................................");

		File file7 = ResourceUtils.getFile("classpath:NewsAmarujala.xlsx");
		InputStream stream7 = FileUtils.openInputStream(file7);
		File f7 = ResourceUtils.getFile("classpath:NewsDtoAmarujala.xml");
		System.out.println("processing excel file " + f7.getName());
		List<NewsDtoExcel> records7 = ExcelReader.parseExcelFileToBeans(stream7, f7);
		for (NewsDtoExcel excel : records7) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			try {
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
										|| ee.className().equals("__main_listing_content image_in_rgt")
										|| ee.className().equals("__main_listing_content ")) {
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
			} catch (Exception e) {
				System.out.println(e);
				// TODO: handle exception
			}
			for (News news2 : newsList) {

				if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
						&& news2.getMainImage() != null) {

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
//		List<News> news = newsRepository.findAll();

		System.out.println("test 7.............................................");

		File file8 = ResourceUtils.getFile("classpath:NewsBBC.xlsx");
		InputStream stream8 = FileUtils.openInputStream(file8);
		File f8 = ResourceUtils.getFile("classpath:NewsDtoBBC.xml");
		System.out.println("processing excel file " + f8.getName());
		List<NewsDtoExcel> records8 = ExcelReader.parseExcelFileToBeans(stream8, f8);
		for (NewsDtoExcel excel : records8) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			try {
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
														Elements img = weeeew.getElementsByTag("img");

														String src2 = img.attr("src");
														System.out.println(src2);
														news.setSimage(src2);
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
														news.setMaindate(weeeew.text());

														if (news.getMainheadline() == null) {
															news.setMainheadline(news.getSheadline());
														}
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

					System.out.println("Page no: " + j);
				}
			} catch (Exception e) {
				System.out.println(e);
				// TODO: handle exception
			}

			for (News news2 : newsList) {

				if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
						&& news2.getMainImage() != null) {

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
								news22.setSimage(news2.getSimage());
								news22.setUpdateDate(new Date());
								newsRepository.save(news22);
							}

						}

					}
					System.out.println(news33.size());
				}
				System.out.println(newsList.size());
			}

//			map.put("news", newsList);

		}
		System.out.println("test 8.............................................");

		File filetv9 = ResourceUtils.getFile("classpath:tv9hindi.xlsx");
		InputStream streamtv9 = FileUtils.openInputStream(filetv9);
		File ftv9 = ResourceUtils.getFile("classpath:NewsDtotv9hindi.xml");
		System.out.println("processing excel file " + ftv9.getName());
		List<NewsDtoExcel> recordstv9 = ExcelReader.parseExcelFileToBeans(streamtv9, ftv9);
		for (NewsDtoExcel excel : recordstv9) {

			System.out.println(excel.toString());

			List<News> newsList = new ArrayList<>();

//			Document doc = Jsoup.connect("https://www.tv9hindi.com").get();
			String urlExcel = excel.getMainLink();
			try {
				Document doc = Jsoup.connect(urlExcel).timeout(30000).get();

				System.out.println("url : " + doc.baseUri());
				Elements elements = doc.getAllElements();

				for (int i = 0; i < elements.size(); i++) {

					Element element = elements.get(i);

					if (element.className().contains("tv9_landingWidget")) {
						System.out.println("22222222222");
						Elements divChildren2 = element.children();

						for (Element ee : divChildren2) {
							News news = new News();
							Elements ss6 = ee.getElementsByTag("figure");

							for (Element sde : ss6) {

								Elements a = sde.getElementsByTag("a");
								String url = a.attr("href");
								System.out.println(url);
								NewsDto newsDto = secondURlTV9Hindi(url);

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

								System.out.println(a.text());
								news.setSheadline(a.text());
								for (Element ff : a) {
									Elements img = ff.getElementsByTag("img");

									String src = img.attr("data-src");
									if (src.contains("?w=")) {
										src = src.substring(0, src.lastIndexOf("?w="));
										System.out.println(src);

										news.setSimage(src);
										newsList.add(news);
										System.out.println();
									} else {
										System.out.println(src);
										news.setSimage(src);
										newsList.add(news);
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

//			map.put("totalRecords", newsList.size());
//			map.put("news", newsList);

		}

		System.out.println("test 9.............................................");

		System.out.println("test.............................................");

//		Assamese
		File file11 = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream11 = FileUtils.openInputStream(file11);
		File f11 = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
		System.out.println("processing excel file " + f11.getName());
		List<NewsDtoExcel> records11 = ExcelReader.parseExcelFileToBeans(stream11, f11);
		for (NewsDtoExcel excel : records11) {
			if (excel.getWebsiteName().equals("ETVbharat")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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
								if (ee.className().contains("fresnel-container")) {
									Elements ee2 = ee.children();
									for (Element ee3 : ee2) {
										News news = new News();
										if (ee.className().contains(
												"fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {

											Elements ee255 = ee3.children();
											for (Element ee25566 : ee255) {
												if (ee25566.className().contains(
														"flex  flex-col pb-1 cursor-pointer rounded-md shadow  w-1/3 bg-white")) {

													Elements a = ee25566.getElementsByTag("a");

													String href = "https://www.etvbharat.com" + a.attr("href");
													System.out.println(href);
													NewsDto newsDto = secondURlETVbharatAssamese(href);

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
								if (ee.className().contains("flex  justify-between px-1 pt-1 pb-1")) {

									Elements a = ee.getElementsByTag("a");

									String href = "https://www.etvbharat.com" + a.attr("href");
									System.out.println(href);
									NewsDto newsDto = secondURlETVbharatAssamese(href);

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
									} else {
										src = img.attr("src");
									}
									System.out.println(src);

									String head = ee.text();

									System.out.println(head);
									news2.setSimage(src);
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

					if (news2.getSimage() != null && news2.getSheadline() != null && news2.getSummary() != null
							&& news2.getMainImage() != null) {

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

		// 2

		File file22 = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream22 = FileUtils.openInputStream(file22);
		File f22 = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
		System.out.println("processing excel file " + f22.getName());
		List<NewsDtoExcel> records22 = ExcelReader.parseExcelFileToBeans(stream22, f22);
		for (NewsDtoExcel excel : records22) {
			if (excel.getWebsiteName().equals("News18")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

//				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//						count++;
//						System.out.println("...........  "+count);
						Element element = elements.get(i);
//						System.out.println("  ..........   "+element );

						if (element.className().contains("jsx-b029de3d7ec3b8c7")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("jsx-b029de3d7ec3b8c7 newctgrstorylist2")) {
									System.out.println("3333");
									Elements ee22 = ee.children();
									for (Element ee55 : ee22) {
										News news = new News();
										if (ee55.className().contains("jsx-6e2b7e1d578b138c")) {
											Elements a = ee55.getElementsByTag("a");

											String href = "https://assam.news18.com" + a.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlNews18Assamese(href);

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
											Elements ee556s6 = ee55.children();
											for (Element ee552 : ee556s6) {
												if (ee552.className().contains("jsx-6e2b7e1d578b138c")) {
													Elements img = ee552.getElementsByTag("img");
													for (Element img2 : img) {
														if (img2.toString()
																.contains("https://images.news18.com/assam/")) {
															String src = img2.attr("src");
															if (src.contains("?impolicy")) {
																src = src.substring(0, src.lastIndexOf("?impolicy"));
																System.out.println(src);
																news.setSimage(src);
															}
														}

													}

												}
												if (ee552.className().contains("jsx-6e2b7e1d578b138c")) {
													Elements h3 = ee552.getElementsByTag("h3");

													String head = h3.text();
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
//		3
		File file33 = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream33 = FileUtils.openInputStream(file33);
		File f33 = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
		System.out.println("processing excel file " + f33.getName());
		List<NewsDtoExcel> records33 = ExcelReader.parseExcelFileToBeans(stream33, f33);
		for (NewsDtoExcel excel : records33) {
			if (excel.getWebsiteName().equals("Nenow")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

//				Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//						count++;
//						System.out.println("...........  "+count);
						Element element = elements.get(i);
//						System.out.println("  ..........   "+element );

						if (element.className().contains("jeg_posts jeg_load_more_flag")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().contains("jeg_post jeg_pl_lg_2 format-standard")) {
									System.out.println("3333");
									Elements ee22 = ee.children();
									News news = new News();
									for (Element ee55 : ee22) {
										if (ee55.className().contains("jeg_thumb")) {
											Elements a = ee55.getElementsByTag("a");

											String href = a.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlNenowAssamese(href);

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
											Elements img = ee55.getElementsByTag("img");

											String src = img.attr("data-src");
											if (src.contains("?resize=")) {
												src = src.substring(0, src.lastIndexOf("?resize="));
												news.setSimage(src);
											}
											System.out.println(src);
											news.setSimage(src);

										}

										if (ee55.className().contains("jeg_postblock_content")) {
											Elements h3 = ee55.getElementsByTag("h3");

											String head = h3.text();
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
//		4 Bangali
		File file44 = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream44 = FileUtils.openInputStream(file44);
		File f44 = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f44.getName());
		List<NewsDtoExcel> records44 = ExcelReader.parseExcelFileToBeans(stream44, f44);
		for (NewsDtoExcel excel : records44) {
			if (excel.getWebsiteName().equals("Hindustan Times")) {

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

			}
		}
//		5
		File file55 = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream55 = FileUtils.openInputStream(file55);
		File f55 = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f55.getName());
		List<NewsDtoExcel> records55 = ExcelReader.parseExcelFileToBeans(stream55, f55);
		for (NewsDtoExcel excel : records55) {
			if (excel.getWebsiteName().equals("News18")) {
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

//		6

		File file66 = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream66 = FileUtils.openInputStream(file66);
		File f66 = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f66.getName());
		List<NewsDtoExcel> records66 = ExcelReader.parseExcelFileToBeans(stream66, f66);
		for (NewsDtoExcel excel : records66) {
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

			}
		}

//		7

		File file77 = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream77 = FileUtils.openInputStream(file77);
		File f77 = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f77.getName());
		List<NewsDtoExcel> records77 = ExcelReader.parseExcelFileToBeans(stream77, f77);
		for (NewsDtoExcel excel : records77) {
			if (excel.getWebsiteName().equals("Oneindia")) {

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

			}
		}
//		8
		File file88 = ResourceUtils.getFile("classpath:Bangali_News.xlsx");
		InputStream stream88 = FileUtils.openInputStream(file88);
		File f88 = ResourceUtils.getFile("classpath:NewsDtoBangaliNews.xml");
		System.out.println("processing excel file " + f88.getName());
		List<NewsDtoExcel> records88 = ExcelReader.parseExcelFileToBeans(stream88, f88);
		for (NewsDtoExcel excel : records88) {
			if (excel.getWebsiteName().equals("ETVBharat")) {

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

			}
		}

//		9 Gujarati

		File file99 = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream99 = FileUtils.openInputStream(file99);
		File f99 = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f99.getName());
		List<NewsDtoExcel> records99 = ExcelReader.parseExcelFileToBeans(stream99, f99);
		for (NewsDtoExcel excel : records99) {
			if (excel.getWebsiteName().equals("TV9")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
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

			}
		}

//		10

		File file1010 = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream1010 = FileUtils.openInputStream(file1010);
		File f1010 = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f1010.getName());
		List<NewsDtoExcel> records1010 = ExcelReader.parseExcelFileToBeans(stream1010, f1010);
		for (NewsDtoExcel excel : records1010) {
			if (excel.getWebsiteName().equals("BBC")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
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

			}
		}
//		11

		File file1111 = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream1111 = FileUtils.openInputStream(file1111);
		File f1111 = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f1111.getName());
		List<NewsDtoExcel> records1111 = ExcelReader.parseExcelFileToBeans(stream1111, f1111);
		for (NewsDtoExcel excel : records1111) {
			if (excel.getWebsiteName().equals("Oneindia")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {
						Document doc = connection.timeout(20000).get();

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
																date = date.substring(index4 + 1,
																		date.lastIndexOf("[IST]")).trim();

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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}

				} catch (NullPointerException e) {
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
//		12
		File file1212 = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream1212 = FileUtils.openInputStream(file1212);
		File f1212 = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f1212.getName());
		List<NewsDtoExcel> records1212 = ExcelReader.parseExcelFileToBeans(stream1212, f1212);
		for (NewsDtoExcel excel : records1212) {
			if (excel.getWebsiteName().equals("ABPlive")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {
						Document doc = connection.timeout(20000).get();

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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}

				} catch (NullPointerException e) {
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

//		13

		File file1313 = ResourceUtils.getFile("classpath:Gujarati_News.xlsx");
		InputStream stream1313 = FileUtils.openInputStream(file1313);
		File f1313 = ResourceUtils.getFile("classpath:NewsDtoGujaratiNews.xml");
		System.out.println("processing excel file " + f1313.getName());
		List<NewsDtoExcel> records1313 = ExcelReader.parseExcelFileToBeans(stream1313, f1313);
		for (NewsDtoExcel excel : records1313) {
			if (excel.getWebsiteName().equals("Indianexpress")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {

						Document doc = connection.timeout(20000).get();

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
														date = date.substring(
																date.lastIndexOf("Updated: ") + tt.length(),
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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}

				} catch (NullPointerException e) {
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
//		14 Kannada

		File file1414 = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream1414 = FileUtils.openInputStream(file1414);
		File f1414 = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
		System.out.println("processing excel file " + f1414.getName());
		List<NewsDtoExcel> records1414 = ExcelReader.parseExcelFileToBeans(stream1414, f1414);
		for (NewsDtoExcel excel : records1414) {
			if (excel.getWebsiteName().equals("News18")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");

					try {
						Document doc = connection.timeout(20000).get();

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
					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}

				} catch (NullPointerException e) {
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
//		15

		File file1515 = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream1515 = FileUtils.openInputStream(file1515);
		File f1515 = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
		System.out.println("processing excel file " + f1515.getName());
		List<NewsDtoExcel> records1515 = ExcelReader.parseExcelFileToBeans(stream1515, f1515);
		for (NewsDtoExcel excel : records1515) {
			if (excel.getWebsiteName().equals("TV9")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {
						Document doc = connection.timeout(20000).get();

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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}
				} catch (NullPointerException e) {
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
//		16

		File file1616 = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream1616 = FileUtils.openInputStream(file1616);
		File f1616 = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
		System.out.println("processing excel file " + f1616.getName());
		List<NewsDtoExcel> records1616 = ExcelReader.parseExcelFileToBeans(stream1616, f1616);
		for (NewsDtoExcel excel : records1616) {
			if (excel.getWebsiteName().equals("Oneindia")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {
						Document doc = connection.timeout(20000).get();

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
																date = date.substring(index4 + 1,
																		date.lastIndexOf("[IST]")).trim();
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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}

				} catch (NullPointerException e) {
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
//		17

		File file1717 = ResourceUtils.getFile("classpath:Kannada_News.xlsx");
		InputStream stream1717 = FileUtils.openInputStream(file1717);
		File f1717 = ResourceUtils.getFile("classpath:NewsDtoKannadaNews.xml");
		System.out.println("processing excel file " + f1717.getName());
		List<NewsDtoExcel> records1717 = ExcelReader.parseExcelFileToBeans(stream1717, f1717);
		for (NewsDtoExcel excel : records1717) {
			if (excel.getWebsiteName().equals("Zee News")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					try {
						Document doc = connection.timeout(20000).get();

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

					} catch (Exception e) {
						System.out.println(e);
						// TODO: handle exception
					}
				} catch (NullPointerException e) {
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
//		18 Malayalam

		File file1818 = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream1818 = FileUtils.openInputStream(file1818);
		File f1818 = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f1818.getName());
		List<NewsDtoExcel> records1818 = ExcelReader.parseExcelFileToBeans(stream1818, f1818);
		for (NewsDtoExcel excel : records1818) {
			if (excel.getWebsiteName().equals("ETVbharat")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");

					Document doc = connection.timeout(20000).get();

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
//		19

		File file1919 = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream1919 = FileUtils.openInputStream(file1919);
		File f1919 = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f1919.getName());
		List<NewsDtoExcel> records1919 = ExcelReader.parseExcelFileToBeans(stream1919, f1919);
		for (NewsDtoExcel excel : records1919) {
			if (excel.getWebsiteName().equals("Oneindia")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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

//		20

		File file2020 = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream2020 = FileUtils.openInputStream(file2020);
		File f2020 = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f2020.getName());
		List<NewsDtoExcel> records2020 = ExcelReader.parseExcelFileToBeans(stream2020, f2020);
		for (NewsDtoExcel excel : records2020) {
			if (excel.getWebsiteName().equals("News18")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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

//		21
		File file2121 = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream2121 = FileUtils.openInputStream(file2121);
		File f2121 = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f2121.getName());
		List<NewsDtoExcel> records2121 = ExcelReader.parseExcelFileToBeans(stream2121, f2121);
		for (NewsDtoExcel excel : records2121) {
			if (excel.getWebsiteName().equals("Indian Express")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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
//		22
		File file2222 = ResourceUtils.getFile("classpath:Malayalam_News.xlsx");
		InputStream stream2222 = FileUtils.openInputStream(file2222);
		File f2222 = ResourceUtils.getFile("classpath:NewsDtoMalayalamNews.xml");
		System.out.println("processing excel file " + f2222.getName());
		List<NewsDtoExcel> records2222 = ExcelReader.parseExcelFileToBeans(stream2222, f2222);
		for (NewsDtoExcel excel : records2222) {
			if (excel.getWebsiteName().equals("Zee News")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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
//		23 Marathi

		File file2323 = ResourceUtils.getFile("classpath:Marathi_News.xlsx");
		InputStream stream2323 = FileUtils.openInputStream(file2323);
		File f2323 = ResourceUtils.getFile("classpath:NewsDtoMarathiNews.xml");
		System.out.println("processing excel file " + f2323.getName());
		List<NewsDtoExcel> records2323 = ExcelReader.parseExcelFileToBeans(stream2323, f2323);
		for (NewsDtoExcel excel : records2323) {
			if (excel.getWebsiteName().equals("Loksatta")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//					count++;
//					System.out.println("...........  "+count);
						Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

						if (element.className().contains("wp-block-newspack-blocks-ie-stories is-style-borders")) {
							System.out.println("2222222222222");
							Elements article = element.getElementsByTag("article");
							for (Element ee5 : article) {

								Elements ee6 = ee5.children();
								News news = new News();
								for (Element ee7 : ee6) {
									Elements figure = ee7.getElementsByTag("figure");
									for (Element figure2 : figure) {
										Elements a = figure2.getElementsByTag("a");
										String href = a.attr("href");
										System.out.println(href);

										NewsDto newsDto = secondURlLoksattaMarathi(href);

										news.setUrl(newsDto.getUrl());
										news.setMainheadline(newsDto.getMainheadline());

										news.setMainImage(newsDto.getMainImage());
										news.setSummary(newsDto.getSummary());
										news.setSurl(excel.getMainLink());

										news.setCategory(excel.getCategory());
										news.setLanguage(excel.getLanguage());
										news.setWebsiteName(excel.getWebsiteName());

										Elements img = figure2.getElementsByTag("img");
										String src = img.attr("src");
										if (src.contains("?w")) {
											src = src.substring(0, src.lastIndexOf("?w"));
										}
										System.out.println(src);
										news.setSimage(src);
//									System.out.println();

									}

									if (ee7.className().contains("entry-wrapper")) {
										Elements eee6 = ee7.children();

										for (Element eee7 : eee6) {
											if (eee7.className().contains("entry-title")) {
												String head = eee7.text();
												System.out.println(head);
												news.setSheadline(head);
											}
											if (eee7.className().contains("entry-meta")) {
												Elements time = eee7.getElementsByTag("time");
												String date = time.text();
												if (date.contains("IST")) {
													date = date.substring(0, date.lastIndexOf("IST"));
													if (date.contains("Updated:")) {
														date = date.replace("Updated:", "").trim();
													}
													System.out.println(date);
												}
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

			}
		}

//		24
		File file2424 = ResourceUtils.getFile("classpath:Marathi_News.xlsx");
		InputStream stream2424 = FileUtils.openInputStream(file2424);
		File f2424 = ResourceUtils.getFile("classpath:NewsDtoMarathiNews.xml");
		System.out.println("processing excel file " + f2424.getName());
		List<NewsDtoExcel> records2424 = ExcelReader.parseExcelFileToBeans(stream2424, f2424);
		for (NewsDtoExcel excel : records2424) {
			if (excel.getWebsiteName().equals("TV9")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
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
									NewsDto newsDto = secondURlTV9Marathi(href);

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

			}
		}

//		25

		File file2525 = ResourceUtils.getFile("classpath:Marathi_News.xlsx");
		InputStream stream2525 = FileUtils.openInputStream(file2525);
		File f2525 = ResourceUtils.getFile("classpath:NewsDtoMarathiNews.xml");
		System.out.println("processing excel file " + f2525.getName());
		List<NewsDtoExcel> records2525 = ExcelReader.parseExcelFileToBeans(stream2525, f2525);
		for (NewsDtoExcel excel : records2525) {
			if (excel.getWebsiteName().equals("BBC")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
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
															String head = a.text();
															news.setSheadline(head);
															NewsDto newsDto = secondURlBBCMarathi(url);

															news.setUrl(newsDto.getUrl());
															news.setMainheadline(newsDto.getMainheadline());

															news.setMainImage(newsDto.getMainImage());
															news.setSummary(newsDto.getSummary());
															news.setSurl(excel.getMainLink());

															news.setCategory(excel.getCategory());
															news.setLanguage(excel.getLanguage());
															news.setWebsiteName(excel.getWebsiteName());

														}
													}
												}

												if (weeeew.className().contains("e1mklfmt0")) {
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

			}
		}

//		26

		File file2626 = ResourceUtils.getFile("classpath:Marathi_News.xlsx");
		InputStream stream2626 = FileUtils.openInputStream(file2626);
		File f2626 = ResourceUtils.getFile("classpath:NewsDtoMarathiNews.xml");
		System.out.println("processing excel file " + f2626.getName());
		List<NewsDtoExcel> records2626 = ExcelReader.parseExcelFileToBeans(stream2626, f2626);
		for (NewsDtoExcel excel : records2626) {
			if (excel.getWebsiteName().equals("News18")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

					System.out.println("url : " + doc.baseUri());
					Elements elements = doc.getAllElements();

					for (int i = 0; i < elements.size(); i++) {
//					count++;
//					System.out.println("...........  "+count);
						Element element = elements.get(i);
//					System.out.println("  ..........   "+element );

						if (element.className().contains("newctgrstorylist2")) {
							System.out.println("2222222222222");
//						Elements divChildren2 = element.children();
							Elements li = element.getElementsByTag("li");
							for (Element ee : li) {
								News news = new News();

								Elements ee22 = ee.children();
								for (Element ee55 : ee22) {

									Elements a = ee55.getElementsByTag("a");

									String href = "https://news18marathi.com" + a.attr("href");
									System.out.println(href);

									NewsDto newsDto = secondURlNews18Marathi(href);

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
									Elements ee5566 = ee55.children();
									for (Element ee55661 : ee5566) {
										Elements ee5566155 = ee55661.children();
										for (Element ee556631 : ee5566155) {
											if (ee556631.className().contains("img-figure")) {
												Elements img = ee556631.getElementsByTag("img");
												for (Element img2 : img) {
													String src = img2.attr("src");
													if (src.contains("?impolicy")) {
														src = src.substring(0, src.lastIndexOf("?impolicy"));
														System.out.println(src);
														news.setSimage(src);
													}
												}

											}
										}

									}
									String head = ee55.text();
									System.out.println(head);
									news.setSheadline(head);
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

			}
		}

//		27

		File file2727 = ResourceUtils.getFile("classpath:Marathi_News.xlsx");
		InputStream stream2727 = FileUtils.openInputStream(file2727);
		File f2727 = ResourceUtils.getFile("classpath:NewsDtoMarathiNews.xml");
		System.out.println("processing excel file " + f2727.getName());
		List<NewsDtoExcel> records2727 = ExcelReader.parseExcelFileToBeans(stream2727, f2727);
		for (NewsDtoExcel excel : records2727) {
			if (excel.getWebsiteName().equals("ABPlive")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Document doc = Jsoup.connect(urlExcel).timeout(200000).get();

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
												NewsDto newsDto = secondURlABPliveMarathi(href);

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
													news.setSimage(src);
												}
												System.out.println(src);

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

			}
		}

//		28 Nepali
		File file2828 = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream2828 = FileUtils.openInputStream(file2828);
		File f2828 = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f2828.getName());
		List<NewsDtoExcel> records2828 = ExcelReader.parseExcelFileToBeans(stream2828, f2828);
		for (NewsDtoExcel excel : records2828) {
			if (excel.getWebsiteName().equals("OnlineKhabar")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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

//		29

		File file2929 = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream2929 = FileUtils.openInputStream(file2929);
		File f2929 = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f2929.getName());
		List<NewsDtoExcel> records2929 = ExcelReader.parseExcelFileToBeans(stream2929, f2929);
		for (NewsDtoExcel excel : records2929) {
			if (excel.getWebsiteName().equals("Thahakhabar")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
//					Connection connection = Jsoup.connect(urlExcel);
//					connection.userAgent("Mozilla/5.0");
//					Document doc = connection.timeout(20000).get();

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

//		30

		File file3030 = ResourceUtils.getFile("classpath:Nepali_News.xlsx");
		InputStream stream3030 = FileUtils.openInputStream(file3030);
		File f3030 = ResourceUtils.getFile("classpath:NewsDtoNepaliNews.xml");
		System.out.println("processing excel file " + f3030.getName());
		List<NewsDtoExcel> records3030 = ExcelReader.parseExcelFileToBeans(stream3030, f3030);
		for (NewsDtoExcel excel : records3030) {
			if (excel.getWebsiteName().equals("Reporters Nepal")) {

				System.out.println(excel.toString());

				List<News> newsList = new ArrayList<>();

				String urlExcel = excel.getMainLink();
				try {
					Connection connection = Jsoup.connect(urlExcel);
					connection.userAgent("Mozilla/5.0");
					Document doc = connection.timeout(20000).get();

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
//		31 Odia

		File file3131 = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream3131 = FileUtils.openInputStream(file3131);
		File f3131 = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f3131.getName());
		List<NewsDtoExcel> records3131 = ExcelReader.parseExcelFileToBeans(stream3131, f3131);
		for (NewsDtoExcel excel : records3131) {
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
//				32

		File file3232 = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream3232 = FileUtils.openInputStream(file3232);
		File f3232 = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f3232.getName());
		List<NewsDtoExcel> records3232 = ExcelReader.parseExcelFileToBeans(stream3232, f3232);
		for (NewsDtoExcel excel : records3232) {
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
//		33
		File file3333 = ResourceUtils.getFile("classpath:Odia_News.xlsx");
		InputStream stream3333 = FileUtils.openInputStream(file3333);
		File f3333 = ResourceUtils.getFile("classpath:NewsDtoOdiaNews.xml");
		System.out.println("processing excel file " + f3333.getName());
		List<NewsDtoExcel> records3333 = ExcelReader.parseExcelFileToBeans(stream3333, f3333);
		for (NewsDtoExcel excel : records3333) {
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
//		34 Punjabi

		File file3434 = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream3434 = FileUtils.openInputStream(file3434);
		File f3434 = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f3434.getName());
		List<NewsDtoExcel> records3434 = ExcelReader.parseExcelFileToBeans(stream3434, f3434);
		for (NewsDtoExcel excel : records3434) {
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
//		35
		File file3535 = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream3535 = FileUtils.openInputStream(file3535);
		File f3535 = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f3535.getName());
		List<NewsDtoExcel> records3535 = ExcelReader.parseExcelFileToBeans(stream3535, f3535);
		for (NewsDtoExcel excel : records3535) {

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
//		36

		File file3636 = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream3636 = FileUtils.openInputStream(file3636);
		File f3636 = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f3636.getName());
		List<NewsDtoExcel> records3636 = ExcelReader.parseExcelFileToBeans(stream3636, f3636);
		for (NewsDtoExcel excel : records3636) {

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

//		37

		File file3737 = ResourceUtils.getFile("classpath:Punjabi_News.xlsx");
		InputStream stream3737 = FileUtils.openInputStream(file3737);
		File f3737 = ResourceUtils.getFile("classpath:NewsDtoPunjabiNews.xml");
		System.out.println("processing excel file " + f3737.getName());
		List<NewsDtoExcel> records3737 = ExcelReader.parseExcelFileToBeans(stream3737, f3737);
		for (NewsDtoExcel excel : records3737) {

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
//		38 Tamil

		File file3838 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream3838 = FileUtils.openInputStream(file3838);
		File f3838 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f3838.getName());
		List<NewsDtoExcel> records3838 = ExcelReader.parseExcelFileToBeans(stream3838, f3838);
		for (NewsDtoExcel excel : records3838) {
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

//		39

		File file3939 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream3939 = FileUtils.openInputStream(file3939);
		File f3939 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f3939.getName());
		List<NewsDtoExcel> records3939 = ExcelReader.parseExcelFileToBeans(stream3939, f3939);
		for (NewsDtoExcel excel : records3939) {
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

//		40

		File file4040 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream4040 = FileUtils.openInputStream(file4040);
		File f4040 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f4040.getName());
		List<NewsDtoExcel> records4040 = ExcelReader.parseExcelFileToBeans(stream4040, f4040);
		for (NewsDtoExcel excel : records4040) {
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

//		41

		File file4141 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream4141 = FileUtils.openInputStream(file4141);
		File f4141 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f4141.getName());
		List<NewsDtoExcel> records4141 = ExcelReader.parseExcelFileToBeans(stream4141, f4141);
		for (NewsDtoExcel excel : records4141) {
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

//		42
		File file4242 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream4242 = FileUtils.openInputStream(file4242);
		File f4242 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f4242.getName());
		List<NewsDtoExcel> records4242 = ExcelReader.parseExcelFileToBeans(stream4242, f4242);
		for (NewsDtoExcel excel : records4242) {
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

//		43

		File file4343 = ResourceUtils.getFile("classpath:Tamil_News.xlsx");
		InputStream stream4343 = FileUtils.openInputStream(file4343);
		File f4343 = ResourceUtils.getFile("classpath:NewsDtoTamilNews.xml");
		System.out.println("processing excel file " + f4343.getName());
		List<NewsDtoExcel> records4343 = ExcelReader.parseExcelFileToBeans(stream4343, f4343);
		for (NewsDtoExcel excel : records4343) {
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

																			news.setMainImage(newsDto.getMainImage());
																			news.setSimage(newsDto.getMainImage());
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
//		44 Telugu

		File file4444 = ResourceUtils.getFile("classpath:Telugu_News.xlsx");
		InputStream stream4444 = FileUtils.openInputStream(file4444);
		File f4444 = ResourceUtils.getFile("classpath:NewsDtoTeluguNews.xml");
		System.out.println("processing excel file " + f4444.getName());
		List<NewsDtoExcel> records4444 = ExcelReader.parseExcelFileToBeans(stream4444, f4444);
		for (NewsDtoExcel excel : records4444) {
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

												String href = "https://telugu.oneindia.com" + a.attr("href");
												System.out.println(href);
												NewsDto newsDto = secondURlOneindiaTelugu(href);

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

												String src = img.attr("src");
												System.out.println(src);
												news.setSimage(src);
//												System.out.println();
											}
											if (ee5.className().contains("cityblock-desc")) {
												Elements h2 = ee5.getElementsByTag("a");

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
//		45
		File file4545 = ResourceUtils.getFile("classpath:Telugu_News.xlsx");
		InputStream stream4545 = FileUtils.openInputStream(file4545);
		File f4545 = ResourceUtils.getFile("classpath:NewsDtoTeluguNews.xml");
		System.out.println("processing excel file " + f4545.getName());
		List<NewsDtoExcel> records4545 = ExcelReader.parseExcelFileToBeans(stream4545, f4545);
		for (NewsDtoExcel excel : records4545) {
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
									NewsDto newsDto = secondURlTV9Telugu(href);

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
									System.out.println(p.text());
									news.setSheadline(p.text());
									Elements img = ee.getElementsByTag("img");

									String src = img.attr("src");

									if (src.contains("?w=")) {
										src = src.substring(0, src.lastIndexOf("?w="));
										System.out.println(src);
										news.setSimage(newsDto.getMainImage());
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
//		46
		File file4646 = ResourceUtils.getFile("classpath:Telugu_News.xlsx");
		InputStream stream4646 = FileUtils.openInputStream(file4646);
		File f4646 = ResourceUtils.getFile("classpath:NewsDtoTeluguNews.xml");
		System.out.println("processing excel file " + f4646.getName());
		List<NewsDtoExcel> records4646 = ExcelReader.parseExcelFileToBeans(stream4646, f4646);
		for (NewsDtoExcel excel : records4646) {
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
								News news = new News();
								if (ee.className().equals("bbc-t44f9r")) {

									Elements sss = ee.getAllElements();
									for (Element ww : sss) {

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
															NewsDto newsDto = secondURlBBCTelugu(url);

															news.setUrl(newsDto.getUrl());

															news.setSdate(newsDto.getMaindate());
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

													System.out.println(weeeew.text());
													news.setMainheadline(weeeew.text());
													news.setMaindate(weeeew.text());
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
//		47

		File file4747 = ResourceUtils.getFile("classpath:Telugu_News.xlsx");
		InputStream stream4747 = FileUtils.openInputStream(file4747);
		File f4747 = ResourceUtils.getFile("classpath:NewsDtoTeluguNews.xml");
		System.out.println("processing excel file " + f4747.getName());
		List<NewsDtoExcel> records4747 = ExcelReader.parseExcelFileToBeans(stream4747, f4747);
		for (NewsDtoExcel excel : records4747) {
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
//									System.out.println("3333");
									Elements ee22 = ee.children();
									for (Element ee55 : ee22) {
										News news = new News();
										if (ee55.className().contains("blog_list_row")) {
											Elements a = ee55.getElementsByTag("a");
//											System.out.println("444444444");
											String href = a.attr("href");
											System.out.println(href);
											NewsDto newsDto = secondURlNews18Telugu(href);

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
											Elements ee5566 = ee55.children();
											for (Element ee55661 : ee5566) {
												Elements ee5566155 = ee55661.getElementsByTag("figure");
												for (Element ee556631 : ee5566155) {
//													if (ee556631.className().contains("jsx-eec58b514a6fb0")) {
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
//													}
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
//		48

		File file4848 = ResourceUtils.getFile("classpath:Telugu_News.xlsx");
		InputStream stream4848 = FileUtils.openInputStream(file4848);
		File f4848 = ResourceUtils.getFile("classpath:NewsDtoTeluguNews.xml");
		System.out.println("processing excel file " + f4848.getName());
		List<NewsDtoExcel> records4848 = ExcelReader.parseExcelFileToBeans(stream4848, f4848);
		for (NewsDtoExcel excel : records4848) {
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
								Elements ee22 = ee.children();
								for (Element ee223 : ee22) {
									if (ee223.className().equals("card-wrapper")) {
										Elements ee2 = ee223.children();
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
																	if (ee9.className()
																			.contains("listing-page-img-thumbnail")) {

																		Elements img = ee9.getElementsByTag("img");

																		String src = img.attr("srcset");

//																	if (flag == 0) {
																		if (src.contains("&w=96&q=75 1x")) {
																			src = "https://telugu.hindustantimes.com"
																					+ src.substring(0,
																							src.lastIndexOf("1x"));
																			System.out.println(src);
																			news.setSimage(src);
																		} else {
																			System.out.println(src);
																			news.setSimage(src);
																		}
//																		flag = 1;
//																	}

																	}
																	if (ee9.className().contains("listnewsContAdj")) {
																		Elements h2 = ee9.children();
																		for (Element h3 : h2) {

																			if (h3.className().contains(
																					"listingNewsCont listingNewsHead")) {
																				Elements a = h3.getElementsByTag("a");
																				String href = a.attr("href");
																				System.out.println(href);

																				NewsDto newsDto = secondURlHindustantimesTelugu(
																						href);

																				news.setUrl(newsDto.getUrl());
																				news.setMainheadline(
																						newsDto.getMainheadline());

																				news.setMainImage(
																						newsDto.getMainImage());
																				news.setSummary(newsDto.getSummary());
																				news.setSurl(excel.getMainLink());

																				news.setCategory(excel.getCategory());
																				news.setLanguage(excel.getLanguage());
																				news.setWebsiteName(
																						excel.getWebsiteName());
																				System.out.println(a.text());
																				news.setSheadline(a.text());
																			}
																			if (h3.className()
																					.contains("relNewsTime")) {
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
//		49

		File file4949 = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream4949 = FileUtils.openInputStream(file4949);
		File f4949 = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f4949.getName());
		List<NewsDtoExcel> records4949 = ExcelReader.parseExcelFileToBeans(stream4949, f4949);
		for (NewsDtoExcel excel : records4949) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("BBC")) {
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

						if (element.className().equals("bbc-1kz5jpr")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className().equals("bbc-t44f9r")) {

									Elements sss = ee.getAllElements();
									News news = new News();
									for (Element ww : sss) {

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

															NewsDto newsDto = secondURlUrduBBC(url);

															news.setUrl(newsDto.getUrl());
															news.setMainheadline(newsDto.getMainheadline());
//													news.setMaindate(newsDto.getMaindate());
															news.setSdate(newsDto.getMaindate());
															news.setSummary(newsDto.getSummary());
															news.setMainImage(newsDto.getMainImage());

															news.setSurl(excel.getMainLink());

															news.setCategory(excel.getCategory());
															news.setLanguage(excel.getLanguage());
															news.setWebsiteName(excel.getWebsiteName());

//													System.out.println(a.text());
															news.setSheadline(a.text());

														}
													}
												}

												if (weeeew.className().contains("e1mklfmt0")) {

													news.setMaindate(weeeew.text());
													news.setSdate(weeeew.text());
													newsList.add(news);
													System.out.println();
												}

											}
										}
									}
//							Elements sss = ee.getElementsByTag("img");
//							System.out.println(sss);
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
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

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
//		50

		File file5050 = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream5050 = FileUtils.openInputStream(file5050);
		File f5050 = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f5050.getName());
		List<NewsDtoExcel> records5050 = ExcelReader.parseExcelFileToBeans(stream5050, f5050);
		for (NewsDtoExcel excel : records5050) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("News18")) {
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

						if (element.className().equals("jsx-458440942 urdnwslist")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();
							for (Element ee : divChildren2) {
								News news = new News();
								if (ee.className().equals("jsx-458440942")) {
									Elements a = ee.getElementsByTag("a");

									String url = a.attr("href");
									System.out.println(url);

									Elements img = ee.getElementsByTag("img");

									String src = img.attr("srcset");
									String[] parts = src.split(" ");
									String first = parts[0];// "hello"
									String ere = "https://urdu.news18.com";
									ere += first;
									System.out.println(ere);
									news.setSimage(ere);
									NewsDto newsDto = secondURlUrduNews18(url);

									news.setUrl(newsDto.getUrl());
									news.setMainheadline(newsDto.getMainheadline());
//							news.setMaindate(newsDto.getMaindate());
									news.setSdate(newsDto.getMaindate());
									news.setMaindate(newsDto.getMaindate());
									news.setSummary(newsDto.getSummary());
									news.setMainImage(newsDto.getMainImage());

									news.setSurl(excel.getMainLink());

									news.setCategory(excel.getCategory());
									news.setLanguage(excel.getLanguage());
									news.setWebsiteName(excel.getWebsiteName());

//							System.out.println(a.text());

									Elements h3 = ee.getElementsByTag("h3");

									String ss = h3.text();
//							System.out.println(ss);
									System.out.println("...................");
									news.setSheadline(ss);
									newsList.add(news);
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
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

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
//		51
		File file5151 = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream5151 = FileUtils.openInputStream(file5151);
		File f5151 = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f5151.getName());
		List<NewsDtoExcel> records5151 = ExcelReader.parseExcelFileToBeans(stream5151, f5151);
		for (NewsDtoExcel excel : records5151) {

			System.out.println(excel.toString());
			if (excel.getWebsiteName().equals("EtvBharat")) {
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

						if (element.className().equals("md:w-8/12 h-full px-2 md:flex md:flex-wrap")) {
							System.out.println("2222222222222");
							Elements divChildren2 = element.children();

							for (Element ee : divChildren2) {
								if (ee.className()
										.contains("fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {

									Elements ee22 = ee.children();
									for (Element ee2233 : ee22) {
										if (ee2233.className()
												.contains("flex w-full justify-between space-x-4 flex-row-reverse")) {
											Elements ee2244 = ee2233.getElementsByTag("a");
											for (Element ee223366 : ee2244) {
												News news = new News();
												String after = "https://www.etvbharat.com";
												String href = after + ee223366.attr("href");
												System.out.println(href);

												Elements img = ee223366.getElementsByTag("img");

												String src = img.attr("src");
												System.out.println(src);

												System.out.println(ee223366.text());

												NewsDto newsDto = secondURlUrduETVBharat(href);
												news.setSimage(src);

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
												news.setSheadline(ee223366.text());
												newsList.add(news);

												System.out.println();
											}
										}
									}
									System.out.println("........");
								}
								News news2 = new News();
								if (ee.className().contains(
										"flex  justify-between px-1 pt-1 pb-1 cursor-pointer border shadow rtl rectangle-card bg-white mt-1 md:mt-2 md:w-1/2 rounded-md")) {

									String after = "https://www.etvbharat.com";
									String href = after + ee.attr("href");
									System.out.println(href);

									Elements img = ee.getElementsByTag("img");

									String src = img.attr("data-src");
									System.out.println(src);

									System.out.println(ee.text());
									news2.setSimage(src);
									news2.setSheadline(ee.text());
									NewsDto newsDto = secondURlUrduETVBharat(href);
									news2.setSimage(src);

									news2.setUrl(newsDto.getUrl());
									news2.setMainheadline(newsDto.getMainheadline());
									news2.setMaindate(newsDto.getMaindate());
									news2.setSdate(newsDto.getMaindate());
									news2.setSummary(newsDto.getSummary());
									news2.setMainImage(newsDto.getMainImage());

									news2.setSurl(excel.getMainLink());

									news2.setCategory(excel.getCategory());
									news2.setLanguage(excel.getLanguage());
									news2.setWebsiteName(excel.getWebsiteName());
									newsList.add(news2);
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
//				int ss = news2.getMainheadline().length();
//				System.out.println(ss);

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
//		52
		System.out.println("Done");
	}

	public static NewsDto secondURlETVbharatAssamese(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

								if (!p2.toString().contains("আরও পড়ুন:") && !p2.toString().contains("twitter")
										&& !p2.toString().contains("www.instagram.com")
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

	public static NewsDto secondURlNews18Assamese(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
//		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   " );

			if (element.className().contains("newleftwrap")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("nwarthd")) {
						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}

					if (ee.className().contains("nwartbx")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("nwartbximg")) {
								Elements img = ee3.getElementsByTag("img");
								String src = img.attr("src");
								if (src.contains("?im=")) {
									src = src.substring(0, src.lastIndexOf("?im="));
									System.out.println(src);
									newsDto.setMainImage(src);
								}
							}
							if (ee3.className().contains("nwartbxdtl")) {
								Elements ee34 = ee3.children();
								for (Element ee344 : ee34) {
									if (ee344.className().contains("nwartbyln")) {

										Elements time = ee344.getElementsByTag("time");

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

					if (ee.className().contains("nwartcnt arbodyactive arbodyhide")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("nwcntrgt")) {
								Elements ee24 = ee3.children();
								for (Element ee35 : ee24) {
									if (ee35.className().contains("nwartcntdtl")) {

										Elements p = ee35.getElementsByTag("p");
										for (Element p2 : p) {
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

	public static NewsDto secondURlNenowAssamese(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
//		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("jeg_inner_content")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("entry-header")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("jeg_post_title")) {
								Elements h1 = ee3.getElementsByTag("h1");

								String head = h1.text();
								System.out.println(head);
								newsDto.setMainheadline(head);
							}
							if (ee3.className().contains("jeg_meta_container")) {
								Elements ee23 = ee3.children();

								for (Element ee34 : ee23) {
									if (ee34.className().contains("jeg_post_meta jeg_post_meta_1")) {
										Elements ee344 = ee34.children();

										for (Element ee3446 : ee344) {
											if (ee3446.className().contains("meta_left")) {
												Elements ee3444 = ee3446.children();

												for (Element ee34436 : ee3444) {
													if (ee34436.className().contains("jeg_meta_date")) {
														Elements a = ee34436.getElementsByTag("a");

														String date = a.text();

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

					if (ee.className().contains("jeg_featured featured_image")) {
						Elements img = ee.getElementsByTag("img");

						String src = img.attr("src");
						if (src.contains("?resize=")) {
							src = src.substring(0, src.lastIndexOf("?resize="));
							newsDto.setMainImage(src);
						}
						System.out.println(src);
						newsDto.setMainImage(src);
					}

					if (ee.className().contains("entry-content no-share")) {
						Elements img = ee.children();
						for (Element img2 : img) {
							if (img2.className().contains("content-inner")) {
								Elements h4 = img2.getElementsByTag("h4");
								if (!h4.toString().equals("")) {
									String se = h4.text();
									if (!se.equals("")) {

										if (!h4.toString().contains("লগতে পঢ়ক:")
												&& !h4.toString().contains("ಮತ್ತಷ್ಟು ರಾಷ್ಟ್ರೀಯ")
												&& !h4.toString().contains("twitter")) {
											if (ss.equals("")) {
												ss += h4.text();

											} else {
												ss += "\n\n" + h4.text();
											}

										}
									}
								}
								Elements p = img2.getElementsByTag("p");
								for (Element p2 : p) {
									String se = p2.text();
									if (!se.equals("")) {

										if (!p2.toString().contains("লগতে পঢ়ক:")
												&& !p2.toString().contains("ಮತ್ತಷ್ಟು ರಾಷ್ಟ್ರೀಯ")
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

								}

							}
						}

					}
				}

			}

		}

		return newsDto;
	}

	// Bangali

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

//	Gujarati

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

	public static NewsDto secondURlOneindiaGujarati(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlABPliveGujarati(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlIndianexpressGujarati(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

//	Kannada

	public static NewsDto secondURlNews18Kannada(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlTV9Kannada(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlOneindiaKannada(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlZeeNewsKannada(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

//	Malayalam

	public static NewsDto secondURlETVbharatMalayalam(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlOneindiaMalayalam(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlNews18Malayalam(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlIndianExpressMalayalam(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlZeeNewsMalayalam(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

//	Marathi

	public static NewsDto secondURlLoksattaMarathi(String url) throws IOException {

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

			if (element.className().contains("wp-container-3 wp-block-column ie-network-grid__lhs")) {
				System.out.println("2222222222222");
				Elements article = element.children();
				for (Element ee5 : article) {

					if (ee5.className().contains("wp-block-post-title")) {
						String head = ee5.text();
						System.out.println(head);
						newsDto.setMainheadline(head);
					}
					if (ee5.className().contains("wp-block-post-featured-image")) {
						Elements img = ee5.getElementsByTag("img");
						String src = img.attr("src");
						if (src.contains("?w")) {
							src = src.substring(0, src.lastIndexOf("?w"));
						}
						System.out.println(src);
						newsDto.setMainImage(src);
					}

					if (ee5.className().contains("wp-container-2 entry-content wp-block-post-content")) {

						Elements p = ee5.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!p2.toString().contains("हेही वाचा") && !p2.toString().contains("twitter")) {
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

	public static NewsDto secondURlTV9Marathi(String url) throws IOException {

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
							if (date.contains("|")) {
								date = date.replace("|", "").trim();
							}
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
						} else {
							System.out.println(src);
						}
						newsDto.setMainImage(src);

					}

					if (ee2.className().contains("ArticleBody")) {

						Elements p = ee2.getElementsByTag("p");
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

	public static NewsDto secondURlBBCMarathi(String url) throws IOException {

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
							Elements h1 = ww.getElementsByTag("h1");
							String headline = h1.text();
							System.out.println(headline);
							newsDto.setMainheadline(headline);

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
									ss += sss;
								} else {
									ss += "\n\n" + sss;
								}
								newsDto.setSummary(ss);
							}

						}

					}
				}
			}
		}

		return newsDto;
	}

	public static NewsDto secondURlNews18Marathi(String url) throws IOException {

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

			if (element.className().contains("newleftwrap")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("nwarthd")) {
						Elements h1 = ee.getElementsByTag("h1");
						String head = h1.text();
						newsDto.setMainheadline(head);
						System.out.println(head);
					}

					if (ee.className().contains("nwartbx")) {

						Elements ee4 = ee.children();
						for (Element ee5 : ee4) {
							if (ee5.className().contains("nwartbximg")) {
								Elements img = ee5.getElementsByTag("img");
								String src = img.attr("src");
								if (src.contains("?im=")) {
									src = src.substring(0, src.lastIndexOf("?im="));
								}
								System.out.println(src);
								newsDto.setMainImage(src);
							}

							if (ee5.className().contains("nwartbxdtl")) {
								Elements ee455 = ee5.children();
								for (Element ee57 : ee455) {
									if (ee57.className().contains("nwartbyln")) {
										Elements time = ee5.getElementsByTag("time");

										String date = time.text();
										if (date.contains("IST")) {
											date = date.substring(0, date.lastIndexOf("IST"));
											newsDto.setMaindate(date);
											System.out.println(date);
										}

									}

								}

							}
						}

					}
					if (ee.className().contains("arbodyhide")) {
						Elements ee4 = ee.children();
						for (Element ee5 : ee4) {
							if (ee5.className().contains("nwcntrgt")) {
								Elements ee6 = ee5.children();
								for (Element ee7 : ee6) {
									if (ee7.className().contains("nwartcntdtl")) {

										Elements p = ee7.getElementsByTag("p");
										for (Element p2 : p) {
											String se = p2.text();
											if (!se.equals("")) {

												if (!p2.toString().contains("Tags")
														&& !p2.toString().contains("twitter")
														&& !p2.toString().contains("सूचना")) {
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

	public static NewsDto secondURlABPliveMarathi(String url) throws IOException {

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

			if (element.className().equals("uk-width-expand uk-position-relative")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {
							if (ee3.className().contains("article-title")) {
								Elements h1 = ee3.getElementsByTag("h1");
								String head = h1.text();
								if (!head.equals("")) {
									System.out.println(head);
									newsDto.setMainheadline(head);
								}
							}

							if (ee3.className().contains(
									"uk-flex uk-flex-bottom _no_margin_bottom uk-margin-bottom uk-flex-between")) {
								Elements p = ee3.getElementsByTag("p");
								String date = p.text();
								if (date.contains("at :")) {
									String tt = "at : ";
									date = date.substring(date.lastIndexOf("at : ") + tt.length(), date.length());
									if (date.contains("(IST)")) {

										date = date.substring(0, date.lastIndexOf("(IST)"));
										newsDto.setMaindate(date);
										System.out.println(date);
									}

								}
							}

							if (ee3.className().contains("news_featured cont_accord_to_img")) {

								Elements ee4 = ee3.children();
								for (Element ee5 : ee4) {
									if (ee5.className().contains("lead-image-for-article")) {
										Elements img = ee5.getElementsByTag("img");
										String src = img.attr("data-src");
										if (src.contains("?impolicy")) {
											src = src.substring(0, src.lastIndexOf("?impolicy"));
											newsDto.setMainImage(src);
										}
										System.out.println(src);
									}
								}

							}
						}
					}
					if (ee.className().contains("article-data _thumbBrk uk-text-break")) {

						Elements p = ee.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
							if (!se.equals("")) {

								if (!se.contains("Tags") && !se.contains("हेही वाचा:")
										&& !se.contains("Phone Launched:") && !se.toString().contains("twitter")) {
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

		return newsDto;
	}

//	Nepali

	public static NewsDto secondURlOnlineKhabarNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlThahakhabarNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
//		Connection connection = Jsoup.connect(url);
//		connection.userAgent("Mozilla/5.0");
//		Document doc2 = connection.timeout(20000).get();
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

	public static NewsDto secondURlReportersNepalNepali(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Connection connection = Jsoup.connect(url);
		connection.userAgent("Mozilla/5.0");
		Document doc2 = connection.timeout(20000).get();
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

//	Odia

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

//	Punjabi

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

//	Tamil

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

//	Telugu

	public static NewsDto secondURlOneindiaTelugu(String url) throws IOException {

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
					if (ee.className().contains("author-detail")) {
						Elements time = ee.getElementsByTag("time");

						String date = time.text();
						if (date.contains(",")) {
							String tt = ",";

							date = date.substring(date.indexOf(", ") + tt.length(), date.length());
							if (date.contains("[IST]")) {

								date = date.replace("[IST]", "").trim();
								newsDto.setMaindate(date);
//								System.out.println(date);
							}

						}
						System.out.println(date);
					}
					if (ee.className().equals("big_center_img")) {

						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {

							Elements ee4 = ee3.children();

							for (Element ee5 : ee4) {
								if (ee5.className().equals("onImage")) {
									Elements img = ee5.getElementsByTag("img");

									String src = "https://telugu.oneindia.com" + img.attr("src");
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
										ss += p2.text();
										newsDto.setSummary(ss);
									} else {
										ss += "\n\n" + p2.text();
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

	public static NewsDto secondURlTV9Telugu(String url) throws IOException {

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

	public static NewsDto secondURlBBCTelugu(String url) throws IOException {

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
//							Elements a = ww.getElementsByTag("h1");
							System.out.println(ww.text());
							newsDto.setMainheadline(ww.text());
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

	public static NewsDto secondURlNews18Telugu(String url) throws IOException {

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

							if (ee3.className().contains("khbren_section")) {
								Elements ee4 = ee3.children();

								for (Element ee5 : ee4) {
									if (ee5.className().contains("khbr_rght_sec")) {
										Elements ee45 = ee5.children();
										for (Element ee455 : ee45) {
											int first = ee455.toString().length();
											String ssss = ee455.toString();
											if (first > 5) {
												ssss = ssss.substring(0, 4);
												if (!ssss.contains("<div")) {

													Elements p = ee455.getElementsByTag("p");

													for (Element p2 : p) {

														if (!p2.toString().contains("class=")
																&& !p2.toString().contains("<strong>")) {
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

		return newsDto;
	}

	public static NewsDto secondURlHindustantimesTelugu(String url) throws IOException {

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
							System.out.println(src);
							newsDto.setMainImage(src);
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

								if (!p2.toString().contains("టాపిక్") && !p2.toString().contains("సంబంధిత కథనం")
										&& !p2.toString().contains("twitter")) {
									if (ss.equals("")) {
										ss += p2.text().trim();
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

//	Urdu
	public static NewsDto secondURlUrduBBC(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
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

				if (element2.className().contains("bbc-fa0wmp")) {

					Elements divChildren2 = element2.children();

					for (Element ww : divChildren2) {

						if (ww.className().contains("bbc-1pfktyq ebmt73l0")) {
							Elements a = ww.getElementsByTag("h1");
							System.out.println(a.text());
							newsDto.setMainheadline(a.text());

						}
						if (ww.className().contains("bbc-1pt3yso ebmt73l0")) {
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
						if (ww.className().contains("bbc-1vjaf6b")) {
							Elements ww44 = ww.children();

							for (Element ww4 : ww44) {
								if (ww4.className().contains("bbc-1rvtlej")) {
									Elements ww445 = ww4.children();

									for (Element ww47 : ww445) {
										if (ww47.className().contains("bbc-v8pmqw")) {

											System.out.println(ww47.text());
											newsDto.setMaindate(ww47.text());

										}
									}
								}
							}
						}
						if (ww.className().contains("bbc-4wucq3 ebmt73l0")) {
							Elements a = ww.getElementsByTag("p");
//							System.out.println(a.text());
							String sss = a.text();
							if (ss.equals("")) {
								ss += sss;
							} else {
								ss += "\n\n" + sss;
							}
							newsDto.setSummary(ss);

						}

					}
				}
			}
		}

		return newsDto;
	}

	public static NewsDto secondURlUrduNews18(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int flag = 0;

		String ss = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			if (element.className().contains("jsx-964381257 news_page news_page_scroll")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {

					if (ee.className().contains("jsx-964381257 container clearfix article_readmore")) {
						Elements ee22 = ee.children();
						for (Element ee44 : ee22) {
							if (ee44.className().contains("jsx-964381257 news_page_left")) {
								Elements ee2233 = ee44.children();
								for (Element ee55 : ee2233) {
									if (ee55.className().contains("jsx-964381257 news_title")) {
										System.out.println(ee55.text());
										newsDto.setMainheadline(ee55.text());
									}
									if (ee55.className().contains("jsx-964381257 article_content")) {
										Elements ee66 = ee55.children();
										for (Element ee5577 : ee66) {
											if (ee5577.className().contains("jsx-964381257 article_content_img")) {
												Elements img = ee5577.getElementsByTag("img");

												for (Element img2 : img) {
													if (img2.toString().contains("src=\"https://images.news18.com")) {
														String src = img2.attr("src");
														if (src.contains(".jpg") || src.contains(".jpeg")) {
															if (src.contains("?im=")) {
																src = src.substring(0, src.lastIndexOf("?im="));
																System.out.println(src);
																newsDto.setMainImage(src);
															} else {
																System.out.println(src);
																newsDto.setMainImage(src);
															}
														}

													}
												}

											}

											if (ee5577.className().contains("article_content_row")) {
												Elements ee626 = ee5577.children();
												for (Element ee55773 : ee626) {
													if (ee55773.className().contains("newbyeline")) {

														Elements ee6246 = ee55773.children();
														for (Element ee62462 : ee6246) {
															if (ee62462.className().contains("newbyeline-agency")) {

																Elements time = ee5577.getElementsByTag("time");
																System.out.println(time.text());
																newsDto.setMaindate(time.text());
															}
														}
													}
												}
												Elements h2 = ee5577.getElementsByTag("h2");

												ss += h2.text();
//												System.out.println(ss);
//													ss
											}
											if (ee5577.className().contains("storypara")) {

												if (ss.equals("")) {
													ss += ee5577.text();
//													System.out.println(ss);
													newsDto.setSummary(ss);
												} else {
													ss += "\n\n" + ee5577.text();
//													System.out.println(ss);
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

		return newsDto;
	}

	public static NewsDto secondURlUrduETVBharat(String url) throws IOException {

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

			if (element.className().equals("flex flex-col md:flex-col-reverse md:mb-8")) {
				Elements img = element.getElementsByTag("img");

				String src = img.attr("src");
				System.out.println(src);
				newsDto.setMainImage(src);
				Elements h1 = element.getElementsByTag("h1");

				System.out.println(h1.text());
				newsDto.setMainheadline(h1.text());
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("mb-3 md:border-b-2 md:border-gray-500")) {
						Elements ee33 = ee.children();

						for (Element ee4 : ee33) {
							if (ee4.className().contains("fresnel-container fresnel-greaterThan-xs")) {
								Elements ee334 = ee4.children();

								for (Element ee54 : ee334) {
									if (ee54.className()
											.contains("text-sm text-gray-600 md:text-black always-english")) {
										String date = ee54.text();

										if (date.contains("Published: ")) {
											String tt = "Published: ";
											date = date.substring(date.lastIndexOf("Published: ") + tt.length(),
													date.length());
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

			if (element.className().equals("text-base md:text-md")) {
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					if (ee.className().equals("content")) {

						Elements ee33 = ee.getElementsByTag("p");

						for (Element ee44 : ee33) {

							if (!ee44.toString().contains("پڑھیں") && !ee44.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += ee44.text();
//									System.out.println(ss);
									newsDto.setSummary(ss);
								} else {
									ss += "\n\n" + ee44.text();
//									System.out.println(ss);
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

//	new

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
							ss2 += tt.text();
							newsDto.setSummary(ss2);
						}
					}

				}
			}
		}

		return newsDto;
	}

	public static NewsDto secondURlNdtvHindi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		int flag = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		String ss2 = "";
		String ss = "";
		Document doc = Jsoup.connect(url).timeout(50000).ignoreHttpErrors(true).get();
		newsDto.setUrl(url); // URL
		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
			Elements heads = element.children();

//			section
			for (Element ddd2 : heads) {

				if (ddd2.className().contains("sp-hd")) {

					Elements date = ddd2.children();

					for (Element ssrr : date) {
						if (ssrr.className().contains("sp-ttl-wrp")) {

							Elements heads2 = ssrr.children();

							for (Element ssrraa : heads2) {
								if (ssrraa.className().contains("sp-ttl")) {
									Elements h1 = ssrraa.getElementsByTag("h1");
									String head = h1.text();
									if (!head.equals("")) {
										System.out.println(head);
										newsDto.setMainheadline(head);
									}

								}
							}

//							System.out.println(ssrr.text());
						}

					}

				}

//				

				if (ddd2.className().contains("row s-lmr")) {
					Elements headsw = ddd2.children();

					for (Element ell : headsw) {
						Elements ss2dd = ell.getElementsByTag("article");
						for (Element ell2 : ss2dd) {
							Elements ell3 = ell2.children();

							for (Element ell4 : ell3) {
								if (ell4.className().contains("sp-wrp")) {
//									System.out.println("//");
									Elements ell5ff = ell4.children();

									for (Element ell5ff8 : ell5ff) {
										if (ell5ff8.className().contains("sp-hd")) {
											Elements ell5ff85 = ell5ff8.children();

											for (Element ell5ff858 : ell5ff85) {
												if (ell5ff858.className().contains("sp-ttl-wrp")) {
													Elements ell5ff8588 = ell5ff858.children();

													for (Element ell5ff85885 : ell5ff8588) {
														if (ell5ff85885.className().contains("ins_storybody")) {
															Elements ell5fdf85885d = ell5ff85885.children();

															for (Element ell5ff85z885 : ell5fdf85885d) {
																if (ell5ff85z885.className()
																		.contains("ins_instory_dv")) {
																	Elements img = ell5ff85z885.getElementsByTag("img");
																	String src = img.attr("data-src");
																	System.out.println(src);
																	newsDto.setMainImage(src);
																	System.out.println("///////");
																}
																if (ell5ff85z885.className().contains("place_cont")) {
																	Elements b = ell5ff85z885.getElementsByTag("b");
																	String loc = b.text();
																	if (!loc.equals("")) {
																		ss += loc + " ";
																		flag = 1;
																	}

																}
															}

															Elements p = ell5ff85885.getElementsByTag("p");
															for (Element pp : p) {
																Elements ppp = pp.getElementsByTag("p");
																String first = ppp.toString();
																if (first.length() > 8) {
																	first = first.substring(0, 8);
																	if (!first.contains("class")) {
																		for (Element p2 : ppp) {
																			String se = p2.text();
																			if (!se.equals("")) {

																				if (!p2.toString().contains("Tags")
																						&& !p2.toString()
																								.contains("फॉलो")
																						&& !p2.toString()
																								.contains("twitter")
																						&& !p2.toString()
																								.contains("post")
																						&& !p2.toString()
																								.contains("देखें")
																						&& !p2.toString()
																								.contains("पढ़ें")) {
																					if (ss.equals("")) {
																						ss += p2.text();
																						newsDto.setSummary(ss);

																					} else {
																						if (flag == 1) {
																							ss += p2.text().trim();
																							newsDto.setSummary(ss);
																							flag = 0;
																						} else {
																							ss += "\n\n"
																									+ p2.text().trim();
																							newsDto.setSummary(ss);
																						}

																					}

																				}
																			}
//																System.out.println(ss);

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
//		

				}
			}

		}
		return newsDto;
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

									String date = el.text();

									if (date.contains("Updated: ") && date.contains("IST")) {
										String tt = "Updated: ";
										date = date.substring(date.lastIndexOf("Updated: ") + tt.length(),
												date.lastIndexOf("IST")).trim();
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

	public static NewsDto secondURlJansatta(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;

		String ss = "";
		String ss2 = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url); // url
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
							newsDto.setMainheadline(ee3.text()); // Mainheadline
						}
						if (ee3.className().contains("ie-network-post-meta")) {
							Elements tt = ee3.children();
							for (Element ee4 : tt) {
								if (ee4.className().contains("ie-network-post-meta-wrapper")) {
									System.out.println(ee4.text()); // date
									newsDto.setMaindate(ee4.text());
								}
							}

						}

						if (ee3.className().contains("wp-block-post-featured-image")) {
							Elements img = ee3.getElementsByTag("img");
							String imgMain = img.attr("src");
							System.out.println(imgMain);
							newsDto.setMainImage(imgMain); // image
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
											newsDto.setSummary(ss2); // summary
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

	public static NewsDto secondURlBBCHi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;
		String ss = "";
		int flag = 0;
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

				if (element2.className().contains("bbc-fa0wmp")) {
					System.out.println("222222222222");
					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {
						if (ee.className().contains("ebmt73l0")) {
							Elements ee2 = ee.children();

							for (Element ee3 : ee2) {

								if (ee3.className().contains("e1p3vdyi0")) {
									Elements h1 = ee3.getElementsByTag("h1");
									String head = h1.text();
									System.out.println(head);
									newsDto.setMainheadline(head);
								}
								if (ee3.className().contains("e1aczekt0")) {
									Elements src2 = ee3.getElementsByTag("img");
									for (Element ww566 : src2) {
										if (flag == 0) {
											String src2f = ww566.attr("src");
											System.out.println(src2f);
											newsDto.setMainImage(src2f);
											flag = 1;
										}

									}
								}

								if (ee3.className().contains("e17g058b0")) {
									Elements a = ee3.getElementsByTag("p");
									String sss = a.text();
//											System.out.println(sss);
									if (!sss.toString().contains("(बीबीसी न्यूज")
											&& !a.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += sss;
											newsDto.setSummary(ss);
										} else {
											ss += "\n\n" + sss;
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

		return newsDto;
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

	public static NewsDto secondURlBhaskarHindi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();
		int ii = 0;
		int count = 0;
		String ss = "";
		String ss2 = "";
		try {
			Document doc2 = Jsoup.connect(url).get();
			newsDto.setUrl(url);
			Elements elements = doc2.getAllElements();
			System.out.println("/////////////////       " + elements.size());

			for (int i = 0; i < elements.size(); i++) {

				Element element = elements.get(i);

				Elements divChildren = element.children();

				for (Element element2 : divChildren) {

					if (element2.className().contains("bf64dc76")) {
						System.out.println("22222222222");
						Elements divChildren2 = element2.children();

						for (Element ee : divChildren2) {

							if (ee.className().contains("a88a1c42")) {

								Elements h1 = ee.getElementsByTag("h1");
								String head = h1.text();
								System.out.println(head);
								newsDto.setMainheadline(head);
							}

							if (ee.className().contains("d1172d9b")) {
								Elements ee2 = ee.children();

								for (Element ee3 : ee2) {

									if (ee3.className().contains("edd8d071")) {

										Elements ee4 = ee3.children();

										for (Element ee5 : ee4) {

											if (ee5.className().contains("c49a6b85")) {
												String date = ee5.text();
												System.out.println(date);
												newsDto.setMaindate(date);
											}
										}

									}
								}
							}
							if (ee.className().contains("f3e032cb")) {

								if (count == 0) {
									Elements img = ee.getElementsByTag("img");

									String src = img.attr("src");
									System.out.println(src); // Mainimage
									newsDto.setMainImage(src);
									count = 1;
								}

							}

							Elements a = ee.getElementsByTag("p");
							String sss = a.text();
							if (!sss.equals("")) {
//						System.out.println(sss);
								if (!sss.toString().contains("देखें") && !a.toString().contains("twitter")) {
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

		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return newsDto;
	}

	public static NewsDto secondURlTV9Hindi(String url) throws IOException {

		NewsDto newsDto = new NewsDto();

		String ss = "";
		String ss2 = "";
		Document doc2 = Jsoup.connect(url).get();
		newsDto.setUrl(url);
		Elements elements = doc2.getAllElements();
		System.out.println("/////////////////       " + elements.size());

		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

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

						String date = ee2.text();

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

					if (ee2.className().contains("ArticleBodyCont")) {

						Elements p = ee2.getElementsByTag("p");
						for (Element p2 : p) {
							String se = p2.text();
//	System.out.println(se);
							if (!se.equals("")) {

								if (!p2.toString().contains("ये भी पढ़ें") && !p2.toString().contains("twitter")) {
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

		return newsDto;
	}
}
