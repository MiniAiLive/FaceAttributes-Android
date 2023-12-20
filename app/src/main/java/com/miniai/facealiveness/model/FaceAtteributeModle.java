package com.miniai.facealiveness.model;

import android.graphics.Bitmap;

public class FaceAtteributeModle {
    private int age;
    private int liveness;
    private int gender;
    private String quality;
    private double luminance;
    private Bitmap bitmap;

    public FaceAtteributeModle()
    {

    }
    public FaceAtteributeModle(int age, int liveness, int gender, String quality, double luminance, Bitmap bitmap) {
        this.age = age;
        this.liveness = liveness;
        this.gender = gender;
        this.quality = quality;
        this.luminance = luminance;
        this.bitmap = bitmap;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLiveness() {
        return liveness;
    }

    public void setLiveness(int liveness) {
        this.liveness = liveness;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public double getLuminance() {
        return luminance;
    }

    public void setLuminance(double luminance) {
        this.luminance = luminance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
