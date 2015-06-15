package fr.evolving.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import fr.evolving.assets.SaveObject;
import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Grid;

public class GameWorld {
	private float runTime = 0;
	private int midPointY;
	private GameRenderer renderer;
	private GameState currentState;
	private Grid myGrid;
	private SaveObject MySaveObject;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, OPTION
	}

	public GameWorld(int midPointY) {
		Gdx.app.log("Creation du plateau de jeu","ok");
		currentState = GameState.MENU;
		myGrid= new Grid(20,20);
		//MySaveObject= new SaveObject("com.mysql.jdbc.Driver","jdbc:mysql://dahut.fr:3306/popfr_games","popfr_games","ef^J,khGMIL~");
		MySaveObject= new SaveObject("com.mysql.jdbc.Driver","jdbc:mysql://192.168.1.252:3306/games","games","WoawGames!!87");
		try {
			MySaveObject.saveObject(myGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(float delta) {
		runTime += delta;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

}
