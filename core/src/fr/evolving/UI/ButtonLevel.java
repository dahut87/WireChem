package fr.evolving.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;

public class ButtonLevel extends ImageTextButton {
	public Level level;
	public boolean Activated;
	TextureRegion Finalled,Locked;
	Label Thelabel;
	float scale;
	float ratio;
	ImageTextButtonStyle style;
	LabelStyle stylelabel;
	
	public ButtonLevel(Level level, boolean Activated,float ratio) {
		super(level.Name, AssetLoader.Skin_level, "world"+String.valueOf(level.aWorld));
		this.level=level;
		this.ratio=ratio;
		this.Activated=Activated;
		if (level.Special==true) {
			Finalled=AssetLoader.Skin_level.getAtlas().findRegion("boss");
		}
		if (Activated==false) {
			this.setDisabled(true);
			Locked=AssetLoader.Skin_level.getAtlas().findRegion("locked");
		}
		this.setColor(1f, 0.47f+(float)level.X/1024f*0.529f,0.607f+(float)level.X/768f*0.392f, 1f);
		this.scale=1f;
		this.setBounds(level.X, level.Y*ratio, 111*scale, 125*scale);
		Thelabel=new Label(level.Element, AssetLoader.Skin_level,"Levelshort");
		Thelabel.setColor(level.X/1024f,level.X/1024f,level.X/1024f,1f);
		Thelabel.setPosition(level.X+54*scale, level.Y*ratio+20*scale, Align.bottom | Align.center);
	}
	
	@Override	
	public void setScale(float scale) {
		this.scale=scale;
		this.setBounds(level.X, level.Y*ratio, 111*scale, 125*scale);
		Thelabel.setPosition(level.X+54*scale, level.Y*ratio+20*scale, Align.bottom | Align.center);
		stylelabel=Thelabel.getStyle();
		//stylelabel.font.setScale(scale);
		Thelabel.setStyle(stylelabel);
		style= this.getStyle();
		style.pressedOffsetX=style.pressedOffsetX*scale;
		style.pressedOffsetY=style.pressedOffsetY*scale;
		style.unpressedOffsetX=style.unpressedOffsetX*scale;
		style.unpressedOffsetY=style.unpressedOffsetY*scale;
		//style.font.setScale(scale);
		this.setStyle(this.style);
	}
	
	public Color getLevelcolor() {
		return AssetLoader.Levelcolors[level.aWorld];
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x,y);
		level.X=x;
		level.Y=y;
		Thelabel.setColor(level.X/1024f,level.X/1024f,level.X/1024f,1f);
		Thelabel.setPosition(level.X+54*scale, level.Y*ratio+20*scale, Align.bottom | Align.center);
		this.setColor(1f, 0.47f+(float)level.X/1024f*0.529f,0.607f+(float)level.X/768f*0.392f, 1f);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (level.Special) {
			batch.draw(Finalled,level.X,level.Y*ratio,Finalled.getRegionWidth()*scale,Finalled.getRegionHeight()*scale);
		}
		if (!Activated) {
			batch.draw(Locked,level.X+this.getWidth()-Locked.getRegionWidth(),level.Y*ratio+this.getHeight()-Locked.getRegionHeight(),Locked.getRegionWidth()*scale,Locked.getRegionHeight()*scale);
		}
		Thelabel.draw(batch, 1f);
	}
	
}
