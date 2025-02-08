package com.example.test.Controller;

import com.example.test.SwitchSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class MainController {

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        SwitchSceneController scena = new SwitchSceneController("RegisterView.fxml", event, 400, 400);

    }

    @FXML
    protected void LoginTestButtonClick(ActionEvent event) throws IOException {
        SwitchSceneController scena = new SwitchSceneController("LoginView.fxml", event, 400, 400);
    }

}