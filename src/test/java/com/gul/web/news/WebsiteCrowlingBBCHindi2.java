package com.gul.web.news;

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

public class WebsiteCrowlingBBCHindi2 {
	public static void main(String[] args) throws IOException {

		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		int ii = 0;
		int flag = 0;
		String ss = "";
//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect("https://www.bbc.com/hindi/articles/cq521eyxqdzo").get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-fa0wmp")) {
					System.out.println("222222222222");
					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {
						if (ee.className().contains("ebmt73l0")) {
							Elements ee2 = ee.children();

							for (Element ee3 : ee2) {

								if (ee3.className().contains("e1p3vdyi0")) {
									Elements h1 = ee3.getElementsByTag("h1");
									String head = h1.text();
									System.out.println(head);
								}
								if (ee3.className().contains("e1aczekt0")) {
									Elements src2 = ee3.getElementsByTag("img");
									for (Element ww566 : src2) {
										if (flag == 0) {
											String src2f = ww566.attr("src");
											System.out.println(src2f);
											flag = 1;
										}

									}
								}

								if (ee3.className().contains("e17g058b0")) {
									Elements a = ee3.getElementsByTag("p");
									String sss = a.text();
//											System.out.println(sss);
									if (!sss.toString().contains("(बीबीसी न्यूज")
											&& !a.toString().contains("twitter")) {
										if (ss.equals("")) {
											ss += sss;
										} else {
											ss += "\n\n" + sss;
										}

									}

								}
							}
						}

					}

				}
			}

		}
		System.out.println(ss);
	}

}
