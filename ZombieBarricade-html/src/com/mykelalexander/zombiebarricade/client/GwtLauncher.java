package com.mykelalexander.zombiebarricade.client;

import com.mykelalexander.zombiebarricade.ZombieBarricade;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(540, 960);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new ZombieBarricade();
	}
}