package com.CI.game.graphics;

import java.util.ArrayList;

import com.CI.lotusFramework.Image;


public class Animation 
{

	private ArrayList frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;
	


	
	public Animation() 
	{
		frames = new ArrayList();
		totalDuration = 0;

		synchronized (this) 
		{
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	public Animation(Image f, Image f2, Image f3, long duration)
	{
		frames = new ArrayList();
		totalDuration = 0;

		synchronized (this) 
		{
			animTime = 0;
			currentFrame = 0;
		}
		this.addFrame(f, duration);
		this.addFrame(f2, duration);
		this.addFrame(f3, duration);
	}

	/***
	 * 	
	 * @param image
	 * @param duration - time to display frame in miliseconds (1000ms = 1s).
	 */
	public synchronized void addFrame(Image image, long duration) 
	{
		totalDuration += duration;
		this.frames.add(new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime) 
	{
		if (this.frames.size() > 1) 
		{
			animTime += elapsedTime;
			if (animTime >= totalDuration) 
			{
				animTime = animTime % totalDuration;
				currentFrame = 0;

			}

			while (animTime > getFrame(currentFrame).endTime) 
			{
				currentFrame++;

			}
		}
	}

	public synchronized Image getImage() 
	{
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	private AnimFrame getFrame(int i) 
	{
		return (AnimFrame) frames.get(i);
	}

	private class AnimFrame 
	{

		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
