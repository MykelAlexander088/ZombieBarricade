package com.mykelalexander.zombiebarricade.model;

import java.util.List;

public class LevelProperties 
{
	public int zombieBufferSize;
	public int blockBufferSize;
	public float startZombieVelocity;
	public float zombieVelocityIncrement;
	public int nextBlockDropSpeed;
	public int nextBlockDropSpeedIncrement;
	public boolean hasCivilians;
	public int civilians;
	public boolean loaded = false;
	
	public List< Zombie > zombieBuffer;
	public int[][] blockBuffer;
	
	public boolean gameOver = false;
	
	public LevelProperties(){}
}
