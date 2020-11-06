package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

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
    protected Vector2 velocityVec;
    protected Vector2 accelerationVec;
    protected float acceleration;
    protected float maxSpeed;
    protected float deceleration;

    public ImageBasedScreenObject(Texture tex) {
        this(tex,0,0,0,0,0,1,1,false,false);
    }
    public ImageBasedScreenObject(Texture tex, int xpos, int ypos, int xorigin, int yorigin, 
    int rotation, int scaleX, int scaleY, boolean flipX, boolean flipY) {
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
        initMovement();
    }
    public void centerOrigin() {
        xorigin = xpos + width/2;
        yorigin = ypos + height/2;
    }
    public void initMovement() {
        velocityVec = new Vector2(0,0);
        accelerationVec = new Vector2(0,0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;
    }
    public void setSpeed(float speed) {
        //if length is 0, assume angle is zero
        if (velocityVec.len() == 0) { 
            velocityVec.set(speed,0);
        } else {
            velocityVec.setLength(speed);
        }
    }
    public float getSpeed() {
        return velocityVec.len();
    }
    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }
    public float getMotionAngle() {
        return velocityVec.angle();
    }
    public void setAcceleration(float acc) {
        acceleration = acc;
    }
    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new 
            Vector2(acceleration,0).setAngle(angle));
    }
    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }
    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }
    public void setDeceleration(float dec) {
        deceleration = dec;
    }
    public boolean isMoving() {
        return (getSpeed() > 0);
    }
    public void applyPhysics(float dt) {
        // apply acceleration
        velocityVec.add(accelerationVec.x*dt, accelerationVec.y*dt);
        float speed = getSpeed();

        // decelerate when not accelerating
        if (accelerationVec.len() == 0) {
            speed -= deceleration * dt;
        }

        // keep speed within set bounds
        speed = MathUtils.clamp(speed,0,maxSpeed);

        // update velocity to match new speed
        setSpeed(speed);

        move(velocityVec.x*dt, velocityVec.y*dt);

        // reset acceleration
        accelerationVec.set(0,0);
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
    public void scale(float sx, float sy) {
        setScaleX(scaleX*sx);
        setScaleY(scaleY*sy);
    }
    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
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
        scale(scaleX*amt,1f);
    }
    public void scaleHeight(float amt) {
        scale(1f,scaleY*amt);
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
    public int getDrawStartX() {
        return 0;
    }
    public int getDrawStartY() {
        return 0;
    }
}
