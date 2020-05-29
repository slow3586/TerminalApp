package com.lia.terminalapp;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

public class SPColor extends com.lia.renderer.SPColor {

    static private String fragS = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform vec3 uColor;\n" +
            "layout(location = 0) out vec4 diffuseColor0;\n" +
            "void main() {\n" +
            "    diffuseColor0 = vec4(uColor, 1);\n" +
            "}";

    static private String vertS = "#version 300 es\n" +
            "layout (location = 0) in vec2 aPos;\n" +
            "void main(void)\n" +
            "{\n" +
            "    gl_Position =  vec4(aPos.x, aPos.y, 0.0, 1.0);\n" +
            "}\n";

    @Override
    public void init() {
        super.init();
        addShader(vertS, GL_VERTEX_SHADER);
        addShader(fragS, GL_FRAGMENT_SHADER);
    }
}
