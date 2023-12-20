package com.miniai.imageutil;

public enum MiniAiRotateDegree {
    DEGREE_90(90),
    DEGREE_180(180),
    DEGREE_270(270);

    int degree;

    private MiniAiRotateDegree(int degree) {
        this.degree = degree;
    }
}
