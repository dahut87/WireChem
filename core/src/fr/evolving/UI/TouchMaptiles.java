package fr.evolving.UI;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import fr.evolving.assets.AssetLoader;
import fr.evolving.automata.Level;
import fr.evolving.automata.Worlds;
import fr.evolving.screens.GameScreen.calling;

public class TouchMaptiles extends Actor implements GestureListener,InputProcessor {

	private TiledMap map;
	private OrthogonalTiledMapRenderer MapRenderer;
	private OrthographicCamera camera;
	private Level level;
	private int sizex;
	private int sizey;
	private float viewwidth, viewheight, decx, decy;
	private String selected;
	private boolean mapexit;
	private int clearsprite;
	private Worlds worlds;

	public TouchMaptiles(Worlds worlds,Level level, int sizex, int sizey) {
		this.worlds=worlds;
		this.level = level;
		this.sizex = sizex;
		this.sizey = sizey;
		this.mapexit=false;
		map = new TiledMap();
		map.getTileSets().addTileSet(AssetLoader.tileSet);
		MapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 128.0f);
		this.setBounds(80, 0, AssetLoader.width, AssetLoader.height);
		camera = new OrthographicCamera();
		this.resize();
	}
	
	public void resize() {
		MapLayers layers = map.getLayers();
		int max=layers.getCount();
		for(int i=0;i<max;i++)
			if (layers.get(0)!=null)
				layers.remove(0);
		for (int i = 0; i < 7; i++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(level.Grid.sizeX, level.Grid.sizeY, sizex, sizey);
			for (int x = 0; x < layer.getWidth(); x++) {
				for (int y = 0; y < layer.getHeight(); y++) {
					Cell cell = new Cell();
					if (i == 0)
						cell.setTile(AssetLoader.tileSet.getTile(this.clearsprite));
					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}
		layers.get(3).setOpacity(0.9f);
		this.redraw();
		this.initzoom();
	}
	
	boolean event_coordination(float x, float y, int button, calling call,
			String[] exec) {
		if (selected != null) {
			if (Arrays.asList(exec).contains(selected)) {
				Vector3 coordsscreen = new Vector3();
				AssetLoader.Camera.unproject(coordsscreen.set(x, y, 0));	
				Vector2 coords = this.screentoworld(coordsscreen.x, coordsscreen.y);
				if (level.Grid.GetXY(coords.x, coords.y) != null) {
					mapexit = false;
					if (call != calling.mouseover)
						Gdx.app.debug("wirechem-Touchmaptiles", "mode:" + call + " outil:"	+ selected + " X: " + coords.x	+ " Y: " + coords.y + " button:" + button);
					Method method;
					try {
						Class<?> base = Class.forName("fr.evolving.screens.GameScreen");
						Class<?>[] params = { float.class, float.class,	int.class, int.class, boolean.class, int.class,	calling.class };
						method = base.getDeclaredMethod("map_" + selected, params);
						method.invoke(((Game) Gdx.app.getApplicationListener()).getScreen(), (float) coordsscreen.x, (float) coordsscreen.y,(int) coords.x, (int) coords.y, true,(int) button, (calling) call);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if (mapexit == false) {
						mapexit = true;
						this.tempclear();
					}
				}
			}

		}
		return true;
	}
	
	public void setSelected(String selected) {
		this.selected=selected;
	}
	
	public void setClearsprite(int sprite) {
		this.clearsprite=sprite;
	}
	
	public int getClearsprite() {
		return this.clearsprite;
	}
	

	public Vector2 screentoworld(float x, float y) {
		x = (int) ((x / this.getWidth() * camera.viewportWidth) + decx);
		y = (int) ((y / this.getHeight() * camera.viewportHeight) + decy);
		return new Vector2(x, y);
	}

	public Vector2 screentoworldsize(float x, float y) {
		x = ((x / this.getWidth() * camera.viewportWidth));
		y = ((y / this.getHeight() * camera.viewportHeight));
		return new Vector2(x, y);
	}

	public void tempdraw(float x, float y, int tile, int rotation, int surtile) {
		Cell cell = ((TiledMapTileLayer) map.getLayers().get(3)).getCell((int) x, (int) y);
		if (cell != null) {
			((TiledMapTileLayer) map.getLayers().get(4)).getCell((int) x,(int) y).setTile(AssetLoader.tileSet.getTile(tile));
			((TiledMapTileLayer) map.getLayers().get(4)).getCell((int) x,(int) y).setRotation(rotation);
			if (surtile != 0)
				((TiledMapTileLayer) map.getLayers().get(3)).getCell((int) x,
						(int) y).setTile(AssetLoader.tileSet.getTile(surtile));
		}
	}

	public void tempclear() {
		for (int x = 0; x < level.Grid.sizeX; x++)
			for (int y = 0; y < level.Grid.sizeY; y++) {
				((TiledMapTileLayer) map.getLayers().get(3)).getCell((int) x,
						(int) y).setTile(null);
				((TiledMapTileLayer) map.getLayers().get(4)).getCell((int) x,
						(int) y).setTile(null);
			}
	}

	public void redraw() {
		for (int x = 0; x < level.Grid.sizeX; x++)
			for (int y = 0; y < level.Grid.sizeY; y++) {
				if (worlds.isDebug()) {
				if (level.Grid.GetXY(x,y).Locked)
					((TiledMapTileLayer) map.getLayers().get(5)).getCell((int) x,(int) y).setTile(AssetLoader.tileSet.getTile(90));
				else
					((TiledMapTileLayer) map.getLayers().get(5)).getCell((int) x,(int) y).setTile(null);
				if (level.Grid.GetXY(x,y).Free)
					((TiledMapTileLayer) map.getLayers().get(6)).getCell((int) x,(int) y).setTile(AssetLoader.tileSet.getTile(89));
				else
					((TiledMapTileLayer) map.getLayers().get(6)).getCell((int) x,(int) y).setTile(null);
				}
				if (level.Grid.getCopper(x, y))
					((TiledMapTileLayer) map.getLayers().get(1)).getCell((int) x, (int) y).setTile(AssetLoader.tileSet.getTile(level.Grid.getCoppercalc(x, y)));
				else
					((TiledMapTileLayer) map.getLayers().get(1)).getCell((int) x,(int) y).setTile(null);
				if (level.Grid.getFiber(x, y))
					((TiledMapTileLayer) map.getLayers().get(0)).getCell((int) x, (int) y).setTile(AssetLoader.tileSet.getTile(61));
				else
					((TiledMapTileLayer) map.getLayers().get(0)).getCell((int) x,(int) y).setTile(AssetLoader.tileSet.getTile(this.clearsprite));
				if (level.Grid.getTransmutercalc(x, y) != 0) {
					((TiledMapTileLayer) map.getLayers().get(2)).getCell((int) x, (int) y).setTile(AssetLoader.tileSet.getTile(level.Grid.getTransmutercalc(x, y)));
					((TiledMapTileLayer) map.getLayers().get(2)).getCell((int) x, (int) y).setRotation(level.Grid.getTransmuterrot(x, y));
					((TiledMapTileLayer) map.getLayers().get(2)).getCell((int) x, (int) y).getTile().getProperties().put("movex",level.Grid.GetXY(x, y).Transmuter_movex);
					((TiledMapTileLayer) map.getLayers().get(2)).getCell((int) x, (int) y).getTile().getProperties().put("movey",level.Grid.GetXY(x, y).Transmuter_movex);
				}
				else
					((TiledMapTileLayer) map.getLayers().get(2)).getCell((int) x,(int) y).setTile(null);
				;
			}
		((TiledMapTileLayer) map.getLayers().get(0)).getCell((int) 0, (int) 0).setTile(AssetLoader.tileSet.getTile(1010));
		((TiledMapTileLayer) map.getLayers().get(0)).getCell((int) 1, (int) 0).setTile(AssetLoader.tileSet.getTile(1010));
		((TiledMapTileLayer) map.getLayers().get(0)).getCell((int) 2, (int) 0).setTile(AssetLoader.tileSet.getTile(1010));
	}

	public void initzoom() {
		if ((level.Grid.sizeX / (float) level.Grid.sizeY) > (this.getWidth() / this.getHeight())) {
			viewwidth = level.Grid.sizeX;
			viewheight = level.Grid.sizeX
					/ ((float) this.getWidth() / this.getHeight());
		} else {
			viewheight = level.Grid.sizeY;
			viewwidth = level.Grid.sizeY
					* ((float) this.getWidth() / this.getHeight());
		}
		Gdx.app.debug("wirechem-Touchmaptiles", "Caméra pour tilemap:" + viewwidth + "x" + viewheight);
		camera.setToOrtho(false, viewwidth, viewheight);
		decx = (level.Grid.sizeX - viewwidth) / 2.0f;
		decy = (level.Grid.sizeY - viewheight) / 2.0f;
		Gdx.app.debug("wirechem-Touchmaptiles", "Décalage:" + decx + "x"	+ decy);
		camera.translate(decx, decy);
	}

	public void fillempty(int tile) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		for (int x = 0; x < layer.getWidth(); x++)
			for (int y = 0; y < layer.getHeight(); y++)
				if (layer.getCell(x, y).getTile().getId() == 53
						|| layer.getCell(x, y).getTile().getId() == 60)
					layer.getCell(x, y).setTile(
							AssetLoader.tileSet.getTile(tile));
	}

	public void setZoom(float factor) {
		viewwidth *= factor;
		viewheight *= factor;
		camera.setToOrtho(false, viewwidth, viewheight);
		camera.translate(decx, decy);
		Gdx.app.debug("wirechem-Touchmaptiles", "Caméra pour tilemap:"	+ camera.viewportWidth + "x" + camera.viewportHeight + " zoom:"	+ factor);
	}

	public float getDecx() {
		return decx;
	}

	public float getDecy() {
		return decy;
	}

	public void setDec(float x, float y) {
		Vector2 dec = screentoworldsize(x, y);
		decx = decx - dec.x;
		decy = decy - dec.y;
		camera.setToOrtho(false, viewwidth, viewheight);
		camera.translate(decx, decy);
		Gdx.app.debug("wirechem-Touchmaptiles", "Decalage:" + dec.x + "x"	+ dec.y + "  newxy:" + decx + "x" + decy);
		return;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		camera.update();
		MapRenderer.setView(camera);
		MapRenderer.render();
		batch.begin();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		String[] exec = { "cleaner", "infos", "zoomp", "zoomm",
				"copper_pen", "fiber_pen", "gold_pen","lock_pen","copper_eraser",
				"fiber_eraser", "transmuter_eraser", "all_eraser",
				"blank", "transmuter", "copper_brush", "fiber_brush" };
		return event_coordination(x, y, button, calling.mouseclick,
				exec);
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		String[] exec = { "transmuter" };
		if (count == 1)
			event_coordination(x, y, button, calling.tap, exec);
		else if (count >= 2)
			event_coordination(x, y, button, calling.taptap, exec);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		String[] exec = { "transmuter" };
		return event_coordination(x, y, 0, calling.longpress, exec);
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		String[] exec = { "zoomp", "zoomm" };
		int zooming = (int) (distance / initialDistance * 1000f);
		return event_coordination(0, 0, zooming, calling.zoom, exec);
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
			float deltaX = pointer2.x - pointer1.x;
			float deltaY = pointer2.y - pointer1.y;
			int angle = (int) ((float) Math.atan2((double) deltaY,
					(double) deltaX) * MathUtils.radiansToDegrees);
			angle += 90;
			if (angle < 0)
				angle = 360 - (-angle);
			String[] exec = { "transmuter" };
			return event_coordination(initialPointer1.x, initialPointer1.y, angle,	calling.pinch, exec);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		String[] exec = { "transmuter", "move", "copper_brush",
				"fiber_brush", "copper_eraser", "fiber_eraser",
				"transmuter_eraser", "all_eraser", "blank" };
		return event_coordination(screenX, screenY, 0, calling.mousedrag, exec);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		String[] exec = { "transmuter" };
		return event_coordination(screenX, screenY, 0, calling.mouseover, exec);
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
