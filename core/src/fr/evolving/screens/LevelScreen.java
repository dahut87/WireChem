package fr.evolving.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.Objectives;
import fr.evolving.UI.ServerList;
import fr.evolving.UI.Transhower;
import fr.evolving.UI.Translist;
import fr.evolving.UI.VertiBarre;
import fr.evolving.UI.Worldlist;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.InitWorlds;
import fr.evolving.assets.Preference;
import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Worlds;
import fr.evolving.automata.Worlds.LinkDelMethod;
import fr.evolving.automata.Worlds.State;
import fr.evolving.database.Base;
import fr.evolving.dialogs.WarningDialog;
import fr.evolving.renderers.LevelRenderer;

public class LevelScreen implements Screen {
	public Array<ButtonLevel> buttonLevels;
	private LevelRenderer Renderer;
	private float runTime;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
	private Stage stage;
	private Table table;
	private WarningDialog dialog;
	private ImageButton Previous, Next, Exit, logosmall, databaseSave, adder, signer, finisher, deletelinker, deletebutton, addbutton, unlocked, duplicate, moveit, link, script;
	public ImageButton modify;
	private Image MenuSolo, MenuMulti, MenuScenario;
	private ImageTextButton cout, tech, cycle, temp, rayon, nrj, cycle_orig, temp_orig, rayon_orig, nrj_orig, up_cycle, up_temp, up_rayon, up_nrj, research, up;
	private TextButton buttonConnect, buttonPlay, buttonStat, buttonSave, buttonApply, buttonPlaythis;
	private ServerList Statdata, Userdata, Gamedata;
	private Worldlist Worlddata;
	private Label Statdatalabel, Userdatalabel, Gamedatalabel, Worlddatalabel, rewardlabel, goallabel, ressourcelabel, handicaplabel, initiallabel,outillabel,modelabel,savelabel;
	private TextField worldfield;
	private TextArea TextDescriptive;
	public Worlds worlds;
	private Objectives Victory;
	private VerticalGroup vertibarmod;
	private HorizontalGroup vertibar;
	public ButtonLevel selected;
	public int addervalue;
	private ButtonGroup<Button> chooser, modifbar;
	public Group group_init, group_stat, group_level, group_base, group_debug, group_choose, group_other;
	private DragAndDrop dragAndDrop;
	public Source lastsource;
	
	public void play() {
		if (worlds.getState()!=State.notloaded && worlds.getState()!=State.databasefailed) {
			Gdx.app.debug("wirechem-LevelScreen","Afficher derniere réalisation, monde :"+worlds.getWorld()+" niveau:"+worlds.getLevel());
			worlds.Forcereload();
			if (worlds.getWorld() < 0)
				worlds.setMaxWorldLevel();
		}
	}

	public void menu() {
		selected = null;
		group_init.setVisible(true);
		group_stat.setVisible(false);
		group_level.setVisible(false);
		group_base.setVisible(false);
		group_debug.setVisible(false);
		group_choose.setVisible(false);
		logosmall.setChecked(false);
		worlds.DesactivateDebug();
		Exit.setPosition(1820, AssetLoader.height - 100);
		initlevel();
		MenuSolo.setRotation(0);
		MenuSolo.setScale(1f);
		MenuSolo.setColor(1f, 1f, 1f, 1f);
		MenuSolo.setPosition(0, AssetLoader.height * 17 / 20 - 300);
		MenuMulti.setRotation(0);
		MenuMulti.setScale(1f);
		MenuMulti.setColor(1f, 1f, 1f, 1f);
		MenuMulti.setPosition(0, AssetLoader.height * 12 / 20 - 300);
		MenuScenario.setRotation(0);
		MenuScenario.setScale(1f);
		MenuScenario.setColor(1f, 1f, 1f, 1f);
		MenuScenario.setPosition(0, AssetLoader.height * 7 / 20 - 300);
		MenuSolo.addAction(Actions.sequence(Actions.moveTo(
				(AssetLoader.width - MenuSolo.getWidth()) / 2,
				AssetLoader.height * 17 / 20 - 300, 0.25f)));
		MenuMulti.addAction(Actions.sequence(Actions.fadeIn(0.1f), Actions
				.moveTo((AssetLoader.width - MenuMulti.getWidth()) / 2,
						AssetLoader.height * 12 / 20 - 300, 0.25f)));
		MenuScenario.addAction(Actions.sequence(Actions.fadeIn(0.2f), Actions
				.moveTo((AssetLoader.width - MenuScenario.getWidth()) / 2,
						AssetLoader.height * 7 / 20 - 300, 0.25f)));

	}

	public void initlevel() {
		selected = null;
		if (buttonLevels==null)
			buttonLevels=new Array<ButtonLevel>();
		for(ButtonLevel button: buttonLevels)
			button.remove();
		buttonLevels.clear();
	}

	public void level() {
		Exit.setPosition(1110, AssetLoader.height - Exit.getHeight() - 5);
		group_init.setVisible(false);
		group_choose.setVisible(true);
		group_level.setVisible(true);
		SetButtonStat();
		if (worlds.isDebug()) {
			group_debug.setVisible(true);
			group_choose.setVisible(false);
			group_base.setVisible(false);
			group_stat.setVisible(false);
		}
		play();
	}

	public void SetButtonConnect() {
		group_stat.setVisible(false);
		group_base.setVisible(true);
		
	}

	public void SetButtonStat() {
		group_stat.setVisible(true);
		group_base.setVisible(false);
	}
	
	public void ResetDragDrog() {
		dragAndDrop.clear();
	}
	
	public void initDragDrop() {
		dragAndDrop.clear();
		if (buttonLevels!=null)
			for(ButtonLevel buttonlevel:buttonLevels)
				AddDragDrop((Actor)buttonlevel);
	}

	public void AddDragDrop(final Actor actor) {
		if (actor==null) return;
		dragAndDrop.addSource(new Source(actor) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject(((ButtonLevel)event.getListenerActor()).level.clone());
				payload.setDragActor(new Label(AssetLoader.language.get("[dragdestination-levelscreen]"), AssetLoader.Skin_ui));
				Label validLabel = new Label(AssetLoader.language.get("[dragok-levelscreen]"), AssetLoader.Skin_ui);
				validLabel.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validLabel);
				Label invalidLabel = new Label(AssetLoader.language.get("[dragno-levelscreen]"), AssetLoader.Skin_ui);
				invalidLabel.setColor(1, 0, 0, 1);
				payload.setInvalidDragActor(invalidLabel);
				return payload;
			}
		});
		dragAndDrop.addTarget(new Target(actor) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				lastsource=source;
				Level levelsrc=(Level)payload.getObject();
				Level leveldst=((ButtonLevel)actor).level;
				Gdx.app.debug("wirechem-LevelScreen", "Verification d'un lien du niveau "+levelsrc.aWorld+","+levelsrc.aLevel+" vers "+leveldst.aWorld+","+leveldst.aLevel);
				if (worlds.verifLink(levelsrc.aWorld, levelsrc.aLevel, leveldst.aWorld, leveldst.aLevel)) {
					getActor().setColor(Color.GREEN);
					return true;
				}
				else {
					getActor().setColor(Color.RED);
					return false;
				}
			}
			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}
			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				Level levelsrc=(Level)payload.getObject();
				Level leveldst=((ButtonLevel)actor).level;
				Gdx.app.debug("wirechem-LevelScreen", "Création d'un lien du niveau "+levelsrc.aWorld+","+levelsrc.aLevel+" vers "+leveldst.aWorld+","+leveldst.aLevel);
				worlds.addLink(levelsrc.aWorld, levelsrc.aLevel, leveldst.aWorld, leveldst.aLevel);
			}
		});
	}
	
	public ClickListener buttonLevelslistener() {
		return new ClickListener() {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			ButtonLevel abutton = (ButtonLevel) event.getListenerActor();
			Gdx.app.debug("wirechem-LevelScreen","Enter button ");
			if ((!worlds.isDebug() || modify.isChecked()) && !abutton.isChecked() && (!abutton.level.Locked || worlds.isDebug()))
				showlevel(abutton);
		}

		public void exit(InputEvent event, float x, float y,int pointer, Actor fromActor) {
			ButtonLevel abutton = (ButtonLevel) event.getListenerActor();
			Gdx.app.debug("wirechem-LevelScreen","Enter button ");
			if ((!worlds.isDebug() || modify.isChecked()) && !abutton.isChecked() && (!abutton.level.Locked || worlds.isDebug()))
				showlevel(abutton);
		}

		public void touchDragged(InputEvent event, float x,	float y, int pointer) {
			ButtonLevel abutton = (ButtonLevel) event.getListenerActor();
			if (worlds.isDebug() && moveit.isChecked()) {
				if (event.getStageX()<1180 && event.getStageX()>0 && event.getStageY()>180 && event.getStageY()<AssetLoader.height-260)
					abutton.setPosition(event.getStageX() - 56,	event.getStageY() - 20);
			}
		}
		public void clicked(InputEvent event, float x, float y) {
			ButtonLevel abutton = (ButtonLevel) event.getListenerActor();
			if (!worlds.isDebug() || modify.isChecked())
				abutton.setChecked(true);
			else
				abutton.setChecked(false);
		}
	};
	}

	public void loadWorld() {
		initlevel();
		Array<Level> levels=worlds.getLevels();
		if (levels!=null)
		for (Level level : levels) {
			if (level != null) {
				if (level.Name.isEmpty())
					level.Name=AssetLoader.language.get("[level"+(level.aWorld+1)+"/"+(level.aLevel+1)+"-name]");
				if (level.Description.isEmpty())
					try {
						level.Description=AssetLoader.language.get("[level"+(level.aWorld+1)+"/"+(level.aLevel+1)+"-desc]");
					}
					catch (Exception E) {level.Description="";}
					finally {}
				ButtonLevel buttonlevel= new ButtonLevel(level, AssetLoader.ratio, true);
				buttonLevels.add(buttonlevel);
				if (worlds.isDebug()) buttonlevel.setDisabled(false);
				Gdx.app.debug("wirechem-LevelScreen", "Ajout du niveau :"+ level.Name + " N°" + String.valueOf(level.aLevel));
				buttonlevel.addListener(buttonLevelslistener());
			}
		}
		for (ButtonLevel button : buttonLevels)
				stage.addActor(button);
	}

	public LevelScreen(Worlds aworlds) {
		this.worlds = aworlds;
		addervalue=1;
		dragAndDrop = new DragAndDrop();
		worlds.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (worlds.getState()!=Worlds.State.notloaded && worlds.getWorld()>=0)
				{
					Level changed=worlds.getChange();
					if (changed!=null) {
						for (int i=0;i<buttonLevels.size;i++)
							if (buttonLevels.get(i).level.aLevel==changed.aLevel)
							{
								Gdx.app.debug("wirechem-LevelScreen", "Changement - destruction du niveau "+changed.aLevel);
								buttonLevels.get(i).remove();
								buttonLevels.removeIndex(i);
								return;
							}
						Gdx.app.debug("wirechem-LevelScreen", "Changement - ajout d'un nouveau niveau "+changed.aLevel);
						ButtonLevel button=new ButtonLevel(changed, AssetLoader.ratio, true);
						if (worlds.isDebug()) {
							button.setDisabled(false);
							button.setTouchable(Touchable.enabled);
						}
						else
							showlevel(button);
						buttonLevels.add(button);
						stage.addActor(button);
						button.addListener(buttonLevelslistener());	
						return;
					}
					Gdx.app.debug("wirechem-LevelScreen", "Changement - rechargement des mondes");
					loadWorld();
					if (worlds.isDebug() && modify.isChecked())
						selectone();
					else if (!worlds.isDebug())
					for (ButtonLevel button : buttonLevels)
					{
						button.setChecked(false);
							if (worlds.getLevelData()!=null && button.level.id == worlds.getLevelData().id) {
								Gdx.app.debug("wirechem-LevelScreen", "Changement - selection du monde en cours "+button.level.aLevel);
								showlevel(button);
								break;
							}
					}
					else
						selectnoone();
					if (worlds.getLevelData()==null)
						selected=buttonLevels.first();
					Previous.setVisible(!worlds.isFirstWorld());
					if (worlds.isDebug())
						Next.setVisible(!worlds.isRealLastWorld());
					else
						Next.setVisible(!worlds.isLastWorld());
				}
				else {
					Gdx.app.debug("wirechem-LevelScreen", "Changement - Niveau non chargé, composants non visibles");
					Previous.setVisible(false);
					Next.setVisible(false);
					buttonPlay.setVisible(false);
					TextDescriptive.setVisible(false);
				}
			}
		});
		Gdx.app.debug("wirechem-LevelScreen","Création des elements primordiaux du screen (stage, renderer, table)");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		Renderer = new LevelRenderer(this);
		dialog = new WarningDialog();
		Gdx.app.debug("wirechem-LevelScreen", "Mise en place du timer.");
		ScrollTimer = new Timer();
		ScrollTask = new TimerTask() {
			@Override
			public void run() {
				Renderer.evolve();
			}
		};
		ScrollTimer.scheduleAtFixedRate(ScrollTask, 0, 30);
		
		//**********************************************************
		//Group Init
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Init.");
		MenuSolo = new Image(AssetLoader.Skin_level, "menu1");
		MenuSolo.setOrigin(MenuSolo.getWidth() / 2, MenuSolo.getHeight() / 2);
		MenuSolo.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				MenuMulti.addAction(Actions.fadeOut(0.5f));
				MenuScenario.addAction(Actions.fadeOut(0.5f));
				MenuSolo.addAction(Actions.sequence(
						Actions.parallel(Actions.rotateBy(640, 0.5f),
								Actions.scaleTo(0.05f, 0.05f, 0.5f)),
								Actions.run(new Runnable() {
									public void run() {
										level();
									}
								})));
			}
		});
		MenuMulti = new Image(AssetLoader.Skin_level, "menu2");
		MenuMulti
		.setOrigin(MenuMulti.getWidth() / 2, MenuMulti.getHeight() / 2);
		MenuMulti.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				MenuSolo.addAction(Actions.fadeOut(0.5f));
				MenuScenario.addAction(Actions.fadeOut(0.5f));
				MenuMulti.addAction(Actions.sequence(
						Actions.parallel(Actions.rotateBy(640, 0.5f),
								Actions.scaleTo(0.05f, 0.05f, 0.5f)),
								Actions.run(new Runnable() {
									public void run() {
										level();
									}
								})));
			}
		});
		MenuScenario = new Image(AssetLoader.Skin_level, "menu3");
		MenuScenario.setOrigin(MenuScenario.getWidth() / 2,
				MenuScenario.getHeight() / 2);
		MenuScenario.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				MenuMulti.addAction(Actions.fadeOut(0.5f));
				MenuSolo.addAction(Actions.fadeOut(0.5f));
				MenuScenario.addAction(Actions.sequence(
						Actions.parallel(Actions.rotateBy(640, 0.5f),
								Actions.scaleTo(0.05f, 0.05f, 0.5f)),
								Actions.run(new Runnable() {
									public void run() {
										level();
									}
								})));
			}
		});
		
		group_init=new Group();
		group_init.addActor(MenuScenario);
		group_init.addActor(MenuMulti);
		group_init.addActor(MenuSolo);
		
		//**********************************************************
		//Group Choose
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Choose.");
		buttonConnect = new TextButton(AssetLoader.language.get("[buttonConnect-levelscreen]"), AssetLoader.Skin_ui, "checkable");
		buttonConnect.setBounds(1480, AssetLoader.height - 60, 190, 40);
		buttonConnect.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
					SetButtonConnect();
			}
		});
		buttonStat = new TextButton(AssetLoader.language.get("[buttonStat-levelscreen]"), AssetLoader.Skin_ui, "checkable");
		buttonStat.setBounds(1710, AssetLoader.height - 60, 190, 40);
		buttonStat.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
					SetButtonStat();
			}
		});
		chooser=new ButtonGroup<Button>();
		chooser.add(buttonStat);
		chooser.add(buttonConnect);
		chooser.setMaxCheckCount(1);
		chooser.setMinCheckCount(1);	
		group_choose=new Group();
		group_choose.addActor(buttonStat);
		group_choose.addActor(buttonConnect);
		
		//**********************************************************
		//Group Level
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Level.");
		rewardlabel = new Label(AssetLoader.language.get("[reward-levelscreen]"), AssetLoader.Skin_ui, "variable");
		rewardlabel.setPosition(1215, AssetLoader.height - 38);
		goallabel = new Label(AssetLoader.language.get("[goal-levelscreen]"), AssetLoader.Skin_ui, "variable");
		goallabel.setPosition(1215, 272);
		ressourcelabel = new Label(AssetLoader.language.get("[ressource-levelscreen]"), AssetLoader.Skin_ui, "variable");
		ressourcelabel.setPosition(1215, 122);
		handicaplabel = new Label(AssetLoader.language.get("[handicap-levelscreen]"), AssetLoader.Skin_ui, "variable");
		handicaplabel.setPosition(1215, 582);
		worldfield = new TextField("", AssetLoader.Skin_ui,"transparent");
		worldfield.setPosition(15, 148);
		worldfield.setMaxLength(100);
		worldfield.setWidth(800);
		worldfield.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (worlds.isDebug() && modify.isChecked()) {
					Gdx.app.debug("wirechem-LevelScreen", "Enter world.");
					stage.setKeyboardFocus(worldfield);
				}
			}
			public void exit(InputEvent event, float x, float y,int pointer, Actor fromActor) {
				if (worlds.isDebug() && modify.isChecked()) {
					Gdx.app.debug("wirechem-LevelScreen", "Outer world.");
					stage.setKeyboardFocus(null);
					if (selected!=null)
						selected.level.Name=worldfield.getText();
						selected.setText(selected.level.Name);
				}
			}
		});
		TextDescriptive = new TextArea("Descriptif", AssetLoader.Skin_ui,"descriptif");
		TextDescriptive.setBounds(15, 15, 1185, 110);
		TextDescriptive.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (worlds.isDebug() && modify.isChecked()) {
					Gdx.app.debug("wirechem-LevelScreen", "Enter desc.");
					stage.setKeyboardFocus(TextDescriptive);
				}
			}

			public void exit(InputEvent event, float x, float y,int pointer, Actor fromActor) {
				if (worlds.isDebug() && modify.isChecked()) {
					Gdx.app.debug("wirechem-LevelScreen", "Outer desc.");
					stage.setKeyboardFocus(null);
					if (selected!=null)
						selected.level.Description=TextDescriptive.getText();
				}
			}
		});
		Next = new ImageButton(AssetLoader.Skin_level, "Next");
		Next.setName("Next");
		Next.setPosition(1030, 185);
		Next.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.NextWorld();
				Gdx.app.debug("wirechem-LevelScreen","World:" + String.valueOf(worlds.getWorld()) + " Maxworld:"	+ String.valueOf(worlds.getMaxWorlds()));
				if (worlds.isDebug() && link.isChecked())
					initDragDrop();
			}
		});
		Previous = new ImageButton(AssetLoader.Skin_level, "Previous");
		Previous.setName("Previous");
		Previous.setPosition(1110, 185);
		Previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.PreviousWorld();
				Gdx.app.debug("wirechem-LevelScreen","World:" + String.valueOf(worlds.getWorld()) + " Maxworld:"+ String.valueOf(worlds.getMaxWorlds()));
				if (worlds.isDebug() && link.isChecked())
					initDragDrop();
			}
		});
		cout = new ImageTextButton("5", AssetLoader.Skin_level, "cout");
		cout.setPosition(1250, 48);
		cout.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Cout_orig+=addervalue;
				selected.level.Cout_orig=Math.max(0, Math.min(100000,selected.level.Cout_orig));
				cout.setText(String.valueOf(selected.level.Cout_orig));
				}
			}
		});
		tech = new ImageTextButton("10", AssetLoader.Skin_level, "tech");
		tech.setPosition(1365, 48);
		tech.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Tech+=addervalue;
				selected.level.Tech=Math.max(-1, Math.min(12,selected.level.Tech));
				tech.setText(String.valueOf(selected.level.Tech));
				}
			}
		});
		temp = new ImageTextButton("10", AssetLoader.Skin_level, "temp");
		temp.setPosition(1365, 360);
		temp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Maxtemp+=addervalue;
				selected.level.Maxtemp=Math.max(0, Math.min(100000,selected.level.Maxtemp));
				temp.setText(String.valueOf(selected.level.Maxtemp));
				}
			}
		});
		cycle = new ImageTextButton("10", AssetLoader.Skin_level, "cycle");
		cycle.setPosition(1240, 360);
		cycle.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Maxcycle+=addervalue;
				selected.level.Maxcycle=Math.max(0, Math.min(100000,selected.level.Maxcycle));
				cycle.setText(String.valueOf(selected.level.Maxcycle));
				}
			}
		});
		nrj = new ImageTextButton("10", AssetLoader.Skin_level, "nrj");
		nrj.setPosition(1365, 490);
		nrj.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Maxnrj+=addervalue;
				selected.level.Maxnrj=Math.max(0, Math.min(100000,selected.level.Maxnrj));
				nrj.setText(String.valueOf(selected.level.Maxnrj));
				}
			}
		});
		rayon = new ImageTextButton("10", AssetLoader.Skin_level, "rayon");
		rayon.setPosition(1240, 490);
		rayon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Maxrayon+=addervalue;
				selected.level.Maxrayon=Math.max(0, Math.min(100000,selected.level.Maxrayon));
				rayon.setText(String.valueOf(selected.level.Maxrayon));
				}
			}
		});
		up_cycle = new ImageTextButton("10", AssetLoader.Skin_level, "up_cycle");
		up_cycle.setPosition(1240, AssetLoader.height-250);
		up_cycle.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[1]+=addervalue;
					selected.level.rewards[1]=Math.max(0, Math.min(3,selected.level.rewards[1]));
					up_cycle.setText(String.valueOf(selected.level.rewards[1]));
				}
			}
		});
		up_nrj = new ImageTextButton("10", AssetLoader.Skin_level, "up_nrj");
		up_nrj.setPosition(1365, AssetLoader.height-120);
		up_nrj.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[4]+=addervalue;
					selected.level.rewards[4]=Math.max(0, Math.min(3,selected.level.rewards[4]));
					up_nrj.setText(String.valueOf(selected.level.rewards[4]));
				}
			}
		});
		up_rayon = new ImageTextButton("10", AssetLoader.Skin_level, "up_rayon");
		up_rayon.setPosition(1240, AssetLoader.height-120);
		up_rayon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[3]+=addervalue;
					selected.level.rewards[3]=Math.max(0, Math.min(3,selected.level.rewards[3]));
					up_rayon.setText(String.valueOf(selected.level.rewards[3]));
				}
			}
		});
		up_temp = new ImageTextButton("10", AssetLoader.Skin_level, "up_temp");
		up_temp.setPosition(1365, AssetLoader.height-250);
		up_temp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[2]+=addervalue;
					selected.level.rewards[2]=Math.max(0, Math.min(3,selected.level.rewards[2]));
					up_temp.setText(String.valueOf(selected.level.rewards[2]));
				}
			}
		});
		up = new ImageTextButton("10", AssetLoader.Skin_level, "up");
		up.setPosition(1240, AssetLoader.height-380);
		up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[5]+=addervalue;
					selected.level.rewards[5]=Math.max(0, Math.min(3,selected.level.rewards[5]));
					up.setText(String.valueOf(selected.level.rewards[5]));
				}
			}
		});
		research = new ImageTextButton("10", AssetLoader.Skin_level, "research");
		research.setPosition(1365, AssetLoader.height-380);
		research.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					selected.level.rewards[0]+=addervalue;
					selected.level.rewards[0]=Math.max(0, Math.min(10000,selected.level.rewards[0]));
					research.setText(String.valueOf(selected.level.rewards[0]));
				}
			}
		});
		
		Gdx.app.debug("wirechem-LevelScreen", "Conditions de victoire.");
		Victory = new Objectives(worlds);
		Victory.setVictory(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		Victory.setPosition(1216, 185);
		Victory.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
					int clicked=(int) (x/Victory.size+1);
					int initialclicked=clicked;
					Gdx.app.debug("wirechem-objectives","Element cliqué:"+clicked);
					boolean flag=false;
					for(int i=0;i<Victory.Victory.length;i++) {
						if (Victory.Victory[i]>0 || Victory.Victory[i]<0) {
							clicked--;
							if (clicked<=0) {
								if (Victory.Victory[i]<0)
									Victory.Victory[i]++;
								clicked=i;
								flag=true;
								break;
							}
						}
					}
					if (flag==true) {
						Victory.Victory[clicked]+=addervalue;
						if (clicked<=6)
							Victory.Victory[clicked]=Math.max(0, Math.min(10,Victory.Victory[clicked]));
						else if (clicked==7)
							Victory.Victory[clicked]=Math.max(0, Math.min(2,Victory.Victory[clicked]));
						else if (clicked==8)
							Victory.Victory[clicked]=Math.max(0, Math.min(8,Victory.Victory[clicked]));
						else if (clicked==9)
							Victory.Victory[clicked]=Math.max(0, Math.min(8,Victory.Victory[clicked]));
						else
							Victory.Victory[clicked]=Math.max(0, Math.min(25,Victory.Victory[clicked]));
					}
					else
					{
						for(int i=0;i<Victory.Victory.length;i++)
						{
							if (Victory.Victory[i]<0)
							{
								flag=true;
								Victory.Victory[i]=0;
								continue;
							}
							if (flag && Victory.Victory[i]==0)
							{
								Victory.Victory[i]=-1;
								return;
							}
						}
						if (!flag && initialclicked<=5)
							for(int i=0;i<Victory.Victory.length;i++)
								if (Victory.Victory[i]==0) {
									Victory.Victory[i]=-1;
									break;
								}
					}
				}
			}
		});
		buttonPlay = new TextButton(AssetLoader.language.get("[buttonPlay-levelscreen]"), AssetLoader.Skin_ui);
		buttonPlay.setBounds(1040, 20, 150, 40);
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.setLevel(selected.level.aLevel);
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(worlds));
			}
		});
		group_level=new Group();
		group_level.addActor(TextDescriptive);
		group_level.addActor(buttonPlay);
		group_level.addActor(Next);
		group_level.addActor(Previous);
		group_level.addActor(cout);
		group_level.addActor(tech);
		group_level.addActor(temp);
		group_level.addActor(cycle);
		group_level.addActor(nrj);
		group_level.addActor(rayon);
		group_level.addActor(up_cycle);
		group_level.addActor(up_nrj);
		group_level.addActor(up_rayon);
		group_level.addActor(up_temp);
		group_level.addActor(up);
		group_level.addActor(research);
		group_level.addActor(Victory);
		group_level.addActor(rewardlabel);
		group_level.addActor(goallabel);
		group_level.addActor(ressourcelabel);
		group_level.addActor(handicaplabel);
		group_level.addActor(worldfield);	
		
		//**********************************************************
		//Group Base
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Base.");
		buttonApply = new TextButton(AssetLoader.language.get("[buttonApply-levelscreen]"), AssetLoader.Skin_ui);
		buttonApply.setBounds(1680, 350, 190, 40);
		buttonApply.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				AssetLoader.Datahandler.CloseAll();
				AssetLoader.Datahandler.Attach(Userdata.getModel(),
						Userdata.getUrl());
				AssetLoader.Datahandler.Attach(Statdata.getModel(),
						Statdata.getUrl());
				AssetLoader.Datahandler.Attach(Gamedata.getModel(),
						Gamedata.getUrl());
				if (!AssetLoader.Datahandler.verifyall()) {
					dialog.show(AssetLoader.language.get("[dialog-levelscreen-errorloading]"),stage);
					initlevel();
				} else
					menu();
				if (AssetLoader.Datahandler.stat() == null)
					Statdata.setColor(1f, 0, 0, 1f);
				else
					Statdata.setColor(1f, 1f, 1f, 1f);
				if (AssetLoader.Datahandler.game() == null)
					Gamedata.setColor(1f, 0, 0, 1f);
				else
					Gamedata.setColor(1f, 1f, 1f, 1f);
				if (AssetLoader.Datahandler.user() == null)
					Userdata.setColor(1f, 0, 0, 1f);
				else
					Userdata.setColor(1f, 1f, 1f, 1f);
				Worlddata.Refresh();
				worlds.initialize();
			}
		});
		buttonSave = new TextButton(AssetLoader.language.get("[buttonSave-levelscreen]"), AssetLoader.Skin_ui);
		buttonSave.setBounds(1480, 350, 190, 40);
		buttonSave.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				menu();
				Preference.prefs.putString("userdata", Userdata.getUrl());
				Preference.prefs.putString("gamedata", Gamedata.getUrl());
				Preference.prefs.putString("statdata", Statdata.getUrl());
				Preference.prefs.flush();
				dialog.show(
						AssetLoader.language.get("[dialog-levelscreen-savedatabase]"),stage);
			}
		});
		buttonPlaythis = new TextButton(AssetLoader.language.get("[buttonPlaythis-levelscreen]"), AssetLoader.Skin_ui);
		buttonPlaythis.setBounds(1480, 50, 230, 40);
		buttonPlaythis.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (!AssetLoader.Datahandler.verifyall())
					dialog.show(AssetLoader.language.get("[dialog-levelscreen-errorlevels]"),stage);
				else {
					if (Worlddata.getSelected() == null)
						dialog.show(AssetLoader.language.get("[dialog-levelscreen-errornoworld]"), stage);
					else {
						worlds.set((String) Worlddata.getSelected());
						Preference.prefs.flush();
						play();
					}
				}
			}
		});
		
		String url = "http://evolving.fr/servers/list.xml";
		Statdata = new ServerList(url, Base.datatype.statdata,
				AssetLoader.Skin_ui);
		Statdatalabel = new Label(AssetLoader.language.get("[Statdatalabel-levelscreen]"),
				AssetLoader.Skin_ui, "grey");
		Statdata.setBounds(1480, AssetLoader.height - 250, 420, 150);
		Statdatalabel.setPosition(1480, AssetLoader.height - 100);
		Userdata = new ServerList(url, Base.datatype.userdata,
				AssetLoader.Skin_ui);
		Userdatalabel = new Label(AssetLoader.language.get("[Userdatalabel-levelscreen]"),
				AssetLoader.Skin_ui, "grey");
		Userdata.setBounds(1480, AssetLoader.height - 450, 420, 150);
		Userdatalabel.setPosition(1480, AssetLoader.height - 300);
		Gamedata = new ServerList(url, Base.datatype.gamedata,
				AssetLoader.Skin_ui);
		Gamedatalabel = new Label(AssetLoader.language.get("[Gamedatalabel-levelscreen]"),
				AssetLoader.Skin_ui, "grey");
		Gamedata.setBounds(1480, AssetLoader.height - 650, 420, 150);
		Gamedatalabel.setPosition(1480, AssetLoader.height - 500);
		Worlddata = new Worldlist(AssetLoader.Skin_ui);
		Worlddatalabel = new Label(AssetLoader.language.get("[Worlddatalabel-levelscreen]"), AssetLoader.Skin_ui,
				"grey");
		Worlddata.setBounds(1480, 100, 420, 200);
		Worlddatalabel.setPosition(1480, 300);
		Gamedata.setWorldlist(Worlddata);
		Statdata.Refresh();
		Userdata.Refresh();
		Gamedata.Refresh();
		group_base=new Group();
		group_base.addActor(buttonPlaythis);
		group_base.addActor(buttonSave);
		group_base.addActor(buttonApply);
		group_base.addActor(Statdata);
		group_base.addActor(Userdata);
		group_base.addActor(Gamedata);
		group_base.addActor(Worlddata);
		group_base.addActor(Statdatalabel);
		group_base.addActor(Userdatalabel);
		group_base.addActor(Gamedatalabel);
		group_base.addActor(Worlddatalabel);
		
		//**********************************************************
		//Group Stat
		//**********************************************************
		group_stat=new Group();
		
		//**********************************************************
		//Group Other
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Other.");
		Exit = new ImageButton(AssetLoader.Skin_level, "Exit");
		Exit.setPosition(1110, AssetLoader.height - Exit.getHeight() - 5);
		Exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Exit.getX() < 1210)
					menu();
				else
					Gdx.app.exit();
			}
		});
		logosmall = new ImageButton(AssetLoader.Skin_level, "logosmall");
		logosmall.setPosition(20,AssetLoader.height - 175 + logosmall.getHeight() / 2);
		logosmall.setChecked(worlds.isDebug());
		logosmall.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (!group_init.isVisible())
				if (logosmall.isChecked()) {
					if (buttonLevels != null)
						for (ButtonLevel button : buttonLevels) 
							button.setDisabled(false);
					worlds.ActivateDebug();
					Next.setVisible(!worlds.isRealLastWorld());
					group_debug.setVisible(true);
					group_choose.setVisible(false);
					group_stat.setVisible(false);
					group_base.setVisible(false);
					selectnoone();
					vertibarmod.setVisible(false);
					moveit.setChecked(true);
				}
				else {
					if (buttonLevels != null)
						for (ButtonLevel button : buttonLevels) 
							button.setDisabled(button.level.Locked);
					ResetDragDrog();
					worlds.DesactivateDebug();
					worlds.updateUnlockLevels();
					worlds.setMaxWorldLevel();
					group_debug.setVisible(false);
					group_choose.setVisible(true);
					worlds.setMaxWorldLevel();
					SetButtonStat();
				}
				else
					logosmall.setChecked(false);
			}
		});
		
		group_other=new Group();
		group_other.addActor(Exit);
		group_other.addActor(logosmall);
	
		
		//**********************************************************
		//Group Debug
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Création du groupe Debug.");
		outillabel = new Label(AssetLoader.language.get("[outil-levelscreen]"), AssetLoader.Skin_ui, "variable");
		outillabel.setPosition(1480, AssetLoader.height - 38);
		modelabel = new Label(AssetLoader.language.get("[mode-levelscreen]"), AssetLoader.Skin_ui, "variable");
		modelabel.setPosition(1480, 272);
		savelabel = new Label(AssetLoader.language.get("[save-levelscreen]"), AssetLoader.Skin_ui, "variable");
		savelabel.setPosition(1480, 122);
		initiallabel = new Label(AssetLoader.language.get("[initiallabel-levelscreen]"),AssetLoader.Skin_ui, "variable");
		initiallabel.setPosition(1480, 582);
		temp_orig = new ImageTextButton("", AssetLoader.Skin_level, "temp");
		temp_orig.setPosition(1665, 360);
		temp_orig.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Temp_orig+=addervalue;
				selected.level.Temp_orig=Math.max(0, Math.min(100000,selected.level.Temp_orig));
				temp_orig.setText(String.valueOf(selected.level.Temp_orig));
				}
			}
		});
		cycle_orig = new ImageTextButton("", AssetLoader.Skin_level, "cycle");
		cycle_orig.setPosition(1540, 360);
		cycle_orig.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Cycle_orig+=addervalue;
				selected.level.Cycle_orig=Math.max(0, Math.min(100000,selected.level.Cycle_orig));
				cycle_orig.setText(String.valueOf(selected.level.Cycle_orig));
				}
			}
		});
		nrj_orig = new ImageTextButton("", AssetLoader.Skin_level, "nrj");
		nrj_orig.setPosition(1665, 490);
		nrj_orig.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Nrj_orig+=addervalue;
				selected.level.Nrj_orig=Math.max(0, Math.min(100000,selected.level.Nrj_orig));
				nrj_orig.setText(String.valueOf(selected.level.Nrj_orig));
				}
			}
		});
		rayon_orig = new ImageTextButton("", AssetLoader.Skin_level, "rayon");
		rayon_orig.setPosition(1540, 490);
		rayon_orig.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (worlds.isDebug()) {
				selected.level.Rayon_orig+=addervalue;
				selected.level.Rayon_orig=Math.max(0, Math.min(100000,selected.level.Rayon_orig));
				rayon_orig.setText(String.valueOf(selected.level.Rayon_orig));
				}
			}
		});
		moveit = new ImageButton(AssetLoader.Skin_level, "moveit");	
		moveit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				vertibarmod.setVisible(false);
				ResetDragDrog();
				selectnoone();
			}
		});
		modify = new ImageButton(AssetLoader.Skin_level, "modify");
		modify.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				vertibarmod.setVisible(true);
				ResetDragDrog();
				selectone();
			}
		});
		link = new ImageButton(AssetLoader.Skin_level, "link");	
		link.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				vertibarmod.setVisible(false);
				initDragDrop();
				selectnoone();
			}
		});
		addbutton = new ImageButton(AssetLoader.Skin_level, "level");
		addbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Level level=new Level(
						worlds.getWorld(),
						worlds.getFreeLevel(),
						"Xenoxanax",
						"Xenoxanax",
						"Xx", new int[] { 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, (float)Math.random() * 1000f,
						(float)Math.random() * 750f, 0, 0, new Grid(3, 3), 0, 0, 0, 0, 99999, 99999,
						99999, 99999, "", false, new int[][] {{}});
				worlds.addLevel(level);
				}
			});
		unlocked = new ImageButton(AssetLoader.Skin_level, "unlocked");	
		unlocked.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selected.level.Locked)
					worlds.unLockLevel(selected.level.aLevel);
				else
					worlds.LockLevel(selected.level.aLevel);
			}
		});
		script = new ImageButton(AssetLoader.Skin_level, "script");
		script.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
	
			}
		});
		duplicate = new ImageButton(AssetLoader.Skin_level, "duplicate");
		duplicate.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.dupLevel(selected.level.aLevel);
			}
		});
		deletebutton = new ImageButton(AssetLoader.Skin_level, "eraser");	
		deletebutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selected!=null) {
						Gdx.app.debug("wirechem-LevelScreen", "Destruction du bouton :"+selected.level.aLevel);
						worlds.delLink(selected.level.aLevel, LinkDelMethod.rebase);
						worlds.delLevel(selected.level.aLevel);
						selectone();
					}
				}
			});
		deletelinker = new ImageButton(AssetLoader.Skin_level, "cut");
		deletelinker.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selected!=null) {
						Gdx.app.debug("wirechem-LevelScreen", "Destruction des liens :"+selected.level.aLevel);
						worlds.delLink(selected.level.aLevel, LinkDelMethod.all);
					}
				}
			});
		finisher = new ImageButton(AssetLoader.Skin_level, "finish");
		finisher.setSize(64, 64);
		finisher.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selected!=null) {
					selected.level.Special=!selected.level.Special;
					finisher.setChecked(selected.level.Special);
				}
			}
		});
		signer = new ImageButton(AssetLoader.Skin_level, "add");
		signer.setPosition(1660, 40);	
		signer.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				worlds.PreviousWorld();
				//initDragDrop();
				//InputEvent eventer=new InputEvent();
				//eventer.setType(InputEvent.Type.touchDragged);
				//buttonLevels.first().fire(eventer);
				//dragAndDrop.addSource(lastsource);
				//lastsource.dragStart(eventer, x, y, pointer);
			}
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String whereis=signer.getStyle().up.toString();
				ImageButton.ImageButtonStyle imagebuttonstyle;
				if (whereis.equals("add")) {
					imagebuttonstyle=AssetLoader.Skin_level.get("sub", ImageButton.ImageButtonStyle.class);
					addervalue=-Math.abs(addervalue);
				}
				else
				{
					imagebuttonstyle=AssetLoader.Skin_level.get("add", ImageButton.ImageButtonStyle.class);
					addervalue=Math.abs(addervalue);
				}
				signer.setStyle(imagebuttonstyle);
			}
		});
		adder = new ImageButton(AssetLoader.Skin_level, "add1");
		adder.setPosition(1720, 40);	
		adder.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String whereis=adder.getStyle().up.toString();
				if (whereis.equals("add1"))	
					addervalue=(int)(10*Math.signum(addervalue));
				else if (whereis.equals("add10"))
					addervalue=(int)(100*Math.signum(addervalue));
				else if (whereis.equals("add100"))	
					addervalue=(int)(1000*Math.signum(addervalue));
				else if (whereis.equals("add1000"))	
					addervalue=(int)(10000*Math.signum(addervalue));
				else
					addervalue=1;
				ImageButton.ImageButtonStyle imagebuttonstyle=AssetLoader.Skin_level.get("add"+String.valueOf(Math.abs(addervalue)), ImageButton.ImageButtonStyle.class);
				adder.setStyle(imagebuttonstyle);
			}
		});
		databaseSave = new ImageButton(AssetLoader.Skin_level, "database-save");
		databaseSave.setPosition(1820, 40);	
		databaseSave.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.save(worlds.getName());
			}
		});
		vertibarmod=new VerticalGroup();
		vertibarmod.setPosition(1550, AssetLoader.height-100);
		vertibarmod.center();
		vertibarmod.space(20f);
		vertibarmod.addActor(unlocked);
		vertibarmod.addActor(finisher);
		vertibarmod.addActor(deletebutton);	
		vertibarmod.addActor(duplicate);
		vertibarmod.addActor(deletelinker);
		vertibarmod.addActor(script);
		vertibarmod.setVisible(false);
		
		vertibar=new HorizontalGroup();
		vertibar.setPosition(1565, 215);
		vertibar.center();
		vertibar.space(20f);
		vertibar.addActor(moveit);
		vertibar.addActor(link);
		vertibar.addActor(modify);		
		vertibar.addActor(addbutton);
		modifbar=new ButtonGroup<Button>();
		modifbar.setMaxCheckCount(1);
		modifbar.setMinCheckCount(1);
		modifbar.add(moveit);
		modifbar.add(link);
		modifbar.add(modify);
		modifbar.add(addbutton);
		moveit.setChecked(true);
		
		group_debug=new Group();
		group_debug.addActor(vertibar);
		group_debug.addActor(vertibarmod);
		group_debug.addActor(adder);
		group_debug.addActor(signer);
		group_debug.addActor(databaseSave);
		group_debug.addActor(cycle_orig);
		group_debug.addActor(temp_orig);
		group_debug.addActor(rayon_orig);
		group_debug.addActor(nrj_orig);
		group_debug.addActor(initiallabel);
		group_debug.addActor(outillabel);
		group_debug.addActor(modelabel);
		group_debug.addActor(savelabel);			
		
		//**********************************************************
		Gdx.app.debug("wirechem-LevelScreen", "Affichage du menu.");
		if (worlds.getWorld() != -1)
			level();
		else
			menu();
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		Renderer.render(delta, runTime);
		if (worlds.getWorld()>-1)
			worldfield.getStyle().fontColor.set(AssetLoader.Levelcolors[worlds.getWorld()]);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width, height);
	}

	@Override
	public void show() {
		Gdx.app.log("wirechem-LevelScreen", "***** Affichage du choix des mondes & niveaux.");
		table.setFillParent(true);
		stage.addActor(worlds);
		stage.addActor(group_init);
		stage.addActor(group_stat);
		stage.addActor(group_level);
		stage.addActor(group_base);
		stage.addActor(group_debug);
		stage.addActor(group_choose);
		stage.addActor(group_other);
		Gdx.input.setInputProcessor(stage);
		Gdx.app.debug("wirechem-LevelScreen", "Début dans la bande son \'intro\'");
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
	
	public void selectone() {
			if (buttonLevels.size>0) 
				showlevel(buttonLevels.first());
	}
	
	public void selectnoone() {
		for(ButtonLevel button: buttonLevels) 
			if (button!=null) {
				selected=button;
				selected.setChecked(false);
			}
		selected=null;
		showlevel(null);
		return;
	}

	public void showlevel(ButtonLevel button) {
		if 	(button!=null) {
			Gdx.app.debug("wirechem-LevelScreen", "Reading button "	+ button.level.Name);
			worldfield.setText(button.level.Name);
			TextDescriptive.setText(button.level.Description);
			TextDescriptive.setVisible(true);
			Victory.setVictory(button.level.Victory_orig);
			button.setChecked(true);
			buttonPlay.setVisible(true);
			worldfield.setVisible(true);
			if (worlds.isDebug()) {
				unlocked.setChecked(!button.level.Locked);				
				finisher.setChecked(button.level.Special);
				worldfield.setDisabled(false);
				TextDescriptive.setDisabled(false);
				cycle_orig.setText(String.valueOf(button.level.Cycle_orig));
				cycle_orig.setVisible(true);
				temp_orig.setText(String.valueOf(button.level.Temp_orig));
				temp_orig.setVisible(true);
				nrj_orig.setText(String.valueOf(button.level.Nrj_orig));
				nrj_orig.setVisible(true);
				rayon_orig.setText(String.valueOf(button.level.Rayon_orig));
				rayon_orig.setVisible(true);
				initiallabel.setVisible(true);
				
			}
			else {
				worldfield.setDisabled(true);
				TextDescriptive.setDisabled(true);
				cycle_orig.setVisible(false);
				temp_orig.setVisible(false);
				nrj_orig.setVisible(false);
				rayon_orig.setVisible(false);
				initiallabel.setVisible(false);
			}
		}
		else
		{
			Gdx.app.debug("wirechem-LevelScreen", "Efface les informations");
			buttonPlay.setVisible(false);
			TextDescriptive.setVisible(false);
			cycle_orig.setVisible(false);
			temp_orig.setVisible(false);
			nrj_orig.setVisible(false);
			rayon_orig.setVisible(false);
			buttonPlay.setVisible(false);
			worldfield.setVisible(false);
			initiallabel.setVisible(false);
		}
		if (button!=null && (worlds.isDebug() || button.level.aWorld>0)) {
			handicaplabel.setVisible(true);
		} else
			handicaplabel.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.Maxcycle < 99999 && button.level.Maxcycle > 0)) {
			cycle.setText(String.valueOf(button.level.Maxcycle));
			cycle.setVisible(true);
		} else
			cycle.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.Maxtemp < 99999 && button.level.Maxtemp > 0)) {
			temp.setText(String.valueOf(button.level.Maxtemp));
			temp.setVisible(true);
		} else
			temp.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.Maxnrj < 99999 && button.level.Maxnrj > 0)) {
			nrj.setText(String.valueOf(button.level.Maxnrj));
			nrj.setVisible(true);
		} else
			nrj.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.Maxrayon < 99999 && button.level.Maxrayon > 0)) {
			rayon.setText(String.valueOf(button.level.Maxrayon));
			rayon.setVisible(true);
		} else
			rayon.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.Cout_orig > 0)) {
			Victory.setVisible(true);
			goallabel.setVisible(true);
			ressourcelabel.setVisible(true);
			cout.setText(String.valueOf(button.level.Cout_orig));
			cout.setVisible(true);
		} else {
			cout.setVisible(false);
			Victory.setVisible(false);
			goallabel.setVisible(false);
			ressourcelabel.setVisible(false);
		}
		if (button!=null && (worlds.isDebug() || button.level.Tech >= 1)) {
			rewardlabel.setVisible(true);
			tech.setText(String.valueOf(button.level.Tech));
			tech.setVisible(true);
		} else {
			tech.setVisible(false);
			rewardlabel.setVisible(false);
		}
		if (button!=null && (worlds.isDebug() || button.level.rewards[0] >= 1)) {
			research.setText(String.valueOf(button.level.rewards[0]));
			research.setVisible(true);
		} else
			research.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.rewards[2] >= 1)) {
			up_temp.setText(String.valueOf(button.level.rewards[2]));
			up_temp.setVisible(true);
		} else
			up_temp.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.rewards[4] >= 1)) {
			up_nrj.setText(String.valueOf(button.level.rewards[4]));
			up_nrj.setVisible(true);
		} else
			up_nrj.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.rewards[3] >= 1)) {
			up_rayon.setText(String.valueOf(button.level.rewards[3]));
			up_rayon.setVisible(true);
		} else
			up_rayon.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.rewards[1] >= 1)) {
			up_cycle.setText(String.valueOf(button.level.rewards[1]));
			up_cycle.setVisible(true);
		} else
			up_cycle.setVisible(false);
		if (button!=null && (worlds.isDebug() || button.level.rewards[5] >= 1)) {
			up.setText(String.valueOf(button.level.rewards[5]));
			up.setVisible(true);
		} else
			up.setVisible(false);
		if (selected != null)
			selected.setChecked(false);
		selected = button;
	}
}
