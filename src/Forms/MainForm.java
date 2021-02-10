package Forms;

import Utilities.*;
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

import static javax.swing.JOptionPane.showMessageDialog;


public class MainForm {

    @FXML
    private Label user;
    @FXML
    private TableView<Customer> custTable;

    /**
     * The handler of the buttons
     * @param e ActionEvent
     * */
    @FXML
    public void button(ActionEvent e)throws IOException {

        switch (((Button) e.getSource()).getText()) {
            case "Add a New Customer" -> Main.callForms(e, "addCustomer");
            case "Modify Customer" -> modCustomer(e);
            case "Customer Records" -> Main.callForms(e, "Records");
            case "Log" -> Main.callForms(e, "log");
            case "Delete Customer" -> deleteCustomer(e);
        }

    }

    /**
     * This delete a selected customer
     * Updates the table view
     * @param e ActionEvent
     */
    @FXML
    public void deleteCustomer(ActionEvent e) {

        Customer customer = custTable.getSelectionModel().getSelectedItem();        //Gets the selected product

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
                    showMessageDialog(null,"SQLException: " + a.getMessage());

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

        String[] lblCustomer = {"ID", "Customer Name"};
        String[] areas = {"id", "name"};
        int colWidth;

        if (tbls.equalsIgnoreCase("customer")) {
            custTable.setItems(DataBase.getAllCustomers());

            for (int i = 0; i < 2; i++) {
                if (lblCustomer[i].equals("ID"))
                    colWidth = 50;
                else
                    colWidth = 450;

                TableColumn column = new TableColumn(lblCustomer[i]);
                column.setCellValueFactory(new PropertyValueFactory<Customer, String>(areas[i]));
                column.setMinWidth(colWidth);
                custTable.getColumns().addAll(column);
            }
        }

    }


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
     * Initializes the form.
     * */
    @FXML
    public void initialize(){

        user.setText(DataBase.getUser().toUpperCase() + " "+ Connect.getCountry());
        colCreator("customer");
    }
}
