package com.miniai.face.enums;

public enum DetectModel {
    RGB(1);

    private int model;

    private DetectModel(int model) {
        this.model = model;
    }

    public int getModel() {
        return this.model;
    }
}
