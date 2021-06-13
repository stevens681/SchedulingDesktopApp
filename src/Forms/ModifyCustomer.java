package Forms;

import Utilities.Connect;
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

/**
 * This will modify a customer
 * @author Fernando Rosa
 * */
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
    private int id;


    /**
     * This will fill the customer fields
     * The Lambda expression in this allowed me to reduce lines of the code
     * and makes the code simpler to read
     * @param customer The selected customer
     * */
    public void selectedCustomer(Customer customer) {
        String table = "first_level_divisions", columnFrom= "Division_ID", columnResult ="Division";
        int countryId = DataBase.idToId(String.valueOf(DataBase.getSearchID(customer.getCity(), table, columnResult, columnFrom))
                , "first_level_divisions", "Division_ID","COUNTRY_ID");
        String country = "";

        switch (countryId) {
            case 38 -> country = "Canada";
            case 230 -> country = "United Kingdom";
            case 231 -> country = "United States";
        }
        id = customer.getId();
        custID.setText("Customer ID:\n"+ customer.getId());
        custNameTxt.setText(customer.getName());
        custAddressTxt.setText(customer.getAddress());
        custPhoneTxt.setText(customer.getPhone());
        custZipTxt.setText(customer.getZipCode());
        countryCombo.getSelectionModel().select(country);
        stateCombo.getSelectionModel().select(customer.getCity());
        setStateCombo();

    }

    /**
     * Will fill the state ComboBox
     * The Lambda expression in this allowed me to reduce lines of the code
     * and makes the code simpler to read
     * */
    public void setStateCombo() {

        String country = countryCombo.getValue().toString();
        stateCombo.getItems().clear();

        switch (country){
            case "Canada" -> stateCombo.getItems().addAll(DataBase.getCanada());
            case "United Kingdom" -> stateCombo.getItems().addAll(DataBase.getUk());
            case "United States" -> stateCombo.getItems().addAll(DataBase.getUsa());
        }

    }

    /**
     * This will make sure there is no empty fields
     * Then will assign all the values
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form or modifying the customer
     **/
    public void saveCustomer(ActionEvent e) throws IOException{
        if(check()) {
            String name = custNameTxt.getText(), address = custAddressTxt.getText(),
                    phone = custPhoneTxt.getText(), zip = custZipTxt.getText(),
                    country = countryCombo.getValue().toString(),
                    state = stateCombo.getValue().toString();

            Customer customer = new Customer(id, name, address, zip, state, phone);
            DataBase.updateCustomer(id, customer, false);
            Main.callForms(e, "MainForm");
        }
    }

    /**
     * Checks for empty text fields and logical error
     * @return If everything is good
     **/
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

        return checked;

    }

    /**
     * This will take you back to the main form
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form
     **/
    public void cancel(ActionEvent e)throws IOException {
        Main.callForms(e, "MainForm");
    }

    /**
     * Initializes the form
     * */
    public void initialize(){
        lbl.setText(DataBase.getUser().toUpperCase());
        countryCombo.getItems().addAll(DataBase.getAllCountries());
        countryCombo.getSelectionModel().select(Connect.getCountry());
        setStateCombo();
        custID.setText("Customer ID:\n"+id);
    }
}
