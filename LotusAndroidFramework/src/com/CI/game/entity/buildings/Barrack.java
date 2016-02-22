package com.CI.game.entity.buildings;

import android.graphics.Rect;

import com.CI.game.graphics.Sprite;

public class Barrack extends BuildingEntity
{
	private int buildingHealth;
	
	public Barrack()
	{
		super();
		this.buildingHealth = 100;
	}
	
	public Barrack(Sprite sprite, int x, int y)
	{
		super(sprite, x, y);
	}
	
	public void update()
	{
		buildingColliderBounds.set(getPixel32X() ,getPixel32Y(), getPixel32X()+64, getPixel32Y() + 64);
		buildingAreaBounds.set(getPixel32X()-64, getPixel32Y()-64, getPixel32X() + 96, getPixel32Y() + 96);
	}

}
