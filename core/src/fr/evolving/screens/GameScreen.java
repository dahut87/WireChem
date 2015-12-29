package fr.evolving.screens;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
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
import fr.evolving.automata.Transmuter.CaseType;
import fr.evolving.inputs.InputHandler;

public class GameScreen implements Screen {
	private InputMultiplexer multiplexer;
	private Array<InputProcessor> processors;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage,stage_menu,stage_info;
    private HorizontalGroup table;
    private VerticalGroup table2;
	private GameWorld world;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private ImageButton[] Barre;
	private ImageTextButton cycle,temp,nrj,rayon,cout,tech,info_cout,info_tech,info_research,info_activation,info_up_nrj,info_up_temp,info_up_rayon,info_up_cycle,info_tree;
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
	private Label fpsLabel,info_nom;
	private TextArea info_desc,info_up_nrj_val,info_up_temp_val,info_up_rayon_val,info_up_cycle_val;
	public boolean unroll,mapexit;
	public enum calling{mouseover,mouseclick,mousedrag,mousex2click,mousescroll};
	
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
		stage_menu = new Stage(AssetLoader.viewport);
		stage_info = new Stage(AssetLoader.viewport);
		this.level=alevel;
		oldx=0;
		oldy=0;
		unroll=false;
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
		        	if (this.getTapCount()>=2 && selected.getName()=="move") 
		        		map.initzoom();
		        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
	        	hideInfo();
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
		Gdx.app.debug(getClass().getSimpleName(),"Création de la barre d'information");
		info_tech=new ImageTextButton("0",AssetLoader.Skin_level,"info_tech");
		info_tech.setSize(48, 48);
		info_tech.setPosition(1200, AssetLoader.height-775);
		info_cout=new ImageTextButton("0",AssetLoader.Skin_level,"info_cout");
		info_cout.setSize(48, 48);
		info_cout.setPosition(1280, AssetLoader.height-775);
		info_research=new ImageTextButton("0",AssetLoader.Skin_level,"info_research");
		info_research.setSize(48, 48);
		info_research.setPosition(1360, AssetLoader.height-775);
		info_activation=new ImageTextButton("0",AssetLoader.Skin_level,"info_activation");
		info_activation.setSize(48, 48);
		info_activation.setPosition(1440, AssetLoader.height-775);
		info_up_cycle=new ImageTextButton("0",AssetLoader.Skin_level,"info_cycle");
		info_up_cycle.setSize(32, 32);
		info_up_cycle.setPosition(1855, AssetLoader.height-810);
		info_up_temp=new ImageTextButton("0",AssetLoader.Skin_level,"info_temp");
		info_up_temp.setSize(32, 32);
		info_up_temp.setPosition(1855, AssetLoader.height-860);
		info_up_rayon=new ImageTextButton("0",AssetLoader.Skin_level,"info_rayon");
		info_up_rayon.setSize(32, 32);
		info_up_rayon.setPosition(1855, AssetLoader.height-910);
		info_up_nrj=new ImageTextButton("0",AssetLoader.Skin_level,"info_nrj");
		info_up_nrj.setSize(32, 32);
		info_up_nrj.setPosition(1855, AssetLoader.height-960);
		info_tree=new ImageTextButton("Arbre...",AssetLoader.Skin_level,"info_tree");
		info_tree.setSize(48, 48);
		info_tree.setPosition(1795, AssetLoader.height-760);
		info_tree.getStyle().font.setScale(0.7f);
		info_nom= new Label("Unknow",AssetLoader.Skin_level,"info_nom");
		info_nom.setPosition(1230, AssetLoader.height-710);
		info_desc=new TextArea("Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description", AssetLoader.Skin_level, "info_desc") ;
		info_desc.setBounds(1220, AssetLoader.height-965, 550, 150);
		info_up_cycle_val=new TextArea("U: 0.1\nT: 0.5", AssetLoader.Skin_level, "info_up") ;
		info_up_cycle_val.setBounds(1800, AssetLoader.height-840, 120, 50);
		info_up_temp_val=new TextArea("U: 0.1\nT: 0.5", AssetLoader.Skin_level, "info_up") ;
		info_up_temp_val.setBounds(1800, AssetLoader.height-875, 120, 50);
		info_up_rayon_val=new TextArea("U: 0.1\nT: 0.5", AssetLoader.Skin_level, "info_up") ;
		info_up_rayon_val.setBounds(1800, AssetLoader.height-925, 120, 50);
		info_up_nrj_val=new TextArea("U: 0.1\nT: 0.5", AssetLoader.Skin_level, "info_up") ;
		info_up_nrj_val.setBounds(1800, AssetLoader.height-975, 120, 50);
		Gdx.app.debug(getClass().getSimpleName(),"Création d'une tilemap");
		map=new TouchMaptiles(level,128,128);
		map.setBounds(0, 0, AssetLoader.width, AssetLoader.height);
		map.addListener(new ClickListener(){
			@Override
			public boolean mouseMoved(InputEvent event,float x,float y) {
				String[] exec={"transmuter"};
				return event_coordination(x,y,0,calling.mouseover,exec);
			}
			@Override
		    public boolean scrolled(InputEvent event,float x,float y,int amount) {
				String[] exec={"transmuter"};
				Gdx.app.debug(event.getListenerActor().toString(),"Coordonnées:");
				return event_coordination(x,y,amount,calling.mousescroll,exec);
			}
			@Override	
			 public void clicked(InputEvent event, float x, float y) {
				oldx=0;
				oldy=0;
				String[] exec={"cleaner","infos","zoomp","zoomm","copper_pen","fiber_pen","copper_eraser","fiber_eraser","transmuter_eraser","all_eraser","blank","transmuter","copper_brush","fiber_brush"};
				if (this.getTapCount()>=2)
					event_coordination(x,y,0,calling.mousex2click,exec);
				else
					event_coordination(x,y,0,calling.mouseclick,exec);
				}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				String[] exec={"transmuter","move","copper_brush","fiber_brush","copper_eraser","fiber_eraser","transmuter_eraser","all_eraser","blank"};
				event_coordination(x,y,0,calling.mousedrag,exec);
			}
		   });
		menu=new Menu(4,8);
		map.addListener(new ClickListener(){
			@Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mapexit=true;
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
					selected.setName("copper_pen");
				if (tile==72)
					selected.setName("copper_brush");
				if (tile==73)
					selected.setName("copper_eraser");
				if (tile==74)
					selected.setName("fiber_pen");
				if (tile==75)
					selected.setName("fiber_brush");
				if (tile==76)
					selected.setName("fiber_eraser");
				if (tile==77)
					selected.setName("transmuter_eraser");
				if (tile==78)
					selected.setName("all_eraser");
				if (tile==79)
					selected.setName("cleaner");
				else if (tile==70)
					selected.setName("blank");
				else if (tile>99) {
					Transmuter transmuter=AssetLoader.getTransmuter(tile);
					if (transmuter!=null) {
						showInfo(transmuter);
						Vector2 gotomain=transmuter.getPostitionMainTile(tile);
						if (gotomain!=null) {
							coords2=menu.worldtoscreen((int)(coords.x+gotomain.x),(int)(coords.y+gotomain.y));
							menuactor.setPosition(coords2.x, coords2.y);
							selected.setName("transmuter");
							selected_transmuter=(Transmuter) transmuter.clone();
							Gdx.app.debug(event.getListenerActor().toString(),"transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+coords2.x+"x"+coords2.y);
						}
					}
				}
				}
				return false;
			}
		 });
	}
	
	boolean event_coordination(float x,float y,int button, calling call, String[] exec) {
		if (selected!=null) {
			if (Arrays.asList(exec).contains(selected.getName())) {
				Vector2 coords=map.screentoworld(x, y);
				if (level.Grid.GetXY(coords.x,coords.y)!=null)
				{
					mapexit=false;
					Gdx.app.debug("evenement","mode:"+call+" outil:"+selected.getName()+ " X: " + coords.x + " Y: " + coords.y);
					Method method;
					try {
						Class<?> base = Class.forName("fr.evolving.screens.GameScreen");
						Class<?>[] params={float.class, float.class,int.class, int.class, boolean.class, int.class, calling.class};
						method = base.getDeclaredMethod("map_"+selected.getName(), params);
						method.invoke(this,(float)x,(float)y,(int)coords.x,(int)coords.y,true,(int)button,(calling)call);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					if (mapexit==false) {
						mapexit=true;
						map.tempclear();
					}
				}
			}
		
		}
		return true;
	}

	void map_transmuter(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (call==calling.mousescroll || call==calling.mousex2click)
		{
			Angular angle=selected_transmuter.getRotation();
			if (angle==Angular.A00)
				selected_transmuter.setRotation(Angular.A90);
			else if (angle==Angular.A90)
				selected_transmuter.setRotation(Angular.A180);
			else if (angle==Angular.A180)
				selected_transmuter.setRotation(Angular.A270);
			else if (angle==Angular.A270)
				selected_transmuter.setRotation(Angular.A00);
		}
		map.tempclear();
		boolean positionisgood=true;
		HashMap<Vector2,CaseType> tiles=selected_transmuter.getTiles();
		Iterator<Vector2> keySetIterator = selected_transmuter.getTiles().keySet().iterator();
		int MainTile=selected_transmuter.getMainTile();
		int color=63;
		if (level.Grid.getCopper(x,y) && level.Grid.getTransmutercalc(x,y)==0)
			color=0;
		else
			positionisgood=false;
		map.tempdraw(x, y, MainTile, selected_transmuter.getRotation().ordinal(),color);
		while(keySetIterator.hasNext()){
			Vector2 key = keySetIterator.next();
			color=63;
			if (((!level.Grid.GetFiber(x+key.x, y+key.y) &&  !level.Grid.getCopper(x+key.x, y+key.y) && tiles.get(key)==CaseType.Rien) || (level.Grid.GetFiber(x+key.x, y+key.y) &&  level.Grid.getCopper(x+key.x, y+key.y) && tiles.get(key)==CaseType.Tout) || (level.Grid.GetFiber(x+key.x, y+key.y) && !level.Grid.getCopper(x+key.x, y+key.y) && tiles.get(key)==CaseType.Fibre) || (level.Grid.getCopper(x+key.x, y+key.y) && !level.Grid.GetFiber(x+key.x, y+key.y) && tiles.get(key)==CaseType.Cuivre)) && (level.Grid.getTransmutercalc(x+key.x, y+key.y)==0))
				color=0;
			else
				positionisgood=false;
			map.tempdraw(x+key.x, y+key.y, ++MainTile, selected_transmuter.getRotation().ordinal(),color);
		if (call==calling.mouseclick && positionisgood && button!=1)
		{
			level.Grid.GetXY(x,y).Transmuter=(Transmuter) selected_transmuter.clone();
			if (alone) level.Grid.tiling_transmuter();
			map.redraw(53);
		}
		}
	}
	
	
	void map_infos(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (level.Grid.GetXY(x,y)!=null)
		{
        	Gdx.app.debug("map","Etat extension:"+unroll);
			if (level.Grid.GetXY(x,y).Copper)
				Gdx.app.debug("map","*** Présence de cuivre");
			if (level.Grid.GetXY(x,y).Fiber>0)
				Gdx.app.debug("map","*** Présence de fibre");
			if (level.Grid.GetXY(x,y).Transmuter_calc>0) {
				Vector2 gotomain=AssetLoader.resolveTransmuterMain(level.Grid.GetXY(x,y).Transmuter_calc);
				if (gotomain!=null) {
					Gdx.app.debug("map","transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+(x+gotomain.x)+"x"+(y+gotomain.y));
					//Gdx.app.debug("map",level.Grid.getTransmuter(x+gotomain.x,y+gotomain.y).getInformations());
					showInfo(level.Grid.getTransmuter(x+gotomain.x,y+gotomain.y));
				}
			}
		}
		else
        	hideInfo();
	}
	
	void map_zoomp(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		map.setZoom(0.9f);
		map.setDec((AssetLoader.width/2-realx)/2,(AssetLoader.height/2-realy)/2);
	}
	
	void map_zoomm(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		map.setZoom(1.1f);
		map.setDec((AssetLoader.width/2-realx)/2,(AssetLoader.height/2-realy)/2);
	}

	
	void map_move(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (oldx!=0 && oldy!=0) {
			map.setDec(realx-oldx,realy-oldy);
			Gdx.app.debug("map","Decalage absolue en pixel:"+(realx-oldx)+"x"+(realy-oldy));
		}
		oldx=realx;
		oldy=realy;
	}
	
	void map_blank(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		map_fiber_eraser(0,0,x,y,false,button,call);
		map_copper_eraser(0,0,x,y,alone,button,call);
	}
	
	void map_cleaner(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		for(x=0;x<level.Grid.sizeX;x++)
			for(y=0;y<level.Grid.sizeY;y++) {
				level.Grid.GetXY(x, y).Copper=false;
				level.Grid.GetXY(x, y).Fiber=0;
				level.Grid.GetXY(x, y).Transmuter=null;
			}
		level.Grid.tiling_copper();
		level.Grid.tiling_transmuter();
		map.redraw(60);
	}
	
	void map_all_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		map_fiber_eraser(0,0,x,y,false,button,call);
		map_transmuter_eraser(0,0,x,y,alone,button,call);
		map_copper_eraser(0,0,x,y,alone,button,call);
	}
	
	void map_transmuter_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (level.Grid.GetXY(x,y).Transmuter_calc!=0)
		{
			Vector2 gotomain=AssetLoader.resolveTransmuterMain(level.Grid.GetXY(x,y).Transmuter_calc);
			if (gotomain!=null) {
				level.Grid.GetXY(x+gotomain.x,y+gotomain.y).Transmuter=null;
				Gdx.app.debug("map","transmuter deplacement vers origine:"+String.valueOf(gotomain.x)+","+String.valueOf(gotomain.y)+" coords:"+(x+gotomain.x)+"x"+(y+gotomain.y));
			}
			if (alone) {
				level.Grid.tiling_transmuter();
				map.redraw(53);
			}
		}
	}
	
	void map_fiber_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Fiber=0;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}
	}
	
	void map_fiber_pen(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Fiber=-1*level.Grid.GetXY(x,y).Fiber+1;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}
	}
	
	void map_fiber_brush(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Fiber=1;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}	
	}
	
	void map_copper_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Copper=false;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}
	}
	
	void map_copper_pen(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Copper=!level.Grid.GetXY(x,y).Copper;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}		
	}
	
	void map_copper_brush(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		level.Grid.GetXY(x,y).Copper=true;
		if (alone) {
			level.Grid.tiling_copper();
			map.redraw(60);
		}
	}	

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		fpsLabel.setText(Gdx.graphics.getFramesPerSecond()+"FPS");
		Renderer.render(delta, runTime,0);
		stage_menu.draw();
		Renderer.render(delta, runTime,1);
		stage.draw();
		if (unroll)
			stage_info.draw();
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
		stage_info.addActor(info_tech);
		stage_info.addActor(info_research);
		stage_info.addActor(info_activation);
		stage_info.addActor(info_up_nrj);
		stage_info.addActor(info_up_temp);
		stage_info.addActor(info_up_rayon);
		stage_info.addActor(info_tree);
		stage_info.addActor(info_nom);
		stage_info.addActor(info_cout);
		stage_info.addActor(info_desc);
		stage_info.addActor(info_up_nrj_val);
		stage_info.addActor(info_up_temp_val);
		stage_info.addActor(info_up_rayon_val);		
		stage_info.addActor(info_up_cycle_val);		
		stage_info.addActor(info_up_cycle);		
		stage_menu.addActor(map);
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
	    processors.add(stage_menu);
	    multiplexer.setProcessors(processors);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public void showInfo(Transmuter transmuter) {
		if (transmuter==null)
				return;
		unroll=true;
		info_nom.setText(transmuter.getName());
		info_desc.setText(transmuter.getDesc());
		info_tech.setVisible(transmuter.getTechnology()>0);
		info_tech.setText(String.valueOf(transmuter.getTechnology()));
		info_cout.setVisible(transmuter.getPrice()>0);
		info_cout.setText(String.valueOf(transmuter.getPrice()));
		info_research.setVisible(transmuter.getResearch()>0);	
		info_research.setText(String.valueOf(transmuter.getResearch()));
		info_activation.setVisible(transmuter.isActivable());
		info_activation.setText(String.valueOf(transmuter.getActivationLevel()));
		info_up_cycle.setVisible(transmuter.isUpgradableCycle());		
		info_up_cycle.setText(String.valueOf(transmuter.getUpgradeCycle()));
		info_up_nrj.setVisible(transmuter.isUpgradableNrj());
		info_up_nrj.setText(String.valueOf(transmuter.getUpgradeNrj()));
		info_up_temp.setVisible(transmuter.isUpgradableTemp());
		info_up_temp.setText(String.valueOf(transmuter.getUpgradeTemp()));
		info_up_rayon.setVisible(transmuter.isUpgradableRayon());
		info_up_rayon.setText(String.valueOf(transmuter.getUpgradeRayon()));
		info_up_cycle_val.setVisible(transmuter.isUpgradableCycle());			
		info_up_cycle_val.setText(String.valueOf("A:"+transmuter.getUpgradeCycle()*10));
		info_up_nrj_val.setVisible(transmuter.isUpgradableNrj());
		info_up_nrj_val.setText(String.valueOf("U:"+transmuter.getUsedNrj()+"\nT:"+transmuter.getTurnNrj()));
		info_up_temp_val.setVisible(transmuter.isUpgradableTemp());	
		info_up_temp_val.setText(String.valueOf("U:"+transmuter.getUsedTemp()+"\nT:"+transmuter.getTurnTemp()));
		info_up_rayon_val.setVisible(transmuter.isUpgradableRayon());
		info_up_rayon_val.setText(String.valueOf("U:"+transmuter.getUsedRayon()+"\nT:"+transmuter.getTurnRayon()));
	}
	
	public void hideInfo() {
		unroll=false;
		
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
