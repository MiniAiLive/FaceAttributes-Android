package com.miniai.face;

public class ErrorInfo {
    public static final int MOK = 0;
    public static final int MERR_BASIC_BASE = 1;
    public static final int MERR_UNKNOWN = 1;
    public static final int MERR_INVALID_PARAM = 2;
    public static final int MERR_UNSUPPORTED = 3;
    public static final int MERR_NO_MEMORY = 4;
    public static final int MERR_BAD_STATE = 5;
    public static final int MERR_USER_CANCEL = 6;
    public static final int MERR_EXPIRED = 7;
    public static final int MERR_USER_PAUSE = 8;
    public static final int MERR_BUFFER_OVERFLOW = 9;
    public static final int MERR_BUFFER_UNDERFLOW = 10;
    public static final int MERR_NO_DISKSPACE = 11;
    public static final int MERR_COMPONENT_NOT_EXIST = 12;
    public static final int MERR_GLOBAL_DATA_NOT_EXIST = 13;
    public static final int MERR_FSDK_BASE = 28672;
    public static final int MERR_FSDK_INVALID_APP_ID = 28673;
    public static final int MERR_FSDK_INVALID_SDK_ID = 28674;
    public static final int MERR_FSDK_INVALID_ID_PAIR = 28675;
    public static final int MERR_FSDK_MISMATCH_ID_AND_SDK = 28676;
    public static final int MERR_FSDK_SYSTEM_VERSION_UNSUPPORTED = 28677;
    public static final int MERR_FSDK_FR_ERROR_BASE = 73728;
    public static final int MERR_FSDK_FR_INVALID_MEMORY_INFO = 73729;
    public static final int MERR_FSDK_FR_INVALID_IMAGE_INFO = 73730;
    public static final int MERR_FSDK_FR_INVALID_FACE_INFO = 73731;
    public static final int MERR_FSDK_FR_NO_GPU_AVAILABLE = 73732;
    public static final int MERR_FSDK_FR_MISMATCHED_FEATURE_LEVEL = 73733;
    public static final int MERR_FSDK_FACEFEATURE_ERROR_BASE = 81920;
    public static final int MERR_FSDK_FACEFEATURE_UNKNOWN = 81921;
    public static final int MERR_FSDK_FACEFEATURE_MEMORY = 81922;
    public static final int MERR_FSDK_FACEFEATURE_INVALID_FORMAT = 81923;
    public static final int MERR_FSDK_FACEFEATURE_INVALID_PARAM = 81924;
    public static final int MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL = 81925;
    public static final int MERR_ASF_EX_BASE = 86016;
    public static final int MERR_ASF_EX_FEATURE_UNSUPPORTED_ON_INIT = 86017;
    public static final int MERR_ASF_EX_FEATURE_UNINITED = 86018;
    public static final int MERR_ASF_EX_FEATURE_UNPROCESSED = 86019;
    public static final int MERR_ASF_EX_FEATURE_UNSUPPORTED_ON_PROCESS = 86020;
    public static final int MERR_ASF_EX_INVALID_IMAGE_INFO = 86021;
    public static final int MERR_ASF_EX_INVALID_FACE_INFO = 86022;
    public static final int MERR_ASF_BASE = 90112;
    public static final int MERR_ASF_ACTIVATION_FAIL = 90113;
    public static final int MERR_ASF_ALREADY_ACTIVATED = 90114;
    public static final int MERR_ASF_NOT_ACTIVATED = 90115;
    public static final int MERR_ASF_SCALE_NOT_SUPPORT = 90116;
    public static final int MERR_ASF_ACTIVEFILE_SDKTYPE_MISMATCH = 90117;
    public static final int MERR_ASF_DEVICE_MISMATCH = 90118;
    public static final int MERR_ASF_UNIQUE_IDENTIFIER_ILLEGAL = 90119;
    public static final int MERR_ASF_PARAM_NULL = 90120;
    public static final int MERR_ASF_LIVENESS_EXPIRED = 90121;
    public static final int MERR_ASF_VERSION_NOT_SUPPORT = 90122;
    public static final int MERR_ASF_SIGN_ERROR = 90123;
    public static final int MERR_ASF_DATABASE_ERROR = 90124;
    public static final int MERR_ASF_UNIQUE_CHECKOUT_FAIL = 90125;
    public static final int MERR_ASF_COLOR_SPACE_NOT_SUPPORT = 90126;
    public static final int MERR_ASF_IMAGE_WIDTH_HEIGHT_NOT_SUPPORT = 90127;
    public static final int MERR_ASF_BASE_EXTEND = 90128;
    public static final int MERR_ASF_READ_PHONE_STATE_DENIED = 90128;
    public static final int MERR_ASF_ACTIVATION_DATA_DESTROYED = 90129;
    public static final int MERR_ASF_SERVER_UNKNOWN_ERROR = 90130;
    public static final int MERR_ASF_INTERNET_DENIED = 90131;
    public static final int MERR_ASF_ACTIVEFILE_SDK_MISMATCH = 90132;
    public static final int MERR_ASF_DEVICEINFO_LESS = 90133;
    public static final int MERR_ASF_LOCAL_TIME_NOT_CALIBRATED = 90134;
    public static final int MERR_ASF_APPID_DATA_DECRYPT = 90135;
    public static final int MERR_ASF_APPID_APPKEY_SDK_MISMATCH = 90136;
    public static final int MERR_ASF_NO_REQUEST = 90137;
    public static final int MERR_ASF_ACTIVE_FILE_NO_EXIST = 90138;
    public static final int MERR_ASF_CURRENT_DEVICE_TIME_INCORRECT = 90139;
    public static final int MERR_ASF_DETECT_MODEL_UNSUPPORTED = 90140;
    public static final int MERR_ASF_ACTIVATION_QUANTITY_OUT_OF_LIMIT = 90141;
    public static final int MERR_ASF_IP_BLACK_LIST = 90142;
    public static final int MERR_ASF_NETWORK_BASE = 94208;
    public static final int MERR_ASF_NETWORK_COULDNT_RESOLVE_HOST = 94209;
    public static final int MERR_ASF_NETWORK_COULDNT_CONNECT_SERVER = 94210;
    public static final int MERR_ASF_NETWORK_CONNECT_TIMEOUT = 94211;
    public static final int MERR_ASF_NETWORK_UNKNOWN_ERROR = 94212;
    int code = -1;

    ErrorInfo() {
    }

    int getCode() {
        return this.code;
    }
}
