package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.recycleviewadapter.OperatorRecycleAdapter;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChiefController extends AppCompatActivity implements OperatorRecycleAdapter.OnShipListener {
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
        setContentView(R.layout.activity_chief_controller);

        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connectionclass();

        shipOpRecycle = findViewById(R.id.recyclerViewShip);
        viewMonthlyForm = findViewById(R.id.textViewMonthlyForm2);
        pictureEngineer = findViewById(R.id.pictureChiefView);

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
                shipOpList = shipOpBeforeAdd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        OperatorRecycleAdapter adapter = new OperatorRecycleAdapter(shipOpList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        shipOpRecycle.setLayoutManager(layoutManager);
        shipOpRecycle.setItemAnimator(new DefaultItemAnimator());
        shipOpRecycle.setAdapter(adapter);
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

    @Override
    public void onClickListener(int position) {
        shipOp = shipOpList.get(position);
        if(shipOp.getStatus().equals("กำลังดำเนินการ")) {
            Toast.makeText(this, "กรุณารอให้ทหารช่างทำรายการ", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, ChiefFormController.class);
            intent.putExtra("SHIPOP", shipOp);
            intent.putExtra("NAVY", navy);
            intent.putExtra("VESSEL", vessel);
            startActivity(intent);
        }
    }

    public void logoutButton(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}