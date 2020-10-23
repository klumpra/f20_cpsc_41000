package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;


public class InputHandlingDemo extends ApplicationAdapter {
    SpriteBatch batch;
    Texture tex;
    TextureRegion img;
    OrthographicCamera cam;
    int WIDTH;
    int HEIGHT;
    /*
    The state of a game is the data that it manages. That data controls game play and what appears on the screen.
    In this game, the player is the picture.
    We need to manage x and y positions and the rotation
    */
	int imgX, imgY;  // state variables associated with the location
    int imgWidth, imgHeight;
    int imgOrgX, imgOrgY;
    int imgAngle;

    @Override
    public void create () {
        batch = new SpriteBatch();
        tex = new Texture("badlogic.jpg");
        imgWidth = tex.getWidth();
        imgHeight = tex.getHeight();
        imgAngle = 0;
        img = new TextureRegion(tex); // gives rotation abilities to the image we loaded in
        imgX = 0;
        imgY = 0;
        imgOrgX = imgWidth/2;
        imgOrgY = imgHeight/2;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(WIDTH,HEIGHT);
        cam.translate(WIDTH/2,HEIGHT/2);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }
    public void wrapCoordinates() {
        if (imgX > WIDTH) {
            imgX = imgX-WIDTH;
        } else if (imgX < -imgWidth) {
            imgX=WIDTH;
        }
        if (imgY > HEIGHT) {
            imgY = imgY - HEIGHT - imgHeight;
        } else if (imgY < -imgHeight) {
            imgY = HEIGHT;
        }
    }

    /**
     * handles all keyboard and mouse input.
     * It is called from render.
     * UP, DOWN, LEFT, RIGHT - camera
     * If shift+UP or shift+DOWN - zoom
     * If shift+LEFT or shift+RIGHT - rotate
     * ESCAPE - leave the program
     * A,S,W,D control the image
     * A,D - left right
     * W,S - up down
     * shift + W and S -> rotate the image
     * If you click on the game, it will print the coordinates
     */
    public void handleInput() {
        boolean shiftHeld = false;
        boolean cameraNeedsUpdating = false;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
            shiftHeld = true;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (shiftHeld) {
                //zoom
                cam.zoom += 0.1;
            } else {
                cam.translate(0,1);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (shiftHeld) {
                //zoom
                cam.zoom -= 0.1;
            } else {
                cam.translate(0,-1);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(1);
            } else {
                cam.translate(-1,0);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(-1);
            } else {
                cam.translate(1,0);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();   // quit the game
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            imgY += 5;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            imgY -= 5;
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            if (shiftHeld) {
                imgAngle += 2;
            } else {
                imgX -= 5;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            if (shiftHeld) {
                imgAngle -= 2;
            } else {
                imgX += 5;
            }
        }

        // if space bar, advance in the direction I am looking
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            imgX += 5*MathUtils.cosDeg(imgAngle+90);
            imgY += 5*MathUtils.sinDeg(imgAngle+90);
        }

        if (Gdx.input.isTouched()) {
            String btnName;
            if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
                btnName = "left";
            } else {
                btnName = "right";
            }
            if (btnName.equals("right")) {
                imgOrgX = Gdx.input.getX();
                imgOrgY = HEIGHT - Gdx.input.getY();
            } else {
                imgX = Gdx.input.getX()-imgWidth/2;
                imgY = HEIGHT - Gdx.input.getY()-imgHeight/2;
            }
        }
        if (cameraNeedsUpdating) {
            updateCamera();
        }

    }
    public void updateCamera() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        batch.begin();
        batch.draw(img, imgX, imgY, imgOrgX, imgOrgY, imgWidth, imgHeight, 1f, 1f, imgAngle);
 //     batch.draw(img, imgX, imgY);
        batch.end();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
//      img.dispose();
    }
}


