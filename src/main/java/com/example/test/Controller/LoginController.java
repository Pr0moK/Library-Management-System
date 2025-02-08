package com.example.test.Controller;

import com.example.test.SwitchSceneController;
import com.example.test.service.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginController {
    @FXML
    private Label LoginError;
    @FXML
    private TextField username;
    @FXML
    private TextField password;



    @FXML
    protected void onFinishLoginButtonClick(ActionEvent event) throws IOException {
        String user;
        String userPassword;

        user = username.getText();
        userPassword = password.getText();


        System.out.println("Login " + user + " " + userPassword);
        DatabaseService databaseService = new DatabaseService();


        boolean result_login = databaseService.CheckIfUserExists(user,userPassword);

        if(result_login){
            System.out.println("Login OK");
            SwitchSceneController scena = new SwitchSceneController("MenuView.fxml",event,800,539);
        } else {
            LoginError.setText("Błąd Logowania");
            System.out.println("Login Failed");
        }


    }

    @FXML
    protected void BackButtonClick(ActionEvent event) throws IOException {
        SwitchSceneController scena = new SwitchSceneController("MainView.fxml",event,400,400);
    }

}
