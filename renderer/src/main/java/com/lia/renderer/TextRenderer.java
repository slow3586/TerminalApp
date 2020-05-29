package com.lia.renderer;

public abstract class TextRenderer {
    public static TextRenderer impl = null;
    abstract public Texture render(String text);
}