package Utilities;

import java.sql.*;
import java.util.Locale;


public class Connect {

    private static String location;
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
        switch (locale.toString()){
            case "US":
                location = "United States";
                break;
            case "CA":
                location = "Canada";
                break;
            case "UK":
                location = "United Kingdom";
                break;
            default:
                location = "";
                break;
        }
        return location;
    }

//    public static void check(){
//        String t = "test", kl = "";
//        try{
//            Statement d = sendData().createStatement();
//            String q = "SELECT User_Name, Password FROM users";
//            ResultSet r = d.executeQuery(q);
//
//
//
//            while (r.next())
//              //System.out.println("This is pass "+r.getString("User_Name"));
//                //kl = r.getString("User_ID");
////                t = r.getString("User_ID");
//                //System.out.println(kl);
//            if( r.getString("User_Name") != null && r.getString("User_Name").toLowerCase().equals(t)){
//                System.out.println("This is the ID " +r.getString("User_ID"));
//                System.out.println("This is the Pass " +r.getString("Password"));
//                System.out.println("This is the User name " +r.getString("User_Name"));
//            }
////            if(kl.toLowerCase().equals(t)){
////                System.out.println("This is the ID " +r.getString("User_ID"));
////            }
//
//            d.close();
//            }
//
//
//        catch (SQLException e){
//        System.out.println(e);
//
//    }
//
//    }
//    public static void createUser(){
//        check();
//
//    }

}
