package Utilities;

public class Contact {

    private int id;
    private String email;

    public Contact(int it, String email){
        this.id = id;
        this.email = email;

    }

    public int getId(){return id;}

    public String getEmail(){return email;}

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
