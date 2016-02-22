package com.CI.game.entity.enemy;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import android.graphics.Rect;

import com.CI.game.GameManager;
import com.CI.game.entity.Camera;
import com.CI.game.entity.Entity;
import com.CI.game.entity.armyunit.ArmyUnit;
import com.CI.game.entity.buildings.BuildingEntity;
import com.CI.game.graphics.Animation;
import com.CI.game.graphics.Assets;
import com.CI.game.graphics.GameScreen;
import com.CI.game.graphics.Sprite;
import com.CI.game.level.Level;
import com.CI.game.level.TileMap;
import com.CI.game.level.tile.Tile;

// Class for enemy soldiers
public class Enemy extends Entity 
{
	private int health;
	private int gridMoves;
	protected int direction = 0;
	private int movementSpeed;
	private boolean walking = false;
	private boolean isAtkReady = false;
	private Animation a;
	private boolean searched = false;
	private LinkedBlockingDeque<Tile> pathToTarget ;
	
	public Enemy()
	{

	}

	public Enemy(int x, int y, Level l)
	{
		super(100, x, y);
		this.level = l;
		// setHealth(5);
		setImage(Sprite.enemSpriteD.getImage());
		setSprite(Sprite.enemSpriteD);
		setTarget(new Entity(12, 12));
		setGridMoves(2);

	}
	
	private void setGridMoves(int i) 
	{
		if (i > 3)
		{
			this.gridMoves = 3;
		}
		else if( i < 0) 
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
		screen.renderArmyEntity(getCenter64X(), getCenter64Y() , this);
	}
	
private Tile last;
private boolean canMove;
	public void update() 
	{		
		this.colliderBounds.set(getPixel32X(), getPixel32Y(), getPixel32X() + 32,	getPixel32Y() + 32);
		this.atkColliderBounds.set(getPixel32X(), getPixel32Y() - 64, getPixel32X() + 400, getPixel32Y() + 400);
		
		
		// check if barrack has been placed, if so move towards it to attack
		
		// find/atk player barrack/troops
		if (getHealth() <= 0) 
		{
			die();
		} else 
		{
			// checkCollision();
			if (!searched && target1 != null) 
			{
				pathToTarget = new LinkedBlockingDeque<Tile>( breadthFirstSearch(GameManager.level.getArmyMovementTileMap()));
				
				 last = pathToTarget.peekLast();
				 pathToTarget.removeFirst();
				searched = true;
			}
			Tile[][] tm;
			//Entity t = getTarget();
			//Entity t2 = getTarget2();
			// tm = GameManager.getTileMap();
			//if (getCenterX() >= 0 || getCenterY() >= 0) {
			if(doesEntityHaveGridMoves())
			{
				if (last != null)
				{

					
					if(last.getTileX() != this.getCenter64X() && last.getTileY() != this.getCenter64Y()  && pathToTarget.peekFirst()!=null && last != null || !pathToTarget.isEmpty() || last != null )
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
						if (getisAtkReady()) 
						{
							// attack();
						}
					} 

				}
				
			setCanMove(false);
			}
		}
	}

	// Check if entity has enough gridMoves to move
	public boolean doesEntityHaveGridMoves() 
	{
		if(this.gridMoves <= 3 && this.gridMoves >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isSpaceOccupied(Tile cur)
	{
		//Tile cur = pathToTarget.peek();
		boolean isSpaceOccupied = true;
		if (cur != null)
		{
		for(int i = 0; i < GameManager.level.getEnemyArmyEntities().size(); i++)
		{
			if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == cur.getTileX() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == cur.getTileY())
			{
				isSpaceOccupied = true;
				break;
			}
			else
			{
				isSpaceOccupied = false;
			}
		}
		if(isSpaceOccupied == false)
		{
			for(int i = 0; i < GameManager.level.getPlayerArmyEntities().size(); i++)
			{
				if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() == cur.getTileX() && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() == cur.getTileY())
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
		}
		return isSpaceOccupied;
	}
	
	public void moveThruPath()
	{
		//System.out.println("YAY!!" + "X: " + cur.getTileX() + " Y: " + cur.getTileY());
		// Check if entities next move is on an existing entities occupied tile.
		Tile curCopy = pathToTarget.peek();
		
		
		if (curCopy != null && isSpaceOccupied(curCopy) == false)
		{
			Tile cur = pathToTarget.poll();

			
		if (cur.getTileX() == (this.getCenter64X()+1) && cur.getTileY() == this.getCenter64Y())// check if cur to the right of player
		{
			walking = true;
			System.out.println("Moved Right");
			moveRight();
			this.gridMoves--;
		}
		else if (cur.getTileY() == this.getCenter64Y()-1 && cur.getTileX() == this.getCenter64X() )// check if cur is above player
		{
			walking = true;
			System.out.println("Moved Up");
			moveUp();
			this.gridMoves--;
		}
		else if (cur.getTileX() == this.getCenter64X()-1 && cur.getTileY() == this.getCenter64Y() )// check if cur is left of plyr
		{
			walking = true;
			System.out.println("Moved Left");
			moveLeft();
			this.gridMoves--;
		}
		else if (cur.getTileY() == this.getCenter64Y()+1 && cur.getTileX() == this.getCenter64X() ) // check if cur is below plyr
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
		
		System.out.println("CenterX: " + this.getCenter64X() + " CenterY: " + this.getCenter64Y());
		
		}
	}
	
	@SuppressWarnings("unchecked")
	private void findPathToTarget() {
		if (getisTargetFound() == false) {

			if (this.getPixel32Y() == getTarget().getPixel32Y()) {

				if (this.getPixel32X() == getTarget().getPixel32X()) {
					// Check for solid tile

					/*
					 * LinkedList<ArmyUnit> entitiess =
					 * getLevel().getPlayerArmyEntities(); for(int i = 0; i <
					 * entitiess.size(); i++) { ArmyUnit e = entitiess.get(i);
					 * if(e != null) { //e.update(); if (getPixelY() ==
					 * e.getPixelY()) { moveUp(); setisTargetFound(true);
					 * setisAtkReady(true);
					 * 
					 * } } }
					 */
					setisTargetFound(true);
					// setisAtkReady(true);

				} else {
					// move left or right toward target

					// this entity is to the right of target, move left
					if (this.getPixel32X() > getTarget().getPixel32X()) {
						moveLeft();

					} else// move right
					{
						moveRight();
					}

				}
			} else {
				// move up or down

				// this entity is below target, move up
				if (this.getPixel32Y() > getTarget().getPixel32Y()) {
					moveUp();
				} else// move down
				{
					moveDown();
				}
			}

		}

		else {
			movementSpeed = 0;
			setCenter64X(getCenter64X() + movementSpeed);
			setCenter64Y(getCenter64Y() + movementSpeed);
			setPixel64X(getPixel64X() + movementSpeed);
			setPixel64Y(getPixel64Y() + movementSpeed);

			move(getCenter64X(), getCenter64Y());

			// System.out.println("Target is null");
		}

	}

	@SuppressWarnings("unchecked")
	private void checkCollision() {
		// System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());

		// check/find keep/barrack
		LinkedList<BuildingEntity> bentities = getLevel()
				.getPlayerBuildingEntities();
		for (int i = 0; i < bentities.size(); i++) {
			BuildingEntity e = bentities.get(i);

			// e.update();
			if (Rect.intersects(this.atkColliderBounds, e.buildingAreaBounds)) {
				// System.out.println("Enemy attack barrack collision!");

				//
				if (e != null || this.target1 == null) {
					//this.target1 = e;
					// findPathToTarget();
				}
				//attack();
				// this.isAtkReady = true;
			}

			// Make Enemy list to hold enemies and check and compare against
			// player troops for coll
		}

		bentities = getLevel().getPlayerBuildingEntities();
		for (int i = 0; i < bentities.size(); i++) {
			BuildingEntity e = bentities.get(i);

			// e.update();
			if (Rect.intersects(this.colliderBounds, e.buildingColliderBounds)) {
				// System.out.println("Enemy attack barrack collision!");

				//
				if (e != null) {
					//moveNone();

				}

			}

		}

		LinkedList<ArmyUnit> entities = getLevel().getPlayerArmyEntities();
		for (int i = 0; i < entities.size(); i++) {
			ArmyUnit e = entities.get(i);
			if (e != null) {
				// e.update();
				if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
					// e.setVisible(false);
					// System.out.println("Enemy Collision!");
					// this.isAtkReady = true;
					this.target1 = e;
					// findPathToTarget();

					//attack();
					canMove = false;
					searched = true;
					// Make Enemy list to hold enemies and check and compare
					// against player troops for coll
				}
			} else {
				break;
			}

		}

		/*
		 * bentities = Entity.getPlayerBuildingEntities(); for(int i = 0; i <
		 * bentities.size(); i++) { BuildingEntity e = bentities.get(i);
		 * 
		 * //e.update(); if (Rect.intersects(this.atkColliderBounds,
		 * e.buildingColliderBounds)) {
		 * //System.out.println("Enemy attack barrack collision!");
		 * 
		 * this.target1 = e; findPathToTarget();
		 * 
		 * //attack(); //this.isAtkReady = true; }
		 * 
		 * // Make Enemy list to hold enemies and check and compare against
		 * player troops for coll }
		 */

		// Check for atk collision
		LinkedList<ArmyUnit> entitiess = getLevel().getPlayerArmyEntities();
		for (int i = 0; i < entitiess.size(); i++) {
			ArmyUnit e = entitiess.get(i);
			if (e != null) {
				// e.update();
				if (Rect.intersects(this.colliderBounds, e.atkColliderBounds)) {
					// System.out.println("Enemy atk player Collision!");
					if (getTarget() == null) {
						//this.target1 = e;
						// findPathToTarget();
						// setisAtkReady(true);
						// moveNone();
						// attack();
					}

					// Make Enemy list to hold enemies and check and compare
					// against player troops for coll
				}
			} else {
				break;
			}

		}
		LinkedList<Enemy> eEntities = getLevel().getEnemyArmyEntities();
		for (int i = 0; i < eEntities.size(); i++) {
			Enemy e = eEntities.get(i);

			// e.update();
			if (this.target1 == e) {
				// System.out.println("Enemy atk player Collision!");

				// Entity target1, target2;
				// this.target1 = null;
				// this.target2 = null;

				// Make Enemy list to hold enemies and check and compare against
				// player troops for coll
			}

		}

		/*
		 * OG collision code if (Rect.intersects(r, et.colliderBounds)||
		 * Rect.intersects(r, Robot.rect2) || Rect.intersects(r, Robot.rect3) ||
		 * Rect.intersects(r, Robot.rect4)) {
		 * 
		 * }
		 */
	}

	private void setisAtkReady(boolean b) {
		this.isAtkReady = b;

	}

	private void move(int xAxis, int yAxis) {
		if (xAxis > 0 && yAxis > 0)// if on axis
		{
			// run for each axis, for seperate collision
			move(xAxis, 0);
			move(0, yAxis);
			return;
		}
		// if (xAxis > 0){direction = 1;}// Right
		// if (xAxis < 0){direction = 3;}// Left
		// if (yAxis > 0){direction = 2;}// Down
		// if (yAxis < 0){direction = 0;}// Up

		if (direction == 0) {
			// setSprite(Sprite.sorcSpriteU);
			if (walking) {
				this.a = Assets.enemAnimUp;
				setImage(a.getImage());
			}
		}

		if (direction == 1) {
			setSprite(Sprite.enemSpriteR);
			if (walking) {
				this.a = Assets.enemAnimRight;
				setImage(a.getImage());
			}
		}

		if (direction == 2) {
			setSprite(Sprite.enemSpriteD);
			if (walking) {
				this.a = Assets.enemAnimDown;
				setImage(a.getImage());
			}
		}

		if (direction == 3) {
			setSprite(Sprite.enemSpriteL);
			if (walking) {
				this.a = Assets.enemAnimLeft;
				setImage(a.getImage());
			}
		}
		/*
		 * if(!collision(xAxis, yAxis))// if no collision, move in direction { x
		 * += xAxis; y += yAxis; } else { Particle p = new Particle(x, y, 50);
		 * level.add(p); //Sound.hit.play();
		 * 
		 * } }
		 */
	}

	private void moveLeft() {
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

	private boolean getCollision()
	{
		// Entity et = (Entity)Entity.getEntities().get(0);
		boolean b = false;

		// System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());

		ArrayList<ArmyUnit> entities = Entity.getPlayerArmyEntities();
		for (int i = 0; i < entities.size(); i++) {
			ArmyUnit e = entities.get(i);

			// e.update();
			if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
				// e.setVisible(false);
				// System.out.println("Collision!");
				this.target1 = e;
				this.isAtkReady = true;
				// this.target = e;
				b = true;
				return b;
				// Make Enemy list to hold enemies and check and compare against
				// player troops for coll
			} else {
				b = false;
				return b;
			}
		}
		ArrayList<BuildingEntity> bEntities = Entity
				.getPlayerBuildingEntities();

		for (int i = 0; i < bEntities.size(); i++) {
			BuildingEntity e = bEntities.get(i);

			// e.update();
			if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
				// e.setVisible(false);
				// System.out.println("Collision!");
				// e.setVisible(false);
				this.isAtkReady = true;
				// this.target = e;
				b = true;
				return b;
				// Make Enemy list to hold enemies and check and compare against
				// player troops for coll
			} else {
				b = false;
				return b;
			}
		}
		return b;

	}

	private void moveNone() {
		setMovementSpeed(0);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() + movementSpeed);
		// direction = 0;
		move(getCenter64X(), getCenter64Y());

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
			
			if (current.getTileX() == this.getTarget().getCenter64X()+1 && current.getTileY() == this.getTarget().getCenter64Y())
			{break;}		
			
			results = new ArrayList<Tile>(tileMap.neighbors(current));// hld neighbors
			System.out.print("\nResult: " );
			for(int i = 0; i < results.size(); i++)// loop thru neighbors
			{
				Tile next = results.get(i);

				System.out.print("X: " + results.get(i).getTileX() + " Y: " + results.get(i).getTileY()+",");
				int newCost = costSoFar.get(costSoFar.size()-1) + tileMap.cost(current,next); 
				// if(costSoFar.contains(i) || newCost < costSoFar.get(costSoFar.size()-1))
				// {
					 
				// }
					
					// check if neighbors have been visited already
						if( visited.contains(next))
						{
							//isVisited = true;
							System.out.print(" Has been visited.");
						}
						else				
						{
							costSoFar.add(newCost);
							 int priority = heuristic(this.getTarget(), next);
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
		}while(!frontier.isEmpty());
		
	/*	System.out.println("Visited Result:");
		for(int i = 0; i < visited.size(); i++)
		{
			System.out.print("X: " + visited.get(i).getTileX() + " Y: " + visited.get(i).getTileY() + ", ");
		}*/
		
		// reconstruct path to target
		int h = heuristic(this.getTarget(), startTile);
			
			Tile cur;
				//Tile goal = tileMap.getTileGrid()[this.getTarget().getCenter64X()-1][this.getTarget().getCenter64Y()];
				int n = cameFrom.indexOf(tileMap.getTileGrid()[this.getTarget().getCenter64X()+1][this.getTarget().getCenter64Y()]);// find goal/index in list
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
		return Math.abs(t.getCenter64X()+1 - n.getTileX())
				+ Math.abs(t.getCenter64Y() - n.getTileY());
	}

	public void attack() 
	{
		moveNone();

		// if(getCollision())
		// {// check if colliding to attack
		// Do attack dmg stuff to target entity till dead
		// this.target.die();
		if (getTarget().getHealth() > 0) {
			getTarget().setHealth(-1);
			// System.out.println("enemy attack!");
			// this.target = null;
		} else {
			setisTargetFound(false);
			// this.target1 = null;

			// getTarget().die();

		}
		// }

		// else {setisAtkReady(false);}
	}

	public void die() 
	{
		setVisible(false);
		GameManager.gold += 20;
	}

	private boolean getisAtkReady() 
	{
		return this.isAtkReady;
	}

	public void setMovementSpeed(int s) 
	{
		this.movementSpeed = s;
	}

	public boolean isCanMove() 
	{
		return this.canMove;
	}

	public void setCanMove(boolean canMove) 
	{
		this.canMove = canMove;
	}

	public void nextTurnUpdate() 
	{
		// 
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
}
