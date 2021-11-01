package com.example.navsho.alluseclass;

import java.io.Serializable;

public class PatrolVessel implements Serializable {
    private String vesName;
    private String vesID;
    private int picPath;

    public PatrolVessel(String vesName, int picture,String vesID) {
        this.vesName = vesName;
        this.picPath = picture;
        this.vesID = vesID;
    }

    public String getVesName() {
        return vesName;
    }

    public void setVesName(String vesName) {
        this.vesName = vesName;
    }

    public int getPicPath() {
        return picPath;
    }

    public void setPicPath(int picPath) {
        this.picPath = picPath;
    }

    public String getVesID() {
        return vesID;
    }

    public void setVesID(String vesID) {
        this.vesID = vesID;
    }

    public static String findPicPath(String name){
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
