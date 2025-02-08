package com.example.test.service;

import com.example.test.model.Book;
import com.example.test.model.LendData;
import com.example.test.model.LendedBooks;
import com.example.test.model.Users;
import com.example.test.repository.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class DataLoaderService extends DatabaseConnection {

    public DataLoaderService() throws IOException {
        super();
    }

    public ObservableList<Book> loadBooksData() throws IOException {
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        String QUERY = "SELECT * FROM books";
        try (Connection connection = getConnection();
             PreparedStatement prep = connection.prepareStatement(QUERY)) {

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Amount")
                );

                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas ładowania danych: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return bookList;
    }

    public ObservableList<Users> loadUsersData() throws IOException {
        ObservableList<Users> usersList = FXCollections.observableArrayList();

        String QUERY = "SELECT * from users";
        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(QUERY)){
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Users user = new Users(
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
                usersList.add(user);
                }

        } catch (SQLException e ){
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public ObservableList<LendData> loadLendData(String user) throws IOException {
        ObservableList<LendData> lendDataList = FXCollections.observableArrayList();

        String QUERY = "SELECT * FROM IssuedBooks WHERE username = ?";

        try(Connection connection = getConnection(); PreparedStatement pr = connection.prepareStatement(QUERY)) {
            pr.setString(1, user);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                LendData lendbook = new LendData(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("dateissued"),
                        rs.getString("datereturn"),
                        rs.getDouble("fee"),
                        rs.getInt("Id")
                );
                lendDataList.add(lendbook);
            }


        }catch (SQLException e ){
            throw new RuntimeException(e);
        }
        return lendDataList;
    }

    public ObservableList<LendedBooks> LoadLendedBooks() throws IOException {
        ObservableList<LendedBooks> LendedBooksList = FXCollections.observableArrayList();

        String QUERY = "SELECT * FROM IssuedBooks";

        try(Connection connection = getConnection(); PreparedStatement pr = connection.prepareStatement(QUERY)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                LendedBooks lendinfo = new LendedBooks(
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("dateissued"),
                        rs.getString("datereturn"),
                        rs.getDouble("fee"),
                        rs.getInt("Id")
                );
                LendedBooksList.add(lendinfo);
            }


        }catch (SQLException e ){
            throw new RuntimeException(e);
        }
        return LendedBooksList;
    }
}
