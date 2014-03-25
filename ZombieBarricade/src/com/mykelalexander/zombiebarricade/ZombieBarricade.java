package com.mykelalexander.zombiebarricade;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ZombieBarricade extends Game {
	public SpriteBatch batch;
	BitmapFont font;
	Audio audio;
	@Override
	public void create() {		
		Gdx.input.setCatchBackKey(false);
		batch =  new SpriteBatch();
		font = new BitmapFont();
		audio = Gdx.audio;
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		this.getScreen().dispose();
	}

	@Override
	public void render() {
		super.render();
	}

}