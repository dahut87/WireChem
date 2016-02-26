package fr.evolving.screens;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.HorizBarre;
import fr.evolving.UI.IconValue;
import fr.evolving.UI.Menu;
import fr.evolving.UI.Objectives;
import fr.evolving.UI.TouchMaptiles;
import fr.evolving.UI.Translist;
import fr.evolving.UI.VertiBarre;
import fr.evolving.UI.IconValue.Icon;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.Preference;
import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;
import fr.evolving.automata.Neutraliser_II;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Transmuter.Angular;
import fr.evolving.automata.Transmuter.CaseType;
import fr.evolving.automata.Worlds;
import fr.evolving.dialogs.PrefWindow;
import fr.evolving.dialogs.SavingWindow;
import fr.evolving.dialogs.WarningDialog;
import fr.evolving.renderers.GameRenderer;

public class GameScreen implements Screen {
	private InputMultiplexer multiplexer;
	private Array<InputProcessor> processors;

	private Stage stage, stage_info, stage_tooltip;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private PrefWindow winOptions;
	private SavingWindow winSave;
	private ImageButton info_up_nrj, info_up_temp, info_up, info_up_rayon,
			info_up_cycle, info_up_nrjval, info_up_tempval, info_up_rayonval,
			info_up_cycleval, nextpage, previouspage;
	private ImageTextButton info_cout, info_tech, info_research, info_activation;
	private IconValue cycle, temp, nrj, rayon, cout, tech, research;
	String[] tocreate;
	private ButtonLevel buttonlevel;
	private Objectives objectives;
	public TouchMaptiles map;
	private Menu menu;
	private HorizBarre horizbar;
	private VertiBarre vertibar;	
	private float oldx, oldy;
	private Label fpsLabel, info_nom;
	private TextArea info_desc, tooltip;
	public boolean unroll;
	public Worlds worlds;
	public Translist translist;

	public enum calling {
		mouseover, mouseclick, mousedrag, longpress, tap, taptap, zoom, fling, pan, pinch
	};

	public enum quality {
		Bas(AssetLoader.language.get("[quality-gamescreen-low]"), TextureFilter.Nearest), Moyen(AssetLoader.language.get("[quality-gamescreen-medium]"), TextureFilter.MipMap), Eleve(
				AssetLoader.language.get("[quality-gamescreen-high]"), TextureFilter.Linear);
		private final String text;
		private final TextureFilter aquality;

		private quality(final String text, TextureFilter aquality) {
			this.text = text;
			this.aquality = aquality;
		}

		@Override
		public String toString() {
			return text;
		}

		public TextureFilter getQuality() {
			return this.aquality;
		}
	};

	public enum adaptation {
		fit(AssetLoader.language.get("[adaptation-gamescreen-fit]")), fill(AssetLoader.language.get("[adaptation-gamescreen-fill]"));
		private final String text;

		private adaptation(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum resolutions {
		r1024_768("XGA (1024x768) 4:3", 1024, 768), r1280_720(
				"720p (1280x720) 16:9", 1280, 720), r1280_768(
				"WXGA (1280x768) 5:3", 1280, 768), r1280_1024(
				"SXGA (1280x1024) 5:4", 1280, 1024), r1400_1050(
				"SXGA+ (1400x1050) 4:3", 1400, 1050), r1680_1050(
				"WSXGA (1680x1050) 16:10", 1680, 1050), r1600_1200(
				"UXGA (1600x1200) 4:3", 1600, 1200), r1920_1080(
				"1080p (1920x1080) 16:9", 1920, 1080), r1920_1200(
				"WUXGA (1920x1200) 16:10", 1920, 1200), rmax(
				"resolution Native", 0, 0);
		private final String text;
		private int resx, resy;
		boolean full;

		private resolutions(final String text, int resx, int resy) {
			this.text = text;
			this.resx = resx;
			this.resy = resy;
		}

		@Override
		public String toString() {
			if (full)
				return text + " "+AssetLoader.language.get("[resolutions-gamescreen-fullscreen]");
			else
				return text;
		}

		public void SetFull(boolean fullscreen) {
			full = fullscreen;
		}

		public int getResolutionX() {
			return resx;
		}

		public int getResolutionY() {
			return resy;
		}

		public void setResolutionX(int x) {
			resx = x;
		}

		public void setResolutionY(int y) {
			resy = y;
		}
	}

	GestureDetector gesturedetector;

	// This is the constructor, not the class declaration
	public GameScreen(Worlds aworlds) {
		Gdx.app.debug("wirechem-GameScreen","Préparation du screen");
		this.worlds = aworlds;
		this.worlds.prepareLevel(false);
		this.level=worlds.getInformations();
		if (worlds.isDebug())
			tocreate = new String[] { "run", "stop", "speed", "separator", "move#", "zoomp#","zoomm#", "infos#", "separator", "raz", "save", "levels", "tree",	"exits", "separator", "screen", "sound", "tuto", "grid", "settings", "separator", "stat","separator","unlocked","delrow","delcol","addrow","addcol","database" };
		else if (level.Tech<1)
			tocreate = new String[] { "run", "stop", "speed", "separator", "move#", "zoomp#","zoomm#", "separator", "levels", "exits", "separator", "screen", "sound", "settings" };
		else if (level.aWorld<1)
			tocreate = new String[] { "run", "stop", "speed", "separator", "move#", "zoomp#","zoomm#", "infos#", "separator", "raz", "save", "levels", "exits", "separator", "screen", "sound", "grid", "settings" };
		else
			tocreate = new String[] { "run", "stop", "speed", "separator", "move#", "zoomp#","zoomm#", "infos#", "separator", "raz", "save", "levels", "tree",	"exits", "separator", "screen", "sound", "tuto", "grid", "settings", "separator", "stat" };
		Gdx.app.debug("wirechem-GameScreen","Création des Barres verticales & horizontales.");
		horizbar=new HorizBarre(tocreate,"preparebarre");
		horizbar.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.debug("wirechem-GameScreen", "Barre | Element changé");
				hideInfo();
				map.tempclear();
				menu.unSelect();
				map.setSelected(getselected());
			}
		});
		vertibar=new VertiBarre(worlds);
		Gdx.app.debug("wirechem-GameScreen","Création des elements primordiaux du screen (stage, renderer, table, level, world)");
		fpsLabel = new Label("0 FPS", AssetLoader.Skin_level, "FPS");
		fpsLabel.setPosition(AssetLoader.width - 75, AssetLoader.height - 220);
		multiplexer = new InputMultiplexer();
		processors = new Array<InputProcessor>();
		stage = new Stage(AssetLoader.viewport);
		stage_info = new Stage(AssetLoader.viewport);
		stage_tooltip = new Stage(AssetLoader.viewport);
		oldx = 0;
		oldy = 0;
		unroll = false;
		Renderer = new GameRenderer(this);
		Gdx.app.debug("wirechem-GameScreen", "Création des barres");
		tooltip = new TextArea("tooltip:x\r\n tooltip:y",AssetLoader.Skin_level, "info_tooltip");
		tooltip.setBounds(541, 27, 100, 50);
		Gdx.app.debug("wirechem-GameScreen","Création de la barre de gestion du haut");
		cycle = new IconValue(Icon.cycle,worlds, AssetLoader.Skin_level);
		cycle.setPosition(10, AssetLoader.height - 74);
		temp = new IconValue(Icon.temp,worlds, AssetLoader.Skin_level);
		temp.setPosition(210, AssetLoader.height - 74);
		rayon = new IconValue(Icon.rayon,worlds, AssetLoader.Skin_level);
		rayon.setPosition(410, AssetLoader.height - 74);
		nrj = new IconValue(Icon.nrj,worlds, AssetLoader.Skin_level);
		nrj.setPosition(610, AssetLoader.height - 74);
		tech = new IconValue(Icon.tech,worlds, AssetLoader.Skin_level);
		tech.setPosition(1345, AssetLoader.height - 74);
		tech.addListener(new Tooltip(tooltip,AssetLoader.Tooltipmanager));
		cout = new IconValue(Icon.cout,worlds, AssetLoader.Skin_level);
		cout.setPosition(1445, AssetLoader.height - 74);
		research = new IconValue(Icon.research,worlds, AssetLoader.Skin_level);
		research.setPosition(1545, AssetLoader.height - 74);
		objectives = new Objectives(worlds);
		objectives.setPosition(890, AssetLoader.height - 95);
		objectives.setVisible(level.Cout>0);
		buttonlevel = new ButtonLevel(level, 1.0f, false);
		buttonlevel.setPosition(1760, AssetLoader.height - 125);
		buttonlevel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug("wirechem-GameScreen","Remise à zéro du monde");
				worlds.prepareLevel(true);
				prepare();
			}
		});
		Gdx.app.debug("wirechem-GameScreen","Création de la barre d'information");
		info_tech = new ImageTextButton("0", AssetLoader.Skin_level,"info_tech");
		info_tech.setSize(48, 48);
		info_tech.setPosition(1200, AssetLoader.height - 775);
		info_cout = new ImageTextButton("0", AssetLoader.Skin_level,"info_cout");
		info_cout.setSize(48, 48);
		info_cout.setPosition(1280, AssetLoader.height - 775);
		info_research = new ImageTextButton("0", AssetLoader.Skin_level,"info_research");
		info_research.setSize(48, 48);
		info_research.setPosition(1360, AssetLoader.height - 775);
		info_activation = new ImageTextButton("0", AssetLoader.Skin_level,"info_activation");
		info_activation.setSize(48, 48);
		info_activation.setPosition(1440, AssetLoader.height - 775);
		info_up_cycleval = new ImageButton(AssetLoader.Skin_level,"info_cycleval");
		info_up_cycleval.setPosition(1775, AssetLoader.height - 855);
		info_up_tempval = new ImageButton(AssetLoader.Skin_level,"info_tempval");
		info_up_tempval.setPosition(1775, AssetLoader.height - 925);
		info_up_rayonval = new ImageButton(AssetLoader.Skin_level,"info_rayonval");
		info_up_rayonval.setPosition(1850, AssetLoader.height - 855);
		info_up_nrjval = new ImageButton(AssetLoader.Skin_level, "info_nrjval");
		info_up_nrjval.setPosition(1850, AssetLoader.height - 925);
		info_up_cycle = new ImageButton(AssetLoader.Skin_level, "info_cycle");
		info_up_cycle.setSize(32, 32);
		info_up_cycle.setPosition(1790, AssetLoader.height - 839);
		info_up_temp = new ImageButton(AssetLoader.Skin_level, "info_temp");
		info_up_temp.setSize(32, 32);
		info_up_temp.setPosition(1792, AssetLoader.height - 910);
		info_up_rayon = new ImageButton(AssetLoader.Skin_level, "info_rayon");
		info_up_rayon.setSize(32, 32);
		info_up_rayon.setPosition(1866, AssetLoader.height - 839);
		info_up_nrj = new ImageButton(AssetLoader.Skin_level, "info_nrj");
		info_up_nrj.setSize(32, 32);
		info_up_nrj.setPosition(1865, AssetLoader.height - 910);
		info_nom = new Label("Unknow", AssetLoader.Skin_level, "info_nom");
		info_nom.setPosition(1230, AssetLoader.height - 710);
		info_desc = new TextArea("Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description",
				AssetLoader.Skin_level, "info_desc");
		info_desc.setBounds(1220, AssetLoader.height - 965, 560, 150);
		info_up=new ImageButton(AssetLoader.Skin_level,"evolution");
		info_up.setPosition(1450, AssetLoader.height - 720);
		info_up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (menu.getTransmuter()!=null && menu.getTransmuter().isUpgradable(worlds.ReadResearch()))
					worlds.ModResearch(-menu.getTransmuter().getUpgrade().getResearch());
					menu.getTransmuter().Upgrade();
					menu.update();
					info_up.setVisible(false);
					hideInfo();
			}
		});
		Gdx.app.debug("wirechem-GameScreen", "Création d'une tilemap");
		map = new TouchMaptiles(worlds,level, 128, 128);
		if (Preference.prefs.getBoolean("Grid"))
			map.setClearsprite(60);
		else
			map.setClearsprite(53);
		map.redraw();
		
		Gdx.app.debug("wirechem-GameScreen", "Création du menu");
		nextpage=new ImageButton(AssetLoader.Skin_level,"extend");
		nextpage.setPosition(1850, AssetLoader.height - 370);
		nextpage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!nextpage.isDisabled()) {
				menu.NextPage();
				Gdx.app.debug("wirechem-GameScreen", "Menu | Page suivante:"+menu.getPage());
				map.tempclear();
				hideInfo();
				nextpage.setDisabled(menu.isNextEmpty());
				previouspage.setDisabled(menu.isPreviousEmpty());
				}
			}
		});
		previouspage=new ImageButton(AssetLoader.Skin_level,"extend2");
		previouspage.setPosition(1800, AssetLoader.height - 370);
		previouspage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!previouspage.isDisabled()) {
				menu.PreviousPage();
				Gdx.app.debug("wirechem-GameScreen", "Menu | Page précédente:"+menu.getPage());
				map.tempclear();
				hideInfo();
				nextpage.setDisabled(menu.isNextEmpty());
				previouspage.setDisabled(menu.isPreviousEmpty());
				}
			}
		});
		menu = new Menu(worlds);
		menu.setBounds(1531f, AssetLoader.height-780, 264, 480);
		menu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.debug("wirechem-GameScreen", "Menu | Element changé");
				hideInfo();
				map.tempclear();
				if (menu.getTransmuter() != null) 
					showInfo(menu.getTransmuter());
				else
					hideInfo();
				horizbar.unSelect();
				map.setSelected(getselected());
			}
		});
		prepare();
	}
	
	public String getselected() {
		if (menu.getSelection()==null)
			if (horizbar.getSelection()==null)
					return null;
				else
					return horizbar.getSelection();
		else
			return menu.getSelection();
	}


	public void map_transmuter(float realx, float realy, int x, int y, boolean alone,int button, calling call) {
		if (!worlds.isDebug() && level.Cout<menu.getTransmuter().getPrice())
			return;
		if (call == calling.taptap && button == 0
				|| (call == calling.mouseclick && button == 1)) {
			Angular angle = menu.getTransmuter().getRotation();
			if (angle == Angular.A00)
				 menu.getTransmuter().setRotation(Angular.A90);
			else if (angle == Angular.A90)
				 menu.getTransmuter().setRotation(Angular.A180);
			else if (angle == Angular.A180)
				 menu.getTransmuter().setRotation(Angular.A270);
			else if (angle == Angular.A270)
				 menu.getTransmuter().setRotation(Angular.A00);
		}
		map.tempclear();
		boolean positionisgood = true;
		int color = 0;
		OrderedMap<Vector2, Integer> tiles =  menu.getTransmuter()
				.getTilesidrotated();
		Entries<Vector2, Integer> iterator = tiles.iterator();
		while (iterator.hasNext()) {
			Entry<Vector2, Integer> all = iterator.next();
			color = 63;
			int index = tiles.keys().toArray().indexOf(all.key, false);
			if ((( menu.getTransmuter().getTilestype(index) == CaseType.Nimporte)
					|| (!level.Grid.getFiber(x + all.key.x, y + all.key.y)
							&& !level.Grid.getCopper(x + all.key.x, y
									+ all.key.y) &&  menu.getTransmuter()
							.getTilestype(index) == CaseType.Rien)
					|| (level.Grid.getFiber(x + all.key.x, y + all.key.y)
							&& level.Grid.getCopper(x + all.key.x, y
									+ all.key.y) &&  menu.getTransmuter()
							.getTilestype(index) == CaseType.Tout)
					|| (level.Grid.getCopper(x + all.key.x, y + all.key.y) &&  menu.getTransmuter()
							.getTilestype(index) == CaseType.Cuivre)
					|| (level.Grid.getFiber(x + all.key.x, y + all.key.y) &&  menu.getTransmuter()
							.getTilestype(index) == CaseType.Fibre)
					|| (level.Grid.getFiber(x + all.key.x, y + all.key.y)
							&& !level.Grid.getCopper(x + all.key.x, y
									+ all.key.y) &&  menu.getTransmuter()
							.getTilestype(index) == CaseType.Fibre_seul) || (level.Grid
					.getCopper(x + all.key.x, y + all.key.y)
					&& !level.Grid.getFiber(x + all.key.x, y + all.key.y) &&  menu.getTransmuter()
					.getTilestype(index) == CaseType.Cuivre_seul))
					&& (level.Grid.getTransmutercalc(x + all.key.x, y
							+ all.key.y) == 0))
				color = 0;
			else
				positionisgood = false;
			map.tempdraw(x + all.key.x, y + all.key.y, all.value,
					 menu.getTransmuter().getRotation().ordinal(), color);
		}
		if ((call == calling.longpress && button == 0) && positionisgood) {
			level.Grid.GetXY(x, y).Transmuter = (Transmuter)  menu.getTransmuter()
					.clone();
			if (alone)
				level.Cout_transmuter=level.Grid.tiling_transmuter();
			map.redraw();
			Gdx.input.vibrate(new long[] { 0, 200, 200, 200 }, -1);
		}
	}

	public void map_infos(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		if (level.Grid.GetXY(x, y) != null) {
			Gdx.app.debug("wirechem-GameScreen", "Etat extension:" + unroll);
			if (level.Grid.GetXY(x, y).Copper)
				Gdx.app.debug("wirechem-GameScreen", "*** Présence de cuivre");
			if (level.Grid.GetXY(x, y).Fiber > 0)
				Gdx.app.debug("wirechem-GameScreen", "*** Présence de fibre");
			if (level.Grid.GetXY(x, y).Transmuter_calc > 0) {
				Gdx.app.debug("wirechem-GameScreen", "transmuter deplacement vers origine:"
						+ level.Grid.GetXY(x, y).Transmuter_movex + ","
						+ level.Grid.GetXY(x, y).Transmuter_movey + " coords:"
						+ (x + level.Grid.GetXY(x, y).Transmuter_movex) + "x"
						+ (y + level.Grid.GetXY(x, y).Transmuter_movey));
				Gdx.app.debug("wirechem-GameScreen",level.Grid.getTransmuter(
								x + level.Grid.GetXY(x, y).Transmuter_movex,
								y + level.Grid.GetXY(x, y).Transmuter_movey)
								.getInformations());
				showInfo(level.Grid.getTransmuter(x
						+ level.Grid.GetXY(x, y).Transmuter_movex, y
						+ level.Grid.GetXY(x, y).Transmuter_movey));
				map.tempclear();
				map.tempdraw(x, y, 1069, 0, 0);
			}
		}

	}

	public void map_zoomp(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		map.setZoom(0.9f);
		map.setDec((AssetLoader.width / 2 - realx) / 2,
				(AssetLoader.height / 2 - realy) / 2);
	}

	public void map_zoomm(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		map.setZoom(1.1f);
		map.setDec((AssetLoader.width / 2 - realx) / 2,
				(AssetLoader.height / 2 - realy) / 2);
	}

	public void map_move(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		if (oldx != 0 && oldy != 0) {
			map.setDec(realx - oldx, realy - oldy);
			Gdx.app.debug("wirechem-GameScreen", "Decalage absolue en pixel:" + (realx - oldx)+ "x" + (realy - oldy));
		}
		oldx = realx;
		oldy = realy;
	}

	public void map_blank(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		map_fiber_eraser(0, 0, x, y, false, button, call);
		map_copper_eraser(0, 0, x, y, alone, button, call);
	}

	public void map_cleaner(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		for (x = 0; x < level.Grid.sizeX; x++)
			for (y = 0; y < level.Grid.sizeY; y++)
				if (!level.Grid.GetXY(x, y).Locked)
					map_transmuter_eraser(0, 0, x, y, false, button, call);
		level.Cout_transmuter=level.Grid.tiling_transmuter();
		for (x = 0; x < level.Grid.sizeX; x++)
			for (y = 0; y < level.Grid.sizeY; y++)
				if (!level.Grid.GetXY(x, y).Locked)
			{
				map_fiber_eraser(0, 0, x, y, false, button, call);
				map_copper_eraser(0, 0, x, y, false, button, call);
			}
		level.Cout_copperfiber=level.Grid.tiling_copper();
		map.redraw();
	}

	public void map_all_eraser(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		map_transmuter_eraser(0, 0, x, y, alone, button, call);
		map_fiber_eraser(0, 0, x, y, false, button, call);
		map_copper_eraser(0, 0, x, y, alone, button, call);
	}

	public void map_transmuter_eraser(float realx, float realy, int x, int y,
			boolean alone, int button, calling call) {
		if (level.Grid.GetXY(x, y).Transmuter_calc != 0 && !level.Grid.GetXY(x, y).Locked) {
			level.Grid.GetXY(x + level.Grid.GetXY(x, y).Transmuter_movex, y
					+ level.Grid.GetXY(x, y).Transmuter_movey).Transmuter = null;
			Gdx.app.debug("wirechem-GameScreen", "transmuter deplacement vers origine:"
					+ level.Grid.GetXY(x, y).Transmuter_movex + ","
					+ level.Grid.GetXY(x, y).Transmuter_movey + " coords:"
					+ (x + level.Grid.GetXY(x, y).Transmuter_movex) + "x"
					+ (y + level.Grid.GetXY(x, y).Transmuter_movey));
		}
		if (alone) {
			level.Cout_transmuter=level.Grid.tiling_transmuter();
			map.redraw();
		}
	}

	public void map_fiber_eraser(float realx, float realy, int x, int y,
			boolean alone, int button, calling call) {
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked) {
			level.Grid.GetXY(x, y).Fiber = 0;
			if (alone) {
				level.Cout_copperfiber=level.Grid.tiling_copper();
				map.redraw();
			}
		}
	}

	public void map_fiber_pen(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		if (!worlds.isDebug() && level.Cout<5)
			return;
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked)
			level.Grid.GetXY(x, y).Fiber = -1 * level.Grid.GetXY(x, y).Fiber
					+ 1;
		if (alone) {
			level.Cout_copperfiber=level.Grid.tiling_copper();
			map.redraw();
		}
	}

	public void map_fiber_brush(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		if (!worlds.isDebug() && level.Cout<5)
			return;
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked)
			level.Grid.GetXY(x, y).Fiber = 1;
		if (alone) {
			level.Cout_copperfiber=level.Grid.tiling_copper();
			map.redraw();
		}
	}

	public void map_copper_eraser(float realx, float realy, int x, int y,
			boolean alone, int button, calling call) {
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked) {
			level.Grid.GetXY(x, y).Copper = false;
			if (alone) {
				level.Cout_copperfiber=level.Grid.tiling_copper();
				map.redraw();
			}
		}
	}

	public void map_copper_pen(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		if (!worlds.isDebug() && level.Cout<1)
			return;
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked)
			level.Grid.GetXY(x, y).Copper = !level.Grid.GetXY(x, y).Copper;
		if (alone) {
			level.Cout_copperfiber=level.Grid.tiling_copper();
			map.redraw();
		}
	}

	public void map_gold_pen(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		level.Grid.GetXY(x, y).Free=!level.Grid.GetXY(x, y).Free;
		level.Cout_copperfiber=level.Grid.tiling_copper();
		level.Cout_transmuter=level.Grid.tiling_transmuter();
		map.redraw();
	}
	
	public void map_lock_pen(float realx, float realy, int x, int y, boolean alone,
			int button, calling call) {
		level.Grid.GetXY(x, y).Locked=!level.Grid.GetXY(x, y).Locked;
		map.redraw();
	}
	
	public void map_copper_brush(float realx, float realy, int x, int y,boolean alone, int button, calling call) {
		if (!worlds.isDebug() && level.Cout<1)
			return;
		if (level.Grid.GetXY(x, y).Transmuter_calc == 0 && !level.Grid.GetXY(x, y).Locked)
			level.Grid.GetXY(x, y).Copper = true;
		if (alone) {
			level.Cout_copperfiber=level.Grid.tiling_copper();
			map.redraw();
		}
	}

	public void exit()
	{
		worlds.SaveLastGrid();
		worlds.SaveTransmuters();
		worlds.SaveResearch();
	}
	
	public void prepare() {
		level.Cout_copperfiber=level.Grid.tiling_copper();
		level.Cout_transmuter=level.Grid.tiling_transmuter();
		map.redraw();
		map.tempclear();
		hideInfo();
	}
	
	@Override
	public void render(float delta) {
		stage.act();
		runTime += delta;
		if (Preference.prefs.getBoolean("Refresh"))
			fpsLabel.setText(Gdx.graphics.getFramesPerSecond() + "FPS");
		Renderer.render(delta, runTime, 0);
		Renderer.render(delta, runTime, 1);
		stage.draw();
		if (unroll)
			stage_info.draw();
		Renderer.render(delta, runTime, 2);
		stage_tooltip.draw();
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width, height);
	}

	@Override
	public void show() {
		Gdx.app.debug("wirechem-GameScreen","Création de la fenêtre d'option");
		winOptions = new PrefWindow();
		stage.addActor(winOptions);
		winSave = new SavingWindow(worlds);
		winSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				prepare();
			}
		});
		stage.addActor(winSave);
		Gdx.app.log("wirechem-GameScreen", "***** Affichage du niveau.");
		stage_info.addActor(info_tech);
		stage_info.addActor(info_research);
		stage_info.addActor(info_activation);
		stage_info.addActor(info_up_nrj);
		stage_info.addActor(info_up_temp);
		stage_info.addActor(info_up_rayon);
		stage_info.addActor(info_up_cycle);
		stage_info.addActor(info_up_nrjval);
		stage_info.addActor(info_up_tempval);
		stage_info.addActor(info_up_rayonval);
		stage_info.addActor(info_up_cycleval);
		stage_info.addActor(info_nom);
		stage_info.addActor(info_cout);
		stage_info.addActor(info_up);
		stage_info.addActor(info_desc);
		//stage_tooltip.addActor(tooltip);
		stage.addActor(horizbar);
		if (worlds.getInformations().Cout>0 || worlds.getInformations().Tech>=1 || worlds.isDebug()) {
			stage.addActor(vertibar);
			stage.addActor(buttonlevel);
			stage.addActor(menu);
			stage.addActor(nextpage);
			stage.addActor(previouspage);	
		}
		stage.addActor(objectives);
		stage.addActor(rayon);
		stage.addActor(nrj);
		if (Preference.prefs.getBoolean("Refresh"))
			stage.addActor(fpsLabel);
		stage.addActor(temp);
		stage.addActor(cycle);
		stage.addActor(tech);
		stage.addActor(cout);
		stage.addActor(research);
		gesturedetector=new GestureDetector(map);
		processors.add(stage_tooltip);
		processors.add(stage_info);
		processors.add(stage);
		processors.add(map);
		processors.add(gesturedetector);
		multiplexer.setProcessors(processors);
		Gdx.input.setInputProcessor(multiplexer);
		preparemenu(0);
	}

	public void preparebarre(String caller, int count) {
		map.tempclear();
		menu.EraseSurtile();
		hideInfo();
		if (caller == "run") {
		} else if (caller == "stop") {
		} else if (caller == "speed") {
		} else if (caller == "move") {
			if (count >= 2)
				map.initzoom();
		} else if (caller == "zoomp") {
			if (count >= 2)
				map.initzoom();
		} else if (caller == "zoomm") {
			if (count >= 2)
				map.initzoom();
		} else if (caller == "infos") {
			if (count >= 2)
				map.initzoom();
		} else if (caller == "raz") {
			winOptions.setVisible(false);
			winSave.setVisible(!winSave.isVisible());
			if (winSave.isVisible())
				winSave.refresh();
		} else if (caller == "save") {
			Gdx.app.debug("wirechem-GameScreen", "Barre | Sauvegarde de la grille.");	
			worlds.SaveGrid();
			winSave.refresh();
		} else if (caller == "levels") {
			Gdx.app.debug("wirechem-GameScreen", "Barre | Affichage des niveaux.");
			exit();
			((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen(worlds));
		} else if (caller == "tree") {
		} else if (caller == "exits") {
			exit();
			Gdx.app.exit();
		} else if (caller == "screen") {
			DisplayMode currentMode = Gdx.graphics.getDesktopDisplayMode();
			if (Gdx.graphics.isFullscreen()) {
				Gdx.app.debug("wirechem-GameScreen", "Barre | vers fenetre.");
				Gdx.graphics.setDisplayMode(currentMode.width,
						currentMode.height, false);
			} else {
				Gdx.app.debug("wirechem-GameScreen", "Barre | vers plein ecran.");
				Gdx.graphics.setDisplayMode(currentMode.width,
						currentMode.height, true);
			}
		} else if (caller == "sound") {
			if (AssetLoader.intro.getVolume() > 0) {
				Gdx.app.debug("wirechem-GameScreen", "Barre | arret son.");
				AssetLoader.intro.setVolume(0f);
			} else {
				Gdx.app.debug("wirechem-GameScreen", "Barre | marche son.");
				AssetLoader.intro.setVolume(1f);
			}
		} else if (caller == "tuto") {
			if (AssetLoader.Tooltipmanager.enabled) {
				Gdx.app.debug("wirechem-GameScreen", "Barre | arret tuto.");
				AssetLoader.Tooltipmanager.enabled = false;
			} else {
				Gdx.app.debug("wirechem-GameScreen", "Barre | marche tuto.");
				AssetLoader.Tooltipmanager.enabled = true;
			}
		} else if (caller=="grid") {
			if (map.getClearsprite()==53)
			{
				map.fillempty(60);
				map.setClearsprite(60);
			}
				else
				{
				map.fillempty(53);
				map.setClearsprite(53);
				}
		} else if (caller == "settings") {
			winOptions.setVisible(!winOptions.isVisible());
			winSave.setVisible(false);
			if (winOptions.isVisible())
				winOptions.refresh();
		} else if (caller == "flag") {
			if (AssetLoader.language.getLocale().getDisplayName()
					.contains("français")) {
				Gdx.app.debug("wirechem-GameScreen", "Barre | Langue USA");
				AssetLoader.language = AssetLoader.usa;
			} else {
				Gdx.app.debug("wirechem-GameScreen", "Barre | Langue FR");
				AssetLoader.language = AssetLoader.french;
			}
		} else if (caller == "stat") {
		} else if (caller == "unlocked") {
			level.Locked=false;
			buttonlevel.setDisabled(false);
			worlds.unLockLevel();
		} else if (caller == "database") {
			worlds.origLevel();
		} else if (caller == "delrow") {
			level.Grid=(Grid) level.Grid.clone(level.Grid.sizeX-1, level.Grid.sizeY);
			map.resize();
		} else if (caller == "delcol") {
			level.Grid=(Grid) level.Grid.clone(level.Grid.sizeX, level.Grid.sizeY-1);
			map.resize();
		} else if (caller == "addrow") {
			level.Grid=(Grid) level.Grid.clone(level.Grid.sizeX+1, level.Grid.sizeY);
			map.resize();
		} else if (caller == "addcol") {
			level.Grid=(Grid) level.Grid.clone(level.Grid.sizeX, level.Grid.sizeY+1);
			map.resize();
		}
	}

	public void preparemenu(int menuitem) {
		map.tempclear();
		horizbar.unSelect();
		menu.setPageType(0,menuitem);		
		nextpage.setDisabled(menu.isNextEmpty());
		previouspage.setDisabled(true);
		hideInfo();
	}

	public void showInfo(Transmuter transmuter) {
		if (transmuter == null)
			return;
		unroll = true;
		info_nom.setText(transmuter.getName());
		info_desc.setText(transmuter.getDesc());
		info_tech.setVisible(transmuter.getTechnology() > 0);
		info_tech.setText(String.valueOf(transmuter.getTechnology()));
		info_cout.setVisible(transmuter.getPrice() > 0);
		info_cout.setText(String.valueOf(transmuter.getPrice()));
		info_research.setVisible(transmuter.getResearch() > 0);
		info_research.setText(String.valueOf(transmuter.getResearch()));
		info_activation.setVisible(transmuter.isActivable());
		info_activation.setText(String.valueOf(transmuter.getMaxActivationLevel()));
		info_up_cycle.setVisible(transmuter.isUpgradableCycle());
		info_up_nrj.setVisible(transmuter.isUpgradableNrj());
		info_up_temp.setVisible(transmuter.isUpgradableTemp());
		info_up_rayon.setVisible(transmuter.isUpgradableRayon());
		info_up_cycleval.setVisible(transmuter.isUpgradableCycle());
		info_up_nrjval.setVisible(transmuter.isUpgradableNrj());
		info_up_tempval.setVisible(transmuter.isUpgradableTemp());
		info_up_rayonval.setVisible(transmuter.isUpgradableRayon());
		info_up_cycleval.getStyle().up = new TextureRegionDrawable(
				AssetLoader.Atlas_level.findRegion("jauge"
						+ transmuter.getUpgradeCycle()));
		info_up_cycleval.setColor(AssetLoader.Levelcolors[1]);
		info_up_nrjval.getStyle().up = new TextureRegionDrawable(
				AssetLoader.Atlas_level.findRegion("jauge"
						+ transmuter.getUpgradeNrj()));
		info_up_nrjval.setColor(AssetLoader.Levelcolors[4]);
		info_up_tempval.getStyle().up = new TextureRegionDrawable(
				AssetLoader.Atlas_level.findRegion("jauge"
						+ transmuter.getUpgradeTemp()));
		info_up_tempval.setColor(AssetLoader.Levelcolors[2]);
		info_up_rayonval.getStyle().up = new TextureRegionDrawable(
				AssetLoader.Atlas_level.findRegion("jauge"
						+ transmuter.getUpgradeRayon()));
		info_up_rayonval.setColor(AssetLoader.Levelcolors[2]);
		info_up.setVisible(transmuter.isUpgradable(worlds.ReadResearch()));
		
	}

	public void hideInfo() {
		unroll = false;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	
}
