package com.example.test.service;

import com.example.test.model.ChoosenUser;
import com.example.test.repository.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class DatabaseService extends DatabaseConnection {


    public DatabaseService() throws IOException {
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

    public void GetUser(String login) {
        String Query = "SELECT first_name, last_name FROM users WHERE username = ?";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(Query)) {
            prep.setString(1, login);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                ChoosenUser user = ChoosenUser.getInstance();
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int CountUsers() {
        String Query = "SELECT COUNT(*) FROM users";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(Query)){
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
