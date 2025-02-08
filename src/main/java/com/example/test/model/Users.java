package com.example.test.model;

public class Users {
    String login;
    String firstName;
    String lastName;



    public Users(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {return login;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
}
