package andrei.sardaru.master.watermark;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		File alapcaImage = new File("alpaca.bmp");
		File alpacaText = new File("watermark.bmp");
		File xorWatermark = new File("white.bmp");
		
		try {
			BufferedImage image = ImageIO.read(alapcaImage);
			BufferedImage overlay = ImageIO.read(alpacaText);
			BufferedImage xorWatermarkImage = ImageIO.read(xorWatermark);
			
			/////////////////////////////////////////////////////////////////
			System.out.println(image);
			System.out.println(image.getHeight() + " " + image.getWidth());
			System.out.println(overlay);
			System.out.println(overlay.getHeight() + " " + overlay.getWidth());
			/////////////////////////////////////////////////////////////////
			
			BufferedImage firstStage = xorEffect(xorWatermarkImage, overlay);
			writeImage("firstStage", "bmp",firstStage);
			
			BufferedImage firstStage_T = transperancy(overlay, 0.5f);
			System.out.println(firstStage_T);
			writeImage("firstStage_T", "bmp",firstStage_T);
			
			BufferedImage secondStage = andEffect(firstStage, image);
			writeImage("secondStage", "bmp",secondStage);
			
			BufferedImage finalStage = orEffect(secondStage, overlay);
			writeImage("finalImage", "bmp",finalStage);
			
			/*System.out.println(overlay);
			BufferedImage test = transperancy(overlay, 0.5f);
			System.out.println(overlay);
			writeImage("test", "jpg", test);*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage xorEffect(BufferedImage imageA, BufferedImage imageB) {
	    if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight())
	        throw new IllegalArgumentException("Dimensions are not the same!");
	    
	    BufferedImage img = new BufferedImage(imageA.getWidth(), imageA.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

	    for (int y = 0; y < imageA.getHeight(); ++y) {
	        for (int x = 0; x < imageA.getWidth(); ++x) {
	           int pixelA = imageA.getRGB(x, y);
	           int pixelB = imageB.getRGB(x, y);
	           int pixelXOR = pixelA ^ pixelB;
	           img.setRGB(x, y, pixelXOR);
	        }
	    }
	    System.out.println(img);
	    return img;
	}
	
	public static BufferedImage andEffect(BufferedImage imageA, BufferedImage imageB) {
	    if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight())
	        throw new IllegalArgumentException("Dimensions are not the same!");
	    
	    BufferedImage img = new BufferedImage(imageA.getWidth(), imageA.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

	    for (int y = 0; y < imageA.getHeight(); ++y) {
	        for (int x = 0; x < imageA.getWidth(); ++x) {
	           int pixelA = imageA.getRGB(x, y);
	           int pixelB = imageB.getRGB(x, y);
	           int pixelAND = pixelB & pixelA;
	           img.setRGB(x, y, pixelAND);
	        }
	    }
	    System.out.println(img);
	    return img;
	}
	
	public static BufferedImage orEffect(BufferedImage imageA, BufferedImage imageB) {
	    if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight())
	        throw new IllegalArgumentException("Dimensions are not the same!");
	    
	    BufferedImage img = new BufferedImage(imageA.getWidth(), imageA.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

	    for (int y = 0; y < imageA.getHeight(); ++y) {
	        for (int x = 0; x < imageA.getWidth(); ++x) {
	           int pixelA = imageA.getRGB(x, y);
	           int pixelB = imageB.getRGB(x, y);
	           int pixelOR = pixelA | pixelB;
	           img.setRGB(x, y, pixelOR);
	        }
	    }
	    System.out.println(img);
	    return img;
	}
	
	public static BufferedImage transperancy(BufferedImage source, double alpha) {
		BufferedImage image_T = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		    Graphics2D graphs = image_T.createGraphics();
		    graphs.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
		    graphs.drawImage(source, null, 0, 0);
		    graphs.dispose();
		    return image_T;
	}

	public static void writeImage(String name, String extension, BufferedImage img) {
		String fullName = name + "." + extension;
		File outputfile = new File(fullName);
		try {
			ImageIO.write(img, extension, outputfile);
			System.out.println(name + " DONE!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
