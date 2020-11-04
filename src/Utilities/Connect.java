package Utilities;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.SQLException;

public class Connect {
    private static final String SERVER_NAME = "wgudb.ucertify.com", PORT = "3306",
            DB_NAME = "WJ07O6R", USERNAME = "U07O6R", PASSWORD = "53689082284",
            URL = "jdbc:mysql://"+SERVER_NAME+":"+PORT+"/"+DB_NAME;

    private static Connection conn;

    public Connect(){}

    public static void connecting(){
        System.out.println(URL);
        try {


            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(conn);

        }
        catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        }

    }


}
