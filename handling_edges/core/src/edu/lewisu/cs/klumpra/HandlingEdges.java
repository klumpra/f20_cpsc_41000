package edu.lewisu.cs.klumpra;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HandlingEdges extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture background;
    float imgX, imgY;
    float imgWidth, imgHeight;
    float WIDTH, HEIGHT;
    OrthographicCamera cam;
    float WORLDWIDTH, WORLDHEIGHT;
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        background = new Texture("lewis.png");
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();   //viewport or screen
		WORLDWIDTH = 2*WIDTH;
		WORLDHEIGHT = 2*HEIGHT;              //of world
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
            imgX-=10;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            imgX+=10;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            imgY+=10;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            imgY-=10; 
        }
	}
	public Vector2 getViewPortOrigin() {
		return new Vector2(cam.position.x-WIDTH/2, cam.position.y - HEIGHT/2);
	}
	public Vector2 getScreenCoordinates() {
		Vector2 viewportOrigin = getViewPortOrigin();
		return new Vector2(imgX-viewportOrigin.x, imgY-viewportOrigin.y);
	}
    public void panCoordinates(float border) {
        Vector2 screenPos = getScreenCoordinates();
        if (screenPos.x > WIDTH - imgWidth - border) {  // about to go off vieport
            if (imgX + imgWidth > WORLDWIDTH - border) {  // about to go off the world
                lockCoordinates(WORLDWIDTH, WORLDHEIGHT);
            } else { // just pan the camera because I have more world to explore
                cam.position.x = cam.position.x + screenPos.x - WIDTH + imgWidth + border;
                System.out.println(cam.position.x);
                cam.update();
                batch.setProjectionMatrix(cam.combined);
            }
        } 
        if (screenPos.x < border) { // about to leave the viewport on the left side
            //move the camera left - subtract the amount we are over the border from
            //the current camera position
            //this is for infinite world in the -x direction
            cam.position.x = cam.position.x - (border - screenPos.x);
            System.out.println(cam.position.x);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
        }
        if (screenPos.y > HEIGHT - imgHeight - border) {  // go off viewport vertically
            if (imgY + imgHeight > WORLDHEIGHT - border) {  // out of real estate in y direction
                lockCoordinates(WORLDWIDTH, WORLDHEIGHT);
            } else { // keep panning we have more room
                cam.position.y = cam.position.y + screenPos.y - HEIGHT + imgHeight + border;
                System.out.println(cam.position.y);
                cam.update();
                batch.setProjectionMatrix(cam.combined);
            }
        }
        if (screenPos.y < border) {
            cam.position.y = cam.position.y - (border - screenPos.y);
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
        panCoordinates(20);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(img, imgX, imgY);
        batch.end();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}


