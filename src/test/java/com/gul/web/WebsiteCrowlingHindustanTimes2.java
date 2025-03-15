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

public class WebsiteCrowlingHindustanTimes2 {
	public static void main(String[] args) throws IOException {
		String ss2 = "";
		Document doc = Jsoup.connect(
				"https://www.hindustantimes.com/india-news/cong-to-take-on-bjp-govt-over-graft-charge-in-karnataka-101680116823950.html")
				.get();

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
//								System.out.println("1");
								}
								if (dd3.className().contains("actionDiv flexElm topTime")) {

									Elements dd4 = dd3.children();
									for (Element dd5 : dd4) {
										if (dd5.className().contains("dateTime secTime storyPage")) {

											String mainDate = dd5.text();

											System.out.println(mainDate);
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
														}

//														if (dd9.toString().contains("<p>")
//																&& !dd9.className().contains("adMinHeight313")) {
//
//															String ss = dd9.text().trim();
//															if (ss2.equals("")) {
//																ss2 += ss;
//															} else {
//																ss2 += "\n\n" + ss;
//															}
//															System.out.println(">><<");
//															System.out.println(ss2);
//														}

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
		System.out.println("??????????????");
		System.out.println(ss2);
//		printWriter.close();
	}
}