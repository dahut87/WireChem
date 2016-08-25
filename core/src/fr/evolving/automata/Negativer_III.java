package fr.evolving.automata;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.OrderedMap;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Transmuter.Class;

public class Negativer_III extends Transmuter {
	private static String Name, Desc;
	private static Class theClass;
	private static int Price;
	private static int Technology;
	private static int Research;
	private static Transmuter Upgrade;
	private static Array<Transmuter> Unlock;
	private static boolean showed;
	private static boolean CanUpgradeTemp;
	private static boolean CanUpgradeCycle;
	private static boolean CanUpgradeRayon;
	private static boolean CanUpgradeNrj;
	private static float UpgradedTemp;
	private static float UpgradedCycle;
	private static float UpgradedRayon;
	private static float UpgradedNrj;
	private static float UsedTemp;
	private static float UsedRayon;
	private static float UsedNrj;
	private static float TurnTemp;
	private static float TurnRayon;
	private static float TurnNrj;
	private static boolean Activable;
	private transient int ActivationLevel;
	private static String id;
	private static OrderedMap<Vector2, CaseType> Tilestype;
	private static OrderedMap<Vector2, Integer> Tilesid;

	public Negativer_III(Level level) {
		super(level);
		this.id="---";
		this.Name = AssetLoader.language.get("[negativeriii-name]");
		this.Desc = AssetLoader.language.get("[negativeriii-desc]");
		this.theClass = Class.Charge;
		this.Price = 150;
		this.Technology = 4;
		this.Research = 120;
		this.Upgrade = null;
		this.Unlock = null;
		this.showed = false;
		this.CanUpgradeTemp = true;
		this.CanUpgradeCycle = true;
		this.CanUpgradeRayon = false;
		this.CanUpgradeNrj = false;
		this.UpgradedTemp = 1f;
		this.UpgradedCycle = 1f;
		this.UpgradedRayon = 1f;
		this.UpgradedNrj = 1f;
		this.UsedTemp = 0.5f;
		this.UsedRayon = 0f;
		this.UsedNrj = 0f;
		this.TurnTemp = 0f;
		this.TurnRayon = 0f;
		this.TurnNrj = 0f;
		this.Activable = true;
		this.ActivationLevel = 0;
		this.Tilestype = new OrderedMap<Vector2, CaseType>();
		this.Tilestype.put(new Vector2(0, 0), CaseType.Tout);
		this.Tilesid = new OrderedMap<Vector2, Integer>();
		this.Tilesid.put(new Vector2(0, 0), 103);
	}

	public String getName() {
		return this.Name;
	}
	
	public String getID() {
		return this.id;
	}

	public String getDesc() {
		return this.Desc;
	}

	public Class getaClass() {
		return this.theClass;
	}
	
	public void savestatic() {
		SetTemp(showed,UpgradedCycle,UpgradedTemp,UpgradedRayon,UpgradedNrj);
	}
	
	public void restorestatic() {
		this.UpgradedTemp = this.temp_UpgradedTemp;
		this.UpgradedCycle = this.temp_UpgradedCycle;
		this.UpgradedRayon = this.temp_UpgradedRayon;
		this.UpgradedNrj = this.temp_UpgradedNrj;
		this.showed=this.temp_showed;
	}
	
	public void ProcessCycle() {
		this.level.Temp += TurnTemp * UpgradedTemp;
		this.level.Rayon += TurnRayon * UpgradedRayon;
		this.level.Nrj += TurnNrj * UpgradedNrj;
		if (this.Activable)
			this.ActivationLevel -= 1;
	}

	public void Run() {
		this.level.Temp += UsedTemp * UpgradedTemp;
		this.level.Rayon += UsedRayon * UpgradedRayon;
		this.level.Nrj += UsedNrj * UpgradedNrj;
	}
	
	public void Unactivate() {
		if (this.Activable)
			ActivationLevel = 0;
	}
	
	public void Activate() {
		if (this.Activable)
			ActivationLevel = this.getMaxActivationLevel();
	}

	public void UpgradeTemp() {
		if (isUpgradableTemp())
			UpgradedTemp += -0.2f;
	}

	public void UpgradeNrj() {
		if (isUpgradableNrj())
			UpgradedNrj += -0.2f;
	}

	public void UpgradeRayon() {
		if (isUpgradableRayon())
			UpgradedRayon += -0.2f;
	}

	public void UpgradeCycle() {
		if (isUpgradableCycle())
			UpgradedCycle += 0.2f;
	}

	public Values<Integer> getTilesid() {
		return Tilesid.values();
	}

	public CaseType getTilestype(int order) {
		return Tilestype.values().toArray().get(order);
	}

	public OrderedMap<Vector2, Integer> getTilesidrotated() {
		OrderedMap<Vector2, Integer> newTiles = new OrderedMap<Vector2, Integer>();
		Iterator<Vector2> keySetIterator = this.Tilesid.keys();
		while (keySetIterator.hasNext()) {
			Vector2 key = keySetIterator.next();
			double delta = key.len();
			double alpha = key.angleRad() + this.getRotation().ordinal()
					* Math.PI / 2;
			newTiles.put(
					new Vector2((float) Math.round(delta * Math.cos(alpha)),
							(float) Math.round(delta * Math.sin(alpha))),
					this.Tilesid.get(key));
		}
		return newTiles;
	}

	public boolean isActivable() {
		return this.Activable;
	}

	public int getMaxActivationLevel() {
		return (int) (10 * this.UpgradedCycle);
	}

	public int getActivationLevel() {
		if (this.Activable)
			return ActivationLevel;
		else
			return -1;
	}

	public boolean getActivation() {
		if (this.Activable)
			return ActivationLevel > 0;
		else
			return true;
	}

	public int getPrice() {
		return Price;
	}

	public int getSize() {
		return (Tilesid.size);
	}

	public int getTechnology() {
		return Technology;
	}

	public int getResearch() {
		return Research;
	}

	public boolean isShowed() {
		return this.showed;
	}

	public void SetShowed(boolean value) {
		this.showed = value;
	}

	public boolean isUpgradableTemp() {
		return CanUpgradeTemp && getUpgradeTemp() < 3;
	}

	public boolean isUpgradableCycle() {
		return CanUpgradeCycle && getUpgradeCycle() < 3;
	}

	public boolean isUpgradableRayon() {
		return CanUpgradeRayon && getUpgradeRayon() < 3;
	}

	public boolean isUpgradableNrj() {
		return CanUpgradeNrj && getUpgradeNrj() < 3;
	}

	public int getUpgradeTemp() {
		return Math.abs((int) ((10 * UpgradedTemp - 10) / 2f));
	}

	public int getUpgradeCycle() {
		return Math.abs((int) ((10 * UpgradedCycle - 10) / 2f));
	}

	public int getUpgradeRayon() {
		return Math.abs((int) ((10 * UpgradedRayon - 10) / 2f));
	}

	public int getUpgradeNrj() {
		return Math.abs((int) ((10 * UpgradedNrj - 10) / 2f));
	}

	public float getUsedTemp() {
		return UsedTemp * UpgradedTemp;
	}

	public float getUsedRayon() {
		return UsedRayon * UpgradedRayon;
	}

	public float getUsedNrj() {
		return UsedNrj * UpgradedNrj;
	}

	public float getTurnTemp() {
		return TurnTemp * UpgradedTemp;
	}

	public float getTurnRayon() {
		return TurnRayon * UpgradedRayon;
	}

	public float getTurnNrj() {
		return TurnNrj * UpgradedNrj;
	}

	public Transmuter getUpgrade() {
		return this.Upgrade;
	}

	public Array<Transmuter> getUnlock() {
		return this.Unlock;
	}

}
