package com.example.navsho.report;

import java.io.Serializable;
import java.util.ArrayList;

public class ShipOperation implements Serializable {
    private String date;
    private String status;

    public ShipOperation(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean checkIfShiOpExist(ArrayList<ShipOperation> shipOpList){
        boolean check = false;
        for(int i=0;i<shipOpList.size();i++){ // เช็คเฉพาะเดือนกับปีว่าซ้ำใน Table แล้วหรือเปล่า
            String [] dateListThis = this.getDate().split("/");
            String [] dateListThat = shipOpList.get(i).getDate().split("/");
            String dateThis = dateListThis[1] + dateListThis[2];
            String dateThat = dateListThat[1] + dateListThat[2];
            check = dateThis.equals(dateThat);
            if(check == true) break;
        }
        return check;
    }

    public String convertDateToDatabase(){
        String [] dateList = this.date.split("/");
        int year  = Integer.parseInt(dateList[2]) - 543;
        String dateFormat = year + "-" + dateList[1] + "-" + dateList[0];
        return dateFormat;
    }
}
