package com.gul.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctet.web.dto.NewsDto;

public class WebsiteCrowlingBhaskar {
	public static void main(String[] args) throws IOException {

		List<NewsDto> list = new ArrayList<>();
		String ss = "";
		String ss2 = "";
		String url = "https://www.bhaskar.com/national/";
		System.out.println(url);
//		String url = "https://www.tv9hindi.com/sports/cricket-news/pcb-chairman-najam-sethi-acc-members-support-excluding-india-to-host-asia-cup-2023-in-icc-meeting-au487-1778037.html";
		Document doc2 = Jsoup.connect(url).timeout(30000).get();

		Elements elements222 = doc2.getAllElements();
		System.out.println("/////////////////       " + elements222.size());

		for (int i = 0; i < elements222.size(); i++) {

			Element element = elements222.get(i);

			Elements divChildren = element.children();

			for (Element element2 : divChildren) {

				if (element2.className().contains("ba1e62a6")) {

					Elements divChildren2 = element2.children();

					for (Element ee : divChildren2) {

						Elements divChildrenss = ee.children();

						for (Element elementss : divChildrenss) {
//

							Elements ddd = elementss.children();
							NewsDto newsDto = new NewsDto();
							for (Element ssdds : ddd) {
//								efb7defa
								if (ssdds.className().contains("efb7defa")) {
									System.out.println("........");

									Elements ww = ssdds.children();

									for (Element rr : ww) {
										if (rr.className().contains("c7ff6507 db9a2680")) {
											Elements aa = rr.getElementsByTag("a");
											String href = "https://www.bhaskar.com" + aa.attr("href");
											System.out.println(href);
											newsDto.setUrl(url);

											for (Element tt : aa) {
												Elements dfddd = tt.children();

												for (Element aabb : dfddd) {
													if (aabb.className().contains("f3426d1d")
															|| aabb.className().contains("ad3ccf1a")) {
														String title = aabb.attr("title");
														System.out.println(title);
														newsDto.setMainheadline(title);
													}
													Elements fff = aabb.children();
													for (Element dd : fff) {
														Elements ffff = dd.children();

														for (Element ggg : ffff) {
															Elements jj = ggg.children();
															for (Element iijj : jj) {

																if (iijj.toString().contains("<img")) {

																	Elements aass = iijj.getElementsByTag("img");
																	String img = aass.attr("src");
																	System.out.println(img);
																	newsDto.setMainImage(img);
																	list.add(newsDto);
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

							for (Element ddrr : ddd) {

								if (ddrr.className().contains("c7ff6507 db9a2680")) {

									Elements sss = ddrr.children();

									for (Element sdsd : sss) {
										String classs = sdsd.attr("class");

										if (classs.equals("")) {
											Elements a = sdsd.getElementsByTag("a");

											String href = "https://www.bhaskar.com" + a.attr("href");
											System.out.println(href);
											newsDto.setUrl(href);
//											Elements dfddd = a.children();
											for (Element aa : a) {
												Elements dfddd = aa.children();
//												c62bd949
												for (Element aabb : dfddd) {

													if (aabb.className().contains("ad3ccf1a")) {
														String title = aabb.attr("title");
														System.out.println(title);
														newsDto.setMainheadline(title);
														list.add(newsDto);
													}
//													c62bd949

//													Elements fff = aabb.children();
//													for (Element dd : fff) {
//														Elements ffff = dd.children();
//
//														for (Element ggg : ffff) {
//															Elements jj = ggg.children();
//
//															for (Element iijj : jj) {
//
//																if (iijj.toString().contains("<img")
//																		|| aabb.toString().contains("<img")) {
//
//																	Elements aass = iijj.getElementsByTag("img");
//																	String img = aass.attr("src");
//																	System.out.println(img);
//																	newsDto.setMainImage(img);
//																	list.add(newsDto);
//																}
//
//															}
//														}
//
//													}

												}
//
											}

										}
//										System.out.println(sss.text());
									}

								}
//
							}
//
						}

					}
				}

			}

		}
		for (int ii = 0; ii < list.size(); ii++) {
			NewsDto newsDto = list.get(ii);
			System.out.println(ii + " : " + newsDto.getUrl()+" : "+newsDto.getMainheadline());
		}
		System.out.println(": " + list.size());
	}
}
