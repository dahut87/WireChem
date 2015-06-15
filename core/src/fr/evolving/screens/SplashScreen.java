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
	private Stage stage = new Stage();
	private Image splashImage;
	
	public SplashScreen(main game) {
		this.game = game;
		AssetLoader.load();
		splashImage = new Image(AssetLoader.Texture_logo);
	}

	@Override
	public void show() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		float desiredWidth = width * .7f;
		float scale = desiredWidth / splashImage.getWidth();
		splashImage.setSize(splashImage.getWidth() * scale, splashImage.getHeight() * scale);
		splashImage.setPosition((width / 2) - (splashImage.getWidth() / 2), (height / 2) - (splashImage.getHeight() / 2));
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

		AssetLoader.intro.setLooping(0, true);
        AssetLoader.intro.play();
		Gdx.app.log("Affichage du SplashScreen","ok");
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
