package com.example.test;

import java.io.IOException;
import java.sql.*;

public class BookManagement extends BaseConnect {


    public BookManagement() throws IOException {
        super();
    }

    public void AddBook(String title, String author, int amount) {
        String InsertQuery = "INSERT INTO books (title, author, amount) VALUES (?, ?, ?)";
        String CheckQuery = "SELECT * FROM books WHERE title = ? and author = ?";
        String UpdateQuery = "UPDATE books SET amount = amount + ? WHERE title = ? AND author = ?";

        try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(CheckQuery)) {

            prep.setString(1, title.toLowerCase());
            prep.setString(2, author.toLowerCase());

            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {

                    System.out.println("Book already exists. Updating amount.");

                    try (PreparedStatement updateBook = connection.prepareStatement(UpdateQuery)) {
                        updateBook.setInt(1, amount);
                        updateBook.setString(2, title.toLowerCase());
                        updateBook.setString(3, author.toLowerCase());

                        int rowsAffected = updateBook.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Successfully updated book amount.");
                        } else {
                            System.out.println("Failed to update book amount.");
                        }
                    }
                } else {
                    System.out.println("Adding new book");
                    try (PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery)) {

                        preparedStatement.setString(1, title.toLowerCase());
                        preparedStatement.setString(2, author);
                        preparedStatement.setInt(3, amount);

                        int RowsAffected = preparedStatement.executeUpdate();

                        if(RowsAffected > 0) {
                            System.out.println("Successfully added book");
                        } else {
                            System.out.println("Failed to add book");
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean RemoveBook(String title, String author) {
        String DeleteQuery = "DELETE FROM books WHERE title = ? AND author = ?";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(DeleteQuery)) {

            prep.setString(1, title.toLowerCase());
            prep.setString(2, author);
            int RowsAffected = prep.executeUpdate();

            if(RowsAffected > 0) {
                System.out.println("Successfully deleted book");
                return true;
            } else {
                System.out.println("Failed to delete book");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean EditBook(String title, String author, int amount) {
        String EditQuery = "UPDATE books SET amount = ? WHERE title = ? and author = ?";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(EditQuery) ) {
            prep.setString(2, title.toLowerCase());
            prep.setString(3, author.toLowerCase());
            prep.setInt(1, amount);

            int RowsAffected = prep.executeUpdate();
            if(RowsAffected > 0) {
                System.out.println("Successfully edited book");
                return true;
            } else {
                System.out.println("Failed to edit book");
                return false;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean LendBook(String title, String author, String user, int date) {
        String IssueQuery = "INSERT INTO IssuedBooks (title, author, username, dateissued, datereturn) VALUES (?, ?, ?, ?, ?)";
        String AvalibleAmount = "SELECT amount FROM books WHERE title = ? AND author = ?";
        final int days = getLoanDuration(date);

        try(Connection connection = getConnection(); PreparedStatement prep  = connection.prepareStatement(AvalibleAmount)){
            prep.setString(1, title.toLowerCase());
            prep.setString(2, author.toLowerCase());

            ResultSet rs = prep.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getInt("amount"));

                if(rs.getInt("amount") > 0) {

                    int newamount = rs.getInt("amount") - 1;
                    EditBook(title, author, newamount);

                    try(PreparedStatement preptwo = connection.prepareStatement(IssueQuery) ){

                        preptwo.setString(1, title.toLowerCase());
                        preptwo.setString(2, author.toLowerCase());
                        preptwo.setString(3, user.toLowerCase());
                        preptwo.setDate(4, Date.valueOf(java.time.LocalDate.now()));
                        preptwo.setDate(5, Date.valueOf(java.time.LocalDate.now().plusDays(days)));

                        int RowsAffected = preptwo.executeUpdate();
                        if(RowsAffected > 0) {
                            System.out.println("Successfully lended book");
                            return true;
                        } else {
                            System.out.println("Failed to lended book");
                            return false;
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException("Error while lending book", e);
                    }
                }else {
                    return false;
                }
            }
        } catch ( SQLException e) {
            throw new RuntimeException("Error with Database: ",e);
        }
        return false;
    }

    private int getLoanDuration(int date) {
        return switch (date) {
            case 1 -> 14;
            case 2 -> 30;
            case 3 -> 60;
            default -> 7;
        };
    }

}

