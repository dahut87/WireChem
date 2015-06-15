package fr.evolving.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fr.evolving.screens.GameScreen;
import fr.evolving.screens.SplashScreen;
import fr.evolving.assets.AssetLoader;

public class main extends Game {
	
	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}