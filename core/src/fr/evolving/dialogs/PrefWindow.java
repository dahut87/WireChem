package fr.evolving.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.Preference;
import fr.evolving.screens.GameScreen.adaptation;
import fr.evolving.screens.GameScreen.quality;
import fr.evolving.screens.GameScreen.resolutions;

public class PrefWindow extends Window {

	private CheckBox SetSound, SetVsynch, SetFullscreen, SetAnimation, Settuto,
	Setdebog, Setgrid, Setrefresh;
	private Slider SetEffectvolume, SetMusicvolume;
	private TextButton Setcancel, Setsave;
	private SelectBox<resolutions> selResolution;
	private SelectBox<quality> selTexturequal;
	private SelectBox<adaptation> selAdaptscreen;
	private ImageButton Setflag;
	private WarningDialog dialog;


	public PrefWindow() {
		super(AssetLoader.language.get("[winOptions-gamescreen]"), AssetLoader.Skin_ui);
		// TODO Auto-generated constructor stub
		this.add(SettingsVideo()).row();
		this.add(SettingsAudio()).row();
		this.add(SettingsOther()).row();
		this.add(SettingsButtons()).pad(10, 0, 10, 0);
		this.setColor(1, 1, 1, 0.8f);
		this.setVisible(false);
		this.pack();
		this.setPosition(100, 250);
		dialog = new WarningDialog(AssetLoader.Skin_ui);
	}

	public void refresh() {
		SetFullscreen.setChecked(Preference.prefs.getBoolean("Fullscreen"));
		SetSound.setChecked(Preference.prefs.getBoolean("Sound"));
		Settuto.setChecked(Preference.prefs.getBoolean("Tutorial"));
		SetVsynch.setChecked(Preference.prefs.getBoolean("VSync"));
		Setrefresh.setChecked(Preference.prefs.getBoolean("Refresh"));
		SetAnimation.setChecked(Preference.prefs.getBoolean("Animation"));
		Setflag.setChecked(Preference.prefs.getBoolean("Language"));
		SetEffectvolume.setValue(Preference.prefs.getFloat("Effect"));
		Setgrid.setChecked(Preference.prefs.getBoolean("Grid"));		
		SetMusicvolume.setValue(Preference.prefs.getFloat("Music"));
		selResolution.setSelectedIndex(Preference.prefs.getInteger("Resolution"));
		selAdaptscreen.setSelectedIndex(Preference.prefs.getInteger("Adaptation"));
		selTexturequal.setSelectedIndex(Preference.prefs.getInteger("Quality"));
		Setdebog.setChecked(Preference.prefs.getInteger("log") == Gdx.app.LOG_DEBUG);
	}

	public void writepref() {
		Preference.prefs.putInteger("ResolutionX", selResolution.getSelected().getResolutionX());
		Preference.prefs.putInteger("ResolutionY", selResolution.getSelected().getResolutionY());
		Preference.prefs.putInteger("Resolution",  selResolution.getSelectedIndex());
		Preference.prefs.putBoolean("Fullscreen", SetFullscreen.isChecked());
		Preference.prefs.putBoolean("Sound", SetSound.isChecked());
		Preference.prefs.putBoolean("Grid", Setgrid.isChecked());
		Preference.prefs.putBoolean("Tutorial", Settuto.isChecked());
		Preference.prefs.putBoolean("VSync", SetVsynch.isChecked());
		Preference.prefs.putBoolean("Refresh", Setrefresh.isChecked());
		Preference.prefs.putBoolean("Animation", SetAnimation.isChecked());
		Preference.prefs.putBoolean("Language", Setflag.isChecked());
		Preference.prefs.putFloat("Effect", SetEffectvolume.getValue());
		Preference.prefs.putFloat("Music", SetMusicvolume.getValue());
		Preference.prefs.putInteger("Adaptation",selAdaptscreen.getSelectedIndex());
		Preference.prefs.putInteger("Quality",selTexturequal.getSelectedIndex());
		if (Setdebog.isChecked())
			Preference.prefs.putInteger("log", Gdx.app.LOG_DEBUG);
		else
			Preference.prefs.putInteger("log", Gdx.app.LOG_INFO);
		Preference.prefs.flush();
	}

	private Table SettingsOther() {
		Table table = new Table();
		table.pad(10, 10, 0, 10);
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-Game]"), AssetLoader.Skin_level, "Fluoxetine-25",Color.ORANGE)).colspan(3);
		table.row();
		table.columnDefaults(0).padRight(10);
		table.columnDefaults(1).padRight(10);
		Settuto = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-tuto]"), AssetLoader.Skin_ui);
		table.add(Settuto).left();
		table.row();
		Setdebog = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-debug]"), AssetLoader.Skin_ui);
		table.add(Setdebog).left();
		table.row();
		Setgrid = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-grid]"),AssetLoader.Skin_ui);
		table.add(Setgrid).left();
		table.row();
		Setrefresh = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-refresh]"),AssetLoader.Skin_ui);
		table.add(Setrefresh).left();
		table.row();
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-language]"), AssetLoader.Skin_ui,"default-font", Color.WHITE)).left();
		Setflag = new ImageButton(AssetLoader.Skin_level, "Setflag");
		table.add(Setflag);
		table.row();
		return table;
	}

	private Table SettingsVideo() {
		Table table = new Table();
		table.pad(10, 10, 0, 10);
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-Video]"),AssetLoader.Skin_level, "Fluoxetine-25",Color.ORANGE)).colspan(3);
		table.row();
		table.columnDefaults(0).padRight(10);
		table.columnDefaults(1).padRight(10);

		SetVsynch = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-sync]"),AssetLoader.Skin_ui);
		table.add(SetVsynch).left();
		Table tablev1 = new Table();
		tablev1.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-resolution]"), AssetLoader.Skin_ui, "default-font",Color.WHITE)).left().row();
		selResolution = new SelectBox<resolutions>(AssetLoader.Skin_ui);
		selResolution.setItems(resolutions.values());
		tablev1.add(selResolution).left().row();
		table.add(tablev1).left();
		table.row();

		SetFullscreen = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-full]"), AssetLoader.Skin_ui);
		table.add(SetFullscreen).left();
		Table tablev2 = new Table();
		tablev2.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-fill]"), AssetLoader.Skin_ui,"default-font", Color.WHITE)).left().row();
		selAdaptscreen = new SelectBox<adaptation>(AssetLoader.Skin_ui);
		selAdaptscreen.setItems(adaptation.values());
		tablev2.add(selAdaptscreen).left().row();
		table.add(tablev2).left();
		table.row();

		Table tablev3 = new Table();
		tablev3.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-quality]"), AssetLoader.Skin_ui,	"default-font", Color.WHITE)).left().row();
		SetAnimation = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-animation]"),AssetLoader.Skin_ui);
		table.add(SetAnimation).left();
		selTexturequal = new SelectBox<quality>(AssetLoader.Skin_ui);
		selTexturequal.setItems(quality.values());
		tablev3.add(selTexturequal).left().row();
		table.add(tablev3).left();
		table.row();
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			Graphics.DisplayMode[] modes = Gdx.graphics.getDisplayModes();
			for (resolutions res : resolutions.values()) {
				res.SetFull(false);
				for (DisplayMode mode : modes) {
					if (res.getResolutionX() == mode.width && res.getResolutionY() == mode.height)
						res.SetFull(true);
				}
			}
			Vector2 maxres = Preference.getmaxresolution();
			resolutions.rmax.SetFull(true);
			resolutions.rmax.setResolutionX((int) maxres.x);
			resolutions.rmax.setResolutionY((int) maxres.y);
		} else
			selResolution.setDisabled(true);
		return table;
	}

	private Table SettingsAudio() {
		Table table = new Table();
		table.pad(10, 10, 0, 10);
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-Audio]"), AssetLoader.Skin_level, "Fluoxetine-25",	Color.ORANGE)).colspan(3);
		table.row();
		table.columnDefaults(0).padRight(10);
		table.columnDefaults(1).padRight(10);
		SetSound = new CheckBox(AssetLoader.language.get("[WinOptions-gamescreen-sound]"), AssetLoader.Skin_ui);
		table.add(SetSound).left();
		table.row();
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-effect]"), AssetLoader.Skin_ui));
		SetEffectvolume = new Slider(0.0f, 1.0f, 0.1f, false,AssetLoader.Skin_ui);
		table.add(SetEffectvolume).left();
		table.row();
		table.add(new Label(AssetLoader.language.get("[WinOptions-gamescreen-music]"), AssetLoader.Skin_ui));
		SetMusicvolume = new Slider(0.0f, 1.0f, 0.1f, false,AssetLoader.Skin_ui);
		table.add(SetMusicvolume).left();
		table.row();
		return table;
	}

	private void onSaveClicked() {
		this.setVisible(false);
		writepref();
		dialog.Show(AssetLoader.language.get("[dialog-gamescreen-preference]"),	this.getStage());
	}

	private void onCancelClicked() {
		this.setVisible(false);
	}

	private Table SettingsButtons() {
		Table table = new Table();
		table.pad(10, 10, 0, 10);
		Setsave = new TextButton(AssetLoader.language.get("[WinOptions-gamescreen-save]"), AssetLoader.Skin_ui);
		table.add(Setsave).padRight(30);
		Setsave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSaveClicked();
			}
		});
		Setcancel = new TextButton(AssetLoader.language.get("[WinOptions-gamescreen-cancel]"), AssetLoader.Skin_ui);
		table.add(Setcancel);
		Setcancel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onCancelClicked();
			}
		});
		return table;
	}


}