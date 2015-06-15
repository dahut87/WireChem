package fr.evolving.automata;

import java.io.Serializable;
import com.badlogic.gdx.Gdx;

public class Grid implements Serializable{
	public Cell[][] Cells;
	private Integer sizeX,sizeY;
	
	public Grid(Integer X,Integer Y) {
		this.sizeX=X;
		this.sizeY=Y;
		this.Cells =  new Cell[this.sizeX][this.sizeY];
		for (int x=0;x<this.sizeX;x++) {
			for (int y=0;y<this.sizeY;y++) {
				this.Cells[x][y]=new Cell();
			}
		}
	
	}
	
	public Cell GetXY(int X,int Y) {
		if (X<0 || Y<0 || X>=this.sizeX || Y>=this.sizeY)  {
			return null;
		}
		return this.Cells[X][Y];
	}
	
	
}
