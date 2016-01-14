package fr.evolving.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;

public class Preference {
	public static Preferences prefs;
	
	public static int init() {
		prefs = Gdx.app.getPreferences("WireWorld - Evolving Games");
		//debug();
		//test();
		if (prefs.contains("log"))
			return prefs.getInteger("log");
		else
		{
			defaults();
			return Gdx.app.LOG_INFO;
		}
	}
	
	public static Vector2 getmaxresolution() {
		Graphics.DisplayMode[] modes=Gdx.graphics.getDisplayModes();
		int totalpixel=0;
		int res;
		for(DisplayMode mode:modes) {
			int temppixel=mode.height*mode.width;
			if (temppixel>totalpixel) totalpixel=temppixel;
		}
		for(DisplayMode mode:modes)
			if (totalpixel==mode.height*mode.width)
				return new Vector2(mode.width,mode.height);
		return null;
	}
	
	public static void defaults() {
		Vector2 maxres=getmaxresolution();
		Gdx.app.log("Preferences","Preference par defaut avec resolution native :"+maxres.x+"x"+maxres.y);
		Preference.prefs.putString("userdata", "local:test.db");
		Preference.prefs.putString("gamedata", "local:test.db");
		Preference.prefs.putString("statdata", "local:test.db");
		Preference.prefs.putInteger("ResolutionX", (int)maxres.x);
		Preference.prefs.putInteger("ResolutionY", (int)maxres.y);
		Preference.prefs.putInteger("Resolution", 9);
		Preference.prefs.putBoolean("Fullscreen", true);
		Preference.prefs.putBoolean("Sound", true);
		Preference.prefs.putBoolean("Tutorial", true);
		Preference.prefs.putBoolean("VSync", true);
		Preference.prefs.putBoolean("Refresh", false);
		Preference.prefs.putBoolean("Animation", true);
		Preference.prefs.putBoolean("Language", false);	
		Preference.prefs.putString("world", "test pour voir");
		Preference.prefs.putFloat("Effect", 1.0f);
		Preference.prefs.putFloat("Music",0.75f);
		Preference.prefs.putInteger("Adaptation", 2);
		Preference.prefs.putInteger("Quality", 2);	
		Preference.prefs.putInteger("log", Gdx.app.LOG_INFO);
		Preference.prefs.flush();
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




