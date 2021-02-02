package Forms;

import Utilities.Appointment;
import Utilities.Customer;
import Utilities.DataBase;

import javafx.collections.ObservableList;
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
    private  TableView<Appointment> custTable;
    private ObservableList<Appointment> appointmentList;
    Customer selectedCustomer;

    public void selectedCustomer(Customer customer){
        this.appointmentList = customer.getAllAppointments();
        custTable.setItems(appointmentList);
        selectedCustomer = customer;
    }

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
        //Customer customer = selectedCustomer;
        Parent parent;
        Stage stage;

        if (selectedCustomer == null)
            showMessageDialog(null, "Please select a customer");
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentForm.fxml"));
            loader.load();
            AppointmentForm selected = loader.getController();
            selected.customer((Customer) selectedCustomer);
            parent = loader.getRoot();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();

        }
    }

    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Appointments"};
        String[] areas = {"aptId", "tittle"};
        int colWidth;

        if (tbls.toLowerCase().equals("appointment")) {
           custTable.setItems(DataBase.getAllAppointments());

            for (int i = 0; i < 2; i++) {
                if (lblCustomer[i].equals("ID"))
                    colWidth = 50;
                else
                    colWidth = 450;

                TableColumn column = new TableColumn(lblCustomer[i]);
                column.setCellValueFactory(new PropertyValueFactory<Appointment, String>(areas[i]));
                column.setMinWidth(colWidth);
                custTable.getColumns().addAll(column);
            }
        }

    }

    @FXML
    public void initialize(){
        colCreator("appointment");
    }
}
