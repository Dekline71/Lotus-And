package com.CI.lotusFramework;


import com.CI.game.entity.Camera;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.tile.Tile;

import android.graphics.Paint;
/*
An individual pixel in an image takes up a bit (1/8 of a byte) of memory. 
Calculation the total memory usage in done by multiplying the width and height; however, 
there is a third dimension to consider: depth. 
This problem is where ImageFormats come in.

ImageFormats from the enumeration in the Graphics interface (RGB565, ARGB4444, ARGB8888) in the code below, 
are three formats that can be used when storing images to memory:

1) (RGB565) takes up the least memory (at least in practice). 
Red, Green, and Blue (RGB) have depths of 5, 6, and 5, respectively. 
However, there is no Alpha value (opacity/transparency).

2) (ARGB4444) has a total depth of 16. (Use this when you need transparency in your image).

3) (ARGB8888) has a total depth of 32. (You should almost never need to use this format. ARGB4444 usually is enough).

The quality of your image will improve with use of the 3rd format as opposed to the 1st, but in return you will take up memory much faster. 
A single 1000x1000 image with depth of 32 will take up 32,000,000 bits, or 4MB. 
If four of those were instantiated on a device with 16MB, an out of memory exception and your game will crash (this assumes you have no other objects stored in memory, which is impossible).
*/
public interface Graphics 
{
	/**
	 * 3 formats that can be used when storing images to memory:

		1) (RGB565) takes up the least memory (at least in practice). 
		Red, Green, and Blue (RGB) have depths of 5, 6, and 5, respectively. 
		However, there is no Alpha value (opacity/transparency).

		2) (ARGB4444) has a total depth of 16. (Use this when you need transparency in your image).

		3) (ARGB8888) has a total depth of 32. (You should almost never need to use this format. ARGB4444 usually is enough).
	 * @author Jake Galletta Conscious Interactive (c) 2014
	 *
	 */
	public static enum ImageFormat 
	{
		ARGB8888, ARGB4444, RGB565
	}
    /***
     * 
     * @param sprite sprite instance to be drawn to screen
     * @param x - x/horizontal position on screen to be drawn
     * @param y - y/vertical position on screen to be drawn
     * 
     */
	public void drawSprite(Sprite sprite, int x, int y) ;
	
    /***
     * 
     * @param Image image to be drawn to screen
     * @param x - x/horizontal position on screen to be drawn
     * @param y - y/vertical position on screen to be drawn
     * 
     */
	public void drawEntityImage(Image Image, int x, int y) ;
	
	public Image newImage(String fileName, ImageFormat format);

	public Image newSprite(Sprite sprite, int srcX, int srcY, int srcWidth, int srcHeight);
	
	public Image newSprite(Sprite sprite);
	
	public Image newTile(Tile tile);
	
	public void clearScreen(int color);

	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawRect(int x, int y, int width, int height, int color);

	public void drawImage(Image image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);

	public void drawImage(Image Image, int x, int y);
	
	public void drawTile(Tile Image, int x, int y);

	void drawString(String text, int x, int y, Paint paint);

	public int getWidth();

	public int getHeight();

	public void drawARGB(int i, int j, int k, int l);

	void drawRect(int left, int top, int right, int bottom);

	public void setTile(Tile t);

	public void drawTile();

	public void setGrid(int i, int j, int k, int l);

	public void drawGrid(Image i);

	public void renderPxlArry(Camera screen);

	public void drawSavedTileFrame();

	public void saveTileFrame();
}
