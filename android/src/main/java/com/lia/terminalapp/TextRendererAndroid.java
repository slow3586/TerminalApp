package com.lia.terminalapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.lia.renderer.Quad;
import com.lia.renderer.TextRenderer;
import com.lia.renderer.Texture;
import com.lia.renderer.V2i;

import java.util.HashMap;

public class TextRendererAndroid extends TextRenderer {

    public static HashMap<String, Texture> mem = new HashMap<>();
    public static Paint textPaint;

    public TextRendererAndroid() {
        textPaint = new Paint();
        textPaint.setFakeBoldText(false);
        textPaint.setAntiAlias(true);
        textPaint.setHinting(Paint.HINTING_ON);
        textPaint.setSubpixelText(true);
        Typeface tf = Typeface.createFromAsset(MainActivity.assetManager, "RobotoMono-Regular.ttf");
        textPaint.setTypeface(tf);
        textPaint.setTextSize(75);
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
    }

    public Texture render(String text){
        for(String i : mem.keySet()){
            if(i.equals(text)){
                return mem.get(i);
            }
        }

        //measure text
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int w0 = Math.max(bounds.width(), 1);
        int h0 = Math.max(bounds.height(), 1);

        float cw = (0.75f*Quad.sizeX)/w0;
        float ch = (0.75f*Quad.sizeY)/h0;
        float c = Math.min(cw,ch);

        int w1 = (int) (w0*c);
        int h1 = (int) (h0*c);

        //generate bitmap
        //Bitmap textBitmap = Bitmap.createBitmap(w1, h1, Bitmap.Config.ARGB_8888);
        Bitmap textBitmap = Bitmap.createBitmap(Quad.sizeX, Quad.sizeY, Bitmap.Config.ARGB_8888);
        textBitmap.eraseColor(Color.argb(0,0,0,0));

        //draw on bitmap
        Canvas bitmapCanvas = new Canvas(textBitmap);
        textPaint.setARGB((int)255,(int)(255),(int)(255),(int)(255));
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        bitmapCanvas.drawText(text, 0, Quad.sizeY -fm.descent, textPaint);

        //bitmap to texture
        int w = textBitmap.getWidth();
        int h = textBitmap.getHeight();

        byte[] data = new byte[w*h*4];
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                int intColor = textBitmap.getPixel(y,x);
                data[0 + y*4 + x*w*4] = (byte) Color.red(intColor);
                data[1 + y*4 + x*w*4] = (byte)Color.green(intColor);
                data[2 + y*4 + x*w*4] = (byte)Color.blue(intColor);
                data[3 + y*4 + x*w*4] = (byte)Color.alpha(intColor);
            }
        }

        Texture tex = new Texture(new V2i(w,h), data);

        //remember texture for text
        mem.put(text, tex);

        return tex;
    }

}