package com.CI.game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.CI.game.entity.Entity;
import com.CI.game.entity.SpawnPoint;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.entity.buildings.Barrack;
import com.CI.game.entity.enemy.Enemy;
import com.CI.game.graphics.GameScreen;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.Level;
import com.CI.game.level.tile.Tile;

public class GameManager 
{
	public static int gold;// players total gold
	private static int xp;// players total experience gained
	static Enemy enemy;
	static Barrack barrack;
	static Tile tileMap[][];
	String mapPath;
	public static Level level;
	public static int totalTurns; // total turns player has played during this game
	public static int curTurn;
	public static int prevTurn;
	
	public GameManager()
	{
		tileMap = GameScreen.tileMap;
		setGold(0);
		xp = 0;
		curTurn = prevTurn = totalTurns = 1;
		mapPath = SampleGame.map;
	}
	
	public GameManager(Level l)
	{
		this.level = l;
		setGold(100);
	}
	
	public static void spawn()
	{
		level.spawn();
	}
	
	public static void update()
	{
		if(level != null)
		{
			spawn();
		}
	}
	
	public Level getLevel()
	{
		return this.level;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
}
