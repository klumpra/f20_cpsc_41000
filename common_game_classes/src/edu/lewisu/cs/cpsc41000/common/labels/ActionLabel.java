package edu.lewisu.cs.cpsc41000.common.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * abstract base class for labels that can perform actions.
 * The most typical use case for this would be when the user clicks on the label.
 * Then you'd want to tell this object to act.
 * In fact, this class has an act function you'll override in descendant classes.
 * @author klumpra
 *
 */
public abstract class ActionLabel {
	private Label label;
	/**
	 * This creates the action label
	 * @param text the text to show
	 * @param xpos the x position of the label
	 * @param ypos the y position of the label
	 * @param fontPath where in our project was the font installed. (should be somewhere in the assets folder)
	 */
	public ActionLabel(String text, int xpos, int ypos, String fontPath) {
		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal(fontPath));
		label = new Label(text,style);
		label.setPosition(xpos,ypos);
	}
	/**
	 * can be useful for testing whether the label was clicked, hovered over, or otherwise involved at location (x,y)
	 * @param x the x location to test
	 * @param y the y location to test
	 * @return true if the (x,y) are within the space of the label
	 */
	public boolean wasClicked(int x, int y) {
		if (x >= label.getX() && x <= label.getX() + label.getWidth() &&
			y >= label.getY() && y <= label.getY() + label.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * when wasClicked returns true, typically the game will call this
	 * act function
	 */
	public abstract void act();
}
