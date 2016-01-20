package fr.evolving.automata;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.OrderedMap;

import fr.evolving.automata.Transmuter.CaseType;

public class Insufler33 extends Transmuter {
	private static String Name, Desc;
	private static Class theClass;
	private static int Price;
	private static int Technology;
	private static int Research;
	private static Transmuter Upgrade;
	private static Transmuter Unlock;
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
	private int ActivationLevel;
	private int Rotation;
	private static OrderedMap<Vector2, CaseType> Tilestype;
	private static OrderedMap<Vector2, Integer> Tilesid;

	public Insufler33(Level level) {
		super(level);
		this.Name = "Insufleur 33%";
		this.Desc = "Insufleur 33% avecave aveca vecavec avec avec avec avecavecavecavec avec avecavecavec avec avecavecavecavec avec";
		this.theClass = Class.Direction;
		this.Price = 50;
		this.Technology = 2;
		this.Research = 0;
		this.Upgrade = new Positiver_II(level);
		this.Unlock = null;
		this.showed = true;
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
		this.Tilestype.put(new Vector2(-1, 1), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(0, 1), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(1, 1), CaseType.Rien);
		this.Tilestype.put(new Vector2(-1, 0), CaseType.Rien);
		this.Tilestype.put(new Vector2(0, 0), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(1, 0), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(-1, -1), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(0, -1), CaseType.Cuivre);
		this.Tilestype.put(new Vector2(1, -1), CaseType.Rien);
		this.Tilesid = new OrderedMap<Vector2, Integer>();
		this.Tilesid.put(new Vector2(-1, 1), 120);
		this.Tilesid.put(new Vector2(0,1), 121);
		this.Tilesid.put(new Vector2(1, 1), 122);
		this.Tilesid.put(new Vector2(-1, 0), 123);
		this.Tilesid.put(new Vector2(0, 0), 129);
		this.Tilesid.put(new Vector2(1, 0), 125);
		this.Tilesid.put(new Vector2(-1, -1), 126);
		this.Tilesid.put(new Vector2(0, -1), 127);
		this.Tilesid.put(new Vector2(1, -1), 128);
	}

	public String getName() {
		return this.Name;
	}

	public String getDesc() {
		return this.Desc;
	}

	public Class getaClass() {
		return this.theClass;
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

	public void Unlock() {
		if (this.Unlock == null)
			return;
		this.Unlock.SetShowed(true);
	}

	public void Upgrade() {
		if (this.Upgrade == null)
			return;
		this.Unlock.SetShowed(true);
		this.SetShowed(false);
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
		Iterator<Vector2> tiles = this.Tilesid.keys();
		while (tiles.hasNext()) {
			Vector2 key = tiles.next();
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
		return ActivationLevel = (int) (10 * this.UpgradedCycle);
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

	public boolean isUpgradable() {
		return this.Upgrade != null && this.Upgrade.isShowed();
	}

	public boolean isUnlockable() {
		return this.Unlock != null && this.Unlock.isShowed();
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

	public Transmuter getUnlock() {
		return this.Unlock;
	}

}
