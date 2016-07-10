package fr.evolving.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.evolving.UI.ButtonLevel;
import fr.evolving.assets.AssetLoader;
import fr.evolving.effects.Laser;
import fr.evolving.screens.LevelScreen;

public class LevelRenderer {
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private SpriteBatch batcher2;
	int scrollx;
	int scrolly;
	int dirx;
	int diry;
	LevelScreen LevelScreen;
	Laser Laser;
	public TextureRegion Texture_logobig;
	
	public LevelRenderer(LevelScreen LevelScreen) {
		this.LevelScreen = LevelScreen;
		this.scrollx = 0;
		this.scrolly = 0;
		this.dirx = 1;
		this.diry = 1;
		batcher = new SpriteBatch();
		batcher2 = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Laser = new Laser();
		AssetLoader.viewport.apply();
	}

	public void evolve() {
		this.scrollx += dirx;
		this.scrolly += diry;
		if (this.scrollx > 1500)
			this.scrolly += diry;
		if (this.scrollx > 1024)
			this.dirx = -1;
		if (this.scrolly > 768)
			this.diry = -1;
		if (this.scrollx < 0)
			this.dirx = 1;
		if (this.scrolly < 0)
			this.diry = 1;
		Laser.i += 0.3f;
		
		if (Laser.i > 10.0f) {
			Laser.i = 0;
		}
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batcher.begin();
		batcher.setProjectionMatrix(AssetLoader.Camera.combined);
		batcher.setColor(0.25f, 0.25f, 0.25f, 1f);
		batcher.draw(AssetLoader.Texture_fond2, 0, 0, this.scrollx / 2,
				this.scrolly / 2, AssetLoader.width, AssetLoader.height);
		batcher.setColor(0.7f, 0.7f, 0.7f, 1);
		batcher.draw(AssetLoader.Texture_fond, 0, 0, this.scrollx,
				this.scrolly, AssetLoader.width, AssetLoader.height);
		batcher.end();

		batcher2.begin();
		batcher2.setProjectionMatrix(AssetLoader.Camera.combined);
		batcher2.setColor(Color.WHITE);
		Texture_logobig = AssetLoader.Skin_level.getRegion("logo3");
		batcher2.draw(Texture_logobig, 120, AssetLoader.height-Texture_logobig.getRegionHeight());
		batcher2.end();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(AssetLoader.Camera.combined);
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		if (LevelScreen.selected != null) {
			shapeRenderer.rect(10, 10, 1190, 165);
			if (LevelScreen.selected.level.Cout_orig > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug()) {
				shapeRenderer.rect(1210, 10, 250, 140);
				shapeRenderer.rect(1210, 160, 250, 140);
			}
			if (LevelScreen.selected.level.aWorld > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug())
				shapeRenderer.rect(1210, 310, 250, 300);
			if (LevelScreen.selected.level.Tech > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug())
				shapeRenderer.rect(1210, 620, 250, AssetLoader.height - 630);
		}
		if (!LevelScreen.group_init.isVisible())
			if (!LevelScreen.worlds.isDebug())
				shapeRenderer.rect(1470, 10, 440, AssetLoader.height - 20);
			else {
				shapeRenderer.rect(1470, 10, 440, 140);
				shapeRenderer.rect(1470, 160, 440, 140);
				shapeRenderer.rect(1470, 310, 440, 300);
				shapeRenderer.rect(1470, 620, 440, AssetLoader.height - 630);
			}
		shapeRenderer.end();
		if (LevelScreen.buttonLevels != null)
			for (int i=0;i<LevelScreen.buttonLevels.size;i++)
			{
				ButtonLevel button1=LevelScreen.buttonLevels.get(i);
				for (int[] item : button1.level.Link)
					for (ButtonLevel button2 : LevelScreen.buttonLevels)
						if ((item.length == 2) && (button2.level.aWorld == item[0]) && (button2.level.aLevel == item[1])) 
							if (!LevelScreen.worlds.isDebug())
								Laser.draw(batcher,button1.level.X,button1.level.Y* AssetLoader.ratio,button2.level.X,button2.level.Y* AssetLoader.ratio,10,0.5f,!button2.level.Locked,	button1.getLevelcolor(),button2.getLevelcolor());
							else {
								Vector2 debut=new Vector2(button1.level.X,button1.level.Y* AssetLoader.ratio);
								Vector2 fin=new Vector2(button2.level.X,button2.level.Y* AssetLoader.ratio);
								Vector2 size=fin.cpy().sub(debut);
								Vector2 newfin=size.cpy().limit(size.len()-40).add(debut);
								Vector2 corps=fin.cpy().sub(debut).limit(45);
								Vector2 fleche1=newfin.cpy().sub(corps.cpy().rotate(30));
								Vector2 fleche2=newfin.cpy().sub(corps.cpy().rotate(-30));
								Laser.drawnotsoold(shapeRenderer,debut.x,debut.y,fin.x,fin.y,10,0.5f,!button2.level.Locked,	button1.getLevelcolor(),button2.getLevelcolor());
								Laser.drawnotsoold(shapeRenderer,fleche2.x,fleche2.y,newfin.x,newfin.y,10,0.5f,!button2.level.Locked,	button1.getLevelcolor(),button2.getLevelcolor());
								Laser.drawnotsoold(shapeRenderer,fleche1.x,fleche1.y,newfin.x,newfin.y,10,0.5f,!button2.level.Locked,	button1.getLevelcolor(),button2.getLevelcolor());

							}
			}		
		}
}
