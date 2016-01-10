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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.Objectives;
import fr.evolving.UI.ServerList;
import fr.evolving.game.main;
import fr.evolving.inputs.InputHandler;

import java.util.Timer;
import java.util.TimerTask;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.SaveObject;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;
import fr.evolving.database.Base;
import fr.evolving.database.Base.datatype;
import fr.evolving.database.LocalBase;
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
	private ServerList Statdata,Userdata,Gamedata;
	private Label Statdatalabel, Userdatalabel, Gamedatalabel;
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
	
	public void menu() {
		cout.setVisible(false);
		tech.setVisible(false);
		cycle.setVisible(false);
		temp.setVisible(false);
		rayon.setVisible(false);
		nrj.setVisible(false);
		Previous.setVisible(false);
		Next.setVisible(false);
		Exit.setVisible(false);
		buttonPlay.setVisible(false);
		TextDescriptive.setVisible(false);
		SetButtonStat();
	}
	
	public void SetButtonConnect() {
		buttonStat.setColor(buttonConnect.getColor());
		buttonConnect.setColor(1f,0,0,1f);
		Statdata.setVisible(true);
		Userdata.setVisible(true);
		Gamedata.setVisible(true);
		Statdatalabel.setVisible(true);
		Userdatalabel.setVisible(true);
		Gamedatalabel.setVisible(true);	
	}
	
	public void SetButtonStat() {
		buttonConnect.setColor(buttonStat.getColor());
		buttonStat.setColor(1f,0,0,1f);
		Statdata.setVisible(false);
		Userdata.setVisible(false);
		Gamedata.setVisible(false);
		Statdatalabel.setVisible(false);
		Userdatalabel.setVisible(false);
		Gamedatalabel.setVisible(false);	
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
				buttonLevels[i]=new ButtonLevel(level,true,AssetLoader.ratio);
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
		buttonConnect = new TextButton("Connexions", AssetLoader.Skin_ui);
		buttonConnect.setBounds(1480, AssetLoader.height-60, 190, 40);
		buttonConnect.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (!Statdata.isVisible())
					SetButtonConnect();
			}	
		});
		buttonPlay = new TextButton("Jouer", AssetLoader.Skin_ui);
		buttonPlay.setBounds(1040, 20, 150, 40);
		buttonPlay.addListener(new ClickListener(){
	        @Override
			public void clicked(InputEvent event, float x, float y) {
					((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(selected.level));
				}				
			});
		buttonStat = new TextButton("Statistiques", AssetLoader.Skin_ui);
		buttonStat.setBounds(1710, AssetLoader.height-60, 190, 40);
		buttonStat.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Statdata.isVisible())
					SetButtonStat();
			}	
		});
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
		String url="http://evolving.fr/servers/list.xml";
		Statdata=new ServerList(url,Base.datatype.statdata,AssetLoader.Skin_ui);
		Statdatalabel=new Label("Stockage des statistiques:", AssetLoader.Skin_ui, "grey");
		Statdata.setBounds(1480, AssetLoader.height-300, 420, 200);
		Statdatalabel.setPosition(1480, AssetLoader.height-100);
		Userdata=new ServerList(url,Base.datatype.userdata,AssetLoader.Skin_ui);
		Userdatalabel=new Label("Stockage des données du joueur:", AssetLoader.Skin_ui, "grey");
		Userdata.setBounds(1480, AssetLoader.height-600, 420, 200);
		Userdatalabel.setPosition(1480, AssetLoader.height-400);
		Gamedata=new ServerList(url,Base.datatype.gamedata,AssetLoader.Skin_ui);
		Gamedatalabel=new Label("Stockage des données du jeu:", AssetLoader.Skin_ui, "grey");
		Gamedata.setBounds(1480, AssetLoader.height-900, 420, 200);
		Gamedatalabel.setPosition(1480, AssetLoader.height-700);
		//menu();
		//Gdx.app.debug(getClass().getSimpleName(),"Création des boutons de niveau.");
		//thelevels= SaveObject.initObject();
		//loadWorld(world);
		LocalBase test=new LocalBase(datatype.gamedata,"local:test.db");
		//for(String tester :test.getworlds())
		//	Gdx.app.debug("test",tester);
		//test.setworld(thelevels, "test pour voir");
		
		
		//thelevels=null;
		thelevels=test.getworld("test pour voir").toArray();
		test.getworld("test pour voire");
		//thelevels[0].Name="anus vivant";
		//test.setworld(thelevels, "test pour voir");
		//test.deleteworld("pop");
		loadWorld(world);
		
		LocalBase test2=new LocalBase(datatype.userdata,"local:test.db");
		test.setlevelunlock(0, 1);	
		Gdx.app.debug("lock",String.valueOf(test.getlevellock(0, 1)));
		Gdx.app.debug("lock",String.valueOf(test.getlevellock(110, 1)));
		Gdx.app.debug("research",String.valueOf(test.getResearchpoint(0)));		
		test.setResearchpoint(0, 5000);
		Gdx.app.debug("research",String.valueOf(test.getResearchpoint(0)));		
		test.setTransmuters(0, AssetLoader.allTransmuter);
		Array<Transmuter> retest=test.getTransmuters(0);
		Gdx.app.debug("research",String.valueOf(test.getResearchpoint(0)));	
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
        stage.addActor(Statdata);
        stage.addActor(Statdatalabel);   
        stage.addActor(Userdata);   
        stage.addActor(Userdatalabel);   
        stage.addActor(Gamedata);   
        stage.addActor(Gamedatalabel);   
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
