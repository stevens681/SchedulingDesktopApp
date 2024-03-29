package Forms;

import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

import static javax.swing.JOptionPane.showMessageDialog;


public class MainForm {

    @FXML
    private Label user;
    @FXML
    private TableView<Customer> custTable;
    @FXML
    private RadioButton radioUSA;
    @FXML
    private RadioButton radioCA;
    @FXML
    private RadioButton radioUK;
    private static final ObservableList<String> canadaID = FXCollections.observableArrayList();
    private static final ObservableList<String> ukID = FXCollections.observableArrayList();
    private static final ObservableList<String> usaID = FXCollections.observableArrayList();

    /**
     * The handler of the buttons
     * The Lambda expression in this allowed me to reduce the code
     * and make the code simpler to read
     * @param e ActionEvent
     *
     * */
    @FXML
    public void button(ActionEvent e)throws IOException {
        switch (((Button) e.getSource()).getText()) {
            case "Add a New Customer" -> Main.callForms(e, "addCustomer");
            case "Modify Customer" -> modCustomer(e);
            case "View/Add Appointment" -> Main.callForms(e, "Records");
            case "Delete Customer" -> deleteCustomer(e);
            case "Report" -> Main.callForms(e, "OpeningForm");
            case "Week and Month" -> weekMonthText();
            case "Add Appointment" -> {
                if(DataBase.getAllCustomers().isEmpty()){
                    showMessageDialog(null, "Please add a customer first!");
                }
                else {
                    Main.callForms(e, "AppointmentForm");
                }
            }
        }
    }

    /**
     * Pull the states from the database
     * The Lambda expression in this allowed me to reduce the code
     * and make the code simpler to read
     * */
    public static void fillStatesID(){

        try {
            Statement data = Connect.sendData().createStatement();
            String query = "SELECT * FROM first_level_divisions";
            ResultSet results = data.executeQuery(query);

            while(results.next()) {

                String state = results.getString("Division");
                switch (results.getInt("COUNTRY_ID")) {
                    case 38 -> canadaID.add(state);
                    case 230 -> ukID.add(state);
                    case 231 -> usaID.add(state);
                }
            }
            data.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * This delete a selected customer
     * Updates the table view
     * @param e ActionEvent
     */
    @FXML
    public void deleteCustomer(ActionEvent e) {

        Customer customer = custTable.getSelectionModel().getSelectedItem();
        if(customer.getAllAppointments().isEmpty()){
            int m = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this customer?");
            if(m == JOptionPane.YES_OPTION){

                try {
                    Statement data = Connect.sendData().createStatement();
                    String query = "DELETE FROM customers WHERE Customer_ID='"+customer.getId()+"'";
                    PreparedStatement statement = Connect.sendData().prepareStatement(query);
                    statement.executeUpdate(query);
                    data.close();

                }catch (SQLException a) {
                    System.out.println("SQLException: " + a.getMessage());

                }
                DataBase.deleteCustomer(customer);
                custTable.setItems(DataBase.getAllCustomers());

            }
        }
        else{
            showMessageDialog(null, "This customer has appointments, " +
                    "please delete all the appointments before deleting the customer.");
        }
    }

    /**
     * This will create the tableview and populate the cells
     * @param  tbls the name of the table
     * */
    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Customer Name", "City"};
        String[] areas = {"id", "name", "city"};
        int colWidth;

        if (tbls.equalsIgnoreCase("customer")) {
            custTable.setItems(DataBase.getAllCustomers());

            for (int i = 0; i < 3; i++) {
                if (lblCustomer[i].equals("ID"))
                    colWidth = 50;
                else
                    colWidth = 225;


                TableColumn column = new TableColumn(lblCustomer[i]);

                column.setCellValueFactory(new PropertyValueFactory<Customer, String>(areas[i]));
                column.setMinWidth(colWidth);
                custTable.getColumns().addAll(column);
            }
        }
    }

    /**
     * Calls the modification form to modify the selected customer
     * @param e ActionEvent
     * @throws IOException if the form does not load
     * */
    @FXML
    public void modCustomer(ActionEvent e) throws IOException {

        Customer customer =  custTable.getSelectionModel().getSelectedItem();
        Parent parent;
        Stage stage;

        if (customer == null)
            showMessageDialog(null, "Please select a customer");
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCustomer.fxml"));
            loader.load();
            ModifyCustomer selected = loader.getController();
            selected.selectedCustomer(custTable.getSelectionModel().getSelectedItem());
            parent = loader.getRoot();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();

        }
    }

    /**
     * Calls the customer records
     * to add appointments and see customers appointments
     * @param e ActionEvent
     * @throws IOException if the form does not load
     * */
    @FXML
    public void records(ActionEvent e) throws IOException {
        Customer customer =  custTable.getSelectionModel().getSelectedItem();
        Parent parent;
        Stage stage;

        if (customer == null)
            showMessageDialog(null, "Please select a customer");
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Records.fxml"));
            loader.load();
            RecordsController selected = loader.getController();
            selected.selectedCustomer(custTable.getSelectionModel().getSelectedItem());
            parent = loader.getRoot();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();

        }
    }

    /**
     * This sort the customers by countries
     * */
    public void radioButtons(ActionEvent e){

        ObservableList<Customer> byCountries = FXCollections.observableArrayList();
        byCountries.clear();

        if(radioUSA.isSelected()){
            for(Customer customer: DataBase.getAllCustomers()){
                for(String s: usaID){
                    if(customer.getCity().equals(s)){
                        byCountries.add(customer);
                        custTable.setItems(byCountries);
                    }

                }
            }
        }
        if(radioUK.isSelected()){
            for(Customer customer: DataBase.getAllCustomers()){
                for(String s: ukID) {
                    if (customer.getCity().equals(s)) {
                        byCountries.add(customer);
                        custTable.setItems(byCountries);
                    }

                }
            }
        }
        if(radioCA.isSelected()){
            for(Customer customer: DataBase.getAllCustomers()) {
                for (String s: canadaID) {
                    if (customer.getCity().equals(s)) {
                        byCountries.add(customer);
                        custTable.setItems(byCountries);
                    }

                }
            }
        }
        if(!radioCA.isSelected() && !radioUK.isSelected() && !radioUSA.isSelected()){
            custTable.setItems(DataBase.getAllCustomers());
        }
    }

    /**
     * Shows a dialog box with upcoming appointments
     * */
    public void weekMonthText(){

    LocalDate today = LocalDate.parse(Main.splitDate(Main.time(), 0));
        String week = "This week Appointments", month = "This month Appointments", text = "";


        for(Customer customer: DataBase.getAllCustomers()) {
            for (Appointment appointment: customer.getAllAppointments()) {

                LocalDate apt = LocalDate.parse(Main.splitDate(appointment.getStart(), 0));
                Period p = Period.between(today, apt);

                if(p.getDays() <= 7 && p.getDays() >= 0){
                    week += "\nAppointment ID: " + appointment.getAptId() + "\n";
                    week += "Appointment Tittle: " + appointment.getTittle() + "\n";
                    week += "Appointment Description: " + appointment.getDescription() + "\n";
                    week += "Appointment Location: " + appointment.getLocation() + "\n";
                    week += "Customer Name: " + customer.getName() + "\n";
                    week += "Appointment Start: " + appointment.getStart() + "\n";
                    week += "Appointment End: " + appointment.getEnd() + "\n";
                    week += "Customer ID: " + customer.getId() + "\n\n";

                }
                else if(p.getDays() <= 30 && p.getDays() >= 8){
                    month += "\nAppointment ID: " + appointment.getAptId() + "\n";
                    month += "Appointment Tittle: " + appointment.getTittle() + "\n";
                    month += "Appointment Description: " + appointment.getDescription() + "\n";
                    month += "Appointment Location: " + appointment.getLocation() + "\n";
                    month += "Customer Name: " + customer.getName() + "\n";
                    month += "Appointment Start: " + appointment.getStart() + "\n";
                    month += "Appointment End: " + appointment.getEnd() + "\n";
                    month += "Customer ID: " + customer.getId() + "\n\n";

                }
                else{
                    text += week + " We do not have appointment this week\n" +
                        month + " We do not have appointment this month";
                }

            }
        }
        showMessageDialog(null,week + month);
    }


    /**
     * Initializes the form.
     * */
    @FXML
    public void initialize(){



        if(DataBase.getAllCountries().isEmpty())
            DataBase.pullCountries();
        if(canadaID.isEmpty() || ukID.isEmpty() || usaID.isEmpty())
            fillStatesID();
        user.setText(DataBase.getUser().toUpperCase() + " "+ Connect.getCountry());
        colCreator("customer");

    }
}
