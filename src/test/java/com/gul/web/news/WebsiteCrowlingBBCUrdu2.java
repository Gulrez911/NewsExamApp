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

public class WebsiteCrowlingBBCUrdu2 {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.bbc.com/urdu/articles/clwdz287v89o").get();

		Elements elements = doc.getAllElements();
		int flag = 0;
		String ss = "";
//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
//			fileWriter.write(element+System.getProperty( "line.separator" )+System.getProperty( "line.separator" ));
//			if (element.className().equals("tbl-forkorts-article-active")) {

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-fa0wmp")) {

					Elements divChildren2 = element2.children();

					for (Element ww : divChildren2) {

						if (ww.className().contains("bbc-1pfktyq ebmt73l0")) {
							Elements a = ww.getElementsByTag("h1");
							System.out.println(a.text());

						}
						if (ww.className().contains("bbc-1pt3yso ebmt73l0")) {
							Elements ww44 = ww.children();

							for (Element ww4 : ww44) {
								if (ww4.className().contains("bbc-1qdcvv9 e1aczekt0")) {
									Elements ww55 = ww4.children();
									for (Element ww5 : ww55) {
										if (ww5.className().contains("bbc-172p16q ebmt73l0")) {
											Elements src2 = ww5.getElementsByTag("img");
											for (Element ww566 : src2) {
												if (flag == 0) {
													String src2f = ww566.attr("src");
													System.out.println(src2f);
													flag = 1;
												}

											}

										}

									}
								}
							}

						}

						if (ww.className().contains("bbc-1vjaf6b")) {
							Elements ww44 = ww.children();

							for (Element ww4 : ww44) {
								if (ww4.className().contains("bbc-1rvtlej")) {
									Elements ww445 = ww4.children();

									for (Element ww47 : ww445) {
										if (ww47.className().contains("bbc-v8pmqw")) {
											System.out.println(ww47.text());
 										}
									}
								}
							}
						}

						if (ww.className().contains("bbc-4wucq3 ebmt73l0")) {
							Elements a = ww.getElementsByTag("p");
							System.out.println(a.text());
							String sss = a.text();
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
