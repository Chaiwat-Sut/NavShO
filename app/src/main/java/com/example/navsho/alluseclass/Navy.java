package com.example.navsho.alluseclass;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Navy implements Parcelable {
    private String navyID;
    private String name;
    private String password;
    private String rank;
    private String role;

    public Navy(String navyID, String name,String password, String rank, String role) {
        this.navyID = navyID;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.role = role;
    }

    protected Navy(Parcel in) {
        navyID = in.readString();
        name = in.readString();
        password = in.readString();
        rank = in.readString();
        role = in.readString();
    }

    public static final Creator<Navy> CREATOR = new Creator<Navy>() {
        @Override
        public Navy createFromParcel(Parcel in) {
            return new Navy(in);
        }

        @Override
        public Navy[] newArray(int size) {
            return new Navy[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(navyID);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(rank);
        dest.writeString(role);
    }
}
