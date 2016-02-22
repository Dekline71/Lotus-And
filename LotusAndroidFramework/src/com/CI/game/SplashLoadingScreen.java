package com.CI.game;

import com.CI.game.graphics.Assets;
import com.CI.lotusFramework.Game;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Screen;
import com.CI.lotusFramework.Graphics.ImageFormat;
/**
 *  Subclass of the Screen superclass, we must call all of Screen's methods.
 *  We have the update method, in which we load our first Image as an RGB565 
 *  (which does not support transparency but takes up the least amount of memory). 
 *  We do not paint anything.
 *  As soon as the loading of the splash.jpg is complete, we go straight to the loading screen
 * @author Jake Galleta Conscious Interactive (c) 2014.
 *
 */
public class SplashLoadingScreen extends Screen 
{
	public SplashLoadingScreen(Game game) 
	{
		super(game);
	}

	@Override
	public void update(double deltaTime)
	{
		Graphics g = game.getGraphics();
		Assets.splash= g.newImage("splash.jpg", ImageFormat.RGB565);

		
		game.setScreen(new LoadingScreen(game));

	}

	@Override
	public void paint(double deltaTime) 
	{

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}

	@Override
	public void update12EverySec() {
		// TODO Auto-generated method stub
		
	}
}