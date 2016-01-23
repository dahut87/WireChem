package fr.evolving.UI;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.reflect.Method;

import fr.evolving.assets.AssetLoader;
import fr.evolving.screens.GameScreen;
import fr.evolving.screens.GameScreen.calling;

public class HorizBarre extends Actor {
	private ImageButton[] Barre;
	private HorizontalGroup table;
	private java.lang.reflect.Method method;
	private TextureRegion oneselection;
	private Timer RotateTimer;
	private TimerTask RotateTask;
	private Actor selected;
	private float rotation;
	ChangeEvent event;

	public HorizBarre(String[] tocreate, String methodname) {
		Class<?> base;
		try {
			Class<?>[] params = {String.class, int.class};
			base = Class.forName("fr.evolving.screens.GameScreen");
			method = base.getDeclaredMethod(methodname, params);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gdx.app.debug(getClass().getSimpleName(), "Mise en place du timer de rotation.");		
		oneselection = AssetLoader.Atlas_level.findRegion("circle");
		rotation=0;
		RotateTimer = new Timer();
		RotateTask = new TimerTask() {
			@Override
			public void run() {
				rotation += 5;
			}
		};
		RotateTimer.scheduleAtFixedRate(RotateTask, 0, 30);

		this.setBounds(0, 0, 1920, 80);
		Barre = new ImageButton[tocreate.length];
		int i = 0;
		Gdx.app.debug(getClass().getSimpleName(), "Barre bas:" + Barre.length
				+ " elements");
		for (String item : tocreate) {
			final String itemtocreate=item.replace("#", "");
			Barre[i] = new ImageButton(AssetLoader.Skin_level, itemtocreate);
			Barre[i].setTouchable(Touchable.enabled);
			Barre[i].setName(itemtocreate);
			if (item.contains("#"))
			{
				Barre[i++].addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (selected==null)	selected = new Actor();
						selected.setName(itemtocreate);
						selected.setBounds(event.getListenerActor().getX(), event.getListenerActor().getY(), 64, 64);
						onchanged();
					}
				});
			}
			else
				Barre[i++].addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						String caller = event.getListenerActor().getName();
						Gdx.app.debug("Barre", "Selection dans la Barre bas:"
								+ caller);
						try {
							method.invoke(((Game) Gdx.app.getApplicationListener()).getScreen(), caller, this.getTapCount());
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		}
		Barre[15].setChecked(Gdx.graphics.isFullscreen());
		Barre[16].setChecked(AssetLoader.intro.getVolume() > 0);
		Barre[17].setChecked(AssetLoader.Tooltipmanager.enabled == true);
		table = new HorizontalGroup();
		table.bottom().padLeft(5f).padBottom(8f).space(10f);
		this.setTouchable(Touchable.enabled);
		table.setTouchable(Touchable.enabled);
		for (i = 0; i < Barre.length; i++)
			table.addActor(Barre[i]);
	}

	public void unSelect() {
		selected=null;
	}
	
	public String getSelection() {
		if (selected!=null)
			return selected.getName();
		else
			return null;
	}
	
	public void onchanged() {
		ChangeEvent event=new ChangeEvent();
		event.setTarget(this);
		event.setListenerActor(this);	
		event.setStage(this.getStage());
		if (event.getStage()!=null) 
			this.fire(event);
	}

	public Actor hit(float x, float y, boolean touchable) {
		return table.hit(x, y, touchable);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		table.draw(batch, parentAlpha);
		if (selected != null) {
			batch.setColor(1f, 0f, 0f, 1f);
			batch.draw(oneselection, selected.getX()+this.getX(),
					selected.getY()+this.getY(),
					selected.getWidth() / 2,
					selected.getHeight() / 2,
					selected.getWidth(),
					selected.getHeight(), 1f, 1f, rotation);
		}	
	}
}

