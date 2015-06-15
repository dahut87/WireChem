package fr.evolving.automata;

import java.io.Serializable;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cell implements Serializable{
	public int Fiber;
	public boolean Copper;
	public transient int Fiber_old;
	public transient Sprite Sprite ;
	public Transmuter Transmuter;
	
	public Cell() {
		this.Fiber=0;
		this.Copper=false;
		this.Fiber_old=0;
		this.Sprite=null;
		this.Transmuter=null;
	}
	
}
