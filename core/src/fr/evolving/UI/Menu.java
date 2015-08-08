package fr.evolving.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;

public class Menu extends Actor{
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer MapRenderer;
	private OrthographicCamera camera;
	private int tilesizex;
	private int tilesizey;
	private float decx;
	private float decy;	
	private int size=32;
	
	
public Menu(int tilesizex,int tilesizey) {
	this.tilesizex=tilesizex;
	this.tilesizey=tilesizey;	
	map=new TiledMap();
    map.getTileSets().addTileSet(AssetLoader.tileSet);
    MapLayers layers = map.getLayers();
    for (int i = 0; i < 3; i++) {
    	TiledMapTileLayer layer = new TiledMapTileLayer(tilesizex, tilesizey, 128, 128);
    	for (int x = 0; x < layer.getWidth(); x++) {
    		for (int y = 0; y < layer.getHeight(); y++) {
    		Cell cell = new Cell();
    		if (i==0)
    			cell.setTile(AssetLoader.tileSet.getTile(54));
    		layer.setCell(x, y, cell);
    		}
    	}
    	layers.add(layer);
    }
    MapRenderer = new OrthogonalTiledMapRenderer(map,1/(float)size);
    camera = new OrthographicCamera();
	camera.setToOrtho(false, tilesizex*32,tilesizex*32*AssetLoader.height/AssetLoader.width);
	Gdx.app.debug(getClass().getSimpleName(),"Caméra pour tilemap:"+(tilesizex*size)+"x"+(tilesizey*size));
	decx=-102f;
	decy=-20f;
	camera.translate(decx,decy);
	Gdx.app.debug(getClass().getSimpleName(),"Décalage:"+decx+"x"+decy);	
}

public void clear()
{
	TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
	for (int x = 0; x < layer.getWidth(); x++)
		for (int y = 0; y < layer.getHeight(); y++)
			layer.getCell(x, y).setTile(AssetLoader.tileSet.getTile(54));
}

public void setMenu(int x,int y,int tile)
{
	Cell cell=((TiledMapTileLayer)map.getLayers().get(0)).getCell(x,y);
	if (cell!=null) {
		cell.setTile(AssetLoader.tileSet.getTile(tile));
	}
}

public int getMenu(int x,int y)
{
	Cell cell=((TiledMapTileLayer)map.getLayers().get(0)).getCell(x,y);
	if (cell!=null)
		return cell.getTile().getId();
	else
		return 0;
}

public Vector2 screentoworld(float x, float y) {
	int xx=(int) ((x-1531f)/60f);
	int yy=(int) ((y-(AssetLoader.height-776f))/60f);
	return new Vector2(xx,yy);
}

public Vector2 worldtoscreen(int x, int y) {
	float xx=1531.0f+x*60f;
	float yy=AssetLoader.height-776.0f+y*60f;
	return new Vector2(xx,yy);
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
