package com.CI.game.entity.buildings;

import com.CI.game.graphics.Sprite;

public class CastleWallV extends BuildingEntity
{
	private int buildingHealth;
	public CastleWallV()
	{
		//super();
		//this.buildingHealth = 100;
	}
	
	public CastleWallV(int x, int y)
	{
		super(Sprite.stoneWallV, x, y);
	}

	public void update()
	{
		if(getHealth() <= 0)
		{
			die();
		}
		buildingColliderBounds.set(getPixel32X() ,getPixel32Y(), getPixel32X()+32, getPixel32Y() + 32);
		buildingAreaBounds.set(getPixel32X()-32, getPixel32Y()-32, getPixel32X() + 32, getPixel32Y() + 32);
	}


}
