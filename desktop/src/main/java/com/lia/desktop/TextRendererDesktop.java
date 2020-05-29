package com.lia.desktop;

import com.lia.renderer.TextRenderer;
import com.lia.renderer.Texture;
import com.lia.renderer.V2i;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class TextRendererDesktop extends TextRenderer {

    public static HashMap<String, Texture> mem = new HashMap<>();
    public static Font font;

    public TextRendererDesktop() {
        Font font = null;
        try {
            File f =  new File(this.getClass().getClassLoader().getResource("RobotoMono-Regular.ttf").getFile());
            font = Font.createFont(Font.TRUETYPE_FONT, f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextRendererDesktop.font = font.deriveFont(35.0f);
    }

    public Texture render(String text){
        if(mem.containsKey(text))
            return mem.get(text);

        FontRenderContext cont = new FontRenderContext(font.getTransform(), true, true);
        Rectangle2D r = font.getStringBounds(text, cont);
        LineMetrics m = font.getLineMetrics(text, cont);
        int w0 = (int) Math.ceil(r.getWidth());
        int h0 = (int) Math.ceil(m.getHeight());
        w0 = Math.max(w0, 1);
        h0 = Math.max(h0, 1);

        BufferedImage img = new BufferedImage(w0, h0, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0, 0, w0, h0);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text, 0, h0 - (int)m.getDescent() - 1);

        int w = img.getWidth();
        int h = img.getHeight();
        byte[] data = new byte[w*h*4];
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                Color c = new Color(img.getRGB(y, x), true);
                data[0 + y*4 + x*w*4] = (byte)c.getRed();
                data[1 + y*4 + x*w*4] = (byte)c.getGreen();
                data[2 + y*4 + x*w*4] = (byte)c.getBlue();
                data[3 + y*4 + x*w*4] = (byte)c.getAlpha();
            }
        }
        Texture t = new Texture(new V2i(w,h), data);

        mem.put(text, t);

        return t;
    }

}
