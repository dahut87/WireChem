package fr.evolving.database;

import com.badlogic.gdx.utils.Array;

import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;

public abstract class Base {
    public enum datatype{statdata,userdata,gamedata}
    
    public Base(datatype model,String param) {
    }
    
    public Base() {
    }
    
	//Gestion type Gamebase
    
	public Array<String> getworlds() {
		return null;
	}
	
    public Array<Level> getworld(String description)  {
		return null;
	}
	
	public boolean setworld(Array<Level> world, String description) {
		return false;
	}
	
	public boolean deleteworld(String description) {
		return false;
	}	

	//Gestion type Userbase
	public boolean getlevellock(int user,int level){
		return false;
	}
	
	public boolean setlevelunlock(int user,int level){
		return false;
	}
	
	public Array<Transmuter> getTransmuters(int user){
		return null;
	}
	
	public boolean setTransmuters(int user,Array<Transmuter> transmuters){
		return false;
	}
	
	public int getResearchpoint(int user){
		return 0;
	}
	
	public boolean setResearchpoint(int user, int point){
		return false;
	}
	
	public Grid getGrid(int user,int level, int place){
		return null;
	}
	
	public boolean setGrid(int user,int level, Grid data){
		return false;
	}
	
	public Array<String> getGrids(int user, int level){
		return null;
	}
	
	//Gestion type Stat
	
	
	//Commun
	
	public boolean Eraseall(datatype base){
		return false;
	}
	
	public static boolean isHandling(datatype base){
		return false;
	}
	
	public void Close() {
	}
	
	public String getprefix() {
		return "";
	}
	
	

}
