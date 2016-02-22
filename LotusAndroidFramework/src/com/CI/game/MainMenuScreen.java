package com.CI.game;

import java.util.List;

import com.CI.game.graphics.Assets;
import com.CI.game.graphics.GameScreen;
import com.CI.lotusFramework.Game;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.Screen;
import com.CI.lotusFramework.Input.TouchEvent;

public class MainMenuScreen extends Screen 
{
	public MainMenuScreen(Game game) 
	{
		super(game);
	}

	@Override
	public void update(double deltaTime) 
	{
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) 
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) 
			{

				if (inBounds(event, 50, 350, 250, 450)) 
				{
					game.setScreen(new GameScreen(game));
				}

			}
		}
	}

	// used to create rectangles with coordinates (x, y, x2, y2). 
	//We use this to create regions in the screen that we can touch to interact with the game 
	//(as we do here in the update method)
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) 
	{
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getGraphics();
		g.drawImage(Assets.menu, 0, 0);
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
        android.os.Process.killProcess(android.os.Process.myPid());

	}

	@Override
	public void update12EverySec() {
		// TODO Auto-generated method stub
		
	}
}
