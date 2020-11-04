package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageBasedScreenObjectDemo extends ApplicationAdapter {
	SpriteBatch batch;
	ImageBasedScreenObject obj;
	ImageBasedScreenObjectDrawer artist;
	InputHandler handler;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture img = new Texture("badlogic.jpg");
		obj = new ImageBasedScreenObject(img);
		artist = new ImageBasedScreenObjectDrawer(batch);
		handler = new InputHandler(obj);
		Gdx.input.setInputProcessor(handler);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		artist.draw(obj);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
