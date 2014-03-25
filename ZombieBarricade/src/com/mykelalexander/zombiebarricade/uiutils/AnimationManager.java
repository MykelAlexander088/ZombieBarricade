package com.mykelalexander.zombiebarricade.uiutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager 
{
	//THE ONLY OBJECT OF ANIMATION MANAGER THAT EXISTS
	private static AnimationManager singleton = null;
	
	//STORAGE OF ANIMATIONS BY ANIMATION NAMES
	private Map< String, Animation > animations;
	
	//LIST OF KEYS IN THE ANIMATIONS MAP
	private List< String > keys;
	
	/**
	 *  Constructor is private to enforce singleton intention
	 *  user must call the getAnimationManager() method.
	 */
	private AnimationManager()
	{
		animations = new HashMap< String, Animation >();
		keys = new ArrayList< String >();
	}
	
	/**
	 * returns the singleton.  If it has not been initialized, it
	 * is initialized here.
	 * @return
	 */
	public static AnimationManager getAnimationManager()
	{
		if(singleton == null)
		{
			singleton = new AnimationManager();
		}
		
		return singleton;
	}
	
	/**
	 * Load an animation for later use.  It puts it into a hash map and can be restored later with the
	 * same key.  Keys should be kept somewhere as constants.
	 * 
	 * @param key - name of animation (this will be the key for retrieval.)
	 * @param texture - the texture/sprite sheet the animation will be from
	 * @param frameDuration - how long does a frame last?
	 * @param numFrames - how many frames?
	 * @param startRow - which row on the sheet does the animation start on?
	 * @param endRow - which row on the sheet does the animation end on?
	 * @param width - width of each frame
	 * @param height - height of each frame
	 */
	public void loadAnimation(String key, String texture, float frameDuration, int numFrames, int startRow, int endRow, int width, int height)
	{
		TextureManager tm  = TextureManager.getTextureManager();
		Texture tex = tm.getTexture(texture);
		TextureRegion[] tr = new TextureRegion[numFrames];
		
		int frame = 0;
		for(int i = startRow; i <= endRow; i++)
		{
			for(int j = 0; j < (numFrames < 8 ? numFrames : 8) ; j++)
			{
				tr[frame++] = new TextureRegion(tex, j * width, i * height, width, height);
			}
		}
		
		animations.put(key, new Animation(frameDuration, tr));	
		keys.add(key);
	}
	
	/**
	 * Get the next frame of the animation for rendering.
	 * 
	 * @param key - the name of the animation.
	 * @param stateTime - the time the object has been in it's current frame
	 * @param loop - set true to loop animation
	 * @return - a TextureRegion representing the next frame
	 */
	public TextureRegion getNextFrame(String key, float stateTime, boolean loop)
	{
		Animation animation = animations.get(key);
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, loop);
		return currentFrame;
	}
	
	public Animation getAnimation(String key)
	{
		return animations.get(key);
	}
	
	public void dispose()
	{
		for(int i = 0; i < keys.size(); i++)
		{
			animations.remove(keys.remove(--i));
		}
	}
	
	
	
	
	
	
	
}
