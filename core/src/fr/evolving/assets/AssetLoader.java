package fr.evolving.assets;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.Application;

import fr.evolving.automata.Filter1;
import fr.evolving.automata.Filter2;
import fr.evolving.automata.Filter4;
import fr.evolving.automata.Filter4Activable;
import fr.evolving.automata.Filter8Activable;
import fr.evolving.automata.FilterActivable;
import fr.evolving.automata.FilterBig;
import fr.evolving.automata.FilterNegative;
import fr.evolving.automata.FilterPositive;
import fr.evolving.automata.Insufler100;
import fr.evolving.automata.Insufler33;
import fr.evolving.automata.Insufler50;
import fr.evolving.automata.Inverter_I;
import fr.evolving.automata.Inverter_II;
import fr.evolving.automata.Negativer;
import fr.evolving.automata.Negativer_I;
import fr.evolving.automata.Negativer_II;
import fr.evolving.automata.Negativer_III;
import fr.evolving.automata.Neutraliser_I;
import fr.evolving.automata.Neutraliser_II;
import fr.evolving.automata.Oneway;
import fr.evolving.automata.Positiver;
import fr.evolving.automata.Positiver_I;
import fr.evolving.automata.Positiver_II;
import fr.evolving.automata.Positiver_III;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.distributor;
import fr.evolving.database.Base.datatype;
import fr.evolving.database.DatabaseManager;
import fr.evolving.database.LocalBase;
import fr.evolving.database.SqlBase;
import fr.evolving.screens.GameScreen;

public class AssetLoader {
	public static Skin Skin_level, Skin_ui;
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
	public static ScalingViewport viewport;
	public static OrthographicCamera Camera;
	private static Texture emptyT;
	private static Texture fullT;
	public static NinePatch empty;
	public static NinePatch full;
	public static AssetManager manager;
	public static TiledMapTileSet tileSet;
	public static Array<Transmuter> allTransmuter;
	public static TooltipManager Tooltipmanager;
	public static I18NBundle french, usa, language;
	public static TextureFilter quality;
	public static DatabaseManager Datahandler;

	public static void loadall() {
		TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
		params.minFilter = quality;
		params.magFilter = quality;
		params.genMipMaps = (quality == TextureFilter.MipMap);
		Gdx.app.debug("wirechem-AssetLoader", "Initialisation du asset manager");
		manager = new AssetManager();
		Gdx.app.debug("wirechem-AssetLoader", "Initialisation du chargement des éléments multimédia");
		manager.load("textures/level.pack", TextureAtlas.class);
		manager.load("textures/ui.pack", TextureAtlas.class);
		manager.load("pictures/fond.png", Texture.class, params);
		manager.load("pictures/fond2.png", Texture.class, params);
		manager.load("musics/intro.mp3", Music.class);
		manager.load("textures/level.json", Skin.class,
				new SkinLoader.SkinParameter("textures/level.pack"));
		manager.load("textures/ui.json", Skin.class,
				new SkinLoader.SkinParameter("textures/ui.pack"));
	}

	public static void finishall() {
		Gdx.app.debug("wirechem-AssetLoader", "Attente fin chargement...");
		manager.finishLoading();
		Gdx.app.debug("wirechem-AssetLoader", "Affectation des éléments multimédia");
		Atlas_level = manager.get("textures/level.pack");
		intro = manager.get("musics/intro.mp3");
		Texture_fond = manager.get("pictures/fond.png");
		Texture_fond.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Texture_fond.setFilter(quality, quality);
		Texture_fond2 = manager.get("pictures/fond2.png");
		Texture_fond2.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Texture_fond2.setFilter(quality, quality);
		Skin_level = manager.get("textures/level.json");
		Skin_ui = manager.get("textures/ui.json");
		Gdx.app.debug("wirechem-AssetLoader", "Definition des constantes");
		Levelcolors = new Color[5];
		Levelcolors = new Color[] { AssetLoader.Skin_level.getColor("world0"),
				AssetLoader.Skin_level.getColor("world1"),
				AssetLoader.Skin_level.getColor("world2"),
				AssetLoader.Skin_level.getColor("world3"),
				AssetLoader.Skin_level.getColor("world4") };
		Typecolors = new Color[13];
		Typecolors = new Color[] { new Color(0, 0, 1f, 1),
				new Color(0, 0.6f, 0, 1), new Color(0.196f, 0.803f, 0.196f, 1),
				new Color(0.5f, 0.5f, 0.5f, 1), new Color(0.8f, 0.8f, 0.8f, 1),
				new Color(0.6f, 0, 0, 1), new Color(1f, 0, 0, 1),
				new Color(0, 0, 0.6f, 1), new Color(0, 0, 0.6f, 1),
				new Color(0, 0, 0.6f, 1), new Color(0, 0, 0.6f, 1),
				new Color(0.294f, 0.466f, 0.615f, 1),
				new Color(0.478f, 0.192f, 0.098f, 1) };
		Typenames = new String[13];
		Typenames = new String[] { "E-", "e-", "Ph", "e0", "E0", "e+", "E+", "K", "L", "M", "N", "n", "p" };
		Gdx.app.debug("wirechem-AssetLoader", "Création des tiles...");
		tileSet = new TiledMapTileSet();
		Array<TextureAtlas.AtlasRegion> allregions = Atlas_level.getRegions();
		Gdx.app.debug("wirechem-AssetLoader", allregions.size + " régions disponibles");
		for (int i = 0; i < allregions.size; i++) {
			allregions.get(i).getTexture().setFilter(quality, quality);
			if (allregions.get(i).name.startsWith("sprite")) {
				if (allregions.get(i).name.contains("#")) {
					String[] name = allregions.get(i).name.split("_");
					String[] type = name[0].split("-");
					if (name[0].contains("sprite")) {
						int id = Integer.parseInt(name[1].split("#")[0]);
						if (tileSet.getTile(1000 + id) == null) {
							Gdx.app.debug("wirechem-AssetLoader", "Animated Tiles N°:"
									+ String.valueOf(id + 1000));
							int maxid = 0;
							for (int j = 1; Atlas_level.findRegion("sprite-"
									+ type[1] + "_" + id + "#" + j) != null; j++)
								maxid = j;
							Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(
									maxid);
							for (int j = 1; j <= maxid; j++)
								frameTiles
										.add(new StaticTiledMapTile(
												(Atlas_level
														.findRegion("sprite-"
																+ type[1] + "_"
																+ id + "#" + j))));
							AnimatedTiledMapTile atile = new AnimatedTiledMapTile(
									0.15f, frameTiles);
							Gdx.app.debug("wirechem-AssetLoader","Taille:" + String.valueOf(frameTiles.size));
							atile.setId(1000 + id);
							atile.getProperties().put("type", type[1]);
							tileSet.putTile(1000 + id, atile);

						}
					}
				} else {
					String[] type = allregions.get(i).name.split("-");
					StaticTiledMapTile atile = new StaticTiledMapTile(
							allregions.get(i));
					atile.setId(allregions.get(i).index);
					atile.getProperties().put("type", type[1]);
					tileSet.putTile(allregions.get(i).index, atile);
					Gdx.app.debug("wirechem-AssetLoader","Type:" + type[1] + " Tiles N°:" + String.valueOf(allregions.get(i).index));
				}
			}
		}
	
		Gdx.app.debug("wirechem-AssetLoader", "Ajout des transmuters");	
		allTransmuter = new Array<Transmuter>();
		allTransmuter.add(new Positiver(null));
		allTransmuter.add(new Positiver_I(null));
		allTransmuter.add(new Positiver_II(null));
		allTransmuter.add(new Positiver_III(null));
		allTransmuter.add(new Negativer(null));
		allTransmuter.add(new Negativer_I(null));
		allTransmuter.add(new Negativer_II(null));
		allTransmuter.add(new Negativer_III(null));
		allTransmuter.add(new Inverter_I(null));
		allTransmuter.add(new Inverter_II(null));
		allTransmuter.add(new Neutraliser_I(null));
		allTransmuter.add(new Neutraliser_II(null));
		allTransmuter.add(new Oneway(null));
		allTransmuter.add(new distributor(null));
		allTransmuter.add(new Insufler100(null));
		allTransmuter.add(new Insufler33(null));
		allTransmuter.add(new Insufler50(null));
		allTransmuter.add(new FilterPositive(null));	
		allTransmuter.add(new FilterNegative(null));	
		allTransmuter.add(new FilterBig(null));	
		allTransmuter.add(new FilterActivable(null));
		allTransmuter.add(new Filter1(null));	
		allTransmuter.add(new Filter2(null));	
		allTransmuter.add(new Filter4(null));	
		allTransmuter.add(new Filter4Activable(null));
		allTransmuter.add(new Filter8Activable(null));	
		for (Transmuter transmuter : allTransmuter) {
			Values<Integer> allTiles = transmuter.getTilesid().iterator();
			while (allTiles.hasNext()) {
				Integer atile = allTiles.next();
				Gdx.app.debug("wirechem-AssetLoader","Ajustement données Tiles N°:" + String.valueOf(atile)	+ " Nom:" + transmuter.getName());
				AssetLoader.tileSet.getTile(atile).getProperties().put("transmuter", transmuter);
				AssetLoader.tileSet.getTile(atile).getProperties().put("name", transmuter.getName());
			}
		}
		Gdx.app.debug("wirechem-AssetLoader", "Ajout de la gestion des tooltips");
		Tooltipmanager = new TooltipManager();
		Gdx.app.debug("wirechem-AssetLoader", "Mise en place de la base de donnée");
		Datahandler = new DatabaseManager();
		Datahandler.RegisterBackend(LocalBase.class);
		Datahandler.RegisterBackend(SqlBase.class);
		Databasemanagerfrompref();
	}

	public static void Databasemanagerfrompref() {
		Datahandler.CloseAll();
		if (Datahandler.Attach(datatype.userdata,
				Preference.prefs.getString("userdata")))
			Gdx.app.debug("wirechem-AssetLoader", "Base user ok");
		else
			Gdx.app.debug("wirechem-AssetLoader", "Base user erreur");
		if (Datahandler.Attach(datatype.statdata,
				Preference.prefs.getString("statdata")))
			Gdx.app.debug("wirechem-AssetLoader", "Base stat ok");
		else
			Gdx.app.debug("wirechem-AssetLoader", "Base stat erreur");
		if (Datahandler.Attach(datatype.gamedata,
				Preference.prefs.getString("gamedata")))
			Gdx.app.debug("wirechem-AssetLoader", "Base jeu ok");
		else
			Gdx.app.debug("wirechem-AssetLoader", "Base jeu erreur");
	}

	public static Transmuter getTransmuter(String Name) {
		for (Transmuter transmuter : allTransmuter) {
			if (transmuter.getID()==Name)
				return transmuter;
		}
		return null;
	}

	public static void init() {
		Gdx.app.debug("wirechem-AssetLoader",
				"Initialisation de la résolution virtuelle...");
		int realWidth = Gdx.graphics.getWidth();
		int realHeight = Gdx.graphics.getHeight();
		float realRatio = realWidth / (float) realHeight;
		Gdx.app.debug("wirechem-AssetLoader", "Résolution de " + realWidth + "x"
				+ realHeight + " ratio de " + String.format("%.2f", realRatio)
				+ ".");
		ratio = 1;
		width = 1920;
		height = 1080;
		if (Math.abs(16f / 9f - realRatio) > Math.abs(4f / 3f - realRatio)) {
			ratio = 1.44f;
			Gdx.app.debug("wirechem-AssetLoader",
					"Ratio 4/3, résolution virtuelle : 1920x1440.");
			height = 1440;
		} else
			Gdx.app.debug("wirechem-AssetLoader",
					"Ratio 16/9, résolution virtuelle : 1920x1080.");
		Camera = new OrthographicCamera(width, height);
		Camera.position.set(width / 2, height / 2, 0);
		Camera.update();
		if (Preference.prefs.getInteger("Adaptation") == 1) {
			viewport = new StretchViewport(width, height);
			Gdx.app.debug("wirechem-AssetLoader",
					"Adaptation d'écran maximale, 'Aspect-Ratio' non conservé.");
		} else {
			viewport = new FitViewport(width, height);
			Gdx.app.debug("wirechem-AssetLoader",
					"Adaptation d'écran totale, 'Aspect-Ratio' conservé.");
		}
		viewport.apply();
	}

	public static void load() {
		Gdx.app.debug("wirechem-AssetLoader", "Ajout de la gestion des locales");
		FileHandle baseFileHandle = Gdx.files.internal("i18n/messages/messages");
		usa = I18NBundle.createBundle(baseFileHandle, new Locale("en"));
		french = I18NBundle.createBundle(baseFileHandle, new Locale("fr"));
		if (Preference.prefs.getBoolean("Language"))
			language = french;
		else
			language = usa;
		I18NBundle.setExceptionOnMissingKey(true);
		
		Gdx.app.debug("wirechem-AssetLoader", "Réglage de la qualité des textures");
		quality = GameScreen.quality.values()[Preference.prefs.getInteger("Quality")].getQuality();
		Gdx.app.debug("wirechem-AssetLoader", "Chargements des éléments minimalistes");
		Texture_logo = new Texture(Gdx.files.internal("pictures/logo.png"),
				quality == TextureFilter.MipMap);
		Texture_logo.setFilter(quality, quality);
		emptyT = new Texture(Gdx.files.internal("pictures/empty.png"),
				quality == TextureFilter.MipMap);
		emptyT.setFilter(quality, quality);
		fullT = new Texture(Gdx.files.internal("pictures/full.png"),
				quality == TextureFilter.MipMap);
		fullT.setFilter(quality, quality);
		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);
	}

	public static void dispose() {
		Texture_logo.dispose();
		Texture_fond.dispose();
		Skin_level.dispose();
		Atlas_level.dispose();
		intro.dispose();
	}

}