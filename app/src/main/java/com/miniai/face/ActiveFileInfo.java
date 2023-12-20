package com.miniai.face;

public class ActiveFileInfo {
    private String appId;
    private String sdkKey;
    private String platform;
    private String sdkType;
    private String sdkVersion;
    private String fileVersion;
    private String startTime;
    private String endTime;

    public ActiveFileInfo() {
    }

    public String getAppId() {
        return this.appId;
    }

    public String getSdkKey() {
        return this.sdkKey;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getSdkType() {
        return this.sdkType;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public String getFileVersion() {
        return this.fileVersion;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String toString() {
        return this.appId + ',' + this.sdkKey + ',' + this.platform + ',' + this.sdkType + ',' + this.sdkVersion + ',' + this.fileVersion + ',' + this.startTime + ',' + this.endTime;
    }
}
