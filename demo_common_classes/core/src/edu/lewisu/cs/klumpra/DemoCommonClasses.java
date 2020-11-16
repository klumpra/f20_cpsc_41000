package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import edu.lewisu.cs.cpsc41000.common.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DemoCommonClasses extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ImageBasedScreenObject obj;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		obj = new ImageBasedScreenObject(img);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
