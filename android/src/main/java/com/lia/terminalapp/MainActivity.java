package com.lia.terminalapp;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import com.lia.core.AppCore;
import com.lia.renderer.App;
import com.lia.renderer.Keyboard;
import com.lia.renderer.Mouse;
import com.lia.renderer.TextRenderer;
import com.lia.renderer.Window;

import jogamp.newt.driver.android.NewtBaseActivity;


public class MainActivity extends NewtBaseActivity {

    public static MainActivity ins;
    public static AssetManager assetManager;

    @Override public void onCreate(final Bundle state) {
        super.onCreate(state);

        ins = this;
        assetManager = getAssets();

        App.impl = new AppCore();
        TextRenderer.impl = new TextRendererAndroid();
        SPColor.impl = new SPColor();
        SPTexture.impl = new SPTexture();

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        Window.size.x = metrics.widthPixels;
        Window.size.y = metrics.heightPixels;

        GLCapabilities glcapabilities = new GLCapabilities(GLProfile.getDefault());
        Window.glWindow = GLWindow.create(glcapabilities);
        Window.glWindow.addMouseListener(Mouse.ins);
        Window.glWindow.addKeyListener(Keyboard.ins);
        Window.glWindow.addGLEventListener(Window.ins);
        Window.glWindow.setFullscreen(true);

        Window.animator = new FPSAnimator(Window.glWindow, 60);
        Window.animator.setUpdateFPSFrames(1,null);

        this.setContentView(this.getWindow(), Window.glWindow);
        this.setAnimator(Window.animator);
    }

}
