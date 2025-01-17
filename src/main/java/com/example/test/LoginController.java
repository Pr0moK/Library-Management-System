package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField fname;
    @FXML
    private TextField surname;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onTestButtonClick() {
        String url = "jdbc:mysql://localhost:3306/java";
        String username = "root";
        String password = "root";
        try(Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Połączono");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        SwitchScene scena = new SwitchScene("register.fxml",event,400,400);

    }}
