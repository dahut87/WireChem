package fr.evolving.math;

import com.badlogic.gdx.math.Vector2;

public class Calcul {

	public static double getAngle(float x1,float y1,float x2,float y2) {
		double theta = Math.atan2(y2 - y1, x2 - x1);
		theta -= Math.PI/2.0f;
	    if (theta < 0) {
	    	theta += 2.0f*Math.PI;
	    }
	    return theta;
	}


	public static float getLen(float x1,float y1,float x2,float y2) {
		Vector2 src=new Vector2(x1,y1);
		Vector2 dst=new Vector2(x2,y2);
		return dst.sub(src).len();
	}

}