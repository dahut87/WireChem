package fr.evolving.dialogs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import fr.evolving.UI.Translist;
import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Transmuter;

public class UpDialog extends Dialog {
	private Translist translist;
	private Container<Translist> container;
	
	public UpDialog() {
		super(AssetLoader.language.get("[dialog-window]"), AssetLoader.Skin_ui);
		translist=new Translist(AssetLoader.allTransmuter, new Color(0.5f,0.5f,0.5f,1f));
		container=new Container<Translist>(translist);
		this.getContentTable().add(container).left();
		this.setModal(true);
		this.button("Ok");
		this.key(Input.Keys.ENTER, true);

	}
	
	public void setTransmuters(Array<Transmuter> transmuters) {
		translist.setTransmuters(transmuters);
	}
	
	public Array<Transmuter> getTransmuters() {
		return translist.getTransmuters();
	}
	
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		this.getContentTable().draw(batch, parentAlpha);

		//container.draw(batch, parentAlpha);
	}
	/*
	public Actor hit(float x, float y, boolean touchable){
		Actor actor=super.hit(x, y, touchable);
		if (actor==null)
			return translist.hit(x, y, touchable);
		else
			return actor;
	}*/
	

}