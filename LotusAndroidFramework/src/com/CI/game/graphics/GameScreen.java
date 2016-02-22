package com.CI.game.graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import android.R.color;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.CI.game.GameManager;
import com.CI.game.MainMenuScreen;
import com.CI.game.SampleGame;
import com.CI.game.entity.Camera;
import com.CI.game.entity.Entity;
import com.CI.game.entity.NPC;
import com.CI.game.entity.Player;
import com.CI.game.entity.Robot;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.entity.armyunit.Sorcerer;
import com.CI.game.entity.buildings.BuildingEntity;
import com.CI.game.entity.buildings.CastleWall;
import com.CI.game.entity.buildings.CastleWallH;
import com.CI.game.entity.buildings.CastleWallV;
import com.CI.game.entity.enemy.Enemy;
import com.CI.game.entity.projectiles.Projectile;
import com.CI.game.entity.resource.Resource;
import com.CI.game.level.Level;
import com.CI.game.level.TileMap;
import com.CI.game.level.tile.GrassTile;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Game;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Screen;
import com.CI.lotusFramework.Input.TouchEvent;
import com.CI.lotusFramework.implementation.AndroidFastRenderView;
import com.CI.lotusFramework.implementation.AndroidGame;
import com.CI.lotusFramework.implementation.AndroidGraphics;
/**
 * All gameplay will take place in this class
 * @author Jake Galletta Conscious Interactive (c) 2014
 *
 */
public class GameScreen extends Screen 
{
	enum GameState 
	{
		Ready, Running, Paused, GameOver, KeepMenu, GameMapMenu, BuildingPrompt
	}

	GameState state = GameState.Ready;

	// Variable Setup
	int xScrll, yScrll; 
	private static Background bg1, bg2;
	private static Robot robot;
	public static NPC hb, hb2;
	//public static Enemy enem = new Enemy(23,20, level);
	private static Resource wood;
	private Level level;
	public int xOffset, yOffset;
	
	private boolean barrackUIPaneActive;
	private boolean isBuildingSelected = false;
	private boolean isMilitarySelected = false;

	private Image currentSprite, character, character2, character3, heliboy,
			heliboy2, heliboy3, heliboy4, heliboy5,testImage, testSprite, sorcSpriteR, sorcSpriteR2, sorcSpriteR3, enemSpriteR;
	private Animation anim, hanim, sorcAnimDown,sorcAnimUp,sorcAnimLeft, sorcAnimRight;

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	public static Tile[][] tileMap;
	private int tileMapXSize, tileMapYSize;

	int livesLeft = 1;
	Paint paint, paint2;
	Random r;
	GameManager gameManager;
	private Player player;
	private Camera screen;
	private boolean isBuildMode;

	private boolean uiItem_1;

	private boolean uiItem_2;

	private boolean isScrllInit;

	public static int UIyItem_1, UIyItem_2;

	public static int UIxItem_1, UIxItem_2;

	public static int militaryUIx;

	public static int militaryUIy;

	public static int brownUIx;
	
	public static int brownUIy;

	public static int buildingUIx;

	public static int buildingUIy;
	
	// global variable for storing the building placement position,
	private int tempTouchX;
	private int tempTouchY;


	public GameScreen(Game game) 
	{
		super(game);
		
		// Initialize game objects here
		//level = Level.spawn;
		level = new Level();
		xScrll = yScrll = 0;
		screen = new Camera(800, 480);
		isScrllInit = false;
		
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		//bg2 = new Background(800,0);
		//robot = new Robot();
		//hb = new NPC(0, 0);
		//hb2 = new NPC(2, 1);
		r = new Random();
		//wood = new Resource(15,2);
		
		GameManager.level = level;
		GameManager.gold = 100;
		this.player = new Player(0,0);
		this.player.init(GameManager.level);
		GameManager.level.setPlayer(this.player);
		//character = Assets.character;
		//character2 = Assets.character2;
		//character3 = Assets.character3;
		testImage = Assets.test;

		//heliboy = Assets.heliboy;
		//heliboy2 = Assets.heliboy2;
		//heliboy3 = Assets.heliboy3;
		//heliboy4 = Assets.heliboy4;
		//heliboy5 = Assets.heliboy5;
		
		sorcSpriteR = Assets.sorcSpriteR;
		sorcSpriteR2 = Assets.sorcSpriteR2;
		sorcSpriteR3 = Assets.sorcSpriteR3;
		
		enemSpriteR = Sprite.enemSpriteR.getImage();

		//anim = new Animation();
		//anim.addFrame(character, 1250);// Main robot sprite
		//anim.addFrame(character2, 50);
		//anim.addFrame(character3, 50);
		//anim.addFrame(character2, 50);
		
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD.getImage(), 350);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD2.getImage(), 350);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD3.getImage(), 350);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD4.getImage(), 350);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD5.getImage(), 350);

		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL.getImage(), 350);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL2.getImage(), 350);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL3.getImage(), 350);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL4.getImage(), 350);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL5.getImage(), 350);

		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU.getImage(), 350);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU2.getImage(), 350);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU3.getImage(), 350);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU4.getImage(), 350);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU5.getImage(), 350);
		
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR.getImage(), 350);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR2.getImage(), 350);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR3.getImage(), 350);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR4.getImage(), 350);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR5.getImage(), 350);
		
		// peasant frames
		int l = 350;
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU.getImage(), 0);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU2.getImage(), 200);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU3.getImage(), 400);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU4.getImage(), 650);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU5.getImage(), 800);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU.getImage(), 1000);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU2.getImage(), 1200);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU3.getImage(), 1400);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU4.getImage(), 1600);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU5.getImage(), 1800);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU.getImage(), 2000);
		
		
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD.getImage(), 0);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD2.getImage(), 200);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD3.getImage(), 400);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD4.getImage(), 600);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD5.getImage(), 800);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD.getImage(), 1000);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD2.getImage(), 1200);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD3.getImage(), 1400);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD4.getImage(), 1600);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD5.getImage(), 1800);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD.getImage(), 2000);


		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL.getImage(), 0);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL2.getImage(), 200);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL3.getImage(), 400);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL4.getImage(), 600);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL5.getImage(), 800);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL.getImage(), 1000);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL2.getImage(), 1200);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL3.getImage(), 1400);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL4.getImage(), 1600);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL5.getImage(), 1800);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL.getImage(), 2000);

		
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR.getImage(), 0);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR2.getImage(), 200);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR3.getImage(), 400);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR4.getImage(), 600);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR5.getImage(), 800);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR.getImage(), 1000);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR2.getImage(), 1200);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR3.getImage(), 1400);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR4.getImage(), 1600);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR5.getImage(), 1800);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR.getImage(), 2000);


		// basic enemy npc animation
		hanim = new Animation();
		hanim.addFrame(Sprite.sorcSpriteL.getImage(), 500);
		hanim.addFrame(Sprite.sorcSpriteL2.getImage(), 500);
		hanim.addFrame(Sprite.sorcSpriteL3.getImage(), 500);
		


		//hanim.addFrame(Sprite.sorcerorSpriteR2.image, 100);
		//hanim.addFrame(heliboy5, 100);
		//hanim.addFrame(heliboy4, 100);
		//hanim.addFrame(heliboy3, 100);
		//hanim.addFrame(heliboy2, 100);

		//currentSprite = anim.getImage();

		//loadMap();//loads tiles in game

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
         
		paint2 = new Paint();
		paint2.setTextSize(100);
		//paint2.setFakeBoldText(true);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
	}

	private void loadMap() 
	{
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		Scanner scanner = new Scanner(SampleGame.map);
		
		while (scanner.hasNextLine()) 
		{
			String line = scanner.nextLine();

			// no more lines to read
			if (line == null) 
			{
				break;
			}

			if (!line.startsWith("!")) 
			{
				lines.add(line);
				width = line.length();
				//width = Math.max(width, line.length());				
			}
		}
		//height = lines();
		height = GameManager.level.getHeight();
		width = GameManager.level.getWidth();
		this.tileMapXSize = width;// set width of tileMap in size of 16x16 tiles encumbering 800 pixels (50)
		this.tileMapYSize = height;// set height of tileMap in size of 16x16 tiles encumbering 480 pixels (30)

		//GameManager.level.setWidth(width);
		//GameManager.level.setHeight(height);
		//GameManager.level.setTiles(new int [width * height]);
		
	//tileMap = new Tile[width][height];// set width/height of tileMap, representing 16x16 tiles encumbering 800x480 pixels (50, 30)
	//level.setTileMap(new TileMap(width, height));
		
		Tile t;
		for (int y = 0; y < height; y++) 
		{
			//String line = (String) lines.get(y);
			for (int x = 0; x < width; x++) 
			{

				//if (x < line.length()) 
				//{
					//char ch = line.charAt(x);
					/*if(ch == '1')
					{
						t = new Tile(Tile.grassTile_1.getSprite().getSIZE(), x, y, Character.getNumericValue(ch));
						tileMap[x][y] = t;
						GameManager.level.getTiles()[x+(y*width)] = ch;
						//GameManager.level.getTileMap().getTileGrid()[x][y] = t;
						//tilearray.add(t);
					}*/
					
					// add grass Tile to Tilesint[] tile map list, if Hex values match
					if(Integer.toHexString(GameManager.level.getTiles()[x+(y*width)]).equals(Tile.col_grass))
					{
						t = new Tile(false, 32, x, y, Sprite.grass);
						//tileMap[x][y] = t;
						
						GameManager.level.getTilesInt()[x+(y * width)] = t;

						//GameManager.level.getTileMap().getTileGrid()[x][y] = t;
						//tilearray.add(t);
					}
					// add sand/dirt Tile to Tilesint[] tile map list, if Hex values match
					else if(Integer.toHexString(GameManager.level.getTiles()[x+(y*width)]).equals(Tile.col_dirt))
					{
						t = new Tile(false, 32, x, y, Sprite.sand);
						GameManager.level.getTilesInt()[x + (y * width)] = t;
					}
					// if Hex values match, add shrub tile to list
					else if(Integer.toHexString(GameManager.level.getTiles()[x+(y*width)]).equals(Tile.col_shrub))
					{
						t = new Tile (false, 32, x, y, Sprite.shrub);
						GameManager.level.getTilesInt()[x + (y * width)] = t;;
					}
					else // set tile to void and add to TilesInt[] tile map list 
					{
						t = new Tile (false, 32, x, y, Sprite.blk);
						GameManager.level.getTilesInt()[x + (y * width)] = t;
					}

					
				//}

			}
		}
		Graphics g = game.getGraphics();
		//setpaintTiles(g);
	}

	@Override
	public void update(double deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
		if (state == GameState.GameMapMenu)
			updateGameMapMenu(touchEvents);
		if (state == GameState.KeepMenu)
			updateKeepMenu(touchEvents);
		if(state == GameState.BuildingPrompt)
			updateBuildingPrompt(touchEvents);
			
	}

	private void updateReady(List<TouchEvent> touchEvents) 
	{
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	
	private void updateRunning(List<TouchEvent> touchEvents, double deltaTime) 
	{
		// GUI position values in pixels
		brownUIx = GameManager.level.getBarrackX() * 32; //320; // x position in pixels 
		brownUIy = GameManager.level.getBarrackY() * 32; //176; // y position in pixels
		
		buildingUIx = 8;
		buildingUIy = 98;
		
		militaryUIx = 8;
		militaryUIy = 48;
		
		UIxItem_1 = 32;
		UIyItem_1 = 92;
		
		UIxItem_2 = 96;
		UIyItem_2 = 64;
		//militaryUIxItem_1;
		//militaryUIyItem_1;
		
		//SampleGame.g =  this.game;
		//SampleGame.g.getGraphics();//  test to render entity independently from gameScreen
			
		// This is identical to the update() method from our Unit 2/3 game.

		
		// 1. All touch input is handled here:
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) 
		{
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_DRAGGED && event.getIsMultiT())
			{
				//System.out.println("MultiTouch!");
				if(inBounds(event, 200, 0, 800, 48) && event.type == TouchEvent.TOUCH_DRAGGED)
				{
					//System.out.println("Touched Top");
					if(screen.yOffset >= 64)
					{
						screen.yOffset -= 64;
					}
				}
				else if(inBounds(event, 200, 432, 800, 48) && event.type == TouchEvent.TOUCH_DRAGGED)
				{
					//System.out.println("Touched Bottom");
					if(screen.yOffset <= 1032) //448
					{				
						screen.yOffset += 64;
					}
				}
				else if(inBounds(event, 752, 0, 48, 480) && event.type == TouchEvent.TOUCH_DRAGGED)
				{
					//System.out.println("Touched Right");
					if (screen.xOffset <= 1064)//1732
					{	
						screen.xOffset += 64;
					}
				}
				else if(inBounds(event, 48, 0, 48, 480) && event.type == TouchEvent.TOUCH_DRAGGED)
				{
					//System.out.println("Touched Left");
					if(screen.xOffset >= 64)
					{
						screen.xOffset -= 64;
					}
				}				
			}
			else
			{
				if(
				   event.type == TouchEvent.TOUCH_DOWN  && !GameManager.level.getPlayer().isPanelOn() && !GameManager.level.getPlayer().isBuildMode())
				{
					//GameManager.level.setIsEntitySelected(false);

					GameManager.level.tileEntityPointer((event.x  / 64) + (screen.xOffset / 64) , ((event.y) / 64) + (screen.yOffset / 64));
				}
				else if(event.type == TouchEvent.TOUCH_DRAGGED && !GameManager.level.getPlayer().isPanelOn() && !GameManager.level.getPlayer().isBuildMode())
				{
					GameManager.level.tileEntityPointerDragged((event.x  / 64) + (screen.xOffset / 64) , ((event.y) / 64) + (screen.yOffset / 64));

				}
				else
				{
					//GameManager.level.setIsEntitySelected(false);
					GameManager.level.setIsTouchingDown(false);
				}
	
				
				if(!event.getIsMultiT())
				{
					//System.out.println("SingleTouch");
				}	
				

				
				if (event.type == TouchEvent.TOUCH_UP) 
				{
					//GameManager.level.setIsTouchingDown(false);
					//GameManager.level.setIsEntitySelected(false);

					
					if(GameManager.level.isPlayerEntitySelected() && GameManager.level.isEntitySelected() && !GameManager.level.checkNotTouchingPlyrEntity())
					{
						GameManager.level.movePlayerEntity();
					}
					else if (GameManager.level.isEnemyEntitySelected() && GameManager.level.isEntitySelected() && !GameManager.level.checkNotTouchingEnemEntity())
					{
						GameManager.level.moveEnemyEntity();
					}					
					
					if (inBounds(event, 0, 0, 35, 35)) 
					{
						pause();
					}
					
					// inBounds test for barracks/keep
					if (isTouched(event, GameManager.level.getBarrackX(), GameManager.level.getBarrackY(), 1, 1)) //112,314,160,128
					{

						if(state == GameState.Running)
						{
							state = GameState.KeepMenu;
						}
						
						GameManager.level.isUI = true;
						GameManager.level.getPlayer().setBarrackUIPaneActive(true);
						GameManager.level.getPlayer().setBuildMode(false);
						GameManager.level.getPlayer().setUiItem_1(false);
						GameManager.level.getPlayer().setUiItem_2(false);
					}				
					else
					{
						GameManager.level.getPlayer().setBarrackUIPaneActive(false);
						GameManager.level.isUI = false;
					}
					
					if (inBounds(event, 160, 0, 39, 42)) // GameMapMenu state check
					{
						if(state == GameState.Running)
						{
							//System.out.println("GameMapMenu!!!!");
							state = GameState.GameMapMenu;
						}
					}
					
					if (inBounds(event, 40, 0, 118, 36 ))// check if plyr touches between/on the left res pane
					{ 						
						GameManager.level.getPlayer().setBuildMode(false);
						GameManager.level.setIsEntitySelected(false);
						// Prob eventually set uiitems to false as a reset for selected objects

						if(!GameManager.level.getPlayer().isPanelOn())// if plyr has not enabled GUIpanel, enable it.
						{
							GameManager.level.getPlayer().setPanelOn(true);// set panel true
							GameManager.level.setUiPaneDrawTrig(false);
							GameManager.level.getPlayer().setBuildMode(false);
							GameManager.level.getPlayer().setUiItem_1(false);
						}
						else // keep GUIPanel toggled off if not touched
						{
							GameManager.level.getPlayer().setPanelOn(false);
							GameManager.level.setUiPaneDrawTrig(false);
							GameManager.level.getPlayer().setBuildMode(false);
							GameManager.level.getPlayer().setUiItem_1(false);
						}
					}
					else if(GameManager.level.isEntitySelected() && !GameManager.level.getPlayer().isPanelOn())// check if plyr selects a entity, and toggle panel for info on entity
					{
						System.out.println(GameManager.level.getSelectedEntity().getPosInLink() + " ");
						GameManager.level.getPlayer().setPanelOn(true);// set panel true
						GameManager.level.setUiPaneDrawTrig(false);
						//GameManager.level.getPlayer().setBuildMode(false);
						//GameManager.level.getPlayer().setUiItem_1(false);
					}		
					else if (inBounds(event, 0, 42 ,200, 439) && GameManager.level.getPlayer().isPanelOn())// when panelOn player can click in area and not close it
					{
						GameManager.level.getPlayer().setPanelOn(true);
						GameManager.level.setUiPaneDrawTrig(false);
					}
					else
					{
						GameManager.level.getPlayer().setPanelOn(false);
						GameManager.level.setUiPaneDrawTrig(false);
						//GameManager.level.setIsEntitySelected(false);
						//GameManager.level.setPlayerEntitySelected(false);
						//GameManager.level.set;

						
					}
					
					// isbuildingSelected check
					if(inBounds(event, 16, 64, 32, 32) && GameManager.level.getPlayer().isPanelOn())
					{
						GameManager.level.getPlayer().setBuildingSelected(true);
					}				
					else if (inBounds(event, 0, 42, 200, 439) && GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isBuildingSelected())// when panelOn player can click in area and not close it
					{
						GameManager.level.getPlayer().setBuildingSelected(true);
						GameManager.level.setUiPaneDrawTrig(false);
					}
					else 
					{
						GameManager.level.getPlayer().setBuildingSelected(false);
					}
					
					
					// isMilitarySelected check
					if(inBounds(event, 48, 64, 32, 32) && GameManager.level.getPlayer().isPanelOn())
					{
						GameManager.level.getPlayer().setMilitarySelected(true);
						GameManager.level.getPlayer().setBuildingSelected(false);

					}
					else
					{
						GameManager.level.getPlayer().setMilitarySelected(false);
					}
			
				}
				
				
			if (event.type == TouchEvent.TOUCH_DOWN) 
			{			
				
				//System.out.println("Event X: " + (((event.x / 64) + screen.xOffset / 64))  + "Event Y:" + ((event.y / 64) + (screen.yOffset / 64) - 1 ));


				
				// set/check selected building entities
				if (GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isBuildingSelected() && inBounds(event, Level.UIxItem_1, Level.UIyItem_1, 32, 32) )
				{
					//this.level.setSelectedBuilding(wall);
					GameManager.level.getPlayer().setBuildMode(true);
					GameManager.level.getPlayer().setUiItem_1(true);
				}
				else if (GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isBuildingSelected() && inBounds(event, Level.UIxItem_2, Level.UIyItem_2, 32, 32))
				{	
					GameManager.level.getPlayer().setBuildMode(true);
					GameManager.level.getPlayer().setUiItem_2(true);					
				}
				else
				{
					//GameManager.level.getPlayer().setBuildMode(false);
				}
				
				// set/check selected military entities
				if (GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isMilitarySelected() && inBounds(event, Level.UIxItem_1, Level.UIyItem_1, 32, 32) )
				{
					//this.level.setSelectedBuilding(wall);
					GameManager.level.getPlayer().setBuildMode(false);
					GameManager.level.getPlayer().setUiItem_1(true);
				}
				else if (GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isMilitarySelected() && inBounds(event, Level.UIxItem_2, Level.UIyItem_2, 32, 32))
				{	
					GameManager.level.getPlayer().setBuildMode(false);
					GameManager.level.getPlayer().setUiItem_2(true);					
				}
				
				if(GameManager.level.getPlayer().isBuildMode() && GameManager.gold >= 10 && GameManager.level.getPlayer().isUiItem_1() && GameManager.level.getPlayer().isPanelOn() && event.x >= 200)
				{
					CastleWallV wall;
					int x = (((event.x - 200) / 32) + (GameManager.level.getPlayer().xScroll / 32 ));
					int y = (((event.y - 32) / 32) + (GameManager.level.getPlayer().yScroll / 32));
					System.out.println("X: " + x + " Y: " + y);//int x = event.x;

					//int xx = x * 2;// precision calc for placement on 16x16 tile grid
					//int yy = y * 2;
					//System.out.println("X: " + xx + " Y: " + yy);//int x = event.x;
					if(event.x >= 192 && event.y > 38 && !checkIfPlacementStacks(x, y))// chk if plyr is not touching within left res pane
					{
						// global variable for storing the building placement position, then use that position to prompt player confirm its placement during updateBuildingPrompt.
						this.tempTouchX = x;
						this.tempTouchY = y;
						
						wall = new CastleWallV(x, y);
						GameManager.level.getPlayerBuildingEntities().add(wall);
						
						/*// add wall to tileMap for solid colli on Ai
						Tile t = new Tile(this.tempTouchX, this.tempTouchY);
						t.setSolid(true);
						level.getBuildingTileMap().getTileGrid()[this.tempTouchX][this.tempTouchY] = t;
						*/
						
						state = GameState.BuildingPrompt;
						//System.out.println("Something cool.");
						
					}
					else
					{
						break;
					}
					/*
					int rX = x + 1;
					if(rX < 59 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
					{
						t = new Tile(rX,y);// right x tile
						t.setSolid(true);
						level.getTileMap().getTileGrid()[rX][yy] = t;
					}
					int dY = y + 1;
					if(dY < 47 && dY >= 0 && rX < 47)// right down tile
					{				
					t = new Tile(rX,dY);
					t.setSolid(true);
					level.getTileMap().getTileGrid()[rX][dY] = t;
					}
					int ndY = y + 1;
					if(ndY < 47 && ndY >= 0)// down
					{
					t = new Tile(xx,ndY);
					t.setSolid(true);
					level.getTileMap().getTileGrid()[x][ndY] = t;
					}
					*/
					//this.isBuildMode = false;
				}
				else if(GameManager.level.getPlayer().isBuildMode() && GameManager.gold >= 10 && GameManager.level.getPlayer().isUiItem_1() && !GameManager.level.getPlayer().isPanelOn() )
				{
					CastleWallV wall;
					int x = (((event.x) / 32) + (GameManager.level.getPlayer().xScroll / 32 ));
					int y = (((event.y - 32) / 32) + (GameManager.level.getPlayer().yScroll / 32));
					//System.out.println("X: " + x + " Y: " + y);//int x = event.x;

					if(event.x >= 192 && event.y > 38 && checkIfPlacementStacks(x, y))// chk if plyr is touching within left res pane
					{
						//int xx = x * 2;// precision calc for placement on 16x16 tile grid
						//int yy = y * 2;
						//System.out.println("X: " + xx + " Y: " + yy);//int x = event.x;
						this.tempTouchX = x;
						this.tempTouchY = y;
						wall = new CastleWallV(x, y);
						GameManager.level.getPlayerBuildingEntities().add(wall);
					
						/*// add wall to tileMap for solid colli on Ai
						Tile t = new Tile(x, y);
						t.setSolid(true);
						level.getBuildingTileMap().getTileGrid()[x][y] = t;
						*/
					}
					else
					{
						GameManager.level.getPlayer().setBuildMode(false);
						//GameManager.level.getPlayer().setUiItem_1(false);
						break;
					}
					/*int rX = x + 1;
					if(rX < 59 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
					{
						t = new Tile(rX,yy);// right x tile
						t.setSolid(true);
						level.getTileMap().getTileGrid()[rX][y] = t;
					}
					int dY = y + 1;
					if(dY < 47 && dY >= 0 && rX < 59)// right down tile
					{				
					t = new Tile(rX,dY);
					t.setSolid(true);
					level.getTileMap().getTileGrid()[rX][dY] = t;
					}
					int ndY = y + 1;
					if(ndY < 47 && ndY >= 0)// down
					{
					t = new Tile(x,ndY);
					t.setSolid(true);
					level.getTileMap().getTileGrid()[x][ndY] = t;
					}
					*/
					//this.isBuildMode = false;
				}
				/*else if (this.isBuildMode == true && !barrackUIPaneActive && GameManager.gold >= 10 && uiItem_2)
				{
					GameManager.gold -= 10;
					Assets.build_fx.play(0.75f); // play sfx
					CastleWall wall;
					int x = (event.x / 32);
					int y = (event.y / 32);
					int xx = x * 2;// precision calc for placement on 16x16 tile grid
					int yy = y * 2;
					System.out.println("X: " + xx + " Y: " + yy);//int x = event.x;
					wall = new CastleWall(x, y);
					level.getPlayerBuildingEntities().add(wall);
					
					// add wall to tileMap for solid colli on Ai
					Tile t = new Tile(xx,yy);
					t.setSolid(true);
					//level.getTileMap().getTileGrid()[xx][yy] = t;
					
					int rX = xx + 1;
					if(rX < 49 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
					{
					t = new Tile(rX,yy);// right x tile
					t.setSolid(true);
					//level.getTileMap().getTileGrid()[rX][yy] = t;
					}
					int dY = yy + 1;
					if(dY < 24 && dY >= 0 && rX < 49)// right down tile
					{				
					t = new Tile(rX,dY);
					t.setSolid(true);
					//level.getTileMap().getTileGrid()[rX][dY] = t;
					}
					int ndY = yy + 1;
					if(ndY < 24 && ndY >= 0)// down
					{
					t = new Tile(xx,ndY);
					t.setSolid(true);
					//level.getTileMap().getTileGrid()[xx][ndY] = t;
					}
					//this.isBuildMode = false;
				}*/
				
				if (GameManager.level.getPlayer().isPanelOn() && GameManager.level.getPlayer().isMilitarySelected() && GameManager.level.getPlayer().isUiItem_1())
				{				
					// Spawn troops
					
					Entity e;
					Sorcerer sorc;
					int last = r.nextInt(25);
			
					if(last < 10)
					{
						last = 19;
					}
					sorc = new Sorcerer(last, 16, this.level);
					
					System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

					GameManager.level.getPlayerArmyEntities().add(sorc);
					GameManager.gold -= 5;

					//currentSprite = anim.getImage();
				}
				
					
		
			}
			
			

			}
		}
		
		// 2. Check miscellaneous events like death:

		//if (livesLeft == 0) 
		//{
			//state = GameState.GameOver;
		//}

		// 3. Call individual update() methods here.
		// This is where all the game updates happen.
		// For example, robot.update();
		//robot.update();
		/*if (robot.isJumped()) 
		//{
			//currentSprite = Assets.characterJump;
		} 
		else if (robot.isJumped() == false && robot.isDucked() == false) 
		{
			currentSprite = anim.getImage();
		}
		 */
		/*
		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) 
		{
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true) 
			{
				p.update();
			} 
			else 
			{
				projectiles.remove(i);
			}
		}
		*/
				
		//updateTiles();
		//hb.update();
		//enem.update();
		//hb2.update();
		bg1.update();
		bg2.update();
		GameManager.level.getPlayer().update();
		GameManager.level.update();// update according to rate of ups
		
		animate();// updates/call animation refs update()
		
		//if (robot.getCenterY() > 500) 
		//{
			//state = GameState.GameOver;
		//}
	}
	
	private boolean checkIfPlacementStacks(int x, int y) 
	{
		boolean b = false;
		
		for (int i = 0; i < GameManager.level.getPlayerBuildingEntities().size(); i++)
		{
			if ( GameManager.level.getPlayerBuildingEntities().get(i).getCenter32X() == x && GameManager.level.getPlayerBuildingEntities().get(i).getCenter32Y() == y)// +1
			{
				b = true;
				System.out.println("Cannot build here.");
				break;
			}
			else
			{
				b = false;
			}
		}
		
		return b;		
	}

	//update method called every sec in faster render view
	public void update12EverySec()
	{
		if(state == GameState.Running)
		{
			GameManager.update();			
		}
	}
	
	//update method called every sec in faster render view
	public void updateMovement()
	{
		if(state == GameState.Running)
		{
			GameManager.level.updateMovement();// slower update for slower entity movement on screen			
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) 
	{
		if ((event.x) > x && (event.x) < x + width - 1 && (event.y) > y
				&& (event.y) < y + height - 1)
			return true;
		else
			return false;
	}
	
	private boolean isTouched(TouchEvent event, int x, int y, int width, int height) 
	{
		int xx = ((event.x / 32) + (screen.xOffset / 32));
		int yy = ((event.y / 32) + (screen.yOffset / 32)) - 1;
		//System.out.println("X: " + xx + "Y: " + yy);

		if (xx >= x && yy >= y && xx <= x + width && yy <= y + height)
			return true;
		else
			return false;
		
		/*if(xx == 12 && yy == 12)
		{
			return true;
		}
		else
		{
			return false;
		}*/
	}

	private void updateBuildingPrompt(List<TouchEvent> touchEvents)
	{
		//System.out.println("Something else.");

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) 
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) 
			{
				if (inBounds(event, 250, 0, 300, 65)) 
				{
					if (!inBounds(event, 0, 0, 35, 35)) 
					{
						// add wall to tileMap for solid colli on Ai
						Tile t = new Tile(this.tempTouchX, this.tempTouchY);
						t.setSolid(true);
						level.getBuildingTileMap().getTileGrid()[this.tempTouchX][this.tempTouchY] = t;
						GameManager.gold -= 10;
						Assets.build_fx.play(0.75f); // play sfx
						


						resume();
					}
				}

				if (inBounds(event, 250, 400, 300, 65)) 
				{
					// undo/ remove building 
					level.getPlayerBuildingEntities().removeLast();
					//nullify();
					
					resume();
				}
			}
		}
	}
	
	private void updatePaused(List<TouchEvent> touchEvents) 
	{
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) 
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) 
			{
				if (inBounds(event, 0, 0, 800, 240)) 
				{
					if (!inBounds(event, 0, 0, 35, 35)) 
					{
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) 
				{
					nullify();
					goToMenu();
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) 
	{
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) 
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) 
			{
				if (inBounds(event, 0, 0, 800, 480)) 
				{
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}
	
	private void updateKeepMenu(List<TouchEvent> touchEvents)
	{
		int len = touchEvents.size();
		for (int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);
			if (!event.getIsMultiT() && event.type == TouchEvent.TOUCH_DOWN)
			{
				if (inBounds(event, 0, 0, 800, 240)) 
				{
					resume();
				}

				if (inBounds(event, 0, 240, 800, 240) && !isTouched(event, GameManager.level.getBarrackX(), GameManager.level.getBarrackY() + 1, 3, 3))
				{
					endTurn();
					
				}
			}
		}
	}
	
	private void updateGameMapMenu(List<TouchEvent> touchEvents)
	{
		int len = touchEvents.size();
		for (int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN)
			{
				if (inBounds(event, 0, 0, 800, 240)) 
				{
					
				}

				if (inBounds(event, 0, 240, 800, 240)) 
				{					
					resume();
				}
			}
		}
	}
	
	private void processNextTurn()
	{
		//update entities
		for(int i = 0; i < GameManager.level.getPlayerBuildingEntities().size(); i++)
		{
			if(GameManager.level.getPlayerBuildingEntities().get(i).isVisible())
			{			
				GameManager.level.getPlayerBuildingEntities().get(i).nextTurnUpdate();
			}
			//if (playerBuildingEntities.get(i).isBarrack())// if building is a barrack
		}
		
		for(int i = 0; i < GameManager.level.getPlayerArmyEntities().size(); i++)
		{
			//if(GameManager.level.getPlayerArmyEntities().get(i).doesEntityHaveGridMoves())
			//{
				GameManager.level.getPlayerArmyEntities().get(i).nextTurnUpdate();
			//}
		}
		
		for(int i = 0; i < GameManager.level.getEnemyArmyEntities().size(); i++)
		{
			//if(GameManager.level.getEnemyArmyEntities().get(i).doesEntityHaveGridMoves())
			//{
				GameManager.level.getEnemyArmyEntities().get(i).nextTurnUpdate();
			//}
		}
	}

	private void endTurn() 
	{
		if(GameManager.curTurn == 1)
		{
			GameManager.totalTurns ++;
			GameManager.curTurn ++;
		}
		else
		{
			GameManager.totalTurns ++;
			GameManager.curTurn ++;
			GameManager.prevTurn ++;
		}
		resume();
		processNextTurn(); // 
		System.out.println("Cur:" + GameManager.curTurn);
	}

	private void updateTiles()
	{
		for (int y = 0; y < tileMapYSize; y++)
		{
			//Tile t = (Tile) tilearray.get(i);
			//t.update();
			for (int x = 0; x < tileMapXSize; x++)
			{
				tileMap[x][y].update();
			}
		}
	}

	@Override
	public void paint(double deltaTime) 
	{
		int yScroll;
		int xScroll;
		//screen.clear();// clear screen b4 every frame
		Graphics g = game.getGraphics();
		screen.setGraphics(g);
		g.clearScreen(0);
// v v 	
		xScroll = screen.xOffset;
		yScroll = screen.yOffset;
		
		if(xScrll == 0 && yScrll == 0 && !isScrllInit )// init scrll field (this statement should run once!)
		{
			//|| xScroll != xScrll || yScroll != yScrll)
			GameManager.level.render(xScroll, yScroll, this.screen, g);
			this.xScrll = xScroll;
			this.yScrll = yScroll;
			isScrllInit = true;

		}
		
		if(xScrll != xScroll || yScrll != yScroll || !GameManager.level.isUiPaneDrawTrig())
		{
			GameManager.level.render(xScroll, yScroll, this.screen, g); // *

			GameManager.level.setUiPaneDrawTrig(true);
			this.xScrll = xScroll;
			this.yScrll = yScroll;
		}
		else 
		{	// draw last fame of x/y scroll on canvas
			GameManager.level.renderEntitiesOnSavedFrame(g, this.screen);
		}
		
// ^Tile rendering ^
		
		//System.out.println("0x" + Integer.toHexString(screen.getPixels()[0]));
		//System.out.println(Integer.toHexString(this.screen.getPixels()[0]));
		//g.renderPxlArry(this.screen);
	//System.out.println("Player X: " + player.getPixelX() + " Y: " + player.getPixelY() + " xScrll: " + screen.xOffset + " yScrll: " + screen.yOffset);
		//g.drawRect(GameManager.level.getPlayer().getPixelX(), GameManager.level.getPlayer().getPixelY(), 800, 480, Color.RED);
		//GameManager.level.renderSprite(4, 4, Sprite.castleWall, screen, g);

		
		//drawBackgroundLayer();
			//paintTiles(g);
		/*
		 * 
		 */
			if(GameManager.level.getPlayer().isBuildMode())
			{
				paintGrid(g);
			}
		
		//g.drawSprite(Sprite.woodPile, wood.getPixelX(), wood.getPixelY());
		/*	
		// Draw projectiles
		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) 
		{
			Projectile p = (Projectile) projectiles.get(i);
			g.drawRect(p.getX(), p.getY(), 10, 5, Color.YELLOW);
		}
		*/
			
		/*	
		//What class should store array of entities like projectile
		LinkedList entities = level.getPlayerArmyEntities();
		  for(int i = 0; i < entities.size(); i++)
		  {
			  ArmyUnit e  = (ArmyUnit) entities.get(i);
			  //ArmyUnit last;

			  //if(direction == 0)
			 
				 // g.drawSprite(Sprite.sorcSpriteR, last.getPixelX() , last.getPixelY());
				  g.drawImage(e.getImage(), e.getPixelX() , e.getPixelY());
				 // g.drawRect(e.atkColliderBounds.left, e.atkColliderBounds.top, e.atkColliderBounds.right, e.atkColliderBounds.bottom);
			  

			  //g.drawEntityImage(e.getSprite().getImage(), last.getCenterX(), last.getCenterY()); 
		  }
		  
		  LinkedList<Enemy> enemyEntities = level.getEnemyArmyEntities();
		  for(int i = 0; i < enemyEntities.size(); i++)
		  {
			  Enemy e  =  enemyEntities.get(i);

			  //g.drawRect(e.colliderBounds.left, e.colliderBounds.top, e.colliderBounds.right, e.colliderBounds.bottom);
			 // g.drawRect(e.atkColliderBounds.left, e.atkColliderBounds.top, e.atkColliderBounds.right, e.atkColliderBounds.bottom);
			  g.drawImage(e.getImage(), e.getPixelX() , e.getPixelY());
		  }
		  
		  LinkedList plyrBuildingEntities = level.getPlayerBuildingEntities();
		  for(int i = 0; i < plyrBuildingEntities.size(); i++)
		  {
			  BuildingEntity e  = (BuildingEntity) plyrBuildingEntities.get(i);

			  g.drawSprite(e.getSprite(), e.getPixelX() , e.getPixelY());
			 // g.drawRect(e.buildingColliderBounds.left, e.buildingColliderBounds.top, e.buildingColliderBounds.right, e.buildingColliderBounds.bottom);
			  	// g.drawRect(e.atkColliderBounds.left, e.atkColliderBounds.top, e.atkColliderBounds.right, e.atkColliderBounds.bottom);
		  }
		*/
		
	
		//	g.drawImage(enemSpriteR, enem.getPixelX(), enem.getPixelY() );
		
	   // drawEntityLayer(xScroll, yScroll, g);
	   //  drawGUILayer();

		// Example:
		// g.drawImage(Assets.background, 0, 0);
		// g.drawImage(Assets.character, characterX, characterY);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
		if(state == GameState.KeepMenu)
			drawKeepUI();
		if(state == GameState.GameMapMenu)
			drawGameMapUI();
		if(state == GameState.BuildingPrompt)
			drawBuildingPromptUI();

	}

	private void setpaintTiles(Graphics g) 
	{		
		Tile t;
		for (int y = 0; y < tileMapYSize; y++) 
		{
			//Tile t = (Tile) tilearray.get(i);
			for(int x = 0; x < tileMapXSize; x++)
			{
				//t = tileMap[x][y];
			t = level.getBuildingTileMap().getTileGrid()[x][y];

			g.setTile(t);
				
			}
		}
	}
	
	private void prePaintGridTiles(Graphics g)
	{	
			Tile t;
			for (int y = 0; y < 25; y++) 
			{				
				for(int x = 0; x < 50; x++)
				{
					t = tileMap[x][y];
					
					g.setGrid(x * 32, y * 32, 32, 32);
				}
			}
	}
	
	private void paintTiles(Graphics g)
	{
		g.drawTile();
	}
	
	private void paintGrid(Graphics g)
	{
		if(GameManager.level.getPlayer().isPanelOn())
		{
			g.drawGrid(Assets.buildModeGrid);
		}
		else
		{
			g.drawImage(Assets.buildModeGridWide, 0, 32);
		}
	}

	public void animate() 
	{
		//anim.update(10);
		hanim.update(50);
		Assets.sorcAnimDown.update(50);
		Assets.sorcAnimLeft.update(50);
		Assets.sorcAnimRight.update(50);
		Assets.sorcAnimUp.update(50);
		
		Assets.enemAnimDown.update(50);
		Assets.enemAnimLeft.update(50);
		Assets.enemAnimRight.update(50);
		Assets.enemAnimUp.update(50);
		
		Assets.peasAnimDown.update(50);
		Assets.peasAnimLeft.update(50);
		Assets.peasAnimRight.update(50);
		Assets.peasAnimUp.update(50);
		

	}
	
	
	public void drawBackgroundLayer()
	{
		Graphics g = game.getGraphics();
		//g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		//g.drawRect(0, 0, 800, 480, Color.BLACK);
		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
	}
	
	public void drawKeepGUI(Graphics g)
	{				
		// Draw Rect barrack soldier interface
		if(player.isBarrackUIPaneActive())
		{
			//g.drawRect(400, 144, 128, 64, Color.BLACK);
			g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawImage(Assets.buildingIcon, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon, militaryUIx, militaryUIy);
			//g.drawRect(740, 0, 64, 500, Color.BLACK);// Vertical Stronghold type GUI
		}
		if(player.isBarrackUIPaneActive() &&  player.isBuildingSelected())
		{
			g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			//g.drawSprite(Sprite.castleWall, UIxItem_1, UIyItem_1 );
			g.drawSprite(Sprite.stoneWallV, UIxItem_1 , UIyItem_1);
			g.drawSprite(Sprite.castleWall, UIxItem_2, UIyItem_2);
			g.drawImage(Assets.buildingIcon_select, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon, militaryUIx, militaryUIy);

		}
		else if(player.isBarrackUIPaneActive() && player.isMilitarySelected())
		{
			g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawSprite(Sprite.peasSpriteD, UIxItem_1, UIyItem_1 );
			g.drawImage(Assets.buildingIcon, buildingUIx, buildingUIy);
			g.drawImage(Assets.militaryIcon_select, militaryUIx, militaryUIy);
		}
		else
		{
			player.setBuildingSelected(false);
			player.setMilitarySelected(false);
		}
	}

	private void nullify() 
	{
		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		bg1 = null;
		bg2 = null;
		robot = null;
		hb = null;
		hb2 = null;
		//enem = null;
		currentSprite = null;
		character = null;
		character2 = null;
		character3 = null;
		heliboy = null;
		heliboy2 = null;
		heliboy3 = null;
		heliboy4 = null;
		heliboy5 = null;
		anim = null;
		hanim = null;
		level.nullLevel();
		//Entity.nullLists();
		


		// Call garbage collector to clean up memory.
		System.gc();

	}

	private void drawBuildingPromptUI()
	{
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(275, 0, 0, 0);
		g.drawString("Yes", 400, 65, paint2);
		g.drawString("No", 400, 460, paint2);

	}
	
	private void drawReadyUI() 
	{
		Graphics g = game.getGraphics();
    	// Darkens the entire screen to display the paused screen.

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 400, 240, paint);

	}
	
	// Game UI
	private void drawRunningUI() 
	{
		Graphics g = game.getGraphics();
		//g.drawImage(Assets.button, 0, 285, 0,   0, 65, 65); // up button
		//g.drawImage(Assets.button, 0, 350, 0,  65, 65, 65); // shoot button
		//g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65); // down button

		//g.drawImage(Assets.button, 0,   0, 0, 195, 35, 35); // pause button
	}

	private void drawPausedUI() 
	{
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);

	}

	private void drawGameOverUI() 
	{
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paint2);
		g.drawString("Tap to return.", 400, 290, paint);

	}
	
	private void drawGameMapUI()
	{
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Game Map", 400, 165, paint);
		g.drawString("(This menu is not done yet.) Tap to return.", 400, 360, paint);
	}
	
	private void drawKeepUI()
	{
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		// draw stone ui for background?
		g.drawString("Keep Menu (messages, skill/tree advancement,", 350, 160, paint);
		g.drawString(" gov/courts, advisement, town/empire management)", 350, 186, paint);
		g.drawString("End Turn/Next Day", 400, 360, paint);
	}
	
	@Override
	public void pause() 
	{
		if (state == GameState.Running)
			state = GameState.Paused;
	}

	@Override
	public void resume() 
	{
		if (state == GameState.Paused)
			state = GameState.Running;
		else if (state == GameState.GameMapMenu)
				 state = GameState.Running;
		else if (state == GameState.KeepMenu)
			     state = GameState.Running;
		else if (state == GameState.BuildingPrompt)
			state = GameState.Running;
	}

	@Override
	public void dispose() 
	{

	}

	@Override
	public void backButton()
	{
		pause();
	}

	private void goToMenu() 
	{
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBg1() 
	{
		// TODO Auto-generated method stub
		return bg1;
	}

	public static Background getBg2() 
	{
		// TODO Auto-generated method stub
		return bg2;
	}

	public static Robot getRobot() 
	{
		// TODO Auto-generated method stub
		return robot;
	}
	
	public boolean getisBuildingSelected()
	{
		return this.isBuildingSelected;
	}
	
	public boolean getisMilitarySelected()
	{
		return this.isMilitarySelected;
	}
	
	public void setOffset(int x, int y)
	{
		this.xOffset = x;
		this.yOffset = y;
	}

}