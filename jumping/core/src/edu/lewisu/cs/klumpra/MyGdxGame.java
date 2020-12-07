package edu.lewisu.cs.klumpra;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import edu.lewisu.cs.cpsc41000.common.EdgeHandler;
import edu.lewisu.cs.cpsc41000.common.ImageBasedScreenObject;
import edu.lewisu.cs.cpsc41000.common.ImageBasedScreenObjectDrawer;
import edu.lewisu.cs.cpsc41000.common.MobileImageBasedScreenObject;
import edu.lewisu.cs.cpsc41000.common.PlatformCharacter;
import edu.lewisu.cs.cpsc41000.common.labels.ActionLabel;
import edu.lewisu.cs.cpsc41000.common.labels.SoundLabel;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	PlatformCharacter obj;
	ImageBasedScreenObjectDrawer artist;
	ArrayList<ImageBasedScreenObject> walls;
	EdgeHandler edgy;
	OrthographicCamera cam;
	float WIDTH, HEIGHT;
	SoundLabel label;
	ActionLabel title;
	int scene;  // either 0 for title screen or 1 for main game
 
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();

		Texture img = new Texture("badlogic.jpg");
		obj = new PlatformCharacter(img,150,0,false);
		obj.setMaxSpeed(100);
		obj.setAcceleration(400);
		obj.setDeceleration(400);

		walls = new ArrayList<ImageBasedScreenObject>();
		Texture wallTex = new Texture("wall.png");
		walls.add(new ImageBasedScreenObject(wallTex,0,0,true));
		walls.add(new ImageBasedScreenObject(wallTex,1000,0,true));
		obj.setPlatforms(walls);
		artist = new ImageBasedScreenObjectDrawer(batch);
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		edgy = new EdgeHandler(obj,cam,batch,-300,1200,-300,1200,0,
		EdgeHandler.EdgeConstants.LOCK,
		EdgeHandler.EdgeConstants.LOCK);
		label = new SoundLabel("hey", 600, 600, "fonts/arial.fnt", "audio/heartbeat.wav");
		title = new SoundLabel("title screen",600,600,"fonts/arial.fnt","audio/heartbeat.wav");
		scene = 0;
	}
	public void renderMain() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			obj.jump();
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			scene = 0;
			return;
		} 
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
		if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
			if (label.wasClicked(Gdx.input.getX(),HEIGHT-Gdx.input.getY())) {
				label.act();
			}
		}
		if (obj.overlaps(label)) {
			label.playSound();
		} else {
			label.stopSound();
		}
		obj.applyPhysics(dt);
		if (obj.getSpeed() > 0) {
//			obj.setRotation(obj.getMotionAngle());
		} 
		Vector2 bounce;
		for (ImageBasedScreenObject wall : walls) {
			if (obj.overlaps(wall)) {
				bounce = obj.preventOverlap(wall);
				if (bounce != null) {
					obj.rebound(bounce.angle(),1f);
					System.out.println("Bam!");
				}
			}
		}
		edgy.enforceEdges();
		batch.begin();
		artist.draw(obj);
		for (ImageBasedScreenObject wall : walls) {
			artist.draw(wall);
		}
		label.draw(batch,1f);
		batch.end();
	}
	public void renderTitleScreen() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			scene = 1;
		} else {
			batch.begin();
			title.draw(batch,1f);
			batch.end();
		}
	}
	@Override
	public void render () {
		if (scene == 0) {
			renderTitleScreen();
		} else {
			renderMain();
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
