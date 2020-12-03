package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {

    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private int aptId;
    private String description;
    private String start;
    private String end;
    private String tittle;
    private String type;
    private String location;



    public Appointment(ObservableList contacts, int aptId, String description, String start, String end, String tittle, String type, String location){

        this.contacts = contacts;
        this.aptId = aptId;
        this.description = description;
        this.start = start;
        this.end = end;
        this.tittle = tittle;
        this.type = type;
        this.location = location;
    }

    public Appointment(){}

    public int getAptId() {
        return aptId;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getTittle() {
        return tittle;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setAptId(int aptId) {
        this.aptId = aptId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addContact(Contact newContact){contacts.add(newContact);}

    public boolean deleteContact(Contact deleteContact){

        for(Contact c: contacts){
            if(c.getId() == deleteContact.getId()){
                contacts.remove(c);
                return true;
            }
        }
        return false;
    }
    public ObservableList<Contact> getAllContacts(){ return contacts;}
}
