package fr.evolving.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	BitmapFont font;
	String reward,goal,ressource,handicap;
	
	public LevelRenderer(LevelScreen LevelScreen) {
		this.reward=AssetLoader.language.get("[reward-levelscreen]");
		this.goal=AssetLoader.language.get("[goal-levelscreen]");
		this.ressource=AssetLoader.language.get("[ressource-levelscreen]");
		this.handicap=AssetLoader.language.get("[handicap-levelscreen]");
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
		font = AssetLoader.Skin_level.getFont("OpenDyslexicAlta-25");
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
		if (LevelScreen.worlds.getWorld() >= 0)
			font.setColor(AssetLoader.Levelcolors[LevelScreen.worlds.getWorld()]);
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
		batcher2.draw(Texture_logobig, 120, AssetLoader.height
				- Texture_logobig.getRegionHeight());

		if (LevelScreen.selected != null) {
			font.draw(batcher2, LevelScreen.selected.level.Name, 15, 165);
			if (LevelScreen.selected.level.Tech > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug())
				font.draw(batcher2, this.reward, 1215,
						AssetLoader.height - 15);
			if (LevelScreen.selected.level.Cout_orig > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug()) {
				font.draw(batcher2, this.ressource, 1215, 145);
				font.draw(batcher2, this.goal, 1215, 295);
			}

			if (LevelScreen.selected.level.aWorld > 0 && !LevelScreen.worlds.isDebug() || LevelScreen.modify.isChecked() && LevelScreen.worlds.isDebug())
				font.draw(batcher2, this.handicap, 1215, 605);
			//font.draw(batcher2, "", 1215, 145);
		}
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
			shapeRenderer.rect(1470, 10, 440, AssetLoader.height - 20);
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
							else
								Laser.drawnotsoold(shapeRenderer,button1.level.X,button1.level.Y* AssetLoader.ratio,button2.level.X,button2.level.Y* AssetLoader.ratio,10,0.5f,!button2.level.Locked,	button1.getLevelcolor(),button2.getLevelcolor());
							
			}		

		}
}
