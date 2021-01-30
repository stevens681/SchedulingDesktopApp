package Forms;

import Utilities.Appointment;
import Utilities.Contact;
import Utilities.Customer;
import Utilities.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppointmentForm {

    private final ObservableList<Contact> newContact = FXCollections.observableArrayList();
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox typeOfApp;
    @FXML
    private ComboBox contactName;
    @FXML
    private TextField tittle;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField email;
    @FXML
    private ComboBox time;
    @FXML
    private Label lbl;


    int appId = DataBase.getAllAppointments().size()+1;

    public void cancel(ActionEvent e)throws IOException {

        Main.callForms(e, "MainForm");
    }


    public void fillContact(){

        for(Customer c: DataBase.getAllCustomers()){
            contactName.getItems().addAll(c.getName());
        }

    }

    public void fillType(){

        String[] type = {"Computer Sales", "Security System", "Technical Support"};

        typeOfApp.getItems().addAll(type);
        typeOfApp.getSelectionModel().select(0);

    }

    public void save(ActionEvent e)throws IOException {
        int customerID = 0;

        Contact contact;
        Appointment newAppt;
        for(Customer c: DataBase.getAllCustomers()){
            if(c.getName() == contactName.getValue()){
                customerID = c.getId();
            }
        }

        if(check()){

            String appTittle = tittle.getText(), date = startDate.getValue().toString(), description = descriptionTxt.getText(),
                location = locationTxt.getText(), mail = email.getText(), hour = time.getValue().toString(),
                    type = typeOfApp.getValue().toString(), name = contactName.getValue().toString();
            contact = new Contact(newContact.size()+1, name, mail);

            newContact.add(contact);

            newAppt = new Appointment(newContact, appId, description, date+" "+hour, date, appTittle,type, location);

            DataBase.addAppointment(newAppt);

            System.out.println("Appointment ID: " + appId);
            System.out.println("Contact ID: " + newContact.size()+1);
            System.out.println("Created Date: " + Main.time());
            System.out.println("Created By: "+ DataBase.getUser());
            System.out.println("Customer ID: "+ customerID );
            System.out.println("Tittle: "+appTittle);
            System.out.println("Type: " + type);
            System.out.println("Description: " +description);
            System.out.println("Location: " + location);
            System.out.println("Date: " +date);
            System.out.println("Contact Name: " + name);
            System.out.println("Email: " + mail);
            System.out.println("Time: " + hour);
            Main.callForms(e, "MainForm");


        }

    }

    /**
     * This will add a new element to the array
     * @param n How big the array is
     * @param arr The array to be expanded
     * @param x The string  to be added
     * */
    public static String[] addString(int n, String arr[], String x) {
        int i;

        String newArr[] = new String[n + 1];

        for (i = 0; i < n; i++)
            newArr[i] = arr[i];

        newArr[n] = x;
        return newArr;
    }

    // in progress
    public void getTime(){

        time.getItems().clear();
        ArrayList<String> timeToSet = new ArrayList<String>();
        timeToSet.add("01:00 PM");
        timeToSet.add("02:00 PM");
        timeToSet.add("03:00 PM");
        timeToSet.add("04:00 PM");
        timeToSet.add("05:00 PM");

        for(Appointment appointment: DataBase.getAllAppointments()){

            if(appointment.getStart().contains(" ")){
                if(splitDate(appointment.getStart()).equals(startDate.getValue().toString())){
                    timeToSet.remove(splitTime(appointment.getStart()));

                }
            }
        }
        
        time.getItems().addAll(timeToSet);
    }

    private void daysAvailable(){

        String[] data = {};

        ObservableList<String> inData = FXCollections.observableArrayList();

        for(Appointment appointment: DataBase.getAllAppointments()){
            if(appointment.getStart().contains(" "))
                inData.add(splitDate(appointment.getStart()));
            else
                inData.add(appointment.getStart());
        }

        Map<String, Integer> result = new HashMap<>();
        for(String s : inData){

            if(result.containsKey(s)){

                result.put(s, result.get(s)+1);
                if(result.containsValue(5)){
                    data = addString(data.length, data, s);
                }
            }else{

                result.put(s, 1);
            }
        }

            String[] finalData = data;
            startDate.setDayCellFactory(days -> new DateCell() {
            @FXML
            public void updateItem(LocalDate date, boolean empty) {

                super.updateItem(date, empty);
                LocalDate todayNow = LocalDate.now();
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || date.compareTo(todayNow) < 0);
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        date.compareTo(todayNow) < 0) {
                    setStyle("-fx-background-color: #EF5350;");
                }

                for(int i = 0; i< finalData.length; i++){
                    String test = finalData[i];
                    DateTimeFormatter  formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate indate = LocalDate.parse(test, formatter);
                    if(date.equals(indate)){
                        setDisable(date.equals(indate));
                        setStyle("-fx-background-color: #A6ACAF;");
                    }
                }
            }
        });
    }

    public static  String splitTime(String dateTime){
        String[] split = dateTime.split("\\s+");
        dateTime = split[1];
        return dateTime;
    }

    public static  String splitDate(String dateTime){
        String[] split = dateTime.split("\\s+");
        dateTime = split[0];
        return dateTime;
    }

    private boolean check(){

        boolean checked = true;
        String msg = "";

        if(tittle.getText().isEmpty()){
            checked = false;
            msg += "The tittle field is empty. \n";
        }
        if(descriptionTxt.getText().isEmpty()){
            checked = false;
            msg += "The description field is empty.\n";
        }

        if(locationTxt.getText().isEmpty()){
            checked = false;
            msg += "The location field is empty.\n";
        }

        if(startDate.getValue()== null){
            checked = false;
            msg += "Please pick a date.\n";
        }

        if(time.getValue() == null){
            checked = false;
            msg += "Please pick a time.\n";
        }

        if(email.getText().isEmpty()){
            checked = false;
            msg += "The email field is empty\n";
        }
        else {
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            Matcher matcher = pattern.matcher(email.getText());
            if(!matcher.matches()){
                checked = false;
                msg += "Please check your email\n";
            }
        }

        lbl.setText(DataBase.getUser().toUpperCase()+" Please check:\n"+msg);

        return checked;
    }

    @FXML
    public void initialize() {

        daysAvailable();
        fillType();
        fillContact();
    }
}
