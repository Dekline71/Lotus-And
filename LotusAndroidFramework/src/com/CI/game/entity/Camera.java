package com.CI.game.entity;

import com.CI.game.GameManager;
import com.CI.game.entity.projectiles.Projectile;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Graphics;

public class Camera // camera screen view that player sees
{
	
	private Graphics g;
	private int width, height;
	public int[] pixels;
	public static final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	//public final int SPRITE_SIZE_MASK = Sprite.grass.SIZE - 1;
	
	public int xOffset, yOffset;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];// 4096 (map size)
		
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];// 48,600
		xOffset = 0;
		yOffset = 0;
	//	for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++)//randomly regenerate color to titleIndex
		//{
			//tiles[i] = random.nextInt(0xffffff);
		//}
		
		//tiles[0] = 0x000000;
	}
	
	public void clear()
	{
		for(int i = 0; i < getPixels().length; i++)
		{
			getPixels()[i] = 0xffffffff;
		}
	}
	
	public void renderSprite(int xPos, int yPos, Sprite sprite, boolean fixed)
	{
		if(fixed)
		{
			yPos -= yOffset;
			xPos -= xOffset;//render sprite to the portion of the map we see currently
		}
		
		for(int y = 0; y < sprite.getHeight(); y++)
		{
			int ya = y + yPos;
			for(int x = 0; x < sprite.getWidth(); x++)
			{
				int xa = x + xPos;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;// if breaks screen pos, break loop iteration
				getPixels()[xa + ya * width] = sprite.getPixels()[x + y * sprite.getWidth()];
			}
		}
		}
	
	/*public void render(int xOffset, int yOffset)
	{		
		for(int y = 0; y < height; y++)
		{		
			int yPixel = y + yOffset;
			
			if (yPixel < 0 || yPixel >= width)continue;
			
			for(int x = 0; x < width; x++)
			{
				int xPixel = x + xOffset;
				
				if (xPixel < 0 || xPixel >= width)continue;
								
				pixels[xPixel + yPixel * width] = Sprite.grass.pixels[(x & SPRITE_SIZE_MASK) + (y & SPRITE_SIZE_MASK) * Sprite.grass.SIZE]; //renders
		
			}
		}
	}*/

	// factor in players position and move tiles accordingly	
	public void renderTile(int xPos, int yPos, Tile tile)
	{
		xPos = this.xOffset - xPos;
		yPos = this.yOffset - yPos;
		for (int y = 0; y < tile.getSprite().getSIZE(); y++)
		{
			int yabsolute = y + yPos;// y position relative to object
			
			for (int x = 0; x < tile.getSprite().getSIZE(); x++)
			{
				int xabsolute = x + xPos;// x position relative to object
				if (xabsolute < -tile.getSprite().getSIZE() || xabsolute >= this.getWidth() || yabsolute < 0 || yabsolute >= this.getHeight()) break;//if tiles exists screen, stop rendering tile
					if (xabsolute < 0)
					{	
						xabsolute = 0;
					}	
					//System.out.println("Im Here");
				this.pixels[xabsolute + (yabsolute * this.getWidth())] = tile.getSprite().getPixels()[x + (y * tile.getSprite().getSIZE())];
				//System.out.println(Integer.toHexString(tile.getSprite().getPixels()[x + (y * tile.getSprite().getSIZE())])+"hi");
			}
			
		}
	}
	
/*	public void renderProjectile(int xPos, int yPos, Projectile p)
	{
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++)
		{
			int yabsolute = y + yPos;// y position relative to object
			
			for (int x = 0; x < p.getSpriteSize(); x++)
			{
				int xabsolute = x + xPos;// x position relative to object
				if (xabsolute < -p.getSpriteSize() || xabsolute >= width || yabsolute < 0 || yabsolute >= height) break;//if tiles exists screen, stop rendering tile
					if (xabsolute < 0){xabsolute = 0;}	
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (col != 0xFFFF00DC)
					pixels[xabsolute + yabsolute * width] = col;
			}
			
		}
	}
*/	
	public void renderPlayer(int xPos, int yPos, Sprite sprite)
	{
		xPos -= xOffset;
		yPos -= yOffset;
		for(int y = 0; y < 32; y++)
		{
			int yabsolute = y + yPos;
			for(int x = 0; x < 32; x++)
			{
				int xabsolute = x + xPos;
				if (xabsolute < -32 || xabsolute >= width || yabsolute < 0 || yabsolute >= height)break;
				if (xabsolute < 0){ xabsolute = 0;}
				int col = sprite.getPixels()[x + y * 32];
				if(col != 0x00000000)
				getPixels()[xabsolute + yabsolute * width] = col;
			}
		}
	}
	
	public void renderEntity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		
		int mapX = (xPos * 32);
		int mapY = yPos * 32;
		int mapXoffset = this.xOffset % 64;
		int mapYoffset = this.yOffset % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.yOffset;
		int xabsolute = xPos + this.xOffset;
								
		// draw entity/sprite if between the scroll offset range
		if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 200, (mapY-mapYoffset)-this.yOffset);
		}
		else if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > -16 && (mapY - mapYoffset) - this.yOffset > 16 && !GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset), (mapY-mapYoffset)-this.yOffset);
		}
	}
	
	public void renderArmyEntity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		
		int mapX = xPos * 64;
		int mapY = yPos * 64;
		int mapXoffset = this.xOffset % 64;
		int mapYoffset = this.yOffset % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.yOffset;
		int xabsolute = xPos + this.xOffset;
								
		// draw entity/sprite if between the scroll offset range
		if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 193, ((mapY-mapYoffset)-this.yOffset) + 14);
		}
		else if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > -16 && (mapY - mapYoffset) - this.yOffset > -16 && !GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) - 8, ((mapY-mapYoffset)-this.yOffset) + 14);
		}
	}
	
	public void render64Entity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		
		int mapX = (xPos * 64);
		int mapY = yPos * 64;
		int mapXoffset = this.xOffset % 32;
		int mapYoffset = this.yOffset % 32;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.yOffset;
		int xabsolute = xPos + this.xOffset;
								
		// draw entity/sprite if between the scroll offset range
		if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 187, ((mapY-mapYoffset)-this.yOffset));
		}
		else if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > -16 && (mapY - mapYoffset) - this.yOffset > -16 && !GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) , ((mapY-mapYoffset)-this.yOffset) );
		}
	}
	
	public void renderObject(int xPos, int yPos, Sprite sprite)
	{
		xPos -= xOffset;
		yPos -= yOffset;
		for(int y = 0; y < 32; y++)
		{
			int yabsolute = y + yPos;
			for(int x = 0; x < 32; x++)
			{
				int xabsolute = x + xPos;
				if (xabsolute < -32 || xabsolute >= width || yabsolute < 0 || yabsolute >= height)break;
				if (xabsolute < 0){ xabsolute = 0;}
				int col = sprite.getPixels()[x + y * 32];
				if(col != 0xFFFFFFFF)
				getPixels()[xabsolute + yabsolute * width] = col;
			}
		}
	}
	
	public void renderSprite(Sprite e, int xPos, int yPos)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		
		int mapX = xPos * 64;
		int mapY = yPos * 64;
		int mapXoffset = this.xOffset % 64;
		int mapYoffset = this.yOffset % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.yOffset;
		int xabsolute = xPos + this.xOffset;
								
		// draw entity/sprite if between the scroll offset range
		if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 193, ((mapY-mapYoffset)-this.yOffset) + 14);
		}
		else if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > -16 && (mapY - mapYoffset) - this.yOffset > -16 && !GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage(e.getImage(), ((mapX-mapXoffset)-this.xOffset) - 8, ((mapY-mapYoffset)-this.yOffset) + 14);
		}
	}
	
	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public int[] getPixels() {
		return this.pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public int getHeight() 
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}

	public void setGraphics(Graphics graphics) 
	{
		this.g = graphics;		
	}
	
}
