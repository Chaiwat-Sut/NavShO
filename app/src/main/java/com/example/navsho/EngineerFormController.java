package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EngineerFormController extends AppCompatActivity {
    private Connection connect;
    private Navy navy;
    private PatrolVessel vessel;
    private ShipOperation shipOp;
    private ImageView modifyImage;
    private TextView dateT;
    private TextView vesID;
    private TextView editorName;
    private TextView chiefName;
    private TextView commanderName;
    private EditText bigMachine;
    private EditText electricMachine;
    private EditText useOfAirCon;
    private EditText useOfAirCom;
    private EditText useOfFreez;
    private EditText useOfShipEng;
    private EditText useOfPump;
    private EditText useOfWaterPure;
    private EditText useOfRudder;
    private EditText useOfSplitOil;
    private EditText useOfGear;
    private EditText getWater;
    private EditText getBensin;
    private EditText getGladinir;
    private EditText getTeslus;
    private EditText getDiesel;
    private EditText giveBensin;
    private EditText giveGladinir;
    private EditText giveTeslus;
    private EditText giveDiesel;
    private EditText giveWater;
    private EditText feedback;
    private Button acceptButton1, clearFormButton;
    private View lightDecease,rejectPopUpPage,acceptPopUpPage;
    private int amountBigMachine,amountElecMachine,amountUseAirCon, amountUseAirCom, amountUseFreez, amountUseShipEng, amountUsePump, amountUsePure, amountUseRudder, amountUseSplitOil, amountUseOfGear ;
    private double amountGetWater, amountGetBensin, amountGetGladinir, amountGetTelus, amountGetDiesel, amountGiveWater, amountGiveBensin, amountGiveGladinir, amountGiveTelus, amountGiveDiesel;
    private String feedbackString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        navy = (Navy) getIntent().getSerializableExtra("NAVY");
        shipOp = (ShipOperation) getIntent().getSerializableExtra("SHIPOP");
        vessel = (PatrolVessel) getIntent().getSerializableExtra("VESSEL") ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_form_controller);
        dateT = findViewById(R.id.dateTextView);
        dateT.setText(shipOp.getDate());
        vesID = findViewById(R.id.textViewVesselId);
        editorName = findViewById(R.id.textViewEditorName);
        chiefName = findViewById(R.id.textViewChiefName);
        commanderName = findViewById(R.id.textViewCommanderName);
        modifyImage = findViewById(R.id.modifyImage);

        lightDecease = findViewById(R.id.lightDecease1);
        acceptPopUpPage = findViewById(R.id.acceptPopUpPage1);

        acceptButton1 = findViewById(R.id.clearFormButton);
        clearFormButton = findViewById(R.id.acceptButton1);

        selectNavyName();
        if(shipOp.getStatus().equals("กลับไปแก้ไข")){
            modifyImage.setVisibility(View.VISIBLE);
            readForm();
        }
    }

    public void selectNavyName() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "Select * from ShipOperation s " +
                        "INNER JOIN Vessel v " +
                        "  ON s.ves_id = v.ves_id " +
                        "INNER JOIN Navy_Vessel nv " +
                        "  ON v.ves_id = nv.ves_id " +
                        "INNER JOIN Navy n " +
                        "  ON n.NavyID =  nv.NavyID " +
                        "Where s.Form_date = '" + shipOp.convertDateToDatabase() + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    vesID.setText(resultSet.getString("ves_id"));
                    if(resultSet.getString("Navy_Role").equals("ทหารช่าง"))
                        editorName.setText(resultSet.getString("First_Last_name"));
                    else if(resultSet.getString("Navy_Role").equals("ต้นกล"))
                        chiefName.setText(resultSet.getString("First_Last_name"));
                    if(resultSet.getString("Navy_Role").equals("ผบ.กตอ."))
                        commanderName.setText(resultSet.getString("First_Last_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readForm() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "SELECT * from ShipOperation " +
                        "Where Form_date ='" +  shipOp.convertDateToDatabase() +"' AND ves_id='" + shipOp.getVesID() +"'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                setEditTextID();
                while(resultSet.next()){
                    bigMachine.setText(String.valueOf(resultSet.getInt("BigMachine")),TextView.BufferType.EDITABLE);
                    electricMachine.setText(String.valueOf(resultSet.getInt("EletricMachine")),TextView.BufferType.EDITABLE);
                    useOfAirCom.setText(String.valueOf(resultSet.getInt("AirCompressor")),TextView.BufferType.EDITABLE);
                    useOfAirCon.setText(String.valueOf(resultSet.getInt("AirConditioner")),TextView.BufferType.EDITABLE);
                    useOfFreez.setText(String.valueOf(resultSet.getInt("Freezer")),TextView.BufferType.EDITABLE);
                    useOfShipEng.setText(String.valueOf(resultSet.getInt("ShipEngine")),TextView.BufferType.EDITABLE);
                    useOfPump.setText(String.valueOf(resultSet.getInt("Pump")),TextView.BufferType.EDITABLE);
                    useOfWaterPure.setText(String.valueOf(resultSet.getInt("WaterPurifyer")),TextView.BufferType.EDITABLE);
                    useOfSplitOil.setText(String.valueOf(resultSet.getInt("SplitOilEngine")),TextView.BufferType.EDITABLE);
                    useOfGear.setText(String.valueOf(resultSet.getInt("Gear")),TextView.BufferType.EDITABLE);
                    useOfRudder.setText(String.valueOf(resultSet.getInt("Rudder")),TextView.BufferType.EDITABLE);
                    getDiesel.setText(String.valueOf(resultSet.getFloat("GetOfDiesel")),TextView.BufferType.EDITABLE);
                    getBensin.setText(String.valueOf(resultSet.getFloat("GetOfBensin")),TextView.BufferType.EDITABLE);
                    getGladinir.setText(String.valueOf(resultSet.getFloat("GetOfGladinir")),TextView.BufferType.EDITABLE);
                    getTeslus.setText(String.valueOf(resultSet.getFloat("GetOfTeslus")),TextView.BufferType.EDITABLE);
                    getWater.setText(String.valueOf(resultSet.getFloat("GetOfWater")),TextView.BufferType.EDITABLE);
                    giveDiesel.setText(String.valueOf(resultSet.getFloat("GiveDiesel")),TextView.BufferType.EDITABLE);
                    giveBensin.setText(String.valueOf(resultSet.getFloat("GiveOfBensin")),TextView.BufferType.EDITABLE);
                    giveGladinir.setText(String.valueOf(resultSet.getFloat("GiveOfGladinir")),TextView.BufferType.EDITABLE);
                    giveTeslus.setText(String.valueOf(resultSet.getFloat("GiveOfTeslus")),TextView.BufferType.EDITABLE);
                    giveWater.setText(String.valueOf(resultSet.getFloat("GiveOfWater")),TextView.BufferType.EDITABLE);
                    feedback.setText(resultSet.getString("Counsel"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEditTextID() {
        bigMachine = findViewById(R.id.editTextBigMachine);
        electricMachine = findViewById(R.id.editTextEletricMachine);
        useOfAirCom = findViewById(R.id.editTextUseOfAirCompressor);
        useOfAirCon = findViewById(R.id.editTextUseOfAirConditioner);
        useOfFreez = findViewById(R.id.editTextUseOfFreezer);
        useOfShipEng = findViewById(R.id.editTextUseOfShipEngine);
        useOfPump = findViewById(R.id.editTextUseOfPump);
        useOfWaterPure = findViewById(R.id.editTextUseOfWaterPurifyer);
        useOfRudder = findViewById(R.id.editTextUseOfRudder);
        useOfSplitOil = findViewById(R.id.editTextUseOfSplitOilEngine);
        useOfGear = findViewById(R.id.editTextUseOfGear);
        getWater = findViewById(R.id.editTextGetOfWater);
        getBensin = findViewById(R.id.editTextGetOfBensin);
        getGladinir = findViewById(R.id.editTextGetOfGladinir);
        getTeslus = findViewById(R.id.editTextGetOfTeslus);
        getDiesel = findViewById(R.id.editTextGetOfDiesel);
        giveBensin = findViewById(R.id.editTextGiveOfBensin);
        giveGladinir = findViewById(R.id.editTextGiveOfGladinir);
        giveTeslus = findViewById(R.id.editTextGiveOfTeslus);
        giveDiesel = findViewById(R.id.editTextGiveDiesel);
        giveWater = findViewById(R.id.editTextGiveOfWater);
        feedback = findViewById(R.id.editTextTextPersonName4);
    }

    public void setValues(){
        amountBigMachine = Integer.parseInt(bigMachine.getText().toString());
        amountElecMachine = Integer.parseInt(electricMachine.getText().toString());
        amountUseAirCom = Integer.parseInt(useOfAirCom.getText().toString());
        amountUseAirCon = Integer.parseInt(useOfAirCon.getText().toString());
        amountUseFreez = Integer.parseInt(useOfFreez.getText().toString());
        amountUseShipEng = Integer.parseInt(useOfShipEng.getText().toString());
        amountUsePump = Integer.parseInt(useOfPump.getText().toString());
        amountUsePure = Integer.parseInt(useOfWaterPure.getText().toString());
        amountUseRudder = Integer.parseInt(useOfRudder.getText().toString());
        amountUseSplitOil = Integer.parseInt(useOfSplitOil.getText().toString());
        amountUseOfGear = Integer.parseInt(useOfGear.getText().toString());
        amountGetWater = Double.parseDouble(getWater.getText().toString());
        amountGetBensin = Double.parseDouble(getBensin.getText().toString());
        amountGetGladinir = Double.parseDouble(getGladinir.getText().toString());
        amountGetTelus = Double.parseDouble(getTeslus.getText().toString());
        amountGetDiesel = Double.parseDouble(getDiesel.getText().toString());
        amountGiveBensin = Double.parseDouble(giveBensin.getText().toString());
        amountGiveGladinir = Double.parseDouble(giveGladinir.getText().toString());
        amountGiveTelus = Double.parseDouble(giveTeslus.getText().toString());
        amountGiveDiesel = Double.parseDouble(giveDiesel.getText().toString());
        amountGiveWater = Double.parseDouble(giveWater.getText().toString());
        feedbackString = feedback.getText().toString();

    }

    public boolean isEditTextEmpty() {
        return (!bigMachine.getText().toString().isEmpty() && !electricMachine.getText().toString().isEmpty()
                && !useOfAirCom.getText().toString().isEmpty() && !useOfAirCon.getText().toString().isEmpty()
                && !useOfFreez.getText().toString().isEmpty() && !useOfShipEng.getText().toString().isEmpty()
                && !useOfPump.getText().toString().isEmpty() && !useOfWaterPure.getText().toString().isEmpty()
                && !useOfRudder.getText().toString().isEmpty() && !useOfSplitOil.getText().toString().isEmpty()
                && !useOfGear.getText().toString().isEmpty() && !getWater.getText().toString().isEmpty()
                && !getBensin.getText().toString().isEmpty() && !getGladinir.getText().toString().isEmpty()
                && !getTeslus.getText().toString().isEmpty() && !getDiesel.getText().toString().isEmpty()
                && !giveBensin.getText().toString().isEmpty() && !giveGladinir.getText().toString().isEmpty()
                && !giveTeslus.getText().toString().isEmpty() && !giveDiesel.getText().toString().isEmpty()
                && !giveWater.getText().toString().isEmpty());
    }

    public void onClickModifyText(View view){

    }

    public void acceptClick(View view){
        lightDecease.setVisibility(View.VISIBLE);
        acceptPopUpPage.setVisibility(View.VISIBLE);

        acceptButton1.setEnabled(false);
        clearFormButton.setEnabled(false);
    }


    public void onClickAcceptPopupButton(View view) {
        setEditTextID();
        if (isEditTextEmpty()) {
            setValues();
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    Statement statement = connect.createStatement();
                    String query = "UPDATE ShipOperation " +
                            "SET BigMachine =" + "'" +amountBigMachine +"'," +
                            "EletricMachine =" + "'" +amountElecMachine +"'," +
                            "AirConditioner =" + "'" +amountUseAirCon +"'," +
                            "AirCompressor ="+ "'" +amountUseAirCom +"'," +
                            "Freezer ="+ "'" +amountUseFreez +"'," +
                            "ShipEngine ="+ "'" +amountUseFreez +"'," +
                            "Pump =" + "'" +amountUsePump +"'," +
                            "Rudder ="  + "'" +amountUseRudder +"'," +
                            "WaterPurifyer =" + "'" +amountUsePure +"'," +
                            "SplitOilEngine =" + "'" +amountUseSplitOil +"'," +
                            "Gear =" + "'" +amountUseOfGear +"'," +
                            "GetOfDiesel =" + "'" +amountGetDiesel +"'," +
                            "GetOfBensin =" + "'" +amountGetBensin +"'," +
                            "GetOfGladinir =" + "'" +amountGetGladinir +"'," +
                            "GetOfTeslus =" + "'" +amountGetTelus +"'," +
                            "GetOfWater =" + "'" +amountGetWater +"'," +
                            "GiveDiesel =" + "'" +amountGiveDiesel +"'," +
                            "GiveOfBensin ="+ "'" +amountGiveBensin +"'," +
                            "GiveOfGladinir ="+ "'" +amountGiveGladinir +"'," +
                            "GiveOfTeslus ="+ "'" +amountGetTelus +"'," +
                            "GiveOfWater =" + "'" +amountGetTelus +"'," +
                            "OperationStatus ='รอต้นกลตรวจสอบ'," +
                            "Counsel =" + "'" +feedbackString +"'"
                            + " Where Form_date =" + "'" +shipOp.convertDateToDatabase()+"'";
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                e.getErrorCode();
            }
            Toast.makeText(EngineerFormController.this,"ส่งข้อมูลแล้ว",Toast.LENGTH_SHORT).show();
        }
        else {
            lightDecease.setVisibility(View.GONE);
            acceptPopUpPage.setVisibility(View.GONE);

            acceptButton1.setEnabled(true);
            clearFormButton.setEnabled(true);
            Toast.makeText(EngineerFormController.this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickRejectPopupButton(View view){
        lightDecease.setVisibility(View.GONE);
        acceptPopUpPage.setVisibility(View.GONE);

        acceptButton1.setEnabled(true);
        clearFormButton.setEnabled(true);
    }

    public void onClickRejectPopUpButton(View view){
        Intent intent = new Intent(this,EngineerController.class);
        intent.putExtra("NAVY",navy);
        intent.putExtra("SHIPOP",shipOp);
        intent.putExtra("VESSEL",vessel);
        startActivity(intent);
    }

}