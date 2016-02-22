package com.CI.game;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.CI.game.graphics.Animation;
import com.CI.game.graphics.Assets;
import com.CI.game.graphics.Sprite;
import com.CI.game.graphics.SpriteSheet;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Game;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Screen;
import com.CI.lotusFramework.Graphics.ImageFormat;
import com.CI.lotusFramework.implementation.AndroidGame;
import com.CI.lotusFramework.implementation.AndroidGraphics;
import com.CI.lotusFramework.implementation.AndroidImage;
/*
 * All Screen classes have 3 important methods, update, paint and back button
 * 
 */
public class LoadingScreen extends Screen 
{
	public LoadingScreen(Game game) 
	{
		
		super(game);
	}
	/**
	 * update() method, you load all the resources that you will use in the game 
	 * (i.e. all the resources that we have created in the Assets class). 
	 * We would not need anything in our paint() method, unless you would like to have an image while 
	 * the game loads these resources (make sure you load this in another class).
	 * @see com.CI.lotusFramework.Screen#update(float)
	 */
	@Override
	public void update(double deltaTime) 
	{
		Bitmap b;
		
		AndroidGraphics g = (AndroidGraphics) game.getGraphics();
		Assets.menu = g.newImage("tempMenu.png", ImageFormat.RGB565);
		Assets.background = g.newImage("backgrounds/background2.png", ImageFormat.RGB565);
		//Assets.character = g.newImage("character.png", ImageFormat.ARGB4444);
		//Assets.character2 = g.newImage("character2.png", ImageFormat.ARGB4444);
		//Assets.character3  = g.newImage("character3.png", ImageFormat.ARGB4444);
		//Assets.characterJump = g.newImage("jumped.png", ImageFormat.ARGB4444);
		//Assets.characterDown = g.newImage("down.png", ImageFormat.ARGB4444);
		//Assets.click = game.getAudio().createSound("bitBump.mp3");// loading a sound file

		
				
		Assets.tiledirt = g.newImage("tiledirt.png", ImageFormat.RGB565);
		Assets.tilegrassTop = g.newImage("tilegrasstop.png", ImageFormat.RGB565);
		Assets.tilegrassBot = g.newImage("tilegrassbot.png", ImageFormat.RGB565);
		Assets.tilegrassLeft = g.newImage("tilegrassleft.png", ImageFormat.RGB565);
		Assets.tilegrassRight = g.newImage("tilegrassright.png", ImageFormat.RGB565);
		
		//Assets.button = g.newImage("button.jpg", ImageFormat.RGB565);

		//This is how you would load a sound if you had one.
		//Assets.click = game.getAudio().createSound("explode.ogg");
		Assets.build_fx = game.getAudio().createSound("build_fx.mp3");
		//SpriteSheet.tempSheet = g.newImage("grass_spritesheet.png", ImageFormat.ARGB4444);// 
		Assets.test = g.newImage("grass_spritesheet.png", ImageFormat.ARGB4444);
		//Assets.sprite = g.newSprite(Sprite.sprite, 64, 192, 32, 32);
		Assets.sorcSpriteR = g.newSprite(Sprite.sorcSpriteR);
		Assets.sorcSpriteR2 = g.newSprite(Sprite.sorcSpriteR2);
		Assets.sorcSpriteR3 = g.newSprite(Sprite.sorcSpriteR3);
		
		Assets.enemSpriteR = g.newSprite(Sprite.enemSpriteR);
		
		Assets.brownUI = g.newImage("Brown_UI_border.png", ImageFormat.ARGB4444);
				
		//Assets.grassTile_1 = g.newTile(Tile.grassTile_1);
		Assets.barrack = g.newImage("barrack_64.png", ImageFormat.ARGB4444);
		
		Assets.sorcAnimUp = new Animation(Sprite.sorcSpriteU.getImage(),Sprite.sorcSpriteU2.getImage(),Sprite.sorcSpriteU3.getImage(), 1500);
		Assets.sorcAnimDown = new Animation(Sprite.sorcSpriteD.getImage(),Sprite.sorcSpriteD2.getImage(),Sprite.sorcSpriteD3.getImage(),1500);
		Assets.sorcAnimLeft = new Animation(Sprite.sorcSpriteL.getImage(),Sprite.sorcSpriteL2.getImage(),Sprite.sorcSpriteL3.getImage(), 1500);
		Assets.sorcAnimRight = new Animation(Sprite.sorcSpriteR.getImage(),Sprite.sorcSpriteR2.getImage(),Sprite.sorcSpriteR3.getImage(), 1500);
		
		Assets.enemAnimUp = new Animation(Sprite.enemSpriteU.getImage(),Sprite.enemSpriteU2.getImage(),Sprite.enemSpriteU3.getImage(), 500);
		Assets.enemAnimDown = new Animation(Sprite.enemSpriteD.getImage(),Sprite.enemSpriteD2.getImage(),Sprite.enemSpriteD3.getImage(), 500);
		Assets.enemAnimLeft = new Animation(Sprite.enemSpriteL.getImage(),Sprite.enemSpriteL2.getImage(),Sprite.enemSpriteL3.getImage(), 500);
		Assets.enemAnimRight = new Animation(Sprite.enemSpriteR.getImage(),Sprite.enemSpriteR2.getImage(),Sprite.enemSpriteR3.getImage(), 500);
		
		Assets.peasAnimUp = new Animation();
		Assets.peasAnimLeft = new Animation();
		Assets.peasAnimRight = new Animation();
		Assets.peasAnimDown = new Animation();
		
		Assets.woodPile = g.newSprite(Sprite.woodPile);
		
		// UI stuff
		Assets.militaryIcon = g.newImage("MilitaryIcon.png", ImageFormat.RGB565);
		Assets.militaryIcon_select = g.newImage("MilitaryIcon_selected.png", ImageFormat.RGB565);
		Assets.buildingIcon = g.newImage("BuildingIcon.png", ImageFormat.RGB565);
		Assets.buildingIcon_select = g.newImage("BuildingIcon_selected.png", ImageFormat.RGB565);
		Assets.castleWall = g.newSprite(Sprite.castleWall);
		
		// create img ref from drawable-nodpi
		//b = BitmapFactory.decodeResource(game.getResources(), R.drawable.stone_leftui);
		//Assets.UIbackground = new AndroidImage(b, ImageFormat.RGB565);

		try
		{
			b = g.getBitmap(g.getAssets(), "ui/stone_leftui.png");
			Assets.UIbackground = new AndroidImage(b, ImageFormat.RGB565);
		
			b = g.getBitmap(g.getAssets(), "environment/grass_SpriteSheet_2.png");
			Assets.g32 = new AndroidImage(b, ImageFormat.ARGB4444);
			
			b = g.getBitmap(g.getAssets(), "ui/stoneHorGUI.png");
			Assets.resPane = new AndroidImage(b, ImageFormat.RGB565);
			
			b = g.getBitmap(g.getAssets(), "ui/l_GUIWidget.png");
			Assets.resWidget = new AndroidImage(b, ImageFormat.RGB565);
			
			b = g.getBitmap(g.assets, "ui/l_StonePanelShort.png");
			Assets.resPanel = new AndroidImage(b, ImageFormat.RGB565);
			
			b = g.getBitmap(g.assets, "ui/gridHorPointer.png");
			Assets.gridHorPointer = new AndroidImage(b, ImageFormat.RGB565);

			b = g.getBitmap(g.assets, "ui/gridVertPointer.png");
			Assets.gridVerPointer = new AndroidImage(b, ImageFormat.RGB565);
			
			b = g.getBitmap(g.assets, "ui/Red64x32.png");
			Assets.healthBarHalf = new AndroidImage(b, ImageFormat.RGB565);
		
			b = g.getBitmap(g.assets, "ui/Red96x32.png");
			Assets.healthBarFull = new AndroidImage(b, ImageFormat.RGB565);
		
			 b = g.getBitmap(g.assets, "ui/Red32x32.png");
			 Assets.healthBarQtr = new AndroidImage(b, ImageFormat.RGB565);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		Assets.combatModeGrid = g.newImage("combatGrid.png", ImageFormat.ARGB4444);
		Assets.combatModeGridWide = g.newImage("combatGridWide.png", ImageFormat.ARGB4444);
		
		Assets.buildModeGrid = g.newImage("buildModeGrid.png", ImageFormat.ARGB4444);
		Assets.buildModeGridWide = g.newImage("buildModeGridWide.png", ImageFormat.ARGB4444);
		// level Maps
		b = BitmapFactory.decodeResource( game.getResources(),R.drawable.lvlmap);
		
		Assets.levelMap = new AndroidImage(b, ImageFormat.ARGB4444);
		
		//Assets.g32 = (AndroidImage) g.newImage("environment/grass_SpriteSheet_2.png", ImageFormat.ARGB4444);
		Assets.g16 = g.newImage("environment/grass_spritesheet.png", ImageFormat.ARGB4444);

		
		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
	}

	@Override
	public void pause() 
	{

	}

	@Override
	public void resume() 
	{

	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void backButton() 
	{

	}
	@Override
	public void update12EverySec() {
		// TODO Auto-generated method stub
		
	}
}