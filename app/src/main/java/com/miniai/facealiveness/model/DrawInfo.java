package com.miniai.facealiveness.model;

import android.graphics.Rect;

public class DrawInfo {
    private Rect rect;
    private int liveness;
    private int color;
    private String name = null;

    public DrawInfo(Rect rect,int liveness,int color,String name) {
        this.rect = rect;
        this.liveness = liveness;
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }




    public int getLiveness() {
        return liveness;
    }

    public void setLiveness(int liveness) {
        this.liveness = liveness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
