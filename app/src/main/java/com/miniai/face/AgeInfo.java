package com.miniai.face;

public class AgeInfo {
    public static final int UNKNOWN_AGE = 0;
    int age;

    public AgeInfo() {
        this.age = 0;
    }

    public AgeInfo(AgeInfo obj) {
        if (obj == null) {
            this.age = 0;
        } else {
            this.age = obj.getAge();
        }

    }

    public int getAge() {
        return this.age;
    }

    public AgeInfo clone() {
        return new AgeInfo(this);
    }
}