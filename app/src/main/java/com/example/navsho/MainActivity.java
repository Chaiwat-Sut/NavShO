package com.example.navsho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity{
    private Connection connect;
    private String connectionResult = "";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Navy navy;

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
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect != null){
                String query = "Select * from Navy_TABLE Where NavyID=" + "'" + inputNavyID + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    navyID = resultSet.getString(1);
                    name = resultSet.getString(2);
                    realPassword = resultSet.getString(3);
                    rank = resultSet.getString(4);
                    role = resultSet.getString(5);
                    Log.i("xxx","" + realPassword);
                }
                if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ทหารช่าง")){
                    navy = new Navy(navyID,name,realPassword,rank,role);
                    Intent intent = new Intent(MainActivity.this,EngineerController.class);
                    intent.putExtra("NAVY",navy);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ต้นกล")){
                    navy = new Navy(navyID,name,realPassword,rank,role);
                    Intent intent = new Intent(MainActivity.this,ChiefController.class);
                    intent.putExtra("NAVY",navy);
                    startActivity(intent);
                }
                else if(inputPassword.equals(realPassword) && !realPassword.equals("") && role.equals("ผบ.กตอ.")){
                    navy = new Navy(navyID,name,realPassword,rank,role);
                    Intent intent = new Intent(MainActivity.this,ChiefController.class);
                    intent.putExtra("NAVY",navy);
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