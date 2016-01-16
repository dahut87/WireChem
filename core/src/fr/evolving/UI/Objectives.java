package fr.evolving.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.evolving.assets.AssetLoader;

public class Objectives extends Actor {

	public ShapeRenderer shaperenderer;
	public int[] Victory;
	BitmapFont font;
	BitmapFont font2;

	public Objectives() {
		shaperenderer = new ShapeRenderer();
		font = AssetLoader.Skin_level.getFont("Vademecum-18");
		font2 = AssetLoader.Skin_level.getFont("OpenDyslexicAlta-28");
	}

	public void setVictory(int[] Victory) {
		this.Victory = Victory;
	}

	@Override
	public final void draw(Batch batch, float parentAlpha) {
		shaperenderer.setProjectionMatrix(batch.getProjectionMatrix());
		int element = 0;
		int type = 0;
		for (int vict : Victory) {
			if (vict != 0) {
				batch.end();
				shaperenderer.begin(ShapeType.Filled);
				shaperenderer.setColor(AssetLoader.Typecolors[type]);
				shaperenderer.rect(this.getX() + element * 40, this.getY(), 40,
						68);
				shaperenderer.end();
				shaperenderer.begin(ShapeType.Line);
				shaperenderer.setColor(1, 1, 1, 1);
				shaperenderer.rect(this.getX() + element * 40, this.getY(), 40,
						68);
				shaperenderer.end();
				batch.begin();
				font.draw(batch, AssetLoader.Typenames[type], this.getX()
						+ element * 40 + 2, this.getY() + 69);
				font2.draw(batch, String.valueOf(vict), this.getX() + element
						* 40 + 11, this.getY() + 35);
				element += 1;
			}
			type += 1;
		}
	}

}
