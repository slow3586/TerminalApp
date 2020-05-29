package com.lia.renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.jogamp.opengl.GL.*;
import static com.lia.renderer.Window.gl;

public class Texture {

    public static Texture current = null;
    IntBuffer id = IntBuffer.allocate(1);
    public V2i size = null;
    public byte[] data = null;

    public Texture(V2i size, byte[] data) {
        gl.glGenTextures(1, id);
        gl.glBindTexture(GL_TEXTURE_2D, id.get(0));
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        newData(size, data);
    }

    public void fillData(byte[] texel){
        fillData(0, 0, size.x, size.y, texel);
    }

    public void fillData(int x, int y, int w, int h, byte[] texel){
        if(x < 0 || y < 0 || w < 0 || h < 0){
            throw new IllegalArgumentException();
        }
        if(x > size.x || y > size.y || w > size.x || h > size.y){
            throw new IllegalArgumentException();
        }
        if(x+w > size.x || y+h > size.y){
            throw new IllegalArgumentException();
        }
        byte[] data = new byte[w*h*4];
        for (int i = 0; i < w*h; i++) {
            data[i*4] = texel[0];
            data[i*4+1] = texel[1];
            data[i*4+2] = texel[2];
            data[i*4+3] = texel[3];
        }
        updateData(x, y, w, h, data);
    }

    public void newData(V2i size, byte[] data){
        bind();
        this.size = size;
        this.data = data;
        if(data == null){
            gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);
        } else {
            ByteBuffer b = ByteBuffer.wrap(data);
            gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, b);
        }
        Texture.unbind();
    }

    public void updateData(byte[] data){
        updateData(0, 0, size.x, size.y, data);
    }

    public void updateData(int x, int y, int w, int h, byte[] data){
        if(x < 0 || y < 0 || w < 0 || h < 0){
            throw new IllegalArgumentException();
        }
        if(x > size.x || y > size.y || w > size.x || h > size.y){
            throw new IllegalArgumentException();
        }
        if(x+w > size.x || y+h > size.y){
            throw new IllegalArgumentException();
        }
        bind();
        ByteBuffer b = ByteBuffer.wrap(data);
        gl.glTexSubImage2D(GL_TEXTURE_2D, 0, x, y, w, h, GL_RGBA, GL_UNSIGNED_BYTE, b);
        this.data = data;
        Texture.unbind();
    }

    public void bind(){
        if(current != this){
            gl.glBindTexture(GL_TEXTURE_2D, id.get(0));
            current = this;
        }
    }

    public static void unbind(){
        if(current != null){
            gl.glBindTexture(GL_TEXTURE_2D, 0);
            current = null;
        }
    }

    public void dispose(){
        gl.glDeleteTextures(1, id);
    }
}
