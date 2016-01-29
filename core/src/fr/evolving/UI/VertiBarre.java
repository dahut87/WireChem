package fr.evolving.UI;

import java.lang.reflect.Method;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Worlds;
import fr.evolving.screens.GameScreen.calling;


public class VertiBarre extends Actor {
	private VerticalGroup table;
	private ImageTextButton[] Barre;
	private ButtonGroup buttonGroup;
	Worlds worlds;
	
	public VertiBarre(Worlds worlds) {
		this.worlds=worlds;
		table = new VerticalGroup();
		table.setPosition(AssetLoader.width, AssetLoader.height - 375);
		table.right();
		table.space(10f);
		buttonGroup=new ButtonGroup<ImageTextButton>();
		Barre = new ImageTextButton[Transmuter.Class.values().length];
		Gdx.app.debug(getClass().getSimpleName(), "Menu:" + Barre.length+ " elements");
		for (int i = 0; i < Barre.length; i++)
		{
			if ((Transmuter.Class.values()[i]!=Transmuter.Class.Scenario && (worlds.getInformations().Tech>=0 || Transmuter.Class.values()[i]==Transmuter.Class.Structure)) || worlds.isDebug())
			{
				Barre[i] = new ImageTextButton(Transmuter.Class.values()[i].toString(), AssetLoader.Skin_level);
				table.addActor(Barre[i]);
				buttonGroup.add(Barre[i]);
				Barre[i].setName(String.valueOf(i));
				Barre[i].addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						int caller = Integer.parseInt(event.getListenerActor().getName());
						Gdx.app.debug("Barre2", "Selection dans la Barre droite:"+ caller);
						Method method;
						try {
							Class<?> base = Class.forName("fr.evolving.screens.GameScreen");
							Class<?>[] params = { int.class };
							method = base.getDeclaredMethod("preparemenu", params);
							method.invoke(((Game) Gdx.app.getApplicationListener()).getScreen(),  caller);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setMinCheckCount(1);
		buttonGroup.setUncheckLast(true);
		this.setBounds(table.getX(),table.getY(),table.getWidth(),table.getHeight());
	}

	public Actor hit(float x, float y, boolean touchable) {
		return table.hit(x, y, touchable);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		table.draw(batch, parentAlpha);
	}

}