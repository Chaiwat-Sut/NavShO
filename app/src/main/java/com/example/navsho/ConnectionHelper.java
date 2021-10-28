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
<<<<<<< HEAD
            connectionURL = "jdbc:jtds:sqlserver://saprojectserver.database.windows.net:1433;database=navyDatabase;user=admin123@saprojectserver;password=admin!23;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(connectionURL);
=======
            connectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
            connection = DriverManager.getConnection(connectionURL,username,password);
>>>>>>> db05ad4e219d34be7e7139de80cb98bd9dcafc26
        }
        catch (Exception e){
            Log.i("Error: ",e.getMessage());
        }

        return connection;
    }
}
