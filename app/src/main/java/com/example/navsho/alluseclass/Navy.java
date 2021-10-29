package com.example.navsho.alluseclass;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Navy implements Serializable {
    private String navyID;
    private String name;
    private String vesID;
    private String rank;
    private String role;

    public Navy(String navyID, String vesID, String name, String rank, String role) {
        this.navyID = navyID;
        this.name = name;
        this.vesID = vesID;
        this.rank = rank;
        this.role = role;
    }

    protected Navy(Parcel in) {
        navyID = in.readString();
        name = in.readString();
        vesID = in.readString();
        rank = in.readString();
        role = in.readString();
    }


    public String getNavyID() {
        return navyID;
    }

    public void setNavyID(String navyID) {
        this.navyID = navyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVesID() {
        return vesID;
    }

    public void setVesID(String vesID) {
        this.vesID = vesID;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
