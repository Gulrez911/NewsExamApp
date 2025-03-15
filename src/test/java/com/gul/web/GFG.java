package com.gul.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class GFG {

	public static void main(String[] args) throws IllegalStateException {

		 try {
			URL  url = new URL("https://images.bhaskarassets.com/webp/thumb/256x0/web2images/521/2023/03/26/comp-13_1679811980.gif");
			BufferedImage image = ImageIO.read(url);
			ImageIO.write(image, "jpg",new File("C://Users//gulfa//Desktop//GIF//test.jpg"));
			ImageIO.write(image, "gif",new File("C://Users//gulfa//Desktop//GIF//test.gif"));
			ImageIO.write(image, "png",new File("C://Users//gulfa//Desktop//GIF//test.png"));
			ImageIO.write(image, "bmp",new File("C://Users//gulfa//Desktop//GIF//test.bmp"));
			System.out.println("Done..................");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
