package com.CI.game.entity;

public class Node 
{
	int h;// distance/spaces to node
	int f;
	int g;// move cost
	Node parent;
	
	public Node()
	{
		
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public int getH()
	{
		return h;
	}
	
	public int getG()
	{
		return g;
	}
	
	public int getF()
	{
		return f;
	}
	
	public Node getParent()
	{
		return parent;
	}
}
