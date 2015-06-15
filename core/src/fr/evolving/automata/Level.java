package fr.evolving.automata;

import java.io.Serializable;

public class Level implements Serializable{
	String Name;
	String Description;
	String Element;
	int[] Current;
	int[] Victory;
	float X;
	float Y;
	int Tech;
	int Cout;
	Grid Grid;
	int Cycle;
	int Temp;
	int Rayon;
	int Nrj;
	int Maxcycle;
	int Maxtemp;
	int Maxrayon;
	int Maxnrj;
	boolean Special;
	String Tuto;
	int[][] Link;
	
public Level(String Name,String Description,String Element,int[] Current,int[] Victory,float X,float Y,int Tech,int Cout,Grid World,int Cycle,int Temp,int Rayon,int Nrj,int Maxcycle,int Maxtemp,int Maxrayon,int Maxnrj,String Tuto,boolean Special, int[][] Link){
	this.Name=Name;
	this.Description=Description;
	this.Element=Element;
	this.Current=Current;
	this.Victory=Victory;
	this.X=X;
	this.Y=Y;
	this.Tech=Tech;
	this.Cout=Cout;
	this.Grid=World;
	this.Cycle=Cycle;
	this.Temp=Temp;
	this.Rayon=Rayon;
	this.Nrj=Nrj;
	this.Maxcycle=Maxcycle;
	this.Maxtemp=Maxtemp;
	this.Maxrayon=Maxrayon;
	this.Maxnrj=Maxnrj;
	this.Special=Special;
	this.Tuto=Tuto;
	this.Link=Link;
}
}
