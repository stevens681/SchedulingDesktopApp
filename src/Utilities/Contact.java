package Utilities;

/**
 * Contact class
 * @author Fernando Rosa
 * */
public class Contact {

    private int id;
    private String email;
    private String name;

    public Contact(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public int getId(){return id;}

    public String getName(){return name;}

    public String getEmail(){return email;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){ this.name = name;}

    public void setEmail(String email) {
        this.email = email;
    }
}
