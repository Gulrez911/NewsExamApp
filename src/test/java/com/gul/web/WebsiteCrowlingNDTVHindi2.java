package com.gul.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNDTVHindi2 {
	public static void main(String[] args) throws IOException {
		int flag = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		String ss2 = "";
		String url = "https://ndtv.in/bollywood/jaane-khuda-short-film-a-love-story-beyond-social-labels-faith-and-age-4006972";
		Document doc = Jsoup.connect(url).timeout(300000).get();

		Elements elements = doc.getAllElements();
		System.out.println(url);
		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
			Elements head = element.children();

//			section
			for (Element ddd2 : head) {

				if (ddd2.className().equals("sp-hd")) {

					

					Elements date = ddd2.children();

					for (Element ssrr : date) {
						if (ssrr.className().equals("sp-ttl-wrp")) {
							
							Elements heads2 = ssrr.children();


							for (Element ssrraa : heads2) {
								if (ssrraa.className().equals("sp-ttl")) {
									System.out.println(".................");
									System.out.println(ssrraa.text());
									System.out.println(".................");
								}
							}
							
//							System.out.println(ssrr.text());
						}
						if (ssrr.className().equals("pst-by")) {
							System.out.println(ssrr.text());
						}
					}

				}

//				

				if (ddd2.className().equals("row s-lmr")) {
					Elements heads = ddd2.children();

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
																if (ell19.className().equals("ins_instory_dv_cont")) {

//																	Elements ell18 = ell19.children();
//																	for (Element ell20 : ell18) {
//																		if (ell20.className().equals(" story_pics")) {
//																			Elements img = ell20
//																					.getElementsByTag("img");
//																			String img2 = img.attr("src");
//																			System.out.println(img2);
//																		}
//																	}

																	if (flag4 == 0) {
																		String img2 = "";
																		Elements img = ell19.getElementsByTag("img");
																		if (img.toString().contains("data-src=")) {

																			img2 = img.attr("data-src");
																		} else {
																			img2 = img.attr("src");
																		}
																		if (!img2.contains("data:image/")) {

																			System.out.println(img2);
																		}

																		flag4 = 1;
																	}

																}
															}

														}
//														ins_instory_dv

														String sss = ell12.toString();
														if (!sss.contains("ins_instory_dv")) {
															String sss2 = ell12.text();
															if (sss.length() > 5) {
																String ll = sss.substring(0, 5);

																if (ll.contains("<b ")) {

																	ss2 += sss2 + " ";
																	System.out.println(ss2);
																}
																if (ll.contains("<p")) {
																	ss2 += sss2;
																	System.out.println(ss2);
																}

															}
														}
														if (ell12.className().contains("twitter-tweet")) {
															continue;
														}
														Elements ell17 = ell12.children();

														for (Element ell19 : ell17) {

															if (ell19.className().contains("reltd-main")) {
																continue;
															}

															if ((ell19.toString().contains("<p")
																	|| ell19.toString().contains("<b"))

															) {

//																String ss = ell19.text();

																Elements ell21 = ell19.children();

																String sss2 = ell19.text();
																if (sss.length() > 5) {
																	String ll = sss.substring(0, 10);

																	if (ll.contains("<p")) {
																		if (!sss2.contains("भी पढ़ें")) {
																			if (ss2.equals("")) {
																				ss2 += sss2;
																			} else {
																				ss2 += "\n" + sss2;
																			}
																		}
																		continue;
																	}

																}

																for (Element ell22 : ell21) {
//																		||!ell22.toString().contains("<ul")
																	if (ell22.toString().contains("href")) {
																		continue;
																	}
//																	if (ell22.toString().contains("<strong>")) {
//																		continue;
//																	}
																	if (ell22.toString().contains("<h3>")) {
																		continue;
																	}
																	if (ell22.toString().contains("देखें Video:")) {
																		continue;
																	}
//																	if (ell22.toString().contains("(Disclaimer: ")) {
//																		continue;
//																	}
//																	if (ell22.toString().contains("<br>")) {
//																		continue;
//																	}
																	
																	String ss = ell22.text();

																	if (!ss.contains("हेडलाइन के अलावा")

																			&& !ell22.toString().contains("<div")
																			&& !ell22.toString().contains("<ul")) {

																		if (!ss.equals("")
																				&& !sss.contains("ins_instory_dv")) {

																			if (!ell19.className()
																					.contains("ft-social")) {
																				if (!ss.contains("भी पढ़ें")) {
																					if (ss2.equals("")) {
																						ss2 += ss;
																					} else {
																						ss2 += "\n\n" + ss;
																					}
																				}
																			}
																		}

//																			System.out.println(ss2);

																	}

																}

															}

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
//		

			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("......");
		System.out.println(ss2);
	}

}
