package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class InputHandler extends InputAdapter {
	private ImageBasedScreenObject obj;
	private boolean shiftHeld = false;
	public InputHandler(ImageBasedScreenObject obj) {
		this.obj = obj;
	}
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = true;
		}
		if (keyCode == Keys.A) {
			if (shiftHeld) {
				obj.rotate(-5);
			} else {
				obj.accelerateAtAngle(180);
			}	
		}
		if (keyCode == Keys.D) {
			if (shiftHeld) {
				obj.rotate(5);
			} else {
				obj.accelerateAtAngle(0);
			}
		}
		if (keyCode == Keys.W) {
			if (shiftHeld) {
				obj.scale(1.1f,1.1f);
			} else {
				obj.accelerateAtAngle(90);
			}	
		}
		if (keyCode == Keys.S) {
			if (shiftHeld) {
				obj.scale(0.9f,0.9f);
			} else {
				obj.accelerateAtAngle(270);
			}
		}
		return true;
	}
	@Override
	public boolean keyUp(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = false;
		}
		return true;
	}
}
public class ImageBasedScreenObjectDemo extends ApplicationAdapter {
	SpriteBatch batch;
	ImageBasedScreenObject obj;
	ImageBasedScreenObject wall;
	ImageBasedScreenObjectDrawer artist;
	InputHandler handler;
	OrthographicCamera cam;

	@Override
	public void create () {
		batch = new SpriteBatch();
//		cam = new OrthographicCamera(Gdx.graphics.getWidth()/2,
//			Gdx.graphics.getHeight()/2);
//		cam.update();
//		batch.setProjectionMatrix(cam.combined);
//		Texture img = new Texture("runningcat.png"); //https://www.pikpng.com/pngvi/iToJwx_runningcat-running-cat-sprite-sheet-clipart/
		Texture img = new Texture("badlogic.jpg");
		obj = new ImageBasedScreenObject(img);
		obj.centerOrigin();
		obj.setMaxSpeed(100);
		obj.setAcceleration(400);
		obj.setDeceleration(100);
//		obj = new AnimatedImageBasedScreenObject(img,4,500,240);
		artist = new ImageBasedScreenObjectDrawer(batch);
		handler = new InputHandler(obj);
		Gdx.input.setInputProcessor(handler);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyPressed(Keys.D)) {
			obj.accelerateAtAngle(0);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			obj.accelerateAtAngle(180);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			obj.accelerateAtAngle(90);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			obj.accelerateAtAngle(270);
		}
		obj.applyPhysics(dt);
		if (obj.getSpeed() > 0) {
			obj.setRotation(obj.getMotionAngle());
		}
		batch.begin();
		artist.draw(obj);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
