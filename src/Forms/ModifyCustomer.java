package Forms;

import Utilities.Customer;
import Utilities.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyCustomer {

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
    @FXML
    private Label lbl;
    @FXML
    private Label custID;


    public void selectedCustomer(Customer customer) {
        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        int countryId = DataBase.idToId(customer.getCity(), "first_level_divisions", "Division_ID","COUNTRY_ID");
        String country = "";

        switch (countryId) {
            case 38 -> country = "Canada";
            case 230 -> country = "United Kingdom";
            case 231 -> country = "United States";
        }
        custID.setText("Customer ID:\n"+ customer.getId());
        custNameTxt.setText(customer.getName());
        custAddressTxt.setText(customer.getAddress());
        custPhoneTxt.setText(customer.getPhone());
        custZipTxt.setText(customer.getZipCode());
        countryCombo.getSelectionModel().select(country);
        stateCombo.getSelectionModel().select(DataBase.getSearchName(customer.getCity(), table, columnFrom, columnResult));
    }

    public void setStateCombo(ActionEvent e) {

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

    public void saveCustomer(ActionEvent e) {
    }

    private boolean check(){

        boolean checked = true;
        String msg = "";

        if(custNameTxt.getText().isEmpty()){
            checked = false;
            msg += "The name field is empty \n";
        }
        if(custAddressTxt.getText().isEmpty()){
            checked = false;
            msg += "The address field is empty\n";
        }
        if(custPhoneTxt.getText().isEmpty()){
            checked = false;
            msg += "The phone field is empty\n";
        }else{
            Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
            Matcher matcher = pattern.matcher(custPhoneTxt.getText());

            if(!matcher.matches()){
                checked = false;
                msg += custPhoneTxt.getText()+ " is not a valid number\n";
            }
        }
        if(custZipTxt.getText().isEmpty()){
            checked = false;
            msg += "The zip field is empty\n";
        }
        lbl.setText(DataBase.getUser().toUpperCase()+" Please check\n"+msg);

        System.out.println(msg);

        return checked;

    }

    public void cancel(ActionEvent e)throws IOException {
        Main.callForms(e, "MainForm");
    }
}
