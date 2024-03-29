package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The database class
 * @author  Fernando Rosa
 * */
public class DataBase {

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<String> canada = FXCollections.observableArrayList();
    private static final ObservableList<String> uk = FXCollections.observableArrayList();
    private static final ObservableList<String> usa = FXCollections.observableArrayList();
    private static final ObservableList<String> allCountries = FXCollections.observableArrayList();
    private static String user;

    /**
     * Checks for the username and the password in the database
     * @param username The user
     * @param pass The password
     * @return if the user is logged in
     * */
    public static boolean login(String username, String pass){

        boolean ok = false;

        try {
            Statement data = Connect.sendData().createStatement();
            String q = "SELECT User_Name, Password FROM users";
            ResultSet results = data.executeQuery(q);

            while (results.next()){
                if(results.getString("User_Name") != null && results.getString("User_Name").toLowerCase().
                        equals(username)) {

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
            System.out.println("SQLException: " + e.getMessage());
        }
        return ok;
    }

    /**
     * Add a user to the database
     * @param username The user
     * @param password The password
     * */
    public static void addUser(String username, String password){
        try{
            Statement data = Connect.sendData().createStatement();

            String query = "INSERT INTO users (User_Name, User_ID, Create_Date, Password)";


            String values = " VALUES ('" + username + "', '" +1+ "', '" + java.time.LocalDate.now() + "', '" + password + "')";
            PreparedStatement statement = Connect.sendData().prepareStatement(query+values);
            statement.executeUpdate();
            data.close();
        }catch (SQLException e){
            System.out.println("Adding the customer SQLException: " + e.getMessage());
        }

    }

    /**
     * Pull the countries from the database
     * */
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
            System.out.println("SQLException: " + e.getMessage());

        }
    }

    /**
     * Pull the states from the database
     * The Lambda expression in this allowed me to reduce the code
     * and make the code simpler to read
     * */
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
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * Searches for a name in a table and converts to ID
     * @param nameToId The name to be converted into an ID
     * @param table The name of the table to search
     * @param columnFrom What column to search
     * @param columnResult The result of the column
     * */
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
            System.out.println("SQLException: " + e.getMessage());
        }
        return id;
    }

    /**
     * Searches for an ID and converts to a name
     * @param idToName The ID to be converted into a name
     * @param table The name of the table to search
     * @param columnFrom What column to search
     * @param columnResult The result of the column
     * */
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
            System.out.println("SQLException: " + e.getMessage());

        }
        return idToName;
    }

    /**
     * Converts an ID into another table ID
     * @param idToId The ID to be converted into an ID
     * @param table The name of the table to search
     * @param columnFrom What column to search
     * @param columnResult The result of the column
     * */
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
            System.out.println("SQLException: " + e.getMessage());
        }
        return id;
    }

    /**
     * Remove a customer
     * @param selectedCustomer The selected customer
     * @return If the part is deleted
     */
    public static boolean deleteCustomer(Customer selectedCustomer) {
        for(Customer c: allCustomers) {
            if (c.getId() == selectedCustomer.getId()) {
                allCustomers.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a appointment
     * @param selectedAppointment The selected appointment
     * @return If the part is deleted
     */
    public static boolean deleteAppointment(Appointment selectedAppointment) {
        for(Appointment a: allAppointments) {
            if (a.getAptId() == selectedAppointment.getAptId()) {
                allAppointments.remove(a);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a contact
     * @param selectedContact The selected contact
     * @return If the part is deleted
     */
    public static boolean deleteContact(Contact selectedContact) {
        for(Contact c: allContacts) {
            if (c.getId() == selectedContact.getId()) {
                try {
                    Statement data = Connect.sendData().createStatement();
                    String query = "DELETE FROM contacts WHERE Contact_ID='"+selectedContact.getId()+"'";
                    PreparedStatement statement = Connect.sendData().prepareStatement(query);
                    statement.executeUpdate(query);
                    data.close();
                    allContacts.remove(c);

                }catch (SQLException a) {
                    System.out.println("SQLException: " + a.getMessage());

                }
                return true;
            }
        }
        return false;
    }

    /**
     * Pull the customers from the database
     * */
    public static void pullCustomers(){

        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM customers";
            ResultSet results = data.executeQuery(query);
            while(results.next()) {
                Customer customer = new Customer(results.getInt("Customer_ID"),
                        results.getString("Customer_Name"), results.getString("Address"),
                        results.getString("Postal_Code"), getSearchName(results.getString("Division_ID"),
                        table, columnFrom, columnResult),
                        results.getString("Phone"));
                allCustomers.add(customer);
            }
            data.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            
        }
    }

    /**
     * Pull the contacts from the database
     * */
    public static void pullContacts(){
        ObservableList<Contact> newContact = FXCollections.observableArrayList();
        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM contacts";
            ResultSet results = data.executeQuery(query);

            while(results.next()) {
                Contact contact = new Contact(results.getInt("Contact_ID"),
                        results.getString("Contact_Name"), results.getString("Email"));

                newContact.add(contact);
                allContacts.add(contact);
                for(Appointment a: allAppointments){
                    if(a.getAptId() == results.getInt("Contact_ID")){
                        a.addContact(contact);
                    }
                }
            }
            data.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * Pull the appointments from the database
     * */
    public static void pullAppointments(){
        ObservableList<Appointment> newAppointment = FXCollections.observableArrayList();
        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM appointments";
            ResultSet results = data.executeQuery(query);

            while(results.next()) {
                Appointment appointment = new Appointment(results.getInt("Appointment_ID"),
                        results.getString("Description"), results.getString("Start"),
                        results.getString("End"), results.getString("Title"),
                        results.getString("Type"), results.getString("Location"));
                newAppointment.add(appointment);
                allAppointments.add(appointment);
                for(Customer c: allCustomers){
                    if(c.getId() == results.getInt("Customer_ID")){
                        c.addAppointment(appointment);
                    }
                }
            }
            data.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * Add a customer to the customer list and the database
     * @param newCustomer The new customer to be added
     * */
    public static void addCustomer(Customer newCustomer){

        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        String name = newCustomer.getName(), address = newCustomer.getAddress(),
                phone = newCustomer.getPhone(), zip = newCustomer.getZipCode(),
                state = getSearchName(newCustomer.getCity(), table, columnFrom, columnResult),
                user = DataBase.getUser();

        System.out.println(state);
        int id=newCustomer.getId();

        boolean exist = true;

        for(Customer c : allCustomers){
            if(newCustomer.getName().equalsIgnoreCase(c.getName())){
                exist =false;
            }
        }

        if(exist){

            try{
                Statement data = Connect.sendData().createStatement();

                String query = "INSERT INTO customers (Customer_ID, Address, Create_Date, " +
                        "Created_By, Customer_Name, Division_ID, " +
                        "Phone, Postal_Code)";

                String values = " VALUES ('" + id + "', '"+ address + "', '" +
                        java.time.LocalDate.now() + "', '" + user + "', '" + name + "', '" +
                        getSearchID(state, "first_level_divisions", "Division", "Division_ID")+"', '"+
                        phone + "', '" + zip + "')";
                PreparedStatement statement = Connect.sendData().prepareStatement(query+values);
                statement.executeUpdate();
                data.close();
            }catch (SQLException e){
                System.out.println("Adding the customer SQLException: " + e.getMessage());
            }
            allCustomers.add(newCustomer);
        }
        else
            showMessageDialog(null, "This customer already exists!");
    }

    /**
     * This will update an existing customer and will edit the database
     * @param index The customer ID
     * @param selectedCustomer The customer to be edited
     * @param isAppointment If the customer update is an appointment
     * */
    public static void updateCustomer(int index, Customer selectedCustomer, boolean isAppointment){

        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        String name = selectedCustomer.getName(), address = selectedCustomer.getAddress(),
                phone = selectedCustomer.getPhone(), zip = selectedCustomer.getZipCode(),
                state = getSearchName(selectedCustomer.getCity(), table, columnFrom, columnResult), user = DataBase.getUser();

        int id=selectedCustomer.getId();

        if(!isAppointment) {
            try{
                Statement data = Connect.sendData().createStatement();

                String query = "UPDATE customers SET Address='"+address+"', Customer_Name='"+name+
                        "', Division_ID='"+getSearchID(state, "first_level_divisions", "Division", "Division_ID")+
                        "', Last_Update='"+java.time.LocalDate.now()+"', Last_Updated_By='"+user+"', Phone='"+phone+"', Postal_Code='"+zip+
                        "' WHERE Customer_ID='"+id+"'";

                PreparedStatement statement = Connect.sendData().prepareStatement(query);
                statement.executeUpdate(query);

                data.close();

            }catch (SQLException e){
                System.out.println("Adding the customer SQLException: " + e.getMessage());
            }
        }
        getAllCustomers().set(index -1, selectedCustomer);

    }

    /**
     * This will update an existing appointment and will edit the database
     * @param index Appointment ID
     * @param selectedAppointment  The appointment to be updated
     * @param selectedContact Contact to be updated
     * @param contactIndex Contact ID
     * */
    public static void updateAppointment(int index, Appointment selectedAppointment, int contactIndex, Contact selectedContact){

        String contactName = selectedContact.getName(), email = selectedContact.getEmail(),
                description = selectedAppointment.getDescription(), end = selectedAppointment.getEnd(),
                location = selectedAppointment.getLocation(), start = selectedAppointment.getStart(),
                title = selectedAppointment.getTittle(), type = selectedAppointment.getType();

        try{
            Statement data = Connect.sendData().createStatement();

            String query = "UPDATE contacts SET Contact_Name='"+contactName+"', Email='"+email+"' WHERE Contact_ID='"+contactIndex+"'";

            PreparedStatement statement = Connect.sendData().prepareStatement(query);
            statement.executeUpdate(query);

            data.close();

        }catch (SQLException e){
            System.out.println("Adding the customer SQLException: " + e.getMessage());
        }

        try{
            Statement data = Connect.sendData().createStatement();

            String query = "UPDATE appointments SET Description='"+description+"', End='" +end+"', "+
                    "Last_Update='"+java.time.LocalDate.now()+"', Last_Updated_By='"+user+"', "+
                    "Location='"+location+"', Start='"+start+"',  Title='"+title+"', Type='"+type+"'"+
                    " WHERE Appointment_ID='"+index+"'";

            PreparedStatement statement = Connect.sendData().prepareStatement(query);
            statement.executeUpdate(query);

            data.close();

        }catch (SQLException e){
            System.out.println("Adding the customer SQLException: " + e.getMessage());
        }

        getAllContacts().set(contactIndex-1, selectedContact);
        getAllAppointments().set(index -1, selectedAppointment);
    }

    /**
     * This will add a new appointment
     * @param newAppointment the new appointment to be added
     * @param contactID The contact ID
     * @param customerID The customer ID
     * */
    public static void addAppointment(Appointment newAppointment, int contactID, int customerID){

        String description = newAppointment.getDescription(), start = newAppointment.getStart(),
                        end = newAppointment.getEnd(), tittle = newAppointment.getTittle(),
                        location = newAppointment.getLocation(), type = newAppointment.getType(),
                        user = DataBase.getUser();

        int id = newAppointment.getAptId();

        boolean exist = true;

        for(Appointment appointment : allAppointments){
            if(id == appointment.getAptId()){
                exist =false;

            }
        }

        if(exist) {

            try{
                Statement data = Connect.sendData().createStatement();

                String query = "INSERT INTO appointments (Appointment_ID, Contact_ID, Create_Date, " +
                        "Created_By, Customer_ID, Description, End, Last_Update, Last_Updated_By, " +
                        "Location, Start, Title, Type)";

                String values = " VALUES ('" + id + "', '" + contactID + "', '" + java.time.LocalDate.now() + "', '" +
                        user+"', '" + customerID + "', '" + description + "', '" + end + "', '" +
                        java.time.LocalDate.now() + "', '" + user + "', '" + location+"', '" +
                        start + "', '" + tittle+"', '" + type+"')";

                PreparedStatement statement = Connect.sendData().prepareStatement(query+values);
                statement.executeUpdate();

                data.close();

            }catch (SQLException e){
                System.out.println("Adding the appointment SQLException: " + e.getMessage());
            }
            allAppointments.add(newAppointment);

        }
        else
            showMessageDialog(null, "This  already exists!");
    }

    /**
     * This will add a new contact
     * @param newContact Contact to be added
     * */
    public static void addContact(Contact newContact){

        String name = newContact.getName(), email = newContact.getEmail();

        int id=newContact.getId();

        boolean exist = true;

        for(Contact c : allContacts){
            if(newContact.getEmail().equalsIgnoreCase(c.getEmail())){
                exist =false;
            }
        }

        if(exist){

            try{
                Statement data = Connect.sendData().createStatement();

                String query = "INSERT INTO contacts (Contact_ID, Contact_Name, Email)";

                String values = " VALUES ('"+id+"', '" + name+"', '" + email+"')";

                PreparedStatement statement = Connect.sendData().prepareStatement(query+values);

                statement.executeUpdate();

                data.close();

            }catch (SQLException e){
                System.out.println("Adding the contact SQLException: " + e.getMessage());
            }
            allContacts.add(newContact);
        }
    }

    public static ObservableList<Customer>getAllCustomers(){return allCustomers;}
    public static ObservableList<Contact>getAllContacts(){return allContacts;}
    public static ObservableList<Appointment>getAllAppointments(){return allAppointments;}
    public static ObservableList<String>getCanada(){return canada;}
    public static ObservableList<String>getUk(){return uk;}
    public static ObservableList<String>getUsa(){return usa;}
    public static ObservableList<String>getAllCountries(){return allCountries;}
    public static String getUser(){return user;}
}
