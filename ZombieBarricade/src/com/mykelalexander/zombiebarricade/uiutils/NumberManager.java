package com.mykelalexander.zombiebarricade.uiutils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.mykelalexander.zombiebarricade.ZombieBarricadeConstants.*;

public class NumberManager 
{
	public static void drawText(SpriteBatch batch, String line, int x, int y, int w, int h )
	{
		TextureManager tm = TextureManager.getTextureManager();
		int len = line.length();
		int srcX = 0;
		int srcWidth = 0;
		for(int i = 0; i < len; i++)
		{
			char character = line.charAt(i);
			
			if(character == ' ')
			{
				x += w;
				continue;
			}
			srcX = (character - '0') * w;
			srcWidth = w;
			batch.draw(tm.getTexture(IMG_NUM_FONT), x, y, srcX, 0, srcWidth, h);
			x += srcWidth;
		}
	}
}
