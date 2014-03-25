package com.mykelalexander.zombiebarricade.model;

/**
 * This class contains all movement based methods and is used by any object
 * that has a position in the world.  All of it's members are public
 * and it is intended for use by aggregation.
 * @author Mykel Alexander
 *
 */
public class Position 
{
	public float xPos;
	public float yPos;
	public float xDes;
	public float yDes;
	
	public float xVel;
	public float yVel;
	
	/**
	 * Constructor initializes an initial position
	 * @param xPos - the initial x position
	 * @param yPos - the initial y position
	 */
	public Position( float xPos, float yPos )
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 *  create a position that is already in motion
	 * @param xPos - the initial x position
	 * @param yPos - the initial y position
	 * @param xVel - the velocity in the x direction
	 * @param yVel - the velocity in the y direction
	 */
	public Position( float xPos, float yPos, float xVel, float yVel)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	/** setter methods **/
	public void setVelocity( float xVel, float yVel )	{ this.xVel = xVel; this.yVel = yVel; }
	public void setDestination(float xDes, float yDes)	{ this.xDes = xDes; this.yDes = yDes; }
	
	/**
	 * Move the position based on the current velocity
	 */
	public void move()
	{
		xPos += xVel;
		yPos += yVel;
	}
	
	/**
	 * advance toward the destination position 
	 */
	public void advance()
	{
		if( xPos < xDes && !hasReachedDestination() )
		{
			xPos += xVel;
			if(xPos > xDes)
				xPos = xDes;
		}
		if( yPos < yDes && !hasReachedDestination())
		{
			yPos += yVel;
			if( yPos > yDes)
				yPos = yDes;
		}
		
		if( xPos > xDes && !hasReachedDestination())
		{
			xPos -= xVel;
			if( xPos < xDes )
				xPos = xDes;
		}
		
		if( yPos > yDes && !hasReachedDestination())
		{
			yPos -= yVel;
			if( yPos < yDes)
				yPos = yDes;
		}
		
		
	}
	
	/**
	 * move directly to destination, ignoring velocity
	 */
	public void instantMoveToDest()
	{
		xPos = xDes;
		yPos = yDes;
	}
	
	/**
	 * @return - are the destination and position parameters equal?
	 */
	public boolean hasReachedDestination()
	{
		return (xPos == xDes && yPos == yDes);
	}
	
	

}
