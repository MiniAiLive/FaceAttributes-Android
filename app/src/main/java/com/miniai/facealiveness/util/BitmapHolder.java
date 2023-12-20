package com.miniai.facealiveness.util;

import android.graphics.Bitmap;

public class BitmapHolder {
    private static BitmapHolder instance;
    private Bitmap capturedBitmap=null;

    private BitmapHolder() {
        // Private constructor to prevent instantiation.
    }

    public static BitmapHolder getInstance() {
        if (instance == null) {
            synchronized (BitmapHolder.class) {
                if (instance == null) {
                    instance = new BitmapHolder();
                }
            }
        }
        return instance;
    }

    public Bitmap getCapturedBitmap() {
        return capturedBitmap;
    }

    public void setCapturedBitmap(Bitmap bitmap) {
        capturedBitmap = bitmap;
    }
}
