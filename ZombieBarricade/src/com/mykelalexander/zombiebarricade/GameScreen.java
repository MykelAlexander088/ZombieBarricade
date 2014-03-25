package com.mykelalexander.zombiebarricade;

import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

import com.mykelalexander.zombiebarricade.model.AnimationComponent;
import com.mykelalexander.zombiebarricade.model.BarricadeBlock;
import com.mykelalexander.zombiebarricade.model.Level;
import com.mykelalexander.zombiebarricade.model.PlayableObject;
import com.mykelalexander.zombiebarricade.model.PlayableObject.ObjectState;
import com.mykelalexander.zombiebarricade.model.Position;
import com.mykelalexander.zombiebarricade.model.World;
import com.mykelalexander.zombiebarricade.model.Zombie;
import com.mykelalexander.zombiebarricade.model.Zombie.ZombieState;
import com.mykelalexander.zombiebarricade.uiutils.AnimationManager;
import com.mykelalexander.zombiebarricade.uiutils.ControlManager;
import com.mykelalexander.zombiebarricade.uiutils.NumberManager;
import com.mykelalexander.zombiebarricade.uiutils.ParticleManager;
import com.mykelalexander.zombiebarricade.uiutils.SoundManager;
import com.mykelalexander.zombiebarricade.uiutils.TextureManager;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class GameScreen implements Screen
{
	public static enum GameState {
		Running
	}
	
	final ZombieBarricade game;
	public GameState state;
	public World world;
	Music music;
	
	//MID LINE RENDER STUFF
	public ShapeRenderer sr;
	float alpha = 0.4f;
	public boolean showMidLine = false;
	
	
	//VIEW PORT
	public OrthographicCamera camera;
	
	//IMAGES
	String backgroundImageFileName;
	String tileImageFileName;
	
	public GameScreen(final ZombieBarricade game)
	{
		this.game = game;
		state = GameState.Running;
		world = new World( new Level("level1.txt"));
		backgroundImageFileName = IMG_BACKGROUND_0;
		tileImageFileName		= IMG_FOREST_TILE;		
		sr = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(new ControlManager(this));
		loadTextures();
		loadAnimations();
		loadParticles();
		loadSounds();
		loadMusic();
		//CREATE CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 1280);
	}
	
	public void loadMusic()
	{
		music = game.audio.newMusic(Gdx.files.internal(MUSIC_PATH+M_EVIL1));
		music.setVolume(0.3f);
		music.setLooping(true);
		music.play();
		
		
	}
	
	public void loadTextures()
	{
		TextureManager tm = TextureManager.getTextureManager();
		tm.loadTexture(backgroundImageFileName, 0, 0, 800, 1280);
		tm.loadTexture(tileImageFileName, 0, 0, 800, 1280);
		tm.loadTexture(IMG_YELLOW_ZOMBIE, 0, 0, 440, 110);
		tm.loadTexture(IMG_RED_ZOMBIE, 0, 111, 440, 110);
		tm.loadTexture(IMG_BLUE_ZOMBIE, 0, 122, 440, 110);
		tm.loadTexture(IMG_YELLOW_BLOCK, 0, 0, 55, 55);
		tm.loadTexture(IMG_RED_BLOCK, 0, 0, 55, 55);
		tm.loadTexture(IMG_BLUE_BLOCK, 0, 0, 55, 55);
		tm.loadTexture(IMG_CIVILIAN_UI, 0, 607, 110, 55);
		tm.loadTexture(IMG_NUM_FONT, 0, 0, 256, 32);
		tm.loadTexture(IMG_COMBO_UI, 0, 666, 77, 110);
	}
	
	public void loadAnimations()
	{
		TextureManager tm = TextureManager.getTextureManager();
		AnimationManager am = AnimationManager.getAnimationManager();
		
		am.loadAnimation(A_YELLOW_ZOMBIE_WALK, IMG_YELLOW_ZOMBIE, 1.0f/8, 8, 0, 0, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_RED_ZOMBIE_WALK, IMG_RED_ZOMBIE, 1.0f/8, 8, 2, 2, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_BLUE_ZOMBIE_WALK, IMG_BLUE_ZOMBIE, 1.0f/8, 8, 4, 4, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_YELLOW_BLOCK_DEATH, IMG_YELLOW_ZOMBIE, 1.0f/13, 8, 1, 1, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_RED_BLOCK_DEATH, IMG_RED_ZOMBIE, 1.0f/13, 8, 3, 3, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_BLUE_BLOCK_DEATH, IMG_BLUE_ZOMBIE, 1.0f/13, 8, 5, 5, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_YELLOW_BLOCK, IMG_YELLOW_BLOCK, 1, 1, 8, 8, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_RED_BLOCK, IMG_RED_BLOCK, 1, 1, 9, 9, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_BLUE_BLOCK, IMG_BLUE_BLOCK, 1, 1, 10, 10, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_YELLOW_BLOCK_ACTIVATE, IMG_YELLOW_BLOCK, 1.0f/13, 8, 8, 8, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_RED_BLOCK_ACTIVATE, IMG_RED_BLOCK, 1.0F/8, 8, 9, 9, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_BLUE_BLOCK_ACTIVATE, IMG_BLUE_BLOCK, 1.0F/8, 8, 10, 10, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_CIVILIAN_WALK, IMG_CIVILIAN, 1.0f/8, 8, 6, 6, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_DYING_CIVILIAN, IMG_CIVILIAN, 1.0f/13, 8, 7, 7, TILE_WIDTH, TILE_HEIGHT);
		am.loadAnimation(A_COMBO_UI, IMG_CIVILIAN_UI, 1, 1, 12, 12, 110, 55);
		am.loadAnimation(A_CIVILIAN_UI, IMG_CIVILIAN_UI, 1, 1, 11, 11, 110, 55);
	}
	
	public void loadParticles()
	{
		ParticleManager pm = ParticleManager.getParticleManager();
		pm.loadParticle(P_FIRE_1);
		pm.loadParticle(P_PLASMA_1);
		pm.loadParticle(P_LIGHTNING_1);
		pm.loadParticle(P_PLACE_BLOCK);
		pm.loadParticle(P_YELLOW_DEATH);
		pm.loadParticle(P_FIRE_EXPLOSION);
		pm.loadParticle(P_BLUE_DEATH);
	}
	
	public void loadSounds()
	{
		SoundManager sm = SoundManager.getSoundManager();
		sm.loadSound(S_RECIEVE_BLOCK, game.audio);
		sm.loadSound(S_RED_BLOCK_LIFTED, game.audio);
		sm.loadSound(S_BLUE_BLOCK_LIFTED, game.audio);
		sm.loadSound(S_YELLOW_BLOCK_LIFTED, game.audio);
		sm.loadSound(S_BLOCK_PLACED, game.audio);
		sm.loadSound(S_YELLOW_DEATH, game.audio);
		sm.loadSound(S_BLOCK_ACTIVATED, game.audio);
		sm.loadSound(S_RED_DEATH, game.audio);
		sm.loadSound(S_BLUE_DEATH, game.audio);
		sm.loadSound(S_CIVILIAN_DEATH, game.audio);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		if(state == GameState.Running)
			updateRunning(delta);
		
		game.batch.end();
		
		drawMidLine();
	}
	
	public void updateRunning(float delta)
	{
		world.update(delta);
		drawWorld(delta);
	}
	
	public void drawWorld(float delta)
	{
		ParticleManager pm = ParticleManager.getParticleManager();
		TextureManager tm = TextureManager.getTextureManager();
		SoundManager sm = SoundManager.getSoundManager();
		AnimationManager am = AnimationManager.getAnimationManager();
		
		game.batch.draw(tm.getTextureRegion(tileImageFileName), 0, 0);
		AnimationComponent.drawAnimations(game.batch, AnimationComponent.OBJECT_LAYER);
		AnimationComponent.drawAnimations(game.batch, AnimationComponent.ZOMBIE_LAYER);
		game.batch.draw(tm.getTextureRegion(backgroundImageFileName), 0, 0);
		AnimationComponent.drawAnimations(game.batch, AnimationComponent.LIFTED_BLOCK_LAYER);
		NumberManager.drawText(game.batch, "" + (world.blockManager.getBlockTimer() + 1), 680, SCREEN_HEIGHT - 1052, 12, 22);
		
		//DRAW CIVILIAN COUNT
		if(world.getLevelProperties().hasCivilians)
		{
			game.batch.draw(am.getNextFrame(A_CIVILIAN_UI, delta, true), 677, SCREEN_HEIGHT - 253);
			NumberManager.drawText(game.batch, "" + world.civilians.savedCivilians(), 723, SCREEN_HEIGHT - 243, 12, 22);
			NumberManager.drawText(game.batch, "" + world.civilians.maxCivilians(), 745, SCREEN_HEIGHT - 243, 12, 22);
		}
		//DRAW COMBO
		if(world.comboManager.visible)
		{
			Color color = game.batch.getColor();
			game.batch.setColor(1, 1, 1, world.comboManager.appearance);
			game.batch.draw(am.getNextFrame(A_COMBO_UI, delta, true), world.comboManager.pos.xPos, world.comboManager.pos.yPos);
			game.batch.setColor(color);
			NumberManager.drawText(game.batch, "" + world.comboManager.currentCombo, (int)world.comboManager.pos.xPos + 30 - 12*(("" + world.comboManager.currentCombo).length() - 1) , (int)world.comboManager.pos.yPos + 5, 12, 22);
			NumberManager.drawText(game.batch, "" + world.comboManager.getScoreMultiplier(), (int)world.comboManager.pos.xPos + 60, (int)world.comboManager.pos.yPos + 5, 12, 22);
		}
		//DRAW SCORE
		NumberManager.drawText(game.batch, "" + world.comboManager.getScore(), 683, SCREEN_HEIGHT - 99, 12, 22);
		AnimationComponent.drawAnimations(game.batch, AnimationComponent.TOP_LAYER);

		pm.drawParticles(delta, game.batch);
		sm.playSounds();
	}
	
	public void drawMidLine()
	{
		if(showMidLine)
		{
			Gdx.gl10.glLineWidth(2);
			sr.setProjectionMatrix(camera.combined);
			sr.begin(ShapeType.Line);
			sr.setColor(1, 0, 0, alpha);
			sr.line(BOARD_X_OFFSET, BOARD_Y_OFFSET - TILE_HEIGHT * 10, BOARD_X_OFFSET + TILE_WIDTH * 10, BOARD_Y_OFFSET - TILE_HEIGHT * 10);
			sr.end();
			
			alpha -= 0.01f;
			if(alpha <= 0)
			{
				showMidLine = false;
				alpha = 1;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		if(Gdx.app.getType() == ApplicationType.Desktop)
		{
			Vector2 size = Scaling.fit.apply(800, 1280, width, height);
			int viewportX = (int)(width - size.x) / 2;
			int viewportY = (int)(height - size.y) / 2;
			int viewportWidth = (int)size.x;
			int viewportHeight = (int)size.y;
			Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		}
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		TextureManager.getTextureManager().dispose();
		ParticleManager.getParticleManager().dispose();
		SoundManager.getSoundManager().dispose();	
		//AnimationManager.getAnimationManager().dispose();
		AnimationComponent.dispose();
		music.stop();
		music.dispose();
		System.gc();
	}
}