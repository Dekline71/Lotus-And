package com.CI.game.entity.armyunit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.CI.game.GameManager;
import com.CI.game.SampleGame;
import com.CI.game.entity.Camera;
import com.CI.game.entity.Entity;
import com.CI.game.entity.Peasant;
import com.CI.game.entity.Robot;
import com.CI.game.entity.enemy.Enemy;
import com.CI.game.graphics.Animation;
import com.CI.game.graphics.Assets;
import com.CI.game.graphics.Background;
import com.CI.game.graphics.GameScreen;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.TileMap;
import com.CI.game.level.tile.Tile;
import com.CI.lotusFramework.Graphics;
import com.CI.lotusFramework.implementation.AndroidGame;
import com.CI.lotusFramework.implementation.AndroidGraphics;

import android.graphics.Rect;


public class ArmyUnit extends Peasant
{

	private int power, speedX;

	private Background bg = GameScreen.getBg1();
	private Robot robot = GameScreen.getRobot();
	private Animation a;
	public Rect r = new Rect(0, 0, 0, 0);

	protected int direction = 0;
	private int movementSpeed;

	private boolean walking = false;
	private boolean isAtkReady = false;
	private boolean searched = false;// 
	
	private LinkedBlockingDeque<Tile> pathToTarget;
	private int gridMoves; // moves entity can move during each turn/day
	
	protected enum Direction
	{
		LEFT, RIGHT, UP, DOWN
	}
	
	public ArmyUnit(int x, int y)
	{
		super(x, y);
		direction = 2;
	}
	
	public ArmyUnit(int x, int y, Entity t)
	{
		super(x, y);
		//this.target1 = t;
		direction = 2;
		setImage(Sprite.peasSpriteD.getImage());
		setGridMoves(2);
	}
	
	public ArmyUnit()
	{
		
	}
	
	private void setGridMoves(int i)
	{
		if (i > 3)
		{
			this.gridMoves = 3;
		}
		else if ( i < 0)
		{
			i = 0;
		}
		else
		{
			this.gridMoves = i;
		}
	}
	
	public void render(Camera screen)
	{
		screen.renderArmyEntity(getCenter64X(), getCenter64Y(), this);
	}
	
	private Tile last;
	private boolean canMove;
	// Behavioral Methods
	public void update() 
	{
		this.colliderBounds.set(getPixel32X()-2 ,getPixel32Y()-2, getPixel32X()+34, getPixel32Y() + 34);
		this.atkColliderBounds.set(getPixel32X()-96, getPixel32Y()-96, getPixel32X() + 96, getPixel32Y() + 96);
			
		if (getHealth() <= 0) 
		{
			die();
		} 
		else 
		{
			//move(getCenter64X(), getCenter64Y());
			//target1 = new Entity(10,1);
			
			//checkCollision();
			if (!searched && this.getTarget() != null )// && getisTargetFound() == false
			{
				pathToTarget = new LinkedBlockingDeque<Tile>( breadthFirstSearch(GameManager.level.getArmyMovementTileMap()));
				
				 last = pathToTarget.peekLast();
				searched = true;
				
			}
			Tile[][] tm;
			//Entity t = getTarget();
			//Entity t2 = getTarget2();
			// tm = GameManager.getTileMap();
			//if (getCenterX() >= 0 || getCenterY() >= 0) {
			if(doesEntityHaveGridMoves())
			{
				if (last != null)// if there is a path
				{
					setisTargetFound(true);

					if(last.getTileX() != getCenter64X() && last.getTileY() != getCenter64Y()  && pathToTarget.peekFirst()!=null && last != null || !pathToTarget.isEmpty() || last != null )
					{
						moveThruPath();
				//}
				/*
				 * if(t != null && getisTargetFound() == false) {
				 * //System.out.println("Target x: " + target.getCenterX());
				 * 
				 * findPathToTarget();
				 * 
				 * //setCenterX(getCenterX() + speedX);
				 * //setCenterY(getCenterY() + speedX); }
				 */

			} 

		}
			setCanMove(false);
			}
			//if (getisAtkReady() == true) 
			//{
				// attack();
			//}
		}				
	//attack();
		/*
		//follow();
	
		if(target != null)
		{
		findPathToTarget();
		}

		r.set(getCenterX() - 25, getCenterY() - 25, getCenterX() + 25, getCenterY() + 35);

		if (Rect.intersects(r, Robot.yellowRed)) 
		{
			checkCollision();
		}
		*/
		

		
		
		/*
		if(this.getCenterX()  < target.getCenterX() )
		{
			setMovementSpeed(1);
		}
		else if(this.getCenterX() > target.getCenterX() )
		{
			setMovementSpeed(-1);
		}
		else
		{
			setMovementSpeed(0);
		}
		
		
		if(this.getCenterY() < target.getCenterY())
		{
			setMovementSpeed(1);
		}
		else if(this.getCenterY() > target.getCenterY())
		{
			setMovementSpeed(-1);
		}
		*/

	}
	
	// Check if entity has enough gridMoves to move

	public boolean doesEntityHaveGridMoves() 
	{
		if(this.gridMoves <= 3 && this.gridMoves > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// Move Entity thru found path by one step each time this method is called
	public void moveThruPath()
	{
		Tile cur = pathToTarget.poll();
		//System.out.println("X: " + cur.getTileX() + " Y: " + cur.getTileY());
		if(cur == null){setisTargetFound(false);walking = false;
		}// if no more steps/path, entity has no target & has not found the no target
		
		if(cur != null)// if there are steps to take
		{
			
		if(cur.getTileX() == (getCenter64X()+1) && cur.getTileY() == getCenter64Y())// check if cur to the right of player
		{
			walking = true;
			System.out.println("Moved Right");
			moveRight();
			this.gridMoves--;
		}
		else if(cur.getTileY() == (getCenter64Y()-1) && cur.getTileX() == getCenter64X() )// check if cur is above player
		{
			walking = true;
			System.out.println("Moved Up");
			moveUp();
			this.gridMoves--;
		}
		else if(cur.getTileX() == (getCenter64X()-1) && cur.getTileY() == getCenter64Y() )// check if cur is left of plyr
		{
			walking = true;
			System.out.println("Moved Left");
			moveLeft();
			this.gridMoves--;
		}
		else if(cur.getTileY() == (getCenter64Y()+1) && cur.getTileX() == getCenter64X() ) // check if cur is below plyr
		{
			walking = true;
			System.out.println("Moved Down");
			moveDown();
			this.gridMoves--;
		}
		else
		{
			walking = false;
		}
		System.out.println("CenterX: " + getCenter64X() + " CenterY: " + getCenter64Y());
		}
	}
	
	private boolean getisAtkReady() 
	{
		return this.isAtkReady;
	}

	public void findPathToTarget(Entity t)
	{
		if(getisTargetFound() == false)
		{
			//if(tm[x][y].checkSolid())// Check for solid tile
		if(this.getPixel64Y() == t.getPixel64Y())
		{
			if(this.getPixel64X() == t.getPixel64X())
			{
				setisTargetFound(true);
				setisAtkReady(true);
				
			}
			else
			{
				// move left or right toward target
				
				// this entity is to the right of target, move left
				if(this.getPixel64X() > t.getPixel64X())
				{
					moveLeft();

				}
				else// move right
				{
					moveRight();
				}
				
			}
		}
		else
		{
			//move up or down
			
			// this entity is below target, move up
			if(this.getPixel64Y() > t.getPixel64Y())
			{
				moveUp();
			}
			else// move down
			{
				moveDown();
			}
		}
		
		}
	
		else
		{
			speedX = 0;
			setCenter32X(getCenter64X() + speedX);
			setCenter32Y(getCenter64Y() + speedX);
			setPixel32X(getPixel64X() + speedX);
			setPixel32Y(getPixel64Y() + speedX);
			
			move(getCenter64X(), getCenter64Y());
			
			//System.out.println("Target is null");
		}
}
	private void setisAtkReady(boolean b) 
	{
		this.isAtkReady = b;
		
	}

	private void move(int xAxis, int yAxis)
	{
		if(xAxis != 0 && yAxis != 0)// if on axis
		{
			// run for each axis, for separate collision
			move(xAxis,0);
			move(0,yAxis);
			return;
		}
		//if (xAxis > 0){direction = 1;}// Right
		//if (xAxis < 0){direction = 3;}// Left
		//if (yAxis > 0){direction = 2;}// Down
		//if (yAxis < 0){direction = 0;}// Up
		
		if(direction == 0)
		{
			//setSprite(Sprite.sorcSpriteU);
			if (walking)
			{
				this.a = Assets.peasAnimUp;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteU.getImage());
			}
		} 
		else if(direction == 1)
		{
			setSprite(Sprite.peasSpriteR);
			if (walking)
			{
				this.a = Assets.peasAnimRight;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteR.getImage());
			}
		}
			
		else if(direction == 2)
		{
			setSprite(Sprite.peasSpriteD);
			if (walking)
			{
				this.a = Assets.peasAnimDown;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteD.getImage());
			}
		}
		
		else if(direction == 3)
		{ 
			setSprite(Sprite.peasSpriteL);
			if (walking)
			{
				this.a = Assets.peasAnimLeft;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteL.getImage());
			}
		}
		/*
		if(!collision(xAxis, yAxis))// if no collision, move in direction
		{				
			x += xAxis;
			y += yAxis;
		}
		else 
		{
			Particle p =  new Particle(x, y, 50);
			level.add(p);
			//Sound.hit.play();
			
		}
	}
	*/
	}
	
	private void moveLeft() 
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() - 64);
		direction = 3;
		move(getCenter64X(), getCenter64Y());
	}

	private void moveRight() 
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() + 64);
		direction = 1;
		move(getCenter64X(), getCenter64Y());
	}

	private void moveDown() 
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() + 64);
		direction = 2;
		move(getCenter64X(), getCenter64Y());
	}

	private void moveUp() 
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() - 64);
		direction = 0;
		move(getCenter64X(), getCenter64Y());
	}


	private void checkCollision() 
	{		
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
	
	// Check for collision w/ Enemy army
		LinkedList entities = getLevel().getEnemyArmyEntities();
		for(int i = 0; i < entities.size(); i++)
		{
			Enemy e = (Enemy) entities.get(i);

				if (Rect.intersects(this.colliderBounds, e.colliderBounds)) // if normal boundaries collide w/ cur enemies boundary.
				{
					//e.setVisible(false);
					//System.out.println("Collision!");
					if(this.target1 != null) // if this entity has a target
					{
						//if(target1 == e)
						//this.target1 = e;
						if(target1.getHealth() <= 0)
						{
							target1 = null;
						}
						else
						{
						walking = false;
						this.isAtkReady = true;
						searched = true;
						canMove = false;
						setisTargetFound(true);
						attack();
						}
					}
					else
					{	
						this.target1 = e;
					
						if(target1.getHealth() <= 0)
						{
							target1 = null;
						}else{
							walking = false;
						this.isAtkReady = true;
						searched = true;
						canMove = false;
						setisTargetFound(true);
						attack();
						}
					}
					// Make Enemy list to hold enemies and check and compare against player troops for coll
				}			
		}
		
		if (target1  == null)
		{
			// check for aggressive attack radius
			entities = getLevel().getEnemyArmyEntities();
			for(int i = 0; i < entities.size(); i++)
			{
				// Something here
				/*if(target1!=null )
				{
					int j = entities.indexOf(target1);
					if(j != -1)
					{
					this.target1 = (Entity) entities.get(j);
					}
					if(target1.getHealth() <= 0)
					{
						target1 = null;
					}
					else
					{
					searched = false;
					setisAtkReady(false);
					setisTargetFound(false);
					canMove = true;
					}
				}*/
				Enemy e = (Enemy) entities.get(i);
				
				if (Rect.intersects(this.atkColliderBounds, e.colliderBounds)) 
				{
				
					//e.setVisible(false);
				if(target1==null )
				{
					
					this.target1 = e;
					if(target1.getHealth() <= 0)
					{
						target1 = null;
					}
					else
					{
					searched = false;
					setisAtkReady(false);
					setisTargetFound(false);
					canMove = true;
					}
				}
				else // if target1 != null
				{
				/*
					//this.target1 = e;
					if(target1.getHealth() <= 0)
					{
						target1 = null;
					}else{
					searched = false;
					setisAtkReady(false);
					setisTargetFound(false);
					canMove = true;
					}*/
					//if(target1 == entities.get(i))
					//{
						//System.out.println("Target index : " + i);
						//this.target1 = e;
						//searched = false;
					//}else{
					//target1 = null;
					//searched = true\\;
					setisAtkReady(false);//}
				}

					
					
					
					// Make Enemy list to hold enemies and check and compare against player troops for coll
				}
				}	
		}
		
		/* OG collision code
		if (Rect.intersects(r, et.colliderBounds)|| Rect.intersects(r, Robot.rect2)
				|| Rect.intersects(r, Robot.rect3) || Rect.intersects(r, Robot.rect4)) 
		{

		}
		*/
		}
	
	
	public ArrayList<Tile> breadthFirstSearch(TileMap tileMap)// graph, start/cur pos, goal/target pos
	{
		//TileMap tileMap = new TileMap(50,25);
		Tile current;
		Tile startTile = tileMap.getTileGrid()[this.getCenter64X()][this.getCenter64Y()];
		ArrayList<Tile> visited = new ArrayList<Tile>();
		ArrayList<Tile> results = new ArrayList<Tile>();
		ArrayList<Tile> cameFrom = new ArrayList<Tile>();
		ArrayList<Integer> costSoFar = new ArrayList<Integer>();// track of total movement cost from start position
		ArrayList<Tile> path = new ArrayList<Tile>();
		ArrayList<Tile> finalPath = new ArrayList<Tile>();
		//ArrayList<Integer> distance = new ArrayList<Integer>();
		boolean isVisited = false;
		visited.add(startTile);
		startTile.setCameFrom(null);
		cameFrom.add(startTile);
		costSoFar.add(0);
		//distance.add(0);
		
		Queue<Tile> frontier = new LinkedBlockingQueue <Tile>();
		frontier.add(startTile);
		do
		{
			current = frontier.poll();
			// check thru each neighbor, 
			// add to frontier if not visited b4
			System.out.println("Visiting: " + "X: "  + current.getTileX() + " Y: " + current.getTileY());
			int tce = this.getTarget().getCenter64X();
			if (current.getTileX() == tce && current.getTileY() == this.getTarget().getCenter64Y())
			{					
				path.add(current);
			
				break;
			}		
			
			results = new ArrayList<Tile>(tileMap.neighbors(current));// hld neighbors
			System.out.print("\nResult: " );
			for(int i = 0; i < results.size();i++)// loop thru neighbors
			{
				Tile next = results.get(i);
				
				System.out.print("X: " + results.get(i).getTileX() + " Y: " + results.get(i).getTileY()+",");
				int newCost = costSoFar.get(costSoFar.size()-1) + tileMap.cost(current,next); 
				// if(costSoFar.contains(i) || newCost < costSoFar.get(costSoFar.size()-1))
				// {
					 
				// }
					
					// check if neighbors have been visited already
						if( visited.contains(next)								)
						{
							//isVisited = true;
							System.out.print(" Has been visited.");
						}
						else				
						{
							costSoFar.add(newCost);
							 int priority = heuristic(this.getTarget(), next);
							ArrayList<Tile> a = new ArrayList<Tile>(frontier);
							if(a.size() <= priority)
							{
								a.ensureCapacity(priority);
							a.add(next);
							}
							else
							{
								a.add(priority, next);
							}
							//frontier.clear();
							frontier = new LinkedBlockingQueue<Tile>(a);
							//for(int ii = 0; ii < a.size(); ii++)
							//{
								//frontier.add(a.get(ii));
							//}
							
							System.out.print(" +ed to Q/list.");
							next.setCameFrom(current);//linked cameFrom node
							frontier.add(next);
							visited.add(next);
							
							cameFrom.add(next);//prev node to next tile
					//distance.add( 1 + distance.get(i));

						}
						//}		
					//System.out.println();

			}
			System.out.println();
		}while(!frontier.isEmpty());
		System.out.println("Visited Result:");
		for(int i = 0; i < visited.size(); i++)
		{
			System.out.print("X: " + visited.get(i).getTileX() + " Y: " + visited.get(i).getTileY() + ", ");
		}
		
		// reconstruct path to target
		int h = heuristic(this.getTarget(), startTile);
			
			Tile cur;
				int n = cameFrom.indexOf(tileMap.getTileGrid()[this.getTarget().getCenter64X()][getTarget().getCenter64Y()]);// find goal/index in list
				if(n != -1)
				{
					cur = cameFrom.get(n);
				}
				else
				{
					cur = null;
				}
				
				while( cur !=  null )
				{
					
				cur = cur.getCameFrom();//get prev tile from current

				if(cur != null)
				{
			
				System.out.println("Adding " + "X:" + cur.getTileX() + " Y: " + cur.getTileY());
				path.add(cur);
				}else{break;}
				
				}
				for(int i = path.size()-1; i >= 0; i--)// reverse path to start to goal
				{
					if(path.get(i)!= null)
					{
						finalPath.add( path.remove(i));
					}
				}
			System.out.println("Path: ");
			for(int i = 0; i < finalPath.size(); i++)
			{
				System.out.print("X: " + finalPath.get(i).getTileX() + " Y: " + finalPath.get(i).getTileY() + ", ");
			}
		//return visited;
			return finalPath;
	}

	public int heuristic(Entity t, Tile n) 
	{
		return Math.abs(t.getCenter64X() - n.getTileX())
				+ Math.abs(t.getCenter64Y() - n.getTileY()) ;
	}
	
	private void moveNone() 
	{
		setMovementSpeed(0);
		//speedX = movementSpeed;
		setCenter32Y(getCenter32Y() + movementSpeed);
		setPixel32Y(getPixel32Y() + movementSpeed);	
		//direction = 0;
		move(getCenter32X(), getCenter32Y());		
	}

	private boolean getCollision() 
	{	
		//Entity et = (Entity)Entity.getEntities().get(0);
		boolean b = false;
	
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
	
		LinkedList entities = getLevel().getEnemyArmyEntities();
		for(int i = 0; i < entities.size(); i++)
		{
			Enemy e = (Enemy) entities.get(i);
			
				//e.update();
				if (Rect.intersects(this.colliderBounds, e.colliderBounds)) 
				{
					//System.out.println("Collision!");
				
					b = true;
					return b; 
					// Make Enemy list to hold enemies and check and compare against player troops for coll
				}
				else
				{
					b = false;
					return b;
				}
		}	
		return b;
				
		}
	
	public void attack()
	{
		if(getisAtkReady())
		{
			//if(getCollision())
			//{// check if colliding to attack
			// Do attack dmg stuff to target entity till dead
			//this.target.die();
				if(target1 != null)
				{
				this.target1.setHealth(-1);
				//setisTargetFound(false);
				}
				//target1 = null;
			//}
		}
	}

	public void follow() 
	{		
		if (getCenter32X() < -95 || getCenter32X() > 810)
		{
			movementSpeed = 0;
		}

		else if (Math.abs(robot.getCenterX() - getCenter32X()) < 5) // change robot to target
		{
			movementSpeed = 0;
		}

		else 
		{

			if (robot.getCenterX() >= getCenter32X()) 
			{
				movementSpeed = 1;
			} else 
			{
				movementSpeed = -1;
			}
		}

	}

	public void die() 
	{
		setVisible(false);
	}

	public int getPower() 
	{
		return power;
	}

	public int getSpeedX() 
	{
		return speedX;
	}

	public Background getBg()
	{
		return bg;
	}
	
	public Animation getAnimation()
	{
		return this.a;
	}
	
	public int getMovementSpeed()
	{
		return this.movementSpeed;
	}
	
	public void setMovementSpeed(int s)
	{
		this.movementSpeed = s;
	}

	public void setPower(int power) 
	{
		this.power = power;
	}

	public void setSpeedX(int speedX) 
	{
		this.speedX = speedX;
	}
	
	public void setSearched(boolean b)
	{
		this.searched = b;
	}

	public void setBg(Background bg) 
	{
		this.bg = bg;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public void nextTurnUpdate() 
	{
		// update movement/combat variables for next turn
		if (this.gridMoves == 2)
		{
			this.gridMoves++;
		}
		else if (this.gridMoves < 2)
		{
			this.gridMoves += 2;
		}
		else if (this.gridMoves >= 3)
		{
			this.gridMoves = 3;
		}
	}
	
	
	/*	Original AI on 32x32 px tile grid
	 * 
	 * public ArrayList<Tile> breadthFirstSearch(TileMap tileMap)// graph, start/cur pos, goal/target pos
	{
		//TileMap tileMap = new TileMap(50,25);
		Tile current;
		Tile startTile = tileMap.getTileGrid()[this.getCenter64X()][this.getCenter32Y()];
		ArrayList<Tile> visited = new ArrayList<Tile>();
		ArrayList<Tile> results = new ArrayList<Tile>();
		ArrayList<Tile> cameFrom = new ArrayList<Tile>();
		ArrayList<Integer> costSoFar = new ArrayList<Integer>();// track of total movement cost from start position
		ArrayList<Tile> path = new ArrayList<Tile>();
		ArrayList<Tile> finalPath = new ArrayList<Tile>();
		//ArrayList<Integer> distance = new ArrayList<Integer>();
		boolean isVisited = false;
		visited.add(startTile);
		startTile.setCameFrom(null);
		cameFrom.add(startTile);
		costSoFar.add(0);
		//distance.add(0);
		
		Queue<Tile> frontier = new LinkedBlockingQueue <Tile>();
		frontier.add(startTile);
		do
		{
			current = frontier.poll();
			// check thru each neighbor, 
			// add to frontier if not visited b4
			System.out.println("Visiting: " + "X: "  + current.getTileX() + " Y: " + current.getTileY());
			
			if (current.getTileX() == target1.getCenter32X() && current.getTileY() == target1.getCenter32Y())
			{break;}		
			
			results = new ArrayList<Tile>(tileMap.neighbors(current));// hld neighbors
			System.out.print("\nResult: " );
			for(int i = 0; i < results.size();i++)// loop thru neighbors
			{
				Tile next = results.get(i);
				
				System.out.print("X: " + results.get(i).getTileX() + " Y: " + results.get(i).getTileY()+",");
				int newCost = costSoFar.get(costSoFar.size()-1) + tileMap.cost(current,next); 
				// if(costSoFar.contains(i) || newCost < costSoFar.get(costSoFar.size()-1))
				// {
					 
				// }
					
					// check if neighbors have been visited already
						if( visited.contains(next)								)
						{
							//isVisited = true;
							System.out.print(" Has been visited.");
						}
						else				
						{
							costSoFar.add(newCost);
							 int priority = heuristic(target1, next);
							ArrayList<Tile> a = new ArrayList<Tile>(frontier);
							if(a.size() < priority)
							{
								a.ensureCapacity(priority);
							a.add(next);
							}
							else
							{
								a.add(priority, next);
							}
							//frontier.clear();
							frontier = new LinkedBlockingQueue<Tile>(a);
							//for(int ii = 0; ii < a.size(); ii++)
							//{
								//frontier.add(a.get(ii));
							//}
							
							System.out.print(" +ed to Q/list.");
							next.setCameFrom(current);//linked cameFrom node
							frontier.add(next);
							visited.add(next);
							
							cameFrom.add(next);//prev node to next tile
					//distance.add( 1 + distance.get(i));

						}
						//}		
					//System.out.println();

			}
			System.out.println();
		}while(!frontier.isEmpty()&& target1!= null);
		System.out.println("Visited Result:");
		for(int i = 0; i < visited.size(); i++)
		{
			System.out.print("X: " + visited.get(i).getTileX() + " Y: " + visited.get(i).getTileY() + ", ");
		}
		
		// reconstruct path to target
		int h = heuristic(target1, startTile);
			
			Tile cur;
				Tile goal = tileMap.getTileGrid()[target1.getCenter32X()-1][target1.getCenter32Y()];
				int n = cameFrom.indexOf(tileMap.getTileGrid()[target1.getCenter32X()][target1.getCenter32Y()]);// find goal/index in list
				if(n != -1)
				{
					cur = cameFrom.get(n);
				}
				else
				{
					cur = null;
				}
				
				while( cur !=  null )
				{
					
				cur = cur.getCameFrom();//get prev tile from current

				if(cur != null)
				{
			
				System.out.println("Adding " + "X:" + cur.getTileX() + " Y: " + cur.getTileY());
				path.add(cur);
				}else{break;}
				
				}
				for(int i = path.size()-1; i > 0; i--)// reverse path to start to goal
				{
					if(path.get(i)!= null)
					{
				finalPath.add( path.remove(i));
					}
				}
			System.out.println("Path: ");
			for(int i = 0; i < finalPath.size(); i++)
			{
				System.out.print("X: " + finalPath.get(i).getTileX() + " Y: " + finalPath.get(i).getTileY() + ", ");
			}
		//return visited;
			return finalPath;
	}
	 */

	
}