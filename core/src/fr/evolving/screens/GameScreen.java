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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

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
	private Stage stage,stage_menu,stage_info,stage_tooltip;
	private HorizontalGroup table;
	private VerticalGroup table2;
	private GameWorld world;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private ImageButton[] Barre;
	private ImageButton info_up_nrj,info_up_temp,info_up_rayon,info_up_cycle,info_up_nrjval,info_up_tempval,info_up_rayonval,info_up_cycleval;
	private ImageTextButton cycle,temp,nrj,rayon,cout,tech,research,info_cout,info_tech,info_research,info_activation;
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
	private TextArea info_desc,tooltip;
	public boolean unroll,mapexit;
	public enum calling{mouseover,mouseclick,mousedrag,longpress,tap,taptap,zoom,fling,pan,pinch};
	GestureDetector gesturedetector;

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
		stage_tooltip = new Stage(AssetLoader.viewport);
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
		tooltip=new TextArea("tooltip:x\r\n tooltip:y", AssetLoader.Skin_level, "info_tooltip") ;
		tooltip.setBounds(541, 27, 100, 50);
		Barre=new ImageButton[tocreate.length];
		int i=0;
		Gdx.app.debug(getClass().getSimpleName(),"Barre bas:"+Barre.length+" elements");	
		for (String tocreateitem: tocreate) {
			Barre[i]= new ImageButton(AssetLoader.Skin_level,tocreateitem);
			Barre[i].setName(tocreateitem);
			Barre[i].addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Actor caller=event.getListenerActor();
					Gdx.app.debug("Barre","Selection dans la Barre bas:"+caller.getName());
					preparebarre(caller, this.getTapCount());
				}
			});
			Barre[i++].addListener(new Tooltip(tooltip, AssetLoader.Tooltipmanager));
		}
		Barre2=new ImageTextButton[Transmuter.Class.values().length];
		Gdx.app.debug(getClass().getSimpleName(),"Menu:"+Barre2.length+" elements");	
		for (i=0;i<Barre2.length;i++) {
			Barre2[i]= new ImageTextButton(AssetLoader.language.get(Transmuter.Class.values()[i].toString()),AssetLoader.Skin_level);
			Barre2[i].setName(String.valueOf(i));
			Barre2[i].addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					int caller=Integer.parseInt(event.getListenerActor().getName());
					Gdx.app.debug("Barre2","Selection dans la Barre droite:"+caller);
					preparemenu(caller);
				}
			});
		}
		Barre[15].setChecked(Gdx.graphics.isFullscreen());
		Barre[16].setChecked(AssetLoader.intro.getVolume()>0);
		Barre[17].setChecked(AssetLoader.Tooltipmanager.enabled==true);
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
		research=new ImageTextButton(String.valueOf(0),AssetLoader.Skin_level,"research2");
		research.setPosition(1545,AssetLoader.height-74);		
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
		info_up_cycleval=new ImageButton(AssetLoader.Skin_level,"info_cycleval");
		info_up_cycleval.setPosition(1819, AssetLoader.height-765);
		info_up_tempval=new ImageButton(AssetLoader.Skin_level,"info_tempval");
		info_up_tempval.setPosition(1819, AssetLoader.height-832);
		info_up_rayonval=new ImageButton(AssetLoader.Skin_level,"info_rayonval");
		info_up_rayonval.setPosition(1819, AssetLoader.height-900);
		info_up_nrjval=new ImageButton(AssetLoader.Skin_level,"info_nrjval");
		info_up_nrjval.setPosition(1819, AssetLoader.height-967);
		info_up_cycle=new ImageButton(AssetLoader.Skin_level,"info_cycle");
		info_up_cycle.setSize(32, 32);
		info_up_cycle.setPosition(1835, AssetLoader.height-747);
		info_up_temp=new ImageButton(AssetLoader.Skin_level,"info_temp");
		info_up_temp.setSize(32, 32);
		info_up_temp.setPosition(1837, AssetLoader.height-816);
		info_up_rayon=new ImageButton(AssetLoader.Skin_level,"info_rayon");
		info_up_rayon.setSize(32, 32);
		info_up_rayon.setPosition(1835, AssetLoader.height-884);
		info_up_nrj=new ImageButton(AssetLoader.Skin_level,"info_nrj");
		info_up_nrj.setSize(32, 32);
		info_up_nrj.setPosition(1835, AssetLoader.height-950);
		info_nom= new Label("Unknow",AssetLoader.Skin_level,"info_nom");
		info_nom.setPosition(1230, AssetLoader.height-710);
		info_desc=new TextArea("Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description", AssetLoader.Skin_level, "info_desc") ;
		info_desc.setBounds(1220, AssetLoader.height-965, 575, 150);
		Gdx.app.debug(getClass().getSimpleName(),"Création d'une tilemap");
		map=new TouchMaptiles(level,128,128);
		map.setBounds(0, 0, AssetLoader.width, AssetLoader.height);
		map.addListener(new ActorGestureListener(){
			@Override
			public void zoom(InputEvent event, float initialDistance, float distance) {
				String[] exec={"zoomp","zoomm"};
				int zooming=(int)(distance/initialDistance*1000f);
				event_coordination(0,0,zooming,calling.zoom,exec);
			}
			@Override
			public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
			{
				float deltaX = pointer2.x - pointer1.x;
				float deltaY = pointer2.y - pointer1.y;
				int angle = (int)((float)Math.atan2((double)deltaY,(double)deltaX) * MathUtils.radiansToDegrees);
				angle += 90;
				if(angle < 0)
					angle = 360 - (-angle);
				String[] exec={"transmuter"};
				event_coordination(initialPointer1.x,initialPointer1.y,angle,calling.pinch,exec);
			}
			@Override
			public boolean longPress(Actor actor, float x, float y) {
				String[] exec={"transmuter"};
				return event_coordination(x,y,0,calling.longpress,exec);
			}
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button)  {
				String[] exec={"transmuter"};
				if (count==1)
					event_coordination(x,y,button,calling.tap,exec);
				else if (count>=2)
					event_coordination(x,y,button,calling.taptap,exec);
			}
		});
		map.addListener(new InputListener(){
			@Override
			public boolean mouseMoved(InputEvent event,float x,float y) {
				String[] exec={"transmuter"};
				return event_coordination(x,y,0,calling.mouseover,exec);
			}

			@Override	
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				oldx=0;
				oldy=0;
				String[] exec={"cleaner","infos","zoomp","zoomm","copper_pen","fiber_pen","copper_eraser","fiber_eraser","transmuter_eraser","all_eraser","blank","transmuter","copper_brush","fiber_brush"};
				return event_coordination(x,y,button,calling.mouseclick,exec);
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
			public void clicked(InputEvent event, float x, float y)  {
				mapexit=true;
				Vector2 coords=menu.screentoworld(x,y);	
				MapProperties tile=menu.getMenubyTile((int)coords.x,(int)coords.y);
				if (tile!=null && tile.containsKey("name"))
				{
					menu.EraseMenuTransmuterSurtile();
					map.tempclear();
					if (menuactor==null)
						menuactor=new Actor();
					map.fillempty(60);
					selected=menuactor;
					if (tile.get("type").toString().startsWith("transmuter"))
					{
						if (tile.containsKey("movetox")) {
							coords.x+=(Integer) tile.get("movetox");
							coords.y+=(Integer) tile.get("movetoy");
						}
						MapProperties tilenew=menu.getMenubyTile((int)coords.x,(int)coords.y);
						selected_transmuter=(Transmuter) ((Transmuter) tilenew.get("transmuter")).clone();
						if (selected_transmuter!=null) {
							selected.setName("transmuter");
							showInfo(selected_transmuter);
							menu.setMenuTransmuterSurtile((int)coords.x,(int) coords.y,selected_transmuter);
							Gdx.app.debug("menu","Choix transmuter:"+selected_transmuter.getName());
						}
					}
					else
						selected.setName(tile.get("name").toString());
					Vector2 coords2=menu.worldtoscreen((int)coords.x,(int)coords.y);
					Gdx.app.debug("menu","Coordonnées:"+x+"x"+y+" Menu:"+coords.x+","+coords.y+" Ecran :"+coords2.x+"x"+coords2.y+" type:"+tile.get("type"));
					menuactor.setBounds(coords2.x, coords2.y, 60, 60);
				}
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
					if (call!=calling.mouseover)
						Gdx.app.debug("evenement","mode:"+call+" outil:"+selected.getName()+ " X: " + coords.x + " Y: " + coords.y+" button:"+button);
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
		if (call==calling.taptap && button==0 || (call==calling.mouseclick && button==1))
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
		int color=0;
		OrderedMap<Vector2, Integer> tiles=selected_transmuter.getTilesidrotated();
		Entries<Vector2, Integer> iterator=tiles.iterator();
		while(iterator.hasNext()){
			Entry<Vector2, Integer> all = iterator.next();
			color=63;
			int index=tiles.keys().toArray().indexOf(all.key, false);
			if (((selected_transmuter.getTilestype(index)==CaseType.Nimporte) || (!level.Grid.getFiber(x+all.key.x, y+all.key.y) &&  !level.Grid.getCopper(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Rien) || (level.Grid.getFiber(x+all.key.x, y+all.key.y) &&  level.Grid.getCopper(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Tout) || (level.Grid.getCopper(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Cuivre) ||  (level.Grid.getFiber(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Fibre) ||  (level.Grid.getFiber(x+all.key.x, y+all.key.y) && !level.Grid.getCopper(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Fibre_seul) || (level.Grid.getCopper(x+all.key.x, y+all.key.y) && !level.Grid.getFiber(x+all.key.x, y+all.key.y) && selected_transmuter.getTilestype(index)==CaseType.Cuivre_seul)) && (level.Grid.getTransmutercalc(x+all.key.x, y+all.key.y)==0))
				color=0;
			else
				positionisgood=false;
			map.tempdraw(x+all.key.x, y+all.key.y, all.value, selected_transmuter.getRotation().ordinal(),color);
		}
		if ((call==calling.longpress && button==0) && positionisgood)
		{
			level.Grid.GetXY(x,y).Transmuter=(Transmuter) selected_transmuter.clone();
			if (alone) level.Grid.tiling_transmuter();
			map.redraw(60);
			Gdx.input.vibrate(new long[] { 0, 400, 500, 400}, -1);
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
				Gdx.app.debug("map","transmuter deplacement vers origine:"+level.Grid.GetXY(x,y).Transmuter_movex+","+level.Grid.GetXY(x,y).Transmuter_movey+" coords:"+(x+level.Grid.GetXY(x,y).Transmuter_movex)+"x"+(y+level.Grid.GetXY(x,y).Transmuter_movey));
				Gdx.app.debug("map",level.Grid.getTransmuter(x+level.Grid.GetXY(x,y).Transmuter_movex,y+level.Grid.GetXY(x,y).Transmuter_movey).getInformations());
				showInfo(level.Grid.getTransmuter(x+level.Grid.GetXY(x,y).Transmuter_movex,y+level.Grid.GetXY(x,y).Transmuter_movey));
				map.tempclear();
				map.tempdraw(x,y,1069,0,0);
			}
		}

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
				map_transmuter_eraser(0,0,x,y,false,button,call);
				map_fiber_eraser(0,0,x,y,false,button,call);
				map_copper_eraser(0,0,x,y,false,button,call);
			}
		level.Grid.tiling_copper();
		level.Grid.tiling_transmuter();
		map.redraw(60);
	}

	void map_all_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		map_transmuter_eraser(0,0,x,y,alone,button,call);
		map_fiber_eraser(0,0,x,y,false,button,call);
		map_copper_eraser(0,0,x,y,alone,button,call);
	}

	void map_transmuter_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (level.Grid.GetXY(x,y).Transmuter_calc!=0)
		{
			level.Grid.GetXY(x+level.Grid.GetXY(x,y).Transmuter_movex,y+level.Grid.GetXY(x,y).Transmuter_movey).Transmuter=null;
			Gdx.app.debug("map","transmuter deplacement vers origine:"+level.Grid.GetXY(x,y).Transmuter_movex+","+level.Grid.GetXY(x,y).Transmuter_movey+" coords:"+(x+level.Grid.GetXY(x,y).Transmuter_movex)+"x"+(y+level.Grid.GetXY(x,y).Transmuter_movey));
		}
		if (alone) {
			level.Grid.tiling_transmuter();
			map.redraw(60);
		}
	}

	void map_fiber_eraser(float realx, float realy,int x, int y,boolean alone,int button,calling call) {
		if (level.Grid.GetXY(x,y).Transmuter_calc==0) {
			level.Grid.GetXY(x,y).Fiber=0;
			if (alone) {
				level.Grid.tiling_copper();
				map.redraw(60);
			}
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
		if (level.Grid.GetXY(x,y).Transmuter_calc==0) {
			level.Grid.GetXY(x,y).Copper=false;
			if (alone) {
				level.Grid.tiling_copper();
				map.redraw(60);
			}
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
		stage_tooltip.draw();
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
		stage_info.addActor(info_up_cycle);		
		stage_info.addActor(info_up_nrjval);
		stage_info.addActor(info_up_tempval);
		stage_info.addActor(info_up_rayonval);
		stage_info.addActor(info_up_cycleval);		
		stage_info.addActor(info_nom);
		stage_info.addActor(info_cout);
		stage_info.addActor(info_desc);	
		stage_menu.addActor(map);
		//stage_tooltip.addActor(tooltip);		
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
		stage.addActor(research);
		stage.addActor(menu);
		//gesturedetector=new GestureDetector(null);
		//processors.add(gesturedetector); 
		processors.add(stage);
		processors.add(stage_menu);
		multiplexer.setProcessors(processors);
		Gdx.input.setInputProcessor(multiplexer);	    
		preparemenu(0);
	}

	public void preparebarre(Actor caller, int count) {
		map.fillempty(53);
		map.tempclear();
		menu.EraseMenuTransmuterSurtile();
		hideInfo();
		if (caller.getName()=="run") {

		}
		else if (caller.getName()=="stop") {
		}
		else if (caller.getName()=="speed") {
		}
		else if (caller.getName()=="move") {
			selected=caller;
			if (count>=2) map.initzoom();
		}
		else if (caller.getName()=="zoomp") {
			selected=caller;
			if (count>=2) map.initzoom();
		}
		else if (caller.getName()=="zoomm") {
			selected=caller;
			if (count>=2) map.initzoom();
		}
		else if (caller.getName()=="infos") {
			selected=caller;
			if (count>=2) map.initzoom();
		}
		else if (caller.getName()=="raz") {
		}
		else if (caller.getName()=="save") {
		}
		else if (caller.getName()=="levels") {
			Gdx.app.debug("Barre","Affichage des niveaux.");
			((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(level.aWorld));
		}
		else if (caller.getName()=="tree") {
		}
		else if (caller.getName()=="exits") {
			Gdx.app.exit();
		}
		else if (caller.getName()=="screen") {
			DisplayMode currentMode = Gdx.graphics.getDesktopDisplayMode();
			if  (Gdx.graphics.isFullscreen()) 
			{
				Gdx.app.debug("Barre","vers fenetre.");
				Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, false);
			}
			else
			{
				Gdx.app.debug("Barre","vers plein ecran.");
				Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, true);
			}
			((ImageButton)caller).setChecked(Gdx.graphics.isFullscreen());
		}
		else if (caller.getName()=="sound") {
			if  (AssetLoader.intro.getVolume()>0) 
			{
				Gdx.app.debug("Barre","arret son.");
				AssetLoader.intro.setVolume(0f);
			}
			else
			{
				Gdx.app.debug("Barre","marche son.");
				AssetLoader.intro.setVolume(1f);
			}
			((ImageButton)caller).setChecked(AssetLoader.intro.getVolume()>0);
		}
		else if (caller.getName()=="tuto") {
			if  (AssetLoader.Tooltipmanager.enabled) 
			{
				Gdx.app.debug("Barre","arret tuto.");
				AssetLoader.Tooltipmanager.enabled=false;
			}
			else
			{
				Gdx.app.debug("Barre","marche tuto.");
				AssetLoader.Tooltipmanager.enabled=true;
			}
			((ImageButton)caller).setChecked(AssetLoader.Tooltipmanager.enabled);
		}
		else if (caller.getName()=="settings") {
		}
		else if (caller.getName()=="flag") {
			if  (AssetLoader.language.getLocale().getDisplayName().contains("français")) 
			{
				Gdx.app.debug("Barre","Langue USA");
				AssetLoader.language=AssetLoader.usa;
			}
			else
			{
				Gdx.app.debug("Barre","Langue FR");
				AssetLoader.language=AssetLoader.french;
			}
			((ImageButton)caller).setChecked(AssetLoader.language.getLocale().getDisplayName().contains("français"));
		}
		else if (caller.getName()=="stat") {
		}
	}

	public void preparemenu(int menuitem) {
		checkMenu(menuitem,true);
		menu.clear();
		map.tempclear();
		map.fillempty(53);
		selected=null;
		menu.EraseMenuTransmuterSurtile();
		hideInfo();
		if (menuitem==0) {
			menu.setMenuTile(0, 7, 71, "copper_pen");
			menu.setMenuTile(1, 7, 72, "copper_brush");
			menu.setMenuTile(2, 7, 73, "copper_eraser");
			menu.setMenuTile(1, 5, 70, "blank");
			menu.setMenuTile(0, 6, 74, "fiber_pen");
			menu.setMenuTile(1, 6, 75, "fiber_brush");
			menu.setMenuTile(2, 6, 76, "fiber_eraser");
			menu.setMenuTile(0, 5, 77, "transmuter_eraser");
			menu.setMenuTile(2, 5, 78, "all_eraser");
			menu.setMenuTile(3, 3, 79, "cleaner");
		}
		else if (menuitem==1) {
			menu.setMenuTransmuter(0,7,"Positiveur I",Angular.A00);
			menu.setMenuTransmuter(2,7,"Negativeur I",Angular.A00);
			menu.setMenuTransmuter(0,6,"Positiveur II",Angular.A00);
			menu.setMenuTransmuter(2,6,"Negativeur II",Angular.A00);
			menu.setMenuTransmuter(0,5,"Positiveur III",Angular.A00);
			menu.setMenuTransmuter(1,5,"Negativeur III",Angular.A00);
			menu.setMenuTransmuter(0,4,"Inverseur I",Angular.A00);
			menu.setMenuTransmuter(1,4,"Inverseur II",Angular.A00);
			menu.setMenuTransmuter(0,3,"Neutraliseur I",Angular.A00);
			menu.setMenuTransmuter(1,3,"Neutraliseur II",Angular.A00);
		}
		else if (menuitem==2) {

		}
		else if (menuitem==3) {

		}
		else if (menuitem==4) {

		}
		else if (menuitem==5) {

		}
		else if (menuitem==6) {

		}
		else if (menuitem==7) {
			menu.setMenuTransmuter(0,7,"Positiveur non activable",Angular.A00);
			menu.setMenuTransmuter(1,7,"Negativeur non activable",Angular.A00);
		}
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
		info_activation.setText(String.valueOf(transmuter.getMaxActivationLevel()));
		info_up_cycle.setVisible(transmuter.isUpgradableCycle());		
		info_up_nrj.setVisible(transmuter.isUpgradableNrj());
		info_up_temp.setVisible(transmuter.isUpgradableTemp());
		info_up_rayon.setVisible(transmuter.isUpgradableRayon());
		info_up_cycleval.setVisible(transmuter.isUpgradableCycle());		
		info_up_nrjval.setVisible(transmuter.isUpgradableNrj());
		info_up_tempval.setVisible(transmuter.isUpgradableTemp());
		info_up_rayonval.setVisible(transmuter.isUpgradableRayon());
		info_up_cycleval.getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("jauge"+transmuter.getUpgradeCycle()));
		info_up_cycleval.setColor(AssetLoader.Levelcolors[0]);
		info_up_nrjval.getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("jauge"+transmuter.getUpgradeNrj()));
		info_up_nrjval.setColor(AssetLoader.Levelcolors[4]);
		info_up_tempval.getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("jauge"+transmuter.getUpgradeTemp()));
		info_up_tempval.setColor(AssetLoader.Levelcolors[1]);
		info_up_rayonval.getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("jauge"+transmuter.getUpgradeRayon()));
		info_up_rayonval.setColor(AssetLoader.Levelcolors[2]);
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
