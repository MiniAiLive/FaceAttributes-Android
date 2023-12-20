package com.miniai.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.miniai.face.enums.CompareModel;
import com.miniai.face.enums.DetectFaceOrientPriority;
import com.miniai.face.enums.DetectMode;
import com.miniai.face.enums.DetectModel;
import com.miniai.face.enums.ImageFormat;
import com.miniai.face.enums.RuntimeABI;
import com.miniai.face.model.MiniAiImageInfo;

import java.util.List;

public class FaceEngine {
    public static final int ASF_NONE = 0;
    public static final int ASF_FACE_DETECT = 1;
    public static final int ASF_FACE_RECOGNITION = 4;
    public static final int ASF_AGE = 8;
    public static final int ASF_GENDER = 16;
    public static final int ASF_FACE3DANGLE = 32;
    public static final int ASF_LIVENESS = 128;
    public static final int ASF_IR_LIVENESS = 1024;
    public static final int CP_PAF_NV21 = 2050;
    public static final int CP_PAF_BGR24 = 513;
    public static final int CP_PAF_GRAY = 1793;
    public static final int CP_PAF_DEPTH_U16 = 3074;
    public static final int ASF_OC_0 = 1;
    public static final int ASF_OC_90 = 2;
    public static final int ASF_OC_270 = 3;
    public static final int ASF_OC_180 = 4;
    public static final int ASF_OC_30 = 5;
    public static final int ASF_OC_60 = 6;
    public static final int ASF_OC_120 = 7;
    public static final int ASF_OC_150 = 8;
    public static final int ASF_OC_210 = 9;
    public static final int ASF_OC_240 = 10;
    public static final int ASF_OC_300 = 11;
    public static final int ASF_OC_330 = 12;
    private long handle = 0L;
    private ErrorInfo initError = new ErrorInfo();
    private ErrorInfo detectError = new ErrorInfo();
    private ErrorInfo processError = new ErrorInfo();
    private ErrorInfo processIrError = new ErrorInfo();
    private ErrorInfo frError = new ErrorInfo();
    private FaceInfo[] mFaceInfoArray;
    private AgeInfo[] mAgeInfoArray;
    private GenderInfo[] mGenderInfoArray;
    private Face3DAngle[] mFaceAngleArray;
    private LivenessInfo[] mLivenessInfoArray;
    private LivenessInfo[] mIrLivenessInfoArray;

    private native long nativeInitFaceEngine(Context var1, long var2, int var4, int var5, int var6, int var7, ErrorInfo var8);

    private static native int nativeActiveFaceEngine(Context var0, String var1, String var2);

    private static native int nativeActiveFaceEngineOnline(Context var0, String var1, String var2);

    private static native int nativeGetActiveFile(Context var0, ActiveFileInfo var1);

    private native int nativeUnInitFaceEngine(long var1);

    private native int nativeDetectFaces(long handle, byte[] imgData, int imgFormat, int width, int height, int detectModel, FaceInfo[] faceInfoArray, ErrorInfo detectError);

    private native int nativeDetectFaces(long handle, Bitmap srcBitmap, int detectModel, FaceInfo[] faceInfoArray, ErrorInfo detectError);

    private native void nativeProcess(long handle, byte[] imgData, int imgFormat, int width, int height, FaceInfo[] faceInfoArray, int combinedMask, ErrorInfo processError);

    private native void nativeProcess(long handle, Bitmap srcBitmap, FaceInfo[] faceInfoArray, int combinedMask, ErrorInfo processError);

    private native void nativeProcessIr(long var1, byte[] var3, int var4, int var5, int var6, FaceInfo[] var7, int var8, ErrorInfo var9);

    private native void nativeProcessIr(long var1, MiniAiImageInfo var3, FaceInfo[] var4, int var5, ErrorInfo var6);

    private native void nativeExtractFaceFeature(long handle, byte[] imgData, int imgFormat, int width, int height, Rect faceRect, int orient, byte[] featureData, ErrorInfo errorInfo);

    private native void nativeExtractFaceFeature(long handle, Bitmap srcBitmap, Rect faceRect, int orient, byte[] featureData, ErrorInfo errorInfo);

    private native float nativePairMatching(long handle, byte[] feature1, byte[] feature2, int compareModel, ErrorInfo errorInfo);

    private native int nativeGetAge(long handle, AgeInfo[] ageInfoArray, ErrorInfo errorInfo);

    private native int nativeGetGender(long handle, GenderInfo[] genderInfoArray, ErrorInfo errorInfo);

    private native int nativeGetFace3DAngle(long var1, Face3DAngle[] var3, ErrorInfo var4);

    private native int nativeSetLivenessParam(long var1, LivenessParam var3);

    private native int nativeGetLiveness(long handle, LivenessInfo[] livenessInfoArray, ErrorInfo errorInfo);

    private native int nativeGetIrLiveness(long var1, LivenessInfo[] var3, ErrorInfo var4);

    private static native void nativeGetVersion(VersionInfo versionInfo);

    private static native int nativeGetRuntimeABI();

    public static RuntimeABI getRuntimeABI() {
        int runtimeCpuArch = nativeGetRuntimeABI();
        switch(runtimeCpuArch) {
            case 1:
                return RuntimeABI.ANDROID_ABI_ARM64;
            case 2:
                return RuntimeABI.ANDROID_ABI_ARM32;
            default:
                return RuntimeABI.ANDROID_ABI_UNSUPPORTED;
        }
    }

    public FaceEngine() {
    }

    public static int active(Context context, String appId, String sdkKey) {
        return context != null && appId != null && sdkKey != null ? nativeActiveFaceEngine(context, appId, sdkKey) : 2;
    }

    public static int activeOnline(Context context, String appId, String sdkKey) {
        return context != null && appId != null && sdkKey != null ? nativeActiveFaceEngineOnline(context, appId, sdkKey) : 2;
    }

    public static int getActiveFileInfo(Context context, ActiveFileInfo activeFileInfo) {
        return activeFileInfo == null ? 2 : nativeGetActiveFile(context, activeFileInfo);
    }

    public int init(Context context, DetectMode detectMode, DetectFaceOrientPriority detectFaceOrientPriority, int detectFaceScaleVal, int detectFaceMaxNum, int combinedMask) {
        if (this.handle != 0L) {
            return 5;
        } else if (context != null && detectMode != null && detectFaceOrientPriority != null) {
            this.handle = this.nativeInitFaceEngine(context, detectMode.getMode(), detectFaceOrientPriority.getPriority(), detectFaceScaleVal, detectFaceMaxNum, combinedMask, this.initError);
            Log.e("FaceEngine:", String.valueOf(this.handle));
            if (this.initError.getCode() == 0) {
                int i;
                if ((combinedMask & 1) != 0) {
                    this.mFaceInfoArray = new FaceInfo[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mFaceInfoArray[i] = new FaceInfo();
                    }
                }

                if ((combinedMask & 8) != 0) {
                    this.mAgeInfoArray = new AgeInfo[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mAgeInfoArray[i] = new AgeInfo();
                    }
                }

                if ((combinedMask & 16) != 0) {
                    this.mGenderInfoArray = new GenderInfo[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mGenderInfoArray[i] = new GenderInfo();
                    }
                }

                if ((combinedMask & 32) != 0) {
                    this.mFaceAngleArray = new Face3DAngle[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mFaceAngleArray[i] = new Face3DAngle();
                    }
                }

                if ((combinedMask & 128) != 0) {
                    this.mLivenessInfoArray = new LivenessInfo[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mLivenessInfoArray[i] = new LivenessInfo();
                    }
                }

                if ((combinedMask & 1024) != 0) {
                    this.mIrLivenessInfoArray = new LivenessInfo[detectFaceMaxNum];

                    for(i = 0; i < detectFaceMaxNum; ++i) {
                        this.mIrLivenessInfoArray[i] = new LivenessInfo();
                    }
                }
            }

            return this.initError.code;
        } else {
            return 2;
        }
    }

    public int unInit() {
        if (this.handle != 0L) {
            int unInitEngineCode = this.nativeUnInitFaceEngine(this.handle);
            if (unInitEngineCode == 0) {
                this.handle = 0L;
            }

            return unInitEngineCode;
        } else {
            return 5;
        }
    }

    public int detectFaces(byte[] data, int width, int height, int format, List<FaceInfo> faceInfoList) {
        return this.detectFaces(data, width, height, format, DetectModel.RGB, faceInfoList);
    }

    public int detectFaces(byte[] data, int width, int height, int format, DetectModel detectModel, List<FaceInfo> faceInfoList) {
        if (format != 2050 && format != 513 && format != 1793 && format != 3074) {
            return 90126;
        } else if (width > 0 && height > 0) {
            if (faceInfoList != null && data != null && detectModel != null) {
                if (!isImageDataValid(data, width, height, format)) {
                    return 86021;
                } else if (this.handle == 0L) {
                    return 5;
                } else {
                    faceInfoList.clear();
                    int count = this.nativeDetectFaces(this.handle, data, ImageFormat.convert(format), width, height, detectModel.getModel(), this.mFaceInfoArray, this.detectError);
                    if (count > 0) {
                        for(int i = 0; i < count; ++i) {
                            faceInfoList.add(new FaceInfo(this.mFaceInfoArray[i]));
                        }
                    }

                    return this.detectError.code;
                }
            } else {
                return 2;
            }
        } else {
            return 90127;
        }
    }

    public int detectFaces(Bitmap bitmapInfo, List<FaceInfo> faceInfoList) {
        return this.detectFaces(bitmapInfo, DetectModel.RGB, faceInfoList);
    }

    public int detectFaces(Bitmap bitmapInfo, DetectModel detectModel, List<FaceInfo> faceInfoList) {
        if (faceInfoList != null && bitmapInfo != null) {
            Bitmap.Config imageFormat = bitmapInfo.getConfig();
            if (imageFormat != Bitmap.Config.RGB_565 && imageFormat != Bitmap.Config.ARGB_8888
                    && imageFormat != Bitmap.Config.ARGB_4444 && imageFormat != Bitmap.Config.ALPHA_8) {
                return 90126;
            } else if (bitmapInfo.getWidth() > 0 && bitmapInfo.getHeight() > 0) {
//                if (!isImageDataValid(bitmapInfo)) {
//                    return 86021;
//                } else
                if (this.handle == 0L) {
                    return 5;
                } else {
                    faceInfoList.clear();
                    int count = this.nativeDetectFaces(this.handle, bitmapInfo, detectModel.getModel(), this.mFaceInfoArray, this.detectError);
                    if (count > 0) {
                        for(int i = 0; i < count; ++i) {
                            faceInfoList.add(new FaceInfo(this.mFaceInfoArray[i]));
                        }
                    }

                    return this.detectError.code;
                }
            } else {
                return 90127;
            }
        } else {
            return 2;
        }
    }

    public int process(byte[] data, int width, int height, int format, List<FaceInfo> faceInfoList, int combinedMask) {
        if (format != 2050 && format != 513) {
            return 90126;
        } else if (width > 0 && height > 0) {
            if (faceInfoList != null && data != null) {
                if (!isImageDataValid(data, width, height, format)) {
                    return 86021;
                } else if (this.handle == 0L) {
                    return 5;
                } else {
                    this.nativeProcess(this.handle, data, ImageFormat.convert(format),  width, height, (FaceInfo[])faceInfoList.toArray(new FaceInfo[0]), combinedMask, this.processError);
                    return this.processError.code;
                }
            } else {
                return 2;
            }
        } else {
            return 90127;
        }
    }

    public int process(Bitmap bitmapInfo, List<FaceInfo> faceInfoList, int combinedMask) {
        Bitmap.Config imageFormat = bitmapInfo.getConfig();
        if (imageFormat != Bitmap.Config.RGB_565 && imageFormat != Bitmap.Config.ARGB_8888
                && imageFormat != Bitmap.Config.ARGB_4444 && imageFormat != Bitmap.Config.ALPHA_8) {
            return 90126;
        } else if (bitmapInfo.getWidth() > 0 && bitmapInfo.getHeight() > 0) {
            if (faceInfoList != null) {
//                if (!isImageDataValid(data, width, height, format)) {
//                    return 86021;
//                } else
                if (this.handle == 0L) {
                    return 5;
                } else {
                    this.nativeProcess(this.handle, bitmapInfo, (FaceInfo[])faceInfoList.toArray(new FaceInfo[0]), combinedMask, this.processError);
                    return this.processError.code;
                }
            } else {
                return 2;
            }
        } else {
            return 90127;
        }
    }

    public int extractFaceFeature(byte[] data, int width, int height, int format, FaceInfo faceInfo, FaceFeature feature) {
        if (format != 2050 && format != 513 && format != 1793) {
            return 90126;
        } else if (width > 0 && height > 0) {
            if (feature != null && feature.getFeatureData() != null && feature.getFeatureData().length >= 1032 && faceInfo != null && data != null) {
                if (!isImageDataValid(data, width, height, format)) {
                    return 86021;
                } else if (this.handle == 0L) {
                    return 5;
                } else {
                    this.nativeExtractFaceFeature(this.handle, data, width, height, format, faceInfo.getRect(), faceInfo.getOrient(), feature.getFeatureData(), this.frError);
                    return this.frError.code;
                }
            } else {
                return 2;
            }
        } else {
            return 90127;
        }
    }

    public int extractFaceFeature(Bitmap bitmapInfo, FaceInfo faceInfo, FaceFeature feature) {
        Bitmap.Config imageFormat = bitmapInfo.getConfig();
        if (feature != null && feature.getFeatureData() != null && feature.getFeatureData().length >= 1032 && faceInfo != null && bitmapInfo != null) {
            if (imageFormat != Bitmap.Config.RGB_565 && imageFormat != Bitmap.Config.ARGB_8888
                    && imageFormat != Bitmap.Config.ARGB_4444 && imageFormat != Bitmap.Config.ALPHA_8) {
                return 90126;
            } else if (bitmapInfo.getWidth() > 0 && bitmapInfo.getHeight() > 0) {
//                if (!isImageDataValid(MiniAiImageInfo)) {
//                    return 86021;
//                } else
                if (this.handle == 0L) {
                    return 5;
                } else {
                    this.nativeExtractFaceFeature(this.handle, bitmapInfo, faceInfo.getRect(), faceInfo.getOrient(), feature.getFeatureData(), this.frError);
                    return this.frError.code;
                }
            } else {
                return 90127;
            }
        } else {
            return 2;
        }
    }

    public int compareFaceFeature(FaceFeature feature1, FaceFeature feature2, FaceSimilar faceSimilar) {
        return this.compareFaceFeature(feature1, feature2, CompareModel.LIFE_PHOTO, faceSimilar);
    }

    public int compareFaceFeature(FaceFeature feature1, FaceFeature feature2, CompareModel compareModel, FaceSimilar faceSimilar) {
        if (feature1 != null && feature1.getFeatureData() != null && feature2 != null && feature2.getFeatureData() != null && faceSimilar != null && compareModel != null) {
            if (this.handle == 0L) {
                return 5;
            } else {
                faceSimilar.score = this.nativePairMatching(this.handle, feature1.getFeatureData(), feature2.getFeatureData(), compareModel.getModel(), this.frError);
                return this.frError.code;
            }
        } else {
            return 2;
        }
    }

    public int getAge(List<AgeInfo> ageInfoList) {
        if (ageInfoList == null) {
            return 2;
        } else if (this.handle == 0L) {
            return 5;
        } else {
            ageInfoList.clear();
            int count = this.nativeGetAge(this.handle, this.mAgeInfoArray, this.processError);
            if (count > 0) {
                for(int i = 0; i < count; ++i) {
                    ageInfoList.add(new AgeInfo(this.mAgeInfoArray[i]));
                }
            }

            return this.processError.code;
        }
    }

    public int getGender(List<GenderInfo> genderInfoList) {
        if (genderInfoList == null) {
            return 2;
        } else if (this.handle == 0L) {
            return 5;
        } else {
            genderInfoList.clear();
            int count = this.nativeGetGender(this.handle, this.mGenderInfoArray, this.processError);
            if (count > 0) {
                for(int i = 0; i < count; ++i) {
                    genderInfoList.add(new GenderInfo(this.mGenderInfoArray[i]));
                }
            }

            return this.processError.code;
        }
    }

    public int getFace3DAngle(List<Face3DAngle> face3DAngleList) {
        if (face3DAngleList == null) {
            return 2;
        } else if (this.handle == 0L) {
            return 5;
        } else {
            face3DAngleList.clear();
            int count = this.nativeGetFace3DAngle(this.handle, this.mFaceAngleArray, this.processError);

            for(int i = 0; i < count; ++i) {
                face3DAngleList.add(new Face3DAngle(this.mFaceAngleArray[i]));
            }

            return this.processError.code;
        }
    }

    public int setLivenessParam(LivenessParam livenessParam) {
        if (livenessParam != null && livenessParam.getIrThreshold() <= 1.0F && livenessParam.getIrThreshold() >= 0.0F && livenessParam.getRgbThreshold() <= 1.0F && livenessParam.getRgbThreshold() >= 0.0F) {
            return this.handle == 0L ? 5 : this.nativeSetLivenessParam(this.handle, livenessParam);
        } else {
            return 2;
        }
    }

    public int getLiveness(List<LivenessInfo> livenessInfoList) {
        if (livenessInfoList == null) {
            return 2;
        } else if (this.handle == 0L) {
            return 5;
        } else {
            livenessInfoList.clear();
            int count = this.nativeGetLiveness(this.handle, this.mLivenessInfoArray, this.processError);

            for(int i = 0; i < count; ++i) {
                livenessInfoList.add(new LivenessInfo(this.mLivenessInfoArray[i]));
            }

            return this.processError.code;
        }
    }

    public int getIrLiveness(List<LivenessInfo> irLivenessInfoList) {
        if (irLivenessInfoList == null) {
            return 2;
        } else if (this.handle == 0L) {
            return 5;
        } else {
            irLivenessInfoList.clear();
            int count = this.nativeGetIrLiveness(this.handle, this.mIrLivenessInfoArray, this.processIrError);

            for(int i = 0; i < count; ++i) {
                irLivenessInfoList.add(new LivenessInfo(this.mIrLivenessInfoArray[i]));
            }

            return this.processIrError.code;
        }
    }

    public static int getVersion(VersionInfo versionInfo) {
        if (versionInfo == null) {
            return 2;
        } else {
            nativeGetVersion(versionInfo);
            return 0;
        }
    }

    private static boolean isImageDataValid(byte[] data, int width, int height, int format) {
        return format == 2050 && (height & 1) == 0 && data.length == width * height * 3 / 2 || format == 513 && data.length == width * height * 3 || format == 1793 && data.length == width * height || format == 3074 && data.length == width * height * 2;
    }

    private static boolean isImageDataValid(MiniAiImageInfo MiniAiImageInfo) {
        byte[][] planes = MiniAiImageInfo.getPlanes();
        int[] strides = MiniAiImageInfo.getStrides();
        if (planes != null && strides != null) {
            if (planes.length != strides.length) {
                return false;
            } else {
                byte[][] var3 = planes;
                int var4 = planes.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    byte[] plane = var3[var5];
                    if (plane == null || plane.length == 0) {
                        return false;
                    }
                }

                switch(MiniAiImageInfo.getImageFormat()) {
                    case 513:
                    case 1793:
                    case 3074:
                        return planes.length == 1 && planes[0].length == MiniAiImageInfo.getStrides()[0] * MiniAiImageInfo.getHeight();
                    case 2050:
                        return (MiniAiImageInfo.getHeight() & 1) == 0 && planes.length == 2 && planes[0].length == planes[1].length * 2 && planes[0].length == MiniAiImageInfo.getStrides()[0] * MiniAiImageInfo.getHeight() && planes[1].length == MiniAiImageInfo.getStrides()[1] * MiniAiImageInfo.getHeight() / 2;
                    default:
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    static {
        System.loadLibrary("miniai_face_engine");
    }
}