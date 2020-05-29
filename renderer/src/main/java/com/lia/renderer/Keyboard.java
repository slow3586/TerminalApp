package com.lia.renderer;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Keyboard extends KeyAdapter {

    public static Keyboard ins = new Keyboard();

    public static List<Short> pressed = new ArrayList<>();
    public static List<Short> released = new ArrayList<>();
    public static List<Short> down = new ArrayList<>();
    public static Short typed = null;

    private Keyboard(){}

    public static void readEvent(int type, KeyEvent ke){
        switch (type) {
            case KeyEvent.EVENT_KEY_PRESSED:
                typed = ke.getKeyCode();
                if(ke.isAutoRepeat()) return;
                if (!pressed.contains(ke.getKeyCode())) {
                    pressed.add(ke.getKeyCode());
                }
                if (!down.contains(ke.getKeyCode())){
                    down.add(ke.getKeyCode());
                }
                break;
            case KeyEvent.EVENT_KEY_RELEASED:
                if(ke.isAutoRepeat()) return;
                released.add(ke.getKeyCode());
                down.remove(new Short(ke.getKeyCode()));
                break;
            default:
                break;
        }
    }

    public static void reset() {
        released.clear();
        pressed.clear();
        typed = null;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        InputManager.eventPipe.put(ke);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        InputManager.eventPipe.put(ke);
    }
}