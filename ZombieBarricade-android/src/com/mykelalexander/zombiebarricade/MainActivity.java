package com.mykelalexander.zombiebarricade;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 			= false;
        cfg.useAccelerometer 	= false;
        cfg.useCompass 			= false;
        cfg.useWakelock 		= true;
        
        initialize(new ZombieBarricade(), cfg);
    }
}