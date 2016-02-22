package com.CI.game.level;

import java.util.ArrayList;
import java.util.Scanner;

import com.CI.game.SampleGame;
import com.CI.game.level.tile.Tile;

public class SpawnLevel extends Level
{
	
	public SpawnLevel(String path) 
	{
		super(path);
	}
	
	protected void loadLevel(String path)
	{
		
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		Scanner scanner = new Scanner(path);
		
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
		
		height = lines.size();

		setTilesInt(new Tile[width * height]);// set width/height of tileMap, representing 16x16 tiles encumbering 800x480 pixels (50, 30)
		//tileMap = new Tile[width][height];

		for (int y = 0; y < height; y++) 
		{
			String line = (String) lines.get(y);
			for (int x = 0; x < width; x++) 
			{

				//if (x < line.length()) 
				//{
					char ch = line.charAt(x);
					if(ch == '1')
					{
						Tile t = new Tile(16, x, y, Character.getNumericValue(ch));
						//tileMap[x][y] = t;
						tilesInt[x+y] = t;
						//tilearray.add(t);
					}
					else
					{
						Tile t = new Tile(16, x, y, Character.getNumericValue(ch));
						tilesInt[x+y] = t;
						//tileMap[x][y] = t;
						//tilearray.add(t);
					}
					
				//}

			}
		}
	}
	
}
