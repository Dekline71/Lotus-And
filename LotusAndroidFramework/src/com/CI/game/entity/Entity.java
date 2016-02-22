package com.CI.game.entity;

import java.util.ArrayList;
import java.util.LinkedList;

import android.graphics.Rect;

import com.CI.game.SampleGame;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.entity.buildings.BuildingEntity;
import com.CI.game.entity.enemy.Enemy;
import com.CI.game.entity.resource.Resource;
import com.CI.game.graphics.Background;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.Level;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Screen;

public class Entity 
{
	protected int center32X;// pos on screen by 16x16 tilemap cordinate
	protected int center32Y;
	protected int center64X;
	protected int center64Y;
	protected int pixel32X, pixel32Y, pixel64X, pixel64Y;// store pos on screen in pixels
	protected int health;

	protected Entity target1;// target entity node for pathfinding
	protected Entity target2;// target entity node for pathfinding
	protected Level level;// instance of level entity exists in
	//private Resource resource;// create bag data structure that holds one type of res at a time 
	
	public static ArrayList<ArmyUnit> playerArmyEntities = new ArrayList<ArmyUnit>(); 
	public static ArrayList<Enemy> enemyArmyEntities = new ArrayList<Enemy>(); 
	public static ArrayList<BuildingEntity> playerBuildingEntities = new ArrayList<BuildingEntity>(); 
	
	private Sprite sprite;
	private Image image;
	
	private boolean isTargetFound = false;
	protected boolean isVisible;
	private boolean removed = false;// has it been removed from level?
	
	public Rect colliderBounds = new Rect(0, 0, 0, 0);
	public Rect atkColliderBounds = new Rect(0, 0, 0, 0);
	private int startX;
	private int startY;
	
	private int posInLink;
	
	// Deep Copy constructor
	public Entity(Entity e)
	{
		this.target2 = null;
		this.target1 = null;
		this.health = e.getHealth();
		this.center32X = e.getCenter32X();
		this.center32Y = e.getCenter32Y();
		this.center64X = e.getCenter64X();
		this.center64Y = e.getCenter64Y();
		
		//this.setStartX(this.center64X);
		//this.setStartY(this.center64Y);
		
		this.pixel32X = e.getPixel32X();
		this.pixel32Y = e.getPixel32Y();
		this.pixel64X = e.getPixel64X();
		this.pixel64Y = e.getPixel64Y();
		isVisible = e.isVisible();
	}
	
	public Entity(int x, int y)
	{
		this.target2 = null;
		this.target1 = null;
		this.health = 0;
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		
		this.setStartX(this.center64X);
		this.setStartY(this.center64Y);
		
		this.pixel32X = getCenter32X() * 32;
		this.pixel32Y = getCenter32Y() * 32;
		this.pixel64X = getCenter64X() * 64;
		this.pixel64Y = getCenter64Y() * 64;
		isVisible = true;
	}
	
	public Entity(int h, int x, int y)
	{
		this.target1 = null;
		this.target2 = null;
		this.health = h;
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		this.setStartX(x / 2);
		this.setStartY(y / 2);
		this.pixel32X = getCenter32X() * 32;
		this.pixel32Y = getCenter32Y() * 32;
		this.pixel64X = getCenter64X() * 64;
		this.pixel64Y = getCenter64Y() * 64;
		isVisible = true;
	}
	
	public Entity(int x, int y, boolean isVis)
	{
		setCenter64X(x);
		setCenter64Y(y);
		isVisible = isVis;
	}
	
	public Entity()
	{
		
	}
	
	public void update() 
	{


	}
	
	public void spawnEntity()
	{
		
	}
		
	public static void nullLists()
	{	
		getEnemyArmyEntities().clear();
		getPlayerArmyEntities().clear();
		getPlayerBuildingEntities().clear();
	}
	
	// independent render from paint() in GameScreen
	public void render(Camera screen)
	{
	
	}

	private void checkCollision() 
	{

	}

	public void follow() 
	{


	}
	
	public void attack()
	{
		
	}

	public void die() 
	{
		//setVisible(false);
	}
	
	public int getPosInLink()
	{
		return this.posInLink;
	}

	public int getCenter32X() 
	{
		return this.center32X;
	}

	public int getCenter32Y() 
	{
		return this.center32Y;
	}
	
	public int getCenter64X() 
	{
		return this.center64X;
	}

	public int getCenter64Y() 
	{
		return this.center64Y;
	}
	
	public int getPixel64X()
	{
		return this.pixel64X;
	}
	
	public int getPixel64Y()
	{
		return this.pixel64Y;
	}
	
	public Sprite getSprite()
	{
		return this.sprite;
	}
	
	public int getPixel32X()
	{
		return this.pixel32X;
	}
	
	public int getPixel32Y()
	{
		return this.pixel32Y;
	}

	public boolean getisTargetFound()
	{
		return isTargetFound;
	}
	
	public void setVisible(boolean b)
	{
		this.isVisible = b;
	}
	
	public void setisTargetFound(boolean b)
	{
		this.isTargetFound = b;
	}
	
	public void setPower(int power) 
	{
		
	}	
	
	public void setSprite(Sprite s)
	{
		this.sprite = s;
	}

	public void setSpeedX(int speedX) 
	{
		
	}
	
	public void setPixel32X(int x) 
	{
		this.pixel32X = x;
	}

	public void setPixel32Y(int y) 
	{
		this.pixel32Y = y;
	}
	
	public void setPixel64X(int x) 
	{
		this.pixel64X = x;
	}

	public void setPixel64Y(int y) 
	{
		this.pixel64Y = y;
	}

	public void setCenter32X(int centerX) 
	{
		this.center32X = centerX;
	}

	public void setCenter32Y(int centerY) 
	{
		this.center32Y = centerY;
	}
	
	public void setCenter64X(int centerX) 
	{
		this.center64X = centerX;
	}

	public void setCenter64Y(int centerY) 
	{
		this.center64Y = centerY;
	}

	public void setBg(Background bg) 
	{
		
	}
	
	public void setHealth(int i)
	{
		this.health += i;
	}
	
	public void setImage(Image i)
	{
		this.image = i;
	}
	
	public void init(Level level)
	{
		this.level = level;
	}
	
	public static ArrayList getPlayerArmyEntities()
	{
		return playerArmyEntities;
	}
	
	public static ArrayList getEnemyArmyEntities()
	{
		return enemyArmyEntities;
	}
	
	public static ArrayList getPlayerBuildingEntities()
	{
		return playerBuildingEntities;
	}

	public boolean isVisible() 
	{
		return isVisible;
	}

	public Image getImage() 
	{
		return this.image;
	}
	
	public Entity getTarget()
	{
		return this.target1;
	}
	
	public Entity getTarget2()
	{
		return this.target2;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public Level getLevel() 
	{
		return this.level;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public void setTarget(Entity t)
	{
		this.target1 = t;
	}
	
	public void setPosInLink(int i)
	{
		this.posInLink = i;
	}

}
