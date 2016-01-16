package fr.evolving.UI;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;

public class Worldlist extends List {

	public Worldlist(Skin skin) {
		super(skin);
		// TODO Auto-generated constructor stub
	}

	public void Refresh() {
		Array<String> worlds = null;
		if (AssetLoader.Datahandler.game() != null)
			worlds = AssetLoader.Datahandler.game().getworlds();
		if (worlds == null)
			worlds = new Array<String>();
		this.setItems(worlds);
	}
}
