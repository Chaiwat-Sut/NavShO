package com.example.navsho;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.navsho.alluseclass.Navy;
import com.example.navsho.alluseclass.PatrolVessel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{


    private Connection connect;
    private String connectionResult = "";
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

    }
    public void loginButton(View view){
        String inputNavyID = usernameEditText.getText().toString();
        String inputPassword = passwordEditText.getText().toString();
        String navyID = "";
        String name = "";
        String realPassword = "";
        String rank = "";
        String role = "";
        String vesselName = "";
        Navy navy;
        PatrolVessel vessel;
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect != null){
                String query = "Select Navy.NavyID,Navy.ves_id,Navy.First_Last_name,Navy.Navy_Rank,Navy.Navy_Role,Vessel.ves_name from Vessel" +
                        " INNER JOIN Navy ON Vessel.ves_id=Navy.ves_id " +
                        "Where NavyID=" + "'" + inputNavyID + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    navyID = resultSet.getString("NavyID");
                    realPassword = resultSet.getString("ves_id");
                    name = resultSet.getString("First_Last_name");
                    rank = resultSet.getString("Navy_Rank");
                    role = resultSet.getString("Navy_Role");
                    vesselName = resultSet.getString("ves_name");
                }

                vessel = new PatrolVessel(realPassword,vesselName);
                vessel.setPicPath(vessel.findPicPath(vesselName));
                navy = new Navy(navyID,realPassword,name,rank,role);
                Intent intent;
                if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ทหารช่าง")){
                    intent = new Intent(MainActivity.this,EngineerController.class);
                    intent.putExtra("NAVY",navy);
                    intent.putExtra("VESSEL",vessel);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ต้นกล")){
                    intent = new Intent(MainActivity.this,ChiefController.class);
                    intent.putExtra("NAVY",navy);
                    intent.putExtra("VESSEL",vessel);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ผบ.กตอ.")){
                    intent = new Intent(MainActivity.this,ChiefController.class);
                    intent.putExtra("NAVY",navy);
                    intent.putExtra("VESSEL",vessel);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this,"รหัสบนบัตรหรือรหัสเรือผิด กรุณากรอกใหม่",Toast.LENGTH_LONG).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this,"" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}