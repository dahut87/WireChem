package fr.evolving.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.Preference;
import fr.evolving.screens.SplashScreen;

public class main extends Game {

	@Override
	public void create() {
		Preference.init();
		Gdx.app.debug("wirechem-main","Récupération de la résolution des préférences.");
		if (Preference.prefs.getInteger("ResolutionX") > 0
				&& Preference.prefs.getInteger("ResolutionY") > 0) {
			try {
				int ResolutionX = Preference.prefs.getInteger("ResolutionX");
				int ResolutionY = Preference.prefs.getInteger("ResolutionY");
				boolean Fullscreen = Preference.prefs.getBoolean("Fullscreen");
				boolean VSync = Preference.prefs.getBoolean("VSync");
				Gdx.graphics.setDisplayMode(ResolutionX, ResolutionY,
						Fullscreen);
				Gdx.graphics.setVSync(VSync);
			} catch (ClassCastException e) {
				Gdx.app.error("wirechem-main","***** Impossible d'appliquer les préférences graphiques");
				Gdx.app.debug("wirechem-main", e.getMessage());
			} finally {
				Gdx.app.log("wirechem-main","***** Changement de résolution selon préférences graphiques");
			}
		} else
			Gdx.app.debug("wirechem-main", "...Aucune préférence !");
		AssetLoader.init();
		Gdx.app.debug("wirechem-main", "Creation de l'objet SplashScreen.");
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}