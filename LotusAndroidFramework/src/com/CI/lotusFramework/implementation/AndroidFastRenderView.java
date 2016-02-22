package com.CI.lotusFramework.implementation;

import com.CI.lotusFramework.Graphics;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable
{
    AndroidGame game;
    Bitmap framebuffer, framebuffer2;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;
	public static int frames = 0;// Amount of frames we have time to render every second
	public static int updates = 0;
	public static long oneSec = 0;
    
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) 
    {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }
    
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer, Bitmap framebuffer2) 
    {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.framebuffer2 = framebuffer2;
        this.holder = getHolder();
    }

    public void resume() 
    { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();   

    }      
    
    public void run() 
    {
        Rect dstRect = new Rect();
        long timer = System.currentTimeMillis();
        long gmtimer = System.currentTimeMillis();
        long mtimer = System.currentTimeMillis();
        long startTime = System.nanoTime();
        final double ns = 1000000000.0f / 60;
        double deltaTime = 0;
		//int frames = 0;// Amount of frames we have time to render every second
		//int updates = 0;
		

		
        while(running) 
        {  
            if(!holder.getSurface().isValid())
                continue;           
            
            long now = System.nanoTime();
            deltaTime += (now - startTime) / ns;
            startTime = now;
            
            while (deltaTime >= 1)
            {
                game.getCurrentScreen().update(deltaTime);
                updates++;
                if(frames > 61)
    			{
    				frames = 0;
    			}
    			else
    			{

                    game.getCurrentScreen().paint(deltaTime);
                  
                    
                    // drawing
                    Canvas canvas = holder.lockCanvas();
                    canvas.getClipBounds(dstRect);
                    canvas.drawBitmap(framebuffer, null, dstRect, null);                           
                    holder.unlockCanvasAndPost(canvas);
    				frames++;
    				
    			}
    			//oneSec = System.currentTimeMillis() - timer;
    			if(System.currentTimeMillis() - timer > 1000)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
    			{

    				//game.getCurrentScreen().updateEverySec();

    				timer += 1000;
    				System.out.println(updates + " ups, " + frames + " fps");
    				//game.getCurrentScreen().updateEverySec();
    				//game.getCurrentScreen().updateMovement();

    				frames = 0;
    				updates = 0;
    				
    			}
    			if(System.currentTimeMillis() - mtimer > 350)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
    			{
    				
    				game.getCurrentScreen().updateMovement();

    				mtimer += 350;
    				
    				
    			}
    			deltaTime--;
            }
                
    			
    			
    			if(System.currentTimeMillis() - gmtimer > 12000)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
    			{
    				
    				game.getCurrentScreen().update12EverySec();

    				gmtimer += 12000;

    				
    			}
    			/*
    			if(System.currentTimeMillis() - mtimer > 100)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
    			{
    				
    				//game.getCurrentScreen().updateEverySec();

    				mtimer += 100;
 				
    			}*/
    			
            	
            
                       
        }
    }

    public void pause()
    {                        
        running = false;                        
        while(true) 
        {
            try 
            {
                renderThread.join();
                break;
            } catch (InterruptedException e) 
            {
                // retry
            }
            
        }
    } 
    
    public void stop()
    {
		running = false;
		try
		{
			renderThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
    }
    
  
}