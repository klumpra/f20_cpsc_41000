package edu.lewisu.cs.cpsc41000.common;

import com.badlogic.gdx.math.Polygon;

public class Boundary implements Collidable {
    private Polygon boundingPoly;
    /**
     * defines a boundary with bottom-left corner at (x1,y1) and
     * top right corner at (x2,y2);
     * @param x1 
     * @param y1
     * @param x2
     * @param y2
     */
    public Boundary(float x1, float y1, float x2, float y2) {
        float[] vertices = new float[8];
        vertices[0] = x1;
        vertices[1] = y1;
        vertices[2] = x2;
        vertices[3] = y1;
        vertices[4] = x2;
        vertices[5] = y2;
        vertices[6] = x1;
        vertices[7] = y2;
        boundingPoly = new Polygon(vertices);
    }
    @Override
    public Polygon getBoundingPolygon() {
        return boundingPoly;
    }
}
