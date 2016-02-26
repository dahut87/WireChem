package fr.evolving.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Worlds;

public class SavingWindow extends Window {

	private List selSaved;
	private Worlds worlds;
	ChangeEvent event;

	public SavingWindow(Worlds worlds) {
		super(AssetLoader.language.get("[winSave-gamescreen]"), AssetLoader.Skin_ui);
		this.worlds=worlds;
		this.add(savingPanel()).row();
		this.setColor(1, 1, 1, 0.8f);
		this.setVisible(false);
		this.pack();
		this.setBounds(50, 100, 250, 450);
	}
	
	private void onchanged() {
		ChangeEvent event=new ChangeEvent();
		event.setTarget(this);
		event.setListenerActor(this);	
		event.setStage(this.getStage());
		if (event.getStage()!=null) 
			this.fire(event);
	}

	private Table savingPanel() {
		Table table = new Table();
		table.pad(10, 10, 0, 10);
		selSaved = new List(AssetLoader.Skin_ui);
		selSaved.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (this.getTapCount() > 1) {
					worlds.ReadGrid(selSaved.getSelectedIndex());
					onchanged();
				}
			}
		});
		ScrollPane scroll = new ScrollPane(selSaved);
		table.add(scroll).width(250).height(440).row();
		return table;
	}

	public void refresh() {
		Array<String> items = worlds.ViewGrids();
		if (items != null)
			selSaved.setItems(items);
	}


}