package com.CI.game.level.tile;

import com.CI.game.entity.Camera;
import com.CI.game.graphics.Sprite;

public class StoneWallTile extends Tile
{

	public StoneWallTile(boolean isSolid, Sprite sprite) {
		super(isSolid, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render (int x, int y, Camera screen)
	{
		screen.renderTile(x << 4, y << 4, this);
	}

}
