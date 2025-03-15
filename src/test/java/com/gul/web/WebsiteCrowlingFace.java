package com.gul.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingFace {
	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("https://unsplash.com/s/photos/faces").get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().equals("mItv1")) {
//					if (element.className().equals("ripi6")) {

				Elements gggg = element.getElementsByTag("figure");

				System.out.println(">>>>   " + gggg.size());
				for (Element ddd : gggg) {

					Elements ss = ddd.getElementsByTag("a");
					String xxxx = ss.attr("href");
					System.out.println(xxxx);

//					if (xxxx.contains(".html")) {
//
//						System.out.println(xxxx);
					secondURl(xxxx);
//
//					}
//						}

				}
			}
		}
	}

	public static void secondURl(String url) throws IOException {

		if (url.contains("/photos/")) {
			url = "https://unsplash.com"+url;
			Document doc2 = Jsoup.connect(url).get();

			Elements elements222 = doc2.getAllElements();
			for (int j = 0; j < elements222.size(); j++) {
				Element ssssssss = elements222.get(j);
//			System.out.println("/////////////////");
				if (ssssssss.className().equals("MorZF")) {
					System.out.println("1111111");

					Elements jj = ssssssss.getElementsByTag("img");

					String d = jj.attr("src");

					System.out.println(d);
					
					Random rand = new Random();
					  
			        // Generate random integers in range 0 to 999
			        int rand_int1 = rand.nextInt(10000000);
 			        
			        String imageName = rand_int1+".JPEG";
			        String destinationFile = "H://Downloaded Images/face/" + imageName;
					saveImage(d,destinationFile);
				}
			}

		}
	}
	
	
public static void url(String url2) throws IOException {
		
		try {
			URL url = new URL(url2);

			
						
			
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {// status 200
				Scanner s = new Scanner(url.openStream());
				while (s.hasNextLine()) {
					System.out.println(s.nextLine());
					
					
//					String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
//					String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
						String sss = "https://wallpapers.com/images/file/";
						int length = sss.length();
						System.out.println(url2.substring(length));
						String destinationFile = "H://Downloaded Images/" + url2.substring(length);

						saveImage(url2, destinationFile);
					
					
					
//					s.nextLine();
					break;
				}
			} else if (code == HttpURLConnection.HTTP_NOT_FOUND) {// status 404

				// TODO: url not found
			} else {
				// TODO: other reponse status
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

}
