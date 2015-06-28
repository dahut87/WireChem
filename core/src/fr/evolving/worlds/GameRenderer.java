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
	//private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private GameScreen GameScreen;

	public void evolve() {

	}

	public GameRenderer(GameScreen GameScreen) {
		this.GameScreen=GameScreen;
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(AssetLoader.Camera.combined);
		//shapeRenderer = new ShapeRenderer();
		//shapeRenderer.setProjectionMatrix(AssetLoader.Camera.combined);
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//shapeRenderer.begin(ShapeType.Filled);
		//shapeRenderer.setColor(0.15f, 0.15f, 0.15f, 0.5f);
		//shapeRenderer.rect(5, 5, AssetLoader.width-10, 74);
		//shapeRenderer.rect(5, AssetLoader.height-81, 810, 74);
		//shapeRenderer.rect(825, AssetLoader.height-107, 500, 100);
		//shapeRenderer.rect(1335, AssetLoader.height-107, 200, 100);
		//shapeRenderer.circle(1850, AssetLoader.height, 300, 60);
		//shapeRenderer.end();
		batcher.begin();
		batcher.setColor(0.25f, 0.25f, 0.25f, 0.5f);
		batcher.draw(AssetLoader.Atlas_level.findRegion("barrehaut"), 0.0f, AssetLoader.height-200.0f,1920.0f,200.0f);
		batcher.draw(AssetLoader.Atlas_level.findRegion("barrebas"), 0.0f, 0.0f,1920.0f,95.0f);
		batcher.end();
	}

}
