package fr.evolving.UI;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Transmuter.Angular;
import fr.evolving.automata.Worlds;

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
	private Actor selected;
	private Transmuter selected_transmuter;
	private TextureRegion oneselection;
	private Timer RotateTimer;
	private TimerTask RotateTask;
	private float rotation;
	ChangeEvent event;
	Worlds worlds;

	public Menu(Worlds worlds) {
		this.worlds=worlds;
		this.tilesizex = 4;
		this.tilesizey = 8;
		this.nbpages=3;
		this.selpage=0;
		this.seltype=0;

		Gdx.app.debug("wirechem-Menu", "Création du Tiledmap et Maprenderer");
		map = new TiledMap[3][Transmuter.Class.values().length];
		initialize();
		MapRenderer = new OrthogonalTiledMapRenderer(map[selpage][seltype], 1 / (float) size);

		Gdx.app.debug("wirechem-Menu", "Caméra pour tilemap:"+ (tilesizex * size) + "x" + (tilesizey * size));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, tilesizex * 32, tilesizex * 32	* AssetLoader.height / AssetLoader.width);
		decx = -102f;
		decy = -20f;
		if (AssetLoader.ratio == 1.44f) decy -= 24;
		Gdx.app.debug("wirechem-Menu", "Décalage:" + decx + "x"+ decy);
		camera.translate(decx, decy);


		Gdx.app.debug("wirechem-Menu", "Ajout des éléments de menu");
		update();

		Gdx.app.debug("wirechem-Menu", "Mise en place du timer de rotation.");		
		oneselection = AssetLoader.Atlas_level.findRegion("circle");
		selected = new Actor();
		rotation=0;
		RotateTimer = new Timer();
		RotateTask = new TimerTask() {
			@Override
			public void run() {
				rotation += 5;
			}
		};
		RotateTimer.scheduleAtFixedRate(RotateTask, 0, 30);

		Gdx.app.debug("wirechem-Menu", "Ajout de l'évènements clicked");		
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Vector2 coords = screentoworld(x, y);
				MapProperties tile = getMenubyTile((int) coords.x,(int) coords.y);
				if (tile != null && tile.containsKey("name")) {
					selected_transmuter=null;
					EraseSurtile();
					if (selected==null)
						selected=new Actor();
					if (tile.get("type").toString().startsWith("transmuter")) {
						if (tile.containsKey("movetox")) {
							coords.x += (Integer) tile.get("movetox");
							coords.y += (Integer) tile.get("movetoy");
						}
						MapProperties tilenew = getMenubyTile((int) coords.x, (int) coords.y);
						selected_transmuter = (Transmuter) ((Transmuter) tilenew.get("transmuter")).clone();
						if (selected_transmuter != null) {
							selected.setName("transmuter");
							setSurtile((int) coords.x,(int) coords.y, selected_transmuter);
							Gdx.app.debug("wirechem-Menu", "Choix transmuter:"+ selected_transmuter.getName());
						}
					} 
					else
						selected.setName(tile.get("name").toString());
					Vector2 coords2 = worldtoscreen((int) coords.x,
							(int) coords.y);
					Gdx.app.debug("wirechem-Menu","Coordonnées:" + x + "x" + y + " Menu:" + coords.x
							+ "," + coords.y + " Ecran :" + coords2.x
							+ "x" + coords2.y + " type:"
							+ tile.get("type")+" selection:"+getSelection());
					selected.setBounds(coords2.x, coords2.y, 60, 60);
					onchanged();
				}
			}
		});
	}

	public void unSelect() {
		selected=null;
		selected_transmuter=null;
		EraseSurtile();
	}

	public String getSelection() {
		if (selected!=null)
			return selected.getName();
		else
			return null;
	}

	public Transmuter getTransmuter() {
		return selected_transmuter;
	}

	public void onchanged() {
		ChangeEvent event=new ChangeEvent();
		event.setTarget(this);
		event.setListenerActor(this);	
		event.setStage(this.getStage());
		if (event.getStage()!=null) 
			this.fire(event);
	}

	public void setPage(int page) {
		selected=null;
		this.selpage=page;
		this.MapRenderer.setMap(map[selpage][seltype]);
		EraseSurtile();
		onchanged();
	}

	public void setPageType(int page,int type) {
		selected=null;
		this.selpage=page;
		this.seltype=type;
		this.MapRenderer.setMap(map[selpage][seltype]);
		EraseSurtile();
		onchanged();
	}

	public boolean isNextEmpty() {
		if (this.selpage>=this.nbpages-2) return true;
		TiledMapTileLayer layer=(TiledMapTileLayer)map[selpage+1][seltype].getLayers().get(0);
		boolean test=layer.getProperties().containsKey("noempty");
		return (!layer.getProperties().containsKey("noempty"));
	}

	public boolean isPreviousEmpty() {
		if (this.selpage<1) return true;
		TiledMapTileLayer layer=(TiledMapTileLayer)map[selpage-1][seltype].getLayers().get(0);
		return (!layer.getProperties().containsKey("noempty"));
	}

	public void NextPage() {
		if (this.selpage<nbpages-1) {
			selected=null;
			this.selpage++;
			this.MapRenderer.setMap(map[selpage][seltype]);
			EraseSurtile();
			onchanged();
		}
	}

	public void PreviousPage() {
		if (this.selpage>0) {
			selected=null;
			this.selpage--;
			this.MapRenderer.setMap(map[selpage][seltype]);
			EraseSurtile();
			onchanged();
		}
	}

	public int getPage() {
		return this.selpage;
	}

	public int getMaxPage() {
		return this.nbpages;
	}

	public void setType(int type) {
		this.seltype=type;
		selected=null;
		this.MapRenderer.setMap(map[selpage][seltype]);
		onchanged();
	}

	public int getType() {
		return this.seltype;
	}

	public void update() {
		clearall();
		if (worlds.isDebug()) 
		{
			this.setMenuTile(0, 4, 87, "gold_pen",0);
			this.setMenuTile(1, 4, 88, "lock_pen",0);
		}
		if (worlds.getLevelData().Cout_orig>=0 || worlds.isDebug())
		{
			this.setMenuTile(0, 7, 71, "copper_pen",0);
			this.setMenuTile(1, 7, 72, "copper_brush",0);
			this.setMenuTile(2, 7, 73, "copper_eraser",0);
			this.setMenuTile(3, 3, 79, "cleaner",0);
		}
		if (worlds.getLevelData().Tech>=0 || worlds.isDebug())
		{
			this.setMenuTile(0, 5, 77, "transmuter_eraser",0);
		}
		if (worlds.getLevelData().Tech>=2 || worlds.isDebug())
		{
			this.setMenuTile(1, 5, 70, "blank",0);
			this.setMenuTile(0, 6, 74, "fiber_pen",0);
			this.setMenuTile(1, 6, 75, "fiber_brush",0);
			this.setMenuTile(2, 6, 76, "fiber_eraser",0);
			this.setMenuTile(2, 5, 78, "all_eraser",0);
		}
		this.setMenuTransmuter(0, 7, "+", Angular.A00,0);
		this.setMenuTransmuter(2, 7, "-", Angular.A00,0);
		this.setMenuTransmuter(0, 6, "++", Angular.A00,0);
		this.setMenuTransmuter(2, 6, "--", Angular.A00,0);
		this.setMenuTransmuter(0, 5, "+++", Angular.A00,0);
		this.setMenuTransmuter(2, 5, "---", Angular.A00,0);
		this.setMenuTransmuter(2, 4, "+-", Angular.A00,0);
		this.setMenuTransmuter(3, 4, "+-+-", Angular.A00,0);
		this.setMenuTransmuter(0, 4, "0", Angular.A00,0);
		this.setMenuTransmuter(1, 4, "00", Angular.A00,0);
		this.setMenuTransmuter(0, 7, ">", Angular.A00,0);
		this.setMenuTransmuter(1, 6, "<>", Angular.A00,1);
		this.setMenuTransmuter(1, 4, ">33", Angular.A00,0);			
		this.setMenuTransmuter(1, 1, ">50", Angular.A00,0);		
		this.setMenuTransmuter(1, 2, ">100", Angular.A00,1);		
		this.setMenuTransmuter(0, 7, "+/",Angular.A00,0);
		this.setMenuTransmuter(1, 7, "-/",Angular.A00,0);
		this.setMenuTransmuter(0, 6, "=+",Angular.A90,0);		
		this.setMenuTransmuter(1, 6, "=-",Angular.A90,0);			
		this.setMenuTransmuter(2, 6, "=!",Angular.A90,0);
		this.setMenuTransmuter(3, 6, "=E",Angular.A90,0);
		this.setMenuTransmuter(1, 4, "=1",Angular.A00,0);
		this.setMenuTransmuter(1, 3, "=2",Angular.A00,0);		
		this.setMenuTransmuter(1, 2, "=4",Angular.A00,0);			
		this.setMenuTransmuter(1, 7, "=4a",Angular.A00,1);
		this.setMenuTransmuter(1, 6, "=8a",Angular.A00,1);
		this.setMenuTransmuter(0, 7, "->1",Angular.A00,0);		
		this.setMenuTransmuter(2, 7, "->2",Angular.A00,0);			
		this.setMenuTransmuter(0, 6, "->3",Angular.A00,0);
		this.setMenuTransmuter(2, 6, "->4",Angular.A00,0);
		this.unSelect();
	}

	private void clearall() {
		unSelect();
		setPage(0);
		for (int k=0;k<Transmuter.Class.values().length;k++)
			for (int j=0;j<nbpages;j++){
				map[j][k].getTileSets().addTileSet(AssetLoader.tileSet);
				for (int i = 0; i <  map[j][k].getLayers().getCount(); i++) {
					TiledMapTileLayer layer = (TiledMapTileLayer) map[j][k].getLayers().get(i);
					for (int x = 0; x < layer.getWidth(); x++) {
						for (int y = 0; y < layer.getHeight(); y++) {
							layer.getCell(x, y).setTile(null);
						}
					}
				}
			}
	}

	private void initialize() {
		for (int k=0;k<Transmuter.Class.values().length;k++)
			for (int j=0;j<nbpages;j++){
				map[j][k]=new TiledMap();
				map[j][k].getTileSets().addTileSet(AssetLoader.tileSet);
				MapLayers layers = map[j][k].getLayers();
				for (int i = 0; i < 3; i++) {
					TiledMapTileLayer layer = new TiledMapTileLayer(tilesizex,tilesizey, 128, 128);
					for (int x = 0; x < layer.getWidth(); x++) {
						for (int y = 0; y < layer.getHeight(); y++) {
							Cell cell = new Cell();
							/*if (i == 0)
								cell.setTile(AssetLoader.tileSet.getTile(54));*/
							layer.setCell(x, y, cell);
						}
					}
					layers.add(layer);
				}
				map[j][k].getLayers().get(1).setOpacity(0.5f);
				map[j][k].getLayers().get(2).setOpacity(0.25f);
			}
	}

	private void setMenuTile(int x, int y, int tile, String title, int page) {
		TiledMapTileLayer layer = ((TiledMapTileLayer) map[page][0].getLayers().get(0));
		Cell cell = layer.getCell(x, y);
		if (cell != null) {
			cell.setTile(AssetLoader.tileSet.getTile(tile));
			cell.getTile().getProperties().put("name", title);
			cell.setRotation(0);
			Gdx.app.debug("wirechem-Menu", "Tile find:" + tile	+ " coords" + x + "," + y);
			layer.getProperties().put("noempty", false);
		}
	}

	public void setSurtile(int x, int y, Transmuter transmuter) {
		if (transmuter != null) {
			Cell cell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(1)).getCell(x, y);
			OrderedMap<Vector2, Integer> tiles = transmuter.getTilesidrotated();
			Entries<Vector2, Integer> iterator = tiles.iterator();
			while (iterator.hasNext()) {
				Entry<Vector2, Integer> all = iterator.next();
				Cell subcell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(1)).getCell((int) (x + all.key.x), (int) (y + all.key.y));
				subcell.setTile(AssetLoader.tileSet.getTile(transmuter.getTilestype(tiles.keys().toArray().indexOf(all.key, false))	.ordinal() + 80));
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
		TiledMapTileLayer layer;
		if (transmuter != null) {
			int type=transmuter.getaClass().ordinal();
			Gdx.app.debug("wirechem-Menu", "Transmuter find:"+ transmuter.getName() + " Angle:" + Angle + " coords"	+ x + "," + y+" page:"+page+" type:"+type);
			if (transmuter.getTechnology()<=worlds.getLevelData().Tech || worlds.isDebug()) {
				Gdx.app.debug("wirechem-Menu", "Autorisé par le niveau");
				if (!transmuter.isShowed() && transmuter.isUpgraded() && !worlds.isDebug())
					layer = ((TiledMapTileLayer) map[page][type].getLayers().get(2));
				else if (transmuter.isShowed() || worlds.isDebug())
					layer = ((TiledMapTileLayer) map[page][type].getLayers().get(0));
				else
					return;
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					layer.getProperties().put("noempty", false);
					transmuter.setRotation(Angle);
					Iterator<Entry<Vector2, Integer>> keySetIterator = transmuter.getTilesidrotated().iterator();
					while (keySetIterator.hasNext()) {
						Entry<Vector2, Integer> all = keySetIterator.next();
						Cell subcell = layer.getCell((int) (x + all.key.x),	(int) (y + all.key.y));
						subcell.setTile(AssetLoader.tileSet.getTile(all.value));
						subcell.setRotation(Angle.ordinal());
						subcell.getTile().getProperties().put("movetox", (int) -all.key.x);
						subcell.getTile().getProperties().put("movetoy", (int) -all.key.y);
					}
				}
			}
		}
	}

	public MapProperties getMenubyTile(int x, int y) {
		Cell cell = ((TiledMapTileLayer) map[selpage][seltype].getLayers().get(0)).getCell(x, y);
		if (cell != null && cell.getTile()!=null)
			return cell.getTile().getProperties();
		else
			return null;
	}

	public Vector2 screentoworld(float x, float y) {
		int xx = (int) (x / 60f);
		int yy = (int) (y / 60f);
		return new Vector2(xx, yy);
	}

	public Vector2 worldtoscreen(int x, int y) {
		float xx = x * 60f;
		float yy = y * 60f;
		return new Vector2(xx, yy);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		camera.update();
		MapRenderer.setView(camera);
		MapRenderer.render();
		batch.begin();
		if (selected != null) {
			batch.setColor(1f, 0f, 0f, 1f);
			batch.draw(oneselection, selected.getX()+this.getX(),
					selected.getY()+this.getY(),
					selected.getWidth() / 2,
					selected.getHeight() / 2,
					selected.getWidth(),
					selected.getHeight(), 1f, 1f, rotation);
		}	
	}

}
