package fr.evolving.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Transmuter;
import fr.evolving.automata.Transmuter.Angular;
import fr.evolving.automata.Transmuter.CaseType;
import fr.evolving.screens.GameScreen.calling;

public class Transhower extends Actor {

	private Transmuter transmuter;
	private TiledMap map;
	private OrthogonalTiledMapRenderer MapRenderer;
	private OrthographicCamera camera;
	private TiledMapTileLayer layer;
	private ShapeRenderer shaperenderer;
	private Transmuter.Angular angle;
	boolean keepaspectratio;
	Color color;

	public Transhower(Transmuter transmuter, Transmuter.Angular angle, boolean keepaspectratio,Color color) {
		this.color=color;
		this.keepaspectratio=keepaspectratio;
		this.angle=angle;
		shaperenderer= new ShapeRenderer();
		map = new TiledMap();
		map.getTileSets().addTileSet(AssetLoader.tileSet);
		MapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 128.0f);
		camera = new OrthographicCamera();
		layer = new TiledMapTileLayer(4, 7, 128, 128);
		super.setBounds(-500, -500, 256, 256);
		map.getLayers().add(layer);
		setTransmuter(transmuter);
	}

	public void clearer() {
		this.transmuter=null;
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = new Cell();
				layer.setCell(x, y, cell);
			}
		}
	}

	public void redraw() {
		this.transmuter.setRotation(angle);
		OrderedMap<Vector2, Integer> tiles =  transmuter.getTilesidrotated();
		Entries<Vector2, Integer> iterator = tiles.iterator();
		float minx=15000;
		float miny=15000;
		float maxx=-15000;
		float maxy=-15000;
		while (iterator.hasNext()) {
			Entry<Vector2, Integer> all = iterator.next();
			if (all.key.x<minx)
				minx=all.key.x;
			if (all.key.y<miny)
				miny=all.key.y;
			if (all.key.x>maxx)
				maxx=all.key.x;
			if (all.key.y>maxy)
				maxy=all.key.y;
		}
		int deltax=(int)(maxx-minx)+1;
		int deltay=(int)(maxy-miny)+1;
		float change=0;
		boolean dir=false;
		if (keepaspectratio) {
			change=deltax-deltay;
			if (change>0) {
				deltay=deltax;
				dir=true;
			}
			else if (change<0) {
				deltax=deltay;
				dir=false;
			}
			}
		iterator.reset();
		while (iterator.hasNext()) {
			Entry<Vector2, Integer> all = iterator.next();
			Gdx.app.debug("wirechem-Transhower", "Transmuter placement:"+(all.key.x-minx)+","+(all.key.y-miny)+" angle:"+this.angle);
			layer.getCell((int)(all.key.x-minx),(int)(all.key.y-miny)).setTile(AssetLoader.tileSet.getTile(all.value));
			layer.getCell((int)(all.key.x-minx),(int)(all.key.y-miny)).setRotation(this.angle.ordinal());
		}
		float sizex=AssetLoader.width/(this.getWidth()/deltax);
		float sizey=AssetLoader.height/(this.getHeight()/deltay);
		float decx = -this.getX()/AssetLoader.width*sizex;
		float decy = -this.getY()/AssetLoader.height*sizey;
		if (keepaspectratio && change!=0)
		if (dir)
			decy=decy-Math.abs(change/2);
		else
			decx=decx-Math.abs(change/2);
		Gdx.app.debug("wirechem-Transhower", "Camera delta:"+deltax+","+deltay+" dec:"+decx+","+decy+" view:"+sizex+","+sizey+" change:"+change+","+dir);
		camera.setToOrtho(false, sizex, sizey);
		camera.translate(decx,decy);
	}
	
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		redraw();
	}
	
	public void setHeight(float height) {
		super.setHeight(height);
		redraw();
	}
	
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		redraw();
	}
	
	public void setWidth(float width) {
		super.setWidth(width);
		redraw();
	}

	public void setTransmuter(Transmuter transmuter) {
		this.clearer();
		this.transmuter=transmuter;
		this.redraw();
	}
	
	public void setAngle(Transmuter.Angular angle)
	{
		this.angle=angle;
		this.transmuter.setRotation(angle);
		this.redraw();
	}
	
	public Transmuter.Angular getAngle() {
		return this.angle;
	}
	
	public void setColor(Color color)
	{
		this.color=color;
		this.redraw();
	}
	
	
	public Color getColor()
	{
		return this.color;
	}

	public Transmuter getTransmuter() {
		return this.transmuter;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setProjectionMatrix(AssetLoader.Camera.combined);
		shaperenderer.rect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		shaperenderer.setColor(this.color);
		shaperenderer.end();
		camera.update();
		MapRenderer.setView(camera);
		MapRenderer.render();
		batch.begin();
	}

}
