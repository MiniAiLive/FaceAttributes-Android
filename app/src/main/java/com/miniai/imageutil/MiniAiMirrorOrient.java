package com.miniai.imageutil;

public enum MiniAiMirrorOrient {
    HORIZONTAL(true),
    VERTICAL(false);

    boolean horizontal;

    private MiniAiMirrorOrient(boolean horizontal) {
        this.horizontal = horizontal;
    }
}