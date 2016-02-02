package fr.evolving.automata;

import java.io.Serializable;

public class Level implements Serializable {
	public String Name;
	public String Description;
	public String Element;
	public transient int[] Current;
	public int[] Victory;
	public int aWorld;
	public int aLevel;
	public int id;
	public float X;
	public float Y;
	public int Tech;
	public int Cout;
	public Grid Grid_orig;
	public int Cycle_orig;
	public int Temp_orig;
	public int Rayon_orig;
	public int Nrj_orig;
	public int Maxcycle;
	public int Maxtemp;
	public int Maxrayon;
	public int Maxnrj;
	public int Research;
	public boolean Special;
	public String Tuto;
	public int[][] Link;
	
	public transient Grid Grid;
	public transient int Cycle;
	public transient int Temp;
	public transient int Rayon;
	public transient int Nrj;
	public transient boolean Locked;
	public transient boolean Locked_old;

	public Level(int aWorld, int aLevel, int id, String Name,
			String Description, String Element, int[] Current, int[] Victory,
			float X, float Y, int Tech, int Cout, Grid World, int Cycle,
			int Temp, int Rayon, int Nrj, int Maxcycle, int Maxtemp,
			int Maxrayon, int Maxnrj, String Tuto, boolean Special, int[][] Link) {
		this.aWorld = aWorld;
		this.aLevel = aLevel;
		this.id = id;
		this.Name = Name;
		this.Description = Description;
		this.Element = Element;
		this.Current = Current;
		this.Victory = Victory;
		this.X = X;
		this.Y = Y;
		this.Tech = Tech;
		this.Cout = Cout;
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
	}
}
