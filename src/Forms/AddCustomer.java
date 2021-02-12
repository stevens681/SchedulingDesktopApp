package Forms;

import Utilities.Connect;
import Utilities.Customer;
import Utilities.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This will add a customer
 * @author Fernando Rosa
 * */
public class AddCustomer {

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

    int id = DataBase.getAllCustomers().size()+1;

    /**
     * Will fill the state ComboBox
     * */
    public void setStateCombo(){

        String country = countryCombo.getValue().toString();
        stateCombo.getItems().clear();

        switch (country) {
            case "Canada" -> stateCombo.getItems().addAll(DataBase.getCanada());
            case "United Kingdom" -> stateCombo.getItems().addAll(DataBase.getUk());
            case "United States" -> stateCombo.getItems().addAll(DataBase.getUsa());
        }

        stateCombo.getSelectionModel().select(0);
    }

    /**
     * This will make sure there is no empty fields
     * Then will assign all the values
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form or save the customer
     **/
    public void saveCustomer(ActionEvent e)throws IOException{

        if(check()){
            String name = custNameTxt.getText(), address = custAddressTxt.getText(),
                    phone = custPhoneTxt.getText(), zip = custZipTxt.getText(),
                    country = countryCombo.getValue().toString(),
                    state = stateCombo.getValue().toString();

            Customer customer = new Customer(id, name, address, zip, state, phone);
            DataBase.addCustomer(customer);

            lbl.setText(DataBase.getUser().toUpperCase()+" Customer added");
            Main.callForms(e, "MainForm");
        }

    }

    /**
     * This will take you back to the main form
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form
     **/
    public void cancel(ActionEvent e)throws IOException{

        Main.callForms(e, "MainForm");
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
     * Initializes the form
     * */
    public void initialize(){
        lbl.setText(DataBase.getUser().toUpperCase());
        DataBase.pullCountries();
        countryCombo.getItems().addAll(DataBase.getAllCountries());
        countryCombo.getSelectionModel().select(Connect.getCountry());
        setStateCombo();
        custID.setText("Customer ID:\n"+id);
    }
}
