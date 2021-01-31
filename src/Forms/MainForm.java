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

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;


public class MainForm {
    @FXML
    private Label user;
    @FXML
    private Button addCustBtn;
    @FXML
    private Button addNewAp;
    @FXML
    private Button cusReBtn;
    @FXML
    private Button logBtn;
    @FXML
    private TableView<Customer> custTable;

    @FXML
    public void button(ActionEvent e)throws IOException {

        switch (((Button) e.getSource()).getText()) {
            case "Add a New Customer" -> Main.callForms(e, "addCustomer");
            case "Modify Customer" -> modCustomer(e);
            case "Customer Records" -> Main.callForms(e, "Records");
            case "Log" -> Main.callForms(e, "log");
        }

    }

    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Customer Name"};
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
     * Initializes the form.
     * */
    @FXML
    public void initialize(){

        user.setText(DataBase.getUser().toUpperCase() + " "+ Connect.getCountry());
        colCreator("customer");
    }
}
