package fr.evolving.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.evolving.game.main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		//config.width = 1920;
		//config.height = 1080;
		config.fullscreen = false;
		config.width = 1280;
		config.height = 1024;
		new LwjglApplication(new main(), config);
	}
}
