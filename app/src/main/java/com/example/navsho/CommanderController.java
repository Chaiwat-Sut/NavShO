package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navsho.alluseclass.PatrolVessel;
import com.example.navsho.recycleviewadapter.OperatorRecycleAdapter;
import com.example.navsho.recycleviewadapter.ShipRecycleAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommanderController extends AppCompatActivity implements ShipRecycleAdapter.OnClickPatrolListener{

    private Connection connect;
    private ArrayList<PatrolVessel> vesselList = new ArrayList<>();
    private RecyclerView recyclerViewShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_controller);
        recyclerViewShip = findViewById(R.id.recyclerViewShip);
        readVesselFormDatabase();
        setAdapter();
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

    private void setAdapter() {
        ShipRecycleAdapter operationAdapter = new ShipRecycleAdapter(vesselList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewShip.setLayoutManager(layoutManager);
        recyclerViewShip.setItemAnimator(new DefaultItemAnimator());
        recyclerViewShip.setAdapter(operationAdapter);
    }



    @Override
    public void onClickListener(int position) {

    }
}