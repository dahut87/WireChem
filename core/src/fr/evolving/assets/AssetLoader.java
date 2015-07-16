package fr.evolving.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class AssetLoader {
	public static Skin Skin_level;
	public static TextureAtlas Atlas_level;
	public static Texture Texture_fond;
	public static Texture Texture_fond2;
	public static Texture Texture_logo;
	public static Music intro;
	public static int width;
	public static Color[] Levelcolors;
	public static Color[] Typecolors;
	public static String[] Typenames;
	public static int height;
	public static float ratio;
	public static boolean stretch=false;
	public static Preferences prefs;
	public static ScalingViewport viewport;
	public static OrthographicCamera Camera;
	private static Texture emptyT;
	private static Texture fullT;
	public static NinePatch empty;
	public static NinePatch full;
	public static AssetManager manager;
	public static TiledMapTileSet tileSet;
	
	public static void addstyle(TextureAtlas Atlas_level,String Name) {
		AtlasRegion AnAtlasRegion = Atlas_level.findRegion(Name);
		if (AnAtlasRegion==null) return;
		TextureData ATextureData = AnAtlasRegion.getTexture().getTextureData();
		ATextureData.prepare();
		Pixmap Pixmap_Ori=ATextureData.consumePixmap();
		Pixmap Pixmap_Over=new Pixmap(AnAtlasRegion.getRegionWidth(), AnAtlasRegion.getRegionHeight(), Pixmap.Format.RGBA8888);
		Pixmap Pixmap_Disable=new Pixmap(AnAtlasRegion.getRegionWidth(), AnAtlasRegion.getRegionHeight(), Pixmap.Format.RGBA8888);
		Pixmap Pixmap_Down=new Pixmap(AnAtlasRegion.getRegionWidth(), AnAtlasRegion.getRegionHeight(), Pixmap.Format.RGBA8888);
		Color acolor;
		for(int x=0; x < AnAtlasRegion.getRegionWidth(); x++)
		{
			for(int y =0; y < AnAtlasRegion.getRegionHeight(); y++)
			{
				acolor= new Color(Pixmap_Ori.getPixel(AnAtlasRegion.getRegionX()+x, AnAtlasRegion.getRegionY()+y));
				Pixmap_Down.drawPixel(x, y,Color.rgba8888(acolor.a,0f,0f,acolor.a));
				Pixmap_Disable.drawPixel(x, y, Color.rgba8888(acolor.r*0.352f,acolor.g*0.352f,acolor.b*0.352f,acolor.a));
				Pixmap_Over.drawPixel(x, y, Color.rgba8888(acolor.r,acolor.g*0.2f,acolor.b*0.2f,acolor.a));
			}
		}
		Atlas_level.addRegion(Name+"_disabled", new TextureRegion(new Texture(Pixmap_Disable)));
		Atlas_level.addRegion(Name+"_over", new TextureRegion(new Texture(Pixmap_Over)));
		Atlas_level.addRegion(Name+"_down", new TextureRegion(new Texture(Pixmap_Down)));
	}
	
	public static void loadall() {
		Gdx.app.debug("AssetLoader","Initialisation du asset manager");
		manager = new AssetManager();
		Gdx.app.debug("AssetLoader","Initialisation du chargement des éléments multimédia");
		manager.load("textures/level.pack", TextureAtlas.class);
		manager.load("pictures/fond.png", Texture.class);
		manager.load("pictures/fond2.png", Texture.class);
		manager.load("musics/intro.mp3", Music.class);
	}
	
	public static void finishall() {	
		Gdx.app.debug("AssetLoader","Ajout des textures disabled,over et down");
		Atlas_level = manager.get("textures/level.pack");
		if (manager.isLoaded("textures/level.pack")) {
		for(String toload: new String[]{"leveler0","leveler1","leveler2","leveler3","leveler4","arrows","arrows2","exit2","cout","tech","cycle","temp","nrj","rayon","logo2"}) 
			addstyle(Atlas_level,toload);
		}
		Gdx.app.debug("AssetLoader","Chargement des skins et attente fin chargement");
	    manager.load("textures/level.json", Skin.class, new SkinLoader.SkinParameter("textures/level.pack"));
	    manager.finishLoading();
		Gdx.app.debug("AssetLoader","Affectation des éléments multimédia");
		intro = manager.get("musics/intro.mp3");
		Texture_fond = manager.get("pictures/fond.png");
		Texture_fond.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Texture_fond2 = manager.get("pictures/fond2.png");
		Texture_fond2.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	    Skin_level = manager.get("textures/level.json");
	    Skin_level.addRegions(Atlas_level);
	    Gdx.app.debug("AssetLoader","Definition des constantes");
		Levelcolors=new Color[5];
		Levelcolors=new Color[]{AssetLoader.Skin_level.getColor("world1"),AssetLoader.Skin_level.getColor("world2"),AssetLoader.Skin_level.getColor("world3"),AssetLoader.Skin_level.getColor("world4"),AssetLoader.Skin_level.getColor("world5")};
		Typecolors=new Color[13];
		Typecolors=new Color[]{new Color(0,0,1f,1),new Color(0,0.6f,0,1),new Color(0.196f,0.803f,0.196f,1),new Color(0.5f,0.5f,0.5f,1),new Color(0.8f,0.8f,0.8f,1),new Color(0.6f,0,0,1),new Color(1f,0,0,1),new Color(0,0,0.6f,1),new Color(0,0,0.6f,1),new Color(0,0,0.6f,1),new Color(0,0,0.6f,1),new Color(0.294f,0.466f,0.615f,1),new Color(0.478f,0.192f,0.098f,1)};
		Typenames=new String[13];
		Typenames=new String[]{"E-","e-","Ph","e0","E0","e+","E+","K","L","M","N","n","p"};
	    Gdx.app.debug("AssetLoader","Création des tiles...");		
        tileSet = new TiledMapTileSet();
        tileSet.setName("copper");
        for (int i = 0; i < 70; i++) {   
           TextureRegion tileText = Atlas_level.findRegion("sprite"+i);
           if (tileText != null) {
        	  StaticTiledMapTile atile= new StaticTiledMapTile(tileText);
        	  atile.setId(i);
              tileSet.putTile(i, atile);
              Gdx.app.debug("AssetLoader","Tiles N°:"+String.valueOf(i));
           }
        }
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
			ratio=1.44f;
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
		emptyT=new Texture(Gdx.files.internal("pictures/empty.png"));
		fullT=new Texture(Gdx.files.internal("pictures/full.png"));
		empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);
	}

	public static void dispose() {
		Texture_logo.dispose();
		Texture_fond.dispose();
		Skin_level.dispose();
		Atlas_level.dispose();
		intro.dispose();
	}

}