package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * This class represents an object that appears on the screen. 
 * It consists of a label. When the user clicks it, it triggers 
 * a sound to be played. Each SoundLabel object has a Label that
 * appears on the screen that the user will click, and a Sound
 * that will be played when it is clicked.
 */
class SoundLabel {
	private Label label;
	private Sound sound;

	public Label getLabel() {
		return label;
	}
	/**
	 * This sets up a SoundLabel that is ready to be clicked and play sounds
	 * @param pathToSound where the sound file is located
	 * @param textToShow the text to show on the screen
	 * @param style the font to use (in a nutshell)
	 * @param xpos xcoord where label will appear
	 * @param ypos ycoord where label will appear
	 */
	public SoundLabel(String pathToSound, String textToShow, LabelStyle style,
	int xpos, int ypos) {
		sound = Gdx.audio.newSound(Gdx.files.internal(pathToSound));
		label = new Label(textToShow,style);
		label.setPosition(xpos,ypos);
	}
	/**
	 * plays sound at max value
	 */
	public void playSound() {
		sound.play();
	}
	/**
	 * plays sound at requested volume
	 * @param vol the requested volume (between 0 and 1)
	 */
	public void playSound(float vol) {
		sound.play(vol);
	}
	/**
	 * This determines if the label was clicked
	 * @param x where the mouse's x coordinate is
	 * @param y where the mouse's y coordinate is
	 * @return true if x,y lie within the label's area
	 */
	public boolean wasClicked(int x, int y) {
		if (x >= label.getX() && x <= label.getX() + label.getWidth() &&
		y >= label.getY() && y <= label.getY() + label.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
}
public class BasicSoundBar extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera cam;
	int WIDTH, HEIGHT;
	LabelStyle labelStyle;
	SoundLabel soundLabel;

	public void setupLabelStyle() {
		labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont(Gdx.files.internal("fonts/arial.fnt"));
	}
	/**
	 * render the soundLabel on the screen
	 */
	public void drawSoundLabel() {
		soundLabel.getLabel().draw(batch,1);

	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		setupLabelStyle();
		soundLabel = new SoundLabel("audio/bulb.mp3","Bulb",labelStyle,50,50);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
			if (soundLabel.wasClicked(Gdx.input.getX(),HEIGHT-Gdx.input.getY())) {
				soundLabel.playSound();
			}
		}
		batch.begin();
		drawSoundLabel();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
