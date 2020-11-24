package Utilities;

public class Customer {

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

    public Customer(){}

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
}
