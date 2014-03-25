package com.mykelalexander.zombiebarricade.uiutils;

import com.mykelalexander.zombiebarricade.model.Position;
import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class ComboManager 
{
	public int currentCombo;
	public float comboTime;
	public float appearance;
	public boolean visible;
	public Position pos;
	
	private int score;
	private int accumilator;
	
	public ComboManager()
	{
		pos = new Position(1281, SCREEN_HEIGHT - 200);
		pos.setVelocity(10, 10);
		score = 0;
		accumilator = 0;
	}
	
	public int update( float delta)
	{
		comboTime -= delta;
		if(comboTime < 0)
		{
			comboTime = 0;
			accumilator += currentCombo * (100 + 100*(currentCombo/50));
			visible = false;
			int currentCombo = this.currentCombo;
			this.currentCombo = 0;
			pos.setDestination(0, 0);
			pos.xPos = 1281;
			pos.yPos = SCREEN_HEIGHT - 200;
			if(accumilator > 0)
			{
				accumilator -= 100;
				score += 100;
			}
			return currentCombo;
		}
		else
		{
			visible = true;
			pos.advance();
			comboTime -= delta;
			appearance -= delta/20;
			return 0;
		}
	}
	
	public void addCombo(int combo)
	{
		if(combo > 0)
		{
			appearance = 1.0f;
			comboTime = 20;
			currentCombo += combo;
			pos.setDestination(677, SCREEN_HEIGHT - 200);
			visible = true;
		}
	}
	
	public int getScoreMultiplier()
	{
		return 100 + 100*(currentCombo/50);
	}
	
	public int getScore()
	{
		return score;
	}
}
