package com.mykelalexander.zombiebarricade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Zombie Barricade";
		cfg.useGL20 = false;
		cfg.width = /*800;*/ cfg.getDesktopDisplayMode().width;
		cfg.height = /*1280;*/ cfg.getDesktopDisplayMode().height;
		cfg.fullscreen = true;
		cfg.resizable = false;
		new LwjglApplication(new ZombieBarricade(), cfg);
	}
}
