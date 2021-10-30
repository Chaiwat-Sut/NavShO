package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EngineerFormController extends AppCompatActivity {
    private Connection connect;
    private Navy navy;
    private ShipOperation shipOp;
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
    int amountBigMachine,amountElecMachine,amountUseAirCon, amountUseAirCom, amountUseFreez, amountUseShipEng, amountUsePump, amountUsePure, amountUseRudder, amountUseSplitOil, amountUseOfGear ;
    double amountGetWater, amountGetBensin, amountGetGladinir, amountGetTelus, amountGetDiesel, amountGiveWater, amountGiveBensin, amountGiveGladinir, amountGiveTelus, amountGiveDiesel;
    String feedbackString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        navy = (Navy) getIntent().getSerializableExtra("NAVY");
        shipOp = (ShipOperation) getIntent().getSerializableExtra("test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_form_controller);
        dateT = findViewById(R.id.dateTextView);
        dateT.setText(shipOp.getDate());
        vesID = findViewById(R.id.textViewVesselId);
        editorName = findViewById(R.id.textViewEditorName);
        chiefName = findViewById(R.id.textViewChiefName);
        commanderName = findViewById(R.id.textViewCommanderName);
        selectNavyName();
    }

    public void selectNavyName() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "SELECT Vessel.ves_id,Vessel.ves_id,Navy.First_Last_name,Navy.Navy_Role from Vessel \n" +
                        "INNER JOIN Navy ON Vessel.ves_id=Navy.ves_id\n" +
                        "Where Navy.ves_id= " + "'" + navy.getVesID() + "'" + " AND Navy.Navy_Role='ต้นกล'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    vesID.setText(resultSet.getString("ves_id"));
                    editorName.setText(navy.getName());
                    chiefName.setText(resultSet.getString("First_Last_name"));
                    commanderName.setText("จิรัณ ทรัพย์ปรีชา");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onClickSummit(View view) {
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
                else {
                    Toast.makeText(EngineerFormController.this,"connect fail",Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            Toast.makeText(EngineerFormController.this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
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

    public void onClickCancel(View view){
        Intent intent = new Intent(this,EngineerController.class);
        startActivity(intent);
        Toast.makeText(this,"I'm quit",Toast.LENGTH_LONG).show();
    }
}