package edu.lewisu.cs.klumpra;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
    private ImageBasedScreenObject obj;
    private boolean shiftHeld = false;
    public InputHandler(ImageBasedScreenObject obj) {
        this.obj = obj;
    }
    public boolean keyDown(int keyCode) {
        if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
            shiftHeld = true;
        }
        if (keyCode == Keys.A) {
            if (shiftHeld) {
                obj.rotate(-5);
            } else {
                obj.move(-5,0);
            }   
        }
        if (keyCode == Keys.D) {
            if (shiftHeld) {
                obj.rotate(5);
            } else {
                obj.move(5,0);
            }
        }
        if (keyCode == Keys.W) {
            if (shiftHeld) {
                obj.scale(1.1f,1.1f);
            } else {
                obj.move(0,5);
            }   
        }
        if (keyCode == Keys.S) {
            if (shiftHeld) {
                obj.scale(0.9f,0.9f);
            } else {
                obj.move(0,-5);
            }
        }
        return true;
    }
    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
            shiftHeld = false;
        }
        return true;
    }
}
