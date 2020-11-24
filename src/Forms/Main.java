package Forms;

import Utilities.Connect;
import Utilities.Customer;
import Utilities.DataBase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Scheduling Desktop Application");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void callForms(ActionEvent actionEvent, String loadForm) throws IOException {

        int w = 0, h = 0;


        switch (loadForm){
            case "Records":
                w = 600;
                h = 600;
                break;
            case "addCustomer":
                    w = 400;
                    h = 600;
                    break;
            case "MainForm":
                w = 300;
                h = 300;
                break;
        }

        Parent addPartForm = FXMLLoader.load(Main.class.getResource(loadForm+".fxml"));
        Stage form = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        form.setScene(new Scene(addPartForm, w, h));
        form.show();
    }

    public static void log(String logData){
        try {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            File logTxt = new File("login_activity.txt");
            if(!logTxt.exists()){
                logTxt.createNewFile();
            }

            logData = formatter.format(date) + " " + logData + "\n";

            FileWriter logWrite = new FileWriter("login_activity.txt", true);
            logWrite.write(logData);
            logWrite.close();


        } catch (IOException e) {
            System.out.println("System error: " + e.toString());

        }

    }



    public static void main(String[] args) {


        Customer cu = new Customer(1, "jo", "12 dsdf", "02286", "monte", "323");
        Customer ci = new Customer(2, "johhgdhgfhgd kjjkhdjs", "13 dsdf", "02286", "monte dsfdf", "323 24323");
        DataBase.addCustomer(cu);
        DataBase.addCustomer(ci);


        
        Connect.connecting();
        //Connect.createUser();
        launch(args);
    }
}
