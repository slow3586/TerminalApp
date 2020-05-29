package com.lia.renderer;

public class V4 {

    public float x=0;
    public float z=0;
    public float y=0;
    public float w=0;

    public V4(int i, int i0, int i1, int i2) {
        x=i;
        y=i0;
        z=i1;
        w=i2;
    }

    public V4(float i, float i0, float i1, float i2) {
        x=i;
        y=i0;
        z=i1;
        w=i2;
    }

    public V4() {}

    public V4(V4 v) {
        x=v.x;
        y=v.y;
        z=v.z;
        w=v.w;
    }
}
