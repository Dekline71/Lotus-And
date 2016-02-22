package com.CI.lotusFramework;

import android.graphics.Bitmap;

import com.CI.lotusFramework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public Bitmap getBitmap();
    public ImageFormat getFormat();
    public void dispose();
	public void setBitmap(Bitmap bitmap);
}
