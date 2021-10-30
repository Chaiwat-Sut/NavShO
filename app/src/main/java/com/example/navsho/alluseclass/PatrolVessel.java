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

    public String findPicPath(String name){
        String path = " ";
        if(name.equals("ร.ล.สัตหีบ")){
            path = "sattahip";
        }else if(name.equals("ร.ล.คลองใหญ่")){
            path = "klongyai";
        }else if(name.equals("ร.ล.ตากใบ")){
            path = "takbai";
        }else if(name.equals("ร.ล.กันตัง")){
            path = "guntun";
        }else if(name.equals("ร.ล.เทพา")){
            path = "lnwpa";
        }else if(name.equals("ร.ล.ท้ายเหมือง")){
            path = "taimueng";
        }else {
            path = null;
        }
        return path;
    }
}
