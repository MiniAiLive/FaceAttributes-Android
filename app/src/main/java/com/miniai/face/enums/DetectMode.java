package com.miniai.face.enums;

public enum DetectMode {
    ASF_DETECT_MODE_VIDEO(0L),
    ASF_DETECT_MODE_IMAGE(4294967295L);

    private long mode;

    private DetectMode(long mode) {
        this.mode = mode;
    }

    public long getMode() {
        return this.mode;
    }
}