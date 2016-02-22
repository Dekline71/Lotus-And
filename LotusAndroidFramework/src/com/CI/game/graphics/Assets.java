package com.CI.game.graphics;

import android.graphics.Bitmap;

import com.CI.game.SampleGame;
import com.CI.lotusFramework.Game;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Image;
import com.CI.lotusFramework.Music;
import com.CI.lotusFramework.Sound;
import com.CI.lotusFramework.implementation.AndroidImage;

public class Assets 
{
	
	public static Image menu, splash, background, character, character2, character3, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
	public static Image tiledirt, tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, characterJump, characterDown, test;
	public static Image sorcSpriteR, sorcSpriteR2, sorcSpriteR3, enemSpriteR, grassTile_1, barrack;
	public static Image woodPile;
	public static Image button;
	public static Sound click, build_fx;
	public static Music theme;
	public static Image brownUI;
	public static Animation sorcAnimUp;
	public static Animation sorcAnimDown;
	public static Animation sorcAnimLeft;
	public static Animation sorcAnimRight;
	public static Animation enemAnimUp;
	public static Animation enemAnimDown;
	public static Animation enemAnimLeft;
	public static Animation enemAnimRight;
	public static Image militaryIcon, buildingIcon;
	public static Image militaryIcon_select;
	public static Image buildingIcon_select;
	public static Image castleWall;
	public static Image buildModeGrid;
	public static Image buildModeGridWide;
	public static Image goldText;
	public static Animation peasAnimUp;
	public static Animation peasAnimLeft;
	public static Animation peasAnimRight;
	public static Animation peasAnimDown;
	public static Image levelMap;
	public static Image UIbackground;
	//public static Image g;
	public static AndroidImage g32;
	public static Image g16;
	public static Graphics assets;
	public static AndroidImage resPane;
	public static AndroidImage resWidget;
	public static AndroidImage resPanel;
	public static Image combatModeGrid;
	public static Image combatModeGridWide;
	public static Image gridHorPointer;
	public static Image gridVerPointer;
	public static AndroidImage healthBarHalf;
	public static AndroidImage healthBarFull;
	public static AndroidImage healthBarQtr;
	
	public static void load(SampleGame sampleGame) 
	{
		assets = sampleGame.getGraphics();
		// TODO Auto-generated method stub
		theme = sampleGame.getAudio().createMusic("At_The_Gates.mp3");
		theme.setLooping(true);
		theme.setVolume(0.75f);
		theme.play();
	}
	
}
