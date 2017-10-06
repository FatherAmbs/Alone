package com.mattambs.alone.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private int width, height;
	private BufferedImage image;
	private int[] pixels;
	
	public final static SpriteSheet testSheet = new SpriteSheet("/test.png");
	
	public SpriteSheet(String name) {
		try {
			image = ImageIO.read(SpriteSheet.class.getResource(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	
	public int[] getPixels() { return pixels; }
	
	public int getWidth() { return width; }
}	
	
