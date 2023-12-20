package com.miniai.face;

public class VersionInfo {
    private String version = null;
    private String buildDate = null;
    private String copyRight = null;

    public VersionInfo() {
    }

    public String getVersion() {
        return this.version;
    }

    public String getBuildDate() {
        return this.buildDate;
    }

    public String getCopyRight() {
        return this.copyRight;
    }

    public String toString() {
        return "VersionInfo{version='" + this.version + '\'' + ", buildDate='" + this.buildDate + '\'' + ", copyRight='" + this.copyRight + '\'' + '}';
    }
}
