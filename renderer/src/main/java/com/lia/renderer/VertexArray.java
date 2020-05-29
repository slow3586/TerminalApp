package com.lia.renderer;

import java.nio.IntBuffer;

import static com.lia.renderer.Window.gl;

public class VertexArray {

    private static VertexArray current;
    private IntBuffer id = IntBuffer.allocate(1);

    public VertexArray() {
        gl.glGenVertexArrays(1, id);
        bind();
    }

    public void bind(){
        if(current!=this){
            current=this;
            gl.glBindVertexArray(id.get(0));
        }
    }

    public static void unbind(){
        gl.glBindVertexArray(0);
        current = null;
    }

    public void dispose(){
        gl.glDeleteVertexArrays(1, id);
    }
}
