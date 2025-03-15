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

public class WebsiteCrowlingBBChindi22 {
	public static void main(String[] args) throws IOException {

		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		int ii = 0;
//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect("https://www.bbc.com/hindi/india-64976620").get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-irdbz7 ebmt73l0")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

//						System.out.println("rrrrrr ");
						Elements sdsds = ee.children();

//						if (sdsds.className().contains("bbc-19j92fr ebmt73l0")) {

//							Elements sss = ee.getAllElements();
						for (Element ww : sdsds) {

							if (ww.className().contains("bbc-1151pbn ebmt73l0")) {

								System.out.println("  Head:   " + ww.text());
							}
							if (ww.className().contains("e1j2237y6 bbc-q4ibpr ebmt73l0")) {

								Elements sss = ww.children();
								for (Element sde : sss) {

									Elements ssss = sde.children();
									for (Element ab : ssss) {
										Elements bc = ab.children();

										for (Element cd : bc) {

											if (cd.className().contains("bbc-1a3w4ok euvj3t11")) {
												System.out.println("  name:   " + cd.text());
											}
											if (cd.className().contains("bbc-1p92jtu euvj3t10")) {
												System.out.println("  bbc:   " + cd.text());
												break;
											}

										}

									}

								}
							}

							if (ww.className().contains("bbc-1ka88fa ebmt73l0")) {
								Elements sss = ww.children();

								for (Element cd : sss) {

									if (cd.className().contains("bbc-1qdcvv9 e1aczekt0")) {
										Elements we = cd.children();

										for (Element se : we) {
											if (se.className().contains("bbc-172p16q ebmt73l0")) {
												Elements sd = se.children();
												if (ii == 0) {
													for (Element sssd : sd) {
														if (sssd.className().contains("bbc-189y18v ebmt73l0")) {
//														System.out.println(sssd);
															Elements ss6 = sssd.getElementsByTag("img");
															String src22 = ss6.attr("src");
															System.out.println(src22);
														}
														if (sssd.className().contains("bbc-3edg7g ebmt73l0")) {
															System.out.println(sssd.text());
														}
													}
													ii++;
												}
											}
										}
									}
								}
							}

							if (ww.className().contains("e1j2237y7 bbc-q4ibpr ebmt73l0")) {
								
								Elements mm = ww.children();
								for(Element elementee:mm) {
									if(elementee.className().contains("bbc-zyc2da e1mklfmt0")) {
										System.out.println(elementee.text());  //date
									}
								}
								
							}
							
							
							if (ww.className().contains("bbc-19j92fr ebmt73l0")) {
								Elements sss = ww.children();
								for (Element cd : sss) {
									if (cd.className().contains("bbc-kl1u1v e17g058b0")
											&& !cd.toString().contains("ीबीसी हिन्दी")) {
										System.out.println(" p " + cd.text());
										System.out.println(" ...............  ");
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
