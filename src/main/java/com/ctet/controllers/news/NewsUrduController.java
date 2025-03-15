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
public class NewsUrduController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsBBCUrdu" })
	public @ResponseBody Map<String, Object> getNewsBBCUrdu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

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

				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

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

//	

	@RequestMapping(value = { "/getNewsNews18Urdu" })
	public @ResponseBody Map<String, Object> getNewsNews18Urdu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

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

				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
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

//

	@RequestMapping(value = { "/getNewsETVBharatUrdu" })
	public @ResponseBody Map<String, Object> getNewsETVBharatUrdu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Urdu_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoUrduNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

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

				map.put("news", newsList);
			}
		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
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
}
