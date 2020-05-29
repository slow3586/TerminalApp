package com.lia.renderer;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLPipelineFactory;
import com.jogamp.opengl.util.FPSAnimator;

public class Window implements GLEventListener {

    public static Window ins = new Window();
    public static GL4ES3 gl;
    public static V2i size = new V2i();
    public static GLWindow glWindow;
    public static FPSAnimator animator;
    public static float delta;

    @Override
    public void display(GLAutoDrawable p0) {
        gl = p0.getGL().getGL4ES3();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        delta = animator.getLastFPSPeriod();

        InputManager.update();

        App.impl.update();

        InputManager.reset();
    }

    @Override
    public void reshape(GLAutoDrawable glauto, int x, int y, int w, int h) {
        gl = glauto.getGL().getGL4ES3();
        size.x = w;
        size.y = h;
        gl.glViewport(0, 0, w, h);

        App.impl.reshape();
    }

    @Override
    public void init(GLAutoDrawable glauto) {
        gl = glauto.getGL().getGL4ES3();
        ///gl = (GL4ES3) glauto.setGL( GLPipelineFactory.create("com.jogamp.opengl.Debug",         null, gl, null) );
        //gl = (GL4ES3) glauto.setGL( GLPipelineFactory.create("com.jogamp.opengl.Trace",         null, gl, new Object[] { System.err } ) );
        gl.setSwapInterval(1);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBlendEquation(GL.GL_FUNC_ADD);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glFrontFace(GL.GL_CCW);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glViewport(0, 0, size.x, size.y);

        SPColor.impl.init();
        SPColor.impl.link();
        SPTexture.impl.init();
        SPTexture.impl.link();
        if(App.impl == null)
            throw new IllegalStateException();

        App.impl.init();
    }

    @Override
    public void dispose(GLAutoDrawable p0) {
    }
}
