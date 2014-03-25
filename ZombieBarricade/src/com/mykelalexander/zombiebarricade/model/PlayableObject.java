package com.mykelalexander.zombiebarricade.model;

import java.util.List;

public abstract class PlayableObject 
{
	public enum ObjectState { INVISIBLE, NEXT, IN_POOL, PLACED, LIFTED, MOVING_TO_POOL, ACTIVATING, USED }
	
	private Position position;
	protected ObjectState state;
	
	public PlayableObject( Position position ) 
	{
		this.position = position;
		this.state = ObjectState.INVISIBLE;
	}
	
	public void moveObject( int x, int y ) 
	{
		position.setDestination(x, y);
	}
	
	public void setState(ObjectState state)
	{
		this.state = state;
	}
	
	public Position getPosition() { return position; 	}
	public ObjectState getState() { return state;		}
	
	public abstract void update(float delta);
	public abstract int activate(List< Zombie > zombies, int index);

}
