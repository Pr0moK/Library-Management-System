package com.example.test;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label ErrorRegister;
    @FXML
    private Label LoginError;
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

    }

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
        DatabaseAction databaseAction = new DatabaseAction();

        boolean UserInDatabase = databaseAction.addUser(FirstName,LastName,name,AccountPassword);

        if (UserInDatabase) {
            ErrorRegister.setText("Nazwa "+ name + " jest zajęta");
        } else if (ErrorRegister.getText().isEmpty() && AccountPassword.isEmpty()) {
                ErrorRegister.setText("Błąd hasła");
        }
        else {
            ErrorRegister.setText("");
        }

    }

    @FXML
    protected void onFinishLoginButtonClick(ActionEvent event) throws IOException {
        String user;
        String userPassword;

        user = username.getText();
        userPassword = password.getText();


        System.out.println("Login " + user + " " + userPassword);
        DatabaseAction databaseAction = new DatabaseAction();


        boolean result_login = databaseAction.CheckIfUserExists(user,userPassword);

        if(result_login){
            System.out.println("Login OK");
            SwitchScene scena = new SwitchScene("menu.fxml",event,400,400);
        } else {
            LoginError.setText("Błąd Logowania");
            System.out.println("Login Failed");
        }


    }

    @FXML
    protected void LoginTestButtonClick(ActionEvent event) throws IOException {
        SwitchScene scena = new SwitchScene("login.fxml",event,400,400);
    }

    @FXML
    protected void BackButtonClick(ActionEvent event) throws IOException {
        SwitchScene scena = new SwitchScene("main.fxml",event,400,400);
    }
    @FXML
    protected void BookListButtonClick(ActionEvent event) throws IOException {
        NewScene booklistscene = new NewScene("booklist.fxml",event,800,800);
    }
    @FXML
    protected void UserListButtonClick(ActionEvent event) throws IOException {
        NewScene userlistscene = new NewScene("membersmanagment.fxml",event,800,800);

    }
    @FXML
    protected void BookListRemoveWindow(ActionEvent event) throws IOException {
        DeleteScene booklistdelete = new DeleteScene(event);
    }
    @FXML
    protected void LendBookWindow(ActionEvent event) throws IOException {
        NewScene lendbookwindow = new NewScene("lendbook.fxml",event,800,800);
    }
}