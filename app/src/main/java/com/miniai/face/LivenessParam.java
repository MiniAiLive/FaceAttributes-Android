package com.miniai.face;

public class LivenessParam {
    private float rgbThreshold;
    private float irThreshold;

    public LivenessParam(float rgbThreshold, float irThreshold) {
        this.rgbThreshold = rgbThreshold;
        this.irThreshold = irThreshold;
    }

    public float getRgbThreshold() {
        return this.rgbThreshold;
    }

    public void setRgbThreshold(float rgbThreshold) {
        this.rgbThreshold = rgbThreshold;
    }

    public float getIrThreshold() {
        return this.irThreshold;
    }

    public void setIrThreshold(float irThreshold) {
        this.irThreshold = irThreshold;
    }
}