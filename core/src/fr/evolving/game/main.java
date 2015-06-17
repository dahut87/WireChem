package fr.evolving.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fr.evolving.screens.GameScreen;
import fr.evolving.screens.SplashScreen;
import fr.evolving.assets.AssetLoader;

public class main extends Game {
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(AssetLoader.setpref());		
		//debug();
		test();
		Gdx.app.debug(getClass().getSimpleName(), "Récupération de la résolution des préférences.");		
		if (AssetLoader.prefs.contains("ResolutionX") && AssetLoader.prefs.contains("ResolutionX"))	{
			try {
				int ResolutionX=AssetLoader.prefs.getInteger("ResolutionX");
				int ResolutionY=AssetLoader.prefs.getInteger("ResolutionY");
				boolean Fullscreen=AssetLoader.prefs.getBoolean("Fullscreen");
				boolean VSync=AssetLoader.prefs.getBoolean("VSync");
				Gdx.graphics.setDisplayMode(ResolutionX, ResolutionY, Fullscreen);
				Gdx.graphics.setVSync(VSync);
			} catch (ClassCastException e) {
				Gdx.app.error("****","Impossible d'appliquer les préférences graphiques");
				Gdx.app.debug(getClass().getSimpleName(),e.getMessage());
			} finally {
				Gdx.app.log("****","Changement de résolution selon préférences graphiques");
			}
		}
		else
			Gdx.app.debug(getClass().getSimpleName(), "...Aucune préférence !");			
    	AssetLoader.init();
		Gdx.app.debug(getClass().getSimpleName(), "Creation de l'objet SplashScreen.");
		setScreen(new SplashScreen(this));
	}
	
	public void debug() {
		AssetLoader.prefs.putInteger("ResolutionX", 1280);
		AssetLoader.prefs.putInteger("ResolutionY", 1024);
		AssetLoader.prefs.putBoolean("Fullscreen", false);
		AssetLoader.prefs.putBoolean("VSync", false);
		AssetLoader.prefs.putInteger("log", Gdx.app.LOG_DEBUG);	
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		AssetLoader.prefs.flush();
	}
	
	public void test() {
		AssetLoader.prefs.putInteger("ResolutionX", 1280);
		AssetLoader.prefs.putInteger("ResolutionY", 1024);
		AssetLoader.prefs.putBoolean("Fullscreen", true);
		AssetLoader.prefs.putBoolean("VSync", true);
		AssetLoader.prefs.putInteger("log", Gdx.app.LOG_INFO);	
		Gdx.app.setLogLevel(Gdx.app.LOG_INFO);
		AssetLoader.prefs.flush();
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}