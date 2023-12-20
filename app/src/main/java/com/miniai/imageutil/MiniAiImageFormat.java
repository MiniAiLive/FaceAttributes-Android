package com.miniai.imageutil;

public enum MiniAiImageFormat {
    BGR24(513),
    NV21(2050),
    NV12(2049),
    I420(1537),
    YV12(1541),
    YUYV(1281),
    GRAY(1793);

    int format;

    private MiniAiImageFormat(int format) {
        this.format = format;
    }

    public static MiniAiImageFormat valueOf(int formatValue) throws IllegalArgumentException {
        MiniAiImageFormat[] values = values();
        MiniAiImageFormat[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            MiniAiImageFormat value = var2[var4];
            if (value.format == formatValue) {
                return value;
            }
        }

        throw new IllegalArgumentException("formatValue '" + formatValue + "' does not match any format");
    }
}