package edu.lewisu.cs.klumpra;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageBasedScreenObjectDrawer {
    private SpriteBatch batch;
    public ImageBasedScreenObjectDrawer(SpriteBatch batch) {
        this.batch = batch;
    }
    public void draw(ImageBasedScreenObject obj) {
        batch.draw(obj.getImg(), obj.getXPos(), obj.getYPos(), 
        obj.getXOrigin(), obj.getYOrigin(), 
        obj.getWidth(), obj.getHeight(), obj.getScaleX(), obj.getScaleY(), obj.getRotation(),
        0,0,(int)(obj.getWidth()),(int)(obj.getHeight()), obj.getFlipX(), obj.getFlipY());
    }
}
