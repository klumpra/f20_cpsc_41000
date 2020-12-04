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

import edu.lewisu.cs.cpsc41000.common.Boundary;
import edu.lewisu.cs.cpsc41000.common.EdgeHandler;
import edu.lewisu.cs.cpsc41000.common.ImageBasedScreenObject;
import edu.lewisu.cs.cpsc41000.common.ImageBasedScreenObjectDrawer;
import edu.lewisu.cs.cpsc41000.common.MobileImageBasedScreenObject;
import edu.lewisu.cs.cpsc41000.common.labels.ActionLabel;
import edu.lewisu.cs.cpsc41000.common.labels.SoundLabel;
import edu.lewisu.cs.cpsc41000.common.motioncontrollers.Tracker;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	MobileImageBasedScreenObject obj,chaser;
	ImageBasedScreenObjectDrawer artist;
	ArrayList<ImageBasedScreenObject> walls;
	EdgeHandler edgy;
	OrthographicCamera cam, titleCam;
	float WIDTH, HEIGHT;
	SoundLabel label;
	int scene;  // 0 - title screen, 1 - game screen
	ActionLabel title;  // will appear on the title screen
	ArrayList<Boundary> boundaries;
	Texture background;
	Tracker tracker;
	boolean trackingActive;
 
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		background = new Texture("beach.png");
		Texture img = new Texture("car.png");
		obj = new MobileImageBasedScreenObject(img,300,0,true);
		obj.setMaxSpeed(100);
		obj.setAcceleration(400);
		obj.setDeceleration(100);
		chaser = new MobileImageBasedScreenObject(img,100,300,true);
		chaser.setMaxSpeed(50);
		chaser.setAcceleration(300);
		chaser.setDeceleration(400);
		boundaries = new ArrayList<Boundary>();
		boundaries.add(new Boundary(269,0,279,333));
		boundaries.add(new Boundary(460,0,470,333));
/*		Texture img = new Texture("badlogic.jpg");
		obj = new MobileImageBasedScreenObject(img,150,0,true);
		obj.setMaxSpeed(100);
		obj.setAcceleration(400);
		obj.setDeceleration(100);
		walls = new ArrayList<ImageBasedScreenObject>();
		Texture wallTex = new Texture("wall.png");
		walls.add(new ImageBasedScreenObject(wallTex,0,0,true));
		walls.add(new ImageBasedScreenObject(wallTex,500,0,true));
*/
		artist = new ImageBasedScreenObjectDrawer(batch);
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		titleCam = new OrthographicCamera(WIDTH,HEIGHT);
		titleCam.translate(WIDTH/2,HEIGHT/2);
		titleCam.update();
		batch.setProjectionMatrix(cam.combined);
		edgy = new EdgeHandler(obj,cam,batch,-300,1200,-300,1200,0,
		EdgeHandler.EdgeConstants.PAN,
		EdgeHandler.EdgeConstants.PAN);
		scene = 0; // start on the title scene
		label = new SoundLabel("hey", 200, 400, "fonts/arial.fnt", "audio/heartbeat.wav");
		title = new ActionLabel("This is the title", 200, 400,"fonts/arial.fnt");
		tracker = new Tracker(chaser,obj,0.5f);
		trackingActive = false;
	}
	public void renderMainScene() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			scene = 0;
			return;
		}
		if (Gdx.input.isKeyJustPressed(Keys.T)) {
			trackingActive = !trackingActive;
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
		obj.applyPhysics(dt);
		if (obj.getSpeed() > 0) {
			obj.setRotation(obj.getMotionAngle()-90f);
		}
		if (trackingActive) {
			tracker.track(dt); // applies physics to the chaser
			if (chaser.getSpeed() > 0) {
				chaser.setRotation(chaser.getMotionAngle()-90f);
			}
		}
		if (chaser.overlaps(obj)) {
			System.out.println("Collision");
		}
		if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
			if (label.wasClicked(Gdx.input.getX(),HEIGHT-Gdx.input.getY())) {
				label.stopSound();
				label.playSound();
			}
		}
		if (obj.overlaps(label)) {
			label.playSound();
		} else {
			label.stopSound();
		}
		Vector2 bounce;
/*
		for (ImageBasedScreenObject wall : walls) {
			if (obj.overlaps(wall)) {
				bounce = obj.preventOverlap(wall);
				obj.rebound(bounce.angle(),1f);
				System.out.println("Bam!");
			}
		}
*/
		for (Boundary b : boundaries) {
			if (obj.overlaps(b)) {
				bounce = obj.preventOverlap(b);
				obj.rebound(bounce.angle(),1f);
			}
			if (chaser.overlaps(b)) {
				bounce = chaser.preventOverlap(b);
				chaser.move(10*bounce.x,10*bounce.y);
//				chaser.rebound(bounce.angle(),1f);
				tracker.avoid(b.getParallel(),dt);
				if (chaser.getSpeed() > 0) {
					chaser.setRotation(chaser.getMotionAngle()-90f);
				}	
			}
		}

		edgy.enforceEdges();
		batch.setProjectionMatrix(cam.combined); // game scene camera
		batch.begin();
		batch.draw(background,-600,0);
		artist.draw(obj);
		artist.draw(chaser);
/*		for (ImageBasedScreenObject wall : walls) {
			artist.draw(wall);
		}
*/
//		label.draw(batch,1f);
		batch.end();
	}
	public void renderTitleScene() {
		Gdx.gl.glClearColor(0,1,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			scene = 1;
		} else {
			batch.setProjectionMatrix(titleCam.combined);
			batch.begin();
			title.draw(batch,1f);
			batch.end();
		}
	}
	@Override
	public void render () {
		if (scene == 1) {
			renderMainScene();
		} else {
			renderTitleScene();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
