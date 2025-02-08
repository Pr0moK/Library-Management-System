package com.example.test.model;

public class ChosenIssuedBook {

    private static final ChosenIssuedBook instance = new ChosenIssuedBook();

    private int issuedBookId;
    private Double fee;


    private ChosenIssuedBook() {}


    public static ChosenIssuedBook getInstance() {
        return instance;
    }

    public void setIssuedBookId(int issuedBookId) {this.issuedBookId = issuedBookId;}
    public int getIssuedBookId() {return issuedBookId;}
    public void setUserFee(Double fee) {
        this.fee = fee;
    }
    public Double getUserFee(){
        return fee;
    }
}
