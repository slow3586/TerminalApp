package com.lia.renderer;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.NEWTEvent;
import com.jogamp.newt.event.NEWTEventFiFo;

public class InputManager {
    public static final NEWTEventFiFo eventPipe = new NEWTEventFiFo();
    
    public static void update(){
        NEWTEvent eventObject;
        while( null != ( eventObject = eventPipe.get() ) ) {
            final int type = eventObject.getEventType();
            if(eventObject instanceof MouseEvent)
                Mouse.readEvent(type, (MouseEvent) eventObject);
            else if(eventObject instanceof KeyEvent)
                Keyboard.readEvent(type, (KeyEvent) eventObject);
        }
    }
    
    public static void reset(){
        Keyboard.reset();
        Mouse.reset();
    }
}
