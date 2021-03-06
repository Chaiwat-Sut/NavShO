package com.example.navsho;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String username,password,ip,port,database;

    public Connection connectionclass(){
        ip = "192.168.1.43";
        username = "test";
        password = "test";
        port = "1433";
        database = "navyDatabase";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL ;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
            connection = DriverManager.getConnection(connectionURL,username,password);
        }
        catch (Exception e){
            Log.i("Error: ",e.getMessage());
        }

        return connection;
    }
}
