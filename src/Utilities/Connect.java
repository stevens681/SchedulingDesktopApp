package Utilities;

import java.sql.*;
import java.util.Locale;

/**
 * Connect class
 * @author Fernando Rosa
 * */
public class Connect {

    private static final String SERVER_NAME = "wgudb.ucertify.com", PORT = "3306",
            DB_NAME = "WJ07O6R", USERNAME = "U07O6R", PASSWORD = "53689082284",
            URL = "jdbc:mysql://"+SERVER_NAME+":"+PORT+"/"+DB_NAME;

    private static Connection dataDB;

    public Connect(){}

    public static void connecting(){

        try {

            dataDB = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e){

            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public static Connection sendData(){
        return dataDB;
    }

    public static String getCountry(){

        Locale locale = Locale.getDefault();
        String location;
        switch (locale.toString()) {
            case "en_US" -> location = "United States";
            case "en_CA", "fr_CA" -> location = "Canada";
            case "en_UK" -> location = "United Kingdom";
            default -> location = "";
        }
        return location;
    }

}
