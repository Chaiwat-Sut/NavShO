package com.example.navsho.alluseclass;

import java.io.Serializable;

public class PatrolVessel implements Serializable {
    private String vesID;
    private String picPath;

    public PatrolVessel(String vesID, String picture) {
        this.vesID = vesID;
        this.picPath = picture;
    }

    public String getVesID() {
        return vesID;
    }

    public void setVesID(String vesID) {
        this.vesID = vesID;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
