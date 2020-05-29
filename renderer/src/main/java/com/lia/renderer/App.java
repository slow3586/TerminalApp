package com.lia.renderer;

public abstract class App {

    public static App impl;

    abstract public void init();
    abstract public void update();
    abstract public void reshape();
}
