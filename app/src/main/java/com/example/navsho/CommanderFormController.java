package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.ShipOperation;
import com.example.navsho.pdfcontroller.Common;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class CommanderFormController extends AppCompatActivity {
    private TextView viewDate, viewVes, viewEditior, viewCommander, viewChief, viewBigEngine, viewElectricEngine, viewUseAirCon, viewUseAirCom, viewUseFreezer, viewUseShipEng, viewUsePump, viewUseHelm, viewUseWaterPure, viewUseOilSplit, viewUseGear, viewGetDiesel, viewGetBensin, viewGetGlanir, viewGetTelus, viewGetWater, viewGiveDiesel, viewGiveBensin, viewGiveGlanir, viewGiveTelus, viewGiveWater;
    private EditText editTextTextMultiLineRejectReason;
    private Connection connect;
    private ShipOperation shipOp;
    private View lightDecease1,acceptPopUpPage,rejectPopUpPage;
    private Button acceptButton,rejectButton;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_form_controller);
        shipOp = (ShipOperation) getIntent().getSerializableExtra("SHIPOP");

        acceptButton = findViewById(R.id.acceptButton);
        rejectButton = findViewById(R.id.rejectButton);

        lightDecease1 = findViewById(R.id.lightDecease1);
        acceptPopUpPage = findViewById(R.id.acceptPopUpPage);
        rejectPopUpPage = findViewById(R.id.modifyPopup);
        editTextTextMultiLineRejectReason = findViewById(R.id.editTextTextMultiLineRejectReason);

        setViewID();
        changeData();
        selectNavyName();
        setTextViewFromDatabase();
        if(!shipOp.getStatus().equals("รอ ผบ.กตอ. ตรวจสอบ")){
            Log.i("xxx","INSIDE");
            acceptButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
        }

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        createPDFFile(Common.getAppPath(CommanderFormController.this)+"รายงาน_" + shipOp.getVesID() + "_" + shipOp.getDate());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    private void createPDFFile(String path) {
        if(new File(path).exists()){
            new File(path).delete();
        }
        try{
            Document document = new Document();
            //Save
            PdfWriter.getInstance(document,new FileOutputStream(path));
            //Open to write
            document.open();

            // Setting
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("จิรัณ ทรัพย์ปรีชา");
            document.addCreator("จิรัณ ทรัพย์ปรีชา");

            //Font Setting
            BaseColor colorAccent = new BaseColor(0,153,20,255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            //Custom font
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8",BaseFont.EMBEDDED);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
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

    public void setViewID(){
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
                    viewVes.setText(resultSet.getString("ves_id"));
                    if(resultSet.getString("Navy_Role").equals("ทหารช่าง"))
                        viewEditior.setText(resultSet.getString("First_Last_name"));
                    else if(resultSet.getString("Navy_Role").equals("ต้นกล"))
                        viewChief.setText(resultSet.getString("First_Last_name"));
                    if(resultSet.getString("Navy_Role").equals("ผบ.กตอ."))
                        viewCommander.setText(resultSet.getString("First_Last_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTextViewFromDatabase(){
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect!=null){
                String query = "Select * from ShipOperation s " +
                        "INNER JOIN Vessel v " +
                        " ON s.ves_id = v.ves_id " +
                        "INNER JOIN Navy_Vessel nv " +
                        " ON v.ves_id = nv.ves_id " +
                        "INNER JOIN Navy n " +
                        " ON n.NavyID =  nv.NavyID " +
                        "Where v.ves_id = '" + shipOp.getVesID() + "' AND s.Form_date ='" + shipOp.convertDateToDatabase() + "'" ;
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    viewDate.setText(shipOp.getDate());
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

    public void rejectClick(View view){
        lightDecease1.setVisibility(View.VISIBLE);
        rejectPopUpPage.setVisibility(View.VISIBLE);

        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }
    public void acceptClick(View view){
        lightDecease1.setVisibility(View.VISIBLE);
        acceptPopUpPage.setVisibility(View.VISIBLE);

        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }

    public void rejectPopUpClick(View view){
        lightDecease1.setVisibility(View.GONE);
        acceptPopUpPage.setVisibility(View.GONE);

        acceptButton.setEnabled(true);
        rejectButton.setEnabled(true);
    }

    public void CheckAgainPopUpClick(View view){
        lightDecease1.setVisibility(View.GONE);
        rejectPopUpPage.setVisibility(View.GONE);

        acceptButton.setEnabled(true);
        rejectButton.setEnabled(true);
    }
    public void comfirmPopUpClick(View view){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                Statement statement = connect.createStatement();
                String query = "UPDATE ShipOperation " +
                        "SET OperationStatus='เสร็จสิน'" +
                        "Where Form_date ='" +shipOp.convertDateToDatabase()+"' AND ves_id ='" + shipOp.getVesID() + "'";
                statement.executeUpdate(query);
                Intent intent = new Intent(this,CommanderController.class);
                startActivity(intent);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
    public void SentBackPopUpClick(View view){
        if(!editTextTextMultiLineRejectReason.getText().toString().isEmpty()){
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    Statement statement = connect.createStatement();
                    String query = "UPDATE ShipOperation " +
                            "SET OperationStatus='กลับไปแก้ไข', Report = '" + editTextTextMultiLineRejectReason.getText().toString() +"', " +
                            "Last_Navy_Role ='ผบ.กตอ.' " +
                            "Where Form_date =" + "'" +shipOp.convertDateToDatabase()+"' AND ves_id ='" + shipOp.getVesID() + "'";
                    statement.executeUpdate(query);
                    Intent intent = new Intent(this,CommanderController.class);
                    startActivity(intent);
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
