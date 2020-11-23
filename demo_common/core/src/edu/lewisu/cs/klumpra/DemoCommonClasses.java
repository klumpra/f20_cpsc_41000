package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.lewisu.cs.cpsc41000.common.EdgeHandler;
import edu.lewisu.cs.cpsc41000.common.EdgeHandler.EdgeConstants;
import edu.lewisu.cs.cpsc41000.common.ImageBasedScreenObjectDrawer;
import edu.lewisu.cs.cpsc41000.common.MobileImageBasedScreenObject;

public class DemoCommonClasses extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    EdgeHandler edgy;
    OrthographicCamera cam;
    float WIDTH, HEIGHT;
    MobileImageBasedScreenObject obj;
    ImageBasedScreenObjectDrawer artist;
    Texture background;
	
	@Override
	public void create () {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(WIDTH,HEIGHT);
        cam.translate(WIDTH/2, HEIGHT/2);
        cam.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
		img = new Texture("badlogic.jpg");
		background = new Texture("background.png");
		obj = new MobileImageBasedScreenObject(img, 0, 0, true);
		obj.setAcceleration(400);
		obj.setDeceleration(400);
        artist = new ImageBasedScreenObjectDrawer(batch);				
        edgy = new EdgeHandler(obj, cam, batch, -300, WIDTH+300, -300, HEIGHT+300, 20, EdgeConstants.PAN, EdgeConstants.PAN);				
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Keys.D)) {
            obj.accelerateAtAngle(0f);
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            obj.accelerateAtAngle(90f);
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            obj.accelerateAtAngle(180f);
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            obj.accelerateAtAngle(270f);
        }
		obj.applyPhysics(dt);
		edgy.enforceEdges();
		batch.begin();
		batch.draw(background, 0, 0);
		artist.draw(obj);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
