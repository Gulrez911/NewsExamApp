package com.ctet.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class ExcelBuilder extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=myReport.pdf");

		File imageFile = (File) model.get("imageFile");

//		File imageFile = new File("C:\\Users\\gulfa\\OneDrive\\Desktop\\WhatsApp Image 2022-04-03 at 12.17.43 PM.jpeg");
		BufferedImage readImage = null;
		int h = 0;
		int w = 0;
		try {
			readImage = ImageIO.read(imageFile);
			h = readImage.getHeight();
			w = readImage.getWidth();

//			check
			ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			ImageReader reader = readers.next();
			System.out.println("Format : " + reader.getFormatName());
//            System.out.println("Format : " + reader.getFormatName());
			System.out.println("Width : " + h + " pixels");
			System.out.println("Height : " + w + " pixels");
		} catch (Exception e) {
			readImage = null;
		}

		float a = w;
		float b = h;

		PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		PageSize pageSize = new PageSize(a, b);

		try (Document document = new Document(pdfDocument, pageSize)) {
			document.setLeftMargin(30);
			document.setRightMargin(30);
			document.setBottomMargin(30);
			document.setTopMargin(30);
			try {
				ImageData data = ImageDataFactory.create(imageFile.toString());
				com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(data);
				document.add(img);
				document.close();
				System.out.println("Done");
			} catch (MalformedURLException e) {
				logger.info("Unable to add image to the pdf. Error: " + e.toString());
			}
		}

	}

}
