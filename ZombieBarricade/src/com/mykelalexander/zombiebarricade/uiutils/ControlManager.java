package com.mykelalexander.zombiebarricade.uiutils;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.mykelalexander.zombiebarricade.GameScreen;
import com.mykelalexander.zombiebarricade.GameScreen.GameState;
import com.mykelalexander.zombiebarricade.ZombieBarricade;
import com.mykelalexander.zombiebarricade.model.BarricadeBlock;
import com.mykelalexander.zombiebarricade.model.Position;
import com.mykelalexander.zombiebarricade.model.World;

import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class ControlManager implements InputProcessor{
	
	private GameScreen game;
	private Vector3 touch;
	
	public ControlManager(GameScreen game)
	{
		this.game = game;
		touch = new Vector3();
	}
	
	private void unproject()
	{
		if( Gdx.app.getType() == ApplicationType.Desktop)
		{
			Vector2 size = Scaling.fit.apply(800, 1280, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			game.camera.unproject(touch, (int)(Gdx.graphics.getWidth() - size.x) / 2, (int)(Gdx.graphics.getHeight() - size.y) / 2, (int)size.x, (int)size.y);
		}
		else
			game.camera.unproject(touch);
		
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		//TOGGLE FULLSCREEN MODE
		if(keycode == Input.Keys.F4)
		{
			int width = Gdx.app.getGraphics().getDesktopDisplayMode().width;
			int height = Gdx.app.getGraphics().getDesktopDisplayMode().height;
			
			if(Gdx.app.getGraphics().isFullscreen())
			{
				Gdx.app.getGraphics().setDisplayMode(800, 1280, false);
			}
			else
				Gdx.app.getGraphics().setDisplayMode(width, height, true);
			
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch.x = screenX;
		touch.y = screenY;
		touch.z = 0;
		unproject();
		Position pos;
		if(game.state == GameState.Running && !game.world.blockManager.isBlockLifted())
		{ 
			List< BarricadeBlock > blockPool = game.world.blockManager.getBlockPool();
			for(int i = 0; i < blockPool.size(); i++)
			{
				pos = blockPool.get(i).getPosition();
				if(touch.x >= pos.xPos && touch.x <= pos.xPos + 55 && touch.y >= pos.yPos && touch.y <= pos.yPos + 55)
				{
					game.world.blockManager.liftBlock(i);
					break;
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//STOP LIFTED LOOPS
		SoundManager sm = SoundManager.getSoundManager();
		sm.stopLoop(S_RED_BLOCK_LIFTED);
		sm.stopLoop(S_BLUE_BLOCK_LIFTED);
		sm.stopLoop(S_YELLOW_BLOCK_LIFTED);
		
		touch.x = screenX;
		touch.y = screenY;
		touch.z = 0;
		unproject();
		
		if(game.world.blockManager.isBlockLifted())
		{
			if(touch.x >= BOARD_X_OFFSET && touch.x <= BOARD_X_OFFSET + TILE_WIDTH * WORLD_WIDTH
					&& touch.y <= BOARD_Y_OFFSET - TILE_WIDTH * WORLD_HEIGHT/2 && touch.y >= BOARD_Y_OFFSET - TILE_HEIGHT * WORLD_HEIGHT)
			{
				for(int y = 10; y < 20; y++)
				{
					for(int x = 0; x < 10; x++)
					{
						if(touch.x >= x * TILE_WIDTH + BOARD_X_OFFSET && touch.x <= x * TILE_WIDTH + BOARD_X_OFFSET + TILE_WIDTH &&
								touch.y >= BOARD_Y_OFFSET - y * TILE_HEIGHT - TILE_HEIGHT && touch.y <= BOARD_Y_OFFSET - y * TILE_HEIGHT )
						{
							game.world.blockManager.placeBlock(x * TILE_WIDTH + BOARD_X_OFFSET, BOARD_Y_OFFSET - y * TILE_HEIGHT - TILE_HEIGHT, game.world.zombieManager.getZombieWorldLayer());
						}
					}
				}
			}
			else if(touch.y >= (BOARD_Y_OFFSET - TILE_HEIGHT * 10))
			{
				game.showMidLine = true;
				game.world.blockManager.returnBlockToPool();
			}
			else
				game.world.blockManager.returnBlockToPool();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(game.world.blockManager.isBlockLifted())
		{
			touch.x = screenX;
			touch.y = screenY;
			touch.z = 0;
			unproject();
			Position pos = game.world.blockManager.getLiftedBlock().getPosition();
			pos.xPos = pos.xDes = (int)touch.x - TILE_WIDTH/2;
			pos.yPos = pos.yDes = (int)touch.y - TILE_HEIGHT/2;
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
