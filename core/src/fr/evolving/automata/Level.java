package fr.evolving.automata;

import java.io.Serializable;

public class Level implements Serializable,Cloneable {
	public String Name;
	public String Description;
	public String Element;
	public int aWorld;
	public int aLevel;
	public int id;
	public float X;
	public float Y;
	public int Tech;
	public int Maxcycle;
	public int Maxtemp;
	public int Maxrayon;
	public int Maxnrj;
	public int Research;
	public boolean Special;
	public String Tuto;
	public int[][] Link;
	public int[] rewards;
	
	public int[] Victory_orig;
	public int Cout_orig;
	public Grid Grid_orig;
	public int Cycle_orig;
	public int Temp_orig;
	public int Rayon_orig;
	public int Nrj_orig;
	
	public transient int[] Victory;
	public transient int Cout_transmuter;
	public transient int Cout_copperfiber;
	public transient int Cout;	
	public transient Grid Grid;
	public transient int Cycle;
	public transient float Temp;
	public transient float Rayon;
	public transient float Nrj;
	
	public transient boolean Locked;

	public Level(int aWorld, int aLevel, String Name,
			String Description, String Element, int[] Current, int[] Victory,
			float X, float Y, int Tech, int Cout, Grid World, int Cycle,
			int Temp, int Rayon, int Nrj, int Maxcycle, int Maxtemp,
			int Maxrayon, int Maxnrj, String Tuto, boolean Special, int[][] Link) {
		this.aWorld = aWorld;
		this.aLevel = aLevel;
		this.id = (int) (Math.random() * Integer.MAX_VALUE);
		this.Name = Name;
		this.Description = Description;
		this.Element = Element;
		this.rewards = Current;
		this.Victory_orig = Victory;
		this.Victory = Victory;
		this.X = X;
		this.Y = Y;
		this.Tech = Tech;
		this.Cout_orig = Cout;
		this.Grid = World;
		this.Grid_orig = World;
		this.Cycle = Cycle;
		this.Temp = Temp;
		this.Rayon = Rayon;
		this.Nrj = Nrj;
		this.Maxcycle = Maxcycle;
		this.Maxtemp = Maxtemp;
		this.Maxrayon = Maxrayon;
		this.Maxnrj = Maxnrj;
		this.Special = Special;
		this.Tuto = Tuto;
		this.Link = Link;
		this.Locked=true;
	}
	
	public Object clone() {
		Level result = new Level(this.aWorld, this.aLevel, this.Name+" BIS", this.Description, this.Element, this.rewards.clone(), this.Victory_orig.clone(), this.X+100f, this.Y+100f, this.Tech, this.Cout_orig, (Grid)this.Grid_orig.clone(), this.Cycle_orig, this.Temp_orig, this.Rayon_orig, this.Nrj_orig, this.Maxcycle, this.Maxtemp, this.Maxrayon, this.Maxnrj, this.Tuto, this.Special, this.Link);
		return result;
	}

}
