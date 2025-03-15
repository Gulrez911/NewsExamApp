package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.data.News;
import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingIndianExpress2 {
	public static void main(String[] args) throws IOException {
		String ss2 = "";
		List<NewsDto> list = new ArrayList<>();
		Document doc = Jsoup.connect(
				"https://indianexpress.com/article/education/haryana-board-exams-2023-bseh-organising-special-exams-for-students-who-missed-class-10th-12th-paper-bseh-org-in-8530452/")
				.get();

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
																			}
																		}
																	}

																	if (dd13.className().contains("custom-caption")) {
																		Elements img2 = dd13.getElementsByTag("img");
																		String img = img2.attr("src");

																		System.out.println(img);
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
																						} else {
																							ss2 += "\n\n" + sss2;
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
																						String ll = sss3.substring(0, 5);

																						if (ll.contains("<p")) {
																						 
																								if (ss2.equals("")) {
																									ss2 += sss4;
																								} else {
																									ss2 += "\n\n" + sss4;
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
		System.out.println("..............................");
		System.out.println(ss2);
//		printWriter.close();
	}
}