package com.miniai.face;

public class Face3DAngle {
    float yaw;
    float roll;
    float pitch;
    int status;

    public Face3DAngle() {
        this.yaw = 0.0F;
        this.roll = 0.0F;
        this.pitch = 0.0F;
        this.status = 1;
    }

    public Face3DAngle(Face3DAngle obj) {
        if (obj == null) {
            this.yaw = 0.0F;
            this.roll = 0.0F;
            this.pitch = 0.0F;
            this.status = 1;
        } else {
            this.yaw = obj.getYaw();
            this.roll = obj.getRoll();
            this.pitch = obj.getPitch();
            this.status = obj.getStatus();
        }

    }

    public float getYaw() {
        return this.yaw;
    }

    public float getRoll() {
        return this.roll;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getStatus() {
        return this.status;
    }

    public Face3DAngle clone() {
        return new Face3DAngle(this);
    }

    public String toString() {
        return "Face3DAngle{yaw=" + this.yaw + ", roll=" + this.roll + ", pitch=" + this.pitch + ", status=" + this.status + '}';
    }
}