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

public class BookListController {

    @FXML
    private Label BookError;

    @FXML
    private TextField bookAmount;

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
    public void initialize() throws IOException {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadBookData();
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
    protected void AddBook() throws IOException {
        if(bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()){
            BookError.setText("Uzupełnij wszystkie pola!");
        }else {
            BookError.setText("");
            try {
                BookManagment bookManagment = new BookManagment();

                bookManagment.AddBook(bookTitle.getText(),bookAuthor.getText(), Integer.parseInt(bookAmount.getText()));
                LoadData();
            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Ilość musi być cyfrą!");
            }
        }
    }

    @FXML
    protected void BookListRemoveWindow(ActionEvent event) throws IOException {
        DeleteScene booklistdelete = new DeleteScene(event);
    }

    @FXML
    protected void EditBook() throws IOException {
        if (bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()) {
            BookError.setText("Uzupełnij wszystkie pola!");
        } else {
            BookError.setText("");
            try {

                BookManagment bookManagment = new BookManagment();

                if (bookManagment.EditBook(bookTitle.getText(), bookAuthor.getText(), Integer.parseInt(bookAmount.getText()))) {
                    System.out.println("Pomyślnie edytowano ilość książek");
                    BookError.setText("");
                } else {
                    System.out.println("Nie istnieje taka książka.");
                    BookError.setText("NIe znaleziono książki");
                }
            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Ilość nie jest cyfrą!");
            }

        }
    }

    @FXML
    protected void LoadData() throws IOException {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("amount"));

        LoadDataToList loader = new LoadDataToList();
        ObservableList<Books> booksList = loader.loadBooksData();
        booksTable.setItems(booksList);
    }

    @FXML
    protected void RemoveBook() throws IOException {
        if (bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()) {
            BookError.setText("Uzupełnij wszystkie pola!");
        } else {
            BookError.setText("");
            try {


                BookManagment bookManagment = new BookManagment();

                if (bookManagment.RemoveBook(bookTitle.getText(), bookAuthor.getText())) {
                    BookError.setText("");
                    LoadData();
                } else {
                    BookError.setText("Nie znaleziono książki!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Ilość nie jest cyfrą!");
            }
        }
    }

    @FXML
    protected void getDataRow() {
        int index = booksTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        bookTitle.setText(String.valueOf(colTitle.getCellData(index)));
        bookAuthor.setText(String.valueOf(colAuth.getCellData(index)));
        bookAmount.setText(String.valueOf(colAmou.getCellData(index)));
    }
}
