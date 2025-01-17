package com.example.test;

import java.io.IOException;
import java.sql.*;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public boolean addUser(String firstName, String lastName, String username, String password) throws IOException {
        BaseConnect baza = new BaseConnect();


        String insertQuery = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dodano użytkownika do bazy danych.");
                return false;
            } else {
                System.out.println("Nie udało się dodać użytkownika.");
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas wstawiania danych do bazy: " + e.getMessage());
            return true;
        }
        return false;
    }
    public boolean CheckIfUserExists(String usernamelogin, String password) {
        try { Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String Query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try {PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1, usernamelogin);
                preparedStatement.setString(2, password);
                try {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        System.out.println("Zalogowani");
                        return true;

                    } else {
                        System.out.println("Nie znaleziono");
                        return false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}