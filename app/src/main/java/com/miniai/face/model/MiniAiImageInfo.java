package com.miniai.face.model;

public class MiniAiImageInfo {
    private int width;
    private int height;
    private int imageFormat;
    private byte[][] planes;
    private int[] strides;

    public MiniAiImageInfo(int width, int height, int imageFormat) {
        this.width = width;
        this.height = height;
        this.imageFormat = imageFormat;
    }

    public MiniAiImageInfo(int width, int height, int imageFormat, byte[][] planes, int[] strides) {
        this.width = width;
        this.height = height;
        this.imageFormat = imageFormat;
        this.planes = planes;
        this.strides = strides;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getImageFormat() {
        return this.imageFormat;
    }

    public void setImageFormat(int imageFormat) {
        this.imageFormat = imageFormat;
    }

    public byte[][] getPlanes() {
        return this.planes;
    }

    public void setPlanes(byte[][] planes) {
        this.planes = planes;
    }

    public int[] getStrides() {
        return this.strides;
    }

    public void setStrides(int[] strides) {
        this.strides = strides;
    }
}