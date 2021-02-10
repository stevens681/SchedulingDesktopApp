package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Customer class
 * @author Fernando Rosa
 * */
public class Customer {

    private ObservableList<Appointment> appointment = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String zipCode;
    private String city;
    private String phone;

    public Customer(int id, String name, String address, String zipCode, String city, String phone){

        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phone = phone;

    }

    public Customer(ObservableList appointment, int id, String name, String address, String zipCode, String city, String phone){

        this.appointment = appointment;
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phone = phone;
    }

    public int getId(){return id;}

    public String getName(){return name;}

    public String getAddress(){return address;}

    public String getZipCode(){return zipCode;}

    public String getCity(){return city;}

    public String getPhone(){return phone;}

    public void setId(int id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setAddress(String address){this.address = address;}

    public void setZipCode(String zipCode){this.zipCode = zipCode;}

    public void setCity(String city){this.city = city;}

    public void setPhone(String phone){this.phone = phone;}

    public void addAppointment(Appointment newAppointment){appointment.add(newAppointment);}

    public boolean deleteAppointment(Appointment deleteAppointment){

        for(Appointment a: appointment){
            if(a.getAptId() == deleteAppointment.getAptId()){
                appointment.remove(a);
                return true;
            }
        }
        return false;
    }
    public ObservableList<Appointment> getAllAppointments(){ return appointment;}
}
