package com.miniai.facealiveness.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;

public class utilhelper {
    public static Bitmap cropFace(Bitmap src, Rect faceRect) {
        // Ensure that the faceRect coordinates are valid
        int cropX1 = Math.max(0, faceRect.left);
        int cropY1 = Math.max(0, faceRect.top);
        int cropX2 = Math.min(src.getWidth(), faceRect.right);
        int cropY2 = Math.min(src.getHeight(), faceRect.bottom);

        // Calculate the dimensions of the cropped area
        int cropWidth = cropX2 - cropX1;
        int cropHeight = cropY2 - cropY1;

        // Ensure that the cropWidth and cropHeight are not negative
        if (cropWidth <= 0 || cropHeight <= 0) {
            // Return null or handle the case when no valid face is detected
            return null;
        }

        // Specify the desired output size
        int cropScaleWidth = 200;
        int cropScaleHeight = 200;

        // Calculate scaling factors for the output size
        float scaleWidth = (float) cropScaleWidth / cropWidth;
        float scaleHeight = (float) cropScaleHeight / cropHeight;

        // Create a transformation matrix to apply scaling
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // Crop and scale the face region from the source image
        Bitmap cropped = Bitmap.createBitmap(src, cropX1, cropY1, cropWidth, cropHeight, matrix, true);

        return cropped;
    }
    public static double calculateLuminance(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        long sumLuminance = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                // Calculate luminance using the formula
                double luminance = 0.299 * red + 0.587 * green + 0.114 * blue;
                sumLuminance += luminance;
            }
        }

        // Calculate the average luminance
        double averageLuminance = (double) sumLuminance / (width * height);

        return averageLuminance;
    }
    public static String facequality(Bitmap bitmap) {
        int bitmapSize = calculateBitmapSize(bitmap);

        if (bitmapSize > 1024 * 1024) {
            return "High Quality";  // If the size is larger than 1MB
        } else {
            return "Low Quality";   // If the size is 1MB or smaller
        }
    }

    public static int calculateBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else {
            return bitmap.getByteCount();
        }
    }


}
