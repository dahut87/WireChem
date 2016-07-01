package fr.evolving.database;

import com.badlogic.gdx.utils.Array;

import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;

public abstract class Base {
	public enum datatype {
		statdata, userdata, gamedata
	}

	public Base(datatype model, String param) {
	}

	public Base() {
	}

	public String getParam() {
		return null;
	}

	// Gestion type Gamebase

	public Array<String> getCampaigns() {
		return null;
	}

	public Array<Level> getCampaign(String description) {
		return null;
	}

	public boolean setCampaign(Array<Level> campaign, String description) {
		return false;
	}

	public boolean deleteCampaign(String description) {
		return false;
	}

	// Gestion type Userbase
	public boolean getLevellock(int user, int level) {
		return false;
	}

	public boolean setLevelunlock(int user, int level, boolean state) {
		return false;
	}

	public Array<Transmuter> getTransmuters(int user) {
		return null;
	}

	public boolean setTransmuters(int user, Array<Transmuter> transmuters) {
		return false;
	}

	public int getResearchpoint(int user) {
		return 0;
	}

	public boolean setResearchpoint(int user, int point) {
		return false;
	}

	public Grid getGrid(int user, int level, int place) {
		return null;
	}

	public Grid getGrid(int user, int level, String tag) {
		return null;
	}

	public boolean setGrid(int user, int level, String tag, Grid data) {
		return false;
	}

	public boolean setGrid(int user, int level, Grid data) {
		return false;
	}

	public Array<String> getGrids(int user, int level) {
		return null;
	}

	// Gestion type Stat

	// Commun

	public boolean Eraseall(datatype base) {
		return false;
	}

	public static boolean isHandling(datatype base) {
		return false;
	}

	public void Close() {
	}

	public String getPrefix() {
		return "";
	}

}
