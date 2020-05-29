package com.lia.renderer;

public class V2i {
    public int x;
    public int y;

    public V2i(){
        this(0,0);
    }

    public V2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public V2i(V2i v){
        this.x = v.x;
        this.y = v.y;
    }

    public V2i(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public V2i add(V2i other){
        this.x+=other.x;
        this.y+=other.y;
        return this;
    }

    public V2i sub(V2i other){
        this.x-=other.x;
        this.y-=other.y;
        return this;
    }

    public V2i zero(){
        this.x=0;
        this.y=0;
        return this;
    }
}
