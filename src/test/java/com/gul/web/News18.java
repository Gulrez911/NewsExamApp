package com.gul.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class News18 {

	public static void main(String[] args) {
//		WebDriver driver = new ChromeDriver();
//
//		driver.get("https://www.ndtv.com/cities/page-1");
//
//		driver.quit();

		// declaration and instantiation of objects/variables
		System.setProperty("webdriver.chrome.driver", "H://Software//Chrome_Driver//chromedriver.exe");
//		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		ChromeDriver driver = new ChromeDriver(options);
		// comment the above 2 lines and uncomment below 2 lines to use Chrome
		// System.setProperty("webdriver.chrome.driver","G:\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();

//		String baseUrl = "https://www.ndtv.com/india";
		String baseUrl = "https://hindi.news18.com/news/entertainment/bollywood-neena-gupta-shares-emotional-video-on-satish-kaushik-death-says-i-am-always-with-his-wife-and-daughter-5506223.html";
//		  String baseUrl = "http://demo.guru99.com/test/newtours/";
		String expectedTitle = "Welcome: Mercury Tours";
		String actualTitle = "";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		// get the actual value of the title
//		actualTitle = driver.getTitle();

//		WebElement n = driver.findElement(By.className("lisingNews"));

//		/html/body/div[2]/div/div/section/div[3]/article/div/div/div/div[1]/div/div[1]/div[2]/h2/a

//		String sss = "/html/body/div[2]/div/div/section/div[3]/article/div/div/div/div[1]/div/div[1]/div[2]/h2/a";
		
		
		
		
		
		
		String sss = "//*[@id=\"article_5506223\"]/div/div[1]/div[1]/div[3]/div[3]";
		String nsss = "";
		nsss = driver.findElement(By.xpath(sss)).getText();
		System.out.println(nsss);

//		String sss = "/html/body/div[2]/div/div/section/div[3]/article/div/div/div/div[1]/div[1]/div[1]/img";

//		WebElement lsss = driver.findElement(By.xpath("//*[@id=\"story_image_main\"]"));

//		nsss2 = driver.findElement(By.cssSelector(sss2)).getText(); 
//		System.out.println(lsss.getAttribute("src"));

		/*
		 * compare the actual title of the page with the expected one and print the
		 * result as "Passed" or "Failed"
		 */
		if (actualTitle.contentEquals(expectedTitle)) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed");
		}

		// close Fire fox
		driver.close();

	}

}
