package fr.evolving.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class AssetLoader {
	public static Skin Skin_level;
	public static TextureAtlas Atlas_level;
	public static TextureAtlas Atlas_game;	
	public static Texture Texture_fond;
	public static Texture Texture_fond2;
	public static Texture Texture_logo;
	public static Sound intro;
	public static int width;
	public static int height;
	public static float ratio;
	public static boolean stretch=false;
	public static Preferences prefs;
	public static ScalingViewport viewport;
	public static OrthographicCamera Camera;
	
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
		Gdx.app.debug("AssetLoader","Chargements de tout les éléments multimédia");
		Texture_fond = new Texture(Gdx.files.internal("pictures/fond.png"));
		Texture_fond.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Texture_fond2 = new Texture(Gdx.files.internal("pictures/fond2.png"));
		Texture_fond2.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Atlas_level= new TextureAtlas(Gdx.files.internal("textures/level.pack"));
		Atlas_game = new TextureAtlas(Gdx.files.internal("textures/game.pack"));
		for(int i=0; i < 5; i++)
			addstyle(Atlas_level,"leveler"+String.valueOf(i));
		addstyle(Atlas_level,"arrows");
		addstyle(Atlas_level,"arrows2");
		addstyle(Atlas_level,"exit2");
		Skin_level = new Skin(Gdx.files.internal("textures/level.json"),Atlas_level);
	}
	
	public static int setpref() {
		prefs = Gdx.app.getPreferences("WireWorld - Evolving Games");
		if (prefs.contains("log"))
			return prefs.getInteger("log");
		else
			return Gdx.app.LOG_INFO;
	}
	
	public static void init() {
		Gdx.app.debug("AssetLoader","Initialisation de la résolution virtuelle...");
		int realWidth=Gdx.graphics.getWidth();
		int realHeight=Gdx.graphics.getHeight();
		float realRatio=realWidth/(float)realHeight;
		Gdx.app.debug("AssetLoader","Résolution de "+realWidth+"x"+realHeight+" ratio de "+String.format("%.2f", realRatio)+".");		
		ratio=1;
		width=1920;
		height=1080;
		if (Math.abs(16f/9f-realRatio)>Math.abs(4f/3f-realRatio)) {
			ratio=4/3;
			Gdx.app.debug("AssetLoader","Ratio 4/3, résolution virtuelle : 1920x1440.");
			height=1440;
		}
		else
			Gdx.app.debug("AssetLoader","Ratio 16/9, résolution virtuelle : 1920x1080.");
		Camera = new OrthographicCamera(width,height);
		Camera.position.set(width/2, height/2, 0);
		Camera.update();
		if (stretch) {
			viewport = new StretchViewport(width,height);
			Gdx.app.debug("AssetLoader","Adaptation d'écran maximale, 'Aspect-Ratio' non conservé.");				
		}
		else {
			viewport = new FitViewport(width,height);
			Gdx.app.debug("AssetLoader","Adaptation d'écran totale, 'Aspect-Ratio' conservé.");					
		}
	    viewport.apply();
	}

	public static void load() {
		Gdx.app.debug("AssetLoader","Chargements des éléments minimalistes");
		Texture_logo = new Texture(Gdx.files.internal("pictures/logo.png"));
		Texture_logo.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		intro = Gdx.audio.newSound(Gdx.files.internal("musics/intro.mp3"));
	}

	public static void dispose() {
		Texture_logo.dispose();
		Texture_fond.dispose();
		Skin_level.dispose();
		Atlas_level.dispose();
		intro.dispose();
	}

}