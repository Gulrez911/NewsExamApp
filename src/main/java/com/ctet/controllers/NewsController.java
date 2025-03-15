package com.ctet.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.common.EmailGenericMessageThread;
import com.ctet.common.ExcelReader;
import com.ctet.common.PropertyConfig;
import com.ctet.data.News;
import com.ctet.repositories.NewsRepository;
import com.ctet.services.impl.NewsSchedulerService;
import com.ctet.services.impl.SchedulerNews;
import com.ctet.services.impl.TaskSchedulerService;
import com.ctet.web.dto.JobOne;
import com.ctet.web.dto.NewsDto;
import com.ctet.web.dto.NewsDtoExcel;

@Controller
public class NewsController {

	@Autowired
	NewsRepository newsRepository;

	private final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	TaskSchedulerService taskSchedulerService;
	@Autowired
	PropertyConfig config;

	@Autowired
	NewsSchedulerService service;

	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public @ResponseBody String schedule(@RequestParam("jobName") String jobName,
			@RequestParam("cronExpression") String cronExpression) {
		logger.info("Requested for job run {} with cron {}", jobName, cronExpression);
		JobOne jobOne = new JobOne(jobName);
		taskSchedulerService.addTaskToScheduler("1", jobOne, cronExpression);
		System.out.println("222222222");

		return "Started";
	}

	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getNews() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("222222222");
		service.performTaskUsingCronOneindia();
//		service.performTaskUsingCronAmarujala();
//		service.performTaskUsingCronBBCHi();
//		service.testNews2();
		List<News> news = newsRepository.findAll();
		map.put("news", news);

		map.put("totalRecords", news.size());
		map.put("status", "ok");
		return map;
	}

	@RequestMapping(value = "/testNews", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> testNews() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("222222222");
		service.testNews();
		service.testNews2();
		map.put("status", "ok");
		return map;
	}

	@RequestMapping(value = "/performTaskUsingCron", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> performTaskUsingCron() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("222222222");
		service.performTaskUsingCron();
		List<News> news = newsRepository.findAll();
		map.put("news", news);

		map.put("totalRecords", news.size());
		map.put("status", "ok");
		return map;
	}

	@RequestMapping(value = "/getNewsTV9", method = RequestMethod.GET)
	public @ResponseBody String getNewsTV9(@RequestParam("jobName") String jobName,
			@RequestParam("cronExpression") String cronExpression) {
		logger.info("Requested for job run {} with cron {}", jobName, cronExpression);
//		JobOne jobOne = new JobOne(jobName);
//		taskSchedulerService.addNewsTV9Scheduler("1", jobOne, cronExpression);

//		TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
//	       CronTrigger cronTrigger = new CronTrigger(cronExpression, timeZone);
//		SchedulerTask schedulerTask=  new SchedulerTask(scheduler.getTestId(), scheduler.getTestName(), scheduler.getCompanyId(), config.getBaseUrl(), scheduler.getUserEmails(), config.getTestLinkHtml_Generic_Location(), config);

		// SchedulerNews schedulerNews = new SchedulerNews(jobName);

//		taskSchedulerService.addNewsTV9Scheduler("1", schedulerNews, cronExpression);

		EmailGenericMessageThread thread = new EmailGenericMessageThread("gulfarooqui09@gmail.com", "Reset", "123",
				config);
		thread.run();

		return "Started";
	}

	@RequestMapping(value = { "/getCategoryNews" })
	public @ResponseBody Map<String, Object> getCategoryNews(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("category") String category) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<News> news = newsRepository.getCategoryNews(category);
		map.put("totalRecords", news.size());
		map.put("news", news);

		return map;

	}

	@RequestMapping(value = { "/getNotification" })
	public @ResponseBody Map<String, Object> getNotification(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<News> newsList = newsRepository.findAllByOrderByIdDescNoti();
		News news2 = new News();
		Collections.shuffle(newsList);
		List<News> newsList2 = new ArrayList<>();
		for (int i = 0; i <= newsList.size(); i++) {
			if (i == 0) {
				newsList2.add(newsList.get(i));

//				news2.setSheadline(newsList.get(i).getSheadline());
//				news2.setSdate(newsList.get(i).getSdate());
//				news2.setSimage(newsList.get(i).getSimage());
//				news2.setSurl(newsList.get(i).getSurl());
//				news2.setUrl(newsList.get(i).getUrl());
//				news2.setMainheadline(newsList.get(i).getMainheadline());
//				news2.setMaindate(newsList.get(i).getMaindate());
//				news2.setMainImage(newsList.get(i).getMainImage());
//				news2.setSummary(newsList.get(i).getSummary());
//				news2.setCategory(newsList.get(i).getCategory());
//				news2.setWebsiteName(newsList.get(i).getWebsiteName());
//				news2.setLanguage(newsList.get(i).getLanguage());

				break;
			}
		}

		map.put("news", newsList2);

		return map;

	}

//	@RequestMapping(value = { "/newsByLanguage" })
//	public @ResponseBody Map<String, Object> newsByLanguage(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(name = "en", required = false) String en,
//			@RequestParam(name = "hi", required = false) String hi) throws Exception {
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (hi.equals("hi")&&en.equals("En")) {
//			List<News> news = newsRepository.findNewsByLang("Hindi");
//		} else if (en.equals("en")) {
//			List<News> news = newsRepository.findNewsByLang("English");
//		}
//
////		List<News> news = newsRepository.findNewsByLang(language);
//		map.put("all", news.size());
//		map.put("news", news);
//
//		return map;
//
//	}

	@RequestMapping(value = { "/newsByLanguage" })
	public @ResponseBody Map<String, Object> newsByLanguage(HttpServletRequest request, HttpServletResponse response,

			@RequestParam(name = "language", required = false) String language) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
//		if (hi.equals("hi")&&en.equals("En")) {
//			List<News> news = newsRepository.findNewsByLang("Hindi");
//		} else if (en.equals("en")) {
//			List<News> news = newsRepository.findNewsByLang("English");
//		}

		List<News> news = newsRepository.findNewsByLang(language);
		map.put("all", news.size());
		map.put("news", news);

		return map;
	}

	@RequestMapping(value = { "/newsFilter" })
	public @ResponseBody Map<String, Object> newsFilter(HttpServletRequest request, HttpServletResponse response,

			@RequestParam(name = "eng", required = false) String eng,
			@RequestParam(name = "hin", required = false) String hin,
			@RequestParam(name = "urd", required = false) String urd,
			@RequestParam(name = "ben", required = false) String ben,
			@RequestParam(name = "mar", required = false) String mar,
			@RequestParam(name = "guj", required = false) String guj,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "tam", required = false) String tam,
			@RequestParam(name = "mal", required = false) String mal,
			@RequestParam(name = "kan", required = false) String kan,
			@RequestParam(name = "pun", required = false) String pun,
			@RequestParam(name = "odi", required = false) String odi,
			@RequestParam(name = "ass", required = false) String ass,
			@RequestParam(name = "nep", required = false) String nep) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<News> news = new ArrayList<News>();

//		if (en != null && hi == null) {
//			news = newsRepository.findNewsByLang(en);
//		} else if (en == null && hi != null) {
//			news = newsRepository.findNewsByLang(hi);
//		} else {
//			news = newsRepository.findAllByOrderByIdDesc();
//		}

		map.put("all", news.size());
		map.put("news", news);

		return map;
	}

	@RequestMapping(value = { "/getNotificationLang" })
	public @ResponseBody Map<String, Object> getNotificationLang(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "eng", required = false) String eng,
			@RequestParam(name = "hin", required = false) String hin,
			@RequestParam(name = "urd", required = false) String urd,
			@RequestParam(name = "ben", required = false) String ben,
			@RequestParam(name = "mar", required = false) String mar,
			@RequestParam(name = "guj", required = false) String guj,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "tam", required = false) String tam,
			@RequestParam(name = "mal", required = false) String mal,
			@RequestParam(name = "kan", required = false) String kan,
			@RequestParam(name = "pun", required = false) String pun,
			@RequestParam(name = "odi", required = false) String odi,
			@RequestParam(name = "ass", required = false) String ass,
			@RequestParam(name = "nep", required = false) String nep
			 

	) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<News> news = newsRepository.getNotificationLang(eng, hin, urd, ben, mar, guj, tel, tam, mal, kan, pun, odi,
				ass, nep);
		Collections.shuffle(news);
		List<News> newss =  new ArrayList<>();
		int count=0;
//		News news222 = new News();
		for ( News news2:news) {
			if(count==0) {
				newss.add(news2);
				count=1;
			}
			
		}
		map.put("all", newss.size());
		map.put("news", newss);

		return map;
	}
	
	@RequestMapping(value = { "/getNewsAll" })
	public @ResponseBody Map<String, Object> getNewsAll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "eng", required = false) String eng,
			@RequestParam(name = "hin", required = false) String hin,
			@RequestParam(name = "urd", required = false) String urd,
			@RequestParam(name = "ben", required = false) String ben,
			@RequestParam(name = "mar", required = false) String mar,
			@RequestParam(name = "guj", required = false) String guj,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "tam", required = false) String tam,
			@RequestParam(name = "mal", required = false) String mal,
			@RequestParam(name = "kan", required = false) String kan,
			@RequestParam(name = "pun", required = false) String pun,
			@RequestParam(name = "odi", required = false) String odi,
			@RequestParam(name = "ass", required = false) String ass,
			@RequestParam(name = "nep", required = false) String nep
			 

	) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<News> news = newsRepository.getNewsAll(eng, hin, urd, ben, mar, guj, tel, tam, mal, kan, pun, odi,
				ass, nep);
		Collections.shuffle(news);
		map.put("all", news.size());
		map.put("news", news);

		return map;
	}
	
	@RequestMapping(value = { "/findNewsByLang1" })
	public @ResponseBody Map<String, Object> findNewsByLang1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "eng", required = false) String eng,
			@RequestParam(name = "hin", required = false) String hin,
			@RequestParam(name = "urd", required = false) String urd,
			@RequestParam(name = "ben", required = false) String ben,
			@RequestParam(name = "mar", required = false) String mar,
			@RequestParam(name = "guj", required = false) String guj,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "tam", required = false) String tam,
			@RequestParam(name = "mal", required = false) String mal,
			@RequestParam(name = "kan", required = false) String kan,
			@RequestParam(name = "pun", required = false) String pun,
			@RequestParam(name = "odi", required = false) String odi,
			@RequestParam(name = "ass", required = false) String ass,
			@RequestParam(name = "nep", required = false) String nep,
			@RequestParam(name = "cat", required = false) String cat

	) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<News> news = newsRepository.findNewsByFilter(eng, hin, urd, ben, mar, guj, tel, tam, mal, kan, pun, odi,
				ass, nep, cat);
		Collections.shuffle(news);
		map.put("all", news.size());
		map.put("news", news);

		return map;
	}

	@RequestMapping(value = { "/newsByLanguage2" })
	public @ResponseBody Map<String, Object> newsByLanguage2(HttpServletRequest request, HttpServletResponse response,

			@RequestParam(name = "en", required = false) String en,
			@RequestParam(name = "hi", required = false) String hi) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
//		if (hi.equals("hi")&&en.equals("En")) {
//			List<News> news = newsRepository.findNewsByLang("Hindi");
//		} else if (en.equals("en")) {
//			List<News> news = newsRepository.findNewsByLang("English");
//		}

		List<News> news = new ArrayList<News>();

		if (en != null && hi == null) {
			news = newsRepository.findNewsByLang(en);
		} else if (en == null && hi != null) {
			news = newsRepository.findNewsByLang(hi);
		} else {
			news = newsRepository.findAllByOrderByIdDesc();
		}

//		else {
//			news = newsRepository.findAllByOrderByIdDesc();
//		}

		map.put("all", news.size());
		map.put("news", news);

		return map;
	}

	@RequestMapping(value = { "/news" })
	public @ResponseBody Map<String, Object> news(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<News> news = newsRepository.findAllByOrderByIdDesc();
		map.put("all", news.size());
		map.put("news", news);

		return map;

	}

//	@RequestMapping(value = { "/", "/welcome" })
	@RequestMapping(value = { "/getNews22" })
	public @ResponseBody Map<String, Object> getNews(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

//		String path = "C://Users//gulfa//Desktop//News.xlsx";
//		String path = "/opt/gul/FileSever/News.xlsx";

//		File file = ResourceUtils.getFile("classpath:News.xlsx");
//		String path ="/opt/gul/FileSever/News.xlsx";
//		String loc ="/opt/gul/FileSever/news.xml";
//		File file = new File(path);

//		File file = new File(path);
//		File f = new File(loc);
//		File f = ResourceUtils.getFile("classpath:News.xml");
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
		List<News> news = newsRepository.findAll();
		map.put("news", news);

		map.put("totalRecords", news.size());
		System.out.println("test.............................................");
		return map;
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
