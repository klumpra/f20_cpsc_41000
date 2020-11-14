package edu.lewisu.cs.klumpra;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ImageBasedScreenObjectBasicDemo extends ApplicationAdapter {
	SpriteBatch batch;
	ImageBasedScreenObject obj;
	ImageBasedScreenObjectDrawer artist;
	ArrayList<ImageBasedScreenObject> walls;
 
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture img = new Texture("badlogic.jpg");
		obj = new ImageBasedScreenObject(img,150,0,true);
		obj.centerOriginGeometrically();
		obj.setMaxSpeed(100);
		obj.setAcceleration(400);
		obj.setDeceleration(100);
		walls = new ArrayList<ImageBasedScreenObject>();
		Texture wallTex = new Texture("wall.png");
		walls.add(new ImageBasedScreenObject(wallTex,0,0,true));
		walls.add(new ImageBasedScreenObject(wallTex,500,0,true));
        artist = new ImageBasedScreenObjectDrawer(batch);
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		Vector2 bounce;
		for (ImageBasedScreenObject wall : walls) {
			if (obj.overlaps(wall)) {
				bounce = obj.preventOverlap(wall);
				obj.rebound(bounce.angle(),1f);
				System.out.println("Bam!");
			}
		}
		batch.begin();
		artist.draw(obj);
		for (ImageBasedScreenObject wall : walls) {
			artist.draw(wall);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
