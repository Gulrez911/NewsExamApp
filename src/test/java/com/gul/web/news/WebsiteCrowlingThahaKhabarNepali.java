package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingThahaKhabarNepali {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://thahakhabar.com/category/economy/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("category-collage")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					Elements ee2 = ee.children();
					for (Element ee3 : ee2) {

						if (ee3.className().contains("border-radius-box border-box mb-30 border-category-grey")) {

							Elements ee255 = ee3.children();
							for (Element ee25566 : ee255) {
								if (ee25566.className().contains("row ml-0 mr-0")) {
									Elements ee2554 = ee25566.children();
									for (Element ee255665 : ee2554) {
										if (ee255665.className().contains(
												"col-lg-6 col-md-6 hot-post-left img-full-height d-none d-md-block pl-0 pr-0")) {

											Elements a = ee255665.getElementsByTag("a");

											String href = "https://thahakhabar.com" + a.attr("href");
											System.out.println(href);

											for (Element aa : a) {
												Elements aa2 = aa.children();
												for (Element aa3 : aa2) {
													if (aa3.className().contains("post-bground-img-hover")) {

														String style = aa3.attr("style");

														if (style.contains(".jpg")) {
															int sepPos = style.indexOf("https://");
															style = style.substring(sepPos, style.lastIndexOf(".jpg"))
																	+ ".jpg";

														} else if (style.contains(".JPG")) {
															int sepPos = style.indexOf("https://");
															style = style.substring(sepPos, style.lastIndexOf(".JPG"))
																	+ ".JPG";
														}

														System.out.println(style);
//														System.out.println();
													}
												}

											}

										}

										if (ee255665.className().contains("col-lg-6 col-md-6 pl-0 pr-0")) {
											Elements ee2556653 = ee255665.children();
											for (Element ee2556643 : ee2556653) {
												if (ee2556643.className().contains("post-body p-30 m-0")) {
													Elements ee2535 = ee2556643.children();
													for (Element ee255636 : ee2535) {
														if (ee255636.className().contains("mb-15")) {
															Elements h3 = ee255636.getElementsByTag("h3");
															String head = h3.text();
															System.out.println(head);
														}
														if (ee255636.className().contains("post-meta mb-10")) {
															Elements aa2 = ee255636.children();
															for (Element aa24 : aa2) {
																if (!aa24.toString().contains("href")) {
																	String date = aa24.text();
																	System.out.println(date);
																	System.out.println();
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
