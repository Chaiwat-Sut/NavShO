package com.example.navsho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.recycleviewadapter.OperatorRecycleAdapter;
import com.example.navsho.recycleviewadapter.ShipRecycleAdapter;
import com.example.navsho.report.ShipOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommanderController extends AppCompatActivity implements ShipRecycleAdapter.OnClickPatrolListener,OperatorRecycleAdapter.OnShipListener{

    private Connection connect;
    private ArrayList<PatrolVessel> vesselList = new ArrayList<>();
    private ArrayList<ShipOperation> shipOpList = new ArrayList<>();
    private ShipOperation shipOp;
    private RecyclerView recyclerViewShip, recycleViewShipOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_controller);
        recyclerViewShip = findViewById(R.id.recyclerViewShip);
        recycleViewShipOp = findViewById(R.id.recycleViewShipOp);
        readVesselFormDatabase();
        setShipAdapter();
    }

    public void readVesselFormDatabase(){
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connectionclass();
        if(connect!=null){
            try {
                String query = "SELECT * from Vessel";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()){
                    String vesName = resultSet.getString("ves_name");
                    int picPath = getResources().getIdentifier
                            (PatrolVessel.findPicPath(
                                    resultSet.getString("ves_name")),"drawable", getPackageName());

                    String vesID = resultSet.getString("ves_id");
                    Log.i("xxx",vesName + " " + picPath + " " + vesID);
                    PatrolVessel patrolVessel = new PatrolVessel(vesName,picPath,vesID);
                    vesselList.add(patrolVessel);
                }
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }
    }

    private void setShipAdapter() {
        ShipRecycleAdapter shipRecycleAdapter = new ShipRecycleAdapter(vesselList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewShip.setLayoutManager(layoutManager);
        recyclerViewShip.setItemAnimator(new DefaultItemAnimator());
        recyclerViewShip.setAdapter(shipRecycleAdapter);
    }

//    private void setShipOpAdapter() {
//        OperatorRecycleAdapter operatorRecycleAdapter = new OperatorRecycleAdapter(shipOpList, On)
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recycleViewShipOp.setLayoutManager(layoutManager);
//        recycleViewShipOp.setItemAnimator(new DefaultItemAnimator());
//        recycleViewShipOp.setAdapter(operatorRecycleAdapter);
//    }


    @Override
    public void onClickListener(int position) {
        PatrolVessel vessel = vesselList.get(position);
        getShipOp(vessel);
    }


    public void getShipOp(PatrolVessel vessel){
        try {
            if (connect != null) {
                String query = "SELECT * from ShipOperation Where ves_id= '"+ vessel.getVesID() +
                        "' ORDER BY Form_date DESC ";                                                                       ;
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<ShipOperation> shipOpBeforeAdd = new ArrayList<>();
                while (resultSet.next()) {
                    String [] dateList = resultSet.getString("Form_date").split("-");
                    int year = Integer.valueOf(dateList[0]);
                    year = year + 543;
                    String date = dateList[2] + "/" + dateList[1] + "/" + year;
                    shipOp = new ShipOperation(date,vessel.getVesID()
                            ,resultSet.getString("OperationStatus"));
                    shipOpBeforeAdd.add(shipOp);
                }
                shipOpList.clear();
                shipOpList.addAll(shipOpBeforeAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recycleViewShipOp.setVisibility(View.VISIBLE);

    }
}