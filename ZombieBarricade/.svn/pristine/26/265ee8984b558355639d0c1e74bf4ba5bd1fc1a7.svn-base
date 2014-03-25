package com.mykelalexander.zombiebarricade.uiutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.IMAGE_PATH;

public class TextureManager 
{
	//THIS CLASS IS A SINGLETON, THIS IS THE ONLY OBJECT THAT WILL EXIST
	private static TextureManager singleton = null;
	
	//STORAGE OF ALL TEXTURES
	private List< Texture > textures;
	
	//GRANTS ACCESS TO THE REGIONS OF THE TEXTURES.  THIS IS USED FOR DRAWING
	private Map< String, TextureRegion > textureRegions;
	
	/**
	 * Private constructor instantiates the data structures.  It is a singleton
	 * hence its privacy setting.
	 */
	private TextureManager()
	{
		textures = new ArrayList< Texture >();
		textureRegions = new HashMap< String, TextureRegion>();
	}
	
	/**
	 * Static accessor for singleton
	 * @return the singleton TextureManager object
	 */
	public static TextureManager getTextureManager()
	{
		//IF A TEXTURE MANAGER WAS NEVER RECIEVED THEN IT MUST BE CREATED
		if( singleton == null)
		{
			singleton = new TextureManager();
		}
		return singleton;
	}
	
	/**
	 * Loads a new texture and uses a specified region of it then puts it  into the textureRegion Hash Map
	 * @param filename - the filename of the texture which is also used as the key
	 * @param x - the x position of the region you wish to use
	 * @param y - the y position of the region you wish to use
	 * @param w - width of the region
	 * @param h - height of the region
	 */
	public void loadTexture(String filename, int x, int y, int w, int h)
	{
		Texture tex = new Texture(Gdx.files.internal(IMAGE_PATH+filename));
		TextureRegion region = new TextureRegion(tex, x, y, w, h);
		
		textures.add(tex);
		textureRegions.put(filename, region);
	}
	
	/**
	 * Loads a new texture and puts it into the textureRegion Hash Map
	 * @param filename - the filename of the texture which is also used as the key
	 */
	public void loadTexture(String filename)
	{
		Texture tex = new Texture(Gdx.files.internal(IMAGE_PATH+filename));
		int w = tex.getWidth();
		int h = tex.getHeight();
		TextureRegion region = new TextureRegion(tex, 0, 0, w, h);
		
		textures.add(tex);
		textureRegions.put(filename, region);
	}
	
	/**
	 * obtains a previously loaded texture and returns it as a TextureRegion
	 * @param filename - the key of the region should be its filename
	 * @return - a texture region linked to the specified key (filename)
	 */
	public TextureRegion getTextureRegion(String filename)
	{
		return textureRegions.get(filename);
	}
	
	/**
	 * obtains a previously loaded texture and returns it
	 * @param filename - the key of the region should be it's filename
	 * @return - the texture linked to the specified key
	 */
	public Texture getTexture(String filename)
	{
		return textureRegions.get(filename).getTexture();
	}
	/**
	 * Frees resources from memory
	 */
	public void dispose()
	{
		for(int i = 0; i < textures.size(); i++)
		{
			textures.get(i).dispose();
		}
		//textureRegions.clear();
	}
	
}
