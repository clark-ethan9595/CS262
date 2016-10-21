package edu.calvin.elc3.homework2;

/*
 * class Player resembles the characteristics of a player of Monopoly
 *
 * @author: Ethan Clark (elc3)
 * @version: 1.0
 * @date: October 19, 2016
 */

public class Player {

    // Declare the private String instance variables
    private String name, email_address, ID_num;

    // Explicit constructor
    public Player(String name, String email_address, String ID_num) {
        this.name = name;
        this.email_address = email_address;
        this.ID_num = ID_num;
    }

    // Accessor methods for the three instance variables
    public String getName() { return name; }
    public String getEmail_address() { return email_address; }
    public String getID_num() { return ID_num; }

}
