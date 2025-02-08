package com.example.test.model;

public class LendData {

    private String LendDate;
    private String ReturnDate;
    private String Title;
    private String Author;
    private double Fee;
    private Integer Id;

    public LendData(String Title, String Author, String LendDate, String ReturnDate, double Fee, Integer Id) {
        this.LendDate = LendDate;
        this.ReturnDate = ReturnDate;
        this.Title = Title;
        this.Author = Author;
        this.Fee = Fee;
        this.Id = Id;
    }

    public String getLendDate() {return LendDate;}
    public String getReturnDate() {return ReturnDate;}
    public String getTitle() {return Title;}
    public String getAuthor() {return Author;}
    public double getFee() {return Fee;}

    public Integer getBookId() {return Id;}

}
