package fr.evolving.automata;

import java.io.Serializable;
import com.badlogic.gdx.Gdx;

public class Grid implements Serializable{
	public Cell[][] Cells;
	public Integer sizeX,sizeY;
	
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
	
	public void tiling() {
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++)
			if (GetCopper(x,y))
			{
				int value=0;
				if (GetCopper(x,y+1))
					value++;
				if (GetCopper(x-1,y))
					value+=8;
				if (GetCopper(x,y-1))
					value+=4;
				if (GetCopper(x+1,y))
					value+=2;
				GetXY(x,y).Copper_calc=value;
			}
			else
				GetXY(x,y).Copper_calc=-1;
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++) 
			{
				int value=0;
				if (GetCoppercalc(x,y)==15)
				{
					if (GetCopper(x-1,y-1))
						value++;
					if (GetCopper(x,y-1))
						value++;
					if (GetCopper(x+1,y-1))
						value++;
					if (GetCopper(x-1,y))
						value++;
					if (GetCopper(x+1,y))
						value++;
					if (GetCopper(x-1,y+1))
						value++;
					if (GetCopper(x,y+1))
						value++;
					if (GetCopper(x+1,y+1))
						value++;
					if (value>=5)
						GetXY(x,y).Copper_calc=GetXY(x,y).Copper_calc+20;
				}
				else
				{
					if (GetCoppercalc(x,y)!=-1)
					{
						int oldvalue=GetXY(x,y).Copper_calc;
						if (GetCoppercalc(x-1,y-1)==15 || GetCoppercalc(x-1,y-1)==35)
							value++;
						if (GetCoppercalc(x,y-1)==15 || GetCoppercalc(x,y-1)==35)
							value++;
						if (GetCoppercalc(x+1,y-1)==15 || GetCoppercalc(x+1,y-1)==35)
							value++;
						if (GetCoppercalc(x-1,y)==15 || GetCoppercalc(x-1,y)==35)
							value++;
						if (GetCoppercalc(x+1,y)==15 || GetCoppercalc(x+1,y)==35)
							value++;
						if (GetCoppercalc(x-1,y+1)==15 || GetCoppercalc(x-1,y+1)==35)
							value++;
						if (GetCoppercalc(x,y+1)==15 || GetCoppercalc(x,y+1)==35)
							value++;
						if (GetCoppercalc(x+1,y+1)==15 || GetCoppercalc(x+1,y+1)==35)
							value++;
						if (value>=1 && oldvalue!=1 && oldvalue!=2 && oldvalue!=4 && oldvalue!=8 && oldvalue!=10 && oldvalue!=5)
							GetXY(x,y).Copper_calc=oldvalue+20;
					}
				}
			}
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++) 
			{
				if (GetCoppercalc(x,y)==35)
				{
					int value=0;
					if (!GetCopper(x+1,y+1))
						value+=2;
					if (!GetCopper(x-1,y-1))
						value+=8;
					if (!GetCopper(x+1,y-1))
						value+=4;
					if (!GetCopper(x-1,y+1))
						value+=1;
					GetXY(x,y).Copper_calc=GetXY(x,y).Copper_calc+value;
				}
			}
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++) 
			{
					int oldvalue=GetXY(x,y).Copper_calc;
					if (oldvalue==27||oldvalue==31||oldvalue==33||oldvalue==34)
					{
						int value=0;
						if (GetCopper(x,y+1) && GetCoppercalc(x,y+1)<15)
							value+=1;
						if (GetCopper(x-1,y) && GetCoppercalc(x-1,y)<15)
							value+=6;
						if (GetCopper(x,y-1) && GetCoppercalc(x,y-1)<15)
							value+=2;
						if (GetCopper(x+1,y) && GetCoppercalc(x+1,y)<15)
							value+=2;
						if (value>0)
							GetXY(x,y).Copper_calc=oldvalue+22+value;
					}
					int value=0;
					if (oldvalue==34 && (GetCoppercalc(x-1,y)==31 || GetCoppercalc(x-1,y)==55  || GetCoppercalc(x-1,y)==58))
						value=62;
					if (oldvalue==34 && (GetCoppercalc(x+1,y)==31 || GetCoppercalc(x+1,y)==55  || GetCoppercalc(x+1,y)==58))
						value=58;
					if (oldvalue==31 && (GetCoppercalc(x-1,y)==34 || GetCoppercalc(x-1,y)==58  || GetCoppercalc(x-1,y)==62))
						value=59;
					if (oldvalue==31 && (GetCoppercalc(x+1,y)==34 || GetCoppercalc(x+1,y)==58  || GetCoppercalc(x+1,y)==62))
						value=55;
					if (oldvalue==33 && (GetCoppercalc(x,y-1)==27 || GetCoppercalc(x,y-1)==50  || GetCoppercalc(x,y-1)==51))
						value=57;
					if (oldvalue==33 && (GetCoppercalc(x,y+1)==27 || GetCoppercalc(x,y+1)==50  || GetCoppercalc(x,y+1)==51))
						value=56;
					if (oldvalue==27 && (GetCoppercalc(x,y-1)==33 || GetCoppercalc(x,y-1)==56  || GetCoppercalc(x,y-1)==57))
						value=51;
					if (oldvalue==27 && (GetCoppercalc(x,y+1)==33 || GetCoppercalc(x,y+1)==56  || GetCoppercalc(x,y+1)==57))
						value=50;
					if (value>0)
						GetXY(x,y).Copper_calc=value;
					
			}
		return;
	}
	
	
	public Cell GetXY(float X,float Y) {
		if (X<0 || Y<0 || X>=this.sizeX || Y>=this.sizeY)
			return null;
		else
			return this.Cells[(int)X][(int)Y];
	}
	
	public boolean GetCopper(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return false;
		else	
			return cell.Copper;
	}
	
	public boolean GetFiber(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return false;
		else	
			return cell.Fiber>0;
	}
	
	public int GetCoppercalc(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return 0;
		else	
			return cell.Copper_calc;
	}
	
	
}
