package com.CI.game.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.CI.game.GameManager;
import com.CI.game.SampleGame;
import com.CI.game.entity.Camera;
import com.CI.game.entity.Entity;
import com.CI.game.entity.Player;
import com.CI.game.entity.SpawnPoint;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.entity.buildings.Barrack;
import com.CI.game.entity.buildings.BuildingEntity;
import com.CI.game.entity.buildings.CastleWallV;
import com.CI.game.entity.enemy.Enemy;
import com.CI.game.graphics.Assets;
import com.CI.game.graphics.GameScreen;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Graphics;

public class Level 
{
	private int width;// width and height of level in pxls
	private int height;
	protected static Tile[] tilesInt;// tileIDs/Index
	protected int [] tiles; // numeric char value identifier of tile in level
	//public static Level spawn = new Level();
	Enemy enemy;
	static Barrack barrack = new Barrack(Sprite.barrack, 6, 6);// should seperate building entity list;
	// Data structures for entities
	private LinkedList<ArmyUnit> playerArmyEntities = new LinkedList<ArmyUnit>(); 
	private LinkedList<Enemy> enemyArmyEntities = new LinkedList<Enemy>(); 
	private LinkedList<BuildingEntity> playerBuildingEntities = new LinkedList<BuildingEntity>(); 
	
	//private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	String mapPath;

	private TileMap buildingTileMap; // grid struct for building entities
	private TileMap armyMovementTileMap; // grid struct for npc/soldier entities
	private int aMMW; // Army Movement Map Width
	private int aMMH; // Army Movement Map Height
	
	private int tileMapXSize, tileMapYSize;
	
	private BuildingEntity selectedBuilding;
	private int barrackX;
	private int barrackY;
	
	private Player player;
	public boolean isUI;
	private boolean uiPaneDrawTrig;
	private Entity selectedEntity = new Entity();
	private boolean isEntitySelected = false;
	private boolean isPlayerEntitySelected = false;
	private boolean isTouchingDown = false;
	private boolean isEnemyEntitySelected = false;
	
	
	public static int brownUIx = 50;
	public static int brownUIy = 8;

	public static int UIxItem_1 = 48;
	public static int UIyItem_1 = 128;
	public static int UIyItem_2 = 128;
	public static int UIxItem_2 = 16;
	
	public Level ()
	{
		mapPath = SampleGame.map;
		
		
		this.setHeight(Assets.levelMap.getHeight());
		this.setWidth(Assets.levelMap.getWidth());
		System.out.println("Width: " + this.getWidth() + " Height: " + this.getHeight());
	//	this.setHeight(48);
		//this.setWidth(60);
	
		tiles = new int [this.getWidth() * this.getHeight()];
		Assets.levelMap.getBitmap().getPixels(this.tiles, 0, getWidth(), 0, 0, getWidth(), getHeight());

		int x, y;
		x = 0;
		y = 23;
		System.out.println("H: " + getHeight() + "W: " + getWidth());
		System.out.println( Integer.toHexString(tiles[x + (y*getWidth())]));
		if("ff267f00".equals(Integer.toHexString(tiles[x + (y*getWidth())])))
		{
			//System.out.println(Integer.toHexString(getTile(x,y).getSprite().getPixels()[1]));
			System.out.println(Integer.toHexString(tiles[1]));

			System.out.println("Poo: " );
			System.out.println(Tile.grassTile_1.getSprite().getSIZE());
		}
		
	// normal buildingtilemap 32x32	
	// V V  	
		tilesInt = new Tile [this.getWidth() * this.getHeight()];// size of map int value for tile indexes of map
		this.buildingTileMap = new TileMap(this.getWidth(), this.getHeight());
	// ^ ^ 	
		for(int yy = 0; yy < this.getHeight(); yy++)
		{
			for(int xx = 0; xx < this.getWidth(); xx ++ )
			{
				tilesInt[xx + (yy * this.getWidth())] = getTile(xx, yy);
				
				this.buildingTileMap.getTileGrid()[xx][yy] = new Tile(xx, yy);
				//this.getTileMap().setTileGrid(this.getTileMap().getTileGrid());
			}
		}
		
		//this.buildingTileMap.initClusters();

		
		
	// 64x64 px army movement tilemap
	this.aMMW = this.getWidth() / 2; // Army Movement Map Width	
	this.aMMH = this.getHeight() / 2;
	this.armyMovementTileMap = new TileMap(aMMW, aMMH);	
	
	for(int yy = 0; yy < this.aMMH ; yy++)
	{
		for(int xx = 0; xx < this.aMMW; xx++ )
		{
			//tilesInt[xx + (yy * this.getWidth())] = getTile(xx, yy);
			
			this.armyMovementTileMap.getTileGrid()[xx][yy] = new Tile(xx, yy);
			//this.getTileMap().setTileGrid(this.getTileMap().getTileGrid());
		}
	}
		
		//loadLevel(mapPath);
		generateLevel();
	}
	
	public Level(String path)
	{
		//loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel()
	{
		barrack = new Barrack(Sprite.barrack, 12, 12);// should separate building entity list

		//this.getTileMap().setTileGrid(tileMap.getTileGrid());
		this.barrackX = barrack.getCenter32X();
		this.barrackY = barrack.getCenter32Y();
		getPlayerBuildingEntities().add(barrack);
		
	
		this.getBuildingTileMap().getTileGrid()[13][12].setSolid(true); 
		this.getBuildingTileMap().getTileGrid()[12][12].setSolid(true); 
		this.getBuildingTileMap().getTileGrid()[13][13].setSolid(true);
		this.getBuildingTileMap().getTileGrid()[12][13].setSolid(true); 
		
		//this.getArmyMovementTileMap().getTileGrid()[6][6].setSolid(true);

		
		// add wall to tileMap for solid colli on Ai
		/*Tile t;
		t = new Tile(barrack.getCenterX(), barrack.getCenterY());
		t.setSolid(true);
		tileMap.getTileGrid()[barrack.getCenterX()][barrack.getCenterY()] = t;
		
		for(int i = 1; i <= 2; i++)
		{
			int rX = barrack.getCenterX() + i;
			if(rX < 60 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
			{
				t = new Tile(rX, barrack.getCenterY());// right x tile
				t.setSolid(true);
				tileMap.getTileGrid()[rX][barrack.getCenterY()] = t;
			}
			int dY = barrack.getCenterY() + i;
			if(dY < 48 && dY >= 0 && rX < 60)// right down tile
			{				
				t = new Tile(rX,dY);
				t.setSolid(true);
				tileMap.getTileGrid()[rX][dY] = t;
			}
			int ndY = barrack.getCenterY() + i;
			if(ndY < 48 && ndY >= 0)// down
			{
				t = new Tile(barrack.getCenterX(), ndY);
				t.setSolid(true);
				tileMap.getTileGrid()[barrack.getCenterX()][ndY] = t;
			}
		}*/
	}
	
	protected void loadLevel(String path)
	{
		
	
	}
	
	public void render(int xScroll, int yScroll, Camera screen, Graphics g)
	{
		System.out.println("xScroll: " + xScroll);
		//screen.setOffset(xScroll, yScroll);
		if(xScroll < 0 || xScroll > 1300) xScroll = 0;
		if(yScroll < 0 || yScroll > 1096) yScroll = 0;
		screen.setOffset(xScroll, yScroll);
		this.getPlayer().xScroll = xScroll;
		this.getPlayer().yScroll = yScroll;

		int mapX = screen.xOffset / 32;
		int mapY = screen.yOffset / 32;
		int mapXoffset = xScroll % 32;
		int mapYoffset = yScroll % 32;
		
		for(int y = 0; y < this.getHeight(); y++)// 15(32x32)
		{
			for(int x = 0; x < this.getWidth(); x++)// 25(32x32)
			{
				// draw & get tile

				if( (((mapX + x) + 6) + ((mapY + y) * this.getWidth()) < tilesInt.length && this.getPlayer().isPanelOn() && xScroll == 1120))// if greater than total index
				{
				    g.drawTile(tilesInt[((mapX + x) + 6) + ((mapY + y) * this.getWidth())], ((x * 32) - mapXoffset) + 200 , (( y * 32 ) - mapYoffset) + 32);// draw tile to main canvas bitmap to be displayed in frame
				}
				else if( ((mapX + x) + ((mapY + y) * this.getWidth()) < tilesInt.length && this.getPlayer().isPanelOn()))// if greater than total index
				{
					//Tile t = tilesInt[(mapX + x) + ((mapY + y) * this.getWidth())];
				    g.drawTile(tilesInt[(mapX + x) + ((mapY + y) * this.getWidth())], ((x * 32 ) - mapXoffset) + 200, (( y * 32 ) - mapYoffset) + 32);// draw tile to main canvas bitmap to be displayed in frame
				 //tileMap.getTileGrid()[mapX + x][mapY + y];
					//g.drawRect( (GameManager.level.getPlayer().getCenterX() * 32) - mapXoffset, ((GameManager.level.getPlayer().getCenterY()) * 32 ) - mapYoffset, 16, 16, Color.RED);
				    
				}
				else if( ((mapX + x) + ((mapY + y) * this.getWidth()) < tilesInt.length && !this.getPlayer().isPanelOn()))// if greater than total index
				{
				    g.drawTile(tilesInt[(mapX + x) + ((mapY + y) * this.getWidth())], ((x * 32 ) - mapXoffset), (( y * 32 ) - mapYoffset) + 32);// draw tile to main canvas bitmap to be displayed in frame

				}
				 
				 // **Need to create another list for ai/pathfinding
				 // Tile tilemap[x][y] = new Tile(sprite, x * 16, y * 16)
				 
				 // g.drawTile(t, (xScroll * 16) - mapXoffset, (yScroll * 16 ) - mapYoffset);// draw tile to main canvas bitmap to be displayed in frame
  
				//tiles[x + (y* 50], xScroll * 16 - mapXoffset, yScroll * 16 = mapYoffset);
			}
	
		 }
		
		//drawGUI(g);// stone background gui elements on screen
		renderCombatGrid(g);
		g.saveTileFrame();
	    // render Entities	
		for(int i = 0; i < playerBuildingEntities.size(); i++)
		{
			if(playerBuildingEntities.get(i).isVisible())
			{			
				playerBuildingEntities.get(i).render(screen);
			}
			//if (playerBuildingEntities.get(i).isBarrack())// if building is a barrack
		}
				
		if( isTouchingDown())
		{
			drawGridPointers(screen, g);
		}

		
		for(int i = 0; i < playerArmyEntities.size(); i++)
		{
			if(playerArmyEntities.get(i).isVisible())
			{
				playerArmyEntities.get(i).render(screen);
			}
		}	

		
		for(int i = 0; i < enemyArmyEntities.size(); i++)
		{
			if(enemyArmyEntities.get(i).isVisible())
			{
				enemyArmyEntities.get(i).render(screen);
			}
		}

		drawGUI(g);// stone background gui elements on screen

	}
	
	private void renderCombatGrid(Graphics g) 
	{
		if(!GameManager.level.getPlayer().isPanelOn() && this.isEntitySelected())	
		{
			g.drawImage(Assets.combatModeGridWide, 0, 32);	
		}
		else if (GameManager.level.getPlayer().isPanelOn() && this.isEntitySelected())
		{
			g.drawImage(Assets.combatModeGrid, 200, 32);
		}		
	}

	public boolean isEntitySelected() 
	{
		return this.isEntitySelected;
	}
	
	public Entity getSelectedEntity()
	{
		return this.selectedEntity;
	}

	public void drawGUI(Graphics g) 
	{
		g.drawImage(Assets.resWidget, 0, 0);// draw stone ui	
		g.drawImage(Assets.resPane, 200, 0);// draw stone ui	
		
		drawHUD(g);
		drawPane(g);
	}
	
	public void drawHUD(Graphics g)
	{
		int cGold;
		int cTurn;
		String s = new String();
		
		//draw current turn/day
		g.drawImage(Sprite.daysText.getImage(), 45, -10);
		cTurn = GameManager.curTurn;
		s = String.valueOf(cTurn);
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '0')
			{
				g.drawImage(Sprite.num_0.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if (c == '1')
			{
				g.drawImage(Sprite.num_1.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '2')
			{
				g.drawImage(Sprite.num_2.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '3')
			{
				g.drawImage(Sprite.num_3.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '4')
			{
				g.drawImage(Sprite.num_4.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if (c == '5')
			{
				g.drawImage(Sprite.num_5.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if (c == '6')
			{
				g.drawImage(Sprite.num_6.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '7')
			{
				g.drawImage(Sprite.num_7.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '8')
			{
				g.drawImage(Sprite.num_8.getImage(), (5*16 + 20) + (16 * i), 15);
			}
			else if(c == '9')
			{
				g.drawImage(Sprite.num_9.getImage(), (5*16 + 20) + (16 * i), 15);
			}
		}
		
		
		// draw current gold
		g.drawImage(Sprite.gold.getImage(), (27*16) + (s.length() * 16), 0);
		cGold =  GameManager.gold;
		s = String.valueOf(cGold);
		g.drawSprite(Sprite.goldText, 20*16, 2);
			for(int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				if(c == '0')
				{
					g.drawImage(Sprite.num_0.getImage(), (23*16) + (16 * i), 6);
				}
				else if (c == '1')
				{
					g.drawImage(Sprite.num_1.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '2')
				{
					g.drawImage(Sprite.num_2.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '3')
				{
					g.drawImage(Sprite.num_3.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '4')
				{
					g.drawImage(Sprite.num_4.getImage(), (23*16) + (16 * i), 6);
				}
				else if (c == '5')
				{
					g.drawImage(Sprite.num_5.getImage(), (23*16) + (16 * i), 6);
				}
				else if (c == '6')
				{
					g.drawImage(Sprite.num_6.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '7')
				{
					g.drawImage(Sprite.num_7.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '8')
				{
					g.drawImage(Sprite.num_8.getImage(), (23*16) + (16 * i), 6);
				}
				else if(c == '9')
				{
					g.drawImage(Sprite.num_9.getImage(), (23*16) + (16 * i), 6);
				}
			}
			
	}
	
	public void tileEntityPointerDragged(int x, int y)
	{
		setIsTouchingDown(true);
		if(this.selectedEntity!= null)
		{
//		this.selectedEntity = new Entity();
		this.selectedEntity.setCenter64X(x);
		this.selectedEntity.setCenter64Y(y);
		System.out.println("X: " + x + "Y: " + y);
		}
		
			//setEnemyEntitySelected(false);
		
	}
	
	public void tileEntityPointer(int x, int y)
	{
		setIsTouchingDown(true);
		
//		this.selectedEntity = new Entity();
		if(this.selectedEntity!= null)
		{
			this.selectedEntity.setCenter64X(x);
			this.selectedEntity.setCenter64Y(y);
			System.out.println("X: " + x + "Y: " + y);
		}			
		//System.out.println("Selected entity.");
		
		//System.out.println("X: " + ((event.x  / 32) + (screen.xOffset / 32 ) + "Y: " + ((event.y / 32) + (screen.yOffset / 32))));
		for(int i = 0; i < playerArmyEntities.size(); i++)
		{
			if(playerArmyEntities.get(i).getCenter64X() == x && playerArmyEntities.get(i).getCenter64Y() == y)
			{
				// set entity to selected to be controlled.
				this.selectedEntity = new Entity(playerArmyEntities.get(i));
				this.setPlayerEntitySelected(true);
				this.setEnemyEntitySelected(false);
				//this.selectedEntity.setCenter64X(x);
				//this.selectedEntity.setCenter64Y(y);
				//this.selectedEntity = playerArmyEntities.get(i);
				this.selectedEntity.setPosInLink(i);
				setIsEntitySelected(true);
				System.out.println("Selected player entity.");
				break;
				
			}
			else
			{
				this.setIsEntitySelected(false);
				this.setPlayerEntitySelected(false);
			}
		}
		
		if(!isEntitySelected() && isTouchingDown())
		{
			for(int i = 0; i < enemyArmyEntities.size(); i++)
			{
				if(enemyArmyEntities.get(i).getCenter64X() == x && enemyArmyEntities.get(i).getCenter64Y() == y)
				{
					// set entity to selected to be controlled.
					this.selectedEntity = new Entity(enemyArmyEntities.get(i));
					//this.selectedEntity.setCenter64X(x);
					//this.selectedEntity.setCenter64Y(y);
					//this.selectedEntity = enemyArmyEntities.get(i);
					this.selectedEntity.setPosInLink(i);
					this.setPlayerEntitySelected(false);
					this.setEnemyEntitySelected(true);

					this.setIsEntitySelected(true);
					System.out.println("Selected enemy entity. " + this.selectedEntity.getPosInLink());
					break;
					
				}
				else
				{
					//this.selectedEntity = null;
					this.setIsEntitySelected(false);
					this.setEnemyEntitySelected(false);

					
				}
			}
		}
		
		
		/*for(int i = 0; i < enemyArmyEntities.size(); i++)
		{
			if(enemyArmyEntities.get(i).getCenter64X() == x && enemyArmyEntities.get(i).getCenter64Y() == y)
			{
				enemyArmyEntities.get(i).render(screen);
			}
		}
		*/
	}
	
	private void setEnemyEntitySelected(boolean b) 
	{
		this.isEnemyEntitySelected = b;
	}

	public void setIsEntitySelected(boolean b) 
	{
		this.isEntitySelected = b;		
	}

	public void drawPane(Graphics g)
	{		
		int buildingUIx = 16;
		int buildingUIy = 64;
		int militaryUIx = 48;
		int militaryUIy = 64;
		
		
		if(GameManager.level.getPlayer().isPanelOn() && GameManager.level.isEntitySelected())
		{
			g.drawImage(Assets.resPanel, 0, 42);
		}
		else if(GameManager.level.getPlayer().isPanelOn() && !GameManager.level.isEntitySelected())
		{
			g.drawImage(Assets.resPanel, 0, 42);
			g.drawImage(Assets.buildingIcon, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon, militaryUIx, militaryUIy);
		}
		
		if (this.getPlayer().isBuildingSelected() || this.getPlayer().isPanelOn() && this.getPlayer().isBuildingSelected())
		{
			//g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawSprite(Sprite.castleWall, UIxItem_1, UIyItem_1 );
			//g.drawSprite(Sprite.stoneWallV, UIxItem_1 , UIyItem_1);
			//g.drawSprite(Sprite.castleWall, UIxItem_2, UIyItem_2);
			g.drawImage(Assets.buildingIcon_select, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon, militaryUIx, militaryUIy);

		}
		else if(this.getPlayer().isMilitarySelected() && this.getPlayer().isPanelOn() && this.getPlayer().isMilitarySelected())
		{
			//g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawSprite(Sprite.peasSpriteD, UIxItem_1, UIyItem_1 );
			g.drawImage(Assets.buildingIcon, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon_select, militaryUIx, militaryUIy);
		}
		else if (this.isPlayerEntitySelected() && this.getPlayer().isPanelOn())
		{
			g.drawSprite(Sprite.num_0, 25, 75);
			// create draw method for cleanlieness
		}
		else
		{
			this.getPlayer().setBuildingSelected(false);
			this.getPlayer().setMilitarySelected(false);
		}
	}

	public void renderEntitiesOnSavedFrame(Graphics g, Camera screen)
	{	
		// draw last fame of x/y tile map scroll on canvas
				g.drawSavedTileFrame();
				//drawGUI(g);
		// render Entities	
				renderCombatGrid(g);
			for(int i = 0; i < playerBuildingEntities.size(); i++)
			{
				if(playerBuildingEntities.get(i).isVisible())
				{			
					playerBuildingEntities.get(i).render(screen);
				}
				//if (playerBuildingEntities.get(i).isBarrack())// if building is a barrack
			}				
			if(isTouchingDown())
			{
				drawGridPointers(screen, g);
			}


			for(int i = 0; i < playerArmyEntities.size(); i++)
			{
				if(playerArmyEntities.get(i).isVisible())
				{
					playerArmyEntities.get(i).render(screen);
				}
			}
				
			for(int i = 0; i < enemyArmyEntities.size(); i++)
			{
				if(enemyArmyEntities.get(i).isVisible())
				{
					enemyArmyEntities.get(i).render(screen);
				}
			}
			

			drawGUI(g);// stone background gui elements on screen
	}

	private void drawGridPointers(Camera screen, Graphics g)
	{

		//System.out.println("X: " + x + " Y: " + y);
		if(this.selectedEntity != null)
		{
				int x = (((this.selectedEntity.getCenter64X() * 64) - screen.xOffset + (screen.xOffset % 64)));
				int y = (((this.selectedEntity.getCenter64Y() * 64) - screen.yOffset + (screen.yOffset % 64)+32));
				g.drawImage(Assets.gridHorPointer, 0, y);
				g.drawImage(Assets.gridVerPointer, x, 32);
				//if(isEntitySelected())
					
				g.drawImage(Sprite.selectedEntityPoint.getImage(), x - 13, y - 13);	
		}	

	}
	
	public void spawn()
	{
		Random r = new Random();
		int rndYPos= r.nextInt(18);
		SpawnPoint spawn = new SpawnPoint(1, rndYPos);
		enemy = new Enemy(1, rndYPos, this);
		this.enemyArmyEntities.add(enemy);
		
	}
	
	public void updateMovement()
	{
		LinkedList enemyEntities = this.enemyArmyEntities;
		
		for(int i = 0; i < enemyEntities.size(); i++)
		{
			Enemy e = (Enemy) enemyEntities.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				//e.moveThruPath();
				e.setCanMove(true);
			}
	
		}
		
		LinkedList playerEntities = this.playerArmyEntities;
		
		for(int i = 0; i < playerEntities.size(); i++)
		{
			ArmyUnit e = (ArmyUnit) playerEntities.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				//e.moveThruPath();
				e.setCanMove(true);
			}
	
		}
	}
	
	public void update()
	{
		//spawn();
		if(!getPlayerBuildingEntities().contains(barrack))//check after restart/from game menu
		{
			getPlayerBuildingEntities().add(barrack);
			System.out.println("fuck");
		}
		
		LinkedList enemyEntities = this.enemyArmyEntities;
		
		for(int i = 0; i < enemyEntities.size(); i++)
		{
			Enemy e = (Enemy) enemyEntities.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				e.update();
			}
			else
			{
				enemyEntities.remove(i);
			}
		}
		
		LinkedList plyrEntities = this.playerArmyEntities;
		for(int i = 0; i < plyrEntities.size(); i++)
		{
			ArmyUnit e = (ArmyUnit) plyrEntities.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				e.update();
			}
			else
			{
				plyrEntities.remove(i);
			}
		}
		
		LinkedList plyrBuildingEntities = this.playerBuildingEntities;
		for(int i = 0; i < plyrBuildingEntities.size(); i++)
		{
			BuildingEntity e = (BuildingEntity) plyrBuildingEntities.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				e.update();
			}
			else
			{
				plyrBuildingEntities.remove(i);
			}
		}
	}
	
	
	
	private void remove()
	{
		
	}
	
	public boolean tileCollision(double x, double y, double xAxis, double yAxis, int size)// x & y will be position of entity, xa/xa is dir its heading
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = (((int)x + (int)xAxis) + c % 2 * size * 2 - 12) / 32;
			int yt = (((int)y + (int)yAxis) + c / 2 * size + 2 ) / 32;
			
			if (getTile( xt, yt).solid()) solid = true;// check future x/y locations for solids
		
		
		}
		return solid;
	}
	
	public void add(Entity e)
	{
		e.init(this);
		/*if (e instanceof Particle)
		{
			particles.add((Particle) e);
		}
		else if(e instanceof Projectile)
		{
			projectiles.add((Projectile) e);
		}
		else
		{
			entities.add(e);
		}
		*/

	}
	
	public void addNPC(Entity n)
	{
		//npcs.add(n);
	}
	
	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	public Tile getTile(int x, int y)
	{		
		//System.out.println(Integer.toHexString(tiles[x + (y*width)]));
		if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight()) return Tile.voidTile;
		if ("ff15ff00".equals(Integer.toHexString(this.tiles[x + (y*this.getWidth())]))) return Tile.grass;
		//if (Integer.toHexString(this.tiles[x + (y * this.getWidth())]).equals(Tile.col_dirt)) return Tile.grass;
		if ("ffffd800".equals(Integer.toHexString(this.tiles[x + (y*this.getWidth())]))) return Tile.sand;
		//if (Integer.toHexString(this.tiles[x + (y * this.getWidth())]).equals(Tile.col_dirt)) return Tile.sand;
		if (Integer.toHexString(this.tiles[x + (y * this.getWidth())]).equals(Tile.col_shrub)) return Tile.shrub;

		//if (tiles[x + y * width] == Tile.col_spawn_floor)return Tile.spawn_floor;
		//if (tiles[x + y * width] == Tile.col_spawn_grass)return Tile.spawn_grass;
		//if (tiles[x + y * width] == Tile.col_spawn_hedge)return Tile.spawn_hedge;
		//if (tiles[x + y * width] == Tile.col_spawn_wall1)return Tile.spawn_wall1;
		//if (tiles[x + y * width] == Tile.col_spawn_wall2)return Tile.spawn_wall2;
		//if (tiles[x + y * width] == Tile.col_spawn_water)return Tile.spawn_water;
			
		return Tile.voidTile;
		//return getTilesInt()[x+y];
	}
	
	public void nullLevel()
	{	
		getEnemyArmyEntities().clear();
		getPlayerArmyEntities().clear();
		getPlayerBuildingEntities().clear();
	}
	
	public LinkedList<ArmyUnit> getPlayerArmyEntities()
	{
		return this.playerArmyEntities;
	}
	
	public LinkedList<Enemy> getEnemyArmyEntities()
	{
		return this.enemyArmyEntities;
	}
	
	public LinkedList<BuildingEntity> getPlayerBuildingEntities()
	{
		return this.playerBuildingEntities;
	}

	public Tile[] getTilesInt() 
	{
		return this.tilesInt;
	}

	public void setTilesInt(Tile[] tilesInt)
	{
		this.tilesInt = tilesInt;
	}
	
	public void setSelectedBuilding(BuildingEntity e)
	{
		this.selectedBuilding = e;
	}
	
	public BuildingEntity getSelectedBuilding()
	{
		return this.selectedBuilding;
	}

	public TileMap getBuildingTileMap() 
	{
		return this.buildingTileMap;
	}
	
	public TileMap getArmyMovementTileMap() 
	{
		return this.armyMovementTileMap;
	}

	public void setTileMap(TileMap t) 
	{
		this.buildingTileMap = t;
	}

	public int [] getTiles() 
	{
		return tiles;
	}

	public void setTiles(int [] tiles) 
	{
		this.tiles = tiles;
	}

	public int getTileMapXSize() 
	{
		return tileMapXSize;
	}

	public void setTileMapXSize(int tileMapXSize) 
	{
		this.tileMapXSize = tileMapXSize;
	}

	public int getTileMapYSize() 
	{
		return tileMapYSize;
	}

	public void setTileMapYSize(int tileMapYSize) 
	{
		this.tileMapYSize = tileMapYSize;
	}

	public void setHeight(int h) 
	{
		this.height = h;		
	}

	public void setWidth(int w) 
	{
		this.width = w;
	}

	public int getHeight() 
	{
		return this.height;
	}

	public int getWidth() 
	{
		return this.width;
	}

	public int getBarrackX() 
	{
		return this.barrackX;
	}
	
	public int getBarrackY()
	{
		return this.barrackY;
	}

	public void setPlayer(Player p) 
	{
		this.player = p;
	}

	public Player getPlayer() 
	{
		return this.player;
	}

	public boolean isUiPaneDrawTrig() 
	{
		return this.uiPaneDrawTrig;
	}

	public void setUiPaneDrawTrig(boolean uiPaneDrawTrig) 
	{
		this.uiPaneDrawTrig = uiPaneDrawTrig;
	}

	public boolean isTouchingDown() {
		return isTouchingDown;
	}

	public void setIsTouchingDown(boolean isTouchingDown) {
		this.isTouchingDown = isTouchingDown;
	}

	public boolean isPlayerEntitySelected() {
		return isPlayerEntitySelected;
	}

	public void setPlayerEntitySelected(boolean isPlayerEntitySelected) {
		this.isPlayerEntitySelected = isPlayerEntitySelected;
	}

	public void movePlayerEntity() 
	{
		int x = GameManager.level.selectedEntity.getCenter64X();
		int y = GameManager.level.selectedEntity.getCenter64Y();
		boolean isSpaceOccupied = false;
		for(int i = 0; i < enemyArmyEntities.size(); i++)
		{
			if(enemyArmyEntities.get(i).getCenter64X() == x && enemyArmyEntities.get(i).getCenter64Y() == y)
			{
				isSpaceOccupied = true;
				break;
			}
			else
			{
				isSpaceOccupied = false;
			}
		}
		
		if(!isSpaceOccupied)
		{
			for(int i = 0; i < playerArmyEntities.size(); i++)
			{
				if(playerArmyEntities.get(i).getCenter64X() == x && playerArmyEntities.get(i).getCenter64Y() == y)
				{
					isSpaceOccupied = true;
					break;
				}
				else
				{
					isSpaceOccupied = false;
				}
		
			}
		}
		
		if(playerArmyEntities.get(this.selectedEntity.getPosInLink()).doesEntityHaveGridMoves() && !isSpaceOccupied)
		{
			this.setIsTouchingDown(false);
			this.setIsEntitySelected(false);
			this.setPlayerEntitySelected(false);
			playerArmyEntities.get(this.selectedEntity.getPosInLink()).setTarget(this.selectedEntity);
			playerArmyEntities.get(this.selectedEntity.getPosInLink()).setisTargetFound(false);
			playerArmyEntities.get(this.selectedEntity.getPosInLink()).setSearched(false);
			this.selectedEntity = null;
		}
		else
		{
			this.setIsTouchingDown(false);
			this.setIsEntitySelected(false);
			this.setPlayerEntitySelected(false);
			this.selectedEntity = null;

		}
	}
	
	public void moveEnemyEntity() 
	{
		int x = GameManager.level.selectedEntity.getCenter64X();
		int y = GameManager.level.selectedEntity.getCenter64Y();
		boolean isSpaceOccupied = false;
		
	
			this.setIsTouchingDown(false);
			this.setIsEntitySelected(false);
			this.setPlayerEntitySelected(false);
			//this.selectedEntity = null;

		
	}

	public boolean checkNotTouchingPlyrEntity() 
	{
		if(this.selectedEntity != null)
		{
			if(playerArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64X() == this.selectedEntity.getCenter64X() && playerArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64Y() == this.selectedEntity.getCenter64Y())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkNotTouchingEnemEntity() 
	{
		if (this.selectedEntity != null)
		{
			if(enemyArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64X() == this.selectedEntity.getCenter64X() && enemyArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64Y() == this.selectedEntity.getCenter64Y())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	

	public boolean isEnemyEntitySelected() 
	{
		
		return this.isEnemyEntitySelected;
	}

	
	/*public void renderSprite(int xPos, int yPos, Sprite sprite, Camera screen, Graphics g)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;
		
		int mapX = xPos * 16;
		int mapY = yPos * 16;
		int mapXoffset = screen.xOffset % 16;
		int mapYoffset = screen.yOffset % 16;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + screen.yOffset;
		int xabsolute = xPos + screen.xOffset;
								
		// draw entity/sprite if between the scroll offset range
		if (screen.xOffset <= xabsolute && screen.yOffset <= yabsolute )
		{
			g.drawImage(sprite.getImage(), (mapX-mapXoffset)-screen.xOffset, (mapY-mapYoffset)-screen.yOffset);
		}
	}*/
	
}
