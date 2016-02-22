package com.CI.game.entity;

import com.CI.game.graphics.Sprite;

public class SpawnPoint extends Entity
{
	public SpawnPoint()
	{
		
	}
	
	public SpawnPoint(int x, int y)
	{
		super(16,x, y);
		setSprite(Sprite.signPost);
	}
}
