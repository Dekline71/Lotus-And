package com.CI.lotusFramework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.CI.game.entity.Camera;
import com.CI.game.graphics.Sprite;
import com.CI.game.graphics.SpriteSheet;
import com.CI.game.level.Level;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Graphics.ImageFormat;

public class AndroidGraphics implements Graphics 
{
	/* -Bitmap allows you to create image objects.
	 * -Canvas is a literal canvas for your images, which are drawn onto the canvas,
	 *  resulting in being drawn to the screen.
     * -Paint is used for styling what you draw to the screen. 
	 */
    public static AssetManager assets;// not static in OG source code
    Bitmap frameBuffer, frameBuffer2;

    Canvas canvasLayer_1;
    Canvas canvasLayer_2;
    Canvas canvasEntityLayer_2;
   static Bitmap tileMap = Bitmap.createBitmap(800, 480, Config.RGB_565);// canavs layer 2 bitmap
    Bitmap prevTileLayer_Frame;// = Bitmap.createBitmap(800, 480, Config.RGB_565);
   //static Bitmap gridMap = Bitmap.createBitmap(800, 480, Config.ARGB_4444);
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
	Canvas canvasGridLayer;
    
    //public static Canvas graphics;
    public static String s;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) 
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvasLayer_1 = new Canvas(frameBuffer);
 
        this.canvasLayer_2 = new Canvas(tileMap);
        //this.canvasEntityLayer_2 = new Canvas(new framebuffer);
        
        //this.canvasGridLayer = new Canvas(gridMap);
        this.paint = new Paint();
    }
    
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, Bitmap frameBuffer2) 
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.frameBuffer2 = frameBuffer2;
        this.canvasLayer_1 = new Canvas(frameBuffer);
       // this.canvasLayer_2 = new Canvas();
        this.paint = new Paint();
    }
    
    /*
    Each pixel in an image takes up a bit (1/8 of a byte) of memory. 
    So you can calculate the total memory usage by multiplying the width and height; however, 
    there is a third dimension to consider: depth. This is where ImageFormats come in.

	following ImageFormats from the Graphics interface (RGB565, ARGB4444, ARGB8888) in the code below, 
	These are three formats that can be used when storing images to memory:

	1) (RGB565) takes up the least memory (at least in practice). 
	Red, Green, and Blue (RGB) have depths of 5, 6, and 5, respectively. 
	However, there is no Alpha value (opacity/transparency).

 	2) (ARGB4444) has a total depth of 16. (Use this when you need transparency in your image).

 	3) (ARGB8888) has a total depth of 32. (You should almost never need to use this format. ARGB4444 usually is enough).

	The quality of your image will improve with use of the 3rd format as opposed to the 1st, but in return you will take up memory much faster. 
	A single 1000x1000 image with depth of 32 will take up 32,000,000 bits, or 4MB. 
	If four of those were instantiated on a device with 16MB, an out of memory exception and your game will crash (this assumes you have no other objects stored in memory, which is impossible).
 */

  
    public Bitmap getBitmap(AssetManager ctx, String pathNameRelativeToAssetsFolder) throws IOException {
    	  InputStream bitmapIs = null;
    	  Bitmap bmp = null;
    	  try {
    	    bitmapIs = ctx.open(pathNameRelativeToAssetsFolder);
    	    bmp = BitmapFactory.decodeStream(bitmapIs);
    	  } catch (IOException e)
    	  {
    	    // Error reading the file
    	    e.printStackTrace();

    	    if(bmp != null) 
    	    {
    	      bmp.recycle();
    	      bmp = null;
    	    }
    	  } finally {
    	    if(bitmapIs != null) 
    	    {
    	       bitmapIs.close();
    	    }
    	  }

    	  return bmp;
    	}
    
  /**
 * Instantiates a new image file into one of 3 ImageFormats (RGB565, ARGB4444, ARGB8888) into memory.
 */

    @Override
    public Image newImage(String fileName, ImageFormat format) 
    {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try 
        {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } catch (IOException e) 
        {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally 
        {
            if (in != null) 
            {
                try 
                {
                    in.close();
                } 
                catch (IOException e) 
                {
                	
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) 
    {
        canvasLayer_1.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) 
    {
        paint.setColor(color);
        canvasLayer_1.drawLine(x, y, x2, y2, paint);
    }
    
    /***
     * @param x x position to be drawn on screen
     * @param y y position to be drawn on screen
     * 
     */
    @Override
    public void drawRect(int x, int y, int width, int height, int color) 
    {
        paint.setColor(color);
        paint.setStyle(Style.STROKE);
        canvasLayer_1.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }
    
    @Override
    public void drawRect(int left, int top, int right, int bottom) 
    {
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.FILL);
        canvasLayer_1.drawRect(left, top, right, bottom, paint);
    }
    
    @Override
    public void drawARGB(int a, int r, int g, int b) 
    {
        paint.setStyle(Style.FILL);
        canvasLayer_1.drawARGB(a, r, g, b);
    }
    
    @Override
    public void drawString(String text, int x, int y, Paint paint)
    {
    	canvasLayer_1.drawText(text, x, y, paint);
    }
    
    // fix?
    public Image newSprite(Sprite sprite, int srcX, int srcY, int srcWidth, int srcHeight)
    {
    	sprite.setSrcX(srcX); 
    	sprite.setSrcY(srcY);
    	sprite.setSrcHeight(srcHeight);
    	sprite.setSrcWidth(srcWidth);
    	Bitmap bMap = sprite.getSpriteSheet().getImage().getBitmap() ;
    	
    	
  //  	SpriteSheet.tempSheet = g.newImage("grass_spritesheet.png", ImageFormat.ARGB4444);// 
  	return new AndroidImage(bMap, ImageFormat.ARGB4444) ;
    
    }
    
    public Image newSprite(Sprite sprite)
    {
    	//sprite.setSrcX(sprite.getSrcX()); 
    	//sprite.setSrcY(s);
    	sprite.setSrcHeight(sprite.getHeight());
    	sprite.setSrcWidth(sprite.getWidth());
    	Bitmap bMap = sprite.getSpriteSheet().getImage().getBitmap() ;
    	
    	
  //  	SpriteSheet.tempSheet = g.newImage("grass_spritesheet.png", ImageFormat.ARGB4444);// 
  	return new AndroidImage(bMap, ImageFormat.ARGB4444) ;
    
    }
    
    public void drawTile( Tile t)
    {
    
    	// 
    	canvasLayer_2.drawBitmap(t.getTileImage().getBitmap(), t.getTileX() * t.getSize(),  t.getTileY() * t.getSize(), null);
    	

    	//  	
    }
    
   
    /***
     * 
     * @param sprite sprite instance to be drawn to screen
     * @param x x position on screen to be drawn
     * @param y y position on screen to be drawn
     * 
     */
    public void drawSprite(Sprite sprite, int x, int y) 
    {
        srcRect.left = sprite.getSrcX();
        srcRect.top = sprite.getSrcY();
        srcRect.right = sprite.getSrcX() + sprite.getSrcWidth();
        srcRect.bottom = sprite.getSrcY() + sprite.getSrcHeight();
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + sprite.getSrcWidth();
        dstRect.bottom = y + sprite.getSrcHeight();

        //canvasLayer_1.drawBitmap(((AndroidImage) sprite.image).bitmap, srcRect, dstRect, null);
        canvasLayer_1.drawBitmap(((AndroidImage) sprite.getImage()).bitmap, x, y, null);
    }

    public void drawImage(Image Image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) 
    {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvasLayer_1.drawBitmap(((AndroidImage) Image).bitmap, srcRect , dstRect,
                null);
    }
    
    @Override
    public void drawImage(Image Image, int x, int y) 
    {
        canvasLayer_1.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
    }
    
    @Override
    public void drawEntityImage(Image Image, int x, int y) 
    {
        canvasLayer_2.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
    }
    
    public void drawTile()
    {
        canvasLayer_1.drawBitmap(tileMap, 0, 0, null);
    }
    
    public void drawGrid(Image i)
    {
    	canvasLayer_1.drawBitmap(((AndroidImage)i).getBitmap(), 200, 32, null);
    }
    
    public Image newTile(Tile tile)
    {
    	Bitmap bMap = tile.getTileImage().getBitmap();
    	Image i = new AndroidImage(bMap, ImageFormat.ARGB4444) ;
    	return i;
    }
    
    public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight)
    {
    	
    	
   	 srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
   
        
        canvasLayer_1.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
    }
   
    @Override
    public int getWidth() 
    {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() 
    {
        return frameBuffer.getHeight();
    }
    
    public Canvas getCanvasLayer_1()
    {
    	return this.canvasLayer_1;
    }

	@Override
	public void setTile(Tile t) 
	{
		// TODO Auto-generated method stub
		canvasLayer_2.drawBitmap(t.getTileImage().getBitmap(), t.getTileX()*t.getSize(), t.getTileY()*t.getSize(), null);
	}
	
	public void setGrid(int x, int y, int width,int height)
	{
		canvasGridLayer.drawBitmap(Sprite.grid.getImage().getBitmap(),x,y,null);
	}

	@Override
	public void drawTile(Tile t, int x, int y) 
	{
		
		canvasLayer_1.drawBitmap(t.getTileImage().getBitmap(), x, y, null);
	}
	
	public void saveTileFrame()
	{
    	prevTileLayer_Frame = Bitmap.createBitmap(frameBuffer);
	}
	
    public void drawSavedTileFrame()
    {
    	canvasLayer_1.drawBitmap(prevTileLayer_Frame, 0, 0, null);
    }   
	
	public void renderPxlArry(Camera s)
	{
		//s.clear();
		int [] p = new int [32*32]; 
		//Sprite.castleWall.getImage().getBitmap().getPixels(s.pixels, 0, 32, 0, 0, 32, 32);
		Bitmap frame ;
		frame = Bitmap.createBitmap(s.pixels, 800, 480, Config.ARGB_4444);
		//b.setPixels(s.pixels, 0, 0, 0, 0, 800, 480);
		//canvasLayer_1.drawBitmap(s.getPixels(), 0, 800, 0, 0, 800, 480, true, null);
		canvasLayer_1.drawBitmap(frame, 0, 0, null);
	}

	public static AssetManager getAssets()
	{
		return assets;
	}
}
