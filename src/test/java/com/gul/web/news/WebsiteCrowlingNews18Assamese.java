package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18Assamese {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://assam.news18.com/assam/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("jsx-b029de3d7ec3b8c7")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("jsx-b029de3d7ec3b8c7 newctgrstorylist2")) {
						System.out.println("3333");
						Elements ee22 = ee.children();
						for (Element ee55 : ee22) {
							if (ee55.className().contains("jsx-6e2b7e1d578b138c")) {
								Elements a = ee55.getElementsByTag("a");

								String href = "https://assam.news18.com" + a.attr("href");
								System.out.println(href);
								Elements ee556s6 = ee55.children();
								for (Element ee552 : ee556s6) {
									if (ee552.className().contains("jsx-6e2b7e1d578b138c")) {
										Elements img = ee552.getElementsByTag("img");
										for (Element img2 : img) {
											if(img2.toString().contains("https://images.news18.com/assam/")) {
												String src = img2.attr("src");
												if (src.contains("?impolicy")) {
													src = src.substring(0, src.lastIndexOf("?impolicy"));
													 System.out.println(src);
												} 
											}
											
										}
									

									}
									if (ee552.className().contains("jsx-6e2b7e1d578b138c")) {
										Elements h3 = ee552.getElementsByTag("h3");

										String head = h3.text();
										System.out.println(head);
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
