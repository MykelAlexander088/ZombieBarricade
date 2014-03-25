package com.mykelalexander.zombiebarricade.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation 
{
	private Point[] clips;
	private TextureRegion[] regions;
	private int currentFrame;
	private int numFrames;
	private int spriteWidth;
	private int spriteHeight;

	
	Animation(int startRow, int endRow, int numFrames, int spriteWidth, int spriteHeight)
	{
		clips = new Point[numFrames];
		this.numFrames = numFrames;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		currentFrame = 0;
		
		int frame = 0;
		for(int i = startRow; i <= endRow; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				clips[frame++] = new Point(j * spriteWidth, i * spriteHeight);
			}
		}
	}
	
	public int getCurrentFrame() { return currentFrame; }
	
	public void setTexture(Texture tex)
	{
		regions = new TextureRegion[numFrames];
		for(int i = 0; i < numFrames; i++)
		{
			regions[i] = new TextureRegion(tex, clips[i].x, clips[i].y, spriteWidth, spriteHeight);
		}
	}
	
	public void update()
	{
		currentFrame++;
		
		if(currentFrame >= numFrames)
			currentFrame = 0;
	}
	
	public TextureRegion getFrame()
	{
		return regions[currentFrame];
	}

}
