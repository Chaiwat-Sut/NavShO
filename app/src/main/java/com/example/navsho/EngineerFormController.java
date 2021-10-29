package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.report.ShipOperation;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EngineerFormController extends AppCompatActivity {
    private Connection connect;
    private Navy navy;
    ShipOperation ship;
    TextView dateT;
    TextView vesID;
    TextView editorName;
    TextView chiefName;
    TextView commanderName;
    EditText bigMachine;
    EditText electricMachine;
    EditText useOfAirCon;
    EditText useOfAirCom;
    EditText useOfFreez;
    EditText useOfShipEng;
    EditText useOfPump;
    EditText useOfWaterPure;
    EditText useOfRudder;
    EditText useOfSplitOil;
    EditText useOfGear;
    EditText getWater;
    EditText getBensin;
    EditText getGladinir;
    EditText getTeslus;
    EditText getDiesel;
    EditText giveBensin;
    EditText giveGladinir;
    EditText giveTeslus;
    EditText giveDiesel;
    EditText giveWater;
    EditText feedback;
    int amountBigMachine1, amountBigMachine2, amountBigMachine3, amountElecMachine1, amountElecMachine2, amountElecMachine3, amountElecMachine4, amountUseAirCon, amountUseAirCom, amountUseFreez, amountUseShipEng, amountUsePump, amountUsePure, amountUseRudder, amountUseSplitOil, amountUseOfGear ;
    double amountGetWater, amountGetBensin, amountGetGladinir, amountGetTelus, amountGetDiesel, amountGiveWater, amountGiveBensin, amountGiveGladinir, amountGiveTelus, amountGiveDiesel;
    String feedbackString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Serializable extras = getIntent().getSerializableExtra("test");
        navy = (Navy) getIntent().getSerializableExtra("NAVY");
        ship = (ShipOperation) extras;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_form_controller);
        dateT = findViewById(R.id.dateTextView);
        dateT.setText(ship.getDate());
        vesID = findViewById(R.id.textViewVesselId);
        editorName = findViewById(R.id.textViewEditorName);
        chiefName = findViewById(R.id.textViewChiefName);
        commanderName = findViewById(R.id.textViewCommanderName);
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "SELECT * from Vessel \n" +
                        "INNER JOIN Navy ON Vessel.ves_id=Navy.ves_id\n" +
                        "Where Navy.ves_id= " + "'" + navy.getVesID() + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void onClickSummit(View view) {
        bigMachine = findViewById(R.id.editTextBigMachine);
        amountBigMachine1 = Integer.parseInt(bigMachine.getText().toString());
        electricMachine = findViewById(R.id.editTextEletricMachine);
        amountElecMachine1 = Integer.parseInt(electricMachine.getText().toString());
        useOfAirCom = findViewById(R.id.editTextUseOfAirCompressor);
        amountUseAirCom = Integer.parseInt(useOfAirCom.getText().toString());
        useOfAirCon = findViewById(R.id.editTextUseOfAirConditioner);
        amountUseAirCon = Integer.parseInt(useOfAirCon.getText().toString());
        useOfFreez = findViewById(R.id.editTextUseOfFreezer);
        amountUseFreez = Integer.parseInt(useOfFreez.getText().toString());
        useOfShipEng = findViewById(R.id.editTextUseOfShipEngine);
        amountUseShipEng = Integer.parseInt(useOfShipEng.getText().toString());
        useOfPump = findViewById(R.id.editTextUseOfPump);
        amountUsePump = Integer.parseInt(useOfPump.getText().toString());
        useOfWaterPure = findViewById(R.id.editTextUseOfWaterPurifyer);
        amountUsePure = Integer.parseInt(useOfWaterPure.getText().toString());
        useOfRudder = findViewById(R.id.editTextUseOfRudder);
        amountUseRudder = Integer.parseInt(useOfRudder.getText().toString());
        useOfSplitOil = findViewById(R.id.editTextUseOfSplitOilEngine);
        amountUseSplitOil = Integer.parseInt(useOfSplitOil.getText().toString());
        useOfGear = findViewById(R.id.editTextUseOfGear);
        amountUseOfGear = Integer.parseInt(useOfGear.getText().toString());
        getWater = findViewById(R.id.editTextGetOfWater);
        amountGetWater = Double.parseDouble(getWater.getText().toString());
        getBensin = findViewById(R.id.editTextGetOfBensin);
        amountGetBensin = Double.parseDouble(getBensin.getText().toString());
        getGladinir = findViewById(R.id.editTextGetOfGladinir);
        amountGetGladinir = Double.parseDouble(getGladinir.getText().toString());
        getTeslus = findViewById(R.id.editTextGetOfTeslus);
        amountGetTelus = Double.parseDouble(getTeslus.getText().toString());
        getDiesel = findViewById(R.id.editTextGetOfDiesel);
        amountGetDiesel = Double.parseDouble(getDiesel.getText().toString());
        giveBensin = findViewById(R.id.editTextGiveOfBensin);
        amountGiveBensin = Double.parseDouble(giveBensin.getText().toString());
        giveGladinir = findViewById(R.id.editTextGiveOfGladinir);
        amountGiveGladinir = Double.parseDouble(giveGladinir.getText().toString());
        giveTeslus = findViewById(R.id.editTextGiveOfTeslus);
        amountGiveTelus = Double.parseDouble(giveTeslus.getText().toString());
        giveDiesel = findViewById(R.id.editTextGiveDiesel);
        amountGiveDiesel = Double.parseDouble(giveDiesel.getText().toString());
        giveWater = findViewById(R.id.editTextGiveOfWater);
        amountGiveWater = Double.parseDouble(giveWater.getText().toString());
        feedback = findViewById(R.id.editTextTextPersonName4);
        feedbackString = feedback.getText().toString();
        ship.setStatus("ส่งให้ต้นกลตรวจสอบ");
//        try {
//            ConnectionHelper connectionHelper = new ConnectionHelper();
//            connect = connectionHelper.connectionclass();
//            if (connect != null) {
//                String query = "INSERT INTO ShipOperation Where ves_id = " + ;
//                Statement statement = connect.createStatement();
//                ResultSet resultSet = statement.executeQuery(query);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void onClickCancel(View view){
        Intent intent = new Intent(this,EngineerController.class);
        startActivity(intent);
        Toast.makeText(this,"I'm quit",Toast.LENGTH_LONG).show();
    }
}