package fr.evolving.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage;
    private Table table;
	private GameWorld world;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private ImageButton[] Barre;
	private ImageTextButton cycle,temp,nrj,rayon,cout,tech;
	String[] tocreate={"run","stop","speed","separator","move","zoomp","zoomm","separator","raz","save","levels","tree","exits","separator","screen","sound","tuto","settings","separator","stat"};
	private ButtonLevel buttonlevel;
	private Objectives objectives;
	private TouchMaptiles map;
	private Image Barrehaut,Barrebas;
	
	// This is the constructor, not the class declaration
	public GameScreen(Level alevel) {
		Gdx.app.debug(getClass().getSimpleName(),"Création des elements primordiaux du screen (stage, renderer, table, level, world)");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		this.level=alevel;
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
		Gdx.app.debug(getClass().getSimpleName(),"Création de la barre de gestion du bas");	
		Barre=new ImageButton[tocreate.length];
		int i=0;
		for (String tocreateitem: tocreate) 
			Barre[i++]= new ImageButton(AssetLoader.Skin_level,tocreateitem);
		Barre[10].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(level.aWorld));
	        }
	     });
		Barre[12].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	    		Gdx.app.exit();
	        }
	     });
		Barre[6].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	map.setZoom(map.getZoom()*0.7f);
	        }
	     });
		Barre[5].addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	map.setZoom(map.getZoom()*1.3f);
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
		Gdx.app.debug(getClass().getSimpleName(),"Ajout des barres");
		Barrehaut=new Image(AssetLoader.Atlas_level.findRegion("barrehaut"));
		Barrehaut.setBounds(0.0f, AssetLoader.height-200.0f,1920.0f,200.0f);
		Barrehaut.setColor(0.25f, 0.25f, 0.25f, 0.9f);
		Barrebas=new Image(AssetLoader.Atlas_level.findRegion("barrebas"));
		Barrebas.setBounds(0.0f, 0.0f,1920.0f,95.0f);
		Barrebas.setColor(0.25f, 0.25f, 0.25f, 0.9f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		runTime += delta;
		world.update(delta);
		Renderer.render(delta, runTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width,height);
	}

	@Override
	public void show() {
		Gdx.app.log("*****","Affichage du niveau.");
		table.bottom().left().padBottom(10f);
		for (int i=0;i<tocreate.length;i++) 
			table.add(Barre[i]).padLeft(10f);
		stage.addActor(map);
		stage.addActor(Barrehaut);
		stage.addActor(Barrebas);
		stage.addActor(objectives);
		stage.addActor(buttonlevel);
		stage.addActor(rayon);
		stage.addActor(nrj);
		stage.addActor(temp);
		stage.addActor(cycle);
		stage.addActor(table);
		stage.addActor(tech);
		stage.addActor(cout);
        Gdx.input.setInputProcessor(stage);
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
