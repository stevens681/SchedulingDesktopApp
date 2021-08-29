package Forms;

import Utilities.Appointment;
import Utilities.Contact;
import Utilities.Customer;
import Utilities.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * This will add a customer
 * @author Fernando Rosa
 * */
public class Report {

    @FXML
    private TextArea textArea;

    /**
     * Takes you back to the main form.
     * @param e ActionEvent
     * @throws IOException if the form has an error
     * */
    public void button(ActionEvent e) throws IOException {
        Main.callForms(e, "MainForm");
    }

    /**
     * This will fill the report information.
     * */
    public void reportText(){

        for(Customer c : DataBase.getAllCustomers()){
            textArea.appendText("Customer \nCustomer ID: " + c.getId() + "\n" +
                    "Customer Name: " + c.getName() + "\n" +
                    "Customer Address: " + c.getAddress() + "\n" +
                    "Customer City: " + c.getCity() + "\n" +
                    "Customer Zip Code: " + c.getZipCode() + "\n" +
                    "Customer Phone: " + c.getPhone() + "\n");
            int appointmentNumber = 0;

            for(Appointment a: c.getAllAppointments()){
                appointmentNumber++;

                textArea.appendText(appointmentNumber + " Appointment\n" +
                        "Appointment ID: " + a.getAptId() + "\n" +
                        "Appointment Tittle: " + a.getTittle() + "\n" +
                        "Appointment Type: " + a.getType() + "\n" +
                        "Appointment Description: " + a.getDescription() + "\n" +
                        "Start: " + a.getStart() + " End: " + a.getEnd() + "\n" +
                        "Appointment Location: " + a.getLocation() + "\n");

                for (Contact contact: a.getAllContacts()){
                    textArea.appendText("Contact Email: " + contact.getEmail() + "\n");
                }
            }
            textArea.appendText("\n");
        }
    }

    /**
     * Initializes the form.
     * */
    @FXML
    public void initialize(){
        reportText();

    }
}
