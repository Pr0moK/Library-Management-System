package com.example.test.Controller;

import com.example.test.NewSceneController;
import com.example.test.model.ChoosenUser;
import com.example.test.model.Users;
import com.example.test.service.DataLoaderService;
import com.example.test.service.IssuedBookFeeService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class UsersController {

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

        IssuedBookFeeService fee = new IssuedBookFeeService();
        fee.CheckFee();
    }

    private void loadUserData() throws IOException {
        DataLoaderService loader = new DataLoaderService();
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

        ChoosenUser data = ChoosenUser.getInstance();
        data.setLogin(colLogin.getCellData(index));
        data.setFirstName(colName.getCellData(index));
        data.setLastName(colSurname.getCellData(index));


        NewSceneController lendbookwindow = new NewSceneController("UserdetailsView.fxml",800,800);

    }
}