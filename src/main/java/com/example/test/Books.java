package com.example.test;

public class Books {

    private String Title;
    private String Author;
    private int Amount;


    public Books(String title, String author, int amount) {
        this.Title = title;
        this.Author = author;
        this.Amount = amount;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTitle() {
        return Title;
    }

    public int getAmount() {
        return Amount;
    }
}
