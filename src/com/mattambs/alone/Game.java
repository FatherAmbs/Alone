package com.mattambs.alone;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mattambs.alone.graphics.Screen;
import com.mattambs.alone.graphics.SpriteSheet;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 240;
	public static final int WIDTH = HEIGHT * 16 / 9;
	public static final int SCALE = 3;
	private static final String TITLE = "Alone";

	private int frames = 0;
	private int ticks = 0;
	private int xScroll = 0, yScroll = 0;
	private Thread thread;
	private Screen screen;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private boolean running = false;

	public Game() {
		this.thread = new Thread(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/test.png"));
	}

	public void start() {
		running = true;
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTickTime = System.nanoTime();
		long lastTime = System.currentTimeMillis();
		double unprocessedTime = 0;
		double nsPerTick = 1000000000.0 / 60;

		while (running) {
			long nowTickTime = System.nanoTime();
			long nowTime = System.currentTimeMillis();

			unprocessedTime = (nowTickTime - lastTickTime) / nsPerTick;
			if (unprocessedTime >= 1) {
				tick();
				ticks++;
				unprocessedTime = 0;
				lastTickTime = System.nanoTime();
			}

			frames++;
			render();

			if (nowTime - lastTime > 1000) {
				// System.out.println(frames + " fps, " + ticks + " ups");
				lastTime = nowTime;
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void tick() {
	}

	private void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		// screen.render(pixels, 0, WIDTH);
		screen.render(pixels, 0, 0);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		JFrame frame = new JFrame(Game.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}

}
