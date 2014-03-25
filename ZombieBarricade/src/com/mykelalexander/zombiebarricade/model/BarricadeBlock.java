package com.mykelalexander.zombiebarricade.model;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mykelalexander.zombiebarricade.ZombieBarricadeConstants;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieState;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieType;
import com.mykelalexander.zombiebarricade.uiutils.AnimationManager;
import com.mykelalexander.zombiebarricade.uiutils.ParticleManager;
import com.mykelalexander.zombiebarricade.uiutils.SoundManager;
import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;
public class BarricadeBlock extends PlayableObject
{
	//TYPES OF BLOCKS
	public static final int YELLOW_BLOCK = 1;
	public static final int RED_BLOCK = 2;
	public static final int BLUE_BLOCK = 3;
	
	public static final int BLOCK_VELOCITY = 15;
	
	private int blockType;
	private AnimationComponent animation;
	
	public BarricadeBlock( int blockType )
	{
		super(new Position(0, 0));
		this.blockType = blockType;
		animation = new AnimationComponent(AnimationComponent.TOP_LAYER);
		animation.render = false;
		setState(ObjectState.INVISIBLE);
	}
	
	public int getBlockType() 		{ return blockType; }
	
	@Override
	public void update(float delta)
	{
		switch(getState())
		{
		case INVISIBLE:
			break;
		case MOVING_TO_POOL:
			updateMovingToPool();
			break;
		case IN_POOL:
			updateInPool();
			break;
		case LIFTED:
			updateLifted();
			break;
		case NEXT:
			break;
		case PLACED:
			updatePlaced();
			break;
		case ACTIVATING:
			updateActivating();
			break;
		case USED:
			break;
		default:
			break;
		
		}
		animation.stateTime += delta;
		animation.x = getPosition().xPos;
		animation.y = getPosition().yPos;
	}
	
	@Override
	public void setState(ObjectState state)
	{
		switch(state)
		{
		case ACTIVATING:
			animation.animation = getActivateAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = false;
			break;
		case INVISIBLE:
			animation.render = false;
			break;
		case IN_POOL:
			animation.animation = getBlockAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = true;
			break;
		case LIFTED:
			animation.animation = getBlockAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = true;
			break;
		case MOVING_TO_POOL:
			animation.animation = getBlockAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = true;
			break;
		case NEXT:
			animation.animation = getBlockAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = true;
			break;
		case PLACED:
			animation.animation = getBlockAnimation();
			animation.stateTime = 0;
			animation.render = true;
			animation.looping = true;
			animation.switchLayer(AnimationComponent.TOP_LAYER, AnimationComponent.OBJECT_LAYER);
			break;
		case USED:
			animation.render = false;
			break;
		default:
			break;
		
		}
		this.state = state;
	}
	
	public String getBlockAnimation()
	{
		switch(blockType)
		{
		case YELLOW_BLOCK:
			return A_YELLOW_BLOCK;
		case RED_BLOCK:
			return A_RED_BLOCK;
		case BLUE_BLOCK:
			return A_BLUE_BLOCK;
		default:
			return null;
		}
	}
	
	public String getActivateAnimation()
	{
		switch(blockType)
		{
		case YELLOW_BLOCK:
			return A_YELLOW_BLOCK_ACTIVATE;
		case RED_BLOCK:
			return A_RED_BLOCK_ACTIVATE;
		case BLUE_BLOCK:
			return A_BLUE_BLOCK_ACTIVATE;
		default:
			return null;
		}
	}
	
	public String getParticleEffect()
	{
		switch(getBlockType())
		{
		case BarricadeBlock.RED_BLOCK:
			return P_FIRE_1;
		case BarricadeBlock.BLUE_BLOCK:
			return P_LIGHTNING_1;
		case BarricadeBlock.YELLOW_BLOCK:
			return P_PLASMA_1;
		default:
			return null;
		}
	}
	
	private void updateActivating() {
		AnimationManager am = AnimationManager.getAnimationManager();
		Animation animation = am.getAnimation(getActivateAnimation());
		if(animation.isAnimationFinished(this.animation.stateTime))
		{
			this.setState(ObjectState.USED);
		}
	}

	public void updateMovingToPool()
	{
		if(!getPosition().hasReachedDestination())
		{

			Position pos = getPosition();
			ParticleManager pm = ParticleManager.getParticleManager();
			pos.advance();
			pm.playParticleEffect(getParticleEffect(), pos.xPos + TILE_WIDTH/2, pos.yPos + TILE_HEIGHT/2);
		}
		else
			setState(ObjectState.IN_POOL);
	}
	
	public void updateInPool()
	{
		if(!getPosition().hasReachedDestination())
			getPosition().advance();
	}
	
	public void updateNext()
	{
		if(!getPosition().hasReachedDestination())
			getPosition().advance();
	}
	
	public void updateLifted()
	{
		if(!getPosition().hasReachedDestination())
			getPosition().advance();
		ParticleManager.getParticleManager().playParticleEffect(getParticleEffect(), getPosition().xPos + TILE_WIDTH/2, getPosition().yPos + TILE_HEIGHT/2);
	}
	
	public void updatePlaced()
	{
		if(!getPosition().hasReachedDestination())
			getPosition().advance();
	}
	

	@Override
	public int activate(List< Zombie > zombies, int index) {
		int points = 0;
		
		switch(blockType)
		{
		case YELLOW_BLOCK:
			points = activateYellow(zombies.get(index));
			break;
		case RED_BLOCK:
			points = activateRed(zombies, index);
			break;
		case BLUE_BLOCK:
			points = activateBlue(zombies, index);
			break;
		}
			
		return points;
		
	}

	private int activateBlue(List< Zombie > zombies, int index) {
		Zombie zombie;
		int score = 0;
		boolean civilianKilled = false;
		SoundManager sm = SoundManager.getSoundManager();
		for(int i = 0; i < zombies.size(); i++)
		{
			zombie = zombies.get(i);
			if(zombie.getPosition().xPos == getPosition().xPos)
			{
				if(zombie.getType() == ZombieType.CIVILIAN)
					civilianKilled = true;
				zombie.setState(ZombieState.DYING_BLUE);
				score++;
			}	
		}
		sm.placeSoundMessage(S_BLOCK_ACTIVATED);
		this.setState(ObjectState.ACTIVATING);
		return civilianKilled ? 0 : score;
		
	}

	private int activateRed(List< Zombie > zombies, int index) {
		Zombie zombie;
		int score = 0;
		boolean civilianKilled = false;
		SoundManager sm = SoundManager.getSoundManager();
		for(int i = 0; i < zombies.size(); i++)
		{
			zombie = zombies.get(i);
			if(zombie.getPosition().yPos >= getPosition().yPos && zombie.getPosition().yPos <= getPosition().yPos + TILE_HEIGHT)
			{
				if(zombie.getType() == ZombieType.CIVILIAN)
					civilianKilled = true;
				zombie.setState(ZombieState.DYING_RED);
				score++;
			}	
		}
		sm.placeSoundMessage(S_BLOCK_ACTIVATED);
		this.setState(ObjectState.ACTIVATING);
		return civilianKilled ? 0 : score;
		
	}

	private int activateYellow(Zombie zombie) {
		SoundManager sm = SoundManager.getSoundManager();
		zombie.setState(ZombieState.DYING_YELLOW);
		this.setState(ObjectState.ACTIVATING);
		sm.placeSoundMessage(S_BLOCK_ACTIVATED);
		
		return 1;	
	}

}
