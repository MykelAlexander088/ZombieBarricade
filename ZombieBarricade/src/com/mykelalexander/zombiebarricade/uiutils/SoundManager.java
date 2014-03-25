package com.mykelalexander.zombiebarricade.uiutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.SOUND_PATH;

public class SoundManager 
{
	//THIS CLASS IS A SINGLETON, THIS IS THE ONLY OBJECT THAT WILL EXIST
	private static SoundManager singleton = null;
	
	//WHEN A SOUND NEEDS TO BE PLAYED, IT IS PLACED IN THIS LIST
	private List< String > soundMessages;
	
	private List< String > keys;
	
	//HASH MAP OF ALL SOUNDS THAT ARE LOADED
	Map<String, Sound> soundPool;
	
	/**
	 * Private constructor instantiates the data structures.  It is a singleton
	 * hence its privacy setting.
	 */
	private SoundManager()
	{
		soundMessages = new ArrayList< String >();
		soundPool = new HashMap<String, Sound>();
		keys = new ArrayList< String >();
	}
	
	/**
	 * Static accessor for singleton
	 * @return the singleton SoundManager object
	 */
	public static SoundManager getSoundManager()
	{
		//IF SOUND MANAGER HAS NEVER BEEN USED, IT MUST BE CREATED
		if( singleton == null)
		{
			singleton = new SoundManager();
		}
		return singleton;
	}
	
	/**
	 * This method loads a sound file and places it in the soundPool
	 * @param filename - the name of the sound file which is also the key
	 * @param audio - audio context from Gdx
	 */
	public void loadSound(String filename, Audio audio)
	{
		soundPool.put(filename, audio.newSound(Gdx.files.internal(SOUND_PATH+filename)));
		keys.add(filename);
	}
	
	/**
	 * This places the key of the sound on a message list to be
	 * played next time the playSounds() method is called
	 * @param filename - the key and name of the sound file
	 */
	public void placeSoundMessage(String filename)
	{
		soundMessages.add(filename);
	}
	
	/**
	 * plays all the sounds on the message list
	 */
	public void playSounds()
	{
		for(int i = 0; i < soundMessages.size(); i++)
		{
			soundPool.get(soundMessages.remove(i--)).play();
		}
	}
	
	/**
	 * loops a sound until stopLoop() is called
	 * @param filename - the key to the sound (should be its filename)
	 */
	public void loopSound(String filename, float volume, float pitch, float pan)
	{
		soundPool.get(filename).loop(volume, pitch, pan);
	}
	
	/**
	 * called after loopSound(), stops the sound from looping
	 * @param filename - the key to the sound (should be its filename)
	 */
	public void stopLoop(String filename)
	{
		soundPool.get(filename).stop();
	}
	
	/**
	 * frees all resources
	 */
	public void dispose()
	{
		for(int i = 0; i < keys.size(); i++)
		{
			soundPool.get(keys.remove(i--)).dispose();
		}
	}	
	
}
