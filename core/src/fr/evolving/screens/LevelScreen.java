package fr.evolving.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import fr.evolving.UI.Objectives;
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
    private ImageButton Previous,Next,Exit;
    private ImageTextButton cout,tech,cycle,temp,rayon,nrj;
	private TextButton buttonPlay,buttonExit;
	private Level[] thelevels;
	private TextArea TextDescriptive;
	public int World;
	Objectives Victory;

	public LevelScreen() {
		this.World=0;
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
		        	ButtonLevel button = (ButtonLevel)event.getListenerActor();		        	
		        	System.out.println(button.getBackground());
		        	TextDescriptive.setText(button.level.Description);
		        	if (button.level.Maxcycle<99999 && button.level.Maxcycle>0) {
		        		cycle.setText(String.valueOf(button.level.Maxcycle));
		        		cycle.setVisible(true);
		        	}
		        	else
		        		cycle.setVisible(false);
		        	if (button.level.Maxtemp<99999 && button.level.Maxtemp>0) {
		        		temp.setText(String.valueOf(button.level.Maxtemp));
		        		temp.setVisible(true);
		        	}
		        	else
		        		temp.setVisible(false);
		        	if (button.level.Maxnrj<99999 && button.level.Maxnrj>0) {
		        		nrj.setText(String.valueOf(button.level.Maxnrj));
		        		nrj.setVisible(true);
		        	}
		        	else
		        		nrj.setVisible(false);
		        	if (button.level.Maxrayon<99999 && button.level.Maxrayon>0) {
		        		rayon.setText(String.valueOf(button.level.Maxrayon));
		        		rayon.setVisible(true);
		        	}
		        	else
		        		rayon.setVisible(false);
		        	if (button.level.Cout>0) {
		        		cout.setText(String.valueOf(button.level.Cout));
		        		cout.setVisible(true);
		        	}
		        	else
		        		cout.setVisible(false);
		        	if (button.level.Tech>=0) {
		        		tech.setText(String.valueOf(button.level.Tech));
		        		tech.setVisible(true);
		        	}
		        	else
		        		tech.setVisible(false);
		        	Victory.setVictory(button.level.Victory);
		        	
		        }
		        public void leave(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		        	ButtonLevel button = (ButtonLevel)event.getListenerActor();		        	
		        	button.setBackground("leveler"+String.valueOf(World)+"_over");
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
		TextDescriptive.setBounds(15, 15, 1185, 100);
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
		cout=new ImageTextButton("5",AssetLoader.Skin_level,"cout");
		cout.setPosition(1250, 48);
		tech=new ImageTextButton("10",AssetLoader.Skin_level,"tech");
		tech.setPosition(1365, 48);
		temp=new ImageTextButton("10",AssetLoader.Skin_level,"temp");
		temp.setPosition(1365, 360);
		cycle=new ImageTextButton("10",AssetLoader.Skin_level,"cycle");
		cycle.setPosition(1250, 360);
		nrj=new ImageTextButton("10",AssetLoader.Skin_level,"nrj");
		nrj.setPosition(1365, 490);
		rayon=new ImageTextButton("10",AssetLoader.Skin_level,"rayon");
		rayon.setPosition(1250, 490);
		Victory=new Objectives();
		Victory.setVictory(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		Victory.setPosition(1216, 185);
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
        stage.addActor(cycle);
        stage.addActor(nrj);
        stage.addActor(temp);
        stage.addActor(rayon);
        stage.addActor(Victory);
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
