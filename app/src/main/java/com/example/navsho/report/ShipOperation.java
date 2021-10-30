package com.example.navsho.report;

import java.io.Serializable;
import java.util.ArrayList;

public class ShipOperation implements Serializable {
    private String date;
    private String status;
    private String vesID;

    public ShipOperation(String date, String vesID,String status) {
        this.date = date;
        this.status = status;
        this.vesID = vesID;
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

    public String getVesID() {
        return vesID;
    }

    public void setVesID(String vesID) {
        this.vesID = vesID;
    }

    public boolean checkIfShiOpExist(ArrayList<ShipOperation> shipOpList){

        for(int i=0;i<shipOpList.size();i++){ // เช็คเฉพาะเดือนกับปีว่าซ้ำใน Table แล้วหรือเปล่า
            String [] shipOpDateList = date.split("/");
            String [] shipOpInArrayList = shipOpList.get(i).getDate().split("/");
            String shipOpDate = shipOpDateList[1] +"/"+ shipOpDateList[2];
            String shipOpDateInArray = shipOpInArrayList[1] +"/"+ shipOpInArrayList[2];
            if(shipOpDate.equals(shipOpDateInArray) && getVesID().equals(shipOpList.get(i).getVesID())){
                return true;
            }
        }
        return false;
    }

    public String convertDateToDatabase(){
        String [] dateList = this.date.split("/");
        int year  = Integer.parseInt(dateList[2]) - 543;
        String dateFormat = year + "-" + dateList[1] + "-" + dateList[0];
        return dateFormat;
    }
}
