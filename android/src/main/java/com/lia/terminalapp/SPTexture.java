package com.lia.terminalapp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES2.*;

public class SPTexture extends com.lia.renderer.SPTexture {

    static private String fragS = "#version 300 es\n" +
            "precision mediump float;\n" +
            "in vec2 aUV_v;\n" +
            "uniform sampler2D sampler;\n" +
            "layout(location = 1) uniform vec4 uTint;\n" +
            "layout(location = 0) out vec4 diffuseColor0;\n" +
            "void main() {\n" +
            "    diffuseColor0 = texture ( sampler, vec2(aUV_v.x, aUV_v.y) );\n" +
            "\tdiffuseColor0.x *= uTint.x;\n" +
            "\tdiffuseColor0.y *= uTint.y;\n" +
            "\tdiffuseColor0.z *= uTint.z;\n" +
            "\tdiffuseColor0.w *= uTint.w;\n" +
            "}";

    static private String vertS = "#version 300 es\n" +
            "layout (location = 0) in vec2 aPos;\n" +
            "out vec2 aUV_v;\n" +
            "void main(void)\n" +
            "{\n" +
            "    const float[] cUV = float[12](\n" +
            "0.0,0.0,\n" +
            "0.0,1.0,\n" +
            "1.0,1.0,\n" +
            "0.0,0.0,\n" +
            "1.0,0.0,\n" +
            "1.0,1.0);\n" +
            "    gl_Position =  vec4(aPos.x, aPos.y, 0.0, 1.0);\n" +
            "    aUV_v = vec2(cUV[gl_VertexID*2],cUV[gl_VertexID*2+1]);\n" +
            "}\n";

    @Override
    public void init() {
        super.init();
        addShader(vertS, GL_VERTEX_SHADER);
        addShader(fragS, GL_FRAGMENT_SHADER);
    }
}
