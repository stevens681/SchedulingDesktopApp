package Forms;

import Utilities.Customer;
import Utilities.DataBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class RecordsController {

    @FXML
    private TableView custTable;

    @FXML
    public void button(ActionEvent e)throws IOException {

        switch (((Button) e.getSource()).getText()) {
            case "Add Appointment" -> apptButton(e);
            //case "Modify Customer" -> modCustomer(e);
            case "Back" -> Main.callForms(e, "MainForm");
            //case "Log" -> Main.callForms(e, "log");
        }

    }

    @FXML
    public void apptButton(ActionEvent e) throws IOException {
        Customer customer = (Customer) custTable.getSelectionModel().getSelectedItem();
        Parent parent;
        Stage stage;

        if (customer == null)
            showMessageDialog(null, "Please select a customer");
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentForm.fxml"));
            loader.load();
            AppointmentForm selected = loader.getController();
            selected.customer((Customer) custTable.getSelectionModel().getSelectedItem());
            parent = loader.getRoot();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();

        }
    }

    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Appointments"};
        String[] areas = {"id", "name"};
        int colWidth;

        if (tbls.toLowerCase().equals("customer")) {
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
    public void initialize(){
        colCreator("customer");
    }
}
