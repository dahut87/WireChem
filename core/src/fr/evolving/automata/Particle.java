package fr.evolving.automata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

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
	
	private Vector2 testorientation(Orientation orientation) {
		int movex=0,movey=0;
		if (orientation.toString().contains("N")) movey=+1;
		if (orientation.toString().contains("S")) movey=-1;
		if (orientation.toString().contains("E")) movex=+1;
		if (orientation.toString().contains("O")) movex=-1;
		if (this.grid.GetXY(this.coordX+movex, this.coordY+movey)!=null && this.grid.GetXY(this.coordX+movex, this.coordY+movey).Fiber)
			return new Vector2(movex,movey);
		else
			return null;
	}
	
	private Orientation[] get_orientations(Orientation orientation) {
		if (orientation==Orientation.E)
			return new Orientation[]{Orientation.E,Orientation.NE,Orientation.SE};
		if (orientation==Orientation.NE)
			return new Orientation[]{Orientation.NE,Orientation.N,Orientation.E};
		if (orientation==Orientation.N)
			return new Orientation[]{Orientation.N,Orientation.NE,Orientation.NO};
		if (orientation==Orientation.NO)
			return new Orientation[]{Orientation.NO,Orientation.N,Orientation.O};
		if (orientation==Orientation.O)
			return new Orientation[]{Orientation.O,Orientation.NO,Orientation.SO};
		if (orientation==Orientation.SO)
			return new Orientation[]{Orientation.SO,Orientation.S,Orientation.O};
		if (orientation==Orientation.S)
			return new Orientation[]{Orientation.S,Orientation.SE,Orientation.SO};
		if (orientation==Orientation.SE)
			return new Orientation[]{Orientation.SE,Orientation.E,Orientation.S};
		return new Orientation[]{Orientation.SE,Orientation.E,Orientation.S};
	}
	
	public void Next() {
		this.life++;
		Vector2 move=null;
		Orientation neworientation=this.orientation;
		if (type==Type.Photon) {
			if (life>=30) this.kill();
			Orientation[] orientations=get_orientations(this.orientation);
			Vector2 soluce0=testorientation(orientations[0]);
			Vector2 soluce1=testorientation(orientations[1]);
			Vector2 soluce2=testorientation(orientations[2]);
			if (soluce0!=null) {
				neworientation=orientations[0];
				move=soluce0;
			}
			else if (soluce1!=null && soluce2!=null)	{
					neworientation=orientations[1];
					move=soluce1;
			}
			else if (soluce1!=null) {
				neworientation=orientations[1];
				move=soluce1;
			}
			else if (soluce2!=null) {
				neworientation=orientations[2];
				move=soluce2;
			}
			if (move==null) 
			{
				Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" killed no place to go");
				this.kill();
			}
			else
			{
				Gdx.app.debug("wirechem-Particle", "coords:"+this.coordX+","+this.coordY+" move to "+orientation+":"+move.x+","+move.y+" life:"+this.life);
				if (orientation!=neworientation)
					oldorientation=orientation;
				orientation=neworientation;
				this.coordX+=move.x;
				this.coordY+=move.y;
			}
		}
	}
	
	
}