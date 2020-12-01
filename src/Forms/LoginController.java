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



    @FXML
    public void login(ActionEvent e)throws IOException {

        String username = userField.getText(), password = passField.getText();
        boolean check = DataBase.login(username, password);

        if(check){

            DataBase.pullCustomers();
            Main.log("Username: " + username + " Logged in");
            Main.callForms(e, "MainForm");
        }
        else {
            Main.log("Username: " + username + " Tried log in");
            errorLbl.setText(error);
        }

    }

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

    public void otherLeng(){
        if(lan){
            french();
        }
        else
            english();
    }


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

    @FXML
    public void initialize() {
        DataBase.testSql();

        //DataBase.pullCustomers();

        location();

    }
}
