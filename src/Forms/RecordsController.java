package Forms;

import Utilities.Customer;
import Utilities.DataBase;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecordsController {
    @FXML
    private TableView custTable;




    public void colCreator(String tbls) {

        String[] lblCustomer = {"ID", "Customer Name"};            //Labels for the customer table columns
        String[] areas = {"id", "name"};      //The values for the fields
        int colWidth;

        if (tbls.toLowerCase().equals("customer")) {
           custTable.setItems(DataBase.getAllCustomers());      //Gets all the parts

            for (int i = 0; i < 2; i++) {
                if (lblCustomer[i].equals("ID"))
                    colWidth = 50;
                else
                    colWidth = 450;

                TableColumn column = new TableColumn(lblCustomer[i]);
                column.setCellValueFactory(new PropertyValueFactory<Customer, String>(areas[i]));
                column.setMinWidth(colWidth);
                custTable.getColumns().addAll(column);        //Adds all the columns for the part's table
            }
        }

    }

    @FXML
    public void initialize(){
        colCreator("customer");
    }
}
