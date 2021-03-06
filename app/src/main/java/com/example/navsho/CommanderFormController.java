package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.ShipOperation;
import com.example.navsho.pdfcontroller.Common;
import com.example.navsho.pdfcontroller.PdfDocumentAdapter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private Button backButton,printButton;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_form_controller);
        shipOp = (ShipOperation) getIntent().getSerializableExtra("SHIPOP");

        acceptButton = findViewById(R.id.acceptButton);
        rejectButton = findViewById(R.id.rejectButton);
        printButton = findViewById(R.id.printButton);
        backButton = findViewById(R.id.backButton);

        lightDecease1 = findViewById(R.id.lightDecease1);
        acceptPopUpPage = findViewById(R.id.acceptPopUpPage);
        rejectPopUpPage = findViewById(R.id.modifyPopup);
        editTextTextMultiLineRejectReason = findViewById(R.id.editTextTextMultiLineRejectReason);

        setViewID();
        changeData();
        selectNavyName();
        setTextViewFromDatabase();
        if(!shipOp.getStatus().equals("?????? ??????.?????????. ?????????????????????")){
            Log.i("xxx","INSIDE");
            acceptButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
        }

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        printButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDFFile(Common.getAppPath(CommanderFormController.this)+"test_pdf.pdf");
                            }
                        });
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
            document.addAuthor("??????????????? ?????????????????????????????????");
            document.addCreator("??????????????? ?????????????????????????????????");

            //Font Setting
            BaseColor colorAccent = new BaseColor(0,153,20,255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            //Custom font
            BaseFont fontName = BaseFont.createFont("assets/fonts/FC Subject Condensed.ttf","UTF-8",BaseFont.EMBEDDED);


            //Create Title of Document
            Font titleFont = new Font(fontName,36.0f,Font.NORMAL,BaseColor.BLACK);
            addNewItem(document,"Report of Usage And Counsel", Element.ALIGN_CENTER,titleFont);

            //Add more
            Font orderNumberFont = new Font(fontName,fontSize,Font.NORMAL,colorAccent);
            addNewItem(document,"Order No: ",Element.ALIGN_LEFT,orderNumberFont);

            Font orderNumberValuesFont = new Font(fontName,valueFontSize,Font.NORMAL,BaseColor.BLACK);
            addNewItem(document,"#717171 ",Element.ALIGN_LEFT,orderNumberValuesFont);

            addLineSeperator(document);

            addNewItem(document,"Order Date",Element.ALIGN_LEFT,orderNumberFont);
            addNewItem(document,"3/8/2021",Element.ALIGN_LEFT,orderNumberValuesFont);

            addLineSeperator(document);

            addNewItem(document,"Account Name: ",Element.ALIGN_LEFT,orderNumberFont);
            addNewItem(document,"Eddy Lee",Element.ALIGN_LEFT,orderNumberValuesFont);

            //Add Product order detail
            addLineSpace(document);
            addNewItem(document,"Product Detail",Element.ALIGN_CENTER,titleFont);
            addLineSpace(document);

            //Item 1
            addNewItemWithLeftAndRight(document,"Pizza 25","(0.0%)",titleFont,orderNumberFont);

            document.close();

            Toast.makeText(this,"??????????????????????????????????????????",Toast.LENGTH_LONG).show();

            printPDF();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        try{
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(CommanderFormController.this,Common.getAppPath(CommanderFormController.this)+"test_pdf.pdf");
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());
        }catch (Exception ex){
            Log.e("Print Error: ","" + ex.getMessage());
        }
    }

    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftFont,Font textRightFont) throws DocumentException {
        Chunk chunkTextLeft = new Chunk(textLeft,textLeftFont);
        Chunk chunkTextRight = new Chunk(textRight,textRightFont);
        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);
        document.add(p);
    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0,68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chuck = new Chunk(text,font);
        Paragraph paragraph = new Paragraph(chuck);
        paragraph.setAlignment(align);
        document.add(paragraph);

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
                    if(resultSet.getString("Navy_Role").equals("????????????????????????"))
                        viewEditior.setText(resultSet.getString("First_Last_name"));
                    else if(resultSet.getString("Navy_Role").equals("???????????????"))
                        viewChief.setText(resultSet.getString("First_Last_name"));
                    if(resultSet.getString("Navy_Role").equals("??????.?????????."))
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
                    viewBigEngine.setText(resultSet.getInt("BigMachine") + " ??????.");
                    viewElectricEngine.setText(resultSet.getInt("EletricMachine") + " ??????.");
                    viewUseAirCon.setText(resultSet.getInt("AirConditioner") + " ??????.");
                    viewUseAirCom.setText(resultSet.getInt("AirCompressor") + " ??????.");
                    viewUseFreezer.setText(resultSet.getInt("Freezer") + " ??????.");
                    viewUseShipEng.setText(resultSet.getInt("ShipEngine") + " ??????.");
                    viewUsePump.setText(resultSet.getInt("Pump") + " ??????.");
                    viewUseHelm.setText(resultSet.getInt("Rudder") + " ??????.");
                    viewUseWaterPure.setText(resultSet.getInt("WaterPurifyer") + " ??????.");
                    viewUseGear.setText(resultSet.getInt("Gear") + " ??????.");
                    viewUseOilSplit.setText(resultSet.getInt("SplitOilEngine") + " ??????.");
                    viewGetBensin.setText(df.format(resultSet.getFloat("GetOfBensin")) + " ??????.");
                    viewGetWater.setText(df.format(resultSet.getFloat("GetOfWater")) + " ?????????");
                    viewGetDiesel.setText(df.format(resultSet.getFloat("GetOfDiesel")) + " ??????.");
                    viewGetTelus.setText(df.format(resultSet.getFloat("GetOfTeslus")) + " ????????????");
                    viewGetGlanir.setText(df.format(resultSet.getFloat("GetOfGladinir"))+ " ????????????");
                    viewGiveBensin.setText(df.format(resultSet.getFloat("GiveOfBensin")) + " ??????.");
                    viewGiveDiesel.setText(df.format(resultSet.getFloat("GiveDiesel")) + " ??????.");
                    viewGiveGlanir.setText(df.format(resultSet.getFloat("GiveOfGladinir"))+ " ????????????");
                    viewGiveTelus.setText(df.format(resultSet.getFloat("GiveOfTeslus"))+ " ????????????");
                    viewGiveWater.setText(df.format(resultSet.getFloat("GiveOfWater"))+ " ?????????");
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
                        "SET OperationStatus='????????????????????????'" +
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
                            "SET OperationStatus='?????????????????????????????????', Report = '" + editTextTextMultiLineRejectReason.getText().toString() +"', " +
                            "Last_Navy_Role ='??????.?????????.' " +
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
            Toast.makeText(this,"???????????????????????????????????????????????????????????????????????????????????????",Toast.LENGTH_LONG);
        }
    }

}
