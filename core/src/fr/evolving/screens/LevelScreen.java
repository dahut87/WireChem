package fr.evolving.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.inputs.InputHandler;
import java.util.Timer;
import java.util.TimerTask;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.SaveObject;
import fr.evolving.automata.Level;
import fr.evolving.effects.Laser;


public class LevelScreen implements Screen {
	public ButtonLevel[] buttonLevels;
	private LevelRenderer Renderer;
	private float runTime;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage;
    private Table table;
    private ImageButton Previous,Next,Exit,cout,tech;
	private TextButton buttonPlay,buttonExit;
	private Level[] thelevels;
	private TextArea TextDescriptive;

	public LevelScreen() {
		Gdx.app.debug(getClass().getSimpleName(),"Création des boutons.");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		Renderer=new LevelRenderer(this);
		buttonLevels = new ButtonLevel[10];
		thelevels= SaveObject.initObject();
		for (int i = 0;i<thelevels.length;i++) {
			if (thelevels[i] != null)
				buttonLevels[i]=new ButtonLevel(thelevels[i],true);	
				buttonLevels[i].addListener(new ClickListener(){
		        @Override
		        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		        	TextDescriptive.setText(((ButtonLevel)event.getListenerActor()).level.Description);
		        }
			});
		}
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
		TextDescriptive = new TextArea("Descriptif", AssetLoader.Skin_level,"Descriptif");
		TextDescriptive.setBounds(15, 15, 1009, 130);
		buttonPlay = new TextButton("Connexions", AssetLoader.Skin_level,"Bouton");
		buttonPlay.setPosition(1500, AssetLoader.height-40);
		buttonExit = new TextButton("Statistiques", AssetLoader.Skin_level,"Bouton");
		buttonExit.setPosition(1720, AssetLoader.height-40);		
		Exit=new ImageButton(AssetLoader.Skin_level,"Exit");
		Exit.setPosition(1110, AssetLoader.height-Exit.getHeight()-5);
		Exit.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Gdx.app.exit();
	        }
		});
		Next=new ImageButton(AssetLoader.Skin_level,"Next");
		Next.setPosition(1030, 170);
		Previous=new ImageButton(AssetLoader.Skin_level,"Previous");
		Previous.setPosition(1110, 170);
		cout=new ImageButton(AssetLoader.Skin_level,"cout");
		cout.setPosition(1250, 50);
		tech=new ImageButton(AssetLoader.Skin_level,"tech");
		tech.setPosition(1370, 50);		
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		Renderer.render(delta, runTime);
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width,height);
	}

	@Override
	public void show() {
		Gdx.app.log("*****","Affichage du choix des mondes & niveaux.");
		for (int i=0;i<10;i++) {
			if (buttonLevels[i]!=null) {
				stage.addActor(buttonLevels[i]);
			}
		}
        table.setFillParent(true);
        stage.addActor(TextDescriptive);
        stage.addActor(Exit);
        stage.addActor(Next);
        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
        stage.addActor(Previous);
        stage.addActor(cout);
        stage.addActor(tech);
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
