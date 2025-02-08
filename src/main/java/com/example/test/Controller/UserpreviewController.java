package com.example.test.Controller;

import com.example.test.model.ChoosenUser;
import com.example.test.model.LendData;
import com.example.test.service.DataLoaderService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class UserpreviewController {

    @FXML
    private TableView<LendData> UserPreviewTable;

    @FXML
    private TableColumn<LendData, String> colAuthor;

    @FXML
    private TableColumn<LendData, String> colDateOfLend;

    @FXML
    private TableColumn <LendData, String> colDateOfReturn;

    @FXML
    private TableColumn<LendData, String> colTitle;

    @FXML
    private TableColumn<LendData, Double> colFee;

    @FXML
    private TableColumn<LendData, Integer> colID;

    @FXML
    private Label firstname;

    @FXML
    private Label surname;

    @FXML
    private Label username;



    ChoosenUser data = ChoosenUser.getInstance();

    @FXML
    private void initialize() throws IOException {
        firstname.setText(data.getFirstName());
        surname.setText(data.getLastName());
        username.setText(data.getUserLogin());

        colID.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        colDateOfLend.setCellValueFactory(new PropertyValueFactory<>("LendDate"));
        colDateOfReturn.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("Fee"));
        LoadLendData();

    }
    private void LoadLendData() throws IOException {
        DataLoaderService loader = new DataLoaderService();
        try {
            ObservableList<LendData> lendDataList = loader.loadLendData(data.getUserLogin());
            UserPreviewTable.setItems(lendDataList);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych wypożyczeń: " + e.getMessage());
        }

    }

}
