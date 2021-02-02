package Utilities;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;


public class DataBase {

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<String> allCountries = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<String> canada = FXCollections.observableArrayList();
    private static final ObservableList<String> uk = FXCollections.observableArrayList();
    private static final ObservableList<String> usa = FXCollections.observableArrayList();
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
                switch (results.getInt("Country_ID")) {
                    case 38, 231, 230 -> allCountries.add(country);
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
                switch (results.getInt("COUNTRY_ID")) {
                    case 38 -> canada.add(state);
                    case 230 -> uk.add(state);
                    case 231 -> usa.add(state);
                }

            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

    }

    public static int getSearchID(String nameToId, String table, String columnFrom, String columnResult){

        int id = 0;

        try {

            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM "+table;
            ResultSet results = data.executeQuery(query);
            while(results.next()) {

                int search = results.getInt(columnResult);

                if(results.getString(columnFrom).equals(nameToId))
                    id = search;
            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

        return id;

    }

    public static String getSearchName(String idToName, String table, String columnFrom, String columnResult){

        try {

            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM "+table;
            ResultSet results = data.executeQuery(query);
            while(results.next()) {

                String search = results.getString(columnResult);

                if(results.getString(columnFrom).equals(idToName))
                    idToName = search;
            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

        return idToName;

    }

    public static int idToId(String idToId, String table, String columnFrom, String columnResult){

        int id = 0;

        try {

            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM "+table;
            ResultSet results = data.executeQuery(query);
            while(results.next()) {

                int search = results.getInt(columnResult);

                if(results.getString(columnFrom).equals(idToId))
                    id = search;
            }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }

        return id;

    }


    public static void pullCustomers(){
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

    public static void testSql(){
        String table = "first_level_divisions", columnFrom= "Division", columnResult ="Division_ID";

        try {
            System.out.println("Test");
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM appointments";
            ResultSet results = data.executeQuery(query);
            while(results.next()) {
                String t = results.getString("Location");
                System.out.println(t+"Inside the loop");
//                if(!results.next()){
//                    System.out.println("Creator: "+
//                            results.getString("Created_By")+
//                            "Name: "+ results.getString("Customer_Name")+
//                            "Division ID: "+results.getString("Division_ID")+
//                            "Date: "+results.getString("Create_Date"));
//                }

           }
            data.close();

        } catch (SQLException e) {
            showMessageDialog(null,"SQLException: " + e.getMessage());

        }
        int i=getSearchID("Alabama", table, columnFrom, columnResult);
        String g = Integer.toString(i);

        System.out.println("City: "+g );

    }

    public static void addCustomer(Customer newCustomer, String country){

        String name = newCustomer.getName(), address = newCustomer.getAddress(),
                phone = newCustomer.getPhone(), zip = newCustomer.getZipCode(),
                state = newCustomer.getCity();
        int id=newCustomer.getId();

        String user = DataBase.getUser();

        boolean exist = true;

        for(Customer c : allCustomers){
            if(newCustomer.getName().toLowerCase().equals(c.getName().toLowerCase())){
                exist =false;

            }
        }

        if(exist){

            try{
                Statement data = Connect.sendData().createStatement();
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = formatter.format(date);

                String query = "INSERT INTO customers (Customer_ID, Address, Create_Date, " +
                        "Created_By, Customer_Name, Division_ID, " +
                        "Phone, Postal_Code)";

                String values = " VALUES ('"+id+"', '"+
                        address+"', '"+
                        time+"', '"+
                        user+"', '"+
                        name+"', '"+
                        getSearchID(state, "first_level_divisions", "Division", "Division_ID")+"', '"+
                        phone+"', '"+
                        zip+"')";

                PreparedStatement statement = Connect.sendData().prepareStatement(query+values);
                statement.executeUpdate();

                data.close();

            }catch (SQLException e){
                showMessageDialog(null,"Adding the customer SQLException: " + e.getMessage());
            }

            allCustomers.add(newCustomer);

        }
        else
            showMessageDialog(null, "This customer already exists!");
    }

    public static void updateCustomer(int index, Customer selectedCustomer){
        getAllCustomers().set(index -1, selectedCustomer);
    }

    public static void addAppointment(Appointment newAppointment){
        allAppointments.add(newAppointment);
    }


    public static ObservableList<Customer>getAllCustomers(){return allCustomers;}
    public static ObservableList<String>getAllCountries(){return allCountries;}
    public static ObservableList<Appointment>getAllAppointments(){return allAppointments;}
    public static ObservableList<String>getCanada(){return canada;}
    public static ObservableList<String>getUk(){return uk;}
    public static ObservableList<String>getUsa(){return usa;}
    public static String getUser(){return user;}
}
