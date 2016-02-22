package com.CI.game.entity;

import com.CI.game.SampleGame;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.graphics.Sprite;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.implementation.AndroidGame;
import com.CI.lotusFramework.implementation.AndroidGraphics;


public class NPC extends ArmyUnit 
{
	public NPC(int centerX, int centerY) 
	{
		super(centerX, centerY);
		//setCenterX(centerX);
		//setCenterY(centerY);
		
	}
	
	public NPC(Sprite sprite, ArmyUnit t, int centerX, int centerY) 
	{
		super(centerX, centerY, t);
		//setCenterX(centerX);
		//setCenterY(centerY);
		setSprite(sprite);
		this.target1 = t;
	}
	
	public NPC()
	{
		
	}

}
