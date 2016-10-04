package fr.evolving.automata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Particle {
	public enum Type {
		Electron, Photon, Proton, Neutron
	}
	public enum Orientation {
		N, S, E, O, NE, SE, NO, SO, Fixed, Kill
	};
	public enum Size {
		Gros, Petit
	};
	public enum Charge {
		Positif, Negatif, Neutre
	};
	
	public final static int PHOTONLIFE=250;
	
	private Orientation oldorientation;
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
		this.life=PHOTONLIFE;
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
	
	public void subLife(int sub) {
		this.life-=sub;
		if (this.life<=0) {
			this.life=0;
			this.kill();
		}
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
	
	private Vector2 TestOrientation(Orientation orientation, Type type) {
		int movex=0,movey=0;
		if (orientation==Orientation.Kill || orientation==Orientation.Fixed) return new Vector2(movex,movey);
		if (orientation.toString().contains("N")) movey=+1;
		if (orientation.toString().contains("S")) movey=-1;
		if (orientation.toString().contains("E")) movex=+1;
		if (orientation.toString().contains("O")) movex=-1;
		if (this.grid.GetXY(this.coordX+movex, this.coordY+movey)!=null) {
			if (type==Type.Photon && !this.grid.GetXY(this.coordX+movex, this.coordY+movey).Fiber)
				return null;
			if (type==Type.Electron && (!this.grid.GetXY(this.coordX+movex, this.coordY+movey).Copper))
				return null;
		}
		else
			return null;
		return new Vector2(movex,movey);
	}

	private Orientation[] getOrientations(Orientation orientation, Type type) {
		if (type==Type.Photon) {
			switch (orientation) {
			case E:
				return new Orientation[]{Orientation.E,Orientation.NE,Orientation.SE, Orientation.Kill};
			case NE:
				return new Orientation[]{Orientation.NE,Orientation.N,Orientation.E, Orientation.Kill};
			case N:
				return new Orientation[]{Orientation.N,Orientation.NE,Orientation.NO, Orientation.Kill};
			case NO:
				return new Orientation[]{Orientation.NO,Orientation.N,Orientation.O, Orientation.Kill};
			case O:
				return new Orientation[]{Orientation.O,Orientation.NO,Orientation.SO, Orientation.Kill};
			case SO:
				return new Orientation[]{Orientation.SO,Orientation.S,Orientation.O, Orientation.Kill};
			case S:
				return new Orientation[]{Orientation.S,Orientation.SE,Orientation.SO, Orientation.Kill};
			case SE:
			default:
				return new Orientation[]{Orientation.SE,Orientation.E,Orientation.S, Orientation.Kill};
			}
		}
		else if (type==Type.Electron) {
			switch (orientation) {
			case E:
				return new Orientation[]{Orientation.E,Orientation.N,Orientation.S,Orientation.O,Orientation.Fixed};
			case N:
				return new Orientation[]{Orientation.N,Orientation.O,Orientation.E,Orientation.S,Orientation.Fixed};
			case S:
				return new Orientation[]{Orientation.S,Orientation.E,Orientation.O,Orientation.N,Orientation.Fixed};
			case O:
				return new Orientation[]{Orientation.O,Orientation.N,Orientation.S,Orientation.E,Orientation.Fixed};
			case Fixed:
			default:
				return new Orientation[]{Orientation.N,Orientation.S,Orientation.E,Orientation.O,Orientation.Fixed};
			}
		}
		else
			return null;
	}
	
	public void Next() {
		if (this.life>0) this.life--;
		if (type==Type.Photon && life==0) this.kill();
		Vector2 move=null;
		Orientation[] orientations=getOrientations(this.orientation, type);
		Array<Orientation> orientations_good=new Array<Orientation>();
		for(Orientation orientationtest:orientations) 
			if (TestOrientation(orientationtest,type)!=null)
				orientations_good.add(orientationtest);
			if (orientations_good.contains(orientation, true))
				orientation=orientation;
			else if (orientations_good.contains(oldorientation, true))
				orientation=oldorientation;
			else {
				for(Orientation orientationtest2:orientations_good) {
					if (orientationtest2==Orientation.Kill) {
						Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" killed no place to go");
						this.kill();
						return;
					}
					else {
						orientation=orientationtest2;
						break;
					}
				}
			}
			move=TestOrientation(orientation,type);
			Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" move to "+orientation+":"+move.x+","+move.y+" life:"+this.life);
			this.coordX+=move.x;
			this.coordY+=move.y;
		}
	
}