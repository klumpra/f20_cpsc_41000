package edu.lewisu.cs.klumpra;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HandlingEdges extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    float imgX, imgY;
    float imgWidth, imgHeight;
    int WIDTH, HEIGHT;
    OrthographicCamera cam;
    int WORLDWIDTH;
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        WORLDWIDTH = 2*WIDTH;
        imgX = 0;
        imgY = 0;
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        cam = new OrthographicCamera(WIDTH,HEIGHT);
        cam.translate(WIDTH/2,HEIGHT/2);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        System.out.println(cam.position.x + " " + cam.position.y);
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.A)) {
            imgX--;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            imgX++;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            imgY++;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            imgY--; 
        }
    }
    public void panCoordinates() {
        float screenPosX = imgX-(cam.position.x-WIDTH/2);
        float screenPosY = imgY-(cam.position.y-HEIGHT/2);
        if (screenPosX > WIDTH - imgWidth - 20) {
            cam.position.x = cam.position.x + screenPosX-WIDTH +imgWidth + 20;
            System.out.println(cam.position.x);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
        }  
        if (screenPosY > HEIGHT - imgHeight - 20) {
            cam.position.y = cam.position.y + screenPosY - HEIGHT + imgHeight + 20;
            System.out.println(cam.position.y);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
        }
    }
    public void wrapCoordinates(float targetWidth, float targetHeight) {
        if (imgX > targetWidth) {
            imgX= -imgWidth;
        } else if (imgX < -imgWidth) {
            imgX=targetWidth;
        }
        if (imgY > targetHeight) {
            imgY = -imgHeight;
        } else if (imgY < -imgHeight) {
            imgY = targetHeight;
        }
    }
    public void wrapCoordinates() {
        wrapCoordinates(WIDTH, HEIGHT);
    }
    public void lockCoordinates(float targetWidth, float targetHeight) {
        if (imgX > targetWidth - imgWidth) {
            imgX = targetWidth - imgWidth;
        } else if (imgX < 0) {
            imgX = 0;
        }
        if (imgY > targetHeight - imgHeight) {
            imgY = targetHeight - imgHeight;
        } else if (imgY < 0) {
            imgY = 0;
        }   }
    public void lockCoordinates() {
        lockCoordinates(WIDTH, HEIGHT);
    }
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        lockCoordinates();
        batch.begin();
        batch.draw(img, imgX, imgY);
        batch.end();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}


