package com.mykelalexander.zombiebarricade.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mykelalexander.zombiebarricade.uiutils.AnimationManager;
import com.mykelalexander.zombiebarricade.uiutils.ParticleManager;
import com.mykelalexander.zombiebarricade.uiutils.SoundManager;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class Zombie 
{
public enum ZombieType { YELLOW, RED, BLUE, CIVILIAN}
	
	public enum ZombieState { OFFSCREEN, ACTIVE, DYING_YELLOW, DYING_RED, DYING_BLUE, DEAD, DYING_CIVILIAN }
	
	private Position position;
	private ZombieType type;
	private ZombieState state;
	private AnimationComponent animation;
	
	public Zombie() {}
	
	public Zombie(Position position, ZombieType type)
	{
		this.position = position;
		this.type = type;
		this.state = ZombieState.OFFSCREEN;
		animation = new AnimationComponent(AnimationComponent.ZOMBIE_LAYER);
		animation.render = false;
	}
	
	public ZombieState getState() 				{ return state; }
	public ZombieType getType() 				{ return type; }
	public Position getPosition() 				{ return position; }
	
	public void update(float delta) 
	{
		switch(state)
		{
		case ACTIVE:
			updateActive();
			break;
		case DEAD:
			break;
		case DYING_BLUE:
			updateDyingBlue();
			break;
		case DYING_RED:
			updateDyingRed();
			break;
		case DYING_YELLOW:
			updateDyingYellow();
			break;
		case OFFSCREEN:
			updateOffscreen();
			break;
		default:
			break;
		}
		animation.stateTime += delta;
		animation.x = position.xPos;
		animation.y = position.yPos;
	}
	
	public void setState(ZombieState state)	{ 
		this.state = state;
		
		switch(state)
		{
		case ACTIVE:
			animation.animation = getWalkingAnimation();
			animation.render = true;
			animation.looping = true;
			animation.stateTime = 0;
			break;
		case DEAD:
			animation.render = false;
			animation.looping = false;
			animation.stateTime = 0;
			break;
		case DYING_BLUE:
			animation.animation = getDeathAnimation();
			animation.render = true;
			animation.looping = false;
			animation.stateTime = 0;
			break;
		case DYING_RED:
			animation.animation = getDeathAnimation();
			animation.render = true;
			animation.looping = false;
			animation.stateTime = 0;
			break;
		case DYING_YELLOW:
			animation.animation = A_YELLOW_BLOCK_DEATH;
			animation.render = true;
			animation.looping = false;
			animation.stateTime = 0;
			break;
		case OFFSCREEN:
			break;
		default:
			break;
		
		}
	}
	
	private String getWalkingAnimation()
	{
		switch(type)
		{
		case YELLOW:
			return A_YELLOW_ZOMBIE_WALK;
		case BLUE:
			return A_BLUE_ZOMBIE_WALK;
		case RED:
			return A_RED_ZOMBIE_WALK;
		case CIVILIAN:
			return A_CIVILIAN_WALK;
		default:
			return null;
		}
	}
	
	private String getDeathAnimation()
	{
		switch(type)
		{
		case YELLOW:
			return A_YELLOW_BLOCK_DEATH;
		case BLUE:
			return A_BLUE_BLOCK_DEATH;
		case RED:
			return A_RED_BLOCK_DEATH;
		case CIVILIAN:
			return A_DYING_CIVILIAN;
		default:
			return null;
		}
	}
	
	private void updateActive() 
	{
		position.move();
	}
	
	private void updateOffscreen()
	{
		position.move();
	}
	
	private void updateDyingYellow()
	{
		AnimationManager am = AnimationManager.getAnimationManager();
		Animation animation = am.getAnimation(A_YELLOW_BLOCK_DEATH);
		
		if(animation.isAnimationFinished(this.animation.stateTime))
		{
			SoundManager sm = SoundManager.getSoundManager();
			sm.placeSoundMessage(S_YELLOW_DEATH);
			this.setState(ZombieState.DEAD);
			ParticleManager.getParticleManager().playParticleEffect(P_YELLOW_DEATH, position.xPos + TILE_WIDTH/2, position.yPos + TILE_HEIGHT/2);
			
		}
	}
	
	private void updateDyingRed()
	{
		AnimationManager am = AnimationManager.getAnimationManager();
		Animation animation = am.getAnimation(getDeathAnimation());
		
		if(animation.isAnimationFinished(this.animation.stateTime))
		{
			SoundManager sm = SoundManager.getSoundManager();
			sm.placeSoundMessage(S_RED_DEATH);
			if(type == ZombieType.CIVILIAN )
				sm.placeSoundMessage(S_CIVILIAN_DEATH);
			this.setState(ZombieState.DEAD);
			ParticleManager.getParticleManager().playParticleEffect(P_FIRE_EXPLOSION, position.xPos + TILE_WIDTH/2, position.yPos + TILE_HEIGHT/2);
			
		}
	}
	
	private void updateDyingBlue()
	{
		AnimationManager am = AnimationManager.getAnimationManager();
		Animation animation = am.getAnimation(getDeathAnimation());
		
		if(animation.isAnimationFinished(this.animation.stateTime))
		{
			SoundManager sm = SoundManager.getSoundManager();
			sm.placeSoundMessage(S_BLUE_DEATH);
			if(type == ZombieType.CIVILIAN )
				sm.placeSoundMessage(S_CIVILIAN_DEATH);
			this.setState(ZombieState.DEAD);
			ParticleManager.getParticleManager().playParticleEffect(P_BLUE_DEATH, position.xPos + TILE_WIDTH/2, position.yPos + TILE_HEIGHT/2);
		}
	}

}
