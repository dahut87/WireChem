package fr.evolving.automata;

import java.io.Serializable;

public class World implements Serializable{
	String Name;
	String Description;
	String Element;
	Integer[] Current;
	Integer[] Victory;
	Integer X;
	Integer Y;
	Integer Tech;
	Integer Cout;
	Grid World;
	Integer Cycle;
	Integer Temp;
	Integer Rayon;
	Integer Nrj;
	Integer Maxcycle;
	Integer Maxtemp;
	Integer Maxrayon;
	Integer Maxnrj;
	String Tuto;
	Integer[][] Link;
	
public void World(String Name,String Description,String Element,Integer[] Current,Integer[] Victory,Integer X,Integer Y,Integer Tech,Integer Cout,Grid World,Integer Cycle,Integer Temp,Integer Rayon,Integer Nrj,Integer Maxcycle,Integer Maxtemp,Integer Maxrayon,Integer Maxnrj,String Tuto,Integer[][] Link){
	this.Name=Name;
	this.Description=Description;
	this.Element=Element;
	this.Current=Current;
	this.Victory=Victory;
	this.X=X;
	this.Y=Y;
	this.Tech=Tech;
	this.Cout=Cout;
	this.World=World;
	this.Cycle=Cycle;
	this.Temp=Temp;
	this.Rayon=Rayon;
	this.Nrj=Nrj;
	this.Maxcycle=Maxcycle;
	this.Maxtemp=Maxtemp;
	this.Maxrayon=Maxrayon;
	this.Maxnrj=Maxnrj;
	this.Tuto=Tuto;
	this.Link=Link;
}
}
