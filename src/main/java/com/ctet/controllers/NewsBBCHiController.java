package com.ctet.controllers;

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
public class NewsBBCHiController {

	@Autowired
	NewsRepository newsRepository;

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNewsBBC" })
	public @ResponseBody Map<String, Object> getNewsBBC(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsBBC.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBBC.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

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
																NewsDto newsDto = secondURl(url);
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

			map.put("news", newsList);

		}
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
	}

	public static NewsDto secondURl(String url) throws IOException {

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

//	public static NewsDto secondURl(String url) throws IOException {
//
//		NewsDto newsDto = new NewsDto();
//		int ii = 0;
//
//		String sum = "";
//		Document doc2 = Jsoup.connect(url).get();
//		newsDto.setUrl(url);
//		Elements elements = doc2.getAllElements();
//		System.out.println("/////////////////       " + elements.size());
//
//		for (int i = 0; i < elements.size(); i++) {
//
//			Element element = elements.get(i);
////			System.out.println("  ..........   "+element );
////			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
////			if (element.className().equals("tbl-forkorts-article-active")) {
//
//			Elements divChildren = element.children();
//
//			for (Element element2 : divChildren) {
//
//				if (element2.className().contains("bbc-irdbz7 ebmt73l0")) {
//
//					Elements divChildren2 = element2.children();
//
//					for (Element ee : divChildren2) {
//
////						System.out.println("rrrrrr ");
//						Elements sdsds = ee.children();
//
////						if (sdsds.className().contains("bbc-19j92fr ebmt73l0")) {
//
////							Elements sss = ee.getAllElements();
//						for (Element ww : sdsds) {
//
//							if (ww.className().contains("bbc-1151pbn ebmt73l0")) {
//
//								System.out.println("  Head:   " + ww.text()); // Headline
//								newsDto.setMainheadline(ww.text());
//							}
//							if (ww.className().contains("e1j2237y6 bbc-q4ibpr ebmt73l0")) {
//
//								Elements sss = ww.children();
//								for (Element sde : sss) {
//
//									Elements ssss = sde.children();
//									for (Element ab : ssss) {
//										Elements bc = ab.children();
//
//										for (Element cd : bc) {
//
//											if (cd.className().contains("bbc-1a3w4ok euvj3t11")) {
//												System.out.println("  name:   " + cd.text());
//												newsDto.setMainheadline(ww.text());
//											}
//											if (cd.className().contains("bbc-1p92jtu euvj3t10")) {
//												System.out.println("  bbc:   " + cd.text());
//												newsDto.setMainheadline(ww.text());
//												break;
//											}
//
//										}
//
//									}
//
//								}
//							}
//
//							if (ww.className().contains("bbc-1ka88fa ebmt73l0")) {
//								Elements sss = ww.children();
//
//								for (Element cd : sss) {
//
//									if (cd.className().contains("bbc-1qdcvv9 e1aczekt0")) {
//										Elements we = cd.children();
//
//										for (Element se : we) {
//											if (se.className().contains("bbc-172p16q ebmt73l0")) {
//												Elements sd = se.children();
//												if (ii == 0) {
//													for (Element sssd : sd) {
//														if (sssd.className().contains("bbc-189y18v ebmt73l0")) {
////														System.out.println(sssd);
//															Elements ss6 = sssd.getElementsByTag("img");
//															String src22 = ss6.attr("src");
//															System.out.println(src22);
//
//															newsDto.setMainImage(src22);
//														}
//														if (sssd.className().contains("bbc-3edg7g ebmt73l0")) {
//															System.out.println(sssd.text()); // image info
//
////															newsDto.setMainheadline(ww.text());
//														}
//													}
//													ii++;
//												}
//											}
//										}
//									}
//								}
//							}
//
//							if (ww.className().contains("e1j2237y7 bbc-q4ibpr ebmt73l0")) {
//
//								Elements mm = ww.children();
//								for (Element elementee : mm) {
//									if (elementee.className().contains("bbc-zyc2da e1mklfmt0")) {
//										System.out.println(elementee.text()); // date
//										newsDto.setMaindate(elementee.text());
//									}
//								}
//
//							}
//
//							if (ww.className().contains("bbc-19j92fr ebmt73l0")) {
//								Elements sss = ww.children();
//								for (Element cd : sss) {
//									if (cd.className().contains("bbc-kl1u1v e17g058b0")
//											&& !cd.toString().contains("ीबीसी हिन्दी")&& !cd.toString().contains("ये भी पढ़ें")) {
//										System.out.println(" p " + cd.text());
//										sum += cd.text();
//										newsDto.setSummary(sum);
//										System.out.println(" ...............  ");
//									}
//								}
//							}
//
//						}
//
//					}
//				}
//			}
//		}
//
//		return newsDto;
//	}

}
