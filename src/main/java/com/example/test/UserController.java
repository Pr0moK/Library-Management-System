package com.example.test;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserController {

    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users, String> colLogin;
    @FXML
    private TableColumn<Users, String> colName;
    @FXML
    private TableColumn<Users, String> colSurname;

    @FXML
    public void initialize() throws IOException {
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loadUserData();
    }

    private void loadUserData() throws IOException {
        LoadDataToList loader = new LoadDataToList();
        try {
            ObservableList<Users> usersList = loader.loadUsersData();
            usersTable.setItems(usersList);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych użytkowników: " + e.getMessage());
        }
    }

    @FXML
    private void getUser() throws IOException {
        int index = usersTable.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }

        String login = colLogin.getCellData(index);
        String name = colName.getCellData(index);
        String surname = colSurname.getCellData(index);

        NewScene lendbookwindow = new NewScene("lendbook.fxml",800,800);

    }
}