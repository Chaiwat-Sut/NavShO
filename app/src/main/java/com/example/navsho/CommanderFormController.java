package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommanderFormController extends AppCompatActivity {
    TextView viewDate, viewVes, viewEditior, viewCommander, viewChief, viewBigEngine, viewElectricEngine, viewUseAirCon, viewUseAirCom, viewUseFreezer, viewUseShipEng, viewUsePump, viewUseHelm, viewUseWaterPure, viewUseOilSplit, viewUseGear, viewGetDiesel, viewGetBensin, viewGetGlanir, viewGetTelus, viewGetWater, viewGiveDiesel, viewGiveBensin, viewGiveGlanir, viewGiveTelus, viewGiveWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_form_controller);
        setView();
        changeData();
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

    public void setView(){
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

    }
    public void acceptClick(View view){

    }
}