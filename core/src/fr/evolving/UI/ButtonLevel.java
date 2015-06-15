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

public class ButtonLevel extends ImageTextButton {
	public int level;
	public int world;
	public boolean Final;
	public int[][] Link;
	public boolean Activated;
	TextureRegion Finalled,Locked;
	public String id,shortid;
	public int xx,yy;
	private Color[] Levelcolors={AssetLoader.Skin_level.getColor("world1"),AssetLoader.Skin_level.getColor("world2"),AssetLoader.Skin_level.getColor("world3"),AssetLoader.Skin_level.getColor("world4"),AssetLoader.Skin_level.getColor("world5")};
	Label Thelabel;
	
	public ButtonLevel(String shortid,String id,int world, int level, boolean Final, int[][] Link, boolean Activated,int x, int y) {
		super(id, AssetLoader.Skin_level, "world"+String.valueOf(world));
		this.xx=x;
		this.yy=y;
		this.id=id;
		this.shortid=shortid;
		this.level=level;
		this.world=world;
		this.Final=Final;
		this.Link=Link;
		this.Activated=Activated;
		if (Final==true) {
			Finalled=AssetLoader.Skin_level.getAtlas().findRegion("boss");
		}
		if (Activated==false) {
			this.setDisabled(true);
			Locked=AssetLoader.Skin_level.getAtlas().findRegion("locked");
		}
		this.setColor(1f, 0.47f+(float)x/Gdx.graphics.getWidth()*0.529f,0.607f+(float)x/Gdx.graphics.getWidth()*0.392f, 1f);
		this.setBounds(x/1024.0f*Gdx.graphics.getWidth(), y/768.0f*Gdx.graphics.getHeight(), 111, 125);
		Thelabel=new Label(this.shortid, AssetLoader.Skin_level,"Levelshort");
		Thelabel.setColor(xx/1024f,xx/1024f,xx/1024f,1f);
		Thelabel.setPosition(xx/1024.0f*Gdx.graphics.getWidth()+54, yy/768.0f*Gdx.graphics.getHeight()+20, Align.bottom | Align.center);
	}
	
	public Color getLevelcolor() {
		return Levelcolors[world];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (Final) {
			batch.draw(Finalled,xx/1024.0f*Gdx.graphics.getWidth(),yy/768.0f*Gdx.graphics.getHeight());
		}
		if (!Activated) {
			batch.draw(Locked,xx/1024.0f*Gdx.graphics.getWidth()+this.getWidth()-Locked.getRegionWidth(),yy/768.0f*Gdx.graphics.getHeight()+this.getHeight()-Locked.getRegionWidth());
		}
		Thelabel.draw(batch, 1f);
	}
	
}
