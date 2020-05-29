package com.lia.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static com.jogamp.opengl.GL.*;
import static com.lia.renderer.Window.gl;

public class Quad {

    public static ArrayList<Quad> needRedraw = new ArrayList<>();

    final public static int countX = 26;
    final public static int countY = 21;

    public static int sizeX = 21;
    public static int sizeY = 45;
    static{
        sizeX = (int)Math.floor(1.0f*Window.size.x/Quad.countX);
        sizeY = (int)Math.floor(1.0f*Window.size.y/Quad.countY);
    }

    public static Quad[][] quads = new Quad[Quad.countY][Quad.countX];

    float fr=1;
    float fg=1;
    float fb=1;
    float fa=1;

    float br=0;
    float bg=0;
    float bb=0;

    String txt = "";
    Texture txtTxtr = null;

    public int x;
    public int y;

    public int xs;
    public int ys;

    public VertexArray va;

    public Quad(int x, int y){
        this.x = x;
        this.y = y;
        needRedraw.add(this);

        txtTxtr = TextRenderer.impl.render("");

        va = new VertexArray();
        va.bind();
        VertexBuffer vb = new VertexBuffer();

        //pos
        vb.bind();
        float w0 = 1.0f*Quad.sizeX /Window.size.x*2;
        float h0 = 1.0f*Quad.sizeY /Window.size.y*2;
        float x0 = (w0*x)-1;
        float y0 = (h0*-y)+1;
        float x1 = x0+w0;
        float y1 = y0-h0;
        FloatBuffer b = FloatBuffer.wrap(new float[]{
                x0,y0,
                x0,y1,
                x1,y1,
                x0,y0,
                x1,y0,
                x1,y1
        });
        gl.glBufferData(GL_ARRAY_BUFFER, 4 * 12, b, GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        VertexBuffer.unbind();
        VertexArray.unbind();

        quads[y][x] = this;
    }

    public static void initAll(){
        for (int y = 0; y < Quad.countY; y++) {
            for (int x = 0; x < Quad.countX; x++) {
                Quad q = new Quad(x,y);
            }
        }
    }

    public static void drawAll(){
        if(!needRedraw.isEmpty()) {
            FrameBuffer.startCopying();

            SPColor.impl.use();
            for (Quad q : needRedraw) {
                gl.glUniform3f(0, q.br,q.bg,q.bb);
                q.va.bind();
                gl.glDrawArrays(GL_TRIANGLES, 0, 6);
            }

            SPTexture.impl.use();
            for (Quad q : needRedraw) {
                if(q.txt.equals(" ") || q.txt.equals("")) continue;

                gl.glUniform4f(1, q.fr,q.fg,q.fb,q.fa);
                q.txtTxtr.bind();
                q.va.bind();
                gl.glDrawArrays(GL_TRIANGLES, 0, 6);
            }

            needRedraw.clear();
            FrameBuffer.endCopying();
        }

        ScreenQuad.draw();
    }

    public static void setText(int x, int y, String txt){
        if(x>=Quad.countX || x<0 || y>=Quad.countY || y <0)
            throw new IllegalArgumentException();

        Quad q = quads[y][x];
        if(q.txt.equals(txt)) return;

        q.txt = txt;
        q.txtTxtr = TextRenderer.impl.render(q.txt);

        if(!needRedraw.contains(q))
            needRedraw.add(q);
        if (!needRedraw.contains(q))
            needRedraw.add(q);
    }

    public static void setBackColor(int x, int y, float r, float g, float b){
        if(x>=Quad.countX || x<0 || y>=Quad.countY || y <0)
            throw new IllegalArgumentException();

        if(r>1 || r<0)
            r = (float) (r - Math.floor(r));
        if(g>1 || g<0)
            g = (float) (g - Math.floor(g));
        if(b>1 || b<0)
            b = (float) (b - Math.floor(b));

        Quad q = quads[y][x];
        if(q.br != r || q.bg != g || q.bb != b) {

            q.br = r;
            q.bg = g;
            q.bb = b;

            if (!needRedraw.contains(q))
                needRedraw.add(q);
        }
    }

    public static void setTextColor(int x, int y, float r, float g, float b, float a){
        if(x>=Quad.countX || x<0 || y>=Quad.countY || y <0)
            throw new IllegalArgumentException();

        Quad q = quads[y][x];
        if(q.fr != r || q.fg != g || q.fb != b || q.fa != a) {

            q.fr = r;
            q.fg = g;
            q.fb = b;
            q.fa = a;

            if (!needRedraw.contains(q))
                needRedraw.add(q);
        }
    }
}
