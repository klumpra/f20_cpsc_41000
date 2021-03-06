package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.graphics.Texture;

public class ImageBasedScreenObject {
    protected Texture img;
    protected float xpos;
    protected float ypos;
    protected float xorigin;
    protected float yorigin;
    protected float width;
    protected float height;
    protected float rotation;
    protected float scaleX;
    protected float scaleY;
    protected boolean flipX;
    protected boolean flipY;
    
    public ImageBasedScreenObject(Texture tex) {
        this(tex,0,0,0,0,0,1,1,false,false);
    }

    public ImageBasedScreenObject(Texture tex, int xpos, int ypos, int xorigin, 
    int yorigin, int rotation, int scaleX, int scaleY, 
    boolean flipX, boolean flipY) {
        img = tex;
        width = img.getWidth();
        height = img.getHeight();
        setXPos(xpos);
        setYPos(ypos);
        setXOrigin(xorigin);
        setYOrigin(yorigin);
        setRotation(rotation);
        setScaleX(scaleX);
        setScaleY(scaleY);
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public float getXPos() {
        return xpos;
    }

    public void setXPos(float xpos) {
        this.xpos = xpos;
    }

    public float getYPos() {
        return ypos;
    }

    public void setYPos(float ypos) {
        this.ypos = ypos;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void scale(float sx, float sy) {
        setScaleX(scaleX*sx);
        setScaleY(scaleY*sy);
    }
    
    public void move(float dx, float dy) {
        xpos += dx;
        ypos += dy;
    }
    public void moveX(float amt) {
        xpos += amt;
    }
    public void moveY(float amt) {
        ypos += amt;
    }
    public void moveUp(float amt) {
        moveY(amt);
    }
    public void moveDown(float amt) {
        moveY(-amt);
    }
    public void moveLeft(float amt) {
        moveX(-amt);
    }
    public void moveRight(float amt) {
        moveX(amt);
    }
    public void rotateCW(float deg) {
        rotate(-deg);
    }
    public void rotateCCW(float deg) {
        rotate(deg);
    }
    public void scaleWidth(float amt) {
        scale(amt,1f);
    }
    public void scaleHeight(float amt) {
        scale(1f,amt);
    }
    public void rotate(float dAngle) {
        rotation += dAngle;
    }
    public void flipAboutX() {
        flipX = !flipX;
    }
    public void flipAboutY() {
        flipY = !flipY;
    }
    public boolean getFlipX() {
        return flipX;
    }
    public void setFlipX(boolean fx) {
        flipX = fx;
    }
    public boolean getFlipY() {
        return flipY;
    }
    public void setFlipY(boolean fy) {
        flipY = fy;
    }
    public void setXOrigin(float xorigin) {
        this.xorigin = xorigin;
    }
    public void setYOrigin(float yorigin) {
        this.yorigin = yorigin;
    }
    public float getXOrigin() {
        return xorigin;
    }
    public float getYOrigin() {
        return yorigin;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
}
