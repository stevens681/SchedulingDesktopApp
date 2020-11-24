package Utilities;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static javax.swing.JOptionPane.showMessageDialog;


public class DataBase {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<String> allCountries = FXCollections.observableArrayList();
    private static ObservableList<String> canada = FXCollections.observableArrayList();
    private static ObservableList<String> uk = FXCollections.observableArrayList();
    private static ObservableList<String> usa = FXCollections.observableArrayList();
    private static String user;


    public static boolean login(String username, String pass){
        boolean ok = false;

        try {
            Statement data = Connect.sendData().createStatement();
            String q = "SELECT User_Name, Password FROM users";
            ResultSet results = data.executeQuery(q);
            while (results.next()){
                if(results.getString("User_Name") != null && results.getString("User_Name").toLowerCase().equals(username)) {

                    String p = results.getString("Password");
                    if(p.equals(pass)) {
                        user = username;
                        ok = true;
                    }

                    else
                        ok =false;
                }

            }

        }
        catch (SQLException e){
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

        return ok;

    }

    public static void pullCountries(){

        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM countries";
            ResultSet results = data.executeQuery(query);
            while(results.next()) {

                String country = results.getString("Country");
                switch(results.getInt("Country_ID")){
                    case 38:
                    case 231:
                    case 230:

                        allCountries.add(country);
                        break;
                }

            }
            fillStates();
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

    }

    public static void fillStates(){
        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM first_level_divisions";
            ResultSet results = data.executeQuery(query);
            while(results.next()) {

                String state = results.getString("Division");
                switch(results.getInt("COUNTRY_ID")){
                    case 38:
                        canada.add(state);
                        break;
                    case 230:
                        uk.add(state);
                        break;
                    case 231:
                        usa.add(state);
                        break;
                }

            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

    }


    public void pullCustomers(){
        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM customers";
            ResultSet results = data.executeQuery(query);
            while(results.next()) {
                Customer customer = new Customer(results.getInt("Customer_ID"),
                        results.getString("Customer_Name"),
                        results.getString("Address"),
                        results.getString("Postal_Code"),
                        results.getString("Division_ID"),
                        results.getString("Phone"));
                allCustomers.add(customer);
            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());
            
        }

    }

    public static void addCustomer(Customer newCustomer){

        boolean exist = true;
        for(Customer c : allCustomers){
            if(newCustomer.getName().toLowerCase().equals(c.getName().toLowerCase())){
                exist =false;
            }
        }
        if(exist){
            allCustomers.add(newCustomer);

        }
        else
            showMessageDialog(null, "This customer already exists!");
    }


    public static ObservableList<Customer>getAllCustomers(){return allCustomers;}
    public static ObservableList<String>getAllCountries(){return allCountries;}
    public static ObservableList<String>getCanada(){return canada;}
    public static ObservableList<String>getUk(){return uk;}
    public static ObservableList<String>getUsa(){return usa;}
    public static String getUser(){return user;}
}
