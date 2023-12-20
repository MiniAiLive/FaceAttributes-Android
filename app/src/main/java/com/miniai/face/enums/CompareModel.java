package com.miniai.face.enums;

public enum CompareModel {
    LIFE_PHOTO(1),
    ID_CARD(2);

    private int model;

    private CompareModel(int model) {
        this.model = model;
    }

    public int getModel() {
        return this.model;
    }
}