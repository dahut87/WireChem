package fr.evolving.automata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import fr.evolving.automata.Transmuter.CaseType;

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
	
	public void tiling_transmuter() {
		for (int x=0;x<this.sizeX;x++)
		for (int y=0;y<this.sizeY;y++)
			GetXY(x,y).Transmuter_calc=0;
		for (int x=0;x<this.sizeX;x++)
		for (int y=0;y<this.sizeY;y++) {
			Transmuter transmuter=getTransmuter(x,y);
			if (transmuter!=null)
			{
				Iterator<Vector2> keySetIterator = transmuter.getTiles().keySet().iterator();
				int MainTile=transmuter.getMainTile();
				GetXY(x,y).Transmuter_calc=(1<<16)*transmuter.getRotation().ordinal()+MainTile++;
				while(keySetIterator.hasNext()){
					Vector2 key = keySetIterator.next();
					GetXY(x+key.x,y+key.y).Transmuter_calc=(1<<16)*transmuter.getRotation().ordinal()+MainTile++;
				}
			}
		}
		for (int x=0;x<this.sizeX;x++)
		for (int y=0;y<this.sizeY;y++)
		{
			if (GetXY(x, y).Transmuter_calc>0)
				Gdx.app.debug("info",x+","+y+">"+GetXY(x, y).Transmuter_calc);
		}
		}
	
	public void tiling_copper() {
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++)
			if (getCopper(x,y))
			{
				int value=0;
				if (getCopper(x,y+1))
					value++;
				if (getCopper(x-1,y))
					value+=8;
				if (getCopper(x,y-1))
					value+=4;
				if (getCopper(x+1,y))
					value+=2;
				GetXY(x,y).Copper_calc=value;
			}
			else
				GetXY(x,y).Copper_calc=-1;
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++) 
			{
				int value=0;
				if (getCoppercalc(x,y)==15)
				{
					if (getCopper(x-1,y-1))
						value++;
					if (getCopper(x,y-1))
						value++;
					if (getCopper(x+1,y-1))
						value++;
					if (getCopper(x-1,y))
						value++;
					if (getCopper(x+1,y))
						value++;
					if (getCopper(x-1,y+1))
						value++;
					if (getCopper(x,y+1))
						value++;
					if (getCopper(x+1,y+1))
						value++;
					if (value>=5)
						GetXY(x,y).Copper_calc=GetXY(x,y).Copper_calc+20;
				}
				else
				{
					if (getCoppercalc(x,y)!=-1)
					{
						int oldvalue=GetXY(x,y).Copper_calc;
						if (getCoppercalc(x-1,y-1)==15 || getCoppercalc(x-1,y-1)==35)
							value++;
						if (getCoppercalc(x,y-1)==15 || getCoppercalc(x,y-1)==35)
							value++;
						if (getCoppercalc(x+1,y-1)==15 || getCoppercalc(x+1,y-1)==35)
							value++;
						if (getCoppercalc(x-1,y)==15 || getCoppercalc(x-1,y)==35)
							value++;
						if (getCoppercalc(x+1,y)==15 || getCoppercalc(x+1,y)==35)
							value++;
						if (getCoppercalc(x-1,y+1)==15 || getCoppercalc(x-1,y+1)==35)
							value++;
						if (getCoppercalc(x,y+1)==15 || getCoppercalc(x,y+1)==35)
							value++;
						if (getCoppercalc(x+1,y+1)==15 || getCoppercalc(x+1,y+1)==35)
							value++;
						if (value>=1 && oldvalue!=1 && oldvalue!=2 && oldvalue!=4 && oldvalue!=8 && oldvalue!=10 && oldvalue!=5)
							GetXY(x,y).Copper_calc=oldvalue+20;
					}
				}
			}
			for (int x=0;x<this.sizeX;x++)
			for (int y=0;y<this.sizeY;y++) 
			{
				if (getCoppercalc(x,y)==35)
				{
					int value=0;
					if (!getCopper(x+1,y+1))
						value+=2;
					if (!getCopper(x-1,y-1))
						value+=8;
					if (!getCopper(x+1,y-1))
						value+=4;
					if (!getCopper(x-1,y+1))
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
						if (getCopper(x,y+1) && getCoppercalc(x,y+1)<15)
							value+=1;
						if (getCopper(x-1,y) && getCoppercalc(x-1,y)<15)
							value+=6;
						if (getCopper(x,y-1) && getCoppercalc(x,y-1)<15)
							value+=2;
						if (getCopper(x+1,y) && getCoppercalc(x+1,y)<15)
							value+=2;
						if (value>0)
							GetXY(x,y).Copper_calc=oldvalue+22+value;
					}
					int value=0;
					if (oldvalue==34 && (getCoppercalc(x-1,y)==31 || getCoppercalc(x-1,y)==55  || getCoppercalc(x-1,y)==58))
						value=62;
					if (oldvalue==34 && (getCoppercalc(x+1,y)==31 || getCoppercalc(x+1,y)==55  || getCoppercalc(x+1,y)==58))
						value=58;
					if (oldvalue==31 && (getCoppercalc(x-1,y)==34 || getCoppercalc(x-1,y)==58  || getCoppercalc(x-1,y)==62))
						value=59;
					if (oldvalue==31 && (getCoppercalc(x+1,y)==34 || getCoppercalc(x+1,y)==58  || getCoppercalc(x+1,y)==62))
						value=55;
					if (oldvalue==33 && (getCoppercalc(x,y-1)==27 || getCoppercalc(x,y-1)==50  || getCoppercalc(x,y-1)==51))
						value=57;
					if (oldvalue==33 && (getCoppercalc(x,y+1)==27 || getCoppercalc(x,y+1)==50  || getCoppercalc(x,y+1)==51))
						value=56;
					if (oldvalue==27 && (getCoppercalc(x,y-1)==33 || getCoppercalc(x,y-1)==56  || getCoppercalc(x,y-1)==57))
						value=51;
					if (oldvalue==27 && (getCoppercalc(x,y+1)==33 || getCoppercalc(x,y+1)==56  || getCoppercalc(x,y+1)==57))
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
	
	public Transmuter getTransmuter(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return null;
		else	
			return cell.Transmuter;
	}
	
	public int getTransmutercalc(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return 0;
		else	
			return cell.Transmuter_calc & 0xFFFF;
	}
	
	public int getTransmuterrot(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return 0;
		else	
			return cell.Transmuter_calc>>16;
	}
	
	public boolean getCopper(float X,float Y) {
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
	
	public int getCoppercalc(float X,float Y) {
		Cell cell=GetXY(X,Y);
		if (cell==null)
			return 0;
		else	
			return cell.Copper_calc;
	}
	
	
}
