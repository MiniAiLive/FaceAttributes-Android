package com.miniai.face;

public class FaceFeature {
    public static final int FEATURE_SIZE = 1032;
    byte[] featureData;

    public FaceFeature(FaceFeature obj) {
        if (obj == null) {
            this.featureData = new byte[1032];
        } else {
            this.featureData = (byte[])obj.getFeatureData().clone();
        }

    }

    public FaceFeature() {
        this.featureData = new byte[1032];
    }

    public FaceFeature(byte[] data) {
        this.featureData = data;
    }

    public byte[] getFeatureData() {
        return this.featureData;
    }

    public void setFeatureData(byte[] data) {
        this.featureData = data;
    }

    public FaceFeature clone() {
        return new FaceFeature(this);
    }
}

