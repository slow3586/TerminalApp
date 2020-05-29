package com.lia.renderer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.lia.renderer.Window.gl;
import static com.jogamp.opengl.GL.GL_FALSE;
import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_INFO_LOG_LENGTH;
import static com.jogamp.opengl.GL2ES2.GL_LINK_STATUS;

public abstract class ShaderProgram {
    int id;
    final List<Integer> shaders = new ArrayList<>();
    
    protected ShaderProgram(){};

    protected static ShaderProgram current;

    public void init(){
        id = gl.glCreateProgram();
    }

    protected void addShader(String code, int type) {
        int shaderID = gl.glCreateShader(type);

        String[] sarr = code.split("\n");
        for(int x = 0; x< sarr.length; x++){
            sarr[x] = sarr[x]+"\n";
        }

        gl.glShaderSource(shaderID, sarr.length, sarr, null);
        gl.glCompileShader(shaderID);
        
        IntBuffer isCompiled = IntBuffer.allocate(1);
        gl.glGetShaderiv(shaderID, GL_COMPILE_STATUS, isCompiled);
        if (isCompiled.get(0) == GL_FALSE) {
            IntBuffer max_length = IntBuffer.allocate(1);
            ByteBuffer errorLog = ByteBuffer.allocate(4096);
            gl.glGetShaderiv(shaderID, GL_INFO_LOG_LENGTH, max_length);
            gl.glGetShaderInfoLog(shaderID, max_length.get(0), max_length, errorLog);
            System.err.println("Shader comp failed! " + new String(errorLog.array(), StandardCharsets.UTF_8));
        }
        gl.glAttachShader(id, shaderID);
        shaders.add(shaderID);
    }

    final public void link() {
        gl.glLinkProgram(id);
        IntBuffer isLinked = IntBuffer.allocate(1);
        gl.glGetProgramiv(id, GL_LINK_STATUS, isLinked);
        if (isLinked.get(0) == GL_FALSE) {
            IntBuffer max_length = IntBuffer.allocate(1);
            gl.glGetProgramiv(id, GL_INFO_LOG_LENGTH, max_length);
            ByteBuffer errorLog = ByteBuffer.allocate(4096);
            gl.glGetProgramInfoLog(id, max_length.get(0), max_length, errorLog);
            throw new IllegalStateException("Shader linking failed! " + id + " " + new String(errorLog.array()));
        }
    }

    final public void use() {
        if(current != this){
            gl.glUseProgram(id);
            current = this;
        }
    }
}
