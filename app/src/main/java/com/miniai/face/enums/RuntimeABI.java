package com.miniai.face.enums;

public enum RuntimeABI {
    ANDROID_ABI_UNSUPPORTED(0),
    ANDROID_ABI_ARM64(1),
    ANDROID_ABI_ARM32(2);

    int value;

    private RuntimeABI(int value) {
        this.value = value;
    }
}