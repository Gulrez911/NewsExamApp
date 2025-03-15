package com.gul.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CheckURL {

	public static void main(String[] args) throws Exception {
		String url1 = "https://wallpapers.com/images/file/jdm-aesthetic-anime-artwork-zkq3bnl8mpkr74ug.jpg";

		try {
			URL url = new URL("https://wallpapers.com/images/file/jdm-aesthetic-anime-artwork-zkq3bnl8mpkr74ug.jpg");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {// status 200
				Scanner s = new Scanner(url.openStream());
				while (s.hasNextLine()) {
					System.out.println(s.nextLine());
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
}
