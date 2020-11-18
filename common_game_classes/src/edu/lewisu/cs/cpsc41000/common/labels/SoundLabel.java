package edu.lewisu.cs.cpsc41000.common.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Designed to detect when it has been clicked and then play a sound
 * through its act() function.
 * @author klumpra
 *
 */
public class SoundLabel extends ActionLabel {
	private Sound sound;
	public SoundLabel(String text, int xpos, int ypos, String fontPath, String soundPath) {
		super(text,xpos,ypos,fontPath);
		sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
	}
	@Override
	public void act() {
		playSound();
	}
	public void playSound() {
		sound.play();
	}
	public void playSound(float vol) {
		sound.play(vol);
	}
}
