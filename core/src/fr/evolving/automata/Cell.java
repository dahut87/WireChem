package fr.evolving.automata;

import java.io.Serializable;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cell implements Serializable{
	public int Fiber;
	public boolean Copper;
	public transient int Copper_calc;
	public transient int Fiber_old;
	public Transmuter Transmuter;
	public transient int Transmuter_calc;
	
	public Cell() {
		this.Fiber=0;
		this.Copper=false;
		this.Fiber_old=0;
		this.Transmuter=null;
		this.Transmuter_calc=0;
	}
	
}
