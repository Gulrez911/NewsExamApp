package com.gul.web;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowling2 {
	public static void main(String[] args) throws IOException {
//		Document doc = Jsoup
//				.connect("https://www.sawaal.com/general-knowledge/biology-questions-and-answers.htm?page=1&sort=")
//				.get();

//		Document doc = Jsoup.connect("https://unsplash.com/collections/8439505/new-year").get();
		Document doc = Jsoup.connect("https://wallpapers.com/wallpapers/girl-anime-blue-flames-mtqsd5bc6uhg0p5s.html").get();

		Elements elements = doc.getAllElements();
//		int count = 0;
//		String myStr3 = "Q:";
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("img-unit")) {
//				System.out.println(".............");
//				Elements imageElement = element.getElementsByTag("img");
////				 String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
//				 String srcValue = imageElement.attr("src");
//				System.out.println(srcValue);
//				System.out.println(".........");
				Elements gggg = element.getElementsByTag("a");
				for (Element ddd : gggg) {

					String xxxx = ddd.attr("href");
//					System.out.println(xxxx); 
					System.out.println(xxxx);
				}
			}

//			if (element.className().equals("photo-card")) {
//			System.out.println(".............");
//				if (element.className().equals("omfF5")) {
//				Elements imageElement = element.getElementsByTag("figure");

//				if(element.className().equals("photo-card")) {
//					 System.out.println("?????????? ");
//				}
//				
//				Elements imageElement = element.getElementsByTag("div");
//				for (Element ele: imageElement) {

			
//			String sss = gggg.attr("href");
//			System.out.println("/////// " + sss);
//					 System.out.println("/////// ");
//				}
//					Elements imageElement = element.getElementsByTag("figure");
//				System.out.println(imageElement.size());
//				for (int j = 0; j < imageElement.size(); j++) {
//					if(element.className().equals("zmDAx"))
//						System.out.println("//////////");
//					}
//					String URLStr = imageElement.select("a").attr("href");
//					 String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
//					Elements absoluteUrl = element.getElementsByTag("srcset");
//					String srcValue = imageElement.attr("href");
//					System.out.println(imageElement.size());
//					System.out.println(URLStr);
//					if()
//				}

//				}

//			}
		}

	}
}
