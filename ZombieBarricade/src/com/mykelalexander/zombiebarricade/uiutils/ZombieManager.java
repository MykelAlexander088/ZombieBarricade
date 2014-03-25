package com.mykelalexander.zombiebarricade.uiutils;

import java.util.ArrayList;
import java.util.List;

import com.mykelalexander.zombiebarricade.model.LevelProperties;
import com.mykelalexander.zombiebarricade.model.Position;
import com.mykelalexander.zombiebarricade.model.World;
import com.mykelalexander.zombiebarricade.model.Zombie;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieState;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieType;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class ZombieManager 
{
	private List< Zombie > zombieWorldLayer;
	private List< Zombie > grave;
	private List< Zombie > civilians;
	private LevelProperties level;
	private World world;
	
	public ZombieManager(LevelProperties level, World world)
	{
		this.level = level;
		zombieWorldLayer = new ArrayList< Zombie >(WORLD_WIDTH * WORLD_HEIGHT);
		grave = new ArrayList< Zombie >(WORLD_WIDTH * WORLD_HEIGHT);
		civilians = new ArrayList< Zombie>();
		this.world = world;
	}
	
	public List< Zombie > getZombieWorldLayer() { return zombieWorldLayer; }
	
	public void moveToGrave(int i)
	{
		grave.add(zombieWorldLayer.remove(i));
	}
	public void update(float delta)
	{
		Zombie current;
		Position pos;

			//FIRST THE WORLD ZOMBIES ADVANCE
			for(int i = 0; i < zombieWorldLayer.size(); i++)
			{
				current = zombieWorldLayer.get(i);
				if(current.getState() == ZombieState.DEAD)
				{
					moveToGrave(i--);
					continue;
				}
				
				pos = current.getPosition();
				current.update(delta);
				if( pos.yPos <= MAIN_BOARD_HEIGHT && current.getType() != ZombieType.CIVILIAN)
				{
					level.gameOver = true;
				}
				else if( pos.yPos <= MAIN_BOARD_HEIGHT && current.getType() == ZombieType.CIVILIAN && !level.gameOver)
				{
					current.setState(ZombieState.DEAD);
					civilians.add(zombieWorldLayer.remove(i--));
					
					//add to civilian saved score
					world.civilians.saveCivilian();
				}
			}
			
			//NOW ADVANCE THE ZOMBIES IN THE BUFFER
			for(int i = 0; i < level.zombieBuffer.size(); i++)
			{
				current = level.zombieBuffer.get(i);
				pos = current.getPosition();
				
				
				if( pos.yPos <= 55 )
				{
					pos.yPos = BOARD_Y_OFFSET;
					current.setState(ZombieState.ACTIVE);
					zombieWorldLayer.add(level.zombieBuffer.remove(i--));
				}
				current.update(delta);
			}
					
			if( level.gameOver)
				return;
		
	}
	
	
	
	
}
