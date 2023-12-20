package com.miniai.imageutil;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public final class MiniAiImageUtil {
    private static final int WIDTH_MULTIPLE = 4;
    private static final int HEIGHT_MULTIPLE = 4;
    private static final int VALUE_FOR_4_ALIGN = 3;

    public MiniAiImageUtil() {
    }

    public static Bitmap getAlignedBitmap(Bitmap bitmap, boolean crop) {
        Log.e("getAlignedBitmap", " bitmap.getConfig=" + String.valueOf(bitmap.getConfig()) + ":" + bitmap.getWidth() + ":" + bitmap.getHeight());
        if (bitmap == null) {
            return null;
        } else if (bitmap.getConfig() != Bitmap.Config.ARGB_8888 && bitmap.getConfig() != Bitmap.Config.RGB_565) {
            return null;
        } else if ((bitmap.getWidth() & 3) == 0 && (bitmap.getHeight() & 3) == 0) {
            return bitmap;
        } else {
            int newWidth = crop ? bitmap.getWidth() & -4 : bitmap.getWidth() + (4 - (bitmap.getWidth() & 3)) % 4;
            int newHeight = crop ? bitmap.getHeight() & -4 : bitmap.getHeight() + (4 - (bitmap.getHeight() & 3)) % 4;
            Bitmap newBmp = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
            nativeAlignBitmap(bitmap, newBmp);
            return newBmp;
        }
    }

    public static byte[] createImageData(int width, int height, MiniAiImageFormat MiniAiImageFormat) {
        return new byte[getDataLengthBySizeAndFormat(width, height, MiniAiImageFormat)];
    }

    private static int getDataLengthBySizeAndFormat(int width, int height, MiniAiImageFormat MiniAiImageFormat) {
        if (MiniAiImageFormat == null) {
            throw new NullPointerException("MiniAiImageFormat not specified");
        } else if ((width & 3) == 0 && (height & 3) == 0) {
            switch(MiniAiImageFormat) {
                case NV21:
                case NV12:
                case I420:
                case YV12:
                    return width * height * 3 / 2;
                case BGR24:
                    return width * height * 3;
                case YUYV:
                    return width * height * 2;
                case GRAY:
                    return width * height;
                default:
                    throw new IllegalArgumentException("invalid MiniAiImageFormat '" + MiniAiImageFormat + "'");
            }
        } else {
            throw new IllegalArgumentException("invalid width or height, width and height must be a multiple of 4");
        }
    }

    private static native String nativeGetVersion();

    private static native int nativeImageFormatTransform(byte[] var0, byte[] var1, int var2, int var3, int var4, int var5);

    private static native int nativeBitmapToImageData(Bitmap var0, byte[] var1, int var2);

    private static native int nativeImageDataToBitmap(byte[] var0, Bitmap var1, int var2);

    private static native void nativeAlignBitmap(Bitmap var0, Bitmap var1);

    private static native int nativeCropImage(byte[] var0, byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    private static native int nativeRotateImage(byte[] var0, byte[] var1, int var2, int var3, int var4, int var5);

    private static native int nativeMirrorImage(byte[] var0, byte[] var1, int var2, int var3, boolean var4, int var5);

    public static int bitmapToImageData(Bitmap bitmap, byte[] data, MiniAiImageFormat MiniAiImageFormat) {
        if (MiniAiImageFormat != null && data != null && bitmap != null) {
            return data.length != getDataLengthBySizeAndFormat(bitmap.getWidth(), bitmap.getHeight(), MiniAiImageFormat) ? 1 : nativeBitmapToImageData(bitmap, data, MiniAiImageFormat.format);
        } else {
            return 4;
        }
    }

    public static int imageDataToBitmap(byte[] data, Bitmap bitmap, MiniAiImageFormat MiniAiImageFormat) {
        if (MiniAiImageFormat != null && data != null && bitmap != null) {
            return data.length != getDataLengthBySizeAndFormat(bitmap.getWidth(), bitmap.getHeight(), MiniAiImageFormat) ? 1 : nativeImageDataToBitmap(data, bitmap, MiniAiImageFormat.format);
        } else {
            return 4;
        }
    }

    public static int cropImage(byte[] originImageData, byte[] cropImageData, int originWidth, int originHeight, Rect rect, MiniAiImageFormat MiniAiImageFormat) {
        return rect != null && MiniAiImageFormat != null ? nativeCropImage(originImageData, cropImageData, originWidth, originHeight, rect.left, rect.top, rect.right, rect.bottom, MiniAiImageFormat.format) : 4;
    }

    public static int cropImage(byte[] originImageData, byte[] cropImageData, int originWidth, int originHeight, int left, int top, int right, int bottom, MiniAiImageFormat MiniAiImageFormat) {
        return MiniAiImageFormat == null ? 4 : nativeCropImage(originImageData, cropImageData, originWidth, originHeight, left, top, right, bottom, MiniAiImageFormat.format);
    }

    public static int cropImage(byte[] originImageData, byte[] cropImageData, int originWidth, int originHeight, Point leftTop, Point rightBottom, MiniAiImageFormat MiniAiImageFormat) {
        return MiniAiImageFormat == null ? 4 : nativeCropImage(originImageData, cropImageData, originWidth, originHeight, leftTop.x, leftTop.y, rightBottom.x, rightBottom.y, MiniAiImageFormat.format);
    }

    public static int rotateImage(byte[] originImageData, byte[] rotateImageData, int originWidth, int originHeight, MiniAiRotateDegree degree, MiniAiImageFormat MiniAiImageFormat) {
        return originImageData != null && rotateImageData != null && MiniAiImageFormat != null && degree != null ? nativeRotateImage(originImageData, rotateImageData, originWidth, originHeight, degree.degree, MiniAiImageFormat.format) : 4;
    }

    public static int mirrorImage(byte[] originImageData, byte[] mirrorImageData, int width, int height, MiniAiMirrorOrient MiniAiMirrorOrient, MiniAiImageFormat MiniAiImageFormat) {
        return MiniAiMirrorOrient != null && MiniAiImageFormat != null ? nativeMirrorImage(originImageData, mirrorImageData, width, height, MiniAiMirrorOrient.horizontal, MiniAiImageFormat.format) : 4;
    }

    public static int transformImage(byte[] originImageData, byte[] targetImageData, int width, int height, MiniAiImageFormat originFormat, MiniAiImageFormat targetFormat) {
        if (originFormat != null && targetFormat != null && originImageData != null && targetImageData != null) {
            if (originFormat == targetFormat) {
                if (originImageData.length == targetImageData.length && originImageData.length == getDataLengthBySizeAndFormat(width, height, originFormat)) {
                    if (originImageData == targetImageData) {
                        return 5;
                    } else {
                        System.arraycopy(originImageData, 0, targetImageData, 0, originImageData.length);
                        return 0;
                    }
                } else {
                    return 1;
                }
            } else {
                return nativeImageFormatTransform(originImageData, targetImageData, width, height, originFormat.format, targetFormat.format);
            }
        } else {
            return 4;
        }
    }

    public static String getVersion() {
        return nativeGetVersion();
    }

    static {
//        System.loadLibrary("miniai_face_engine");
    }
}
