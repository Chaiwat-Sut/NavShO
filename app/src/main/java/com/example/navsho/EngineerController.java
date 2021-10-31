    package com.example.navsho;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.recycleviewadapter.RecycleAdapter;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

    public class EngineerController extends AppCompatActivity implements RecycleAdapter.onShipListener{
    private ArrayList<ShipOperation> shipOpList;
    private RecyclerView shipOpRecycle;
    private Intent intent = getIntent();
    private Connection connect;

    private ShipOperation shipOp;
    private Navy navy;
    private PatrolVessel vessel;

    private String date;

    private TextView statusText;
    private TextView viewMonthlyForm;

    private ImageView pictureEngineer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_controller);

        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connectionclass();

        shipOpRecycle = findViewById(R.id.shipOpRecycle);
        viewMonthlyForm = findViewById(R.id.textViewMonthlyForm);
        statusText = findViewById(R.id.statusText);
        pictureEngineer = findViewById(R.id.pictureEngineerView);

        shipOpList = new ArrayList<>();
        navy = (Navy) getIntent().getSerializableExtra("NAVY");
        vessel = (PatrolVessel) getIntent().getSerializableExtra("VESSEL");
        getShipName();
        getShipOp();
        setAdapter();
        setShipImage();

        date = getCurrentDate();

    }

    private void setShipImage() {
        int id = getResources().getIdentifier(vessel.getPicPath(), "drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(id);
        pictureEngineer.setImageDrawable(drawable);
    }

    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String [] dateList = formattedDate.split("-");
        int year = Integer.valueOf(dateList[0]);
        year = year + 543;
        String date = dateList[2] + "/" + dateList[1] + "/" + year;
        return date;
    }

    public void getShipName() {
        try {
            if (connect != null) {
                String query = "SELECT * from Vessel \n" +
                        "INNER JOIN Navy ON Vessel.ves_id=Navy.ves_id\n" +
                        "Where Navy.ves_id= " + "'" + navy.getVesID() + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    viewMonthlyForm.setText("แบบฟอร์มรายเดือนของ " + resultSet.getString("ves_name"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setAdapter() {
        RecycleAdapter adapter = new RecycleAdapter(shipOpList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        shipOpRecycle.setLayoutManager(layoutManager);
        shipOpRecycle.setItemAnimator(new DefaultItemAnimator());
        shipOpRecycle.setAdapter(adapter); 
    }

    public void getShipOp(){
        try {
            if (connect != null) {
                String query = "SELECT * from ShipOperation Where ves_id= '"+ navy.getVesID() +
                        "' ORDER BY Form_date DESC ";                                                                       ;
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<ShipOperation> shipOpBeforeAdd = new ArrayList<>();
                while (resultSet.next()) {
                    String [] dateList = resultSet.getString("Form_date").split("-");
                    int year = Integer.valueOf(dateList[0]);
                    year = year + 543;
                    String date = dateList[2] + "/" + dateList[1] + "/" + year;
                    shipOp = new ShipOperation(date,navy.getVesID()
                            ,resultSet.getString("OperationStatus"));
                    shipOpBeforeAdd.add(shipOp);
                }
                shipOpList.clear();
                shipOpList.addAll(shipOpBeforeAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onClickCreateShipOpByMonth(View view){
        shipOp = new ShipOperation(date,navy.getVesID(),"กำลังดำเนินการ");
        if(!shipOp.checkIfShiOpExist(shipOpList)){
            // เปลี่ยนปี พ.ศ เป็น ค.ศ. และเปลี่ยน format วันที่
            Log.i("xxx","notExist");
            date = shipOp.convertDateToDatabase();
            // เอาขึ้น database
            if(connect!=null){
                String insert = "INSERT INTO ShipOperation (Form_date,ves_id,NavyID_writer,OperationStatus) " +
                        "VALUES ('"+ date + "','" + navy.getVesID() +"','" + navy.getNavyID() + "','" + shipOp.getStatus() +"')";
                Statement statement = null;
                try {
                    statement = connect.createStatement();
                    statement.executeUpdate(insert);
                } catch (SQLException e) {
                    e.getErrorCode();
                }
            }
            getShipOp();
            setAdapter();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(this,"มีฟอร์มของเดือนนี้อยู่แล้ว\nกรุณาเลือกจากในตาราง",Toast.LENGTH_LONG).show();
        }
    }


    public void logoutButton(View view){
        Intent intent = new Intent(EngineerController.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClickListener(int position) {
        shipOp = shipOpList.get(position);
        if(shipOp.getStatus().equals("กำลังดำเนินการ") || shipOp.getStatus().equals("กลับไปแก้ไข")){
            Intent intent = new Intent(this,EngineerFormController.class);
            intent.putExtra("SHIPOP", shipOp);
            intent.putExtra("NAVY",navy);
            startActivity(intent);
        }
        else{
            if(shipOp.getStatus().equals("รอต้นกลตรวจสอบ")){
                Toast.makeText(this,"กรุณารอการตรวจสอบจากต้นกล",Toast.LENGTH_LONG).show();
            }
            else if(shipOp.getStatus().equals("รอ ผบ.กตอ. ตรวจสอบ")){
                Toast.makeText(this,"กรุณารอการตรวจสอบจาก ผบ.กตอ.",Toast.LENGTH_LONG).show();
            }
            else if(shipOp.getStatus().equals("เสร็จสิน")){
                Toast.makeText(this,"ส่งแบบฟอร์มเสร็จสิ้นแล้ว",Toast.LENGTH_LONG).show();
            }
        }
    }
}