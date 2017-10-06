package com.mattambs.alone.graphics;

public class Sprite {

	private int[] pixels;
	private final int SIZE;
	private int x, y;
	private SpriteSheet sheet;
	
	public static final Sprite blueTile  = new Sprite(0, 0, Screen.TILE_WIDTH, SpriteSheet.testSheet);

	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		this.SIZE = size;
		this.sheet = sheet;
		this.x = x * size;
		this.y = y * size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		int[] sheetPixels = sheet.getPixels();
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y*SIZE] = sheetPixels[x + this.x + (y + this.y) * sheet.getWidth()];
			}
		}
	}
	
	public int[] getPixels() { return pixels; }
}
