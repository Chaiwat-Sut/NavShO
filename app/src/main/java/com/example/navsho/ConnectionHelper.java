package com.example.navsho;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

public class ConnectionHelper {
    Connection con;
    String username,password,ip,port,database;

    public Connection connectionclass(){
        ip = "192.168.1.36";
        username = "test";
        password = "test";
        port = "1433";
        database = "navyDataBase";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+
                    database+";user="+username+";password="+password+";";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (Exception e){
            Log.i("Error: ",e.getMessage());
        }

        return connection;
    }
}
