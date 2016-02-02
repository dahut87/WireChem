package fr.evolving.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.evolving.UI.ButtonLevel;
import fr.evolving.UI.Objectives;
import fr.evolving.UI.ServerList;
import fr.evolving.UI.WarnDialog;
import fr.evolving.UI.Worldlist;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.InitWorlds;
import fr.evolving.assets.Preference;
import fr.evolving.automata.Level;
import fr.evolving.automata.Worlds;
import fr.evolving.automata.Worlds.State;
import fr.evolving.database.Base;
import fr.evolving.renderers.LevelRenderer;

public class LevelScreen implements Screen {
	public ButtonLevel[] buttonLevels;
	private LevelRenderer Renderer;
	private float runTime;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
	private Stage stage;
	private Table table;
	private WarnDialog dialog;
	private ImageButton Previous, Next, Exit;
	public ImageButton logosmall;
	public Image MenuSolo, MenuMulti, MenuScenario;
	private ImageTextButton cout, tech, cycle, temp, rayon, nrj;
	private TextButton buttonConnect, buttonPlay, buttonStat, buttonSave,
	buttonApply, buttonPlaythis;
	private ServerList Statdata, Userdata, Gamedata;
	private Worldlist Worlddata;
	private Label Statdatalabel, Userdatalabel, Gamedatalabel, Worlddatalabel;
	private TextArea TextDescriptive;
	public Worlds worlds;
	private Objectives Victory;
	public ButtonLevel selected;


	public void play() {
		if (worlds.getState()!=State.notloaded && worlds.getState()!=State.databasefailed) {
			if (worlds.getWorld() < 0)
				worlds.setMaxWorldLevel();
			Gdx.app.debug(getClass().getSimpleName(),"Afficher derniere réalisation, monde :"+worlds.getWorld()+" niveau:"+worlds.getLevel());
			worlds.Forcereload();
		}
	}

	public void menu() {
		selected = null;
		cout.setVisible(false);
		tech.setVisible(false);
		cycle.setVisible(false);
		temp.setVisible(false);
		rayon.setVisible(false);
		nrj.setVisible(false);
		Previous.setVisible(false);
		Next.setVisible(false);
		Victory.setVisible(false);
		Exit.setPosition(1820, AssetLoader.height - 100);
		buttonPlay.setVisible(false);
		TextDescriptive.setVisible(false);
		MenuSolo.setVisible(true);
		MenuMulti.setVisible(true);
		MenuScenario.setVisible(true);
		buttonConnect.setVisible(false);
		buttonStat.setVisible(false);
		Statdata.setVisible(false);
		Userdata.setVisible(false);
		Gamedata.setVisible(false);
		Statdatalabel.setVisible(false);
		Userdatalabel.setVisible(false);
		Gamedatalabel.setVisible(false);
		Worlddatalabel.setVisible(false);
		buttonPlaythis.setVisible(false);
		Worlddata.setVisible(false);
		buttonSave.setVisible(false);
		buttonApply.setVisible(false);
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
		buttonPlay.setVisible(false);
		TextDescriptive.setVisible(false);
		cout.setVisible(false);
		tech.setVisible(false);
		cycle.setVisible(false);
		temp.setVisible(false);
		rayon.setVisible(false);
		nrj.setVisible(false);
		Previous.setVisible(false);
		Next.setVisible(false);
		Victory.setVisible(false);
		if (buttonLevels != null)
			for (int j = 0; j < 10; j++)
				if (buttonLevels[j] != null) {
					buttonLevels[j].remove();
					buttonLevels[j] = null;
				}
	}

	public void level() {
		Exit.setPosition(1110, AssetLoader.height - Exit.getHeight() - 5);
		MenuSolo.setVisible(false);
		MenuMulti.setVisible(false);
		MenuScenario.setVisible(false);
		buttonConnect.setVisible(true);
		buttonStat.setVisible(true);
		SetButtonStat();
		play();
	}

	public void SetButtonConnect() {
		buttonStat.setColor(1f, 1f, 1f, 1f);
		buttonConnect.setColor(1f, 0, 0, 1f);
		Statdata.setVisible(true);
		Userdata.setVisible(true);
		Gamedata.setVisible(true);
		Statdatalabel.setVisible(true);
		Userdatalabel.setVisible(true);
		Gamedatalabel.setVisible(true);
		buttonSave.setVisible(true);
		buttonApply.setVisible(true);
		Worlddatalabel.setVisible(true);
		Worlddata.setVisible(true);
		buttonPlaythis.setVisible(true);
	}

	public void SetButtonStat() {
		buttonConnect.setColor(1f, 1f, 1f, 1f);
		buttonStat.setColor(1f, 0, 0, 1f);
		buttonStat.setColor(1f, 0, 0, 1f);
		Statdata.setVisible(false);
		Userdata.setVisible(false);
		Gamedata.setVisible(false);
		Statdatalabel.setVisible(false);
		Userdatalabel.setVisible(false);
		Gamedatalabel.setVisible(false);
		buttonSave.setVisible(false);
		buttonApply.setVisible(false);
		Worlddatalabel.setVisible(false);
		Worlddata.setVisible(false);
		buttonPlaythis.setVisible(false);
	}

	public void loadWorld() {
		int i = 0;
		if (buttonLevels != null)
			for (int j = 0; j < 10; j++) {
				if (buttonLevels[j] != null) {
					buttonLevels[j].remove();
					buttonLevels[j] = null;
				}
			}
		buttonLevels = null;
		buttonLevels = new ButtonLevel[10];
		for (Level level : worlds.getLevels()) {
			if (level != null) {
				if (level.Name.isEmpty())
					level.Name=AssetLoader.language.get("[level"+(level.aWorld+1)+"/"+(level.aLevel+1)+"-name]");
				if (level.Description.isEmpty())
					level.Description=AssetLoader.language.get("[level"+(level.aWorld+1)+"/"+(level.aLevel+1)+"-desc]");		
				buttonLevels[i] = new ButtonLevel(level, AssetLoader.ratio, true);
				if (worlds.isDebug()) buttonLevels[i].setDisabled(false);
				Gdx.app.debug(getClass().getSimpleName(), "Ajout du niveau :"+ level.Name + " N°" + String.valueOf(level.aLevel));
				buttonLevels[i++].addListener(new ClickListener() {
					@Override
					public void enter(InputEvent event, float x, float y,
							int pointer, Actor fromActor) {
						ButtonLevel abutton = (ButtonLevel) event
								.getListenerActor();
						Gdx.app.debug(event.getListenerActor().toString(),"Enter button ");
						if (!abutton.isChecked() && (!abutton.level.Locked || worlds.isDebug()))
							showlevel(abutton);
					}

					public void exit(InputEvent event, float x, float y,
							int pointer, Actor fromActor) {
						ButtonLevel abutton = (ButtonLevel) event
								.getListenerActor();
						Gdx.app.debug(event.getListenerActor().toString(),"Enter button ");
						if (!abutton.isChecked() && (!abutton.level.Locked || worlds.isDebug()))
							showlevel(abutton);
					}

					public void touchDragged(InputEvent event, float x,
							float y, int pointer) {
						ButtonLevel abutton = (ButtonLevel) event.getListenerActor();
						if (worlds.isDebug()) {
							abutton.setPosition(event.getStageX() - 56,	event.getStageY() - 20);
						}
					}
				});
			}
		}
		for (int j = 0; j < 10; j++) {
			if (buttonLevels[j] != null) {
				stage.addActor(buttonLevels[j]);
			}
		}
		/*Gdx.app.debug(getClass().getSimpleName(), "Mise en place du level 0.");
		buttonLevels[0].setChecked(true); 
		showlevel(buttonLevels[0]);*/
	}

	public LevelScreen(Worlds aworlds) {
		this.worlds = aworlds;
		worlds.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (worlds.getState()!=Worlds.State.notloaded && worlds.getWorld()>=0)
				{
					LevelScreen.this.loadWorld();
					if (buttonLevels!=null)
						for (int j = 0; j < 10; j++)
						{
							if (buttonLevels[j]!=null) {
								buttonLevels[j].setChecked(false);
								if (worlds.getInformations()!=null && buttonLevels[j].level.id == worlds.getInformations().id) {
									selected=buttonLevels[j];
									break;
								}
							}
						}
					if (worlds.getInformations()==null)
						selected=buttonLevels[0];
					selected.setChecked(true);
					showlevel(selected);
					Previous.setVisible(!worlds.isFirstWorld());
					if (worlds.isDebug())
						Next.setVisible(!worlds.isRealLastWorld());
					else
						Next.setVisible(!worlds.isLastWorld());
					buttonPlay.setVisible(true);
					TextDescriptive.setVisible(true);
				}
				else {
					Previous.setVisible(false);
					Next.setVisible(false);
					buttonPlay.setVisible(false);
					TextDescriptive.setVisible(false);
				}
			}
		});
		Gdx.app.debug(getClass().getSimpleName(),"Création des elements primordiaux du screen (stage, renderer, table)");
		stage = new Stage(AssetLoader.viewport);
		table = new Table();
		Renderer = new LevelRenderer(this);
		dialog = new WarnDialog(AssetLoader.Skin_ui);
		Gdx.app.debug(getClass().getSimpleName(), "Mise en place du timer.");
		ScrollTimer = new Timer();
		ScrollTask = new TimerTask() {
			@Override
			public void run() {
				Renderer.evolve();
			}
		};
		ScrollTimer.scheduleAtFixedRate(ScrollTask, 0, 30);
		Gdx.app.debug(getClass().getSimpleName(), "Création du menu.");
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
		Gdx.app.debug(getClass().getSimpleName(), "Création des boutons.");
		logosmall = new ImageButton(AssetLoader.Skin_level, "logosmall");
		logosmall.setPosition(20,AssetLoader.height - 175 + logosmall.getHeight() / 2);
		logosmall.setChecked(worlds.isDebug());
		logosmall.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (logosmall.isChecked()) {
					if (buttonLevels != null)
						for (int j = 0; j < 10; j++) {
							if (buttonLevels[j] != null)
								buttonLevels[j].setDisabled(false);
						}
					worlds.ActivateDebug();
					Next.setVisible(!worlds.isRealLastWorld());
				}
				else {
					if (buttonLevels != null)
						for (int j = 0; j < 10; j++) {
							if (buttonLevels[j] != null) 
								buttonLevels[j].setDisabled(buttonLevels[j].level.Locked);
							}
					worlds.DesactivateDebug();
					worlds.updateUnlockLevels();
					worlds.setMaxWorldLevel();
					loadWorld();
					
				}
			}
		});
		TextDescriptive = new TextArea("Descriptif", AssetLoader.Skin_level,
				"Descriptif");
		TextDescriptive.setBounds(15, 15, 1185, 110);
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
					dialog.Show(AssetLoader.language.get("[dialog-levelscreen-errorloading]"),stage);
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
				dialog.Show(
						AssetLoader.language.get("[dialog-levelscreen-savedatabase]"),stage);
			}
		});
		buttonConnect = new TextButton(AssetLoader.language.get("[buttonConnect-levelscreen]"), AssetLoader.Skin_ui);
		buttonConnect.setBounds(1480, AssetLoader.height - 60, 190, 40);
		buttonConnect.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (!Statdata.isVisible())
					SetButtonConnect();
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
		buttonStat = new TextButton(AssetLoader.language.get("[buttonStat-levelscreen]"), AssetLoader.Skin_ui);
		buttonStat.setBounds(1710, AssetLoader.height - 60, 190, 40);
		buttonStat.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Statdata.isVisible())
					SetButtonStat();
			}
		});
		buttonPlaythis = new TextButton(AssetLoader.language.get("[buttonPlaythis-levelscreen]"), AssetLoader.Skin_ui);
		buttonPlaythis.setBounds(1480, 50, 230, 40);
		buttonPlaythis.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (!AssetLoader.Datahandler.verifyall())
					dialog.Show(AssetLoader.language.get("[dialog-levelscreen-errorlevels]"),stage);
				else {
					if (Worlddata.getSelected() == null)
						dialog.Show(AssetLoader.language.get("[dialog-levelscreen-errornoworld]"), stage);
					else {
						worlds.set((String) Worlddata.getSelected());
						Preference.prefs.flush();
						play();
					}
				}
			}
		});
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
		Next = new ImageButton(AssetLoader.Skin_level, "Next");
		Next.setPosition(1030, 185);
		Next.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.NextWorld();
				Gdx.app.debug(event.getListenerActor().toString(),
						"World:" + String.valueOf(worlds.getWorld()) + " Maxworld:"
								+ String.valueOf(worlds.getMaxWorlds()));
			}
		});
		Previous = new ImageButton(AssetLoader.Skin_level, "Previous");
		Previous.setPosition(1110, 185);
		Previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				worlds.PreviousWorld();
				Gdx.app.debug(event.getListenerActor().toString(),
						"World:" + String.valueOf(worlds.getWorld()) + " Maxworld:"
								+ String.valueOf(worlds.getMaxWorlds()));
			}
		});
		cout = new ImageTextButton("5", AssetLoader.Skin_level, "cout");
		cout.setPosition(1250, 48);
		tech = new ImageTextButton("10", AssetLoader.Skin_level, "tech");
		tech.setPosition(1365, 48);
		temp = new ImageTextButton("10", AssetLoader.Skin_level, "temp");
		temp.setPosition(1365, 360);
		cycle = new ImageTextButton("10", AssetLoader.Skin_level, "cycle");
		cycle.setPosition(1250, 360);
		nrj = new ImageTextButton("10", AssetLoader.Skin_level, "nrj");
		nrj.setPosition(1365, 490);
		rayon = new ImageTextButton("10", AssetLoader.Skin_level, "rayon");
		rayon.setPosition(1250, 490);
		Gdx.app.debug(getClass().getSimpleName(), "Conditions de victoire.");
		Victory = new Objectives();
		Victory.setVictory(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		Victory.setPosition(1216, 185);
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
		Gdx.app.debug(getClass().getSimpleName(), "Affichage du menu.");
		if (worlds.getWorld() != -1)
			level();
		else
			menu();
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
		AssetLoader.viewport.update(width, height);
	}

	@Override
	public void show() {
		Gdx.app.log("*****", "Affichage du choix des mondes & niveaux.");
		table.setFillParent(true);
		stage.addActor(worlds);
		stage.addActor(MenuSolo);
		stage.addActor(MenuMulti);
		stage.addActor(MenuScenario);
		stage.addActor(TextDescriptive);
		stage.addActor(buttonPlaythis);
		stage.addActor(Exit);
		stage.addActor(Next);
		stage.addActor(buttonApply);
		stage.addActor(buttonSave);
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
		stage.addActor(Worlddata);
		stage.addActor(Worlddatalabel);
		Gdx.input.setInputProcessor(stage);
		Gdx.app.debug("AssetLoader", "Début dans la bande son \'intro\'");
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
		Gdx.app.debug(getClass().getSimpleName(), "Reading button "
				+ button.level.Name);
		TextDescriptive.setText(button.level.Description);
		if (button.level.Maxcycle < 99999 && button.level.Maxcycle > 0) {
			cycle.setText(String.valueOf(button.level.Maxcycle));
			cycle.setVisible(true);
		} else
			cycle.setVisible(false);
		if (button.level.Maxtemp < 99999 && button.level.Maxtemp > 0) {
			temp.setText(String.valueOf(button.level.Maxtemp));
			temp.setVisible(true);
		} else
			temp.setVisible(false);
		if (button.level.Maxnrj < 99999 && button.level.Maxnrj > 0) {
			nrj.setText(String.valueOf(button.level.Maxnrj));
			nrj.setVisible(true);
		} else
			nrj.setVisible(false);
		if (button.level.Maxrayon < 99999 && button.level.Maxrayon > 0) {
			rayon.setText(String.valueOf(button.level.Maxrayon));
			rayon.setVisible(true);
		} else
			rayon.setVisible(false);
		if (button.level.Cout > 0) {
			cout.setText(String.valueOf(button.level.Cout));
			cout.setVisible(true);
		} else
			cout.setVisible(false);
		if (button.level.Tech >= 1) {
			tech.setText(String.valueOf(button.level.Tech));
			tech.setVisible(true);
		} else
			tech.setVisible(false);
		Victory.setVisible(button.level.Cout > 0);
		Victory.setVictory(button.level.Victory);
		if (selected != null)
			selected.setChecked(false);
		selected = button;
		button.setChecked(true);
	}

}
