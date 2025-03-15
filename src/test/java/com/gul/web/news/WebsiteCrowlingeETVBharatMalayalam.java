package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingeETVBharatMalayalam {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.etvbharat.com/malayalam/kerala").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("md:w-8/12 h-full px-2 md:flex md:flex-wrap")) {
				System.out.println("2222222222222");
//				Elements divChildren2 = element.getElementsByTag("a");
				Elements divChildren2 = element.children();
				for (Element ee : divChildren2) {
					if (ee.className().contains("fresnel-container fresnel-greaterThan-xs w-full flex space-x-2")) {
						Elements ee2 = ee.children();
						for (Element ee3 : ee2) {

							if (ee3.className().contains("flex w-full justify-between")) {

								Elements ee255 = ee3.children();
								for (Element ee25566 : ee255) {
									if (ee25566.className().contains(
											"flex  flex-col pb-1 cursor-pointer rounded-md shadow  w-1/3 bg-white")) {
										
										Elements a = ee25566.getElementsByTag("a");

										String href ="https://www.etvbharat.com"+ a.attr("href");
										System.out.println(href);
										
										Elements ee4 = ee25566.children();
										for (Element ee5 : ee4) {
											if (ee5.className().contains("relative")) {

												Elements ee6 = ee5.children();
												for (Element ee7 : ee6) {
													if (ee7.className().contains("rounded-t-md w-full")) {
														Elements img = ee7.getElementsByTag("img");
														String src = img.attr("src");

														System.out.println(src);
													}

												}

											}
											if (ee5.className()
													.contains("h-12 my-2 pl-2 pr-1 text-gray-700 leading-tight")) {

												String head = ee5.text();

												System.out.println(head);
												System.out.println();
											}

										}

									}
								}
							}

						}

					}
					if (ee.className().contains(
							"flex  justify-between px-1 pt-1 pb-1 cursor-pointer border shadow  rectangle-card bg-white mt-1 md:mt-2 md:w-1/2 rounded-md")) {

						Elements a = ee.getElementsByTag("a");

						String href ="https://www.etvbharat.com"+ a.attr("href");
						System.out.println(href);
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
						System.out.println();
					}

				}
			}

		}
	}
}
