package com.CI.lotusFramework;

import android.content.res.Resources;

/* The Architecture of the game is 3 parts:
 * 	1. The game interface
	2. The implementation of the interface
	3. The source code

 	The architecture is like a human body with components designed to come together to create one.

1. In the interface, abstract components of our game are formed, such as Audio or Graphics. 
	In these components, we create the variables and properties that instances of each component will have, 
	but do not define them. Basically, we create the "skeleton" of our game.

2. In the implementation, we implement the interface above and define the methods and variables created in the above interface. 
	Think of it like using the above interface to create an instance of it, such as AndroidAudio and AndroidGraphics implementations using the Audio and Graphics interfaces). 
	In other words, we are building on top of the "skeleton" above by adding "muscle."

3. In the game source code, we create the classes that will together form our game. 
	In these classes, we call the methods that we have defined in the implementation to interact with Android. 
	This will be very similar to what we have worked on in Units 2 and 3. (Think of this as the skin above the skeleton and muscle).
 */
public interface Game 
{

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

	public Resources getResources();
}
