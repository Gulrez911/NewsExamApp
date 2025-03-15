package com.gul.web.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteCrowlingNDTVHindi2 {
	public static void main(String[] args) throws IOException {
		String ss = "";
		int flag = 0;
		String url = "https://ndtv.in/india/supreme-court-collegium-recommends-transfer-of-9-high-court-judges-4288532";
		Document doc = Jsoup.connect(url).timeout(300000).get();

		Elements elements = doc.getAllElements();
		System.out.println(url);
//		System.out.println(elements.size());
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
			Elements heads = element.children();

//			section
			for (Element ddd2 : heads) {

				if (ddd2.className().contains("sp-hd")) {

					Elements date = ddd2.children();

					for (Element ssrr : date) {
						if (ssrr.className().contains("sp-ttl-wrp")) {

							Elements heads2 = ssrr.children();

							for (Element ssrraa : heads2) {
								if (ssrraa.className().contains("sp-ttl")) {
									Elements h1 = ssrraa.getElementsByTag("h1");
									String head = h1.text();
									if (!head.equals("")) {
										System.out.println(head);
									}

								}
							}

//							System.out.println(ssrr.text());
						}

					}

				}

//				

				if (ddd2.className().contains("row s-lmr")) {
					Elements headsw = ddd2.children();

					for (Element ell : headsw) {
						Elements ss2dd = ell.getElementsByTag("article");
						for (Element ell2 : ss2dd) {
							Elements ell3 = ell2.children();

							for (Element ell4 : ell3) {
								if (ell4.className().contains("sp-wrp")) {
//									System.out.println("//");
									Elements ell5ff = ell4.children();

									for (Element ell5ff8 : ell5ff) {
										if (ell5ff8.className().contains("sp-hd")) {
											Elements ell5ff85 = ell5ff8.children();

											for (Element ell5ff858 : ell5ff85) {
												if (ell5ff858.className().contains("sp-ttl-wrp")) {
													Elements ell5ff8588 = ell5ff858.children();

													for (Element ell5ff85885 : ell5ff8588) {
														if (ell5ff85885.className().contains("ins_storybody")) {
															Elements ell5fdf85885d = ell5ff85885.children();

															for (Element ell5ff85z885 : ell5fdf85885d) {
																if (ell5ff85z885.className()
																		.contains("ins_instory_dv")) {
																	Elements img = ell5ff85z885.getElementsByTag("img");
																	String src = img.attr("data-src");
																	System.out.println(src);
																	System.out.println("///////");
																}
																if (ell5ff85z885.className().contains("place_cont")) {
																	Elements b = ell5ff85z885.getElementsByTag("b");
																	String loc = b.text();
																	if (!loc.equals("")) {
																		ss += loc + " ";
																		flag = 1;
																	}

																}
															}

															Elements p = ell5ff85885.getElementsByTag("p");
															for (Element pp : p) {
																Elements ppp = pp.getElementsByTag("p");
																String first = ppp.toString();
																if (first.length() > 8) {
																	first = first.substring(0, 8);
																	if (!first.contains("class")) {
																		for (Element p2 : ppp) {
																			String se = p2.text();
																			if (!se.equals("")) {

																				if (!p2.toString().contains("Tags")
																						&& !p2.toString()
																								.contains("फॉलो")
																						&& !p2.toString()
																								.contains("twitter")
																						&& !p2.toString()
																								.contains("पढ़ें")) {
																					if (ss.equals("")) {
																						ss += p2.text();

																					} else {
																						if (flag == 1) {
																							ss += p2.text().trim();
																							flag = 0;
																						} else {
																							ss += "\n\n"
																									+ p2.text().trim();
																						}

																					}

																				}
																			}
//																System.out.println(ss);

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
//									Elements ell5 = ell4.children();
//
//									for (Element ell6 : ell5) {
//										Elements ell7 = ell6.children();
//
//										for (Element ell8 : ell7) {
//
//											Elements ell9 = ell8.children();
//											for (Element ell10 : ell9) {
//
//												if (ell10.className().equals("sp-cn ins_storybody")) {
//													Elements ell11 = ell10.children();
//													if (flag3 == 0) {
//														for (Element ell12 : ell11) {
//
//															if (ell12.className().equals("ins_instory_dv")) {
//																Elements ell17 = ell12.children();
//
//																for (Element ell19 : ell17) {
//																	if (ell19.className()
//																			.equals("ins_instory_dv_cont")) {
//
////																	Elements ell18 = ell19.children();
////																	for (Element ell20 : ell18) {
////																		if (ell20.className().equals(" story_pics")) {
////																			Elements img = ell20
////																					.getElementsByTag("img");
////																			String img2 = img.attr("src");
////																			System.out.println(img2);
////																		}
////																	}
//
//																		if (flag4 == 0) {
//																			String img2 = "";
//																			Elements img = ell19
//																					.getElementsByTag("img");
//																			if (img.toString().contains("data-src=")) {
//
//																				img2 = img.attr("data-src");
//																			} else {
//																				img2 = img.attr("src");
//																			}
//																			if (!img2.contains("data:image/")) {
//
//																				System.out.println(img2);
//																			}
//
//																			flag4 = 1;
//																		}
//
//																	}
//																}
//
//															}
////														ins_instory_dv
//
//															String sss = ell12.toString();
//															if (!sss.contains("ins_instory_dv")) {
//																String sss2 = ell12.text();
//																if (sss.length() > 5) {
//																	String ll = sss.substring(0, 5);
//
//																	if (ll.contains("<b ")) {
//
//																		ss2 += sss2 + " ";
//																		System.out.println(ss2);
//																	}
//																	if (ll.contains("<p")) {
//																		ss2 += sss2;
//																		System.out.println(ss2);
//																	}
//
//																}
//															}
//															if (ell12.className().contains("twitter-tweet")) {
//																continue;
//															}
//															Elements ell17 = ell12.children();
//
//															for (Element ell19 : ell17) {
//
//																if (ell19.className().contains("reltd-main")) {
//																	continue;
//																}
//
//																if ((ell19.toString().contains("<p")
//																		|| ell19.toString().contains("<b"))
//
//																) {
//
////																String ss = ell19.text();
//
//																	Elements ell21 = ell19.children();
//
//																	String sss2 = ell19.text();
//																	if (sss.length() > 5) {
//																		String ll = sss.substring(0, 10);
//
//																		if (ll.contains("<p")) {
//																			if (!sss2.contains("भी पढ़ें")) {
//																				if (ss2.equals("")) {
//																					ss2 += sss2;
//																				} else {
//																					ss2 += "\n" + sss2;
//																				}
//																			}
//																			continue;
//																		}
//
//																	}
//
//																	for (Element ell22 : ell21) {
////																		||!ell22.toString().contains("<ul")
//																		if (ell22.toString().contains("href")) {
//																			continue;
//																		}
////																	if (ell22.toString().contains("<strong>")) {
////																		continue;
////																	}
//																		if (ell22.toString().contains("<h3>")) {
//																			continue;
//																		}
//																		if (ell22.toString().contains("देखें Video:")) {
//																			continue;
//																		}
////																	if (ell22.toString().contains("(Disclaimer: ")) {
////																		continue;
////																	}
////																	if (ell22.toString().contains("<br>")) {
////																		continue;
////																	}
//
//																		String ss = ell22.text();
//
//																		if (!ss.contains("हेडलाइन के अलावा")
//
//																				&& !ell22.toString().contains("<div")
//																				&& !ell22.toString().contains("<ul")) {
//
//																			if (!ss.equals("") && !sss
//																					.contains("ins_instory_dv")) {
//
//																				if (!ell19.className()
//																						.contains("ft-social")) {
//																					if (!ss.contains("भी पढ़ें")) {
//																						if (ss2.equals("")) {
//																							ss2 += ss;
//																						} else {
//																							ss2 += "\n\n" + ss;
//																						}
//																					}
//																				}
//																			}
//
////																			System.out.println(ss2);
//
//																		}
//
//																	}
//
//																}
//
//															}
//
//														}
//														flag3 = 1;
//													}
//												}
//											}
//
//										}
//
//									}
								}
							}
						}
					}
//		

				}
			}

		}
		System.out.println(ss);
	}

}
