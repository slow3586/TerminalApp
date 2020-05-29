package com.lia.renderer;

import java.nio.IntBuffer;

import static com.lia.renderer.Window.gl;

public class FrameBuffer {
    public static IntBuffer id = IntBuffer.allocate(1);

    static{
        gl.glGenFramebuffers(1, id);
    }

    public static void startCopying(){
        gl.glBindFramebuffer(gl.GL_FRAMEBUFFER, id.get(0));
        gl.glFramebufferTexture2D(gl.GL_FRAMEBUFFER, gl.GL_COLOR_ATTACHMENT0, gl.GL_TEXTURE_2D, ScreenQuad.texture.id.get(0), 0);
        gl.glDrawBuffers(1, new int[]{gl.GL_COLOR_ATTACHMENT0},0);
        gl.glViewport(0,0,ScreenQuad.texture.size.x,ScreenQuad.texture.size.y);
    }

    public static void endCopying(){
        gl.glBindFramebuffer(gl.GL_FRAMEBUFFER, 0);
        gl.glViewport(0,0,Window.size.x,Window.size.y);
    }
}
