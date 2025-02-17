package com.example.test.Controller;

import com.example.test.service.BookService;
import com.example.test.model.Book;
import com.example.test.service.DataLoaderService;
import com.example.test.model.Users;
import com.example.test.utils.OnStatisticListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Arrays;

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
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> colAmou;

    @FXML
    private TableColumn<Book, String> colAuth;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users, String> colLogin;
    @FXML
    private TableColumn<Users, String> colName;
    @FXML
    private TableColumn<Users, String> colSurname;
    @FXML
    private ChoiceBox <String> DatePicker;
    @FXML
    private String[] dates = {"7 dni", "14 dni", "30 dni", "60 dni"};
    @FXML
    private Label LendMessage;


    private OnStatisticListener StatisticListener;

    public void setOnStatisticListner(OnStatisticListener listener) {
        this.StatisticListener = listener;
    }

    @FXML
    public void initialize() throws IOException {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        DatePicker.getItems().addAll(dates);
        DatePicker.setValue(dates[0]);

        loadBookData();
        loadUserData();
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
    private void loadBookData() throws IOException {
        DataLoaderService loader = new DataLoaderService();
        try {
            ObservableList<Book> bookList = loader.loadBooksData();
            booksTable.setItems(bookList);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych użytkowników: " + e.getMessage());
        }
    }

    @FXML
    void IssueBookButton(ActionEvent event) throws IOException {
        BookService bookService = new BookService();

        String date = DatePicker.getValue();
        int dateindex = Arrays.asList(dates).indexOf(date);

        if(bookService.LendBook(bookTitle.getText(),bookAuthor.getText(),userLogin.getText(), dateindex)){
            LendMessage.setText("Pomyślnie wypożyczono książkę.");
            System.out.println("Book Lend");
            if(StatisticListener != null){
                StatisticListener.onStatisticReturn();
            }
        } else {
            LendMessage.setText("Nie wypożyczono książki.");
            System.out.println("Book Not Lend");
        }
        loadBookData();
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
