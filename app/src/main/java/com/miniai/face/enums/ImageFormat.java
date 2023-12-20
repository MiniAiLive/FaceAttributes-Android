package com.miniai.face.enums;

import com.miniai.face.FaceEngine;

public enum ImageFormat {
    RGBA(0),
    RGB(1),
    BGR(2),
    GRAY(3),
    BGRA(4),
    YCrCb(5),
    YUV(6),
    HSV(7),
    XYZ(8),
    BGR555(9),
    BGR565(10),
    YUV_NV21(11),
    YUV_NV12(12),
    YUV_I420(13),
    HSV_FULL(14);
    private long format;
    ImageFormat(int format) {
        if (format < 15){
            this.format = format;
        }
        else{
            if (format == FaceEngine.CP_PAF_NV21){
                this.format = 11;
            }
            else if (format == FaceEngine.CP_PAF_BGR24){
                this.format = 2;
            }
            else if (format == FaceEngine.CP_PAF_GRAY){
                this.format = 3;
            }
            else if (format == FaceEngine.CP_PAF_DEPTH_U16){
                this.format = 10;
            }
        }
    }

    public static int convert(int format) {
        if (format < 15){
            return format;
        }
        else{
            if (format == FaceEngine.CP_PAF_NV21){
                return 11;
            }
            else if (format == FaceEngine.CP_PAF_BGR24){
                return 2;
            }
            else if (format == FaceEngine.CP_PAF_GRAY){
                return 3;
            }
            else if (format == FaceEngine.CP_PAF_DEPTH_U16){
                return 10;
            }
        }
        return 0;
    }

    public long getFormat() {
        return this.format;
    }
}
