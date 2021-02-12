package Forms;

import Utilities.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Locale;


/**
 * @author Fernando Rosa
 * This form allows the user to login into the appoitment application.
 * */
public class LoginController {

    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    @FXML
    private Button loginBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label tittleLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Label passLbl;
    @FXML
    private Label errorLbl;
    @FXML
    private Hyperlink lengLbl;
    boolean lan;
    String error = "Please check your username and password";

    /**
     * The login checks for the username and password to be correct.
     * @param e Action evernt
     * @throws IOException Failed to login
     * */
    @FXML
    public void login(ActionEvent e)throws IOException {

        String username = userField.getText().toLowerCase(), password = passField.getText();
        boolean check = DataBase.login(username, password);

        if(check){

            DataBase.pullCustomers();
            DataBase.pullAppointments();
            DataBase.pullContacts();

            Main.log("Username: " + username + " Logged in");

//            DataBase.testSql();
            Main.callForms(e, "MainForm");
        }
        else {
            Main.log("Username: " + username + " Tried log in");
            errorLbl.setText(error);
        }

    }

    /**
     * Sets the language to french.
     * */
    public void french(){
        tittleLbl.setText("Application de planification");
        tittleLbl.setTranslateX(-25.0);
        userLbl.setText("Nom d'utilisateur");
        userLbl.setTranslateX(-45.0);
        passLbl.setText("Mot de passe");
        passLbl.setTranslateX(-25.0);
        loginBtn.setText("S'identifier");
        loginBtn.setTranslateX(-30.0);
        cancelBtn.setText("Annuler");
        lengLbl.setText("English");
        lan = false;
        error = "Veuillez vérifier votre nom d'utilisateur et votre mot de passe";
    }

    /**
     * Sets the language to english.
     * */
    public void english(){
        tittleLbl.setText("Scheduling Application");
        tittleLbl.setTranslateX(0);
        userLbl.setText("Username");
        userLbl.setTranslateX(0);
        passLbl.setText("Password");
        passLbl.setTranslateX(0);
        loginBtn.setText("Login");
        loginBtn.setTranslateX(0);
        cancelBtn.setText("Cancel");
        lengLbl.setText("Française");
        lan = true;
        error = "Please check your username and password";

    }

    /**
     * Selects the language.
     * */
    public void otherLeng(){
        if(lan){
            french();
        }
        else
            english();
    }


    /**
     * Gets the location and the computer language.
     * */
    public void location(){

        Locale user = Locale.getDefault();

        String location = user.toString();
        String labels = "";

        if(location.startsWith("en")) {
            labels += "English";

        }

        if(location.startsWith("fr")){
            labels += "Française";
            french();
        }

        switch (location) {
            case "en_US" -> {
                labels += " United States";
                lan = true;
            }
            case "en_CA", "fr_CA" -> {
                labels += " Canada";
                lan = false;
            }
            case "en_UK" -> {
                labels += " United Kingdom";
                lan = true;
            }
        }
        errorLbl.setText(labels);
    }

    /**
     * Initializes the form.
     * */
    @FXML
    public void initialize() {

        location();
    }
}
