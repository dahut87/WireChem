package fr.evolving.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.evolving.assets.AssetLoader;
import fr.evolving.game.main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new main(), config);
		Gdx.app.log("****", "Lancement de l'application \'Desktop\' WireChem...");
	}
}
