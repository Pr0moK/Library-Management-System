package com.example.test.model;

public class ChoosenUser {

    private static final ChoosenUser instance = new ChoosenUser();

    private String firstName;
    private String lastName;
    private String login;

    private ChoosenUser() {}


    public static ChoosenUser getInstance() {
        return instance;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUserLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

}
