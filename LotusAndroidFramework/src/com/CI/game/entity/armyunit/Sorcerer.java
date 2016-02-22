package com.CI.game.entity.armyunit;

import com.CI.game.entity.Entity;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.Level;


public class Sorcerer extends ArmyUnit
{
	public Sorcerer()
	{
		setHealth(5);
	}
	
	public Sorcerer(int x, int y, Level l)
	{
		super(x, y, null);
		setHealth(5);
		this.level = l;
		setSprite(Sprite.peasSpriteR);
	}
	
	public Sorcerer(Sprite sprite, Entity t, int centerX, int centerY) 
	{
		super(centerX, centerY, t);
		//setCenterX(centerX);
		//setCenterY(centerY);
		setSprite(sprite);
		this.target1 = t;
		setHealth(5);
	}
}
