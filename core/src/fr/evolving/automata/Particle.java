package fr.evolving.automata;

import com.badlogic.gdx.Gdx;

public class Particle {
	public enum Type {
		Electron, Photon, Proton, Neutron
	}
	public enum Orientation {
		N, S, E, O, NE, SE, NO, SO
	};
	public enum Size {
		Gros, Petit
	};
	public enum Charge {
		Positif, Negatif, Neutre
	};
	
	private Orientation orientation;
	private Size size;
	private Charge charge;
	private int coordX;
	private int coordY;
	private Type type;
	private int life;
	private boolean Alive;
	
	static private Grid grid;
	
	public Particle(Grid grid) {
		this.Alive=true;
		this.type=Type.Electron;
		this.orientation=Orientation.E;
		this.size=Size.Petit;
		this.charge=Charge.Neutre;
		this.coordX=0;
		this.coordY=0;
		Particle.grid=grid;
	}
	
	public void kill() {
		this.Alive=false;
	}
	
	public boolean isAlive() {
		return this.Alive;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public void setLife(int life) {
		this.life=life;
	}

	public int getCoordx() {
		return this.coordX;
	}
	
	public int getCoordy() {
		return this.coordY;
	}
	
	public void setCoordx(int coordX) {
		this.coordX=coordX;
	}
	
	public void setCoordy(int coordY) {
		this.coordY=coordY;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
	
	public Size getSize() {
		return this.size;
	}
	
	public Charge getCharge() {
		return this.charge;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type=type;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation=orientation;
	}
	
	public void setCharge(Charge charge) {
		this.charge=charge;
	}
	
	public void setSize(Size size) {
		this.size=size;
	}
	
	public void SizeGrow() {
		this.size=Size.Gros;
	}
	
	public void SizeMinimize() {
		this.size=Size.Gros;
	}
	
	public void ChargeNeutralize() {
		this.charge=Charge.Neutre;
	}
	
	public void ChargePositive() {
		this.charge=Charge.Positif;
	}
	
	public void ChargeNegative() {
		this.charge=Charge.Negatif;
	}
	
	public void ChargeInvert() {
		if (this.charge==Charge.Negatif)
			this.charge=Charge.Positif;
		else if (this.charge==Charge.Positif)
			this.charge=Charge.Negatif;
	}
	
	public void Next() {
		this.life++;
		int movex = 0,movey = 0;
		Orientation neworientation=this.orientation;
		if (type==Type.Photon) {
			if (life>=30) this.kill();
			if (this.orientation==Orientation.E)
			{
				if (this.grid.GetXY(this.coordX+1, this.coordY)!=null && this.grid.GetXY(this.coordX+1, this.coordY).Fiber) {
					movex=+1;
					neworientation=Orientation.E;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY-1)!=null && this.grid.GetXY(this.coordX+1, this.coordY-1).Fiber) {
					movex=+1;
					movey=-1;
					neworientation=Orientation.SE;
				}
			}
			else if (this.orientation==Orientation.NE)
			{
				if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
				else if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY)!=null && this.grid.GetXY(this.coordX+1, this.coordY).Fiber) {
					movex=+1;
					neworientation=Orientation.E;
				}
			}
			else if (this.orientation==Orientation.N)
			{
				if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
			}
			else if (this.orientation==Orientation.NO)
			{
				if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.O;
				}
			}
			else if (this.orientation==Orientation.O)
			{
				if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
			}
			else if (this.orientation==Orientation.SO)
			{
				if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
			}
			else if (this.orientation==Orientation.S)
			{
				if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
			}
			else if (this.orientation==Orientation.SE)
			{
				if (this.grid.GetXY(this.coordX, this.coordY+1)!=null && this.grid.GetXY(this.coordX, this.coordY+1).Fiber) {
					movey=+1;
					neworientation=Orientation.N;
				}
				else if (this.grid.GetXY(this.coordX-1, this.coordY+1)!=null && this.grid.GetXY(this.coordX-1, this.coordY+1).Fiber) {
					movey=+1;
					movex=-1;
					neworientation=Orientation.NO;
				}
				else if (this.grid.GetXY(this.coordX+1, this.coordY+1)!=null && this.grid.GetXY(this.coordX+1, this.coordY+1).Fiber) {
					movex=+1;
					movey=+1;
					neworientation=Orientation.NE;
				}
			}
			if (movex==0 && movey==0)
			{
				Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" killed no place to go");
				this.kill();
			}
			else {
				Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" move to "+orientation+":"+movex+","+movey+" life:"+this.life);
				orientation=neworientation;
				this.coordX+=movex;
				this.coordY+=movey;
			}
		}
	}
}
