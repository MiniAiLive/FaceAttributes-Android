package com.miniai.face;

import android.graphics.Rect;

public class FaceInfo {
    Rect rect;
    int orient;
    int faceId = -1;

    public FaceInfo(Rect rect, int orient) {
        this.rect = new Rect(rect);
        this.orient = orient;
    }

    public FaceInfo(FaceInfo obj) {
        if (obj == null) {
            this.rect = new Rect();
            this.orient = 0;
            this.faceId = 0;
        } else {
            this.rect = new Rect(obj.rect);
            this.orient = obj.orient;
            this.faceId = obj.faceId;
        }

    }

    public FaceInfo() {
        this.rect = new Rect();
        this.orient = 0;
    }

    public Rect getRect() {
        return this.rect;
    }

    public int getOrient() {
        return this.orient;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public int getFaceId() {
        return this.faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public String toString() {
        return this.rect.toString() + "," + this.orient;
    }

    public FaceInfo clone() {
        return new FaceInfo(this);
    }
}
