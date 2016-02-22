package com.CI.game.entity.particle;

import java.util.Random;

import com.CI.game.entity.Entity;
import com.CI.game.graphics.Sprite;

public class Particle extends Entity
{
	private Sprite sprite;
	private int life;
	private int time = 0;
	
	protected double xx, yy,xa,ya; // Amt of pixels it moves in relative axis 
	public Particle(int x, int y, int life)
	{
		super(x,y);
		Random r = new Random();
		int rnd = r.nextInt(20);
		this.life = life + (rnd - 10); 
		this.xx = x;
		this.yy = y;
		//sprite = Sprite.particle_normal;
		this.xa = r.nextGaussian();// returns number between -1 and 1
		this.ya = r.nextGaussian();
	}
	
	public void update()
	{
		time++;
		if(time >= 7400) time = 0;
		if(time > life)
		{
			//remove();
		}
		
		this.xx += xa;
		this.yy += ya;
	}
}
