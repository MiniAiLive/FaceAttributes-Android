package com.miniai.face;

public class GenderInfo {
    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static final int UNKNOWN = -1;
    int gender;

    public GenderInfo() {
        this.gender = -1;
    }

    public GenderInfo(GenderInfo obj) {
        if (obj == null) {
            this.gender = -1;
        } else {
            this.gender = obj.getGender();
        }

    }

    public int getGender() {
        return this.gender;
    }

    public GenderInfo clone() {
        return new GenderInfo(this);
    }
}