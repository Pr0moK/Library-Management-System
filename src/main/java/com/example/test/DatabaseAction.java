package com.example.test;

import java.io.IOException;
import java.sql.*;

public class DatabaseAction extends BaseConnect{


    public DatabaseAction() throws IOException {
        super();
    }

    public boolean addUser(String firstName, String lastName, String username, String password) {

        String insertQuery = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dodano użytkownika do bazy danych.");
                return true;
            } else {
                System.out.println("Nie udało się dodać użytkownika.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas dodawania użytkownika: " + e.getMessage());
            return false;
        }
    }

    public boolean CheckIfUserExists(String usernamelogin, String password) {
        String Query = "SELECT * FROM users WHERE username = ? AND password = ?";


        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(Query)) {

            preparedStatement.setString(1, usernamelogin);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Zalogowano.");
                    return true;
                } else {
                    System.out.println("Nie znaleziono użytkownika.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas sprawdzania użytkownika: " + e.getMessage());
            return false;
        }
    }
}
