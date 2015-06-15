package fr.evolving.automata;

import java.io.Serializable;

public class Automata implements Serializable{
	private int X;
	private int Y;
	private Grid MyGrid;
	private Cell Current;
	private transient Cell Old;	

	public Automata(int X,int Y,Grid MyGrid) {
		this.X=X;
		this.Y=Y;
		this.MyGrid=MyGrid;
		this.Current=new Cell();
		this.Old=new Cell();
	}
	
	public Automata GetRelative(int X,int Y) {
		int goX=this.X+X;
		int goY=this.Y+Y;
		if (goX<0 || goY<0 || goX>=this.MyGrid.automatas.length || goY>=this.MyGrid.automatas[0].length)  {
			return null;
		}
		return this.MyGrid.automatas[goX][goY];
	}
}
