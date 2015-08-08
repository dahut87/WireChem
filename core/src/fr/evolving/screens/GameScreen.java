package fr.evolving.screens;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import fr.evolving.UI.Menu;
import fr.evolving.worlds.GameRenderer;
import fr.evolving.worlds.GameWorld;
import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.Objectives;
import fr.evolving.UI.TouchMaptiles;
import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;
import fr.evolving.automata.Positiver_I;
import fr.evolving.automata.Positiver_II;
import fr.evolving.automata.Positiver_III;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Transmuter.Angular;
import fr.evolving.inputs.InputHandler;

public class GameScreen implements Screen {
	private InputMultiplexer multiplexer;
	private Array<InputProcessor> processors;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage,stage2;
    private HorizontalGroup table;
    private VerticalGroup table2;
	private GameWorld world;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private ImageButton[] Barre;
	private ImageTextButton cycle,temp,nrj,rayon,cout,tech;
	private ImageTextButton[] Barre2;	
	String[] tocreate={"run","stop","speed","separator","move","zoomp","zoomm","infos","separator","raz","save","levels","tree","exits","separator","screen","sound","tuto","settings","separator","stat"};
	public Actor selected;
	public Transmuter selected_transmuter;
	private ButtonLevel buttonlevel;
	private Objectives objectives;
	private TouchMaptiles map;
	private Menu menu;
	private Actor menuactor;
	private float oldx,oldy;
	private Label fpsLabel;

	
	// This is the constructor, not the class declaration
	public GameScreen(Level alevel) {
		Gdx.app.debug(getClass().getSimpleName(),"Création des Barres verticales & horizontales");
		table = new HorizontalGroup();
		table.bottom().padLeft(5f).padBottom(8f).space(10f);
		table2 = new VerticalGroup();
		table2.setPosition(AssetLoader.width, AssetLoader.height*2/3);
		table2.right();
		table2.space(10f);
		Gdx.app.debug(getClass().getSimpleName(),"Création des elements primordiaux du screen (stage, renderer, table, level, world)");
		fpsLabel=new Label("0 FPS",AssetLoader.Skin_level,"FPS");
		fpsLabel.setPosition(AssetLoader.width-75, AssetLoader.height-220);
		multiplexer = new InputMultiplexer();
		processors = new Array<InputProcessor>();
		stage = new Stage(AssetLoader.viewport);
		stage2 = new Stage(AssetLoader.viewport);
		this.level=alevel;
		oldx=0;
		oldy=0;
		world = new GameWorld(level);
		Renderer = new GameRenderer(this);
		world.setRenderer(Renderer);
		Gdx.app.debug(getClass().getSimpleName(),"Mise en place du timer.");
		ScrollTimer=new Timer();
		ScrollTask = new TimerTask()
		{
			@Override
			public void run() 
			{
				Renderer.evolve();
			}	
		};
		ScrollTimer.scheduleAtFixedRate(ScrollTask, 0, 30);
		Gdx.app.debug(getClass().getSimpleName(),"Création des barres");	
		Barre=new ImageButton[tocreate.length];
		int i=0;
		Gdx.app.debug(getClass().getSimpleName(),"Barre bas:"+Barre.length+" elements");	
		for (String tocreateitem: tocreate) {
			Barre[i]= new ImageButton(AssetLoader.Skin_level,tocreateitem);
			Barre[i++].setName(tocreateitem);
		}
		Barre2=new ImageTextButton[Transmuter.Class.values().length];
		Gdx.app.debug(getClass().getSimpleName(),"Menu:"+Barre2.length+" elements");	
		for (i=0;i<Barre2.length;i++) {
			Barre2[i]= new ImageTextButton(Transmuter.Class.values()[i].toString(),AssetLoader.Skin_level);
			Barre2[i].setName(Transmuter.Class.values()[i].toString());
		}
		Barre[11].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Niveaux");
	        	((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(level.aWorld));
	        }
	     });
		Barre[0].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"tests");
	        	level.Grid.Cells[10][0].Transmuter=new Positiver_I(level);
	        	level.Grid.Cells[10][1].Transmuter=new Positiver_II(level);
	        	level.Grid.Cells[10][2].Transmuter=new Positiver_I(level);
	        	Gdx.app.debug("0 upgrade",String.valueOf(level.Grid.Cells[10][0].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("0 activation",String.valueOf(level.Grid.Cells[10][0].Transmuter.getActivationLevel()));
	        	Gdx.app.debug("2 activation",String.valueOf(level.Grid.Cells[10][2].Transmuter.getActivationLevel()));
	        	level.Grid.Cells[10][0].Transmuter.Activate();
	        	level.Grid.Cells[10][0].Transmuter.UpgradeCycle();
	        	Gdx.app.debug("0 activation",String.valueOf(level.Grid.Cells[10][0].Transmuter.getActivationLevel()));
	        	Gdx.app.debug("2 activation",String.valueOf(level.Grid.Cells[10][2].Transmuter.getActivationLevel()));
	        	level.Grid.Cells[10][0].Transmuter.Activate();
	        	Gdx.app.debug("0 activation",String.valueOf(level.Grid.Cells[10][0].Transmuter.getActivationLevel()));
	        	Gdx.app.debug("2 upgrade",String.valueOf(level.Grid.Cells[10][2].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("0 upgrade",String.valueOf(level.Grid.Cells[10][0].Transmuter.getUpgradeCycle()));
	        	level.Grid.Cells[10][0].Transmuter.UpgradeCycle();	
	        	Gdx.app.debug("2 upgrade",String.valueOf(level.Grid.Cells[10][2].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("0 upgrade",String.valueOf(level.Grid.Cells[10][0].Transmuter.getUpgradeCycle()));
	        	level.Grid.Cells[10][0].Transmuter.UpgradeCycle();	
	        	Gdx.app.debug("2 upgrade",String.valueOf(level.Grid.Cells[10][2].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("0 upgrade",String.valueOf(level.Grid.Cells[10][0].Transmuter.getUpgradeCycle()));
	        	level.Grid.Cells[10][0].Transmuter.UpgradeCycle();	
	        	Gdx.app.debug("2 upgrade",String.valueOf(level.Grid.Cells[10][2].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("0 upgrade",String.valueOf(level.Grid.Cells[10][0].Transmuter.getUpgradeCycle()));
	        	Gdx.app.debug("1 upgrade",String.valueOf(level.Grid.Cells[10][1].Transmuter.getUpgradeCycle()));	
	        	Gdx.app.debug("0 nom",String.valueOf(level.Grid.Cells[10][0].Transmuter.getName()));
	        	Gdx.app.debug("1 nom",String.valueOf(level.Grid.Cells[10][1].Transmuter.getName()));	
	        	Gdx.app.debug("1 taille",String.valueOf(level.Grid.Cells[10][1].Transmuter.getTiles().size()));	
	        	level.Grid.Cells[10][0].Transmuter.UpgradeTemp();
	        	level.Grid.Cells[10][1].Transmuter.UpgradeTemp();
	        	level.Grid.Cells[10][2].Transmuter.UpgradeTemp();
	        	level.Grid.Cells[10][2].Transmuter.UpgradeNrj();
	        	level.Grid.Cells[10][2].Transmuter.setRotation(Angular.A90);
	        	Gdx.app.debug("2 informations",String.valueOf(level.Grid.Cells[10][0].Transmuter.getInformations()));
	        	Gdx.app.debug("2 informations",String.valueOf(level.Grid.Cells[10][1].Transmuter.getInformations()));
	        	Gdx.app.debug("2 informations",String.valueOf(level.Grid.Cells[10][2].Transmuter.getInformations()));	
	        	level.Grid.Cells[5][5].Transmuter=new Positiver_I(level);
	        	level.Grid.Cells[5][5].Transmuter.setRotation(Angular.A90);
	        	level.Grid.Cells[8][8].Transmuter=new Positiver_II(level);
	        	level.Grid.Cells[8][8].Transmuter.setRotation(Angular.A180);
	        	level.Grid.Cells[2][2].Transmuter=new Positiver_I(level);
	        	level.Grid.Cells[2][2].Transmuter.setRotation(Angular.A270);
	        	level.Grid.Cells[7][2].Transmuter=new Positiver_III(level);
	        	level.Grid.Cells[7][2].Transmuter.setRotation(Angular.A270);
	        	Gdx.app.debug("7.2 A270 place main",String.valueOf(level.Grid.Cells[7][2].Transmuter.getPostitionMainTile(205)));
	        	level.Grid.tiling_transmuter();
	        	int[] result;
	        	result=level.Grid.Cells[5][5].Transmuter.getallTiles();
	        	for (int i=0;i<result.length;i++)
	        		Gdx.app.debug("getalltiles 5,5",String.valueOf(result[i]));
	        	result=level.Grid.Cells[8][8].Transmuter.getallTiles();
	        	for (int i=0;i<result.length;i++)	        	
        			Gdx.app.debug("getalltiles 8,8",String.valueOf(result[i]));
	        	Gdx.app.debug("istransmuter 203",AssetLoader.getTransmuter(201).getName());
	        }
	     });
		if (Gdx.graphics.isFullscreen())
			Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("windows"));
		Barre[15].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Ecran");
	        	DisplayMode currentMode = Gdx.graphics.getDesktopDisplayMode();
	        	if  (Gdx.graphics.isFullscreen()) 
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"vers fenetre.");
	        		Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, false);
	        		Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("screen"));
	        	}
	        	else
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"vers plein ecran.");
	        		Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, true);
	        		Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("windows"));
	        	}
	        }
	     });
		if (AssetLoader.intro.getVolume()==0)
			Barre[16].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("nosound"));
		Barre[16].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().getName(),"Barre:Son");
	        	if  (AssetLoader.intro.getVolume()>0) 
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"arret son.");
	        		AssetLoader.intro.setVolume(0f);
	        		Barre[16].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("nosound"));
	        	}
	        	else
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"marche son.");
	        		AssetLoader.intro.setVolume(1f);
	        		Barre[16].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("sound"));
	        	}
	        }
	     });
		Barre[13].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Quitter");
	    		Gdx.app.exit();
	        }
	     });
		for (i=4;i<8;i++) {
			Barre[i].addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		        	selected=event.getListenerActor();
					map.fillempty(53);
		        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Selection dans la Barre bas");
		        	if (this.getTapCount()>=2 && selected.getName()=="move") {
		        		map.initzoom();
		        	}
		        }
		     });
		}
		Barre2[0].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(0,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        	menu.setMenuTile(0, 7, 71);
	        	menu.setMenuTile(1, 7, 72);
	        	menu.setMenuTile(2, 7, 73);
	        	menu.setMenuTile(1, 5, 70);
	        	menu.setMenuTile(0, 6, 74);
	        	menu.setMenuTile(1, 6, 75);
	        	menu.setMenuTile(2, 6, 76);
	        	menu.setMenuTile(0, 5, 77);
	        	menu.setMenuTile(2, 5, 78);
	        	menu.setMenuTile(3, 3, 79);
	        	Barre2[0].setChecked(true);
	        }
	     });
		Barre2[1].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(1,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        	menu.setMenuTransmuter(0,7,"Positiveur I",Angular.A00);
	        	menu.setMenuTransmuter(0,6,"Positiveur II",Angular.A00);
	        	menu.setMenuTransmuter(0,5,"Positiveur III",Angular.A00);
	        }
	     });
		Barre2[2].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(2,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Barre2[3].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(3,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Barre2[4].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(4,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Barre2[5].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(5,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Barre2[6].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(6,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Barre2[7].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	checkMenu(7,true);
	        	menu.clear();
				map.fillempty(53);
	        	selected=null;
	        }
	     });
		Gdx.app.debug(getClass().getSimpleName(),"Création de la barre de gestion du haut");	
		cycle=new ImageTextButton(String.valueOf(level.Cycle),AssetLoader.Skin_level,"cycle2");
		cycle.setPosition(10,AssetLoader.height-74);
		temp=new ImageTextButton(String.valueOf(level.Temp),AssetLoader.Skin_level,"temp2");
		temp.setPosition(210,AssetLoader.height-74);
		nrj=new ImageTextButton(String.valueOf(level.Nrj),AssetLoader.Skin_level,"nrj2");
		nrj.setPosition(410,AssetLoader.height-74);
		rayon=new ImageTextButton(String.valueOf(level.Rayon),AssetLoader.Skin_level,"rayon2");
		rayon.setPosition(610,AssetLoader.height-74);
		tech=new ImageTextButton(String.valueOf(level.Tech),AssetLoader.Skin_level,"tech2");
		tech.setPosition(1345,AssetLoader.height-74);
		cout=new ImageTextButton(String.valueOf(level.Cout),AssetLoader.Skin_level,"cout2");
		cout.setPosition(1445,AssetLoader.height-74);
		objectives=new Objectives();
		objectives.setVictory(level.Victory);
		objectives.setPosition(890,AssetLoader.height-95);
		buttonlevel=new ButtonLevel(level,true);
		buttonlevel.setPosition(1760,AssetLoader.height-125);
		Gdx.app.debug(getClass().getSimpleName(),"Création d'une tilemap");
		map=new TouchMaptiles(level,128,128);
		map.setBounds(0, 0, AssetLoader.width, AssetLoader.height);
		map.addListener(new ClickListener(){
			@Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				oldx=0;
				oldy=0;
				if (selected!=null)
					Gdx.app.debug(event.getListenerActor().toString(),"Cliquage sur la map, mode:"+selected.getName());
				if (selected==null)
					;
				else if (selected.getName()=="cleaner") 
				{
					for(x=0;x<level.Grid.sizeX;x++)
						for(y=0;y<level.Grid.sizeY;y++) {
							level.Grid.GetXY(x, y).Copper=false;
							level.Grid.GetXY(x, y).Fiber=0;
							level.Grid.GetXY(x, y).Transmuter=null;
						}
					level.Grid.tiling_copper();
					level.Grid.tiling_transmuter();
					map.redraw(60);
					return false;
				}
				else if (selected.getName()=="infos") 
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						if (level.Grid.GetXY(coords.x,coords.y).Copper)
							Gdx.app.debug(getClass().getSimpleName(),"*** Présence de cuivre");
						if (level.Grid.GetXY(coords.x,coords.y).Fiber>0)
							Gdx.app.debug(getClass().getSimpleName(),"*** Présence de fibre");
						if (level.Grid.GetXY(coords.x,coords.y).Transmuter_calc>0) {
							Vector2 gotomain=AssetLoader.resolveTransmuterMain(level.Grid.GetXY(coords.x,coords.y).Transmuter_calc);
							if (gotomain!=null) {
								Gdx.app.debug(event.getListenerActor().toString(),"transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+(coords.x+gotomain.x)+"x"+(coords.y+gotomain.y));
								Gdx.app.debug(event.getListenerActor().toString(),level.Grid.getTransmuter(coords.x+gotomain.x,coords.y+gotomain.y).getInformations());
							}
						}
					}
					return false;
				}
				else if (selected.getName()=="zoomp") 
				{
					map.setZoom(0.9f);
					map.setDec((AssetLoader.width/2-x)/2,(AssetLoader.height/2-y)/2);
					return false;
				}
				else if (selected.getName()=="zoomm") 
				{
					map.setZoom(1.1f);
					map.setDec((AssetLoader.width/2-x)/2,(AssetLoader.height/2-y)/2);
					return false;
				}
				else if (selected.getName()=="copper-pen")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						if (level.Grid.getCopper(coords.x,coords.y)==false)
							level.Grid.GetXY(coords.x,coords.y).Copper=true;
						else
							level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling_copper();
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="fiber-pen")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						if (level.Grid.GetFiber(coords.x,coords.y)==false)
							level.Grid.GetXY(coords.x,coords.y).Fiber=1;
						else
							level.Grid.GetXY(coords.x,coords.y).Fiber=0;
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="blank")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Fiber=0;
						level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling_copper();
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="transmuter")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Transmuter=selected_transmuter;
						level.Grid.tiling_transmuter();
						map.redraw(60);
					}	
				}
				return true;
			 }
		   });
		map.addListener(new ClickListener(){
			@Override
		    public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if (selected!=null)
					Gdx.app.debug(event.getListenerActor().toString(),"Drag sur la map, mode:"+selected.getName());
				if (selected==null)
					;
				else if (selected.getName()=="move") {					
					if (oldx!=0 && oldy!=0) {
						map.setDec(x-oldx,y-oldy);
						Gdx.app.debug(event.getListenerActor().toString(),"Decalage absolue en pixel:"+(x-oldx)+"x"+(y-oldy));
					}
					oldx=x;
					oldy=y;
					}
				else if (selected.getName()=="copper-brush")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Copper=true;
						level.Grid.tiling_copper();
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="fiber-brush")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Fiber=1;
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="copper-eraser")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling_copper();
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="fiber-eraser")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Fiber=0;
						map.redraw(60);
					}	
				}
				else if (selected.getName()=="transmuter-eraser")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						if (level.Grid.GetXY(coords.x,coords.y).Transmuter_calc!=0)
						{
							Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
							Vector2 gotomain=AssetLoader.resolveTransmuterMain(level.Grid.GetXY(coords.x,coords.y).Transmuter_calc);
							if (gotomain!=null) {
								level.Grid.GetXY(coords.x+gotomain.x,coords.y+gotomain.y).Transmuter=null;
								Gdx.app.debug(event.getListenerActor().toString(),"transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+(coords.x+gotomain.x)+"x"+(coords.y+gotomain.y));
							}
							level.Grid.tiling_transmuter();
							map.redraw(53);
						}
					}
				}
				else if (selected.getName()=="all-eraser")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						if (level.Grid.GetXY(coords.x,coords.y).Transmuter_calc!=0)
						{
							Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
							Vector2 gotomain=AssetLoader.resolveTransmuterMain(level.Grid.GetXY(coords.x,coords.y).Transmuter_calc);
							if (gotomain!=null) {
								level.Grid.GetXY(coords.x+gotomain.x,coords.y+gotomain.y).Transmuter=null;
								Gdx.app.debug(event.getListenerActor().toString(),"transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+(coords.x+gotomain.x)+"x"+(coords.y+gotomain.y));
							}
							level.Grid.tiling_transmuter();
						}
						level.Grid.GetXY(coords.x,coords.y).Fiber=0;
						level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling_copper();
						map.redraw(60);
					}
				}
				else if (selected.getName()=="blank")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						level.Grid.GetXY(coords.x,coords.y).Fiber=0;
						level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling_copper();
						map.redraw(60);
					}	
				}
			}
		});
		menu=new Menu(4,8);
		map.addListener(new ClickListener(){
			@Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Vector2 coords=menu.screentoworld(x,y);	
				int tile=menu.getMenuTile((int)coords.x,(int)coords.y);
				Gdx.app.debug(event.getListenerActor().toString(),"Coordonnées:"+x+"x"+y+" Coordonnées deprojettée:"+coords.x+"x"+coords.y+" tile:"+tile);
				if (menuactor==null)
					menuactor=new Actor();
				if (tile!=54 && tile!=0) {
					Vector2 coords2=menu.worldtoscreen((int)coords.x,(int)coords.y);
					menuactor.setBounds(coords2.x, coords2.y, 60, 60);
					selected=menuactor;
					map.fillempty(60);
				if (tile==71)
					selected.setName("copper-pen");
				if (tile==72)
					selected.setName("copper-brush");
				if (tile==73)
					selected.setName("copper-eraser");
				if (tile==74)
					selected.setName("fiber-pen");
				if (tile==75)
					selected.setName("fiber-brush");
				if (tile==76)
					selected.setName("fiber-eraser");
				if (tile==77)
					selected.setName("transmuter-eraser");
				if (tile==78)
					selected.setName("all-eraser");
				if (tile==79)
					selected.setName("cleaner");
				else if (tile==70)
					selected.setName("blank");
				else if (tile>99) {
					Transmuter transmuter=AssetLoader.getTransmuter(tile);
					if (transmuter!=null) {
						Vector2 gotomain=transmuter.getPostitionMainTile(tile);
						if (gotomain!=null) {
							coords2=menu.worldtoscreen((int)(coords.x+gotomain.x),(int)(coords.y+gotomain.y));
							menuactor.setPosition(coords2.x, coords2.y);
							selected.setName("transmuter");
							selected_transmuter=transmuter;
							Gdx.app.debug(event.getListenerActor().toString(),"transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+coords2.x+"x"+coords2.y);
						}
					}
				}
				}
				return false;
			}
		 });
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		fpsLabel.setText(Gdx.graphics.getFramesPerSecond()+"FPS");
		Renderer.render(delta, runTime,0);
		stage2.draw();
		Renderer.render(delta, runTime,1);
		stage.draw();
		Renderer.render(delta, runTime,2);
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width,height);
	}

	@Override
	public void show() {
		Gdx.app.log("*****","Affichage du niveau.");
		for (int i=0;i<Barre2.length;i++) 
			table2.addActor(Barre2[i]);
		for (int i=0;i<Barre.length;i++) 
			table.addActor(Barre[i]);
		stage2.addActor(map);
		stage.addActor(objectives);
		stage.addActor(buttonlevel);
		stage.addActor(rayon);
		stage.addActor(nrj);
		stage.addActor(fpsLabel);
		stage.addActor(temp);
		stage.addActor(cycle);
		stage.addActor(table2);
		stage.addActor(table);
		stage.addActor(tech);
		stage.addActor(cout);
		stage.addActor(menu);
	    processors.add(stage);
	    processors.add(stage2);
	    multiplexer.setProcessors(processors);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public void checkMenu(int menu,boolean check)
	{
		for (int i=0;i<Barre2.length;i++) 
			Barre2[i].setChecked(false);
		Barre2[menu].setChecked(true);
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
