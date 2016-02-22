package com.CI.lotusFramework.implementation;

import android.graphics.Bitmap;

import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Graphics.ImageFormat;

public class AndroidImage implements Image 
{
    Bitmap bitmap;
    ImageFormat format;
    
    public AndroidImage(Bitmap bitmap, ImageFormat format) 
    {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() 
    {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() 
    {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() 
    {
        return format;
    }
    
    public Bitmap getBitmap()
    {
    	return this.bitmap;
    }
    
    public void setBitmap(Bitmap b)
    {
    	this.bitmap = b;
    }

    @Override
    public void dispose() 
    {
        bitmap.recycle();
    }      
}
