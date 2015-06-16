package fr.evolving.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.inputs.InputHandler;
import java.util.Timer;
import java.util.TimerTask;
import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.SaveObject;
import fr.evolving.automata.Level;
import fr.evolving.effects.Laser;


public class LevelScreen implements Screen {
	protected static final Class<? extends Actor> ButtonLevel = null;
	public ButtonLevel[] buttonLevels;
	private LevelRenderer Renderer;
	private float runTime;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage = new Stage();
    private Table table = new Table();
    private ImageButton Previous,Next,Exit;
	private TextButton buttonPlay,buttonExit;
	private Level[] thelevels=new Level[9];
	private float spaces,sizes;
	private TextArea TextDescriptive;
	
	// This is the constructor, not the class declaration
	public LevelScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		Renderer=new LevelRenderer((int)screenWidth,(int)screenHeight,this);
		buttonLevels = new ButtonLevel[10];
		thelevels= SaveObject.initObject();
		for (int i = 0;i<thelevels.length;i++) {
			if (thelevels[i] != null)
				buttonLevels[i]=new ButtonLevel(thelevels[i],true);	
				buttonLevels[i].addListener(new ClickListener(){
		        @Override
		        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		        	TextDescriptive.setText(((ButtonLevel)event.getListenerActor()).level.Description);
		        }
			});
		}
		ScrollTimer=new Timer();
		ScrollTask = new TimerTask()
		{
			@Override
			public void run() 
			{
				Renderer.evolve();
			}	
		};
		ScrollTimer.scheduleAtFixedRate(ScrollTask, 0, 30);
		TextDescriptive = new TextArea("Descriptif", AssetLoader.Skin_level,"Descriptif");
		TextDescriptive.setBounds(15, 15, 1009, 130);
		buttonPlay = new TextButton("Jouer", AssetLoader.Skin_level,"Bouton");
		buttonExit = new TextButton("Quitter", AssetLoader.Skin_level,"Bouton");
		Exit=new ImageButton(AssetLoader.Skin_level,"Exit");
		Exit.setPosition(900, Gdx.graphics.getHeight()-Exit.getHeight());
		Exit.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Gdx.app.exit();
	        }
		});
		Next=new ImageButton(AssetLoader.Skin_level,"Next");
		Next.setPosition(840, 170);
		Previous=new ImageButton(AssetLoader.Skin_level,"Previous");
		Previous.setPosition(920, 170);
		table.right().top().padTop(100);
		sizes=(screenWidth-1024.0f)*0.7f;
		if (sizes>250.0f)
			sizes=250.0f;
		spaces=(screenWidth-1024.0f-sizes)/2;
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		Renderer.render(delta, runTime);
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.app.log("Affichage du  LevelScreen","ok");
        table.add(buttonPlay).size(sizes,60).padRight(spaces).padBottom(20).row();
        table.add(buttonExit).size(sizes,60).padRight(spaces).padBottom(20).row();
		for (int i=0;i<10;i++) {
			if (buttonLevels[i]!=null) {
				stage.addActor(buttonLevels[i]);
			}
		}
        table.setFillParent(true);
        stage.addActor(TextDescriptive);
        stage.addActor(table);
        stage.addActor(Exit);
        stage.addActor(Next);
        stage.addActor(Previous);
        Gdx.input.setInputProcessor(stage);
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
