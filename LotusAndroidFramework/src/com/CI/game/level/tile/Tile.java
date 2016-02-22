package com.CI.game.level.tile;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.CI.game.entity.Camera;
import com.CI.game.entity.Node;
import com.CI.game.entity.Robot;
import com.CI.game.graphics.Assets;
import com.CI.game.graphics.Background;
import com.CI.game.graphics.GameScreen;
import com.CI.game.graphics.Sprite;
import com.CI.game.graphics.SpriteSheet;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Graphics.ImageFormat;
import com.CI.lotusFramework.implementation.AndroidImage;

public class Tile 
{

	private int tileX, tileY, speedX;
	public int type;
	public Image image;
	public Sprite sprite;
	private int SIZE; 
	private Robot robot = GameScreen.getRobot();
	private Background bg = GameScreen.getBg1();
	private SpriteSheet sheet;
	private Node node;

	private Rect r;

	private boolean isSolid;
	
	public static Tile grass = new GrassTile(false, Sprite.grass32);
	public static Tile grassTile_1 = new GrassTile(false, Sprite.grass32);
	public static Tile stoneWall = new StoneWallTile(true, Sprite.stoneWall);
	public static Tile stoneWallV = new Tile(true, Sprite.stoneWallV);
	public static Tile stoneWallH = new Tile(true, Sprite.stoneWallH);

	public static Tile sand = new GrassTile(false, Sprite.sand32);
	public static Tile shrub = new GrassTile(false, Sprite.shrub32);
	public static Tile grid = new Tile(false, Sprite.grid);
	public static Tile voidTile = new GrassTile(false, Sprite.blk);
	
	private Tile cameFrom;
	
	private int ID;
	
	public static String col_grass = "ff15ff00";
	public static String col_shrub = "ff267f00";
	public static String col_dirt  = "ffffd800";
	
	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public Tile(int x, int y)
	{
		this.tileX = x;// get pos on screen
		this.tileY = y;
		setSolid(false);
	}
	
	public Tile(boolean solid, int size, int x, int y, Sprite sprite) 
	{
		this.isSolid = solid;
		this.sprite = sprite;
		image = this.sprite.getImage();
		setSize(size);
		//tileX = x * 40;// spaces out
		//tileY = y * 40;
		this.tileX = x;// get pos on screen
		this.tileY = y;// get pos on screen
	
		//this.SIZE = 32;
		//r = new Rect();
	}
	
	public Tile(int size, int x, int y, int typeInt) 
	{
		this.ID = type;
		setSize(size);
		//tileX = x * 40;// spaces out
		//tileY = y * 40;
		tileX = x;// get pos on screen
		tileY = y;// get pos on screen
	
		//this.SIZE = 32;
		r = new Rect();
	
		//load();
		type = typeInt;

		r = new Rect();

		if (type == 5) 
		{
			image = Tile.sand.getSprite().getImage();
		}
		
		else if (type == 8) 
		{
			image = Assets.tilegrassTop;
		} 
		else if (type == 4) 
		{
			image = Assets.tilegrassLeft;
		} 
		else if (type == 6)
		{
			image = Tile.shrub.getSprite().getImage();
		} 
		else if (type == 2) 
		{
			image = Tile.stoneWall.getSprite().getImage();
		}
		else if (type == 1) 
		{
			image = Tile.grassTile_1.getSprite().getImage();
			
		}
		else 
		{
			type = 0;
		}

	}
	
	public Tile(boolean isSolid, Sprite sprite)
	{
		setSize(sprite.getSIZE());
		this.sprite = sprite;
		image = sprite.getImage();
		//tileX = x * getSize();
		//tileY = y * getSize();
		r = new Rect();
		
		
		//load(); // no longer needed since sprite holds sheet image ref by calling its own load
	}
	

	private void setSprite(Sprite s) 
	{
		this.sprite = s;		
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}

	private void load()
	{

		Matrix m = new Matrix ();
		m.postScale(1.0f,1.0f);
		Bitmap bMap = Bitmap.createBitmap(image.getBitmap(),tileX, tileY, SIZE, SIZE, m, false); 
		this.image = new AndroidImage(bMap, ImageFormat.ARGB4444);	
	}

		public void update() 
		{
			//speedX = bg.getSpeedX() * 5;
			//tileX += speedX;
			//r.set(tileX, tileY, tileX+SIZE, tileY+SIZE);
	
			
			/*
			if (Rect.intersects(r, Robot.yellowRed) && type != 0) 
			{
				checkVerticalCollision(Robot.rect, Robot.rect2);
				checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft, Robot.footright);
			}*/
	
		}

	public int getSize()
	{
		return SIZE;
	}
		
	public int getTileX() 
	{
		return tileX;
	}

	public void setTileX(int tileX) 
	{
		this.tileX = tileX;
	}

	public int getTileY() 
	{
		return tileY;
	}
	
	public void setSize(int size)
	{
		this.SIZE = size;
	}

	public void setTileY(int tileY) 
	{
		this.tileY = tileY;
	}

	public Image getTileImage() 
	{
		return image;
	}
	
	public boolean solid()
	{
		return this.isSolid;
	}

	public void setTileImage(Image tileImage) 
	{
		this.image = tileImage;
	}

	public void checkVerticalCollision(Rect rtop, Rect rbot) 
	{
		if (Rect.intersects(rtop, r)) 
		{
			
		}

		if (Rect.intersects(rbot, r) && type == 8) 
		{
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(tileY - 63);
		}
	}

	public void checkSideCollision(Rect rleft, Rect rright, Rect leftfoot, Rect rightfoot) 
	{
		if (type != 5 && type != 2 && type != 0)
		{
			if (Rect.intersects(rleft, r)) 
			{
				robot.setCenterX(tileX + 102);
	
				robot.setSpeedX(0);
	
			}else if (Rect.intersects(leftfoot, r)) 
			{
				
				robot.setCenterX(tileX + 85);
				robot.setSpeedX(0);
			}
			
			if (Rect.intersects(rright, r)) 
			{
				robot.setCenterX(tileX - 62);
	
				robot.setSpeedX(0);
			}
			
			else if (Rect.intersects(rightfoot, r)) 
			{
				robot.setCenterX(tileX - 45);
				robot.setSpeedX(0);
			}
		}
	}

	public Tile getCameFrom() {
		return this.cameFrom;
	}

	public Tile setCameFrom(Tile cameFrom) {
		this.cameFrom = cameFrom;
		return this.cameFrom;
	}
	
	public void setSolid(boolean b)
	{
		this.isSolid = b;
	}
	
	public void render (int x, int y, Camera screen)
	{
		screen.renderTile(x * 32, y * 32, this);
	}
	
	//public boolean solid(){return false;}// tile is not solid by default.

}