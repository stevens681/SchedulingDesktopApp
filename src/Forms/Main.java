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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This is the main class of the project
 * @author Fernando Rosa
 * */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Scheduling Desktop Application");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    /**
     * Calls the different form and sets the size of their windows
     * @param actionEvent  ActionEvent
     * @param loadForm the form that it will be loaded
     * @throws IOException if the form has an error
     * */
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

        Parent parentForm = FXMLLoader.load(Main.class.getResource(loadForm+".fxml"));
        Stage form = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        form.setScene(new Scene(parentForm, w, h));
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

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);

        return time;
    }

    /**
     * This will get the system timezone
     * */
    public static String timeZone(){
        String zone ="";
        TimeZone timeZone = TimeZone.getDefault();
        zone = timeZone.getDisplayName(false, 0);

        return zone;
    }

    /**
     * Convert to the system timezone
     * @param time the time to be converted
     * */
    public static String convertZone( String time){

        if(time.contains(".")){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
            DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            Date localZone = null;
            try {
                localZone = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format.setTimeZone(TimeZone.getTimeZone(timeZone()));
            String zone = outFormat.format(localZone);
            return zone;
        }

        else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            Date localZone = null;
            try {
                localZone = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format.setTimeZone(TimeZone.getTimeZone(timeZone()));
            String zone = format.format(localZone);
            return zone;
        }

    }


    public static void main(String[] args) {

        Connect.connecting();
        launch(args);
    }
}
