package Forms;

import Utilities.Connect;
import Utilities.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import javafx.event.ActionEvent;
import java.io.IOException;

public class AddCustomer {

    @FXML
    private Button cancelBtn;
    @FXML
    private TextField custNameTxt;
    @FXML
    private TextField custAddressTxt;
    @FXML
    private TextField custPhoneTxt;
    @FXML
    private TextField custZipTxt;
    @FXML
    private ComboBox countryCombo;
    @FXML
    private ComboBox stateCombo;

    public void setStateCombo(){

        String country = countryCombo.getValue().toString();
        stateCombo.getItems().clear();

        switch (country){
            case "Canada":
                stateCombo.getItems().addAll(DataBase.getCanada());
                break;
            case "United Kingdom":
                stateCombo.getItems().addAll(DataBase.getUk());
                break;
            case "United States":
                stateCombo.getItems().addAll(DataBase.getUsa());
                break;
        }

        stateCombo.getSelectionModel().select(0);
    }

    public void saveCustomer(){

        String c = custAddressTxt.getText();
        System.out.println(c);
    }

    public void cancel(ActionEvent e)throws IOException{

        Main.callForms(e, "MainForm");
    }



    public void initialize(){
        DataBase.pullCountries();

        countryCombo.getItems().addAll(DataBase.getAllCountries());

        countryCombo.getSelectionModel().select(Connect.getCountry());
        setStateCombo();

    }
}
