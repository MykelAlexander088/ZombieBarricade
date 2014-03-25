package com.mykelalexander.zombiebarricade.uiutils;

public class CivilianManager 
{
	private int maxCivilians;
	private int savedCivilians;
	
	public CivilianManager(int maxCivilians)
	{
		this.maxCivilians = maxCivilians;
		savedCivilians = 0;
	}
	
	public int savedCivilians()
	{
		return savedCivilians;
	}
	
	public int maxCivilians()
	{
		return maxCivilians;
	}
	
	public void saveCivilian()
	{
		savedCivilians++;
	}
}
