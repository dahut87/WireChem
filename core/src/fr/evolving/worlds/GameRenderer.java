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

	public void evolve() {

	}

	public GameRenderer(GameScreen GameScreen) {
		this.GameScreen=GameScreen;
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(AssetLoader.Camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(AssetLoader.Camera.combined);
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.5f);
		shapeRenderer.rect(5, 5, AssetLoader.width-10, 74);
		shapeRenderer.rect(5, AssetLoader.height-81, 550, 74);
		shapeRenderer.end();
		batcher.begin();
		batcher.disableBlending();
		batcher.end();
	}

}
