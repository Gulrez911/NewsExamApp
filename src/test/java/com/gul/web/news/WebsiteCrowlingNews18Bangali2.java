package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Bangali2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		try {

			Document doc = Jsoup.connect(
					"https://bengali.news18.com/short-videos/life-style/continental-food-in-reasonable-price-pbd-1223454.html")
					.get();

			Elements elements = doc.getAllElements();

			System.out.println(elements.size());
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

														} else {
															ss += "\n\n" + p2.text();
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
		} catch (Exception e) {
			System.out.println(".........");
			// TODO: handle exception
		}

		System.out.println(ss);

	}

}
