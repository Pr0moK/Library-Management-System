package com.example.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class LoadDataToList extends BaseConnect {

    public LoadDataToList() throws IOException {
        super();
    }

    public ObservableList<Books> loadBooksData() throws IOException {
        ObservableList<Books> booksList = FXCollections.observableArrayList();

        String QUERY = "SELECT * FROM books";
        try (Connection connection = getConnection();
             PreparedStatement prep = connection.prepareStatement(QUERY)) {

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Title") + "Test");
                Books book = new Books(
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Amount")
                );

                booksList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas ładowania danych: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return booksList;
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
}
