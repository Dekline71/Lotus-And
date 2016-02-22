package com.CI.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.CI.game.graphics.Assets;
import com.CI.lotusFramework.Screen;
import com.CI.lotusFramework.implementation.AndroidGame;
import com.CI.game.R;
/***
 * This class extends AndroidGame class (which extends Activity), and therefore is an Activity 
 * @author Jake Galletta Conscious Interactive (c) 2014
 *
 */
public class SampleGame extends AndroidGame 
{

	public static String map;
	boolean firstTimeCreate = true;
	
	/**
	 * Checks whether this is the first time this class has been run, if so load methods from Assets will cue music
	 * This allows flexibility to manage audio without having multiple instances of background music. 
	 */
	@Override
	public Screen getInitScreen() 
	{

		if (firstTimeCreate) 
		{
			Assets.load(this);
			firstTimeCreate = false;
		}

		InputStream is = getResources().openRawResource(R.raw.map3);
		map = convertStreamToString(is);

		return new SplashLoadingScreen(this);

	}

	@Override
	public void onBackPressed() 
	{
		getCurrentScreen().backButton();
	}
	/**
	 * passes map.txt file into a String
	 * @param is
	 * @return String
	 */
	private static String convertStreamToString(InputStream is) 
	{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				sb.append((line + "\n"));
			}
		} catch (IOException e) 
		{
			Log.w("LOG", e.getMessage());
		} finally 
		{
			try 
			{
				is.close();
			} catch (IOException e) 
			{
				Log.w("LOG", e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public void onResume() 
	{
		super.onResume();

		Assets.theme.play();

	}

	@Override
	public void onPause() 
	{
		super.onPause();
		Assets.theme.pause();

	}
	
	
}