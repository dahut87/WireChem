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
import fr.evolving.automata.Transmuter;

public class TouchMaptiles extends Actor{
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer MapRenderer;
	private OrthographicCamera camera;
	private Level level;
	private int sizex;
	private int sizey;
	private float viewwidth,viewheight,decx,decy;
    
public TouchMaptiles(Level level,int sizex,int sizey) {
	this.level=level;
	this.sizex=sizex;
	this.sizey=sizey;
	map=new TiledMap();
    map.getTileSets().addTileSet(AssetLoader.tileSet);
    MapLayers layers = map.getLayers();
    for (int i = 0; i < 3; i++) {
    	TiledMapTileLayer layer = new TiledMapTileLayer(level.Grid.sizeX, level.Grid.sizeY, sizex, sizey);
    	for (int x = 0; x < layer.getWidth();x++) {
    		for (int y = 0; y < layer.getHeight(); y++) {
    		Cell cell = new Cell();
    		if (i==0)
    			cell.setTile(AssetLoader.tileSet.getTile(53));
    		layer.setCell(x, y, cell);
    		}
    	}
    	layers.add(layer);
    }
    MapRenderer = new OrthogonalTiledMapRenderer(map,1/128.0f);
    camera = new OrthographicCamera();
    initzoom();
}

public Vector2 screentoworld(float x, float y) {
	x=(int)((x/AssetLoader.width*camera.viewportWidth)+decx);
	y=(int)((y/AssetLoader.height*camera.viewportHeight)+decy);
	return new Vector2(x,y);
}

public Vector2 screentoworldsize(float x, float y) {
	x=((x/AssetLoader.width*camera.viewportWidth));
	y=((y/AssetLoader.height*camera.viewportHeight));
	return new Vector2(x,y);
}

public void redraw(int tile) {
	for (int x=0;x<level.Grid.sizeX;x++)
		for (int y=0;y<level.Grid.sizeY;y++) {
			((TiledMapTileLayer)map.getLayers().get(2)).getCell((int)x, (int)y).setTile(null);
			((TiledMapTileLayer)map.getLayers().get(1)).getCell((int)x, (int)y).setTile(null);
			((TiledMapTileLayer)map.getLayers().get(0)).getCell((int)x, (int)y).setTile(AssetLoader.tileSet.getTile(tile));
		}
	for (int x=0;x<level.Grid.sizeX;x++)
		for (int y=0;y<level.Grid.sizeY;y++) {
			if (level.Grid.getCopper(x,y))
				((TiledMapTileLayer)map.getLayers().get(1)).getCell((int)x, (int)y).setTile(AssetLoader.tileSet.getTile(level.Grid.getCoppercalc(x,y)));
			if (level.Grid.GetFiber(x,y))
				((TiledMapTileLayer)map.getLayers().get(0)).getCell((int)x, (int)y).setTile(AssetLoader.tileSet.getTile(61));
			if (level.Grid.getTransmutercalc(x, y)!=0)
			{
				((TiledMapTileLayer)map.getLayers().get(2)).getCell((int)x, (int)y).setTile(AssetLoader.tileSet.getTile(level.Grid.getTransmutercalc(x, y)));
				((TiledMapTileLayer)map.getLayers().get(2)).getCell((int)x, (int)y).setRotation(level.Grid.getTransmuterrot(x, y));
			}
		}
}

public void initzoom() {
    if ((level.Grid.sizeX/(float)level.Grid.sizeY)>(AssetLoader.width/AssetLoader.height)) 
    {
    	viewwidth=level.Grid.sizeX;
    	viewheight=level.Grid.sizeX/((float)AssetLoader.width/AssetLoader.height);
    }
   else 
    {
	   viewheight=level.Grid.sizeY;
	   viewwidth=level.Grid.sizeY*((float)AssetLoader.width/AssetLoader.height);       
    }
	Gdx.app.debug(getClass().getSimpleName(),"Caméra pour tilemap:"+viewwidth+"x"+viewheight);
	camera.setToOrtho(false, viewwidth,viewheight);
	decx=(level.Grid.sizeX-viewwidth)/2.0f;
	decy=(level.Grid.sizeY-viewheight)/2.0f;
	Gdx.app.debug(getClass().getSimpleName(),"Décalage:"+decx+"x"+decy);	
	camera.translate(decx,decy);
}

public void fillempty(int tile)
{
	TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
    for (int x = 0; x < layer.getWidth(); x++)
    for (int y = 0; y < layer.getHeight(); y++)
    if (layer.getCell(x, y).getTile().getId()==53 || layer.getCell(x, y).getTile().getId()==60) layer.getCell(x, y).setTile(AssetLoader.tileSet.getTile(tile));	
}

public void setZoom(float factor) {
    viewwidth*=factor;
    viewheight*=factor;
    camera.setToOrtho(false, viewwidth, viewheight);
	camera.translate(decx,decy);
	Gdx.app.debug(getClass().getSimpleName(),"Caméra pour tilemap:"+camera.viewportWidth+"x"+camera.viewportHeight+" zoom:"+factor); 
}

public float getDecx() {
    return decx;
}

public float getDecy() {
    return decy;
}

public void setDec(float x,float y) {
	Vector2 dec=screentoworldsize(x,y);
	decx=decx-dec.x;
	decy=decy-dec.y;
	camera.setToOrtho(false, viewwidth, viewheight);
	camera.translate(decx,decy);
	Gdx.app.debug(getClass().getSimpleName(),"Decalage:"+dec.x+"x"+dec.y+"  newxy:"+decx+"x"+decy);
	return;
}

@Override
public void draw(Batch batch, float parentAlpha) {       
	batch.end();
    //batch.setProjectionMatrix(camera.combined);
	camera.update();
    MapRenderer.setView(camera);
	//MapRenderer.setView(camera.combined,0,0,maxx,maxx);
    MapRenderer.render();
    batch.begin();
}



}
