package com.gul.web.news;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingDivyaBhaskarGujarati {
	public static void main(String[] args) throws IOException {
		File file = new File("C:/Users/gulfa/Desktop/demo2.txt");

		Document doc = Jsoup.connect("https://www.divyabhaskar.co.in/international/").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("f0e619a2")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					Elements ee2 = ee.children();
					for (Element ee3 : ee2) {
						if (ee3.className().equals("ba1e62a6")) {
							System.out.println("2222222222222");
							Elements ul = ee3.children();

							for (Element eerr : ul) {
//								PrintWriter out = new PrintWriter(file); // Step 2

								// Write the name of four oceans to the file
//						        out.println(eerr);   // Step 3

//						        out.close();  // Step 4

								Elements erer = eerr.children();

								for (Element erer2 : erer) {
									Elements erer3 = erer2.children();
									for (Element erer32 : erer3) {

										if (erer32.className().equals("efb7defa")) {
											Elements erer324 = erer32.children();
											for (Element erer3245 : erer324) {
												if (erer3245.className().equals("c7ff6507 db9a2680")) {

													Elements a = erer3245.getElementsByTag("a");
													String url = "https://www.divyabhaskar.co.in" + a.attr("href");
													System.out.println(url);

													Elements img = erer3245.getElementsByTag("img");
													String src = img.attr("src");
													System.out.println(src);

													Elements h3 = erer3245.getElementsByTag("h3");

													String head = h3.text();
													System.out.println(head);
													System.out.println();
												}
											}

										}

										if (erer32.className().equals("c7ff6507 db9a2680")) {

											Elements a = erer32.getElementsByTag("a");
											String url = "https://www.divyabhaskar.co.in" + a.attr("href");
											System.out.println(url);

											Elements img = erer32.getElementsByTag("img");
											String src = img.attr("src");
											System.out.println(src);

											Elements h3 = erer32.getElementsByTag("h3");

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

//			if (element.className().equals("ba1e62a6")) {
//				System.out.println("2222222222222");
//				Elements ul = element.getElementsByTag("ul");
//				
//				for (Element ee : ul) {
//				 
//
//						Elements sss = ee.getAllElements();
//						for (Element ww : sss) {
//
//							if (ww.className().contains("efb7defa")) {
//								Elements ss = ww.getAllElements();
//								for (Element weeeew : ss) {
//									if (weeeew.className().contains("c7ff6507")) {
//										Elements a = weeeew.getElementsByTag("a");
//										String url = a.attr("href");
//										System.out.println(url);
//										System.out.println(a.text());
//										System.out.println();
//									 
//									}
//
//
//									
//									
//									
//								}
//							}
//					
// 
//					}
//				}
//				
//			}

		}

	}

}
