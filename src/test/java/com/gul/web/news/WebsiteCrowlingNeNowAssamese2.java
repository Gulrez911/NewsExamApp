package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNeNowAssamese2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		Document doc = Jsoup.connect("https://assam.nenow.in/married-indian-woman-marries-facebook-friend-in-pakistan/")
				.get();

		Elements elements = doc.getAllElements();

		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {
//			count++;
//			System.out.println("...........  "+count);
			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );

			if (element.className().contains("jeg_inner_content")) {
				System.out.println("2222222222222");
				Elements divChildren2 = element.children();

				for (Element ee : divChildren2) {
					if (ee.className().contains("entry-header")) {
						Elements ee2 = ee.children();

						for (Element ee3 : ee2) {
							if (ee3.className().contains("jeg_post_title")) {
								Elements h1 = ee3.getElementsByTag("h1");

								String head = h1.text();
								System.out.println(head);
							}
							if (ee3.className().contains("jeg_meta_container")) {
								Elements ee23 = ee3.children();

								for (Element ee34 : ee23) {
									if (ee34.className().contains("jeg_post_meta jeg_post_meta_1")) {
										Elements ee344 = ee34.children();

										for (Element ee3446 : ee344) {
											if (ee3446.className().contains("meta_left")) {
												Elements ee3444 = ee3446.children();

												for (Element ee34436 : ee3444) {
													if (ee34436.className().contains("jeg_meta_date")) {
														Elements a = ee34436.getElementsByTag("a");

														String date = a.text();

														System.out.println(date);

													}
												}
											}
										}

									}
								}
							}
						}

					}

					if (ee.className().contains("jeg_featured featured_image")) {
						Elements img = ee.getElementsByTag("img");

						String src = img.attr("src");
						if (src.contains("?resize=")) {
							src = src.substring(0, src.lastIndexOf("?resize="));

						}
						System.out.println(src);
					}

					if (ee.className().contains("entry-content no-share")) {
						Elements img = ee.children();
						for (Element img2 : img) {
							if (img2.className().contains("content-inner")) {
								Elements h4 = img2.getElementsByTag("h4");
								if (!h4.toString().equals("")) {
									String se = h4.text();
									if (!se.equals("")) {

										if (!h4.toString().contains("লগতে পঢ়ক:")
												&& !h4.toString().contains("ಮತ್ತಷ್ಟು ರಾಷ್ಟ್ರೀಯ")
												&& !h4.toString().contains("twitter")) {
											if (ss.equals("")) {
												ss += h4.text();

											} else {
												ss += "\n\n" + h4.text();
											}

										}
									}
								}
								Elements p = img2.getElementsByTag("p");
								for (Element p2 : p) {
									String se = p2.text();
									if (!se.equals("")) {

										if (!p2.toString().contains("লগতে পঢ়ক:")
												&& !p2.toString().contains("ಮತ್ತಷ್ಟು ರಾಷ್ಟ್ರೀಯ")
												&& !p2.toString().contains("twitter")) {
											if (ss.equals("")) {
												ss += p2.text();

											} else {
												ss += "\n\n" + p2.text();
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
		System.out.println(ss);

	}

}
