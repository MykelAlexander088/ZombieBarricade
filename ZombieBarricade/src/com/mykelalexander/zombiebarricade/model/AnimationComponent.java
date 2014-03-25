package com.mykelalexander.zombiebarricade.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mykelalexander.zombiebarricade.uiutils.AnimationManager;

public class AnimationComponent {
	//LIST OF ALL ANIMATION COMPONENTS IN EXISTENCE
	public static List< AnimationComponent >[] animationComponents;
	public static int[] index;
	
	//LAYERS
	public static final int OBJECT_LAYER = 0;
	public static final int ZOMBIE_LAYER = 1;
	public static final int LIFTED_BLOCK_LAYER = 2;
	public static final int TOP_LAYER = 3;
	
	//INSTANCE VARIABLES ARE PACKAGE PUBLIC. THEY ARE MANIPULATED
	//DIRECTLY BY THE USER OF THE COMPONENT.
	boolean render;
	boolean looping;
	float x;
	float y;
	String animation;
	float stateTime;
	int id;
	
	/**
	 * If this is the first animation component to be created,
	 * instantiate the list.
	 * @param layer - the layer to add the component to
	 */
	public AnimationComponent(final int layer){
		if(animationComponents == null)
			animationComponents = new ArrayList[4];
		
		for(int i = 0; i < animationComponents.length; i++)
			if(animationComponents[i] == null)
				animationComponents[i] = new ArrayList< AnimationComponent >();
		
		if(index == null)
		{
			index = new int[4];
			for(int i = 0; i < index.length; i++)
				index[i] = 0;
		}
		
		animationComponents[layer].add(this);
		id = index[layer]++;
	}
	
	public void switchLayer(final int from, final int to)
	{
		//MOVE THE COMPONENT TO DESIRED LAYER
		AnimationComponent ac = animationComponents[from].remove(this.id);
		animationComponents[to].add(ac);
		
		//IF IT'S THE LAST ITEM IN THE LIST, WE DON'T NEED TO SHIFT
		//THE INDICES
		if(ac.id == index[from]-1)
		{
			index[from]--;
			ac.id = index[to]++;
			return;
		}
		
		//IF IT'S NOT THE LAST ONE, WE NEED TO SHIFT THE INDICES DOWN
		ac.id = index[to]++;
		index[from] = 0;
		for(int i = 0; i < animationComponents[from].size(); i++)
		{
			animationComponents[from].get(i).id = index[from]++;
		}
		
	}
	
	//DRAWS ALL ANIMATIONS THAT ARE FLAGGED FOR RENDERING
	public static void drawAnimations(SpriteBatch batch)
	{
		AnimationManager am = AnimationManager.getAnimationManager();
		TextureRegion tex;
		AnimationComponent ac;
		
		for(int i = 0; i < animationComponents.length; i++)
		{
			for(int j = 0; j < animationComponents[i].size(); j++)
			{
				ac = animationComponents[i].get(j);
				if(!ac.render)
					continue;
				tex = am.getNextFrame(ac.animation, ac.stateTime, ac.looping);
				batch.draw(tex, ac.x, ac.y);
			}
		}
	}
	
	//DRAW LAYER OF ANIMATIONS
	public static void drawAnimations(SpriteBatch batch, int layer)
	{
		AnimationManager am = AnimationManager.getAnimationManager();
		TextureRegion tex;
		AnimationComponent ac;
		
		for(int j = 0; j < animationComponents[layer].size(); j++)
		{
			ac = animationComponents[layer].get(j);
			if(!ac.render)
				continue;
			tex = am.getNextFrame(ac.animation, ac.stateTime, ac.looping);
			batch.draw(tex, ac.x, ac.y);
		}
	}
	
	public static void dispose(){
		for(int i = 0; i < animationComponents.length; i++)
		{
			for(int j = 0; j < animationComponents[i].size(); j++)
			{
				animationComponents[i].remove(j);
			}
			animationComponents[i] = null;
		}
		animationComponents = null;
	}
}
