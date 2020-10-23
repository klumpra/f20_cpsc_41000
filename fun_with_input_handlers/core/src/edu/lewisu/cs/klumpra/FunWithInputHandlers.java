package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

abstract class CameraEffect {
    protected OrthographicCamera cam;
    protected int duration, progress;
    protected ShapeRenderer renderer;
    protected SpriteBatch batch;
    public CameraEffect(OrthographicCamera cam, int duration, 
    SpriteBatch batch, ShapeRenderer renderer) {
        this.cam = cam;
        this.duration = duration;
        this.batch = batch;
        this.renderer = renderer;
        progress = duration;
    }
    public boolean isActive() {
        return (progress<duration);
    }
    public abstract void play();
    public void updateCamera() {
        cam.update();
        if (renderer != null) {
            renderer.setProjectionMatrix(cam.combined);
        }
        if (batch != null) {
            batch.setProjectionMatrix(cam.combined);
        }
    }
    public void start() {
        progress = 0;
    }
}

class CameraShake extends CameraEffect {
    private int intensity;
    private int speed;
    public int getIntensity() {
        return intensity;
    }
    public void setIntensity(int intensity) {
        if (intensity < 0) {
            this.intensity = 0;
        } else {
            this.intensity = intensity;
        }
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        if (speed < 0) {
            speed = 0;
        } else {
            if (speed > duration) {
                speed = duration / 2;
            } else {
                this.speed = speed;
            }
        }
    }
    @Override
    public boolean isActive() {
        return super.isActive() && speed > 0;
    }
    public CameraShake(OrthographicCamera cam, int duration, SpriteBatch batch,
    ShapeRenderer renderer, int intensity, int speed) {
        super(cam,duration,batch,renderer);
        setIntensity(intensity);
        setSpeed(speed);
    }
    @Override
    public void play() {
        if (isActive()) {
            if (progress % speed == 0) {
                intensity = -intensity;
                cam.translate(2*intensity,0);
            }
            progress++;
            if (!isActive()) {
                cam.translate(-intensity,0);
            }
            updateCamera();
        }
    }
    @Override
    public void start() {
        super.start();
        cam.translate(intensity,0);
        updateCamera();
    }
}

class InputHandler extends InputAdapter {
	private boolean shiftHeld = false;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Vector3 startCam, startMouse;

	public InputHandler(SpriteBatch batch, OrthographicCamera cam) {
		this.batch = batch;
		this.cam = cam;
	}
	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = true;
		}
		if (keyCode == Keys.SPACE) {
			if (shiftHeld) {
				System.out.println("Shiftfire!");
			} else {
				System.out.println("Fire!");
			}
		}
		return true;  // this function completely handles the event
	}
	@Override
	public boolean keyUp(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = false;
		}
		return true;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		startCam = new Vector3(cam.position.x,cam.position.y,0);
		startMouse = new Vector3(screenX, screenY, 0);
		return true;
	}
	public void updateCamera() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float diffX = screenX - startMouse.x;
		float diffY = screenY - startMouse.y;
		cam.position.x = startCam.x + diffX;
		cam.position.y = startCam.y - diffY;
		updateCamera();
		return true;
	}
}

public class FunWithInputHandlers extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera cam;
	int WIDTH;
	int HEIGHT;
	CameraShake shaker;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		InputHandler handler1 = new InputHandler(batch,cam);
		Gdx.input.setInputProcessor(handler1);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shaker = new CameraShake(cam, 100, batch, null, 10, 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			shaker.start();
		}
		shaker.play();
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
