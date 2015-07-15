package fr.evolving.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.Objectives;
import fr.evolving.game.main;
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
    public ImageButton logosmall;
    private ImageTextButton cout,tech,cycle,temp,rayon,nrj;
	private TextButton buttonConnect,buttonPlay,buttonStat;
	private Level[] thelevels;
	private TextArea TextDescriptive;
	public int world;
	private Objectives Victory;
	public ButtonLevel selected;
	
	public int getMaxWorld() {
			int max=0;
			for (Level level :thelevels)
				if (level!=null && level.aWorld>max)
					max=level.aWorld;
			return	max;
	}
	
	public void loadWorld(int aworld) {
		int i=0;
		if (buttonLevels!=null)
		for (int j=0;j<10;j++) {
			if (buttonLevels[j]!=null) {
				buttonLevels[j].remove();
			}
		}
		buttonLevels = null;
		buttonLevels = new ButtonLevel[10];
		for (Level level :thelevels) {
			if (level!=null && level.aWorld==aworld) {
				buttonLevels[i]=new ButtonLevel(level,true);
				Gdx.app.debug(getClass().getSimpleName(),"Ajout du niveau :"+level.Name+" N°"+String.valueOf(level.aLevel));
				buttonLevels[i++].addListener(new ClickListener(){
		        @Override
		        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		        	ButtonLevel abutton = (ButtonLevel)event.getListenerActor();
		        	Gdx.app.debug(event.getListenerActor().toString(), "Enter button ");
		        	if (!abutton.isChecked())
		        		 showlevel(abutton);
		        }
		        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		        	ButtonLevel abutton = (ButtonLevel)event.getListenerActor();
		        	Gdx.app.debug(event.getListenerActor().toString(), "Enter button ");
		        	if (!abutton.isChecked())
		        		showlevel(abutton);
				}
				public void touchDragged(InputEvent event,float x,float y,int pointer) {
					ButtonLevel abutton = (ButtonLevel)event.getListenerActor();
					if (logosmall.isChecked()) {
						abutton.setPosition(event.getStageX()-56, event.getStageY()-20);
						}
					}		
				});
			}
	
		}
		for (int j=0;j<10;j++) {
			if (buttonLevels[j]!=null) {
				stage.addActor(buttonLevels[j]);
			}
		}
		Gdx.app.debug(getClass().getSimpleName(),"Mise en place du level 0.");
		world=world;
		buttonLevels[0].setChecked(true);
		showlevel(buttonLevels[0]);
	}

	public LevelScreen(int aworld) {
		this.world=aworld;
		Gdx.app.debug(getClass().getSimpleName(),"Création des elements primordiaux du screen (stage, renderer, table)");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		Renderer=new LevelRenderer(this);
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
		Gdx.app.debug(getClass().getSimpleName(),"Création des boutons.");
		logosmall=new ImageButton(AssetLoader.Skin_level,"logosmall");	
		logosmall.setPosition(20, AssetLoader.height-175+logosmall.getHeight()/2);
		TextDescriptive = new TextArea("Descriptif", AssetLoader.Skin_level,"Descriptif");
		TextDescriptive.setBounds(15, 15, 1185, 100);
		buttonConnect = new TextButton("Connexions", AssetLoader.Skin_level,"Bouton");
		buttonConnect.setBounds(1480, AssetLoader.height-60, 190, 40);
		buttonPlay = new TextButton("Jouer", AssetLoader.Skin_level,"Bouton");
		buttonPlay.setBounds(1040, 20, 150, 40);
		buttonPlay.addListener(new ClickListener(){
	        @Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(selected.level));
				}				
			});
		buttonStat = new TextButton("Statistiques", AssetLoader.Skin_level,"Bouton");
		buttonStat.setBounds(1710, AssetLoader.height-60, 190, 40);
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
		Next.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	if (world<getMaxWorld()) {
	        		world++;
		        	loadWorld(world);
	        	}
	    		Gdx.app.debug(event.getListenerActor().toString(),"World:"+String.valueOf(world)+" Maxworld:"+String.valueOf(getMaxWorld()));
	        }
	     });
		Previous=new ImageButton(AssetLoader.Skin_level,"Previous");
		Previous.setPosition(1110, 170);
		Previous.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	if (world>0) {
	        		world--;
		        	loadWorld(world);
	        	}
	    		Gdx.app.debug(event.getListenerActor().toString(),"World:"+String.valueOf(world)+" Maxworld:"+String.valueOf(getMaxWorld()));
	        }
	     });
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
		Gdx.app.debug(getClass().getSimpleName(),"Conditions de victoire.");
		Victory=new Objectives();
		Victory.setVictory(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		Victory.setPosition(1216, 185);
		Gdx.app.debug(getClass().getSimpleName(),"Création des boutons de niveau.");
		thelevels= SaveObject.initObject();
		loadWorld(world);
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
        stage.addActor(buttonConnect);
        stage.addActor(buttonStat);       
        stage.addActor(Previous);
        stage.addActor(cout);
        stage.addActor(tech);
        stage.addActor(cycle);
        stage.addActor(nrj);
        stage.addActor(temp);
        stage.addActor(rayon);
        stage.addActor(Victory);
        stage.addActor(logosmall);
        Gdx.input.setInputProcessor(stage);
		Gdx.app.debug("AssetLoader","Début dans la bande son \'intro\'");       
		AssetLoader.intro.setLooping(true);
        AssetLoader.intro.play();
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
	
	public void showlevel(ButtonLevel button) {
		Gdx.app.debug(getClass().getSimpleName(), "Reading button "+button.level.Name);
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
		if (button.level.Tech>=1) {
			tech.setText(String.valueOf(button.level.Tech));
			tech.setVisible(true);
		}
		else
			tech.setVisible(false);
		Victory.setVisible(button.level.Cout>0);
		Victory.setVictory(button.level.Victory);
		//for (int i = 0;i<thelevels.length;i++) {
		//	if (thelevels[i] != null && buttonLevels[i]!=button)
		//		buttonLevels[i].setChecked(false);
		if (selected!=null)
			selected.setChecked(false);
		selected=button;
		button.setChecked(true);
		}

}
