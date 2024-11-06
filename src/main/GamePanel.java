package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize = 16;
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale;
	final int maxScreenColumn = 16;
	final int maxScreenRow = 12;
	
	int screenWidth = tileSize * maxScreenColumn;
	int screenHeight = tileSize * maxScreenRow;
	
	int FPS = 60; 
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	//Default Position of player
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			
			
			try {
				
				double remainingTime = 	nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
	
	}
	
	public void update() {
		if(keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
}