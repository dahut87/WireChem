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
	
	public SplashScreen(main game) {
		this.game = game;
		AssetLoader.load();
		stage = new Stage(AssetLoader.viewport);
		splashImage = new Image(AssetLoader.Texture_logo);
	}

	@Override
	public void show() {
		Gdx.app.log("****","Affichage du SplashScreen");
		scale=2;
		splashImage.setScale(scale);
		splashImage.setPosition((AssetLoader.width / 2) - (scale * splashImage.getWidth() / 2), (AssetLoader.height / 2) - (scale * splashImage.getHeight() / 2));
		stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f),Actions.run(new Runnable() {
        //splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.run(new Runnable() {
            @Override
            public void run() {
                AssetLoader.loadall();
            }
        }),Actions.run(new Runnable() {
        //splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.run(new Runnable() {
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
