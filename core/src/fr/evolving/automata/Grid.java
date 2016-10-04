package fr.evolving.automata;

import java.io.Serializable;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Particle.Charge;
import fr.evolving.automata.Particle.Orientation;
import fr.evolving.automata.Particle.Size;
import fr.evolving.automata.Particle.Type;
import fr.evolving.automata.Transmuter.CaseType;

public class Grid implements Serializable,Cloneable {
	protected Cell[][] Cells;
	public Integer sizeX, sizeY;
	
	private transient Array<Particle> particles;
	private transient Array<Transmuter> transmuters;
	private transient Array<Vector2> transmuterscoords;
	
	public Grid(Integer X, Integer Y) {
		Reinit();
		this.sizeX = X;
		this.sizeY = Y;
		this.Cells = new Cell[this.sizeX][this.sizeY];
		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				this.Cells[x][y] = new Cell();
			}
		}
	}
	
	public void Reinit() {
		if (particles==null)
			particles=new Array<Particle>();
		if (transmuters==null)
			transmuters=new Array<Transmuter>();
		if (transmuterscoords==null)
			transmuterscoords=new Array<Vector2>();
	}
	
	//Réalise un cycle de simulation dans la grille
	public void Cycle() {
		for(Transmuter transmuter:transmuters) {
			transmuter.ProcessCycle();
		}
		for(Particle particle: particles) {
			Gdx.app.debug("wirechem-Grid", "Grid Cycle -> Particle "+particle.getType()+"/"+particle.getSize()+ " coords:"+particle.getCoordx()+","+particle.getCoordy()+"/"+particle.getOrientation()+" charge:"+particle.getCharge());
			if (particle.getType()==Type.Photon) {
				particle.Next();
				for(int i=0;i<transmuters.size;i++) {
					OrderedMap<Vector2, Integer> tiles = transmuters.get(i).getTilesidrotated();
					Entries<Vector2, Integer> iterator = tiles.iterator();
					while (iterator.hasNext()) {
						Entry<Vector2, Integer> all = iterator.next();
						CaseType thecase=transmuters.get(i).getTilestype(tiles.keys().toArray().indexOf(all.key, false));
						Vector2 position=transmuterscoords.get(i);
						if (GetXY(position.x+all.key.x,position.y+all.key.y).Fiber && position.x+all.key.x==particle.getCoordx() && position.y+all.key.y==particle.getCoordy())
							if (thecase==CaseType.Fibre||thecase==CaseType.Tout||thecase==CaseType.Fibre_seul) {
								int needed=transmuters.get(i).getMaxActivationLevel()-transmuters.get(i).getActivationLevel();
								Gdx.app.debug("wirechem-Grid", "Grid Cycle -> Activation ? Photon life:"+particle.getLife()+" coords:"+particle.getCoordx()+","+particle.getCoordy()+" Transmuter:"+transmuters.get(i).getName()+" activation:"+transmuters.get(i).getActivationLevel()+" necessaire:"+needed);
								if (needed<=particle.getLife()) {
									particle.subLife(needed);
									transmuters.get(i).Activate();
									Gdx.app.debug("wirechem-Grid", "Grid Cycle -> Activation OUI Photon life:"+particle.getLife()+" coords:"+particle.getCoordx()+","+particle.getCoordy()+" Transmuter:"+transmuters.get(i).getName()+" activation:"+transmuters.get(i).getActivationLevel());
								}
								else
									Gdx.app.debug("wirechem-Grid", "Grid Cycle -> Activation NON Photon life:"+particle.getLife()+" coords:"+particle.getCoordx()+","+particle.getCoordy()+" Transmuter:"+transmuters.get(i).getName()+" activation:"+transmuters.get(i).getActivationLevel());
							}
					}
				}
			}	
			else if (particle.getType()==Type.Electron) {
				particle.Next();
				Transmuter trans=GetXY(particle.getCoordx(),particle.getCoordy()).Transmuter;
				if (trans!=null && trans.getActivation())
					trans.Run(particle);
			}
			if (!particle.isAlive()) {
				Gdx.app.debug("wirechem-Particle", "coords:"+particle.getCoordx()+","+particle.getCoordy()+" killed & removed");
				particles.removeValue(particle, true);
			}
		}
	}
	
	//Affiche le cycle en cours à l'écran
	public void tiling_particle() {
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++)
				GetXY(x, y).Fiber_state = 0;
		for(Particle particle: particles) {
			if (particle.getType()==Type.Photon) {
				GetXY(particle.getCoordx(), particle.getCoordy()).Fiber_state=1+Math.floorDiv(Particle.PHOTONLIFE-particle.getLife(),Math.floorDiv(Particle.PHOTONLIFE,10));
				Gdx.app.debug("wirechem-Grid", "Grid Tiling -> Photon state :"+GetXY(particle.getCoordx(), particle.getCoordy()).Fiber_state+":"+particle.getCoordx()+","+particle.getCoordy());
			}
			else if (particle.getType()==Type.Electron) {
				int value=16;
				if (particle.getCharge()==Charge.Positif) value+=1;
				if (particle.getCharge()==Charge.Neutre) value+=2;
				if (particle.getSize()==Size.Petit) value+=3;
				GetXY(particle.getCoordx(), particle.getCoordy()).Fiber_state+=1000*value;
				Gdx.app.debug("wirechem-Grid", "Grid Tiling -> Electron state :"+GetXY(particle.getCoordx(), particle.getCoordy()).Fiber_state+":"+particle.getCoordx()+","+particle.getCoordy());
			}
		}
	}
	
	//Initialise la simulation pour permettre ensuite de faire des cycles
	public void Initialize(Level level) {
		particles.clear();
		this.tiling_particle();
		particles.add(new Particle(this));
		particles.get(0).setType(Type.Photon);
		particles.get(0).setCoordx(6);
		particles.get(0).setCoordy(3);
		particles.get(0).setOrientation(Orientation.E);
		particles.add(new Particle(this));
		particles.get(1).setType(Type.Electron);
		particles.get(1).setSize(Size.Gros);
		particles.get(1).setCharge(Charge.Negatif);
		particles.get(1).setCoordx(7);
		particles.get(1).setCoordy(13);
		particles.get(1).setOrientation(Orientation.O);
		transmuters.clear();
		transmuterscoords.clear();		
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++)
				if (GetXY(x, y).Transmuter!=null) {
					transmuters.add(GetXY(x, y).Transmuter);
					transmuterscoords.add(new Vector2(x,y));
				}
		for(Transmuter transmuter:transmuters) {
			transmuter.Unactivate();
		}
		if (transmuters.size>0) transmuters.first().AttachLevel(level);
	}

	//Genère des tiles qui correspondent aux transmuteurs sur la grille
	public int tiling_transmuter() {
		int result=0;
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++)
			{
				GetXY(x, y).Transmuter_calc = 0;
				if (GetXY(x, y).Transmuter!=null && !GetXY(x, y).Free)
					result+=GetXY(x, y).Transmuter.getPrice();
			}
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				Transmuter transmuter = getTransmuter(x, y);
				if (transmuter != null) {
					Iterator<Entry<Vector2, Integer>> tiles = transmuter.getTilesidrotated().iterator();
					while (tiles.hasNext()) {
						Entry<Vector2, Integer> all = tiles.next();
						Cell cell=GetXY(x + all.key.x, y + all.key.y);
						if (cell!=null) {
							cell.Transmuter_calc = (1 << 16)	* transmuter.getRotation().ordinal()+ all.value;
							cell.Transmuter_movex = (int) -all.key.x;
							cell.Transmuter_movey = (int) -all.key.y;
						}
						else
						{
								result-=GetXY(x, y).Transmuter.getPrice();
								Iterator<Entry<Vector2, Integer>> tileseraser = transmuter.getTilesidrotated().iterator();
								while (tileseraser.hasNext()) {
									Entry<Vector2, Integer> allereaser = tileseraser.next();
									Cell celleraser=GetXY(x + allereaser.key.x, y + allereaser.key.y);
									if (celleraser!=null) {
									celleraser.Transmuter=null;
									celleraser.Transmuter_calc=0;
									celleraser.Transmuter_movex=0;
									celleraser.Transmuter_movey=0;
									}
								}
								break;
						}
					}
				}
			}
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				if (GetXY(x, y).Transmuter_calc > 0)
					Gdx.app.debug("wirechem-Grid", x + "," + y + ">"+ GetXY(x, y).Transmuter_calc);
			}
		return result;
	}

	//
	public int tiling_copper() {
		int result=0;
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				if (getFiber(x,y) && !GetXY(x, y).Free)
					result+=5;
				if (getCopper(x, y)) {
					if (!GetXY(x, y).Free)
					{
						result++;
						if (getFiber(x,y))
							result+=45;
					}
					int value = 0;
					if (getCopper(x, y + 1))
						value++;
					if (getCopper(x - 1, y))
						value += 8;
					if (getCopper(x, y - 1))
						value += 4;
					if (getCopper(x + 1, y))
						value += 2;
					GetXY(x, y).Copper_calc = value;
				} else
					GetXY(x, y).Copper_calc = -1;
			}
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				int value = 0;
				if (getCoppercalc(x, y) == 15) {
					if (getCopper(x - 1, y - 1))
						value++;
					if (getCopper(x, y - 1))
						value++;
					if (getCopper(x + 1, y - 1))
						value++;
					if (getCopper(x - 1, y))
						value++;
					if (getCopper(x + 1, y))
						value++;
					if (getCopper(x - 1, y + 1))
						value++;
					if (getCopper(x, y + 1))
						value++;
					if (getCopper(x + 1, y + 1))
						value++;
					if (value >= 5)
						GetXY(x, y).Copper_calc = GetXY(x, y).Copper_calc + 20;
				} else {
					if (getCoppercalc(x, y) != -1) {
						int oldvalue = GetXY(x, y).Copper_calc;
						if (getCoppercalc(x - 1, y - 1) == 15
								|| getCoppercalc(x - 1, y - 1) == 35)
							value++;
						if (getCoppercalc(x, y - 1) == 15
								|| getCoppercalc(x, y - 1) == 35)
							value++;
						if (getCoppercalc(x + 1, y - 1) == 15
								|| getCoppercalc(x + 1, y - 1) == 35)
							value++;
						if (getCoppercalc(x - 1, y) == 15
								|| getCoppercalc(x - 1, y) == 35)
							value++;
						if (getCoppercalc(x + 1, y) == 15
								|| getCoppercalc(x + 1, y) == 35)
							value++;
						if (getCoppercalc(x - 1, y + 1) == 15
								|| getCoppercalc(x - 1, y + 1) == 35)
							value++;
						if (getCoppercalc(x, y + 1) == 15
								|| getCoppercalc(x, y + 1) == 35)
							value++;
						if (getCoppercalc(x + 1, y + 1) == 15
								|| getCoppercalc(x + 1, y + 1) == 35)
							value++;
						if (value >= 1 && oldvalue != 1 && oldvalue != 2
								&& oldvalue != 4 && oldvalue != 8
								&& oldvalue != 10 && oldvalue != 5)
							GetXY(x, y).Copper_calc = oldvalue + 20;
					}
				}
			}
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				if (getCoppercalc(x, y) == 35) {
					int value = 0;
					if (!getCopper(x + 1, y + 1))
						value += 2;
					if (!getCopper(x - 1, y - 1))
						value += 8;
					if (!getCopper(x + 1, y - 1))
						value += 4;
					if (!getCopper(x - 1, y + 1))
						value += 1;
					GetXY(x, y).Copper_calc = GetXY(x, y).Copper_calc + value;
				}
			}
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++) {
				int oldvalue = GetXY(x, y).Copper_calc;
				if (oldvalue == 27 || oldvalue == 31 || oldvalue == 33
						|| oldvalue == 34) {
					int value = 0;
					if (getCopper(x, y + 1) && getCoppercalc(x, y + 1) < 15)
						value += 1;
					if (getCopper(x - 1, y) && getCoppercalc(x - 1, y) < 15)
						value += 6;
					if (getCopper(x, y - 1) && getCoppercalc(x, y - 1) < 15)
						value += 2;
					if (getCopper(x + 1, y) && getCoppercalc(x + 1, y) < 15)
						value += 2;
					if (value > 0)
						GetXY(x, y).Copper_calc = oldvalue + 22 + value;
				}
				int value = 0;
				if (oldvalue == 34
						&& (getCoppercalc(x - 1, y) == 31
								|| getCoppercalc(x - 1, y) == 55 || getCoppercalc(
								x - 1, y) == 58))
					value = 62;
				if (oldvalue == 34
						&& (getCoppercalc(x + 1, y) == 31
								|| getCoppercalc(x + 1, y) == 55 || getCoppercalc(
								x + 1, y) == 58))
					value = 58;
				if (oldvalue == 31
						&& (getCoppercalc(x - 1, y) == 34
								|| getCoppercalc(x - 1, y) == 58 || getCoppercalc(
								x - 1, y) == 62))
					value = 59;
				if (oldvalue == 31
						&& (getCoppercalc(x + 1, y) == 34
								|| getCoppercalc(x + 1, y) == 58 || getCoppercalc(
								x + 1, y) == 62))
					value = 55;
				if (oldvalue == 33
						&& (getCoppercalc(x, y - 1) == 27
								|| getCoppercalc(x, y - 1) == 50 || getCoppercalc(
								x, y - 1) == 51))
					value = 57;
				if (oldvalue == 33
						&& (getCoppercalc(x, y + 1) == 27
								|| getCoppercalc(x, y + 1) == 50 || getCoppercalc(
								x, y + 1) == 51))
					value = 56;
				if (oldvalue == 27
						&& (getCoppercalc(x, y - 1) == 33
								|| getCoppercalc(x, y - 1) == 56 || getCoppercalc(
								x, y - 1) == 57))
					value = 51;
				if (oldvalue == 27
						&& (getCoppercalc(x, y + 1) == 33
								|| getCoppercalc(x, y + 1) == 56 || getCoppercalc(
								x, y + 1) == 57))
					value = 50;
				if (value > 0)
					GetXY(x, y).Copper_calc = value;

			}
		return result;
	}

	public Cell GetXY(float X, float Y) {
		if (X < 0 || Y < 0 || X >= this.sizeX || Y >= this.sizeY)
			return null;
		else
			return this.Cells[(int) X][(int) Y];
	}

	public Transmuter getTransmuter(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return null;
		else
			return cell.Transmuter;
	}

	public int getTransmutercalc(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return 0;
		else
			return cell.Transmuter_calc & 0xFFFF;
	}

	public int getTransmuterrot(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return 0;
		else
			return cell.Transmuter_calc >> 16;
	}

	public boolean getCopper(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return false;
		else
			return cell.Copper;
	}

	public boolean getFiber(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return false;
		else
			return cell.Fiber;
	}

	public int getCoppercalc(float X, float Y) {
		Cell cell = GetXY(X, Y);
		if (cell == null)
			return 0;
		else
			return cell.Copper_calc;
	}
	
	public Object clone() {
		Grid result = new Grid(this.sizeX,this.sizeY);
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++)
				result.Cells[x][y] = (Cell)this.Cells[x][y].clone();
		return result;
	}
	
	public Object clone(int newsizex,int newsizey) {
		if (newsizex<3) newsizex=3;
		if (newsizey<3) newsizey=3;		
		Grid result = new Grid(newsizex,newsizey);
		for (int x = 0; x < newsizex; x++)
			for (int y = 0; y < newsizey; y++)
				if (x<this.sizeX && y<this.sizeY)
					result.Cells[x][y] = (Cell)this.Cells[x][y].clone();
		return result;
	}

}
