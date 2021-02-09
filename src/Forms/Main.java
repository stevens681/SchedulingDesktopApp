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

    public static String timeZone(){
        String zone ="";
        TimeZone timeZone = TimeZone.getDefault();
        zone = timeZone.getDisplayName(false, 0);

        return zone;
    }

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


//        Customer cu = new Customer(1, "jo", "12 dsdf", "02286", "monte", "323");
//        Customer ci = new Customer(2, "johhgdhgfhgd kjjkhdjs", "13 dsdf", "02286", "monte dsfdf", "323 24323");
//        DataBase.addCustomer(cu);
//        DataBase.addCustomer(ci);

//        Appointment a = new Appointment(1, "1 qw", "2020-12-15 04:00", "2020-12-07", "1", "in", "12 dfsdf");
//        Appointment b = new Appointment(2, "1 qw", "2020-12-15 05:00", "2020-12-08", "1", "in", "12 dfsdf");
//        Appointment c = new Appointment(3, "1 qw", "2020-12-14", "2020-12-09", "1", "in", "12 dfsdf");
//        Appointment d = new Appointment(4, "1 qw", "2020-12-14", "2020-12-10", "1", "in", "12 dfsdf");
//        Appointment e = new Appointment(5, "1 qw", "2020-12-16", "2020-12-11", "1", "in", "12 dfsdf");
//        Appointment f = new Appointment(6, "1 qw", "2020-12-02", "2020-12-12", "1", "in", "12 dfsdf");
//
//        DataBase.addAppointment(a);
//        DataBase.addAppointment(b);
//        DataBase.addAppointment(c);
//        DataBase.addAppointment(d);
//        DataBase.addAppointment(e);
//        DataBase.addAppointment(f);

        System.out.println("The whole time -->"+time());
        System.out.println("TimeZone -->"+timeZone());
        System.out.println("Converted zone -->"+convertZone(time()));
        String[] segments = time().split(" ", -1);
        System.out.println("Converted zone -->"+convertZone("0000-00-00 20:00:00 PST"));
        System.out.println("segment  -->" + segments[1]);

        //System.out.println("\n\nThis is split "+ localTimeZone("08:00:00"));
        Connect.connecting();
        //Connect.createUser();
        launch(args);
    }
}
