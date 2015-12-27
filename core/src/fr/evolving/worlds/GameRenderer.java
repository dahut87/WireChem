package fr.evolving.worlds;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import fr.evolving.assets.AssetLoader;
import fr.evolving.inputs.InputHandler;
import fr.evolving.screens.GameScreen;

public class GameRenderer {
	private GameWorld myWorld;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private GameScreen GameScreen;
	private TextureRegion oneselection;
	private float rotation;

	public GameRenderer(GameScreen GameScreen) {
		this.GameScreen=GameScreen;
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(AssetLoader.Camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(AssetLoader.Camera.combined);
		oneselection= AssetLoader.Atlas_level.findRegion("circle");
		rotation=0f;
	}
	
	public void evolve() {
		rotation+=5;
	}

	public void render(float delta, float runTime,int layer) {
		if (layer==0) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
		else if (layer==1) {
			batcher.begin();
			batcher.setColor(0.25f, 0.25f, 0.25f, 1f);
			batcher.draw(AssetLoader.Atlas_level.findRegion("barrehaut"),0.0f,AssetLoader.height-198.0f,1920.0f,200.0f);
			batcher.draw(AssetLoader.Atlas_level.findRegion("barrecentre"),1480f,AssetLoader.height-785.0f,590f,530.0f);
			batcher.draw(AssetLoader.Atlas_level.findRegion("barrebas"),0.0f,0.0f,1920.0f,170.0f);
			if (GameScreen.unroll)
				batcher.draw(AssetLoader.Atlas_level.findRegion("barrecentre2"),1180f,AssetLoader.height-1000.0f,880f,386.0f);
			batcher.end();
		}
		else if (layer==2) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.end();
			batcher.begin();
			if (GameScreen.selected!=null) 
			{
				batcher.setColor(1f, 0f, 0f, 1f);
				batcher.draw(oneselection, GameScreen.selected.getX(), GameScreen.selected.getY(), GameScreen.selected.getWidth()/2,GameScreen.selected.getHeight()/2,GameScreen.selected.getWidth(), GameScreen.selected.getHeight(),1f,1f,rotation);
			}
			batcher.end();
		}
	}

}
