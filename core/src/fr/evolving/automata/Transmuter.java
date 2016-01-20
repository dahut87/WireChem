package fr.evolving.automata;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.OrderedMap;

public abstract class Transmuter implements Cloneable, Serializable {
	public enum CaseType {
		Rien, Cuivre_seul, Fibre_seul, Cuivre, Fibre, Tout, Nimporte
	};

	public enum Class {
		Structure, Charge, Direction, Filtrage, Synthese, Detection, Divers, Scenario
	};

	public enum Angular {
		A00, A90, A180, A270
	};

	protected Level level;
	protected Angular Rotation;

	public Transmuter(Level level) {
		this.level = level;
		this.Rotation = Angular.A00;
	}

	public String getName() {
		return "";
	}

	public String getDesc() {
		return "";
	}

	public Class getaClass() {
		return null;
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

	public boolean isTransmuter(String Name) {
		return Name.equals(this.getName());
	}

	public CaseType getTilestype(int order) {
		return null;
	}

	public OrderedMap<Vector2, Integer> getTilesidrotated() {
		return null;
	}

	public Values<Integer> getTilesid() {
		return null;
	}

	public int getMaxActivationLevel() {
		return 0;
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
		OrderedMap<Vector2, Integer> tiles = this.getTilesidrotated();
		Entries<Vector2, Integer> iterator = tiles.iterator();
		String result;
		result = "**********************************\n" + "Name:"
				+ this.getName()
				+ "\nClass:"
				+ this.getaClass()
				+ " Rotation:"
				+ this.getRotation()
				+ "\nPrice:"
				+ this.getPrice()
				+ " Tech:"
				+ this.getTechnology()
				+ "\nResearch:"
				+ this.getResearch()
				+ " Size:"
				+ this.getSize()
				+ "\nActivable:"
				+ this.isActivable()
				+ " Activation:"
				+ this.getActivationLevel()
				+ " Visible:"
				+ this.isShowed()
				+ "\nUpgradable:"
				+ ((this.isUpgradable()) ? this.getUpgrade().getName() : this
						.isUpgradable())
				+ " Unlockable:"
				+ ((this.isUnlockable()) ? this.getUnlock().getName() : this
						.isUnlockable()) + "\nUpgrade Cycle:"
				+ this.getUpgradeCycle() + " upgrade:"
				+ this.isUpgradableCycle() + "\nUpgrade Temperature:"
				+ this.getUpgradeTemp() + " upgrade:" + this.isUpgradableTemp()
				+ "\nUpgrade Nrj:" + this.getUpgradeNrj() + " upgrade:"
				+ this.isUpgradableNrj() + "\nUpgrade Rayon:"
				+ this.getUpgradeRayon() + " upgrade:"
				+ this.isUpgradableRayon() + "\nTemperature /turn:"
				+ this.getTurnTemp() + " Rayon /turn:" + this.getTurnRayon()
				+ " Nrj /turn:" + this.getTurnNrj() + "\nTemperature /use:"
				+ this.getUsedTemp() + " Rayon /use:" + this.getUsedRayon()
				+ " Nrj /use:" + this.getUsedNrj() + "\nTiles:";
		Values<Integer> allTiles = this.getTilesid().iterator();
		while (allTiles.hasNext())
			result += String.valueOf(allTiles.next()) + " ";
		while (iterator.hasNext()) {
			Entry<Vector2, Integer> all = iterator.next();
			result += "\ncoords:"
					+ all.key.x
					+ ","
					+ all.key.y
					+ " type: "
					+ this.getTilestype(tiles.keys().toArray()
							.indexOf(all.key, false)) + " id:" + all.value;
		}
		result += "\n**********************************";
		return result;

	}

	public Angular getRotation() {
		return this.Rotation;
	}

	public void setRotation(Angular rotation) {
		this.Rotation = rotation;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}
}