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
public class NewsBhaskarController {

	@Autowired
	NewsRepository newsRepository;

//	@RequestMapping(value = { "/", "/welcome" })
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getNewsBhaskar" })
	public @ResponseBody Map<String, Object> getNewsBBC(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		File file = ResourceUtils.getFile("classpath:NewsBhaskar.xlsx");
		InputStream stream = FileUtils.openInputStream(file);
		File f = ResourceUtils.getFile("classpath:NewsDtoBhaskar.xml");
		System.out.println("processing excel file " + f.getName());
		List<NewsDtoExcel> records = ExcelReader.parseExcelFileToBeans(stream, f);
		for (NewsDtoExcel excel : records) {

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
		List<News> news = newsRepository.findAll();
		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
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

}
