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
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * This will add an appointment
 * @author Fernando Rosa
 * */
public class AppointmentForm {

    private final ObservableList<Contact> newContact = FXCollections.observableArrayList();
    private ObservableList<Appointment> addNewAppointment = FXCollections.observableArrayList();
    private Appointment newAppt;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox typeOfApp;
    @FXML
    private TextField contactName;
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
    public int id;
    public String name, address, zipCode, city, phone;

    /**
     * This will fill the customer fields in this class
     * */
    public void customer(Customer customer){
        contactName.setText(customer.getName());
        name = customer.getName();
        phone = customer.getPhone();
        address = customer.getAddress();
        zipCode = customer.getZipCode();
        city = customer.getCity();
        id = customer.getId();
        this.addNewAppointment = customer.getAllAppointments();

    }

    /**
     * The cancel button takes you back to the main form
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form
     * */
    public void cancel(ActionEvent e)throws IOException {

        Main.callForms(e, "MainForm");
    }

    /**
     * Fills the type ComboBox
     * */
    public void fillType(){

        String[] type = {"Computer Sales", "Security System", "Technical Support"};
        typeOfApp.getItems().addAll(type);
        typeOfApp.getSelectionModel().select(0);

    }

    /**
     * The save button add an appointment to a customer
     * @param e ActionEvent
     * @throws IOException Failed to save the appointment
     * */
    public void save(ActionEvent e)throws IOException {

        Contact contact;
        Customer upCustomer;

        if(check()){

            String appTittle = tittle.getText(), date = startDate.getValue().toString(), description = descriptionTxt.getText(),
                location = locationTxt.getText(), mail = email.getText(), hour = time.getValue().toString(),
                    type = typeOfApp.getValue().toString(), end="";
            int contactId = DataBase.getAllContacts().size()+1;

            switch (hour){
                case "08:00:00" -> end = "08:45:00";
                case "09:00:00" -> end = "09:45:00";
                case "10:00:00" -> end = "10:45:00";
                case "11:00:00" -> end = "11:45:00";
                case "12:00:00" -> end = "12:45:00";
            }

            contact = new Contact(contactId, contactName.getText(), mail);

            newContact.add(contact);

            DataBase.addContact(contact);

            newAppt = new Appointment(newContact, appId, description, date+" "+hour,
                    date+" "+end, appTittle,type, location);

            addNewAppointment.add(newAppt);

            upCustomer = new Customer(addNewAppointment, id, name, address, zipCode, city, phone);

            DataBase.addAppointment(newAppt, contactId, id);

            DataBase.updateCustomer(id, upCustomer, true);

            showMessageDialog(null, "Customer: " + contactName.getText()
                                +"\nDate: " + date);

            Main.callForms(e, "MainForm");
        }
    }

    /**
     * This will add a new element to the array
     * @param n How big the array is
     * @param arr The array to be expanded
     * @param x The string  to be added
     * */
    public static String[] addString(int n, String[] arr, String x) {
        int i;

        String[] newArr = new String[n + 1];

        for (i = 0; i < n; i++)
            newArr[i] = arr[i];

        newArr[n] = x;
        return newArr;
    }

    /**
     * This will fill the time ComboBox
     **/
    public void getTime(){

        time.getItems().clear();
        ArrayList<String> timeToSet = new ArrayList<String>();
        timeToSet.add("08:00:00");
        timeToSet.add("09:00:00");
        timeToSet.add("10:00:00");
        timeToSet.add("11:00:00");
        timeToSet.add("12:00:00");


        for(Appointment appointment: DataBase.getAllAppointments()){

            String remove = appointment.getStart().substring(0, appointment.getStart().indexOf("."));
            if(appointment.getStart().contains(" ")){
                if(splitDate(remove).equals(startDate.getValue().toString())){
                    timeToSet.remove(splitTime(remove));
                }
            }
        }

        time.getItems().addAll(timeToSet);
    }

    /**
     *This will create the calender
     * it will check if the for the present day
     * then it will mark off in red all the days
     * that have passed, and mark off in gray if
     * there is not anymore appointment place
     *for the day
     * */
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

    /**
     * Split the String time and returns the date
     * @param dateTime String date
     * @return the time
     * */
    public static  String splitTime(String dateTime){
        String[] split = dateTime.split("\\s+");
        dateTime = split[1];
        return dateTime;
    }

    /**
     * Split the String date and returns the time
     * @param dateTime String date
     * @return the date
     * */
    public static  String splitDate(String dateTime){
        String[] split = dateTime.split("\\s+");
        dateTime = split[0];
        return dateTime;
    }

    /**
     * Checks for empty text fields and logical error
     * @return If everything is good
     **/
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

    /**
     * Initializes the form.
     * */
    @FXML
    public void initialize() {

        daysAvailable();
        fillType();
    }
}
