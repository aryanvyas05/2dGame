package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(22, 16, 8, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		
		setDefaultValues();					
		getPlayerImage();
	}
	
	public void getPlayerImage() {
		try {		
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;	
		direction = "down";
	}
	public void update() {
		if(keyH.upPressed == true || keyH.downPressed == true 
				|| keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed == true) {
				direction = "up";				
			}
			else if(keyH.downPressed == true) {
				direction = "down";				
			}
			else if(keyH.leftPressed == true) {
				direction = "left";				
			}
			else if(keyH.rightPressed == true) {
				direction = "right";				
			}
			
			collisionOn = false;
			gp.checker.checkTile(this);
			
			int objIndex = gp.checker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(!collisionOn) {
				switch(direction) {
				case"up": worldY -= speed; break;
				case"down": worldY += speed; break;
				case"left": worldX -= speed; break;
				case"right": worldX += speed; break;
				}
			}
			
			spriteCounter++;
	        if (spriteCounter > 12) {
	            if (spriteNumber == 1) {
	                spriteNumber = 2;
	            } else {
	                spriteNumber = 1;
	            }
	            spriteCounter = 0;
	        }
	    }
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				hasKey++;
				gp.obj[i] = null;
				System.out.println("Key: " + hasKey);
				break;
			case "Door":
				if(hasKey > 0);
				gp.obj[i] = null;
				hasKey--;
				break;
			}
		}
	}
    
	public void draw(Graphics2D g2) {
		
		
		BufferedImage image = null;
		switch (direction) {
        case "up":
            image = (spriteNumber == 1) ? up1 : up2;
            break;
        case "down":
            image = (spriteNumber == 1) ? down1 : down2;
            break;
        case "left":
            image = (spriteNumber == 1) ? left1 : left2;
            break;
        case "right":
            image = (spriteNumber == 1) ? right1 : right2;
            break;
    }
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
	
}
