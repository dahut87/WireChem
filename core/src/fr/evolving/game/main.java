package fr.evolving.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fr.evolving.screens.GameScreen;
import fr.evolving.screens.SplashScreen;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.Preference;

public class main extends Game {
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Preference.init());		
		Gdx.app.debug(getClass().getSimpleName(), "Récupération de la résolution des préférences.");		
		if (Preference.prefs.getInteger("ResolutionX")>0 && Preference.prefs.getInteger("ResolutionY")>0)	{
			try {
				int ResolutionX=Preference.prefs.getInteger("ResolutionX");
				int ResolutionY=Preference.prefs.getInteger("ResolutionY");
				boolean Fullscreen=Preference.prefs.getBoolean("Fullscreen");
				boolean VSync=Preference.prefs.getBoolean("VSync");
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
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}