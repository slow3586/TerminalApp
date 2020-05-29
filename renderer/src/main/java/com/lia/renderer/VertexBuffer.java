package com.lia.renderer;

import java.nio.IntBuffer;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.lia.renderer.Window.gl;

public class VertexBuffer {
    private static VertexBuffer current;
    public IntBuffer id = IntBuffer.allocate(1);

    public VertexBuffer() {
        gl.glGenBuffers(1, id);
        bind();
    }

    public void bind(){
        if(current != this){
            gl.glBindBuffer(GL_ARRAY_BUFFER, id.get(0));
            current = this;
        }
    }

    public static void unbind(){
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        current = null;
    }

    public void dispose(){
        gl.glDeleteBuffers(1, id);
    }
}
