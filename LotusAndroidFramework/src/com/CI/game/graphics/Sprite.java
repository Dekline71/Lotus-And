package com.CI.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Graphics.ImageFormat;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.implementation.AndroidImage;

public class Sprite
{
	private int  srcX,  srcY,  srcWidth,  srcHeight;
	private int SIZE;
	private int x, y;
	private int width, height;
	private int[] pixels;
	private SpriteSheet sheet;
	
	public static Image testSprite;
	
	private Image image;
	
	public static Sprite sorcSpriteR = new Sprite(32, 1, 5, SpriteSheet.sheet);
	public static Sprite sorcSpriteR2 = new Sprite(32, 1, 6, SpriteSheet.sheet);
	public static Sprite sorcSpriteR3 = new Sprite(32, 1, 7, SpriteSheet.sheet);
	
	public static Sprite sorcSpriteL = new Sprite(32, 3, 5, SpriteSheet.sheet);
	public static Sprite sorcSpriteL2 = new Sprite(32,3, 6, SpriteSheet.sheet);
	public static Sprite sorcSpriteL3 = new Sprite(32,3, 7, SpriteSheet.sheet);
	
	public static Sprite sorcSpriteU = new Sprite(32,0,5, SpriteSheet.sheet);
	public static Sprite sorcSpriteU2 = new Sprite(32,0,6, SpriteSheet.sheet);
	public static Sprite sorcSpriteU3 = new Sprite(32,0,7, SpriteSheet.sheet);

	public static Sprite sorcSpriteD = new Sprite(32, 2, 5, SpriteSheet.sheet);
	public static Sprite sorcSpriteD2 = new Sprite(32, 2, 6, SpriteSheet.sheet);
	public static Sprite sorcSpriteD3 = new Sprite(32, 2, 7, SpriteSheet.sheet);

	public static Sprite enemSpriteR = new Sprite(26, 1, 0, SpriteSheet.peon);
	public static Sprite enemSpriteR2 = new Sprite(26, 1, 1, SpriteSheet.peon);
	public static Sprite enemSpriteR3 = new Sprite(26, 1, 2, SpriteSheet.peon);
	public static Sprite enemSpriteR4 = new Sprite(26, 1, 3, SpriteSheet.peon);
	public static Sprite enemSpriteR5 = new Sprite(26, 1, 4, SpriteSheet.peon);
	
	public static Sprite enemSpriteU = new Sprite(26, 0, 0, SpriteSheet.peon);
	public static Sprite enemSpriteU2 = new Sprite(26, 0, 1, SpriteSheet.peon);
	public static Sprite enemSpriteU3 = new Sprite(26, 0, 2, SpriteSheet.peon);
	public static Sprite enemSpriteU4 = new Sprite(26, 0, 3, SpriteSheet.peon);
	public static Sprite enemSpriteU5 = new Sprite(26, 0, 4, SpriteSheet.peon);
	
	public static Sprite enemSpriteD = new Sprite(26, 2, 0, SpriteSheet.peon);
	public static Sprite enemSpriteD2 = new Sprite(26, 2, 1, SpriteSheet.peon);
	public static Sprite enemSpriteD3 = new Sprite(26, 2, 2, SpriteSheet.peon);
	public static Sprite enemSpriteD4 = new Sprite(26, 2, 3, SpriteSheet.peon);
	public static Sprite enemSpriteD5 = new Sprite(26, 2, 4, SpriteSheet.peon);
	
	public static Sprite enemSpriteL = new Sprite(26, 4, 0, SpriteSheet.peon);
	public static Sprite enemSpriteL2 = new Sprite(26, 4, 1, SpriteSheet.peon);
	public static Sprite enemSpriteL3 = new Sprite(26, 4, 2, SpriteSheet.peon);
	public static Sprite enemSpriteL4 = new Sprite(26, 4, 3, SpriteSheet.peon);
	public static Sprite enemSpriteL5 = new Sprite(26, 4, 4, SpriteSheet.peon);
	
	public static Sprite peasSpriteU = new Sprite(24, 0, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteU2 = new Sprite(24, 0, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteU3 = new Sprite(24, 0, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteU4 = new Sprite(24, 0, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteU5 = new Sprite(24, 0, 4, SpriteSheet.peasant);

	public static Sprite peasSpriteR = new Sprite(24, 1, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteR2 = new Sprite(24, 1, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteR3 = new Sprite(24, 1, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteR4 = new Sprite(24, 1, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteR5 = new Sprite(24, 1, 4, SpriteSheet.peasant);

	public static Sprite peasSpriteD = new Sprite(24, 2, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteD2 = new Sprite(24, 2, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteD3 = new Sprite(24, 2, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteD4 = new Sprite(24, 2, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteD5 = new Sprite(24, 2, 4, SpriteSheet.peasant);

	public static Sprite peasSpriteL = new Sprite(24, 3, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteL2 = new Sprite(24, 3, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteL3 = new Sprite(24, 3, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteL4 = new Sprite(24, 3, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteL5 = new Sprite(24, 3, 4, SpriteSheet.peasant);

	public static Sprite grass = new Sprite(16, 2, 1, SpriteSheet.sheet);
	
	public static Sprite woodPile = new Sprite(32, 7, 0, SpriteSheet.sheet);
	public static Sprite signPost = new Sprite(32, 7, 1, SpriteSheet.sheet);
	public static Sprite stoneWall = new Sprite(16,4,2,SpriteSheet.sheet);
	public static Sprite stoneWallH = new Sprite(32,3,4,SpriteSheet.sheet);
	public static Sprite stoneWallV = new Sprite(32,3,3,SpriteSheet.sheet);

	public static Sprite sand = new Sprite (16, 2, 2, SpriteSheet.sheet);
	public static Sprite shrub = new Sprite(16, 3, 4,SpriteSheet.sheet );

	public static Sprite blk = new Sprite(16, 1, 4, SpriteSheet.sheet);
	
	// 32x32 
	public static Sprite grass32 = new Sprite(32, 0, 6, SpriteSheet.grassSheet); 
	public static Sprite shrub32 = new Sprite(32, 1, 7,SpriteSheet.grassSheet );
	public static Sprite sand32 = new Sprite (32, 0, 3, SpriteSheet.grassSheet);
	
	// Building sprites
	public static Sprite barrack = new Sprite(64, 0, 0,SpriteSheet.barrack);
	public static Sprite castleWall = new Sprite(32, 4, 4,SpriteSheet.sheet);
	public static Sprite castleWall2;
	public static Sprite grid = new Sprite(32, 1, 4,SpriteSheet.sheet );
	
	// UI
	public static Sprite gold = new Sprite(48, 4,2,SpriteSheet.sheet);
	public static Sprite num_0 = new Sprite(16, 8,0,SpriteSheet.sheet);
	public static Sprite num_1 = new Sprite(16, 9,0,SpriteSheet.sheet);
	public static Sprite num_2 = new Sprite(16, 10,0,SpriteSheet.sheet);
	public static Sprite num_3 = new Sprite(16, 11,0,SpriteSheet.sheet);
	public static Sprite num_4 = new Sprite(16, 12,0,SpriteSheet.sheet);
	public static Sprite num_5 = new Sprite(16, 13,0,SpriteSheet.sheet);
	public static Sprite num_6 = new Sprite(16, 8,1,SpriteSheet.sheet);
	public static Sprite num_7 = new Sprite(16, 9,1,SpriteSheet.sheet);
	public static Sprite num_8 = new Sprite(16, 10,1,SpriteSheet.sheet);
	public static Sprite num_9 = new Sprite(16, 11,1,SpriteSheet.sheet);
	public static Sprite goldText = new Sprite(48, 1, 2, SpriteSheet.sheet);
	public static Sprite daysText = new Sprite(64, 1, 0, SpriteSheet.grassSheet);
	public static Sprite selectedEntityPoint = new Sprite(32, 1, 2, SpriteSheet.grassSheet);
	
	public Sprite()
	{
		this.setSIZE(0);
	}
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		super();
		this.setSIZE(size);
		this.width = size;
		this.height = size;
		setPixels(new int[getSIZE() * getSIZE()]);//create new pixel arry size of the sprite
		//setColor(0xffffffff);
		this.x = x * size;//set x location of target sprite in sprite sheet
		this.y = y * size;//set y location of target sprite in sprite sheet
		setSrcX(this.x);
		setSrcY(this.y);
		this.sheet = sheet;//load targeted instance of SpriteSheet
		this.image = sheet.getImage();
		load();//load targeted sprite
		
	}
	
	public Sprite(int width, int height, int color)
	{
		setSIZE(getSIZE() - 1);
		this.width = width;
		this.height = height;
		setPixels(new int[width * height]);
		setColor(color);
	}
	
	public Sprite(int size, int color)
	{
		setSIZE(size);
		this.width = size;
		this.height = size;
		setPixels(new int[getSIZE()*getSIZE()]);
		setColor(color);
	}
	
	private void setColor(int color)
	{
		for(int i = 0; i < width * height; i++)
		{
			getPixels()[i] = color;
		}
	}
	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	// Extracts a single sprite from targeted sprite sheet
	private void load()
	{
		//Matrix m = new Matrix ();
		//m.postScale(1.0f,1.0f);
		Bitmap bMap = Bitmap.createBitmap(this.image.getBitmap(), x, y, getSIZE(), getSIZE());
		this.image = new AndroidImage(bMap, ImageFormat.RGB565);	
		
		this.image.getBitmap().getPixels(this.pixels, 0, width, 0, 0, width, height);
		//System.out.println( Integer.toHexString(pixels[0]));
loadPXLS();
		/*for(int y = 0; y < SIZE; y++)
		{
			for(int x = 0; x < SIZE; x++)
			{
				pixels[x + y * width] = image.getBitmap().getPixel(x, y);//Set pixel to sprites pixel in sheet
			}
		}*/
	}
	
	private void loadPXLS()
	{
		//loop thru all pixels in sprite and copy color data from sprite sheet
		for(int y = 0; y < getSIZE(); y++)
		{
			for(int x = 0; x < getSIZE(); x++)
			{
				getPixels()[x + y * width] = this.sheet.getPixels()[(x + this.x) + (y + this.y) * this.sheet.SIZE];//Set pixel to sprites pixel in sheet
			}
		}
	}
	
	public void setSrcHeight(int h)
	{
		this.srcHeight = h;
	}
	
	public void setSrcWidth(int w)
	{
		this.srcWidth = w;
	}
	
	public void setSrcX(int x)
	{
		this.srcX = x;
	}
	
	public void setSrcY(int y)
	{
		this.srcY = y;
	}
	
	public int getSrcX()
	{
		return srcX;
	}
	
	public int getSrcY()
	{
		return srcY;
	}
	
	public int getSrcWidth()
	{
		return srcWidth;
	}
	
	public int getSrcHeight()
	{
		return srcHeight;
	}

	public Image getImage()
	{
		return this.image;
	}
	
	public SpriteSheet getSpriteSheet()
	{
		return sheet;
	}
	public int[] getPixels() {
		return pixels;
	}
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	public int getSIZE() {
		return this.SIZE;
	}
	public void setSIZE(int sIZE) {
		this.SIZE = sIZE;
	}
}
