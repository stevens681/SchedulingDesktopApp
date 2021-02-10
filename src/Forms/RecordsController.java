package Forms;

import Utilities.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The record form controller
 * @author Fernando Rosa
 * */
public class RecordsController {

    Customer selectedCustomer;
    @FXML
    private  TableView<Appointment> custTable;
    @FXML
    private Label addressLabel;
    @FXML
    private Label nameLabel;

    private ObservableList<Appointment> appointmentList;

    /**
     * This will fill the fields for the customers
     * @param customer
     * */
    public void selectedCustomer(Customer customer){

        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        int countryId = DataBase.idToId(customer.getCity(), "first_level_divisions",
                "Division_ID","COUNTRY_ID");
        String country = "";
        String number = customer.getPhone().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");

        switch (countryId) {
            case 38 -> country = "CA";
            case 230 -> country = "UK";
            case 231 -> country = "US";
        }

        this.appointmentList = customer.getAllAppointments();
        custTable.setItems(appointmentList);
        selectedCustomer = customer;
        addressLabel.setText(country + ", " + customer.getAddress() + ", " +
                DataBase.getSearchName(customer.getCity(), table, columnFrom, columnResult)+"\nTel: "+
                number);
        nameLabel.setText(customer.getName() );

    }

    /**
     * This will read the button and do the action
     * @param e ActionEvent
     * @throws IOException failed to do the action
     * */
    @FXML
    public void button(ActionEvent e)throws IOException {

        switch (((Button) e.getSource()).getText()) {
            case "Add Appointment" -> apptButton(e);
            case "Details" -> detail(e);
            case "Back" -> Main.callForms(e, "MainForm");
            case "Delete Appointment" -> deleteAppointment(e);

        }
    }

    /**
     * This delete a selected appointment
     * Updates the table view
     * @param e ActionEvent
     */
    @FXML
    public void deleteAppointment(ActionEvent e) {

        Appointment appointment = custTable.getSelectionModel().getSelectedItem();
        ObservableList<Contact> contactList;
        contactList = appointment.getAllContacts();
        int m = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this appointment?");
        if(m == JOptionPane.YES_OPTION){

            try {
                Statement data = Connect.sendData().createStatement();
                String query = "DELETE FROM appointments WHERE Appointment_ID='"+appointment.getAptId()+"'";
                PreparedStatement statement = Connect.sendData().prepareStatement(query);
                statement.executeUpdate(query);
                data.close();

            }catch (SQLException a) {
                showMessageDialog(null,"SQLException: " + a.getMessage());

            }
            for (Contact c: contactList){
                DataBase.deleteContact(c);
            }
            selectedCustomer.deleteAppointment(appointment);
            appointmentList.remove(appointment);
            DataBase.deleteAppointment(appointment);
            custTable.setItems(appointmentList);
        }
    }

    /**
     * Opens the appointment form
     * @param e ActionEvent
     * @throws IOException failed to do the action
     * */
    @FXML
    public void apptButton(ActionEvent e) throws IOException {

        Parent parent;
        Stage stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentForm.fxml"));
        loader.load();
        AppointmentForm selected = loader.getController();
        selected.customer(selectedCustomer);
        parent = loader.getRoot();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * The detail button
     * @param e ActionEvent
     * @throws IOException Check for exception
     * */
    @FXML
    public void detail(ActionEvent e) throws IOException{
        Appointment appointmentDetail = custTable.getSelectionModel().getSelectedItem();
        if(!appointmentList.isEmpty()){
            if(appointmentDetail == null){
                showMessageDialog(null, "Please select a appointment.");
            }
            else{
                System.out.println(appointmentDetail.getStart());
                String msg = "Customer: "+selectedCustomer.getName()
                        +"\nLocation: "+ appointmentDetail.getLocation()
                        +"\nTime: "+localTimeZone(appointmentDetail.getStart() + " UTC");

                showMessageDialog(null, msg, "Appointment", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else
            showMessageDialog(null, "Please Add a New Appointment", "EMPTY APPOINTMENT",
                    JOptionPane.PLAIN_MESSAGE);
    }

    /**te
     * Provides the local timezone
     * @param time The UTC times
     * @return Local time
     * */
    public static String localTimeZone(String time){

        time = Main.convertZone(time);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa z");
        Date date = null;

        try {
            date= format.parse(time);
            time = outFormat.format(date);

        }
        catch (ParseException e){
            e.printStackTrace();
        }

        String[] splitTime = time.split(" ", -1);

        time = splitTime[1] + " " + splitTime[2] + " " + splitTime[3] ;

        return time;

    }

    /**
     * This will create the tableview and populate the cells
     * @param  tbls the name of the table
     * */
    @FXML
    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Description", "Type"};
        String[] areas = {"aptId", "description", "type"};
        int colWidth;

        if (tbls.equalsIgnoreCase("appointment")) {
           custTable.setItems(DataBase.getAllAppointments());

            for (int i = 0; i < 3; i++) {
                if (lblCustomer[i].equals("ID"))
                    colWidth = 20;
                else
                    colWidth = 240;

                TableColumn column = new TableColumn(lblCustomer[i]);
                column.setCellValueFactory(new PropertyValueFactory<Appointment, String>(areas[i]));
                column.setMinWidth(colWidth);
                custTable.getColumns().addAll(column);
            }
        }

    }

    /**
     * Initializes the form.
     * */
    @FXML
    public void initialize(){
        colCreator("appointment");
    }
}
