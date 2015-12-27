package fr.evolving.automata;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Transmuter implements Cloneable {
	public enum CaseType{Rien,Cuivre,Fibre,Tout,Nimporte};
	public enum Class{Structure,Charge,Direction,Filtrage,Synthèse,Détection,Divers,Scénario};
	public enum Angular{A00,A90,A180,A270};
	protected Level level;
	protected Angular Rotation;
	
	public Transmuter(Level level) {
		this.level=level;
		this.Rotation=Angular.A00;
	}
	
	public String getName() {
		return "";
	}
	
	public String getDesc() {
		return "";
	}
	
	public String getaClass() {
		return "";
	}
	
	public void ProcessCycle() {
	}
	
	public void Run() {
	}
	
	public void Unlock() {
	}
	
	public void Upgrade() {
	}	
	
	public void Activate() {
	}
	
	public void UpgradeTemp() {
	}
	
	public void UpgradeNrj() {
	}	
	
	public void UpgradeRayon() {
	}	
	
	public void UpgradeCycle() {
	}
	
	public int getMainTile() {
		return 0;
	}
	
	public int FindMainTile(int Id) {
		return 0;
	}
	
	public boolean isTransmuter(int Id) {
		return (FindMainTile(Id)==getMainTile());
	}
	
	public boolean isTransmuter(String Name) {
		return  Name.equals(this.getName());
	}
	
	public Vector2 getPostitionMainTile(int Id) {
		if (!isTransmuter(Id))
			return null;
		HashMap<Vector2,CaseType> tiles=this.getTiles();
		Iterator<Vector2> keySetIterator = tiles.keySet().iterator();
		int Idrec=this.getMainTile();
		if ((Id & 0xFFFF)==Idrec)
			return new Vector2();
		Transmuter.Angular oldrotation=this.getRotation();
		this.setRotation(Transmuter.Angular.values()[Id>>16]);
		while(keySetIterator.hasNext()){
    	  Vector2 key = keySetIterator.next();
    	  Idrec++;
    	  if ((Id & 0xFFFF)==Idrec) {
    		this.setRotation(oldrotation);
  		    return new Vector2().sub(key);
    	  }
    	}
		this.setRotation(oldrotation);
		return null;
	}
	
	public HashMap<Vector2,CaseType> getTiles() {
		return null;
	}
	
	public int getActivationLevel() {
		return 0;
	}
	
	public boolean getActivation() {
		return false;
	}
	
	public int getPrice() {
		return 0;
	}
	
	public int getSize() {
		return 0;
	}

	public int getTechnology() {
		return 0;
	}

	public int getResearch() {
		return 0;
	}
	
	public boolean isUpgradable() {
		return false;
	}
	
	public boolean isUnlockable() {
		return false;
	}
	
	public boolean isShowed() {
		return false;
	}
	
	public void SetShowed(boolean value) {
	}
	
	public boolean isActivable() {
		return false;
	}
	
	public boolean isUpgradableTemp() {
		return false;
	}
	
	public boolean isUpgradableCycle() {
		return false;
	}
	
	public boolean isUpgradableRayon() {
		return false;
	}
	
	public boolean isUpgradableNrj() {
		return false;
	}
	
	public Transmuter getUpgrade() {
		return null;
	}
	
	public Transmuter getUnlock() {
		return null;
	}
	
	public int getUpgradeCycle() {
		return 0;
	}
	
	public int getUpgradeTemp() {
		return 0;
	}
	
	public int getUpgradeRayon() {
		return 0;
	}
	
	public int getUpgradeNrj() {
		return 0;
	}
	
	public float getUsedTemp() {
		return 0;
	}
	
	public float getUsedRayon() {
		return 0;
	}
	
	public float getUsedNrj() {
		return 0;
	}
	
	public float getTurnTemp() {
		return 0;
	}
	
	public float getTurnRayon() {
		return 0;
	}
	
	public float getTurnNrj() {
		return 0;
	}
	
	public int[] getallTiles() {
		return null;
	}
	
	public Vector2[] getallSize() {
		return null;
	}

	public String getInformations() {
		HashMap<Vector2,CaseType> tiles=this.getTiles();
		Iterator<Vector2> keySetIterator = tiles.keySet().iterator();
		String result;
		result="**********************************\n"+"Name:"+this.getName()+"\nClass:"+this.getaClass()+" Id:"+this.getMainTile()+" Rotation:"+this.getRotation()+"\nPrice:"+this.getPrice()+" Tech:"+this.getTechnology()+"\nResearch:"+this.getResearch()+" Size:"+this.getSize()+"\nActivable:"+this.isActivable()+" Activation:"+this.getActivationLevel()+" Visible:"+this.isShowed()+"\nUpgradable:"+((this.isUpgradable())?this.getUpgrade().getName():this.isUpgradable())+" Unlockable:"+((this.isUnlockable())?this.getUnlock().getName():this.isUnlockable())+"\nUpgrade Cycle:"+this.getUpgradeCycle()+" upgrade:"+this.isUpgradableCycle()+"\nUpgrade Temperature:"+this.getUpgradeTemp()+" upgrade:"+this.isUpgradableTemp()+"\nUpgrade Nrj:"+this.getUpgradeNrj()+" upgrade:"+this.isUpgradableNrj()+"\nUpgrade Rayon:"+this.getUpgradeRayon()+" upgrade:"+this.isUpgradableRayon()+"\nTemperature /turn:"+this.getTurnTemp()+" Rayon /turn:"+this.getTurnRayon()+" Nrj /turn:"+this.getTurnNrj()+"\nTemperature /use:"+this.getUsedTemp()+" Rayon /use:"+this.getUsedRayon()+" Nrj /use:"+this.getUsedNrj()+"\nTiles:";
		int[] allTiles;
		Vector2[] allSize;
		allTiles=this.getallTiles();
		for(int i=0;i<allTiles.length;i++)
			result+=allTiles[i]+" ";
		allSize=this.getallSize();
		result+="Size x&y:"+allSize[0].x+","+allSize[0].y+" to "+ allSize[1].x+","+allSize[1].y+"\n *Placement*\n";
		while(keySetIterator.hasNext()){
    	  Vector2 key = keySetIterator.next();
    	  result+="\ncoords:" + key.x+","+key.y + " type: " + tiles.get(key);
    	}
    	result+="\n**********************************";
		return result;
	
	}
	
	public Angular getRotation() {
		return this.Rotation;
	}
	
	public void setRotation(Angular rotation) {
		this.Rotation=rotation;
	}
	
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}
}