package com.CI.game.entity.buildings;

import java.util.ArrayList;

import android.graphics.Rect;

import com.CI.game.entity.Camera;
import com.CI.game.entity.Entity;
import com.CI.game.graphics.Sprite;

public class BuildingEntity extends Entity
{
	public Rect buildingColliderBounds = new Rect(0, 0, 0, 0);
	public Rect buildingAreaBounds = new Rect(0, 0, 0, 0);
	
	public BuildingEntity()
	{
		
	}
	
	public BuildingEntity(int x, int y)
	{
		this.target2 = null;
		this.target1 = null;
		this.health = 100;
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		
		this.pixel32X = x * 32;
		this.pixel32Y = y * 32;
		this.pixel64X = x * 64;
		this.pixel64Y = y * 64;
		isVisible = true;
	}
	
	public BuildingEntity(Sprite sprite, int x, int y)
	{
		//super(100, x, y);
		setSprite(sprite);
		setImage(sprite.getImage());
		this.target2 = null;
		this.target1 = null;
		this.health = 100;
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		
		this.pixel32X = x * 32;
		this.pixel32Y = y * 32;
		this.pixel64X = x * 64;
		this.pixel64Y = x * 64;
		isVisible = true;
	}
	
	public void render(Camera screen)
	{
		screen.renderEntity(getCenter32X(), getCenter32Y() + 1, this);
	}
	
	public void update()
	{
		
	}

	public void nextTurnUpdate() 
	{
		// Update variables for next turn
		
	}
}
