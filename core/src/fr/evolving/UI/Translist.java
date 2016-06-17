package fr.evolving.UI;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Transmuter;

public class Translist extends Actor{
	
	private Array<Transmuter> transmuters;
	private ImageButton Next,Previous;
	Transhower Selected;
	Table table;
	int whereis;
	Color color;
	ChangeEvent event;
	
	public Translist(Array<Transmuter> transmuters,Color color) {
		this.color=color;
		super.setBounds(-500, -500, 256, 256);
		Previous=new ImageButton(AssetLoader.Skin_level, "extend2");
		Previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug("wirechem-Translist", "Previous transmuter");
				previousTransmuter();
				onchanged();
			}
		});
		Next=new ImageButton(AssetLoader.Skin_level, "extend");
		Next.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug("wirechem-Translist", "Next transmuter");
				nextTransmuter();
				onchanged();
			}
		});
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug("wirechem-Translist", "Next angle");
				nextAngle();
			}
		});
		table=new Table();
		setTransmuters(transmuters);
	}
	
	public void onchanged() {
		ChangeEvent event=new ChangeEvent();
		event.setTarget(this);
		event.setListenerActor(this);	
		event.setStage(this.getStage());
		if (event.getStage()!=null) 
			this.fire(event);
	}
	
	public void setColor(Color color) {
		this.color=color;
		assignTransmuter(whereis);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setTransmuters(Array<Transmuter> transmuters) {
		this.transmuters=transmuters;
		onchanged();
		whereis=0;
		if (transmuters!=null && transmuters.size>0)
			assignTransmuter(whereis);
	}
	
	public Array<Transmuter> getTransmuters() {
		return this.transmuters;
	}
	
	public void assignTransmuter(int where) {
		if (Selected==null)
			Selected=new Transhower(transmuters.get(where), transmuters.get(where).getRotation(), true, color);
		else {
			Selected.setAngle(transmuters.get(where).getRotation());
			Selected.setTransmuter(transmuters.get(where));
			Selected.setColor(this.color);
		}
		redraw();
	}
	
	public void nextAngle() {
		Transmuter.Angular angle=getTransmuter().getRotation();
		if (angle==Transmuter.Angular.A00)
			getTransmuter().setRotation(Transmuter.Angular.A90);
		else if (angle==Transmuter.Angular.A90)
			getTransmuter().setRotation(Transmuter.Angular.A180);
		else if (angle==Transmuter.Angular.A180)
			getTransmuter().setRotation(Transmuter.Angular.A270);
		else if (angle==Transmuter.Angular.A270)
			getTransmuter().setRotation(Transmuter.Angular.A00);
		assignTransmuter(whereis);
	}
	
	public void previousTransmuter() {
		if (whereis>0) {
			whereis--;
			assignTransmuter(whereis);
		}
	}
	
	public Transmuter getTransmuter() {
		return transmuters.get(whereis);
	}
	
	public void nextTransmuter() {
		if (whereis<transmuters.size-1) {
			whereis++;
			assignTransmuter(whereis);
		}
	}
	
	public void redraw() {
		if (Selected!=null)
			Selected.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		table.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		table.clear();
		table.add(Previous).left().pad(this.getWidth()/4).padTop(this.getHeight()).size(this.getWidth()/512*64, this.getHeight()/512*64);
		table.add(Next).right().pad(this.getWidth()/4).padTop(this.getHeight()).size(this.getWidth()/512*64, this.getHeight()/512*64);
	}
	
	public Actor hit(float x, float y, boolean touchable) {
		Actor actor=table.hit(x, y, touchable);
		if (actor!=null) {
			return actor;
		}
		else
			return super.hit(x, y, touchable);
	}
	
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		redraw();
	}
	
	public void setHeight(float height) {
		super.setHeight(height);
		redraw();
	}
	
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		redraw();
	}
	
	public void setWidth(float width) {
		super.setWidth(width);
		redraw();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (Selected!=null)
			Selected.draw(batch, (float) 1.0);
		table.draw(batch, parentAlpha);
		}
}
