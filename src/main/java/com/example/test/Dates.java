package com.example.test;

public class Dates {

    private String LendDate;
    private String ReturnDate;

    public Dates(String LendDate, String ReturnDate) {
        this.LendDate = LendDate;
        this.ReturnDate = ReturnDate;
    }

    public String getLendDate() {return LendDate;}
    public String getReturnDate() {return ReturnDate;}

}
