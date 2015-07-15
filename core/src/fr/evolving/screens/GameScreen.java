package fr.evolving.screens;

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
	String[] tocreate={"run","stop","speed","separator","move","zoomp","zoomm","separator","raz","save","levels","tree","exits","separator","screen","sound","tuto","settings","separator","stat"};
	String[] tocreate2={"Structure","Charge","Direction","Selection","Création","Détection","Action","Scénario"};
	public Actor selected;
	private ButtonLevel buttonlevel;
	private Objectives objectives;
	private TouchMaptiles map;
	private Menu menu;
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
		for (String tocreateitem: tocreate) {
			Barre[i]= new ImageButton(AssetLoader.Skin_level,tocreateitem);
			Barre[i++].setName(tocreateitem);
		}
		Barre2=new ImageTextButton[tocreate2.length];
		i=0;
		for (String tocreateitem: tocreate2) {
			Barre2[i]= new ImageTextButton(tocreateitem,AssetLoader.Skin_level);
			Barre2[i++].setName(tocreateitem);
		}
		Barre[10].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Niveaux");
	        	((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(level.aWorld));
	        }
	     });
		if (Gdx.graphics.isFullscreen())
			Barre[14].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("windows"));
		Barre[14].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Ecran");
	        	DisplayMode currentMode = Gdx.graphics.getDesktopDisplayMode();
	        	if  (Gdx.graphics.isFullscreen()) 
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"vers fenetre.");
	        		Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, false);
	        		Barre[14].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("screen"));
	        	}
	        	else
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"vers plein ecran.");
	        		Gdx.graphics.setDisplayMode(currentMode.width, currentMode.height, true);
	        		Barre[14].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("windows"));
	        	}
	        }
	     });
		if (AssetLoader.intro.getVolume()==0)
			Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("nosound"));
		Barre[15].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().getName(),"Barre:Son");
	        	if  (AssetLoader.intro.getVolume()>0) 
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"arret son.");
	        		AssetLoader.intro.setVolume(0f);
	        		Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("nosound"));
	        	}
	        	else
	        	{
	        		Gdx.app.debug(event.getListenerActor().toString(),"marche son.");
	        		AssetLoader.intro.setVolume(1f);
	        		Barre[15].getStyle().up =new TextureRegionDrawable(AssetLoader.Atlas_level.findRegion("sound"));
	        	}
	        }
	     });
		Barre[12].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Quitter");
	    		Gdx.app.exit();
	        }
	     });
		for (i=4;i<7;i++) {
			Barre[i].addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		        	selected=event.getListenerActor();
		        	Gdx.app.debug(event.getListenerActor().toString(),"Barre:Selection dans la Barre bas");
		        }
		     });
		}
		Barre2[0].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Gdx.app.debug(event.getListenerActor().toString(),"Affichage sousmenu");
	        	((TiledMapTileLayer)menu.map.getLayers().get(0)).getCell(1,6).setTile(AssetLoader.tileSet.getTile(10));
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
				if (selected==null)
					;
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
				else if (selected.getName()=="pen")
				{
					Vector2 coords=map.screentoworld(x, y);
					if (level.Grid.GetXY(coords.x,coords.y)!=null)
					{
						Gdx.app.debug(event.getListenerActor().toString(),"Screen coordinates translated to world coordinates: "+ "X: " + coords.x + " Y: " + coords.y);
						if (level.Grid.GetCopper(coords.x,coords.y)==false)
							level.Grid.GetXY(coords.x,coords.y).Copper=true;
						else
							level.Grid.GetXY(coords.x,coords.y).Copper=false;
						level.Grid.tiling();
						map.redraw();
					}	
					return false;
				}
				return true;
			 }
		   });
		map.addListener(new ClickListener(){
			@Override
		    public void touchDragged(InputEvent event, float x, float y, int pointer) {
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
				}
		   });
		menu=new Menu(4,8);
		map.addListener(new ClickListener(){
			@Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Vector2 coords=menu.screentoworld(x,y);	
				Gdx.app.debug(event.getListenerActor().toString(),"Coordonnées:"+x+"x"+y+" Coordonnées deprojettée:"+coords.x+"x"+coords.y);
				
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
		for (int i=0;i<tocreate2.length;i++) 
			table2.addActor(Barre2[i]);
		for (int i=0;i<tocreate.length;i++) 
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
