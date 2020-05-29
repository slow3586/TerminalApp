package com.lia.renderer;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Mouse extends MouseAdapter {
    public static Mouse ins = new Mouse();
    
    public static V2i pos = new V2i();
    public static V2i rel = new V2i();
    public static List<Short> pressed = new ArrayList<>();
    public static List<Short> released = new ArrayList<>();
    public static List<Short> down = new ArrayList<>();
    public static List<Short> consumed = new ArrayList<>();
    public static Boolean consumedMouseOver = false;
    public static Boolean moved = false;
    public static Float wheel = 0f;
    
    public static List<V2i> pointersReleased = new ArrayList<>();
    public static List<V2i> pointersPressed = new ArrayList<>();
    public static List<V2i> pointersPos = new ArrayList<>();
    
    private Mouse(){}
    
    public static void reset() {
        moved = false;
        rel.zero();
        pressed.clear();
        released.clear();
        consumed.clear();
        consumedMouseOver = false;
        wheel = 0f;
        
        pointersReleased.clear();
        pointersPressed.clear();
        pointersPos.clear();
    }

    public static final Short LEFT = 1;
    public static final Short MIDDLE = 2;
    public static final Short RIGHT = 3;
    
    public static void readEvent(int type, MouseEvent me){
        if(type == MouseEvent.EVENT_MOUSE_DRAGGED)
            type = MouseEvent.EVENT_MOUSE_MOVED;
        switch (type) {
            case MouseEvent.EVENT_MOUSE_MOVED:
                moved = true;
                V2i _new = new V2i(me.getX(), me.getY());
                rel.add(new V2i(pos).sub(_new));
                pos = _new;
                
                if(me.getPointerCount()>1){
                    for (int i = 0; i < me.getPointerCount(); i++) {
                        pointersPos.add(new V2i(me.getX(i), me.getY(i)));
                    }
                }
                
                break;
            case MouseEvent.EVENT_MOUSE_PRESSED:
                if (!pressed.contains(me.getButton())) {
                    pressed.add(me.getButton());
                }
                if (!down.contains(me.getButton())){
                    down.add(me.getButton());
                }
                
                if(me.getPointerCount()>1){
                    for (int i = 0; i < me.getPointerCount(); i++) {
                        pointersPressed.add(new V2i(me.getX(i), me.getY(i)));
                    }
                }

                moved = true;
                _new = new V2i(me.getX(), me.getY());
                rel.add(new V2i(pos).sub(_new));
                pos = _new;
                
                break;
            case MouseEvent.EVENT_MOUSE_RELEASED:
                released.add(me.getButton());
                down.remove(new Short(me.getButton()));
                
                if(me.getPointerCount()>1){
                    for (int i = 0; i < me.getPointerCount(); i++) {
                        pointersReleased.add(new V2i(me.getX(i), me.getY(i)));
                    }
                }
                        
                break;
            case MouseEvent.EVENT_MOUSE_WHEEL_MOVED:
                wheel = me.getRotation()[1]*-1;
                break;
            default:
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseWheelMoved(MouseEvent me) {
        InputManager.eventPipe.put(me); 
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {
        InputManager.eventPipe.put(me); 
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        InputManager.eventPipe.put(me);   
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        InputManager.eventPipe.put(me); 
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        InputManager.eventPipe.put(me); 
    }
    
    
}
