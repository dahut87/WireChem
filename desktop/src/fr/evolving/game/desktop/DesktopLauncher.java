package fr.evolving.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.evolving.assets.AssetLoader;
import fr.evolving.game.main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		//config.width = 1920;
		//config.height = 1080;
		
		config.fullscreen = false;
		config.width = AssetLoader.width;
		config.height = AssetLoader.height;
		new LwjglApplication(new main(), config);
	}
}
