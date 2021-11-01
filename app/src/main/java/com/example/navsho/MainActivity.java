package com.example.navsho;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Navy navy;
    private PatrolVessel vessel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        String vesselID = "";
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect != null){
                String query = "Select * from Navy " +
                               "Where navyID ='" + inputNavyID + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    navyID = resultSet.getString("NavyID");
                    realPassword = resultSet.getString("pass");
                    name = resultSet.getString("First_Last_name");
                    rank = resultSet.getString("Navy_Rank");
                    role = resultSet.getString("Navy_Role");
                }

                query = "SELECT Vessel.ves_id,Vessel.ves_name from Vessel " +
                        "INNER JOIN Navy_Vessel ON Vessel.ves_id = Navy_Vessel.ves_id\n" +
                        "Where navyID = '" + navyID + "'";
                resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    vesselID = resultSet.getString("ves_id");
                    vesselName = resultSet.getString("ves_name");
                }

                if(PatrolVessel.findPicPath(vesselName) != null){
                    int vesselPicPath = getResources().getIdentifier(PatrolVessel.findPicPath(vesselName), "drawable", getPackageName());
                    vessel = new PatrolVessel(realPassword,vesselPicPath,vesselID);
                }
                navy = new Navy(navyID,realPassword,name,rank,role);
                Intent intent;
                if(inputPassword.equals(realPassword) && !inputPassword.isEmpty() && role.equals("ทหารช่าง")){
                    intent = new Intent(MainActivity.this,EngineerController.class);
                    intent.putExtra("NAVY",navy);
                    intent.putExtra("VESSEL",vessel);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !inputPassword.isEmpty() && role.equals("ต้นกล")){
                    intent = new Intent(MainActivity.this,ChiefController.class);
                    intent.putExtra("NAVY",navy);
                    intent.putExtra("VESSEL",vessel);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !inputPassword.isEmpty() && role.equals("ผบ.กตอ.")){
                    intent = new Intent(MainActivity.this,CommanderController.class);
                    intent.putExtra("NAVY",navy);
                    startActivity(intent);
                }
                else{
                    Log.i("xxx",navyID + " " + realPassword);
                    Toast.makeText(this,"รหัสบนบัตรหรือรหัสเรือผิด กรุณากรอกใหม่",Toast.LENGTH_LONG).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this,"" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}