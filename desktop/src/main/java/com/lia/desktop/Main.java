package com.lia.desktop;


import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import com.lia.core.AppCore;
import com.lia.renderer.App;
import com.lia.renderer.Keyboard;
import com.lia.renderer.Mouse;
import com.lia.renderer.TextRenderer;
import com.lia.renderer.V2i;
import com.lia.renderer.Window;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        TextRenderer.impl = new TextRendererDesktop();
        SPColor.impl = new SPColor();
        SPTexture.impl = new SPTexture();

        GLCapabilities glcapabilities = new GLCapabilities(GLProfile.getDefault());
        Window.glWindow = GLWindow.create(glcapabilities);
        Window.glWindow.addMouseListener(Mouse.ins);
        Window.glWindow.addKeyListener(Keyboard.ins);
        Window.glWindow.addGLEventListener(Window.ins);

        Window.animator = new FPSAnimator(Window.glWindow, 60);
        Window.animator.setUpdateFPSFrames(1,null);
        Window.animator.start();

        Window.glWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowResized(com.jogamp.newt.event.WindowEvent windowEvent) {
            }

            @Override
            public void windowMoved(com.jogamp.newt.event.WindowEvent windowEvent) {
            }

            @Override
            public void windowDestroyNotify(com.jogamp.newt.event.WindowEvent windowEvent) {
            }

            @Override
            public void windowDestroyed(com.jogamp.newt.event.WindowEvent windowEvent) {
                Window.animator.stop();
                System.exit(1);
            }

            @Override
            public void windowGainedFocus(com.jogamp.newt.event.WindowEvent windowEvent) {
            }

            @Override
            public void windowLostFocus(com.jogamp.newt.event.WindowEvent windowEvent) {
            }

            @Override
            public void windowRepaint(WindowUpdateEvent windowUpdateEvent) {
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        V2i ss = new V2i(screenSize.width*0.9f, screenSize.height*0.9f);
        float mod = 1;

        if(ss.y<ss.x){
            mod = 1.0f*ss.y/1920;
        } else {
            mod = 1.0f*ss.x/1080;
        }

        V2i ssn = new V2i(1080*mod, 1920*mod);

        int xz = (int) ((screenSize.getWidth()-ssn.x)/2);
        int yz = (int) ((screenSize.getHeight()-ssn.y)/2);
        Window.glWindow.setSize(ssn.x, ssn.y);
        Window.glWindow.setPosition(xz, yz);
        Window.size = ssn;
        Window.glWindow.setResizable(false);
        Window.glWindow.setVisible(true);

        App.impl = new AppCore();
    }
}
