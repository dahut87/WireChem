package fr.evolving.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Worlds;

public class Objectives extends Actor {

	private ShapeRenderer shaperenderer;
	public int[] Victory;
	BitmapFont font;
	BitmapFont font2;
	public final int size=48;
	private TextureRegion Next,Add;
	Worlds worlds;

	public Objectives(Worlds worlds) {
		this.worlds=worlds;
		shaperenderer = new ShapeRenderer();
		font = AssetLoader.Skin_level.getFont("Vademecum-18");
		font2 = AssetLoader.Skin_level.getFont("OpenDyslexicAlta-28");
		Next = AssetLoader.Skin_level.getAtlas().findRegion("Button-Next-icon");
		Add = AssetLoader.Skin_level.getAtlas().findRegion("Button-Add-icon");
		this.setHeight(68);
		this.setWidth(6*size);
	}

	public void setVictory(int[] Victory) {
		this.Victory = Victory;
	}

	@Override
	public final void draw(Batch batch, float parentAlpha) {
		shaperenderer.setProjectionMatrix(batch.getProjectionMatrix());
		int element = 0;
		int type = 0;
		boolean flag =false;
		int[] thevictory,currentvictory;
		if (Victory!=null) {
			thevictory=Victory;
			currentvictory=Victory;
		}
		else
		{
			thevictory=worlds.getLevelData().Victory_orig;
			currentvictory=worlds.getLevelData().Victory;
		}
		for (int i=0;i<thevictory.length;i++) {
			if (thevictory[i] != 0) {
				batch.end();
				shaperenderer.begin(ShapeType.Filled);
				shaperenderer.setColor(AssetLoader.Typecolors[type]);
				shaperenderer.rect(this.getX() + element * size, this.getY(), size, 68*currentvictory[i]/thevictory[i]);
				shaperenderer.end();
				shaperenderer.begin(ShapeType.Line);
				shaperenderer.setColor(1, 1, 1, 1);
				shaperenderer.rect(this.getX() + element * size, this.getY(), size,	68);
				shaperenderer.end();
				batch.begin();
				font.draw(batch, AssetLoader.Typenames[type], this.getX()+ element * size + 2, this.getY() + 69);
				if (thevictory[i] >0)
					font2.draw(batch, String.valueOf(thevictory[i]), this.getX() + element * size + 11, this.getY() + 35);
				else
				{
					font2.draw(batch, "??", this.getX() + element * size + 11, this.getY() + 35);
					flag=true;
				}
				element += 1;
			}
			type += 1;
		}
		if (flag)
			batch.draw(Next, this.getX() + element * size+11, this.getY()+15);
		else if (worlds.isDebug() && element<5 && Victory!=null)
			batch.draw(Add, this.getX() + element * size+11, this.getY()+15);
	}

}
