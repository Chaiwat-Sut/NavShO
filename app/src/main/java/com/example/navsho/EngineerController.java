package com.example.navsho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.recycleviewadapter.RecycleAdapter;
import com.example.navsho.report.ShipOperation;

import java.util.ArrayList;

public class EngineerController extends AppCompatActivity implements RecycleAdapter.onShipListener{
    private ArrayList<ShipOperation> shipOp;
    private RecyclerView shipOpRecycle;
    private Navy navy = (Navy) getIntent().getSerializableExtra("NAVY");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_controller);
        shipOpRecycle = findViewById(R.id.shipOpRecycle);
        shipOp = new ArrayList<>();

        setShipOp();
        setAdapeter();
    }

    private void setAdapeter() {
        RecycleAdapter adapter = new RecycleAdapter(shipOp,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        shipOpRecycle.setLayoutManager(layoutManager);
        shipOpRecycle.setItemAnimator(new DefaultItemAnimator());
        shipOpRecycle.setAdapter(adapter); 
    }

    public void setShipOp(){
        shipOp.add(new ShipOperation("1/01/2021","กำลังดำเนิน"));
    }

    public void logoutButton(View view){
        Intent intent = new Intent(EngineerController.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClickListener(int position) {
        Log.d("xxx","onClick: "+shipOp.get(position).getDate());
        Intent intent = new Intent(this,EngineerFormController.class);
        intent.putExtra("test", shipOp.get(position));
        startActivity(intent);
    }
}