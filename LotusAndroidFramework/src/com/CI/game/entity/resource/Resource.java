package com.CI.game.entity.resource;

import com.CI.game.entity.Entity;

public class Resource extends Entity
{
	private String tag;
	private int amount;
	
	public Resource()
	{
		
	}
	
	public Resource(int x, int y)
	{
		super(0, x, y);
	}
}
