package com.example.test.Controller;

import com.example.test.SwitchSceneController;
import com.example.test.service.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class RegisterController {
    @FXML
    private Label ErrorRegister;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField fname;
    @FXML
    private TextField surname;

    @FXML
    protected void onFinishReg() throws IOException{
        String name;
        String LastName;
        String AccountPassword;
        String FirstName;

        name = username.getText();
        LastName = surname.getText();
        AccountPassword = password.getText();
        FirstName = fname.getText();


        System.out.printf("Imie %s Nazwisko %s Uzytkownik %s Hasło %s%n", FirstName, LastName, name, AccountPassword);

        if(name.isEmpty() || AccountPassword.isEmpty() || FirstName.isEmpty() || LastName.isEmpty()){
            ErrorRegister.setText("Uzupełnij wszystkie pola!");
        } else {
            DatabaseService databaseService = new DatabaseService();

            boolean UserInDatabase = databaseService.addUser(FirstName, LastName, name, AccountPassword);

            if (!UserInDatabase) {
                ErrorRegister.setText("Nazwa " + name + " jest zajęta");
            } else {
                ErrorRegister.setText("Zarejestrowano!");
            }
        }
    }

    @FXML
    protected void BackButtonClick(ActionEvent event) throws IOException {
        SwitchSceneController scena = new SwitchSceneController("MainView.fxml",event,400,400);
    }
}