package com.mattambs.alone.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Screen {
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private SpriteSheet sheet;

	private static final int MAP_WIDTH = 64; // must be 2^x
	private static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	public static final int TILE_WIDTH = 8; //
	private static final int TILE_WIDTH_MASK = TILE_WIDTH - 1;

	private int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];

	private final int width, height;

	private int xScroll, yScroll;

	private Random random = new Random();

	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		for (int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}

	}

	public void render(int[] pixels, int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yy = y + yOffset;
			;
			for (int x = 0; x < width; x++) {
				int xx = x + xOffset;
				int tileIndex = ((xx >> 4) & 7) + ((yy >> 4) & 7) * MAP_WIDTH;
				pixels[x + y * width] = Sprite.blueTile.getPixels()[(x & 7) + (y & 7) * 8];
			}
		}
	}

	public void clear(int[] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}
}