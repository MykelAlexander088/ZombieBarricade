package com.mykelalexander.zombiebarricade.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mykelalexander.zombiebarricade.err.InvalidLevelFileException;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieType;

public class Level 
{
	
	public String filePath;
	
	//CONSTANTS
	public static final int TILE_WIDTH 		 = 55;
	public static final int TILE_HEIGHT		 = 55;
	public static final int SCREEN_WIDTH	 = 800;
	public static final int SCREEN_HEIGHT	 = 1280;
	public static final int BOARD_X_OFFSET	 = 99;
	public static final int BOARD_Y_OFFSET	 = SCREEN_HEIGHT - 66;
	
	//LEVEL FILE SYMBOLS
	public static final String IS_VALID_FILE				= "LEVEL";
	public static final String COMMENT						= "@";
	public static final String ZOMBIE_BUFFER_SIZE			= "ZOMBIE_BUFFER_SIZE:";
	public static final String ZOMBIE_BUFFER				= "ZOMBIE_BUFFER:";
	public static final String BLOCK_BUFFER_SIZE			= "BLOCK_BUFFER_SIZE:";
	public static final String BLOCK_BUFFER					= "BLOCK_BUFFER:";
	public static final String NEXT_BLOCK_DROP_SPEED		= "NEXT_BLOCK_DROP_SPEED:";
	public static final String NEXT_BLOCK_INCREMENT_SPEED	= "NEXT_BLOCK_INCREMENT_SPEED:";
	public static final String START_ZOMBIE_VELOCITY		= "START_ZOMBIE_VELOCITY:";
	public static final String ZOMBIE_VELOCITY_INCREMENT	= "ZOMBIE_VELOCITY_INCREMENT:";
	public static final String CIVILIANS					= "CIVILIANS:";
	public static final String EOF							= "END";
	
	//TYPES OF INPUT FOR ZOMBIES
	private static final int NO_ZOMBIE		= 0;
	private static final int YELLOW_ZOMBIE	= 1;
	private static final int RED_ZOMBIE		= 2;
	private static final int BLUE_ZOMBIE	= 3;
	private static final int CIVILIAN		= 4;
	
	//LEVEL PROPERTIES
	private LevelProperties properties;
	
	public Level( String filename )
	{
		FileHandle file = Gdx.files.internal("data/level/"+filename);
		properties = new LevelProperties();
		Scanner in = null;
		
		try
		{
			//OPEN THE FILE
			in = new Scanner(file.read());
			
			//CHECK IF IT IS A LEVEL FILE
			if(!in.next().equalsIgnoreCase(IS_VALID_FILE))
				throw new InvalidLevelFileException();
			
			
			String currentField;
			
			//EXTRACT FIELDS FROM THE FILE
			while(!(currentField = in.next()).equalsIgnoreCase(EOF))
			{
				//IF A COMENT IS REACHED, IGNORE THAT LINE
				if(currentField.equalsIgnoreCase(COMMENT))
					continue;
				
				//SET THE ZOMBIE BUFFER SIZE
				if(currentField.equalsIgnoreCase(ZOMBIE_BUFFER_SIZE))
				{
					properties.zombieBufferSize = in.nextInt();
					if(properties.zombieBufferSize != 0)
						properties.zombieBuffer = new ArrayList<Zombie>(properties.zombieBufferSize);
				}
				
				// FILL THE ZOMBIE BUFFER IF NECESSARY
				if(currentField.equalsIgnoreCase(ZOMBIE_BUFFER) && properties.zombieBufferSize != 0)
				{
					List< Zombie > zombieBuffer = properties.zombieBuffer;
					float startVel = properties.startZombieVelocity;
					for(int y = 0; y < properties.zombieBufferSize; y++)
					{
						for(int x = 0; x < 10; x++)
						{
							switch( in.nextInt() )
							{
							case NO_ZOMBIE:
								break;
							case YELLOW_ZOMBIE:
								zombieBuffer.add(new Zombie(new Position(
										x * TILE_WIDTH + BOARD_X_OFFSET,
										TILE_HEIGHT * properties.zombieBufferSize - y * TILE_HEIGHT, 
										0, startVel ), ZombieType.YELLOW));
										break;
							case RED_ZOMBIE:
								zombieBuffer.add(new Zombie(new Position(
										x * TILE_WIDTH + BOARD_X_OFFSET,
										TILE_HEIGHT * properties.zombieBufferSize - y * TILE_HEIGHT, 
										0, startVel ), ZombieType.RED));
										break;
							case BLUE_ZOMBIE:
								zombieBuffer.add(new Zombie(new Position(
										x * TILE_WIDTH + BOARD_X_OFFSET,
										TILE_HEIGHT * properties.zombieBufferSize - y * TILE_HEIGHT, 
										0, startVel ), ZombieType.BLUE));
										break;
							case CIVILIAN:
								zombieBuffer.add(new Zombie(new Position(
										x * TILE_WIDTH + BOARD_X_OFFSET,
										TILE_HEIGHT * properties.zombieBufferSize - y * TILE_HEIGHT, 
										0, startVel ), ZombieType.CIVILIAN));
										break;
							}
						}
					}
				}
				
				//SET THE BLOCK BUFFER SIZE
				if( currentField.equalsIgnoreCase(BLOCK_BUFFER_SIZE))
				{
					properties.blockBufferSize = in.nextInt();
					if(properties.blockBufferSize != 0)
						properties.blockBuffer = new int[properties.blockBufferSize][2];
				}
				
				//FILL THE BLOCK BUFFER
				if(currentField.equalsIgnoreCase(BLOCK_BUFFER) && properties.blockBufferSize != 0)
				{
					for(int i = 0; i < properties.blockBufferSize; i++)
					{
						properties.blockBuffer[i][0] = in.nextInt();
						properties.blockBuffer[i][1] = in.nextInt();
					}
				}
				
				//SET NEXT BLOCK DROP SPEED
				if(currentField.equalsIgnoreCase(NEXT_BLOCK_DROP_SPEED))
					properties.nextBlockDropSpeed = in.nextInt();
				
				//SET NEXT BLOCK SPEED INCREMENT VALUE
				if(currentField.equalsIgnoreCase(NEXT_BLOCK_INCREMENT_SPEED))
					properties.nextBlockDropSpeedIncrement = in.nextInt();
				
				//START SPEED OF ZOMBIES
				if(currentField.equalsIgnoreCase(START_ZOMBIE_VELOCITY))
					properties.startZombieVelocity = in.nextFloat();
				
				//ZOMBIE SPEED INCREMENT VALUE
				if(currentField.equalsIgnoreCase(ZOMBIE_VELOCITY_INCREMENT))
					properties.zombieVelocityIncrement = in.nextFloat();
				
				//CIVILIAN COUNT
				if(currentField.equalsIgnoreCase(CIVILIANS))
				{
					properties.civilians = in.nextInt();
					if(properties.civilians > 0)
						properties.hasCivilians = true;
				}
				
				//EVENT PROCESSING
				
				properties.loaded = true;
				
			}
		} 
		catch(InvalidLevelFileException ilfe)
		{
			ilfe.printStackTrace();
		}
		finally
		{
			if(in != null)
				in.close();
		}
	}
	
	public LevelProperties getLevelProperties() { return properties; }
}
