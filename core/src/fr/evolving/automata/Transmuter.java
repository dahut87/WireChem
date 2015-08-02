package fr.evolving.automata;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Transmuter {
	public enum CaseType{Rien,Cuivre,Fibre,Tout,Nimporte};
	public enum Class{Structure,Charge,Direction,Filtrage,Synthèse,Détection,Divers,Scénario};
	protected Level level;
	
	public Transmuter(Level level) {
		this.level=level;
	}
	
	public String getName() {
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

	public String getInformations() {
		Iterator<Vector2> keySetIterator = this.getTiles().keySet().iterator();
		String result;
		result="**********************************\n"+"Name:"+this.getName()+"\nClass:"+this.getaClass()+" Id:"+this.getMainTile()+"\nPrice:"+this.getPrice()+" Tech:"+this.getTechnology()+"\nResearch:"+this.getResearch()+" Size:"+this.getSize()+"\nActivable:"+this.isActivable()+" Activation:"+this.getActivationLevel()+" Visible:"+this.isShowed()+"\nUpgradable:"+((this.isUpgradable())?this.getUpgrade().getName():this.isUpgradable())+" Unlockable:"+((this.isUnlockable())?this.getUnlock().getName():this.isUnlockable())+"\nUpgrade Cycle:"+this.getUpgradeCycle()+" upgrade:"+this.isUpgradableCycle()+"\nUpgrade Temperature:"+this.getUpgradeTemp()+" upgrade:"+this.isUpgradableTemp()+"\nUpgrade Nrj:"+this.getUpgradeNrj()+" upgrade:"+this.isUpgradableNrj()+"\nUpgrade Rayon:"+this.getUpgradeRayon()+" upgrade:"+this.isUpgradableRayon()+"\nTemperature /turn:"+this.getTurnTemp()+" Rayon /turn:"+this.getTurnRayon()+" Nrj /turn:"+this.getTurnNrj()+"\nTemperature /use:"+this.getUsedTemp()+" Rayon /use:"+this.getUsedRayon()+" Nrj /use:"+this.getUsedNrj()+"\n";
    	while(keySetIterator.hasNext()){
    	  Vector2 key = keySetIterator.next();
    	  result+="coords:" + key + " type: " + this.getTiles().get(key);
    	}
    	result+="\n**********************************";
		return result;
	
	}
}