package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Malayalam2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
//		int flag = 0;
		Document doc = Jsoup.connect(
				"https://malayalam.news18.com/news/career/want-to-be-a-primary-school-teacher-study-diploma-in-elementary-education-ak-613474.html")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
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
														if (ss.equals("")&&ss.isEmpty()) {
															ss += p2.text();

														} else {
															ss += "\n\n" + p2.text();
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
		if(!ss.equals("")) {
			
			System.out.println(ss);
		}
	}

}
