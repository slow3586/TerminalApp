package com.lia.core;

import com.lia.renderer.App;
import com.lia.renderer.Mouse;
import com.lia.renderer.Quad;
import com.lia.renderer.Window;

public class AppCore extends App {
    @Override
    public void init() {
        Quad.initAll();

        Terminal.addLine("hello world0");
        Terminal.addLine("hello world1");
        Terminal.addLine("hello world2");

        Terminal.addOption("option0", new Runnable() {
            @Override
            public void run() {
                System.out.println("option0 run");
            }
        });
        Terminal.addOption("option1oooooooooooooooooooooooooooooooooooooooooooooooooooooooo", new Runnable() {
            @Override
            public void run() {
                Terminal.addLine("hello");
                Terminal.render();
            }
        });
        Terminal.addOption("option2", new Runnable() {
            @Override
            public void run() {
                System.out.println("option2 run");
            }
        });

        Terminal.render();
    }


    long lt;
    @Override
    public void update() {
        Terminal.update();
        Quad.drawAll();

        String fpss = String.valueOf(1000.0f/Window.delta);
        Quad.setText(0, 0, fpss.substring(0, 1));
        Quad.setText(1, 0, fpss.substring(1, 2));
        Quad.setText(2, 0, fpss.substring(2, 3));

        Quad.setBackColor(1,1,1,0,0);
        Quad.setBackColor(2,2,0,1,0);



    }

    @Override
    public void reshape() {

    }
}
