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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fr.evolving.worlds.LevelRenderer;
import fr.evolving.UI.ButtonLevel;
import fr.evolving.inputs.InputHandler;
import java.util.Timer;
import java.util.TimerTask;
import fr.evolving.assets.AssetLoader;
import fr.evolving.effects.Laser;


public class LevelScreen implements Screen {
	public ButtonLevel[] buttonLevels;
	private LevelRenderer Renderer;
	private float runTime;
	private Timer ScrollTimer;
	private TimerTask ScrollTask;
    private Stage stage = new Stage();
    private Table table = new Table();
    private ImageButton Previous,Next,Exit;
	private TextButton buttonPlay,buttonExit;
	
	// This is the constructor, not the class declaration
	public LevelScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		Renderer=new LevelRenderer((int)screenWidth,(int)screenHeight,this);
		buttonLevels = new ButtonLevel[10];
		int[][] integer=new int[][] {{0,2},{0,3},{0,1}};
		buttonLevels[0]= new ButtonLevel("H","Hydrogene en test",0,0,true, integer, true,120,120);
		buttonLevels[1]= new ButtonLevel("Li","Lithium a voir",0,1,false, new int[0][0], false,820,520);
		buttonLevels[2]= new ButtonLevel("Ne","Neon",0,2,true, new int[0][0], true,420,420);
		buttonLevels[3]= new ButtonLevel("Mg","Magnesium 23",0,3,false, new int[0][0], false,420,220);	
		buttonLevels[4]= new ButtonLevel("Pr","prout 21",1,3,true, integer, true,520,520);
		int[][] integer2=new int[][] {{0,1},{1,3},{0,2}};	
		buttonLevels[5]= new ButtonLevel("Pr","prout",4,5,true, integer2, true,900,40);
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
		buttonPlay = new TextButton("Play", AssetLoader.Skin_level);
		buttonExit = new TextButton("Exit", AssetLoader.Skin_level);
		Exit=new ImageButton(AssetLoader.Skin_level,"Exit");
		Exit.setPosition(900, Gdx.graphics.getHeight()-Exit.getHeight());
		Exit.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Gdx.app.exit();
	        }
		});
		Next=new ImageButton(AssetLoader.Skin_level,"Next");
		Next.setPosition(800, 100);
		Previous=new ImageButton(AssetLoader.Skin_level,"Previous");
		Previous.setPosition(900, 100);
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
        table.add(buttonPlay).size(150,60).padBottom(20).row();
        table.add(buttonExit).size(150,60).padBottom(20).row();
		for (int i=0;i<10;i++) {
			if (buttonLevels[i]!=null) {
				stage.addActor(buttonLevels[i]);
			}
		}
        table.setFillParent(true);
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
