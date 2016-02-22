package com.CI.lotusFramework;

public abstract class Screen 
{
    protected final Game game;

    public Screen(Game game) 
    {
        this.game = game;
    }

    public abstract void update(double deltaTime);

    public abstract void paint(double deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
	public abstract void backButton();

	public abstract void update12EverySec();

	//public abstract void drawEntityLayer(Graphics g);

	public void updateMovement() {
		// TODO Auto-generated method stub
		
	}

}
