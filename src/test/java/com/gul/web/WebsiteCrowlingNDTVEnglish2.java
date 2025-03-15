package com.gul.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNDTVEnglish2 {
	public static void main(String[] args) throws IOException {
		int flag = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		String ss2 = "";
		String url = "https://www.ndtv.com/india-news/man-held-for-blackmailing-sexually-harassing-girls-on-instagram-3894633";
		Document doc = Jsoup.connect(url).timeout(300000).get();

		Elements elements = doc.getAllElements();
		System.out.println(url);
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
//			System.out.println("  ..........   "+element );
			Elements section = element.getElementsByTag("section");
//			section
			for (Element ddd2 : section) {

				Elements h1 = ddd2.children();
				for (Element el : h1) {
					if (el.className().equals("sp-hd")) {

						Elements head = el.children();

						for (Element ell : head) {

							if (flag2 == 0) {
								if (ell.className().equals("sp-ttl-wrp")) {
									Elements ss2dd = ell.getElementsByTag("h1");
//								String href = ss2dd.attr("href");
									System.out.println(ss2dd.text());
									flag2 = 1;
								}
							}
							if (flag == 0) {

								if (ell.className().equals("pst-by")) {
									String date = el.text();
									
									if (date.contains("Updated: ")&&date.contains("IST")) {
										String tt = "Updated: ";
										date = date.substring(date.lastIndexOf("Updated: ") + tt.length(), date.lastIndexOf("IST")).trim();
//										date = date.substring(date.lastIndexOf(" ") , date.length()).trim();
										System.out.println(date);
										System.out.println();
									}
									
//									System.out.println(ell.text());
									flag = 1;
								}
							}

						}

					}

					if (el.className().equals("row s-lmr")) {
						Elements heads = el.children();

						for (Element ell : heads) {
							Elements ss2dd = ell.getElementsByTag("article");
							for (Element ell2 : ss2dd) {
								Elements ell3 = ell2.children();
								for (Element ell4 : ell3) {
									Elements ell5 = ell4.children();
									for (Element ell6 : ell5) {
										Elements ell7 = ell6.children();

										for (Element ell8 : ell7) {

											Elements ell9 = ell8.children();
											for (Element ell10 : ell9) {

												if (ell10.className().equals("sp-cn ins_storybody")) {
													Elements ell11 = ell10.children();
													if (flag3 == 0) {
														for (Element ell12 : ell11) {

															if (ell12.className().equals("ins_instory_dv")) {
																Elements ell17 = ell12.children();

																for (Element ell19 : ell17) {
																	if (ell19.className()
																			.equals("ins_instory_dv_cont")) {
																		if (flag4 == 0) {

																			Elements img = ell19
																					.getElementsByTag("img");
																			String img2 = img.attr("src");
																			if (!img2
																					.contains("data:image/svg+xml;b")) {

																				System.out.println(img2);
																			}

																			flag4 = 1;
																		}
																	}
																}

															}

															if ((ell12.toString().contains("<p")
																	|| ell12.toString().contains("<b"))
																	&& !ell12.className().equals("ins_instory_dv")
																	&& !ell12.toString()
																			.contains("(Except for the headline")) {

																if (ss2.equals("")) {
																	ss2 += ell12.text();
																} else {
																	if (!ell12.text().equals("")) {
																		ss2 += "\n\n" + ell12.text();
																	}

																}
//																ss2 += ell12.text();
//																System.out.println(ss2);
//																System.out.println("...................");
															}

														}
														flag3 = 1;
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

			}
		}

		System.out.println("..........");
		System.out.println(ss2.trim());
	}

}
