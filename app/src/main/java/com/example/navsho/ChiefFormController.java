package com.example.navsho;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class ChiefFormController extends AppCompatActivity {
    private TextView viewDate, viewVes, viewEditior, viewCommander, viewChief, viewBigEngine, viewElectricEngine, viewUseAirCon, viewUseAirCom, viewUseFreezer, viewUseShipEng, viewUsePump, viewUseHelm, viewUseWaterPure, viewUseOilSplit, viewUseGear, viewGetDiesel, viewGetBensin, viewGetGlanir, viewGetTelus, viewGetWater, viewGiveDiesel, viewGiveBensin, viewGiveGlanir, viewGiveTelus, viewGiveWater;
    private EditText reportEditText;
    private Navy navy;
    private PatrolVessel vessel;
    private ShipOperation shipOp;

    private Connection connect;

    private static final DecimalFormat df = new DecimalFormat("0.000");
    private Button acceptButton,rejectButton;
    private View lightDecease,rejectPopUpPage,acceptPopUpPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_form_controller);
        navy = (Navy) getIntent().getSerializableExtra("NAVY");
        shipOp = (ShipOperation) getIntent().getSerializableExtra("SHIPOP");
        vessel = (PatrolVessel) getIntent().getSerializableExtra("VESSEL");

        lightDecease = findViewById(R.id.lightDecease);
        rejectPopUpPage = findViewById(R.id.rejectPopUpPage);
        acceptPopUpPage = findViewById(R.id.acceptPopUpPage);

        acceptButton = findViewById(R.id.acceptButton);
        rejectButton = findViewById(R.id.rejectButton);

        reportEditText = findViewById(R.id.reportEditText);

        setTextViewID();
        changeData();
        setTextViewFromDatabase();

    }
    public void setTextViewFromDatabase(){
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect!=null){
                String query = "SELECT * from ShipOperation " +
                               "INNER JOIN Navy ON ShipOperation.NavyID_writer=Navy.NavyID " +
                               "Where ShipOperation.Form_date = '" + shipOp.convertDateToDatabase() +"' AND ShipOperation.ves_id='" + shipOp.getVesID() + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    viewDate.setText(shipOp.getDate());
                    viewVes.setText(shipOp.getVesID());
                    viewEditior.setText(resultSet.getString("First_Last_Name"));
                    viewChief.setText(navy.getName());
                    viewCommander.setText("จิรัณ ทรัพย์ปรีชา");
                    viewBigEngine.setText(resultSet.getInt("BigMachine") + " ชม.");
                    viewElectricEngine.setText(resultSet.getInt("EletricMachine") + " ชม.");
                    viewUseAirCon.setText(resultSet.getInt("AirConditioner") + " ชม.");
                    viewUseAirCom.setText(resultSet.getInt("AirCompressor") + " ชม.");
                    viewUseFreezer.setText(resultSet.getInt("Freezer") + " ชม.");
                    viewUseShipEng.setText(resultSet.getInt("ShipEngine") + " ชม.");
                    viewUsePump.setText(resultSet.getInt("Pump") + " ชม.");
                    viewUseHelm.setText(resultSet.getInt("Rudder") + " ชม.");
                    viewUseWaterPure.setText(resultSet.getInt("WaterPurifyer") + " ชม.");
                    viewUseGear.setText(resultSet.getInt("Gear") + " ชม.");
                    viewUseOilSplit.setText(resultSet.getInt("SplitOilEngine") + " ชม.");
                    viewGetBensin.setText(df.format(resultSet.getFloat("GetOfBensin")) + " กล.");
                    viewGetWater.setText(df.format(resultSet.getFloat("GetOfWater")) + " ตัน");
                    viewGetDiesel.setText(df.format(resultSet.getFloat("GetOfDiesel")) + " กล.");
                    viewGetTelus.setText(df.format(resultSet.getFloat("GetOfTeslus")) + " ลิตร");
                    viewGetGlanir.setText(df.format(resultSet.getFloat("GetOfGladinir"))+ " ลิตร");
                    viewGiveBensin.setText(df.format(resultSet.getFloat("GiveOfBensin")) + " กล.");
                    viewGiveDiesel.setText(df.format(resultSet.getFloat("GiveDiesel")) + " กล.");
                    viewGiveGlanir.setText(df.format(resultSet.getFloat("GiveOfGladinir"))+ " ลิตร");
                    viewGiveTelus.setText(df.format(resultSet.getFloat("GiveOfTeslus"))+ " ลิตร");
                    viewGiveWater.setText(df.format(resultSet.getFloat("GiveOfWater"))+ " ตัน");
                }
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
    public void changeData(){
        viewDate.setText("");
        viewVes.setText("");
        viewEditior.setText("");
        viewCommander.setText("");
        viewChief.setText("");
        viewBigEngine.setText("");
        viewElectricEngine.setText("");
        viewUseAirCom.setText("");
        viewUseAirCon.setText("");
        viewUseFreezer.setText("");
        viewUseShipEng.setText("");
        viewUsePump.setText("");
        viewUseHelm.setText("");
        viewUseWaterPure.setText("");
        viewUseGear.setText("");
        viewUseOilSplit.setText("");
        viewGetBensin.setText("");
        viewGetWater.setText("");
        viewGetDiesel.setText("");
        viewGetTelus.setText("");
        viewGetGlanir.setText("");

        viewGiveBensin.setText("");
        viewGiveDiesel.setText("");
        viewGiveGlanir.setText("");
        viewGiveTelus.setText("");
        viewGiveWater.setText("");
    }

    public void setTextViewID(){
        viewDate = findViewById(R.id.textViewDate);
        viewVes = findViewById(R.id.textViewVesselId);
        viewEditior = findViewById(R.id.textViewEditorName);
        viewCommander = findViewById(R.id.textViewCommanderName);
        viewChief = findViewById(R.id.textViewChiefName);
        viewBigEngine = findViewById(R.id.textViewBigEngine);
        viewElectricEngine = findViewById(R.id.textViewElectricEngine);
        viewUseAirCom = findViewById(R.id.textViewAirCompressorTime);
        viewUseAirCon = findViewById(R.id.textViewAirConditionerTime);
        viewUseFreezer = findViewById(R.id.textViewFreezerTime);
        viewUseShipEng = findViewById(R.id.textViewShipEngineTime);
        viewUsePump =  findViewById(R.id.textViewPumpTime5);
        viewUseHelm = findViewById(R.id.textViewHelmTime);
        viewUseWaterPure = findViewById(R.id.textViewWaterPurifyerTime);
        viewUseGear = findViewById(R.id.textViewGearTime);
        viewUseOilSplit = findViewById(R.id.textViewOilSpilterTime);
        viewGetBensin = findViewById(R.id.textViewGetBensin);
        viewGetWater = findViewById(R.id.textViewGetWater);
        viewGetDiesel = findViewById(R.id.textViewGetDesel);
        viewGetTelus = findViewById(R.id.textViewGetTeslus);
        viewGetGlanir = findViewById(R.id.textViewGetGlanir);
        viewGiveBensin = findViewById(R.id.textViewGiveBensin);
        viewGiveDiesel = findViewById(R.id.textViewGiveDessel);
        viewGiveGlanir = findViewById(R.id.textViewGiveGladinir);
        viewGiveTelus = findViewById(R.id.textViewGiveTeslus);
        viewGiveWater = findViewById(R.id.textViewGiveWater);
    }

    public void rejectClick(View view){
        lightDecease.setVisibility(View.VISIBLE);
        rejectPopUpPage.setVisibility(View.VISIBLE);

        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }
    public void acceptClick(View view){
        lightDecease.setVisibility(View.VISIBLE);
        acceptPopUpPage.setVisibility(View.VISIBLE);

        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }

    public void rejectPopUpClick(View view){
        lightDecease.setVisibility(View.GONE);
        acceptPopUpPage.setVisibility(View.GONE);

        acceptButton.setEnabled(true);
        rejectButton.setEnabled(true);
    }

    public void CheckAgainPopUpClick(View view){
        lightDecease.setVisibility(View.GONE);
        rejectPopUpPage.setVisibility(View.GONE);

        acceptButton.setEnabled(true);
        rejectButton.setEnabled(true);
    }

    public void confirmPopUpClick(View view){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                Statement statement = connect.createStatement();
                String query = "UPDATE ShipOperation " +
                        "SET OperationStatus='รอ ผบ.กตอ. ตรวจสอบ'" +
                        "Where Form_date =" + "'" +shipOp.convertDateToDatabase()+"' AND ves_id ='" + shipOp.getVesID() + "'";
                statement.executeUpdate(query);
                Log.i("xxx","UPDATE");
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        Intent intent = new Intent(this,ChiefController.class);
        intent.putExtra("NAVY",navy);
        intent.putExtra("VESSEL",vessel);
        startActivity(intent);
    }


    public void SentBackPopUpClick(View view){
        if(!reportEditText.getText().toString().isEmpty()){
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    Statement statement = connect.createStatement();
                    String query = "UPDATE ShipOperation " +
                            "SET OperationStatus='กลับไปแก้ไข', Report = '" + reportEditText.getText().toString() +"' " +
                            "Where Form_date =" + "'" +shipOp.convertDateToDatabase()+"' AND ves_id ='" + shipOp.getVesID() + "'";
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }
        else{
            Toast.makeText(this,"กรุณาพิมพ์บอกรายการที่จะแก้ไข",Toast.LENGTH_LONG);
        }
    }
}
