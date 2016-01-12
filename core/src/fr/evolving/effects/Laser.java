package fr.evolving.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import fr.evolving.assets.AssetLoader;

public class Laser {
	
	public float i=0;
	TextureRegion overlaymiddle,middle,overlay;
	
	public Laser() {
		i=0;
		overlaymiddle=AssetLoader.Skin_level.getAtlas().findRegion("overlay-middle");
		middle=AssetLoader.Skin_level.getAtlas().findRegion("middle");
		overlay=AssetLoader.Skin_level.getAtlas().findRegion("overlay");
	}
	
	public void draw(Batch Laser,float xx1,float yy1,float xx2,float yy2,float maxwidth,float power,boolean active,Color colorsrc,Color colordst) {	
		float x1=xx1+26;
		float y1=yy1+20;
		float x2=xx2+26;
		float y2=yy2+20;
		Vector2 vector1=new Vector2(x1, y1);
		Vector2 vector2=new Vector2(x2, y2);		
		Vector2 vectorall = vector2.sub(vector1);
		final int inc=16;
		Vector2 vectoradd = vectorall.cpy().setLength(inc);
		Vector2 vectoraddit = vectorall.cpy().setLength(64);
		Laser.begin();
		Laser.setColor(colorsrc);
		Laser.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		Laser.draw(overlaymiddle,x1,y1,32,0,64,vectorall.len(),1f,1f,vectorall.angle()+270);
		Laser.draw(middle,x1,y1,32,0,64,vectorall.len(),1f,1f,vectorall.angle()+270);
		Laser.setColor(new Color(1f,1f,1f,1f)); 
		for(int j=0;j<(vectorall.len()-vectoradd.cpy().setLength(i*inc).len())/64-1;j++)
			Laser.draw(overlay,x1+i*vectoradd.x+j*vectoraddit.x,y1+i*vectoradd.y+j*vectoraddit.y,32,0,64,64,1f,1f,vectorall.angle()+270);
		Laser.draw(overlay,x1,y1,32,0,64,i*inc,1f,1f,vectorall.angle()+270);		
		Laser.end();
	}
		
	public void drawnotsoold(ShapeRenderer Laser,float xx1,float yy1,float xx2,float yy2,float maxwidth,float power,boolean active,Color colorsrc,Color colordst) {	
		float x1=xx1+58;
		float y1=yy1+20;
		float x2=xx2+58;
		float y2=yy2+20;
		Laser.begin(ShapeType.Filled);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		if (active) {
			Vector2 vectorall = new Vector2(x2, y2).sub(new Vector2(x1, y1));
			float length = vectorall.len();
			float size=20;
			Vector2 vectoradd = vectorall.scl(size/length);
			float adding=0;
			for(float i = 2; i+2 < length/size; i += 1) 
			{
				float width=(float)(maxwidth-Math.random()*2);
			    while(width >= 0) 
			    {
					adding=(width*power/maxwidth);
					Color Acolor=colorsrc.cpy().lerp(colordst.cpy(), (i/(length/size)));
					Laser.setColor(Acolor.add(adding,adding,adding,0.5f));
					if (Math.random()>0.4) Laser.rectLine(x1 + i*vectoradd.x, y1 + i*vectoradd.y, x1+ (i+1)*vectoradd.x, y1+(i+1)* vectoradd.y, width);
					width=width-1;
			    }
			}
		}
		else
		{
			Color Acolor=new Color(0.5f,0.5f,0.5f,1f);
			Laser.setColor(Acolor);
			Laser.rectLine(x1, y1 , x2, y2, 2);
		}
		Laser.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	

	public void drawold(ShapeRenderer Laser,float xx1,float yy1,float xx2,float yy2,float maxwidth,float power,boolean active,Color colorsrc,Color colordst) {
		float x1=xx1+20;
		float y1=yy1+20;
		float x2=xx2+20;
		float y2=yy2+20;
		float adding=0;
		Laser.begin(ShapeType.Point);
		Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
		float length = vec2.len();
		float awidth=0,width = 0;
		float size = 0;
		for(int i = 0; i < length; i += 1) {
			if ((i % 5)==0)
				awidth=(float)(Math.random()*maxwidth/2);
			width=awidth;
			if (i % ((int)width*20+1)==0)
				size=(float)(Math.random());
			vec2.clamp(length - i, length - i);
		    while(width >= 0) {
				adding=(width*power/maxwidth);
				Color Acolor=colordst.cpy().lerp(colorsrc.cpy(), (i/length));
				Laser.setColor(Acolor.add(adding,adding,adding,1f));
				if (size>0.4f) {
					Laser.point(x1 + vec2.x-width/2, y1 + vec2.y+width/2, 0);
					Laser.point(x1 + vec2.x+width/2, y1 + vec2.y-width/2, 0);
				}
				width=width-1;
		     }
		 }
		 Laser.end();
	}
}
	
	
