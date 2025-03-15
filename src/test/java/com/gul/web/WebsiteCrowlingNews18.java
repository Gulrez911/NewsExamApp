package com.gul.web;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNews18 {
	public static void main(String[] args) throws IOException {

		int ii = 0;
		String destinationFile = "C:/Users/gulfa/Desktop/test.txt";

		FileWriter fileWriter = new FileWriter(destinationFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

//		Document doc = Jsoup.connect("https://hindi.news18.com/news/entertainment/bollywood-tabu-sister-farah-naaz-threatened-to-beat-anil-kapoor-madhuri-dixit-on-film-rakhwaala-set-slapped-chunky-pandey-5505901.html").get();
		Document doc = Jsoup.connect(
				"https://hindi.news18.com/news/jobs/indian-army-agniveer-bharti-2023-application-last-date-extended-till-15-march-online-exam-from-17-april-5527769.html")
				.get();

		Elements elements = doc.getAllElements();

//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("article_section")) {
//					System.out.println("666666");
					System.out.println(element2.getElementsByTag("h1").text());

//					Elements divChildren2 = element2.children();

					String see = ".khbr_rght_sec";

					Elements links = element2.select(see);
					System.out.println(links.text());
//					System.out.println("Outer HTML Before Modification :" + links.text());

//				 Elements sss = element2.getElementsByTag("div");

//				 System.out.println(sss.size());
//					for (Element ddd : sss) {

//					System.out.println(ddd.text());
//					}
//					Elements divChildren2 = element2.children();

				}
			}
		}

// 				System.out.println(ii);

//			}
// 			}

//		}

//		printWriter.close();
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
