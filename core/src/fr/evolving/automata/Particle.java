package fr.evolving.automata;

public class Particle {
	public enum Type {
		Electron, Photon, Proton, Neutron
	}
	public enum Orientation {
		Nord, Sud, Est, Ouest
	};
	public enum Size {
		Gros, Petit
	};
	public enum Charge {
		Positif, Negatif, Neutre
	};
	
	protected Orientation orientation;
	protected Size size;
	protected Charge charge;
	protected int coordX;
	protected int coordY;
	
	static protected Grid grid;
	
	public Particle(Grid grid) {
		this.orientation=Orientation.Est;
		this.size=Size.Petit;
		this.charge=Charge.Neutre;
		this.coordX=0;
		this.coordY=0;
		Particle.grid=grid;
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
		
	}
	

}
