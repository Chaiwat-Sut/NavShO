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


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://saprojectserver.database.windows.net:1433;database=navyDatabase;user=admin123@saprojectserver;password=admin!23;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (Exception e){
            Log.i("Error: ",e.getMessage());
        }

        return connection;
    }
}
