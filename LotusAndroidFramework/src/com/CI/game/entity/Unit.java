package com.CI.game.entity;

public class Unit extends NPC
{
	protected int direction;
	protected boolean moving = false;
	protected boolean walking = false;
	
	protected enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}
	
	public void update()
	{
		move(getCenter32X(), getCenter32Y());
	}
	
	public void move(int xAxis, int yAxis)
	{
		if(xAxis != 0 && yAxis != 0)// if on axis
		{
			// run for each axis, for seperate collision
			move(xAxis,0);
			move(0,yAxis);
			return;
		}
		if (xAxis > 0){direction = 1;}// Right
		if (xAxis < 0){direction = 3;}// Left
		if (yAxis > 0){direction = 2;}// Down
		if (yAxis < 0){direction = 0;}// Up
		
		/*
		if(!collision(xAxis, yAxis))// if no collision, move in direction
		{				
			x += xAxis;
			y += yAxis;
		}
		else 
		{
			
			//Sound.hit.play();
			
		}
		*/
	}
	
	/*
	
	private boolean collision(int xAxis, int yAxis)// check future positions
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = ((x+xAxis) + c % 2 * 12 - 7) / 16;
			int yt = ((y+yAxis)+ c / 2 * 12 + 3) / 16;
		
		if (level.getTile(xt, yt).solid()){ solid = true;}// check future x,y locations for solids
		}
		return solid;
	}
	
	*/
	

}
