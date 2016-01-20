package fr.evolving.UI;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Transmuter.Angular;

public class Menu extends Actor {

	private TiledMap[][] map;
	private OrthogonalTiledMapRenderer MapRenderer;
	private OrthographicCamera camera;
	private int tilesizex;
	private int tilesizey;
	private int nbpages;
	private int selpage;
	private int seltype;
	private float decx;
	private float decy;
	private int size = 32;
	private Level level;

	public Menu(Level level) {
		this.tilesizex = 4;
		this.tilesizey = 8;
		this.nbpages=3;
		this.selpage=0;
		this.seltype=0;
		this.level=level;
		map = new TiledMap[3][Transmuter.Class.values().length];
		clear();
		MapRenderer = new OrthogonalTiledMapRenderer(map[selpage][seltype], 1 / (float) size);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, tilesizex * 32, tilesizex * 32
				* AssetLoader.height / AssetLoader.width);
		Gdx.app.debug(getClass().getSimpleName(), "Caméra pour tilemap:"
				+ (tilesizex * size) + "x" + (tilesizey * size));
		decx = -102f;
		decy = -20f;
		if (AssetLoader.ratio == 1.44f) decy -= 24;
		camera.translate(decx, decy);
		Gdx.app.debug(getClass().getSimpleName(), "Décalage:" + decx + "x"+ decy);
		Gdx.app.debug(getClass().getSimpleName(), "Ajout des éléments de menu");
		init();
	}
	
	public void setPage(int page) {
		this.selpage=page;
		this.MapRenderer.setMap(map[selpage][seltype]);
		EraseSurtile();
	}
	
	public void NextPage() {
		if (this.selpage<nbpages-1)
			this.selpage++;
		this.MapRenderer.setMap(map[selpage][seltype]);
			EraseSurtile();
	}
	
	public void PreviousPage() {
		if (this.selpage>0)
			this.selpage--;
		this.MapRenderer.setMap(map[selpage][seltype]);
		EraseSurtile();
	}
	
	public int getPage() {
		return this.selpage;
	}
	
	public void setType(int type) {
		this.seltype=type;
		this.MapRenderer.setMap(map[selpage][seltype]);
	}
	
	public int getType() {
		return this.seltype;
	}
	
	private void init() {
			this.setMenuTile(0, 7, 71, "copper_pen",0);
			this.setMenuTile(1, 7, 72, "copper_brush",0);
			this.setMenuTile(2, 7, 73, "copper_eraser",0);
			this.setMenuTile(1, 5, 70, "blank",0);
			this.setMenuTile(0, 6, 74, "fiber_pen",0);
			this.setMenuTile(1, 6, 75, "fiber_brush",0);
			this.setMenuTile(2, 6, 76, "fiber_eraser",0);
			this.setMenuTile(0, 5, 77, "transmuter_eraser",0);
			this.setMenuTile(2, 5, 78, "all_eraser",0);
			this.setMenuTile(3, 3, 79, "cleaner",0);
			this.setMenuTransmuter(0, 7, "Positiveur I", Angular.A00,0);
			this.setMenuTransmuter(2, 7, "Negativeur I", Angular.A00,0);
			this.setMenuTransmuter(0, 6, "Positiveur II", Angular.A00,0);
			this.setMenuTransmuter(2, 6, "Negativeur II", Angular.A00,0);
			this.setMenuTransmuter(0, 5, "Positiveur III", Angular.A00,0);
			this.setMenuTransmuter(1, 5, "Negativeur III", Angular.A00,0);
			this.setMenuTransmuter(0, 4, "Inverseur I", Angular.A00,0);
			this.setMenuTransmuter(1, 4, "Inverseur II", Angular.A00,0);
			this.setMenuTransmuter(0, 3, "Neutraliseur I", Angular.A00,0);
			this.setMenuTransmuter(1, 3, "Neutraliseur II", Angular.A00,0);
			this.setMenuTransmuter(0, 7, "Antiretour", Angular.A00,0);
			this.setMenuTransmuter(1, 6, "Distributeur", Angular.A00,1);
			this.setMenuTransmuter(1, 4, "Insufleur 33%", Angular.A00,0);			
			this.setMenuTransmuter(1, 1, "Insufleur 50%", Angular.A00,0);		
			this.setMenuTransmuter(1, 2, "Insufleur 100%", Angular.A00,1);		
			this.setMenuTransmuter(0, 7, "Positiveur non activable",Angular.A00,0);
			this.setMenuTransmuter(1, 7, "Negativeur non activable",Angular.A00,0);
	}

	public void clear() {
		for (int k=0;k<Transmuter.Class.values().length;k++)
			for (int j=0;j<nbpages;j++){
				map[j][k]=new TiledMap();
				map[j][k].getTileSets().addTileSet(AssetLoader.tileSet);
				MapLayers layers = map[j][k].getLayers();
				for (int i = 0; i < 3; i++) {
					TiledMapTileLayer layer = new TiledMapTileLayer(tilesizex,
							tilesizey, 128, 128);
					for (int x = 0; x < layer.getWidth(); x++) {
						for (int y = 0; y < layer.getHeight(); y++) {
							Cell cell = new Cell();
							if (i == 0)
								cell.setTile(AssetLoader.tileSet.getTile(54));
							layer.setCell(x, y, cell);
						}
					}
					layers.add(layer);
				}
				map[j][k].getLayers().get(1).setOpacity(0.5f);
			}
	}

	private void setMenuTile(int x, int y, int tile, String title, int page) {
		Cell cell = ((TiledMapTileLayer) map[page][0].getLayers().get(0)).getCell(x, y);
		if (cell != null) {
			cell.setTile(AssetLoader.tileSet.getTile(tile));
			cell.getTile().getProperties().put("name", title);
			cell.setRotation(0);
			Gdx.app.debug(getClass().getSimpleName(), "Tile find:" + tile
					+ " coords" + x + "," + y);
		}
	}

	public void setSurtile(int x, int y, Transmuter transmuter) {
		if (transmuter != null) {
			Cell cell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(1)).getCell(x,y);
			OrderedMap<Vector2, Integer> tiles = transmuter.getTilesidrotated();
			Entries<Vector2, Integer> iterator = tiles.iterator();
			while (iterator.hasNext()) {
				Entry<Vector2, Integer> all = iterator.next();
				Cell subcell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(1))
						.getCell((int) (x + all.key.x), (int) (y + all.key.y));
				subcell.setTile(AssetLoader.tileSet.getTile(transmuter
						.getTilestype(
								tiles.keys().toArray().indexOf(all.key, false))
						.ordinal() + 80));
			}

		}
	}

	public void EraseSurtile() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map[selpage][seltype].getLayers().get(1);
		for (int x = 0; x < layer.getWidth(); x++)
			for (int y = 0; y < layer.getHeight(); y++)
				layer.getCell(x, y).setTile(null);
	}

	public int getSizeX() {
		return tilesizex;
	}

	public int getSizeY() {
		return tilesizey;
	}

	private void setMenuTransmuter(int x, int y, String Name, Transmuter.Angular Angle,int page) {
		Transmuter transmuter = AssetLoader.getTransmuter(Name);
		if (transmuter != null) {
			int type=transmuter.getaClass().ordinal();
			Cell cell = ((TiledMapTileLayer) map[page][type].getLayers().get(0)).getCell(x, y);
			if (cell != null) {

				Gdx.app.debug(getClass().getSimpleName(), "Transmuter find:"
						+ transmuter.getName() + " Angle:" + Angle + " coords"
						+ x + "," + y+" page:"+page+" type:"+type);
				if (transmuter.getTechnology()<=level.Tech) {
					Gdx.app.debug(getClass().getSimpleName(), "Autorisé par le niveau");
					transmuter.setRotation(Angle);
					Iterator<Entry<Vector2, Integer>> keySetIterator = transmuter
							.getTilesidrotated().iterator();
					while (keySetIterator.hasNext()) {
						Entry<Vector2, Integer> all = keySetIterator.next();
						Cell subcell = ((TiledMapTileLayer) map[page][type].getLayers().get(0))
								.getCell((int) (x + all.key.x),
										(int) (y + all.key.y));
						subcell.setTile(AssetLoader.tileSet.getTile(all.value));
						subcell.setRotation(Angle.ordinal());
						subcell.getTile().getProperties()
						.put("movetox", (int) -all.key.x);
						subcell.getTile().getProperties()
						.put("movetoy", (int) -all.key.y);
					}
				}

			}
		}
	}

	public MapProperties getMenubyTile(int x, int y) {
		Cell cell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(0)).getCell(x, y);
		if (cell != null)
			return cell.getTile().getProperties();
		else
			return null;
	}

	public Vector2 screentoworld(float x, float y) {
		int xx = (int) ((x - 1531f) / 60f);
		int yy = (int) ((y - (AssetLoader.height - 776f)) / 60f);
		return new Vector2(xx, yy);
	}

	public Vector2 worldtoscreen(int x, int y) {
		float xx = 1531.0f + x * 60f;
		float yy = AssetLoader.height - 776.0f + y * 60f;
		return new Vector2(xx, yy);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		camera.update();
		MapRenderer.setView(camera);
		MapRenderer.render();
		batch.begin();
	}

}
