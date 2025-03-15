package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingABPlivePunjabi {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://punjabi.abplive.com/ajab-gajab").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("uk-container")) {
//				System.out.println("2222222222222");
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

														Elements ee3456703 = ee345670.children();
														for (Element ere54 : ee3456703) {
															Elements a = ere54.getElementsByTag("a");
															String href = a.attr("href");
															System.out.println(href);
															Elements ere5455 = ere54.children();
															for (Element ere542 : ere5455) {
																if (ere542.className().contains("uk-grid-collapse")) {
																	Elements img = ere542.getElementsByTag("img");
																	String src = img.attr("data-src");
																	if (src.contains("?impolicy")) {
																		src = src.substring(0, src.lastIndexOf("?impolicy"));
																		System.out.println(src);
																	}
																	 
																	System.out.println(ere542.text());
																	System.out.println();
																}
															}

//														}
														}
//														System.out.println("222222");
//														Elements a = ee3.getElementsByTag("a");
//														String href = a.attr("href");
//														System.out.println(href);
//														System.out.println();
													}
												}
												System.out.println("222222");
//								Elements a = ee3.getElementsByTag("a");
//								String href = a.attr("href");
//								System.out.println(href);

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
