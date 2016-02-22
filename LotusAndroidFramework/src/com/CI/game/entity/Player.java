package com.CI.game.entity;

public class Player extends Entity
{
	private boolean barrackUIPaneActive;
	private boolean isBuildMode;
	private boolean uiItem_1;
	private boolean uiItem_2;
	private boolean isBuildingSelected;
	private boolean isMilitarySelected;
	private boolean isPanelOn;
	public int xScroll, yScroll;

	public Player(int x,int y)
	{
		super(x, y);
		this.barrackUIPaneActive = false;
		this.isBuildMode = false;
		uiItem_1 = false;
		uiItem_2 = false;
		isBuildingSelected = false;
		isMilitarySelected = false;
	}
	
	public void update()
	{
		int xPos = 0, yPos = 0;
	}

	public boolean isBarrackUIPaneActive() 
	{
		return this.barrackUIPaneActive;
	}

	public void setBarrackUIPaneActive(boolean barrackUIPaneActive) 
	{
		this.barrackUIPaneActive = barrackUIPaneActive;
	}

	public boolean isBuildMode() 
	{
		return this.isBuildMode;
	}

	public void setBuildMode(boolean isBuildMode) 
	{
		this.isBuildMode = isBuildMode;
	}

	public boolean isUiItem_1() 
	{
		return this.uiItem_1;
	}

	public void setUiItem_1(boolean uiItem_1) 
	{
		this.uiItem_1 = uiItem_1;
	}

	public boolean isUiItem_2()
	{
		return uiItem_2;
	}

	public void setUiItem_2(boolean uiItem_2)
	{
		this.uiItem_2 = uiItem_2;
	}

	public boolean isBuildingSelected() 
	{
		return this.isBuildingSelected;
	}

	public void setBuildingSelected(boolean isBuildingSelected) 
	{
		this.isBuildingSelected = isBuildingSelected;
	}

	public boolean isMilitarySelected() 
	{
		return this.isMilitarySelected;
	}

	public void setMilitarySelected(boolean isMilitarySelected) 
	{
		this.isMilitarySelected = isMilitarySelected;
	}
	
	public boolean isPanelOn() {
		return isPanelOn;
	}

	public void setPanelOn(boolean isPanelOn) {
		this.isPanelOn = isPanelOn;
	}
}
