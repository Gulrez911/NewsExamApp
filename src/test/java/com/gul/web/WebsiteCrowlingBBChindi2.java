package com.gul.web;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBBChindi2 {
	public static void main(String[] args) throws IOException {

		int ii = 0;
		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect("https://www.bbc.com/hindi/articles/cv2jx153z17o").get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("ebmt73l0")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

//						System.out.println("rrrrrr ");
						Elements sdsds = ee.children();

//						if (sdsds.className().contains("bbc-19j92fr ebmt73l0")) {

//							Elements sss = ee.getAllElements();
						for (Element ww : sdsds) {

							if (ww.className().contains("ebmt73l0")) {

								Elements ss1 = ww.children();

								Element s1 = ss1.get(0);
								Elements ss2 = s1.children();

								Element s2 = ss2.get(0);
								Elements ss3 = s2.children();
								Element s3 = ss3.get(0);
								Elements ss4 = s3.children();

								for (Element sde : ss4) {

									Elements ss6 = sde.getElementsByTag("img");
									String src22 = ss6.attr("src");
									System.out.println(src22);
								}

								Element s4 = ss4.get(0);
								Elements ss5 = s4.children();

//								Element s5=	ss5.get(0);
//								
//								Elements ss6 =	s5.getElementsByTag("img");
//								String src22 = ss6.attr("src");
//								System.out.println(src22);
//								System.out.println("11111111111111111111111111111111111111111111");
							} else {

								System.out.println(ww.text());
								System.out.println();
							}
 
						}

						if (ee.toString().contains("ये भी पढ़ें:-")) {
							System.out.println("found");
						}

						if (ee.className().contains("bbc-1151pbn ebmt73l0")) {

							System.out.println(ee.text());
						}
						if (ee.className().contains("evk1bvh2 bbc-q4ibpr ebmt73l0")) {
							System.out.println(ee.text());
						}
						if (ee.className().contains("bbc-19j92fr ebmt73l0")) {
							System.out.println(ee.text());
						}

//						bbc-6aqi2i

					}
				}
			}
		}

 
	}

 

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
