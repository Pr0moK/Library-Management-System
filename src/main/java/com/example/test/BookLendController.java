package com.example.test;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.awt.print.Book;
import java.io.IOException;

public class BookLendController {

    @FXML
    private TextField userSurname;

    @FXML
    private TextField userName;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookTitle;

    @FXML
    private TableView<Books> booksTable;

    @FXML
    private TableColumn<Books, Integer> colAmou;

    @FXML
    private TableColumn<Books, String> colAuth;

    @FXML
    private TableColumn<Books, String> colTitle;

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
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        loadBookData();
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
    private void loadBookData() throws IOException {
        LoadDataToList loader = new LoadDataToList();
        try {
            ObservableList<Books> booksList = loader.loadBooksData();
            booksTable.setItems(booksList);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych użytkowników: " + e.getMessage());
        }
    }

    @FXML
    void AddBook(ActionEvent event) {

    }

    @FXML
    void BookListRemoveWindow(ActionEvent event) {

    }

    @FXML
    void IssueBookButton(ActionEvent event) throws IOException {
        BookManagment bookManagment = new BookManagment();
        bookManagment.LendBook(bookTitle.getText(),bookAuthor.getText(),userLogin.getText());
    }

    @FXML
    void getDataRowUser(MouseEvent event) {
        int index = usersTable.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }
        userLogin.setText(String.valueOf(colLogin.getCellData(index)));
        userName.setText(String.valueOf(colName.getCellData(index)));
        userSurname.setText(String.valueOf(colSurname.getCellData(index)));

    }

    @FXML
    protected void getDataRowBook() {
        int index = booksTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        bookTitle.setText(String.valueOf(colTitle.getCellData(index)));
        bookAuthor.setText(String.valueOf(colAuth.getCellData(index)));
    }
}
