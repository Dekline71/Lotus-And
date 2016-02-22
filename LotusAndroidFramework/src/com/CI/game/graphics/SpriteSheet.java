package com.CI.game.graphics;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Graphics.ImageFormat;
import com.CI.lotusFramework.implementation.AndroidGraphics;
import com.CI.lotusFramework.implementation.AndroidImage;

public class SpriteSheet 
{
	private String path;// Path to spriteSheet
	public final int SIZE;// Size of spritesheet
	private int[] pixels;
	
	private Image image;
	
	public static Image tempSheet;
	public static SpriteSheet sheet = new SpriteSheet("environment/grass_spritesheet.png", 256);
	public static SpriteSheet barrack = new SpriteSheet("entities/barrack_64.png", 64);
	public static SpriteSheet peon = new SpriteSheet("entities/peonSheet.png", 148);
	public static SpriteSheet peasant = new SpriteSheet("entities/peasantSheet.png", 164);
	//public static SpriteSheet grassSheet = new SpriteSheet(Assets.g32, 256);
	public static SpriteSheet grassSheet = new SpriteSheet("environment/grass_SpriteSheet_2.png", 256);
	
	public SpriteSheet(Image i, int size)
	{
		this.image = i;
		//this.image.setBitmap(i.getBitmap());
		SIZE=size;
		
		try
		{			
			Bitmap b = this.image.getBitmap();
			int w = this.image.getWidth();
			int h = this.image.getHeight();
			b.getPixels(this.getPixels(), 0, w, 0, 0, w, h);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}
	
	public SpriteSheet(String path, int size)
	{
		this.path = path;
		SIZE = size;
		this.setPixels(new int[SIZE * SIZE]);
		load();//set/get pixels to spritesheet
	}
	
	private void load()
	{
		try
		{
			this.image = newImage(this.path, ImageFormat.ARGB4444);
			Bitmap i = this.image.getBitmap();
			int w = this.image.getWidth();
			int h = this.image.getHeight();
			i.getPixels(this.getPixels(), 0, w, 0, 0, w, h);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}
	
	 public Image newImage(String fileName, ImageFormat format) 
	 {
		 AssetManager assets = AndroidGraphics.getAssets();//

	        Config config = null;
	        if (format == ImageFormat.RGB565)
	            config = Config.RGB_565;
	        else if (format == ImageFormat.ARGB4444)
	            config = Config.ARGB_4444;
	        else
	            config = Config.ARGB_8888;

	        Options options = new Options();
	        options.inPreferredConfig = config;
	        
	       
	        
	        //InputStream in = null;
	        Bitmap bitmap = null;
	        try 
	        {
	        	// Load bitmap from res
	            //in = assets.open(fileName);
	            //bitmap = BitmapFactory.decodeStream(in, null, options);

	        	
	    		AndroidGraphics g = (AndroidGraphics) Assets.assets;
	    		bitmap = g.getBitmap(g.getAssets(), fileName);

	            if (bitmap == null)
	                throw new RuntimeException("Couldn't load bitmap from asset '"
	                        + fileName + "'");
	        } catch (IOException e) 
	        {
	            throw new RuntimeException("Couldn't load bitmap from asset '"
	                    + fileName + "'");
	        }/* finally 
	        {
	            if (in != null) 
	            {
	                try 
	                {
	                    in.close();
	                } catch (IOException e) 
	                {
	                }
	            }
	        }*/

	        if (bitmap.getConfig() == Config.RGB_565)
	            format = ImageFormat.RGB565;
	        else if (bitmap.getConfig() == Config.ARGB_4444)
	            format = ImageFormat.ARGB4444;
	        else
	            format = ImageFormat.ARGB8888;

	        return new AndroidImage(bitmap, format);
	 }
	 
	 public Image getImage()
	 {
		 return image;
	 }

	public int[] getPixels() 
	{
		return pixels;
	}

	public void setPixels(int[] pixels) 
	{
		this.pixels = pixels;
	}
}
