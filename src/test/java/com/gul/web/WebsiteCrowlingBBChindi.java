package com.gul.web;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBBChindi {
	public static void main(String[] args) throws IOException {

		int ii = 0;
		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect("https://www.bbc.com/hindi/topics/cwr9j8g1kj9t?page=1").get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-1kz5jpr")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

//						System.out.println("rrrrrr ");

						if (ee.className().contains("bbc-t44f9r")) {

							Elements sss = ee.getAllElements();
							for (Element ww : sss) {

								if (ww.className().contains("e1v051r10")) {
									Elements ss = ww.getAllElements();
									for (Element weeeew : ss) {
										if (weeeew.className().contains("promo-image")) {
											
											Elements img = weeeew.getElementsByTag("img");
		 									
											String src2 = img.attr("src");
											System.out.println(src2);
//											Elements dddd = weeeew.getAllElements();
//											for (Element d : dddd) {
//
//												if (d.className().contains("e5q9uf21")) {
//													Elements sd = d.getAllElements();
//													for (Element dd : sd) {
//														if (dd.className().contains("bbc-6aqi2i")) {
//															Elements abc = dd.getAllElements();
//
//															for (Element aaa : abc) {
//
//																Elements abcd = aaa.children();
//
//																for (Element bbb : abcd) {
//																	Elements dsdsds = bbb.children();
//
//																	if (dsdsds.toString().contains("img")) {
//
//																		String src2 = dsdsds.attr("src");
//																		System.out.println(src2);
//																	}
//																}
////															if(er.className().contains("e5q9uf21")) {
////																
////															}
//															}
//
//														}
//													}
//												}
//											}
											
											
										}

										if (weeeew.className().contains("promo-text")) {
											Elements www = weeeew.getAllElements();
											for (Element ccc : www) {
												if (ccc.className().contains("e47bds20")) {

													Elements a = ccc.getElementsByTag("a");
													String url = a.attr("href");
													System.out.println(url);
													System.out.println(a.text());
													
												}
											}
										}

										
										if (weeeew.className().contains("e1mklfmt0")) {
											
											System.out.println(weeeew.text());
											System.out.println();
										}
										
										
									}
								}
							}
//							Elements sss = ee.getElementsByTag("img");
//							System.out.println(sss);
						}
//						bbc-6aqi2i

					}
				}
			}
		}

//					System.out.println("Outer HTML Before Modification :" + links.text());

//				 Elements sss = element2.getElementsByTag("div");

//				 System.out.println(sss.size());
//					for (Element ddd : sss) {

//					System.out.println(ddd.text());
//					}
//					Elements divChildren2 = element2.children();

	}

// 				System.out.println(ii);

//			}
// 			}

//		}

//		printWriter.close();

	public static void secondURl(String url) throws IOException {

		Document doc2 = Jsoup.connect(url).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());
//		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";
//	 
//
//		FileWriter fileWriter = new FileWriter(destinationFile);
//		PrintWriter printWriter = new PrintWriter(fileWriter);
//		 System.out.println(elements222.text());
//		printWriter.printf(elements222.text(), 1000);
//		printWriter.close();

		for (int j = 0; j < elements222.size(); j++) {
			Element ss = elements222.get(j);
			if (ss.className().equals("sp-hd")) {

				Elements ddd2222 = ss.children();

				for (Element sp : ddd2222) {

					if (sp.className().equals("sp-ttl")) {

						String hdd = sp.text();
						System.out.println(hdd);
						System.out.println("1111111111111");
					}
				}

			}
		}

	}

}
