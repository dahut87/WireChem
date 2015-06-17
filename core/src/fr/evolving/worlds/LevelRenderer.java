package fr.evolving.worlds;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import fr.evolving.assets.AssetLoader;
import fr.evolving.effects.Laser;
import fr.evolving.inputs.InputHandler;
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
	TextureRegion Texture_logobig;
	TextureRegion Texture_logosmall;
	
	public LevelRenderer(LevelScreen LevelScreen) {
		this.LevelScreen=LevelScreen;
		this.scrollx=0;
		this.scrolly=0;
		this.dirx=1;
		this.diry=1;
		batcher = new SpriteBatch();
		batcher2 = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Laser=new Laser();
	}
	
	public void evolve() {
		this.scrollx+=dirx;
		this.scrolly+=diry;
		if (this.scrollx>1500)
			this.scrolly+=diry;
        if (this.scrollx > 1024)
        	this.dirx = -1;
        if (this.scrolly > 768)
        	this.diry = -1;
        if (this.scrollx < 0)
        	this.dirx = 1;
        if (this.scrolly < 0)
        	this.diry = 1;
        Laser.i+=1f;
		if (Laser.i>10.0f) {
			Laser.i=0;
		}
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		//Gdx.gl.glViewport(0, 0, AssetLoader.width, AssetLoader.height);
		
		batcher.begin();
		batcher.setColor(0.25f,0.25f,0.25f,1);
		batcher.draw(AssetLoader.Texture_fond2, 0, 0, this.scrollx/2, this.scrolly/2, AssetLoader.width, AssetLoader.height);
		batcher.setColor(0.7f,0.7f,0.7f,1);
		batcher.draw(AssetLoader.Texture_fond, 0, 0, this.scrollx, this.scrolly, AssetLoader.width, AssetLoader.height);
		batcher.setColor(1,1,1,1);
		Texture_logobig=AssetLoader.Skin_level.getRegion("logo3");
		Texture_logosmall=AssetLoader.Skin_level.getRegion("logo2");		
		batcher.draw(Texture_logosmall,20, AssetLoader.height-Texture_logobig.getRegionHeight()+Texture_logosmall.getRegionHeight()/2);
		batcher.draw(Texture_logobig,120, AssetLoader.height-Texture_logobig.getRegionHeight());
		batcher.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		shapeRenderer.rect(10, 10, 1114, 140);
		shapeRenderer.rect(1134, 10, AssetLoader.width-1144, 140);
		shapeRenderer.rect(1134, 160, AssetLoader.width-1144, 140);
		shapeRenderer.rect(1134, 310, AssetLoader.width-1144, 300);
		shapeRenderer.rect(1134, 620, AssetLoader.width-1144, AssetLoader.height-620);
		shapeRenderer.end();
				
		batcher2.begin();
		batcher2.setColor(1f,1f,1f,1f);
		batcher2.draw(AssetLoader.Atlas_game.findRegion("cout"),1150,40);
		batcher2.draw(AssetLoader.Atlas_game.findRegion("tech"),1280,40);
		batcher2.end();
		
        for (int i=0;i<LevelScreen.buttonLevels.length;i++) {
			if (LevelScreen.buttonLevels[i]!=null) {
				for (int[] item : LevelScreen.buttonLevels[i].level.Link)
				{
					int found=-1;
			        for (int j=0;j<LevelScreen.buttonLevels.length;j++)
			        {
			        	if ((LevelScreen.buttonLevels[j]!=null) && (LevelScreen.buttonLevels[j].level.aWorld==item[0]) && (LevelScreen.buttonLevels[j].level.aLevel==item[1])) {
			        		found=j;
			        		break;
			        	}
			        }
			        if (found!=-1)
			        {
			        	Laser.draw(batcher,LevelScreen.buttonLevels[i].level.X+20,LevelScreen.buttonLevels[i].level.Y+20,LevelScreen.buttonLevels[found].level.X+20,LevelScreen.buttonLevels[found].level.Y+20,10,0.5f,LevelScreen.buttonLevels[found].Activated,LevelScreen.buttonLevels[i].getLevelcolor(),LevelScreen.buttonLevels[found].getLevelcolor());
			        }
			    }
			}
		}
	}

}
