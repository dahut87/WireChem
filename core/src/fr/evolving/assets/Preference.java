package fr.evolving.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Preference {
	public static Preferences prefs;
	
	public static int init() {
		prefs = Gdx.app.getPreferences("WireWorld - Evolving Games");
		//debug();
		//test();
		if (prefs.contains("log"))
			return prefs.getInteger("log");
		else
			return Gdx.app.LOG_INFO;
	}
	
	public static void debug() {
		Preference.prefs.putInteger("ResolutionX", 1280);
		Preference.prefs.putInteger("ResolutionY", 720);
		Preference.prefs.putBoolean("Fullscreen", false);
		Preference.prefs.putBoolean("VSync", false);
		Preference.prefs.putInteger("log", Gdx.app.LOG_DEBUG);	
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		Preference.prefs.flush();
	}

	public static void test() {
		Preference.prefs.putInteger("ResolutionX", 1920);
		Preference.prefs.putInteger("ResolutionY", 1080);
		Preference.prefs.putBoolean("Fullscreen", true);
		Preference.prefs.putBoolean("VSync", true);
		Preference.prefs.putInteger("log", Gdx.app.LOG_INFO);	
		Gdx.app.setLogLevel(Gdx.app.LOG_INFO);
		Preference.prefs.flush();
	}

}




