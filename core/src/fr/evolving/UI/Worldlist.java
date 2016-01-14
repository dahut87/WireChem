package fr.evolving.UI;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.Preference;

public class Worldlist extends List {

	public Worldlist(Skin skin) {
		super(skin);
		// TODO Auto-generated constructor stub
	}
	
	public void Refresh() {
		Array<String> worlds=AssetLoader.Datahandler.game().getworlds();
		String world=Preference.prefs.getString("world");
		if (!worlds.contains(world, false))
			worlds.add(world);
		this.setItems(worlds);
	}
}
