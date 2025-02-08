package com.example.test.service;

import com.example.test.repository.DatabaseConnection;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.sql.*;

public class BookService extends DatabaseConnection {


    public BookService() throws IOException {
        super();
    }

    private boolean CheckIfBookExists(String bookName, String author) {
        String CheckQuery = "SELECT * FROM books WHERE title = ? and author = ?";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(CheckQuery)){
            prep.setString(1, WordUtils.capitalize(bookName));
            prep.setString(2, WordUtils.capitalize(author));

            try(ResultSet rs = prep.executeQuery();){
                if(rs.next()){
                    System.out.println("Book exists in database");
                    return true;
                } else {
                    System.out.println("Book does not exist in database");
                    return false;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private boolean UpdateBookAmount(String bookName, String author ,int amount) {
        String UpdateQuery = "UPDATE books SET amount = amount + ? WHERE title = ? AND author = ?";
        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(UpdateQuery)){
            prep.setInt(1, amount);
            prep.setString(2, WordUtils.capitalize(bookName));
            prep.setString(3, WordUtils.capitalize(author));
            int RowsAffected = prep.executeUpdate();

            if(RowsAffected > 0){
                System.out.println("Book updated + " + amount);
                return true;
            } else {
                System.out.println("Book does not updated + " + amount);
                return false;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void AddBook(String title, String author, int amount) {
        String InsertQuery = "INSERT INTO books (title, author, amount) VALUES (?, ?, ?)";
        if(CheckIfBookExists(title, author)) {
            UpdateBookAmount(title, author, amount);
        } else {
            try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(InsertQuery)) {

                prep.setString(1, WordUtils.capitalize(title));
                prep.setString(2, WordUtils.capitalize(author));
                prep.setInt(3, amount);
                int RowsAffected = prep.executeUpdate();
                if (RowsAffected > 0) {
                    System.out.println("Successfully added book");
                } else {
                    System.out.println("Failed to add book");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean RemoveBook(String title, String author) {
        String DeleteQuery = "DELETE FROM books WHERE title = ? AND author = ?";

        try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(DeleteQuery)) {

            prep.setString(1, WordUtils.capitalize(title));
            prep.setString(2, WordUtils.capitalize(author));
            int RowsAffected = prep.executeUpdate();

            if (RowsAffected > 0) {
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

    public boolean EditBookAmount(String title, String author, int amount) {
        String EditQuery = "UPDATE books SET amount = ? WHERE title = ? and author = ?";

        try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(EditQuery)) {
            prep.setString(2, WordUtils.capitalize(title));
            prep.setString(3, WordUtils.capitalize(author));
            prep.setInt(1, amount);

            int RowsAffected = prep.executeUpdate();
            if (RowsAffected > 0) {
                System.out.println("Successfully edited book");
                return true;
            } else {
                System.out.println("Failed to edit book");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean LendBook(String title, String author, String user, int date) {
        String IssueQuery = "INSERT INTO IssuedBooks (title, author, username, dateissued, datereturn, fee) VALUES (?, ?, ?, ?, ?, ?)";
        String AvalibleAmount = "SELECT amount FROM books WHERE title = ? AND author = ?";
        final int days = getLoanDuration(date);

        try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(AvalibleAmount)) {
            prep.setString(1, WordUtils.capitalize(title));
            prep.setString(2, WordUtils.capitalize(author));

            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("amount"));

                if (rs.getInt("amount") > 0) {

                    int newamount = rs.getInt("amount") - 1;
                    EditBookAmount(title, author, newamount);

                    try (PreparedStatement preptwo = connection.prepareStatement(IssueQuery)) {

                        preptwo.setString(1, WordUtils.capitalize(title));
                        preptwo.setString(2, WordUtils.capitalize(author));
                        preptwo.setString(3, user);
                        preptwo.setDate(4, Date.valueOf(java.time.LocalDate.now()));
                        preptwo.setDate(5, Date.valueOf(java.time.LocalDate.now().plusDays(days)));
                        preptwo.setInt(6, 0);

                        int RowsAffected = preptwo.executeUpdate();
                        if (RowsAffected > 0) {
                            System.out.println("Successfully lended book");
                            return true;
                        } else {
                            System.out.println("Failed to lended book");
                            return false;
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException("Error while lending book", e);
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with Database: ", e);
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

    public boolean ReturnBook(Integer BookId) {
        String QUERY = "SELECT title, author, fee FROM IssuedBooks WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(QUERY)) {
            prep.setInt(1, BookId);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                double fee = rs.getDouble("fee");
                String title = rs.getString("title");
                String author = rs.getString("author");
                if (fee > 0) {
                    return true;
                } else {
                    String QUERYRETURN = "DELETE FROM IssuedBooks WHERE id = ?";
                    try (PreparedStatement prep2 = connection.prepareStatement(QUERYRETURN)) {
                        prep2.setInt(1, BookId);
                        prep2.executeUpdate();
                        if(CheckIfBookExists(title, author)) {
                            UpdateBookAmount(title, author, 1);
                        }
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public int CountBooks(){
        String QUERY = "SELECT SUM(amount) FROM books";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(QUERY)){
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int CountIssuedBooks(){
        String QUERY = "SELECT COUNT(*) FROM IssuedBooks";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(QUERY)){
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}

