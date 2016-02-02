package fr.evolving.automata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import fr.evolving.assets.AssetLoader;
import fr.evolving.assets.InitWorlds;
import fr.evolving.assets.Preference;

public class Worlds extends Actor {
	private ChangeEvent event;
	private Array<Level> levels;
	private String name;
	private int usedworld;
	private Level usedlevel;
	private Array<Transmuter> Transmuters;
	private State state;
	private boolean Debug;
	private int research;
	
	public enum State {pause,simulating,notloaded,databasefailed};
	
	public Worlds(String campaign) {
		name=campaign;
		initialize();
	}
	
	public void initialize() {
		Debug=false;
		levels=null;
		usedworld=-1;
		research=-2;
		usedlevel=null;
		if (!AssetLoader.Datahandler.verifyall()) {
			Gdx.app.debug(getClass().getSimpleName(),"Pilotes de bases de donnée défaillant.");
			state=State.databasefailed;
		}
		else
			state=State.notloaded;
		this.load(name);
		this.ReadTransmuters();
		if (state==State.notloaded)
			create(name);
		onchanged();
	}
	
	public void ModResearch(int addsub) {
		research+=addsub;
		SaveResearch();
	}
	
	public int ReadResearch() {
		if (research==-2)
			research=AssetLoader.Datahandler.user().getResearchpoint(0);
		return research;
	}
	
	public void SaveResearch() {
		AssetLoader.Datahandler.user().setResearchpoint(0,research);
	}
	
	public void ActivateDebug() {
		this.Debug=true;
	}
	
	public void DesactivateDebug() {
		this.Debug=false;
	}
	
	public boolean isDebug() {
		return this.Debug;
	}
	
	public void SaveTransmuters() {
		AssetLoader.Datahandler.user().setTransmuters(0,Transmuters);
	}
	
	public void ReadTransmuters() {
		Transmuters=AssetLoader.Datahandler.user().getTransmuters(0);
		//String test=Transmuters.get(1).getInformations();
		if (Transmuters==null)
			state=State.notloaded;
		else
			state=State.pause;
	}
	
	public Array<String> ViewGrids() {
		if (usedlevel!=null)
			return AssetLoader.Datahandler.user().getGrids(0,usedlevel.id);
		else
			return null;
	}
	
	public void ReadGrid(int number) {
		if (usedlevel!=null)
			usedlevel.Grid = AssetLoader.Datahandler.user().getGrid(0,	usedlevel.id, number);
	}
	
	public void SaveGrid() {
		if (usedlevel!=null)
			AssetLoader.Datahandler.user().setGrid(0, usedlevel.id, usedlevel.Grid);
	}
	
	public void ReadLastGrid() {
		if (usedlevel!=null)
			usedlevel.Grid = AssetLoader.Datahandler.user().getGrid(0,	usedlevel.id, "LAST");
	}
	
	public void SaveLastGrid() {
		if (usedlevel!=null)
			AssetLoader.Datahandler.user().setGrid(0, usedlevel.id, "LAST",	usedlevel.Grid);
	}
	
	public void Forcereload() {
		onchanged();
	}
	
	public void onchanged() {
		ChangeEvent event=new ChangeEvent();
		event.setTarget(this);
		event.setListenerActor(this);	
		event.setStage(this.getStage());
		if (event.getStage()!=null) 
			this.fire(event);
	}
	
	public Array<Level> getLevels() {
		Array<Level> tempworld=new Array<Level>();
		if (state!=State.notloaded && this.levels!=null)
		{
			for(Level level:levels)
				if (level!=null && level.aWorld==usedworld)
				{
					if (level.aLevel==0)
						level.Locked=false;
					tempworld.add(level);
				}
			return tempworld;
		}
		else
			return null;
	}
	
	public void updateUnlockLevels() {
		if (levels!=null)
		for(Level level:levels)
			if (level!=null)
			level.Locked=AssetLoader.Datahandler.user().getLevellock(0, level.id);
	}
	
	public State getState() {
		return state;
	}
	
	public void prepareLevel(boolean force) {
		Gdx.app.debug(getClass().getSimpleName(),"Récupération des conditions initiales.");
		usedlevel.Cout=usedlevel.Cout_orig;
		usedlevel.Cycle=usedlevel.Cycle_orig;
		usedlevel.Temp=usedlevel.Temp_orig;
		usedlevel.Rayon=usedlevel.Rayon_orig;
		usedlevel.Nrj=usedlevel.Nrj_orig;
		usedlevel.Victory=usedlevel.Victory_orig.clone();	
		Gdx.app.debug(getClass().getSimpleName(),"Récupération des derniers niveaux.");
		ReadLastGrid();
		if (usedlevel.Grid == null || force) {
			Gdx.app.debug(getClass().getSimpleName(), "Copie monde original.");
			usedlevel.Grid = usedlevel.Grid_orig;

		} else {
			Gdx.app.debug(getClass().getSimpleName(),
					"Récupération de la dernière grille.");
			usedlevel.Grid.tiling_copper();
			usedlevel.Grid.tiling_transmuter();
		}
	}
	
	public void setLevel(int alevel) {
		if (state!=State.notloaded)
		if (usedworld>=0) {
			Array<Level> tempworld=getLevels();
			for(Level level:tempworld)
				if (level.aLevel==alevel)
				{
					usedlevel=level;
					return;
				}
		}
	}
	
	public Level getInformations() {
		return usedlevel;
	}
	
	public int getLevel() {
		if (usedlevel!=null)
			return usedlevel.aLevel;
		else
			return 0;
	}
	
	public void delLevel() {
		usedlevel=null;
	}
	
	public void setWorld(int world) {
		if (state!=State.notloaded)
		if (world<getMaxWorlds()) {
			delLevel();
			usedworld=world;
			onchanged();
		}
	}
	
	public void setMaxWorldLevel() {
		usedworld=getMaxUnlockWorlds();
		usedlevel=getMaxUnlockLevel();
		onchanged();
	}
	
	public boolean isFirstWorld() {
		return (usedworld==0);
	}
	
	public boolean isLastWorld() {
		return (usedworld==getMaxUnlockWorlds());
	}
	
	public boolean isRealLastWorld() {
		return (usedworld==getMaxWorlds());
	}
	
	
	public void NextWorld() {
		if (state!=State.notloaded)
		if (usedworld<getMaxWorlds()) {
			delLevel();
			usedworld++;
			onchanged();
		}
	}
	
	public void PreviousWorld() {
		if (state!=State.notloaded)
		if (usedworld>0) {
			delLevel();
			usedworld--;
			onchanged();
		}
	}
	
	public int getWorld() {
		if (state!=State.notloaded)
			return usedworld;
		else
			return -1;
	}
	
	public void set(String campaign) {
		Gdx.app.log("*****", "Définition de la compagne "+campaign);
		Preference.prefs.putString("world", campaign);
		Preference.prefs.flush();
		load(campaign);
	}
	
	public void load(String campaign) {
		Gdx.app.log("*****", "Chargement de la compagne "+campaign);
		levels=AssetLoader.Datahandler.game().getCampaign(campaign);
		updateUnlockLevels();
		name=campaign;
		if (levels==null)
			state=State.notloaded;
		else
			state=State.pause;
	}
	
	public void create(String campaign) {
		Gdx.app.log("*****", "initialisation de la compagne "+campaign);
		try {
			levels=InitWorlds.go();
			Preference.prefs.putString("world",campaign);
			Preference.prefs.flush();
			name=campaign;
			AssetLoader.Datahandler.game().setCampaign(levels,name);
			state=State.pause;
			research=0;
			Transmuters=AssetLoader.allTransmuter;
			SaveTransmuters();
			SaveResearch();
		}
		catch (Exception e) {
			state=State.notloaded;
		}
	}
	
	public void save(String campaign) {
		Gdx.app.log("*****", "enregistrement de la compagne "+campaign);
		AssetLoader.Datahandler.game().setCampaign(levels,campaign);
	}
	
	public int getMaxWorlds() {
		int max = 0;
		for (Level level : levels)
			if (level != null && level.aWorld > max)
				max = level.aWorld;
		return max;
	}
	
	public int getMaxUnlockWorlds() {
		int maxworld=0;
		for (Level level : levels)
			if (!level.Locked && level.aWorld>maxworld)
				maxworld=level.aWorld;
		return maxworld;
	}
	
	public Level getMaxUnlockLevel() {
		Array<Level> tempworld=getLevels();
		int maxlevel=0;
		Level themaxlevel=null;
		for (Level level : tempworld)
			if (!level.Locked && level.aLevel>maxlevel) {
				maxlevel=level.aLevel;
				themaxlevel=level;
			}
		return themaxlevel;
	}
	
	public String getName() {
		return this.name;
	}

}
