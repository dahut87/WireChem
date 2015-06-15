package fr.evolving.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
	public static Skin Skin_level;
	public static TextureAtlas Atlas_level;	
	public static Texture Texture_fond;
	public static Texture Texture_fond2;
	public static Texture Texture_logo;
	public static Sound intro;
	private static Preferences prefs;
	
	public static void addstyle(TextureAtlas Atlas_level,String Name) {
		AtlasRegion AnAtlasRegion = Atlas_level.findRegion(Name);
		if (AnAtlasRegion==null) return;
		TextureData ATextureData = AnAtlasRegion.getTexture().getTextureData();
		ATextureData.prepare();
		Pixmap Pixmap_Over=ATextureData.consumePixmap();
		Pixmap Pixmap_Disable=new Pixmap(Pixmap_Over.getWidth(), Pixmap_Over.getHeight(), Pixmap.Format.RGBA8888);
		Pixmap Pixmap_Down=new Pixmap(Pixmap_Over.getWidth(), Pixmap_Over.getHeight(), Pixmap.Format.RGBA8888);
		for(int x=0; x < Pixmap_Over.getWidth(); x++)
		{
			for(int y =0; y < Pixmap_Over.getHeight(); y++)
			{
				Color acolor= new Color(Pixmap_Over.getPixel(x, y));
				Pixmap_Down.drawPixel(x, y,Color.rgba8888(acolor.a,0f,0f,acolor.a));
				Pixmap_Disable.drawPixel(x, y, Color.rgba8888(acolor.r*0.352f,acolor.g*0.352f,acolor.b*0.352f,acolor.a));
				Pixmap_Over.drawPixel(x, y, Color.rgba8888(acolor.r,acolor.g*0.2f,acolor.b*0.2f,acolor.a));
			}
		}
		Atlas_level.addRegion(Name+"_disabled", new TextureRegion(new Texture(Pixmap_Disable),AnAtlasRegion.getRegionX(),AnAtlasRegion.getRegionY(),AnAtlasRegion.getRegionWidth(),AnAtlasRegion.getRegionHeight()));
		Atlas_level.addRegion(Name+"_over", new TextureRegion(new Texture(Pixmap_Over),AnAtlasRegion.getRegionX(),AnAtlasRegion.getRegionY(),AnAtlasRegion.getRegionWidth(),AnAtlasRegion.getRegionHeight()));
		Atlas_level.addRegion(Name+"_down", new TextureRegion(new Texture(Pixmap_Down),AnAtlasRegion.getRegionX(),AnAtlasRegion.getRegionY(),AnAtlasRegion.getRegionWidth(),AnAtlasRegion.getRegionHeight()));
	}
	
	public static void loadall() {
		Gdx.app.log("Chargements des éléments multimédia","ok");
		Texture_fond = new Texture(Gdx.files.internal("pictures/fond.png"));
		Texture_fond.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Texture_fond2 = new Texture(Gdx.files.internal("pictures/fond2.png"));
		Texture_fond2.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Atlas_level= new TextureAtlas(Gdx.files.internal("textures/level.pack"));
		for(int i=0; i < 5; i++)
			addstyle(Atlas_level,"leveler"+String.valueOf(i));
		addstyle(Atlas_level,"arrows");
		addstyle(Atlas_level,"arrows2");
		addstyle(Atlas_level,"exit2");
		Skin_level = new Skin(Gdx.files.internal("textures/level.json"),Atlas_level);
		prefs = Gdx.app.getPreferences("WireWorld");

		if (!prefs.contains("resolutionx")) {
			prefs.putInteger("resolutionx", 0);
		}
	}

	public static void load() {
		Gdx.app.log("Chargements des éléments minimalistes","ok");
		Texture_logo = new Texture(Gdx.files.internal("pictures/logo.png"));
		Texture_logo.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		intro = Gdx.audio.newSound(Gdx.files.internal("musics/intro.mp3"));
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		Texture_logo.dispose();
		Texture_fond.dispose();
		Skin_level.dispose();
		Atlas_level.dispose();
		intro.dispose();
	}

}