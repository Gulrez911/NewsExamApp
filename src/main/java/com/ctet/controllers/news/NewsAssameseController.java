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
public class NewsAssameseController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = { "/getNewsETVbharatAssamese" })
	public @ResponseBody Map<String, Object> getNewsETVbharatAssamese(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
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

	public static NewsDto secondURlETVbharatAssamese(String url) throws IOException {

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

//	2
	@RequestMapping(value = { "/getNewsNews18Assamese" })
	public @ResponseBody Map<String, Object> getNewsNews18Assamese(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
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
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlNews18Assamese(String url) throws IOException {

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

//	3
	@RequestMapping(value = { "/getNewsNenowAssamese" })
	public @ResponseBody Map<String, Object> getNewsNenowAssamese(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:Assamese_News.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoAssameseNews.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {
			if (excel.getWebsiteName().equals("Nenow")) {

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
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURlNenowAssamese(String url) throws IOException {

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
}
