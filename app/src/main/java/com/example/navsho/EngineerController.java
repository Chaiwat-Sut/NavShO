    package com.example.navsho;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.recycleviewadapter.OperatorRecycleAdapter;
//import com.example.navsho.recycleviewadapter.ShipRecycleAdapter;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

    public class EngineerController extends AppCompatActivity implements OperatorRecycleAdapter.OnShipListener {
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
        Drawable drawable = getResources().getDrawable(vessel.getPicPath());
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
                String query = "Select v.ves_name,n.NavyID from Vessel v " +
                        "INNER JOIN Navy_Vessel nv " +
                        "ON v.ves_id = nv.ves_id " +
                        "INNER JOIN Navy n " +
                        "ON n.NavyID =  nv.NavyID " +
                        "Where n.NavyID = '" + navy.getNavyID() +"'";
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
        OperatorRecycleAdapter operationAdapter = new OperatorRecycleAdapter(shipOpList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        shipOpRecycle.setLayoutManager(layoutManager);
        shipOpRecycle.setItemAnimator(new DefaultItemAnimator());
        shipOpRecycle.setAdapter(operationAdapter);

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
                String insert = "INSERT INTO ShipOperation (Form_date,ves_id,NavyID_writer,OperationStatus,Last_Navy_Role) " +
                        "VALUES ('"+ date + "','" + navy.getVesID() +"','" + navy.getNavyID() + "','" + shipOp.getStatus() +"'," +
                        "'กำลังดำเนินการ')";
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
            intent.putExtra("VESSEL",vessel);
            startActivity(intent);
        }
        else{
            if(shipOp.getStatus().equals("รอต้นกลตรวจสอบ")){
                Intent intent = new Intent(this,ChiefFormController.class);
                intent.putExtra("SHIPOP", shipOp);
                intent.putExtra("NAVY",navy);
                intent.putExtra("VESSEL",vessel);
                startActivity(intent);
            }
            else if(shipOp.getStatus().equals("รอ ผบ.กตอ. ตรวจสอบ")){
                Intent intent = new Intent(this,ChiefFormController.class);
                intent.putExtra("SHIPOP", shipOp);
                intent.putExtra("NAVY",navy);
                intent.putExtra("VESSEL",vessel);
                startActivity(intent);
            }
            else if(shipOp.getStatus().equals("เสร็จสิน")){
                Intent intent = new Intent(this,ChiefFormController.class);
                intent.putExtra("SHIPOP", shipOp);
                intent.putExtra("NAVY",navy);
                intent.putExtra("VESSEL",vessel);
                startActivity(intent);
            }
        }
    }
}