package com.example.navsho.alluseclass;

import java.io.Serializable;

public class Navy implements Serializable {
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
}
