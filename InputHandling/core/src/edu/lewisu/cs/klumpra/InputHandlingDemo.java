package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
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
		if (Gdx.input.isKeyJustPressed(Keys.W)) {
			imgY += 5;
		}
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			imgY -= 5;
		}
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			if (shiftHeld) {
				imgAngle += 2;
			} else {
				imgX -= 5;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			if (shiftHeld) {
				imgAngle -= 2;
			} else {
				imgX += 5;
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
		batch.draw(img, imgX, imgY, imgWidth/2, imgHeight/2, imgWidth, imgHeight, 1, 1, imgAngle);
//		batch.draw(img, imgX, imgY);
//		batch.draw(img2,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
