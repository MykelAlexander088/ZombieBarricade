package com.mykelalexander.zombiebarricade;

public class ZombieBarricadeConstants 
{
	//FILE DIRECTORIES
	public static final String IMAGE_PATH 			= "images/";
	public static final String PARTICLE_PATH 		= "particleEffects/";
	public static final String SOUND_PATH			= "sounds/";
	public static final String MUSIC_PATH			= "music/";
	
	//DIMENSIONAL CONSTANTS
	public static final int WORLD_WIDTH				= 10;
	public static final int WORLD_HEIGHT			= 20;
	public static final int TILE_HEIGHT			 	= 55;
	public static final int TILE_WIDTH			 	= 55;
	public static final int SCREEN_HEIGHT			= 1280;
	public static final int BLOCK_POOL_SIZE		 	= 10;
	public static final int BLOCK_POOL_X_OFFSET	 	= 99;
	public static final int BLOCK_POOL_Y_OFFSET	 	= SCREEN_HEIGHT - 1194 - TILE_HEIGHT;
	public static final int MAIN_BOARD_HEIGHT		= SCREEN_HEIGHT - 1165;
	public static final int BOARD_Y_OFFSET	 	 	= SCREEN_HEIGHT - 66;
	public static final int BOARD_X_OFFSET			= 99;
	public static final int NEXT_BLOCK_X			= 699;
	public static final int NEXT_BLOCK_Y			= SCREEN_HEIGHT - 1106 - TILE_HEIGHT;
	
	//IMAGE FILES
	public static final String IMG_YELLOW_ZOMBIE 	= "zombies.png";
	public static final String IMG_RED_ZOMBIE 		= "zombies.png";
	public static final String IMG_BLUE_ZOMBIE		= "zombies.png";
	public static final String IMG_CIVILIAN			= "zombies.png";
	public static final String IMG_YELLOW_BLOCK		= "zombies.png";
	public static final String IMG_RED_BLOCK		= "zombies.png";
	public static final String IMG_BLUE_BLOCK		= "zombies.png";
	public static final String IMG_CIVILIAN_UI		= "zombies.png";
	public static final String IMG_COMBO_UI			= "zombies.png";
	public static final String IMG_LAB_TILE			= "tileLab.png";
	public static final String IMG_FOREST_TILE		= "tileForest.png";
	public static final String IMG_BACKGROUND_0		= "background0.png";
	public static final String IMG_NUM_FONT			= "numbers.png";
	
	//PARTICLE FILES
	public static final String P_FIRE_1				= "fireParticle1.p";
	public static final String P_PLASMA_1			= "plasmaParticle1.p";
	public static final String P_LIGHTNING_1		= "lightningParticle1.p";
	public static final String P_PLACE_BLOCK		= "placeBlock.p";
	public static final String P_YELLOW_DEATH		= "yellowDeath.p";
	public static final String P_FIRE_EXPLOSION		= "fireExplosion.p";
	public static final String P_BLUE_DEATH			= "blueDeath.p";
	
	//ANIMATIONS
	public static final String A_YELLOW_ZOMBIE_WALK	= "yellowZombieWalk";
	public static final String A_YELLOW_BLOCK_DEATH = "yellowBlockDeath";
	public static final String A_RED_ZOMBIE_WALK	= "redZombieWalk";
	public static final String A_RED_BLOCK_DEATH	= "redBlockDeath";
	public static final String A_BLUE_ZOMBIE_WALK	= "blueZombieWalk";
	public static final String A_BLUE_BLOCK_DEATH	= "blueBlockDeath";
	public static final String A_YELLOW_BLOCK		= "yellowBlock";
	public static final String A_YELLOW_BLOCK_ACTIVATE = "yellowBlockActivate";
	public static final String A_RED_BLOCK			= "redBlock";
	public static final String A_RED_BLOCK_ACTIVATE = "redBlockActivate";
	public static final String A_BLUE_BLOCK			= "blueBlock";
	public static final String A_BLUE_BLOCK_ACTIVATE = "blueBlockActivate";
	public static final String A_DYING_CIVILIAN		= "dyingCivilian";
	public static final String A_CIVILIAN_WALK		= "civilianWalk";
	public static final String A_COMBO_UI			= "comboUI";
	public static final String A_CIVILIAN_UI		= "civilianUI";
	
	//SOUND EFFECTS
	public static final String S_RECIEVE_BLOCK		= "recieveBlock.wav";
	public static final String S_RED_BLOCK_LIFTED	= "fireLifted.wav";
	public static final String S_BLUE_BLOCK_LIFTED	= "electricLifted.wav";
	public static final String S_YELLOW_BLOCK_LIFTED= "plasmaLifted.wav";
	public static final String S_BLOCK_PLACED		= "placeBlock.wav";
	public static final String S_BLOCK_ACTIVATED	= "blockActivated.wav";
	public static final String S_YELLOW_DEATH		= "yellowDeath.wav";
	public static final String S_RED_DEATH			= "redDeath.wav";
	public static final String S_BLUE_DEATH			= "blueDeath.wav";
	public static final String S_CIVILIAN_DEATH		= "civilianDeath.wav";
	//MUSIC
	public static final String M_SONG1				= "song1.ogg";
	public static final String M_EVIL1				= "evilsong1.ogg";
	
}