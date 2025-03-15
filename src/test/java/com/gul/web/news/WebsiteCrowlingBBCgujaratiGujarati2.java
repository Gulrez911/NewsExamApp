package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingBBCgujaratiGujarati2 {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://www.bbc.com/gujarati/india-48450105").get();

		Elements elements = doc.getAllElements();
		int flag = 0;
		String ss = "";
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("bbc-fa0wmp")) {

					Elements divChildren2 = element2.children();

					for (Element ww : divChildren2) {

						if (ww.className().contains("bbc-1151pbn ebmt73l0")) {
							Elements a = ww.getElementsByTag("h1");
							System.out.println(a.text());

						}
						if (ww.className().contains("bbc-1ka88fa ebmt73l0")) {
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

						if (ww.className().contains("bbc-19j92fr ebmt73l0")) {
							Elements a = ww.getElementsByTag("p");
							String sss = a.text();
//							System.out.println(sss);
							if (!sss.toString().contains("(बीबीसी न्यूज")&&!a.toString().contains("twitter")) {
								if (ss.equals("")) {
									ss += sss;
								} else {
									ss +=  "\n\n"+sss;
								}

							}

						}

					}
				}
			}
		}
		System.out.println(ss.trim());
	}

}
