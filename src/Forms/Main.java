package Forms;

import Utilities.Connect;
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


        switch (loadForm) {
            case "Records", "MainForm" -> {
                w = 600;
                h = 600;
            }
            case "addCustomer", "ModifyCustomer" -> {
                w = 400;
                h = 600;
            }
            case "AppointmentForm" -> {
                w = 600;
                h = 400;
            }
        }

        Parent addPartForm = FXMLLoader.load(Main.class.getResource(loadForm+".fxml"));
        Stage form = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        form.setScene(new Scene(addPartForm, w, h));
        form.show();
    }

    /**
     * This log every user that logs in, to an external .txt file
     * @param logData The string to be log
     * */
    public static void log(String logData){
        try {


            File logTxt = new File("login_activity.txt");
            if(!logTxt.exists()){
                logTxt.createNewFile();
            }

            logData = time() + " " + logData + "\n";

            FileWriter logWrite = new FileWriter("login_activity.txt", true);
            logWrite.write(logData);
            logWrite.close();


        } catch (IOException e) {
            System.out.println("System error: " + e.toString());

        }

    }

    /**
     * This get the system time
     * */
    public static String time(){

        String time = "";

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);


        return time;
    }



    public static void main(String[] args) {


//        Customer cu = new Customer(1, "jo", "12 dsdf", "02286", "monte", "323");
//        Customer ci = new Customer(2, "johhgdhgfhgd kjjkhdjs", "13 dsdf", "02286", "monte dsfdf", "323 24323");
//        DataBase.addCustomer(cu);
//        DataBase.addCustomer(ci);


        
        Connect.connecting();
        //Connect.createUser();
        launch(args);
    }
}
