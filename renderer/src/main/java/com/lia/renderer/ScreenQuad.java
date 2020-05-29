package com.lia.renderer;

import java.nio.FloatBuffer;
import static com.lia.renderer.Window.gl;
import static com.jogamp.opengl.GL.*;

public class ScreenQuad {
    public static Texture texture;

    final public static ScreenQuad ins = new ScreenQuad();
    public static VertexArray va;

    private ScreenQuad(){
        texture = new Texture(new V2i(Window.size), null);
        texture.fillData(new byte[]{0,0,0,(byte)255});

        va = new VertexArray();
        va.bind();
        VertexBuffer vb = new VertexBuffer();

        //pos
        vb.bind();
        FloatBuffer b0 = FloatBuffer.wrap(new float[]{
                -1,-1,
                -1,1,
                1,1,
                -1,-1,
                1,-1,
                1,1
        });
        gl.glBufferData(GL_ARRAY_BUFFER, 4 * 12, b0, GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        VertexBuffer.unbind();
        VertexArray.unbind();
    }

    public static void draw(){
        gl.glClear(GL_COLOR_BUFFER_BIT);

        SPTexture.impl.use();
        gl.glUniform4f(1, 1,1,1,1);
        va.bind();
        texture.bind();
        gl.glDrawArrays(GL_TRIANGLES, 0, 6);
        //gl.glDrawArrays(GL_LINES, 0, 6);
    }
}
