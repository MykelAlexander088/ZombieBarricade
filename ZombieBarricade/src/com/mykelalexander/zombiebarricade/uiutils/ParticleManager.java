package com.mykelalexander.zombiebarricade.uiutils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.PARTICLE_PATH;
import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.IMAGE_PATH;

public class ParticleManager 
{
	//THIS CLASS IS A SINGLE, THIS IS THE ONLY OBJECT THAT WILL EXIST
	private static ParticleManager singleton = null;
	
	//HOLDS THE EFFECTS THAT ARE POOLED
	private Array< PooledEffect > effects;
	
	//THIS STORES THE AVAILABLE PARTICLES TO DISPLAY
	private Map < String, ParticleEffectPool > particleEffectPools;
	
	/**
	 * Private constructor instantiates the data structures.  It is a singleton
	 * hence it's privacy setting.
	 */
	private ParticleManager()
	{
		particleEffectPools = new HashMap< String, ParticleEffectPool >();
		effects = new Array<PooledEffect>();
		
	}
	
	/**
	 * static accessor for singleton
	 * @return - a reference to the ParticleManager
	 */
	public static ParticleManager getParticleManager()
	{
		if(singleton == null)
		{
			singleton = new ParticleManager();
		}
		return singleton;
	}
	
	/**
	 * Loads a new particle effect and places it's effect pool into the Hash Map
	 * it's key is the filename.
	 * @param filename
	 */
	public void loadParticle(String filename)
	{
		//INSTANTIATES A NEW PARTICLE
		ParticleEffect effect = new ParticleEffect();
		
		//LOADS THE PARTICLE FROM THE .P FILE AND LINKS IT TO THE IMAGE PATH
		effect.load(Gdx.files.internal(PARTICLE_PATH+filename), Gdx.files.internal(IMAGE_PATH));
		
		//CREATES A POOL FOR THE NEW PARTICLE
		ParticleEffectPool effectPool = new ParticleEffectPool(effect, 1, 2);
		
		//ADDS THE POOL TO THE HASH MAP FOR LATER USE, IT CAN BE RETRIEVED BY IT'S FILENAME
		particleEffectPools.put(filename, effectPool);
	}
	
	/**
	 * puts a particle in the list off particles that will be played when the drawParticles method
	 * is called
	 * @param filename - the .p filename of the particle.  This is also it's key.
	 * @param x - x position of destination
	 * @param y - y position of destination
	 */
	public void playParticleEffect(String filename, float x, float y)
	{
		//RETRIEVE THE PARTICLE EFFECT
		PooledEffect effect = particleEffectPools.get(filename).obtain();
		
		//SET IT TO PLAY AT THE CHOSEN POSITION
		effect.setPosition(x, y);
		
		//ADD IT TO THE ARRAY OF PARTICLES THAT WILL BE PLAYED ON THE NEXT CALL TO 
		effects.add(effect);
	}
	
	/**
	 * processes the rendering of particles
	 * @param delta - the change in time
	 * @param batch - access to the screen
	 */
	public void drawParticles(float delta, SpriteBatch batch)
	{
		for(int i = effects.size - 1; i >= 0; i--)
		{
			PooledEffect effect = effects.get(i);
			effect.draw(batch, delta);
			if(effect.isComplete())
			{
				effect.free();
				effects.removeIndex(i);
			}
		}
	}
	
	/**
	 * Frees all of the particle resources
	 */
	public void dispose()
	{
		for(int i = effects.size - 1; i >= 0; i--)
			effects.get(i).free();
		effects.clear();
	}
	
}
