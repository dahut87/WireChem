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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;

public class ButtonLevel extends ImageTextButton {
	public Level level;
	public boolean Activated;
	TextureRegion Finalled,Locked;
	Label Thelabel;
	
	public ButtonLevel(Level level, boolean Activated) {
		super(level.Name, AssetLoader.Skin_level, "world"+String.valueOf(level.aWorld));
		this.level=level;
		this.Activated=Activated;
		if (level.Special==true) {
			Finalled=AssetLoader.Skin_level.getAtlas().findRegion("boss");
		}
		if (Activated==false) {
			this.setDisabled(true);
			Locked=AssetLoader.Skin_level.getAtlas().findRegion("locked");
		}
		this.setColor(1f, 0.47f+(float)level.X/1024f*0.529f,0.607f+(float)level.X/768f*0.392f, 1f);
		this.setBounds(level.X, level.Y*AssetLoader.ratio, 111, 125);
		Thelabel=new Label(level.Element, AssetLoader.Skin_level,"Levelshort");
		Thelabel.setColor(level.X/1024f,level.X/1024f,level.X/1024f,1f);
		Thelabel.setPosition(level.X+54, level.Y*AssetLoader.ratio+20, Align.bottom | Align.center);
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
		Thelabel.setPosition(level.X+54, level.Y*AssetLoader.ratio+20, Align.bottom | Align.center);
		this.setColor(1f, 0.47f+(float)level.X/1024f*0.529f,0.607f+(float)level.X/768f*0.392f, 1f);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (level.Special) {
			batch.draw(Finalled,level.X,level.Y*AssetLoader.ratio);
		}
		if (!Activated) {
			batch.draw(Locked,level.X+this.getWidth()-Locked.getRegionWidth(),level.Y*AssetLoader.ratio+this.getHeight()-Locked.getRegionWidth());
		}
		Thelabel.draw(batch, 1f);
	}
	
}
