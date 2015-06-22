package fr.evolving.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import fr.evolving.worlds.GameRenderer;
import fr.evolving.worlds.GameWorld;
import fr.evolving.worlds.LevelRenderer;
import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;
import fr.evolving.inputs.InputHandler;

public class GameScreen implements Screen {
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage;
    private Table table,table2;
	private GameWorld world;
	private GameRenderer Renderer;
	private float runTime;
	public Level level;
	private ImageButton[] bottom;
	private ImageButton[] topleft;
	String[] bottom_tocreate={"run","stop","speed","separator","move","zoomp","zoomm","separator","raz","save","levels","tree","exits","separator","screen","sound","tuto","settings","separator","stat"};
	String[] topleft_tocreate={"cycle","temp","nrj","rayon"};
	
	
	// This is the constructor, not the class declaration
	public GameScreen(Level level) {
		Gdx.app.debug(getClass().getSimpleName(),"Création des elements primordiaux du screen (stage, renderer, table, level, world)");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		table2 = new Table();
		this.level=level;
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
		bottom=new ImageButton[bottom_tocreate.length];
		int i=0;
		for (String tocreateitem: bottom_tocreate) 
			bottom[i++]= new ImageButton(AssetLoader.Skin_level,tocreateitem);
		topleft=new ImageButton[topleft_tocreate.length];
		i=0;
		for (String tocreateitem: topleft_tocreate) 
			topleft[i++]= new ImageButton(AssetLoader.Skin_level,tocreateitem);

	}

	@Override
	public void render(float delta) {
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
		for (int i=0;i<bottom_tocreate.length;i++) 
			table.add(bottom[i]).padLeft(10f);
		stage.addActor(table);
		table2.top().left().padTop(10f);
		for (int i=0;i<topleft_tocreate.length;i++) 
			table2.add(topleft[i]).padLeft(10f);
		stage.addActor(table2);
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
