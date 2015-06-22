package fr.evolving.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import fr.evolving.assets.AssetLoader;
import fr.evolving.game.main;

public class SplashScreen implements Screen {

	private main game;	
	private Stage stage;
	private Image splashImage;
	private float scale;
	private SpriteBatch batcher;
	
	public SplashScreen(main game) {
		AssetLoader.load();
		this.game = game;
		batcher= new SpriteBatch();
		stage = new Stage(AssetLoader.viewport);
		splashImage = new Image(AssetLoader.Texture_logo);
		AssetLoader.loadall();
	}

	@Override
	public void show() {
		Gdx.app.log("****","Affichage du SplashScreen");
		scale=2;
		splashImage.setScale(scale);
		splashImage.setPosition((AssetLoader.width / 2) - (scale * splashImage.getWidth() / 2), (AssetLoader.height / 2) - (scale * splashImage.getHeight() / 2));
		stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(3f),Actions.run(new Runnable() {
            @Override
            public void run() {
                AssetLoader.finishall();
            }
        }),Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
            }
        })));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
	    stage.draw();
	    if 	(AssetLoader.manager!=null) {
		    batcher.begin();
		    batcher.setProjectionMatrix(AssetLoader.Camera.combined);
	    	AssetLoader.empty.draw(batcher, (AssetLoader.width / 2) - 400f, 150f, 800f, 50f);
	    	AssetLoader.full.draw(batcher, (AssetLoader.width / 2) - 400f, 150f, AssetLoader.manager.getProgress()*800f, 50f);
			AssetLoader.manager.update();
	    	batcher.end();
	    }
	}

	@Override
	public void resize(int width, int height) {
		AssetLoader.viewport.update(width,height);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
