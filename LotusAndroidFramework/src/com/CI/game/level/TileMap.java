package com.CI.game.level;

import java.util.ArrayList;

import com.CI.game.level.tile.Tile;

public class TileMap 
{
	private int width, height;
	private static ArrayList<Tile> walls = new ArrayList<Tile>() ;
	private Tile[][] tileGrid;
	private Cluster[] clusters;
	/*
	 * # # # # # # # *
	 * # * * * * * # *
	 * # * # # # * # *
	 * # * # # # * # *
	 * # * * * * * # *
	 */
	public TileMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		setTileGrid(new Tile[width][height]);
		//setWalls(new ArrayList<Tile>());

	}
	
	public TileMap()
	{
		
	}
	
	ArrayList<Tile> in_bounds(ArrayList<Tile> r)// get current position in grid struct
	{
		ArrayList<Tile> newR = new ArrayList<Tile>();
		// is each item in index apart of the map
		for(int i = 0; i < r.size(); i++ )
		{
			int x = r.get(i).getTileX();
			int y = r.get(i).getTileY();
			if(x < 0 || x < width)// if x pos of tile is off grid remove tile
			{
				r.remove(i) ;
			}
			else if(y < 0 || y < height)// if y pos of tile is off grid, remove
			{
				r.remove(i);
			}else if(r.get(i) != null)
			{
				// add instance to newR list
				newR.add(r.get(i));
			}
		}
		return newR;
	}
	
	public boolean passable(Tile r)//check for walls/solids objects
	{
		ArrayList<Tile> newR = new ArrayList<Tile>();
		boolean isSolid = false;
			for(int w = 0; w < getWalls().size(); w++)// loop thru walls
			{
				System.out.println("wX: " + getWalls().get(w).getTileX() + "wY: " + getWalls().get(w).getTileY() +"X: " +  r.getTileX()+ " Y: " + r.getTileY());
				if(r.getTileX() == getWalls().get(w).getTileX() && r.getTileY() == getWalls().get(w).getTileY())// if tiles 
				{
					//r.remove(i);
					isSolid = true;
					//return isSolid;
				}
				else
				{
					// add to new list
					//newR.add(r);
					isSolid = false;
					//return isSolid;
				}
				
			}
		
		
		return isSolid;
	}
	
	public ArrayList<Tile> neighbors(Tile currentTile)// get neighbor for current tile
	{
		int x,y;
		
		x = currentTile.getTileX();
		y = currentTile.getTileY();
		// find neighbor tiles from current tile
		
		ArrayList<Tile> results = new ArrayList<Tile>();
		ArrayList<Tile> t = new ArrayList<Tile>();

		//Right
		if(x < this.width-1)
		{
			if(!getTileGrid()[x+1][y].solid())// if passable add to list
			{
				t.add(getTileGrid()[x+1][y]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if(y < this.height-1)
		{
			if(!getTileGrid()[x][y+1].solid())
			{
				t.add(getTileGrid()[x][y+1]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if(x > 0)
		{
			if(!getTileGrid()[x-1][y].solid())
			{
				t.add(getTileGrid()[x-1][y]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if(y > 0)
		{
			if (!getTileGrid()[x][y-1].solid())
			{
				t.add(getTileGrid()[x][y-1]);
			}			else
			{
				System.out.println("solid");
			}
		}
		//Tile n = getTileGrid()[x][y];
		//t.add(n);
		

		// check if tiles are in bounds of grid if not, remove
		//results = in_bounds(t);// filter
		// then check tiles for solid/walls, and remove wall tiles from result
		//t = new ArrayList<Tile>(passable(t));// filter
		//t.add(getTileGrid()[x][y]);
		return t;
	}
	
	// slice this tile map into chunks(clusters) to make path finding very efficient
	public void initClusters() 
	{
		// Cluster initialization	
		final int CLSTR_MASK = 6;
		int clusterWidth = this.width / CLSTR_MASK;// 6
		int clusterHeight = this.height / CLSTR_MASK;// 6
		clusters = new Cluster[CLSTR_MASK* CLSTR_MASK];
			
		for(int i = 0; i < clusters.length; i++)
		{
			this.clusters[i] = new Cluster(clusterWidth, clusterHeight);
		}
				
		for(int y = 0; y < this.height; y++)
		{
			for(int x = 0; x < this.width; x++)
			{
				//int cHeight = clusterHeight;
				//int cWidth = clusterWidth;
				int cid = ((x / CLSTR_MASK) + (y / CLSTR_MASK)) * CLSTR_MASK;// id of cluster sector
				
				//if(y > height){ /*cHeight = height - y; */	}
				//if(x > width) {	/*cWidth = width - x;*/     }

				// (x / CLSTR_MASK) + (y / CLSTR_MASK)* CLSTR_MASK)
				
				// draw clusters for test
				clusters[cid].getTileGrid()[x % CLSTR_MASK][y % CLSTR_MASK] = getTileGrid()[x][y];
								
				System.out.println("");
			}
		}
		
		

		// loop thru every cluster to identify entrances between adjacent clusters. 
		for(int y = 0; y < CLSTR_MASK; y++)
		{
			for(int x = 0; x < CLSTR_MASK; x++)
			{
				// for each cluster, check/find adj clusters, check edges between adj 
				// clusters for an obstacle free area (entrance).
			
			
				// clusters to the left(x == 0) check for if, do not check left
				if(y > clusterHeight)
				{
					// clusters[x + (y * CLSTR_MASK)]
				}
				else if(x == 0)
				{
					// check only below and to the right tiles
					//clusters[x + ( Y CLSTR_MASK)]
					//if (y > clusterHeight)
				}
				
				
				// clusters on the top row (y == 0), do not check above
				
				
				
				// clusters on the right column(x == clusterWidth), do not check right
				
				
				
				// clusters to the bottom row (y == clusterHeight, ), do not check below
				
				
			}
		}
		
		// find adj clusters, compare connecting edges between them,
		// for an obstacle free area as the entrance.
		

	}
	
	public void initEntrances()
	{

	}
	
	public void update()
	{
		
	}
	
	// Use this method to add a tile to the tilemap, by finding &
	// just updating that one sector the tile should placed in.
	public void addTile()
	{
		// find chunk/ cluster sector and then step thru all neccessary tile methods
		
		// update tile in chunk sector
	}

	public Tile[][] getTileGrid() {
		return this.tileGrid;
	}

	public void setTileGrid(Tile[][] tileGrid) 
	{		
		this.tileGrid = tileGrid;
	}
	
	public int cost(Tile c, Tile n)
	{
		return Math.abs(c.getTileX() - n.getTileX()) + Math.abs(c.getTileY() - n.getTileY());
	}

	public ArrayList<Tile> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<Tile> walls) {
		this.walls = walls;
	}
}
