package Forms;

import Utilities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


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
    public void button(ActionEvent e)throws IOException {


        switch (((Button)e.getSource()).getText()){
            case "Add a New Customer":
                Main.callForms(e, "addCustomer");
                break;
            case "Add New Appointment":
                Main.callForms(e, "addCustomer");
                break;
            case "Customer Records":
                Main.callForms(e, "Records");
                break;
            case "Log":
                Main.callForms(e, "log");
                break;
        }

    }

        @FXML
    public void initialize(){
        user.setText(DataBase.getUser().toUpperCase() + " "+ Connect.getCountry());
    }
}
