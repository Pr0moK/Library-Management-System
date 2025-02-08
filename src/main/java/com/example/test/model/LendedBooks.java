package com.example.test.model;

public class LendedBooks {

    private String LendDate;
    private String UserLogin;
    private String ReturnDate;
    private String Title;
    private String Author;
    private double Fee;
    private Integer Id;

    public LendedBooks(String Login, String Title, String Author, String LendDate, String ReturnDate, double Fee, Integer Id) {
        this.LendDate = LendDate;
        this.UserLogin = Login;
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
    public String getUserLogin() {return UserLogin;}
    public Integer getBookId() {return Id;}

}
